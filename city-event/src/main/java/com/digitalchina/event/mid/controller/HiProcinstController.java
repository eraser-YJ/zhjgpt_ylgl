package com.digitalchina.event.mid.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.dto.BedeptDto;
import com.digitalchina.event.dto.HistogramDto;
import com.digitalchina.event.dto.LineDto;
import com.digitalchina.event.dto.PieChartDto;
import com.digitalchina.event.dto.SerieDto;
import com.digitalchina.event.mid.entity.Bedept;
import com.digitalchina.event.mid.service.BedeptService;
import com.digitalchina.event.mid.service.HiProcinstService;
import com.digitalchina.modules.constant.TransConstant;
import com.digitalchina.modules.constant.enums.EventTypeEnum;
import com.digitalchina.modules.constant.enums.PeriodEnum;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 历史的流程实例 前端控制器
 * </p>
 *
 * @author lzy
 * @since 2019-09-06
 */
@Authorize
@Api(tags = "事件统计接口")
@RestController
@RequestMapping("/hiProcinst")
public class HiProcinstController {
	@Autowired
	private HiProcinstService hiProcinstService;
	@Autowired
	private BedeptService bedeptService;

	private static final BigDecimal MAIN_PER = new BigDecimal("0.8");

	private static final Integer MAX_DEPT = Integer.valueOf(3);

	/**
	 * 问题趋势分析 历史4季/6月/12周/30天
	 * 
	 * @param type 类型 DAY WEEK MONTH QUARTER
	 * @return
	 */
	@PostMapping(value = "trendAnalysis", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "事件变化趋势分析")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "类型:默认DAY,可选值： DAY  WEEK  MONTH  QUARTER", dataType = "String", required = false) })
	public RespMsg<LineDto> trendAnalysis(@RequestParam(defaultValue = "DAY") String type) {
		if (!type.equals(PeriodEnum.DAY.getCode()) && !type.equals(PeriodEnum.WEEK.getCode())
				&& !type.equals(PeriodEnum.MONTH.getCode()) && !type.equals(PeriodEnum.QUARTER.getCode())) {
			throw new ServiceException("参数错误！");
		}
		List<Map<String, Object>> dataList = hiProcinstService.trendAnalysis(type);
		return RespMsg.ok(transformOneLine(dataList, "事件变化趋势"));
	}

	@PostMapping("avgProceTime")
	@ApiOperation(value = "总体事件平均处理时长")
	public RespMsg<BigDecimal> avgProceTime() {
		return RespMsg.ok(hiProcinstService.avgProceTime());
	}

	@PostMapping("completionAnalysis")
	@ApiOperation(value = "超过/未超过平均处理时长的事件数量及占比")
	public RespMsg<List<PieChartDto>> completionAnalysis() {
		List<PieChartDto> dataList = hiProcinstService.completionAnalysis();
		calculatePer(dataList);
		return RespMsg.ok(dataList);
	}

	/**
	 * 统计各来源/区域/类型/部门事件平均处理时长，并与总平均时长对比分析
	 * 
	 * @param type 类型 SOURCE AREA TYPE DEPT
	 * @return
	 */
	@PostMapping(value = "typeAnalysis", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "统计各来源/区域/类型/部门事件平均处理时长，并与总平均时长对比分析")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "类型:默认SOURCE,可选值： SOURCE  AREA  TYPE  DEPT", dataType = "String", required = false) })
	@Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER, isolation = Isolation.REPEATABLE_READ)
	public RespMsg<JSONObject> typeAnalysis(@RequestParam(defaultValue = "SOURCE") String type) {
		if (!type.equals(EventTypeEnum.SOURCE.getCode()) && !type.equals(EventTypeEnum.AREA.getCode())
				&& !type.equals(EventTypeEnum.TYPE.getCode()) && !type.equals(EventTypeEnum.DEPT.getCode())) {
			throw new ServiceException("参数错误！");
		}
		List<HistogramDto> dataList = hiProcinstService.typeAnalysis(type);
		for (HistogramDto histogramDto : dataList) {
			DecimalFormat df = new DecimalFormat("#.00");
			histogramDto.setValue(Float.valueOf(df.format(histogramDto.getValue())));
		}
		BigDecimal avg = hiProcinstService.avgProceTime();
		JSONObject resultObj = new JSONObject();
		resultObj.put("data", dataList);
		resultObj.put("avg", avg);
		return RespMsg.ok(resultObj);
	}

	/**
	 * 统计并排行显示事件高发类型/区域/部门
	 * 
	 * @param type TYPE AREA DEPT
	 * @return
	 */
	@PostMapping(value = "topByType", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "统计并排行显示事件高发类型/区域/部门")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "类型:TYPE,可选值： TYPE AREA  DEPT", dataType = "String", required = false),
			@ApiImplicitParam(name = "rows", value = "前几条,默认返回全部", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "bedid", value = "部门id，统计该部门下的子部门，可选;不传默认统计一级部门.", dataType = "Integer", required = false) })
	public RespMsg<List<HistogramDto>> topByType(@RequestParam(defaultValue = "TYPE") String type,
			@RequestParam(required = false) Integer rows, @RequestParam(required = false) Integer bedid) {
		if (!type.equals(EventTypeEnum.AREA.getCode()) && !type.equals(EventTypeEnum.TYPE.getCode())
				&& !type.equals(EventTypeEnum.DEPT.getCode())) {
			throw new ServiceException("参数错误！");
		}
		Integer bdtype = null;
		if (null != bedid) {
			Bedept bedept = bedeptService.getById(bedid);
			bdtype = bedept.getBdtype();
		}

		List<HistogramDto> dataList = hiProcinstService.topByType(type, rows, bedid, bdtype);
		return RespMsg.ok(dataList);
	}

	/**
	 * 获取一二级部门树
	 * 
	 * @return
	 */
	@PostMapping("getTree")
	@ApiOperation(value = "获取一二级部门树")
	public List<BedeptDto> getTree() {
		List<Bedept> list = bedeptService
				.list(Condition.<Bedept>lambda().eq(Bedept::getBdtype, 1).or().eq(Bedept::getBdtype, 2));
		return BedeptDto.makeTree(
				BedeptDto.makeTree(list.stream().map(item -> new BedeptDto(item)).collect(Collectors.toList())));
	}

	/**
	 * 事件来源分析 1.统计各来源事件数量及占比； 2.分析占比第一的事件来源对应的主要责任部门。
	 * 
	 * @return
	 */
	@PostMapping("sourceAnalysis")
	@ApiOperation(value = "事件来源分析")
	@Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER, isolation = Isolation.REPEATABLE_READ)
	public RespMsg<JSONObject> sourceAnalysis() {
		List<PieChartDto> dataList = hiProcinstService.sourceAnalysis();
		calculatePer(dataList);
		List<HistogramDto> descList = hiProcinstService.deptAnalysisForFirstSource();
		JSONObject resultObj = new JSONObject();
		resultObj.put("data", dataList);
		resultObj.put("desc", descListToDesc(descList));
		return RespMsg.ok(resultObj);
	}

	/**
	 * 转成一条单数值的折线
	 * 
	 * @param dataList 数据
	 * @param name     折线名称
	 * @return
	 */
	private LineDto transformOneLine(List<Map<String, Object>> dataList, String name) {
		LineDto lineDto = new LineDto();
		List<String> xData = dataList.stream().map(item -> (String) item.get("name")).collect(Collectors.toList());
		lineDto.setXData(xData);
		List<SerieDto> yData = new ArrayList<>();
		lineDto.setYData(yData);

		SerieDto<Long> serieDto = new SerieDto<>();
		serieDto.setName(name);
		List<Long> data = dataList.stream().map(item -> (Long) item.get("value")).collect(Collectors.toList());
		serieDto.setData(data);
		yData.add(serieDto);
		return lineDto;
	}

	/**
	 * 计算占比
	 * 
	 * @param dataList
	 */
	private void calculatePer(List<PieChartDto> dataList) {
		if (ObjectUtil.isEmpty(dataList)) {
			return;
		}
		Integer sum = dataList.stream().map(PieChartDto::getValue).reduce(0, Integer::sum);
		BigDecimal total = new BigDecimal(sum);
		for (PieChartDto dto : dataList) {
			dto.setPer(getPer(new BigDecimal(dto.getValue()), total, 2));
		}

	}

	/**
	 * 计算百分率，不带百分号
	 * 
	 * @param mount 分子
	 * @param total 总数
	 * @param scale 保留的小数位
	 * @return
	 */
	private BigDecimal getPer(BigDecimal mount, BigDecimal total, int scale) {
		BigDecimal zero = new BigDecimal(0);
		if ((total.compareTo(zero) == 0)) {
			return zero;
		}
		return mount.multiply(new BigDecimal(100)).divide(total, scale, BigDecimal.ROUND_HALF_UP);

	}

	/**
	 * 策略暂定为取前三 或 超过80% 描述主要责任部门
	 * 
	 * @param descList
	 * @return
	 */
	private String descListToDesc(List<HistogramDto> descList) {
		if (ObjectUtil.isEmpty(descList)) {
			return null;
		}
		Integer sum = descList.stream().map(HistogramDto::getValue).reduce(0f, Float::sum).intValue();
		if (sum == 0) {
			return null;
		}
		BigDecimal deadLine = new BigDecimal(sum).multiply(MAIN_PER);

		StringBuffer sb = new StringBuffer("占比第一的事件来源对应的主要责任部门为:");
		Integer total = Integer.valueOf(0);
		for (int i = 0; i < descList.size(); i++) {
			if (i == MAX_DEPT || new BigDecimal(total).compareTo(deadLine) >= 0) {
				return sb.deleteCharAt(sb.length() - 1).append("。").toString();
			}
			sb.append(descList.get(i).getName()).append(",");
			total = total + descList.get(i).getValue().intValue();
		}
		return sb.deleteCharAt(sb.length() - 1).append("。").toString();
	}

}
