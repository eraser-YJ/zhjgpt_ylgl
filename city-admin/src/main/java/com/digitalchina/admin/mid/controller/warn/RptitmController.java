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
import com.digitalchina.admin.mid.entity.Rptitm;
import com.digitalchina.admin.mid.service.RptitmService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 报告项前端控制器
 * 
 * @author lzy
 * @since 2019/8/28
 */
@RestController
@RequestMapping("/admin/rptitm")
@Authorize
@Api(tags = "后台管理系统报告项")
public class RptitmController {

	@Autowired
	private RptitmService rptitmService;

	@PostMapping("list")
	@ApiOperation("获取报告项数据（列表）")
	@ApiImplicitParam(name = "rtnm", value = "项名称")
	public RespMsg<List<Rptitm>> list(String rtnm) {
		QueryWrapper<Rptitm> wrapper = new QueryWrapper<Rptitm>();
		Optional.ofNullable(rtnm).filter(ObjectUtil::isNotEmpty).ifPresent(s -> wrapper.like(Rptitm.RTNM, s));
		wrapper.orderByDesc(Rptitm.CRDT);
		List<Rptitm> rptitmList = rptitmService.list(wrapper);
		return RespMsg.ok(rptitmList);
	}

	@PostMapping("get")
	@ApiOperation("获取报告项数据（单个）")
	@ApiImplicitParam(name = "rtid", required = true, value = "报告项ID")
	public RespMsg<Rptitm> get(Integer rtid) {
		Rptitm rptitm = rptitmService.getOne(Condition.<Rptitm>create().eq(Rptitm.RTID, rtid));
		return RespMsg.ok(rptitm);
	}

	@PostMapping("save")
	@ApiOperation("新增报告项数据")
	@Transactional
	public RespMsg<Rptitm> saveOne(Rptitm rptitm) {
		Optional.ofNullable(rptitm).ifPresent(s -> rptitmService.save(s));
		return RespMsg.ok(rptitm);
	}

	@PostMapping("update")
	@ApiOperation("修改单个报告项数据")
	@Transactional
	public RespMsg<Rptitm> updateOne(Rptitm rptitm) {
		Optional.ofNullable(rptitm).ifPresent(s -> rptitmService.updateById(s));
		return RespMsg.ok(rptitm);
	}

	@PostMapping("removeById")
	@ApiOperation("删除单个报告项数据")
	@ApiImplicitParam(name = "rtid", required = true, value = "报告模板ID")
	public RespMsg<Void> delete(Integer rtid) {
		Optional.ofNullable(rtid).ifPresent(s -> rptitmService.remove(Condition.<Rptitm>create().eq(Rptitm.RTID, s)));
		return RespMsg.ok();
	}
}
