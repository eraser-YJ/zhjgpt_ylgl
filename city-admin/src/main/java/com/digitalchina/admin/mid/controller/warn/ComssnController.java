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
import com.digitalchina.admin.mid.entity.Comssn;
import com.digitalchina.admin.mid.service.ComssnService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "委办局管理")
@Authorize
@RestController
@RequestMapping("/admin/comssn")
public class ComssnController {

	@Autowired
	private ComssnService comssnService;

	@PostMapping("list")
	@ApiOperation("获取委办局数据（列表）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cmnnm", value = "委办局名称"),
			@ApiImplicitParam(name = "cmnnm2", value = "委办局原名称"),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer") })
	public RespMsg<IPage<Comssn>> list(String cmnnm, String cmnnm2, @RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "1") Integer current) {

		QueryWrapper<Comssn> wrapper = Condition.<Comssn>create();
		wrapper.like(ObjectUtil.isNotEmpty(cmnnm), Comssn.CMNNM, cmnnm);
		wrapper.like(ObjectUtil.isNotEmpty(cmnnm2), Comssn.CMNNM2, cmnnm2);
		wrapper.orderByDesc(Comssn.CMNID);
		IPage<Comssn> comssns = comssnService.page(new Page<>(current, size), wrapper);
		return RespMsg.ok(comssns);
	}

	@PostMapping("get")
	@ApiOperation("获取委办局数据（单个）")
	@ApiImplicitParam(name = "cmnid", required = true, value = "委办局ID")
	public RespMsg<Comssn> get(Integer cmnid) {

		Comssn comssn = comssnService.getOne(Condition.<Comssn>create().eq(Comssn.CMNID, cmnid));
		return RespMsg.ok(comssn);
	}

	@PostMapping("save")
	@ApiOperation("新增委办局数据")
	@Transactional
	public RespMsg<Comssn> saveOne(Comssn comssn) {
		Optional.ofNullable(comssn).ifPresent(s -> comssnService.save(s));
		return RespMsg.ok(comssn);
	}

	@PostMapping("update")
	@ApiOperation("修改单个委办局数据")
	@Transactional
	public RespMsg<Comssn> updateOne(Comssn comssn) {
		Optional.ofNullable(comssn).ifPresent(s -> {
			s.setModt(DateUtil.formatDateTime(new Date()));
			comssnService.updateById(s);
		});
		return RespMsg.ok(comssn);
	}

	@PostMapping("removeById")
	@ApiOperation("删除单个委办局数据")
	@ApiImplicitParam(name = "cmnid", required = true, value = "委办局ID")
	public RespMsg<Void> delete(Integer cmnid) {
		Optional.ofNullable(cmnid).ifPresent(s -> comssnService.remove(Condition.<Comssn>create().eq(Comssn.CMNID, s)));
		return RespMsg.ok();
	}

}
