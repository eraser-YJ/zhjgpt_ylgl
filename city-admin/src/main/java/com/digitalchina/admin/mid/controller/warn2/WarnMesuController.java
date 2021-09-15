package com.digitalchina.admin.mid.controller.warn2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.dto.warn2.TypeDto;
import com.digitalchina.admin.mid.dto.warn2.WarnMesuDetailDto;
import com.digitalchina.admin.mid.dto.warn2.WarnMesuDto;
import com.digitalchina.admin.mid.entity.Comssn;
import com.digitalchina.admin.mid.service.ComssnService;
import com.digitalchina.admin.mid.service.warn2.WarnMesuService;
import com.digitalchina.admin.remoteservice.CityWarnService;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 预警模型-指标维护
 * </p>
 *
 * @author Warrior
 * @since 2019-08-30
 */
@RestController
@RequestMapping("admin/warn/mesu")
//@Authorize
@Api(tags = "预警模型-指标维护")
public class WarnMesuController {

	@Autowired
	private WarnMesuService service;

	@Autowired
	private ComssnService comssnService;

	@Autowired
	private CityWarnService cityWarnService;

	@GetMapping("list")
	@ApiOperation("列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = false),
			@ApiImplicitParam(name = "current", value = "第几页，默认1页", dataType = "int", required = false),
			@ApiImplicitParam(name = "special", value = "专题", dataType = "String", required = false),
			@ApiImplicitParam(name = "topic", value = "主题", dataType = "String", required = false),
			@ApiImplicitParam(name = "type", value = "类型（0监测点，1：组件）", dataType = "String", required = false),
			@ApiImplicitParam(name = "cmnid", value = "数据来源（委办局id)", dataType = "String", required = false),
			@ApiImplicitParam(name = "keyword", value = "关键字", dataType = "String", required = false) })
	public RespMsg<IPage<WarnMesuDto>> iotlist(@RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "1") Integer current, String special, String topic, Integer type,
			Integer cmnid, String keyword) {

		if (0 == type) {
			// 物联网
			return RespMsg.ok(service.iotlist(new Page<>(current, size), special, topic, cmnid, keyword));
		} else if (1 == type) {
			// 业务组件
			return RespMsg.ok(service.nflist(new Page<>(current, size), special, topic, cmnid, keyword));
		} else {
			throw new ServiceException("参数不合法");
		}
	}

	@GetMapping("detail")
	@ApiOperation("明细")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id"), @ApiImplicitParam(name = "type", value = "类型（0监测点，1：组件）") })
	public RespMsg<WarnMesuDetailDto> detail(Integer id, Integer type) {
		if (0 == type) {
			// 物联网
			return RespMsg.ok(service.iotDetail(id));
		} else if (1 == type) {
			// 业务组件
			return RespMsg.ok(service.nfDetail(id));
		} else {
			throw new ServiceException("参数不合法");
		}
	}

	@GetMapping("comssn")
	@ApiOperation("数据来源（委办局")
	public RespMsg<List<Comssn>> list() {
		return RespMsg.ok(comssnService.list(Condition.<Comssn>create().select("cmnid", "cmnnm")));
	}

	@GetMapping("topic")
	@ApiOperation("主题专题")
	public RespMsg<List<TypeDto>> topic() {
		return cityWarnService.getspecialandtheme();
	}

}
