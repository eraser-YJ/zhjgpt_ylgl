package com.digitalchina.admin.mid.controller.warn2;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.dto.warn2.WarnRiskDetailDto;
import com.digitalchina.admin.mid.entity.WrnlRisk;
import com.digitalchina.admin.mid.service.warn2.WrnlRiskService;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.enums.FrequencyEnum;
import com.digitalchina.modules.entity.SysDept;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 预警模型-风险维护
 * </p>
 *
 * @author Warrior
 * @since 2019-08-30
 */
@RestController
@RequestMapping("admin/warn/risk")
//@Authorize
@Api(tags = "预警模型-风险维护")
public class WarnRiskController {

	@Autowired
	private WrnlRiskService service;

	@GetMapping("list")
	@ApiOperation("列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = false),
			@ApiImplicitParam(name = "current", value = "第几页，默认1页", dataType = "int", required = false),
			@ApiImplicitParam(name = "special", value = "专题", dataType = "String", required = false),
			@ApiImplicitParam(name = "topic", value = "主题", dataType = "String", required = false),
			@ApiImplicitParam(name = "type", value = "类型（0监测点，1：组件）", dataType = "String", required = false),
			@ApiImplicitParam(name = "status", value = "状态（1启用， 0停用）", dataType = "String", required = false),
			@ApiImplicitParam(name = "keyword", value = "关键字", dataType = "String", required = false) })
	public RespMsg<IPage<WrnlRisk>> list(@RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "1") Integer current, String special, String topic, Integer type,
			Integer status, String keyword) {

		if (0 == type) {
			// 物联网
			return RespMsg.ok(service.iotlist(new Page<WrnlRisk>(current, size), special, topic, status, keyword));
		} else if (1 == type) {
			// 业务组件
			return RespMsg.ok(service.nflist(new Page<WrnlRisk>(current, size), special, topic, status, keyword));
		} else {
			throw new ServiceException("参数不合法");
		}
	}

	@GetMapping("detail")
	@ApiOperation("明细")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id"), @ApiImplicitParam(name = "type", value = "类型（0监测点，1：组件）") })
	public RespMsg<WarnRiskDetailDto> detail(Integer id) {
		return RespMsg.ok(service.detail(id));
	}

	@PostMapping("save")
	@ApiOperation("保存")
	public RespMsg<Void> save(@RequestBody WarnRiskDetailDto data) {
		service.save(data);
		return RespMsg.ok();
	}

	@GetMapping("enable")
	@ApiOperation("停、启用")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id"), @ApiImplicitParam(name = "status", value = "状态（1启用、0停用）") })
	public RespMsg<Void> detail(Integer id, Integer status) {
		service.updateById(WrnlRisk.builder().id(id).status(status).modt(new Date()).build());
		return RespMsg.ok();
	}

	@GetMapping("frequency")
	@ApiOperation("频率")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id"), @ApiImplicitParam(name = "status", value = "状态（1启用、0停用）") })
	public RespMsg<List<Map<String, Object>>> frequency(Integer id, Integer status) {
		return RespMsg.ok(FrequencyEnum.fields());
	}

	@GetMapping("dept")
	@ApiOperation("统一部门")
	public RespMsg<List<SysDept>> dept() {
		return RespMsg.ok(service.depts());
	}

}
