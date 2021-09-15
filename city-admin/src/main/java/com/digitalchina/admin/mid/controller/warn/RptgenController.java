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
import com.digitalchina.admin.mid.entity.Rptgen;
import com.digitalchina.admin.mid.service.RptgenService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 生成的报告前端控制器
 * 
 * @author lzy
 * @since 2019/8/28
 */
@RestController
@RequestMapping("/admin/rptgen")
@Authorize
@Api(tags = "后台管理系统生成的报告")
public class RptgenController {

	@Autowired
	private RptgenService rptgenService;

	@PostMapping("list")
	@ApiOperation("获取生成的报告数据（列表）")
	@ApiImplicitParam(name = "rgnm", value = "报告名称")
	public RespMsg<List<Rptgen>> list(String rgnm) {
		QueryWrapper<Rptgen> wrapper = new QueryWrapper<Rptgen>();
		Optional.ofNullable(rgnm).filter(ObjectUtil::isNotEmpty).ifPresent(s -> wrapper.like(Rptgen.RGNM, s));
		wrapper.orderByDesc(Rptgen.CRDT);
		List<Rptgen> rptgenList = rptgenService.list(wrapper);
		return RespMsg.ok(rptgenList);
	}

	@PostMapping("get")
	@ApiOperation("获取生成的报告数据（单个）")
	@ApiImplicitParam(name = "rgid", required = true, value = "报告ID")
	public RespMsg<Rptgen> get(Integer rgid) {
		Rptgen rptgen = rptgenService.getOne(Condition.<Rptgen>create().eq(Rptgen.RGID, rgid));
		return RespMsg.ok(rptgen);
	}

	@PostMapping("save")
	@ApiOperation("新增生成的报告数据")
	@Transactional
	public RespMsg<Rptgen> saveOne(Rptgen rptgen) {
		Optional.ofNullable(rptgen).ifPresent(s -> rptgenService.save(s));
		return RespMsg.ok(rptgen);
	}

	@PostMapping("update")
	@ApiOperation("修改单个生成的报告数据")
	@Transactional
	public RespMsg<Rptgen> updateOne(Rptgen rptgen) {
		Optional.ofNullable(rptgen).ifPresent(s -> rptgenService.updateById(s));
		return RespMsg.ok(rptgen);
	}

	@PostMapping("removeById")
	@ApiOperation("删除单个生成的报告数据")
	@ApiImplicitParam(name = "rgid", required = true, value = "报告ID")
	public RespMsg<Void> delete(Integer rgid) {
		Optional.ofNullable(rgid).ifPresent(s -> rptgenService.remove(Condition.<Rptgen>create().eq(Rptgen.RGID, s)));
		return RespMsg.ok();
	}
}
