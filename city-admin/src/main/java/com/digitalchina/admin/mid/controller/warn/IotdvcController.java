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
import com.digitalchina.admin.mid.entity.Iotdvc;
import com.digitalchina.admin.mid.service.IotdvcService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "设备(点位)管理")
@Authorize
@RestController
@RequestMapping("/admin/iotdvc")
public class IotdvcController {

	@Autowired
	private IotdvcService iotdvcService;

	@PostMapping("list")
	@ApiOperation("获取设备(点位)数据（列表）")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer") })
	public RespMsg<IPage<Iotdvc>> list(@RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "1") Integer current) {

		QueryWrapper<Iotdvc> wrapper = Condition.<Iotdvc>create().orderByDesc(Iotdvc.IDID);
		IPage<Iotdvc> iotdvcs = iotdvcService.page(new Page<>(current, size), wrapper);
		return RespMsg.ok(iotdvcs);
	}

	@PostMapping("get")
	@ApiOperation("获取设备(点位)数据（单个）")
	@ApiImplicitParam(name = "idid", required = true, value = "设备(点位)ID")
	public RespMsg<Iotdvc> get(Integer idid) {

		Iotdvc iotdvc = iotdvcService.getOne(Condition.<Iotdvc>create().eq(Iotdvc.IDID, idid));
		return RespMsg.ok(iotdvc);
	}

	@PostMapping("save")
	@ApiOperation("新增设备(点位)数据")
	@Transactional
	public RespMsg<Iotdvc> saveOne(Iotdvc iotdvc) {

		Optional.ofNullable(iotdvc).ifPresent(s -> iotdvcService.saveOne(s));
		return RespMsg.ok(iotdvc);
	}

	@PostMapping("update")
	@ApiOperation("修改单个设备(点位)数据")
	@Transactional
	public RespMsg<Iotdvc> updateOne(Iotdvc iotdvc) {

		Optional.ofNullable(iotdvc).ifPresent(s -> {
			s.setModt(DateUtil.formatDateTime(new Date()));
			iotdvcService.updateOne(s);
		});
		return RespMsg.ok(iotdvc);
	}

	@PostMapping("removeById")
	@ApiOperation("删除单个设备(点位)数据")
	@ApiImplicitParam(name = "idid", required = true, value = "设备(点位)ID")
	public RespMsg<Void> delete(Integer idid) {

		Optional.ofNullable(idid).ifPresent(s -> iotdvcService.remove(Condition.<Iotdvc>create().eq(Iotdvc.IDID, s)));
		return RespMsg.ok();
	}

}
