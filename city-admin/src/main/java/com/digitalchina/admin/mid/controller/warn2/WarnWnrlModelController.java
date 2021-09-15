package com.digitalchina.admin.mid.controller.warn2;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.SignQuota;
import com.digitalchina.admin.mid.entity.warn.WarnWnrlModel;
import com.digitalchina.admin.mid.entity.warn.WarnWnrlModelParam;
import com.digitalchina.admin.mid.entity.warn.WarnWrnlModelCondition;
import com.digitalchina.admin.mid.service.WarnWnrlModelParamService;
import com.digitalchina.admin.mid.service.WarnWnrlModelService;
import com.digitalchina.admin.mid.service.WarnWrnlModelConditionService;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.TransConstant;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 预警规则模型 前端控制器
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-30
 */
//@Authorize
@Api(tags = "预警模型维护")
@RestController
@RequestMapping("/warnWnrlModel")
public class WarnWnrlModelController {
	@Autowired
	private WarnWnrlModelService warnWnrlModelService;
	@Autowired
	private WarnWnrlModelParamService warnWnrlModelParamService;
	@Autowired
	private WarnWrnlModelConditionService warnWrnlModelConditionService;

	/**
	 * 提交前的数据检查
	 * 
	 * @param entity
	 */
	private void beforeCommit(WarnWnrlModel entity) {
		if (ObjectUtil.isEmpty(entity)) {
			throw new ServiceException("请求参数有误！");
		}
		List<WarnWnrlModelParam> params = entity.getParams();
		List<WarnWrnlModelCondition> conditions = entity.getConditions();
		// 检查形参是否重复
		if (ObjectUtil.isNotEmpty(params)
				&& (params.stream().map(WarnWnrlModelParam::getCode).distinct().count() < params.size())) {
			throw new ServiceException("模型参数中,参数英文名有重复！");

		}
		// 检查条件中的形参是否有误
		if (ObjectUtil.isEmpty(params) && ObjectUtil.isNotEmpty(conditions)) {
			throw new ServiceException("请求参数有误，有预警规则无模型参数！");
		}

		if (ObjectUtil.isNotEmpty(params) && ObjectUtil.isNotEmpty(conditions)) {
			List<String> paramList = params.stream().map(WarnWnrlModelParam::getCode).collect(Collectors.toList());
			for (WarnWrnlModelCondition condition : conditions) {
				if (!paramList.contains(condition.getCode1())) {
					throw new ServiceException(condition.getCode1() + "不是模型参数！");
				}
				if (StrUtil.isNotEmpty(condition.getCode2()) && !paramList.contains(condition.getCode2())) {
					throw new ServiceException(condition.getCode2() + "不是模型参数！");
				}
				boolean mark = StrUtil.isNotEmpty(condition.getCode2());
				mark ^= StrUtil.isNotEmpty(condition.getVal());
				if (!mark) {
					throw new ServiceException("条件中另一个参数要么是固定值，要么是确定的参数！");
				}
			}
		}
	}

	@PostMapping("save")
	@ApiOperation("新增修改预警模型")
	@Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
	public RespMsg save(@Validated @RequestBody WarnWnrlModel entity) {
		beforeCommit(entity);
		Date now = new Date();
		entity.setCrdt(now);
		entity.setModt(now);
		warnWnrlModelService.sau(entity);
		return RespMsg.ok();
	}

	@GetMapping("delete")
	@ApiOperation("删除预警模型")
	@ApiImplicitParam(name = "id", value = "主键ID", dataType = "Integer", required = true)
	@Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
	public RespMsg delete(Integer id) {
		warnWnrlModelService.removeById(id);
		warnWnrlModelParamService
				.remove(Condition.<WarnWnrlModelParam>create().lambda().eq(WarnWnrlModelParam::getModelid, id));
		warnWrnlModelConditionService
				.remove(Condition.<WarnWrnlModelCondition>create().lambda().eq(WarnWrnlModelCondition::getModelid, id));
		return RespMsg.ok();
	}

	@GetMapping("find")
	@ApiOperation("查询单个预警模型详情")
	@ApiImplicitParam(name = "id", value = "主键ID", dataType = "Integer", required = true)
	public RespMsg<WarnWnrlModel> find(Integer id) {
		WarnWnrlModel warnWnrlModel = warnWnrlModelService.getById(id);
		if (ObjectUtil.isEmpty(warnWnrlModel)) {
			return RespMsg.ok(warnWnrlModel);
		}
		warnWnrlModel.setParams(warnWnrlModelParamService
				.list(Condition.<WarnWnrlModelParam>create().lambda().eq(WarnWnrlModelParam::getModelid, id)));
		warnWnrlModel.setConditions(warnWrnlModelConditionService.getListByModeId(id));
		return RespMsg.ok(warnWnrlModel);
	}

	@GetMapping("state")
	@ApiOperation("启用或停用")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "主键ID", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "status", value = "要更新的状态：1 启用、0 停用", dataType = "Integer", required = true) })
	public RespMsg state(Integer id, Integer status) {
		WarnWnrlModel entity = new WarnWnrlModel();
		entity.setId(id);
		entity.setStatus(status);
		entity.setModt(new Date());
		warnWnrlModelService.updateById(entity);
		return RespMsg.ok();
	}

	@GetMapping(value = "list")
	@ApiOperation("获取预警模型列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "status", value = "状态 1 启用、0 停用", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "keyword", value = "keyword", dataType = "String", required = false),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10", required = false),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer", required = false) })
	public RespMsg<IPage<WarnWnrlModel>> list(@RequestParam(required = false) Integer status,
			@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "1") Integer current) {
		IPage<SignQuota> page = new Page<>(current, size);
		return RespMsg.ok(warnWnrlModelService.getWarnWnrlModelForPage(page, status, keyword));
	}

	@GetMapping("queryAll")
	@ApiOperation(value = "查询所有模型列表，用于字典")
	public RespMsg<List<WarnWnrlModel>> queryAll() {
		return RespMsg.ok(warnWnrlModelService.list(null));
	}

	@GetMapping("getParas")
	@ApiOperation(value = "根据模型id查询参数列表，用于字典")
	@ApiImplicitParam(name = "id", value = "主键ID", dataType = "Integer", required = true)
	public RespMsg<List<WarnWnrlModelParam>> queryAll(Integer id) {
		return RespMsg.ok(warnWnrlModelParamService
				.list(Condition.<WarnWnrlModelParam>create().lambda().eq(WarnWnrlModelParam::getModelid, id)));
	}

}
