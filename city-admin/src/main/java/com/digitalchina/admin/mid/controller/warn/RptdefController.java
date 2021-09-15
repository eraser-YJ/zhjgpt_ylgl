package com.digitalchina.admin.mid.controller.warn;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.digitalchina.admin.mid.entity.Rptdef;
import com.digitalchina.admin.mid.service.RptdefService;
import com.digitalchina.common.web.RespMsg;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 报告模板前端控制器
 * 
 * @author lzy
 * @since 2019/8/28
 */
@RestController
@RequestMapping("/admin/rptdef")
/* @Authorize */
@Api(tags = "后台管理系统报告模板")
public class RptdefController {

	@Autowired
	private RptdefService rptdefService;

	@PostMapping("list")
	@ApiOperation("获取报告模板数据（列表）")
	@ApiImplicitParam(name = "rdnm", value = "模板名称")
	public RespMsg<List<Rptdef>> list(String rdnm) {
		QueryWrapper<Rptdef> wrapper = new QueryWrapper<Rptdef>();
		Optional.ofNullable(rdnm).filter(ObjectUtil::isNotEmpty).ifPresent(s -> wrapper.like(Rptdef.RDNM, s));
		wrapper.orderByDesc(Rptdef.CRDT);
		List<Rptdef> rptdefList = rptdefService.list(wrapper);
		return RespMsg.ok(rptdefList);
	}

	@PostMapping("get")
	@ApiOperation("获取报告模板数据（单个）")
	@ApiImplicitParam(name = "rdid", required = true, value = "报告模板ID")
	public RespMsg<Rptdef> get(Integer rdid) {
		Rptdef rptdef = rptdefService.getOne(Condition.<Rptdef>create().eq(Rptdef.RDID, rdid));
		return RespMsg.ok(rptdef);
	}

	@PostMapping("save")
	@ApiOperation("新增报告模板数据")
	@Transactional
	public RespMsg<Rptdef> saveOne(Rptdef rptdef) {
		Optional.ofNullable(rptdef).ifPresent(s -> rptdefService.save(s));
		return RespMsg.ok(rptdef);
	}

	@PostMapping("update")
	@ApiOperation("修改单个报告模板数据")
	@Transactional
	public RespMsg<Rptdef> updateOne(Rptdef rptdef) {
		Optional.ofNullable(rptdef).ifPresent(s -> rptdefService.updateById(s));
		return RespMsg.ok(rptdef);
	}

	@PostMapping("removeById")
	@ApiOperation("删除单个报告模板数据")
	@ApiImplicitParam(name = "rdid", required = true, value = "报告模板ID")
	public RespMsg<Void> delete(Integer rdid) {
		Optional.ofNullable(rdid).ifPresent(s -> rptdefService.remove(Condition.<Rptdef>create().eq(Rptdef.RDID, s)));
		return RespMsg.ok();
	}
}
