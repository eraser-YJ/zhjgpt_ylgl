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
import com.digitalchina.admin.mid.entity.Cpspv;
import com.digitalchina.admin.mid.service.CpspvService;
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
 * 组合属性值 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
@Api(tags = "组合属性值管理")
@Authorize
@RestController
@RequestMapping("/admin/cpspv")
public class CpspvController {
	@Autowired
	private CpspvService cpspvService;

	@PostMapping("list")
	@ApiOperation("获取组合属性值数据（列表）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cpvs", value = "组合属性值"),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer") })
	public RespMsg<IPage<Cpspv>> list(String cpvs, @RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "1") Integer current) {
		IPage<Cpspv> page = new Page<>(current, size);
		QueryWrapper<Cpspv> wrapper = new QueryWrapper<Cpspv>();
		wrapper.like(ObjectUtil.isNotEmpty(cpvs), Cpspv.CPVS, cpvs);
		IPage<Cpspv> cpspvs = cpspvService.page(page, wrapper);
		return RespMsg.ok(cpspvs);
	}

	@PostMapping("get")
	@ApiOperation("获取组合属性值数据（单个）")
	@ApiImplicitParam(name = "cpvid", required = true, value = "组合属性值ID")
	public RespMsg<Cpspv> get(Integer cpvid) {
		Cpspv cpspv = cpspvService.getOne(Condition.<Cpspv>create().eq(Cpspv.CPVID, cpvid));
		return RespMsg.ok(cpspv);
	}

	@PostMapping("save")
	@ApiOperation("新增组合属性值数据")
	@Transactional
	public RespMsg<Cpspv> saveOne(Cpspv cpspv) {
		Optional.ofNullable(cpspv).ifPresent(s -> cpspvService.save(s));
		return RespMsg.ok(cpspv);
	}

	@PostMapping("update")
	@ApiOperation("修改单个组合属性值数据")
	@Transactional
	public RespMsg<Cpspv> updateOne(Cpspv cpspv) {
		Optional.ofNullable(cpspv).ifPresent(s -> {
			s.setModt(DateUtil.formatDateTime(new Date()));
			cpspvService.updateById(s);
		});
		return RespMsg.ok(cpspv);
	}

	@PostMapping("removeById")
	@ApiOperation("删除单个组合属性值数据")
	@ApiImplicitParam(name = "cpvid", required = true, value = "组合属性清单值ID")
	public RespMsg<Void> delete(Integer cpvid) {
		Optional.ofNullable(cpvid).ifPresent(s -> cpspvService.remove(Condition.<Cpspv>create().eq(Cpspv.CPVID, s)));
		return RespMsg.ok();
	}
}
