package com.digitalchina.admin.mid.controller.warn;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.Iotdsen;
import com.digitalchina.admin.mid.service.IotdsenService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 设备传感器(实时数据) 前端控制器
 * </p>
 *
 * @author lichunlong
 * @since 2019-08-30
 */
@Api(tags = "设备传感器(实时数据) 管理")
@Authorize
@RestController
@RequestMapping("/admin/iotdsen")
public class IotdsenController {

	@Autowired
	private IotdsenService iotdsenService;

	@PostMapping("list")
	@ApiOperation("获取设备传感器(实时数据) 数据（列表）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "idsnm", value = "传感器名称"),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer") })
	public RespMsg<IPage<Iotdsen>> list(String idsnm, @RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "1") Integer current) {

		IPage<Iotdsen> page = new Page<>(current, size);
		QueryWrapper<Iotdsen> wrapper = new QueryWrapper<Iotdsen>();
		wrapper.like(ObjectUtil.isNotEmpty(idsnm), Iotdsen.IDSNM, idsnm);
		wrapper.orderByDesc(Iotdsen.IDSID);
		IPage<Iotdsen> cpspvs = iotdsenService.page(page, wrapper);
		return RespMsg.ok(cpspvs);
	}

	@PostMapping("get")
	@ApiOperation("获取设备传感器(实时数据) 数据（单个）")
	@ApiImplicitParam(name = "idsid", required = true, value = "设备传感器(实时数据) ID")
	public RespMsg<Iotdsen> get(Integer idsid) {
		Iotdsen iotdsen = iotdsenService.getOne(Condition.<Iotdsen>create().eq(Iotdsen.IDSID, idsid));
		return RespMsg.ok(iotdsen);
	}

	@PostMapping("save")
	@ApiOperation("新增设备传感器(实时数据) 数据")
	@Transactional
	public RespMsg<Iotdsen> saveOne(Iotdsen iotdsen) {
		Optional.ofNullable(iotdsen).ifPresent(s -> iotdsenService.save(s));
		return RespMsg.ok(iotdsen);
	}

	@PostMapping("update")
	@ApiOperation("修改单个设备传感器(实时数据) 数据")
	@Transactional
	public RespMsg<Iotdsen> updateOne(Iotdsen iotdsen) {
		Optional.ofNullable(iotdsen).ifPresent(s -> {
			s.setModt(DateUtil.formatDateTime(new Date()));
			iotdsenService.updateById(s);
		});
		return RespMsg.ok(iotdsen);
	}

	@PostMapping("removeById")
	@ApiOperation("删除单个设备传感器(实时数据) 数据")
	@ApiImplicitParam(name = "idsid", required = true, value = "设备传感器(实时数据) ID")
	public RespMsg<Void> delete(Integer idsid) {
		Optional.ofNullable(idsid)
				.ifPresent(s -> iotdsenService.remove(Condition.<Iotdsen>create().eq(Iotdsen.IDSID, s)));
		return RespMsg.ok();
	}

}
