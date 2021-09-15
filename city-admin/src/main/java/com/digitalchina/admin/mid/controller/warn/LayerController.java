package com.digitalchina.admin.mid.controller.warn;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.Comssn;
import com.digitalchina.admin.mid.entity.Layer;
import com.digitalchina.admin.mid.service.ComssnService;
import com.digitalchina.admin.mid.service.LayerService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.enums.StateEnum;
import com.digitalchina.modules.entity.SysDept;
import com.digitalchina.modules.service.SysDeptService;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "图层管理")
@RestController
@RequestMapping("/admin/layer")
public class LayerController {

	@Autowired
	private LayerService layerService;

	@Autowired
	private SysDeptService sysDeptService;

	@Autowired
	private ComssnService comssnService;

	@PostMapping("list")
	@ApiOperation("获取图层数据（列表）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "lynm", value = "图层名称（描述"),
			@ApiImplicitParam(name = "dpid", value = "所属部门"),
			@ApiImplicitParam(name = "srctp", value = "业务分类(1基础管理、2交通出行、3公共安全、4生态环境、5民生服务)"),
			@ApiImplicitParam(name = "prop", value = "属性(1静态资源,11点,12线,13面，2.动态资源,21传感器)"),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer") })
	public RespMsg<IPage<Layer>> list(String lynm, Integer dpid, Integer srctp, Integer prop,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current) {
		QueryWrapper<Layer> wrapper = Condition.<Layer>create();
		wrapper.like(ObjectUtil.isNotEmpty(lynm), Layer.LYNM, lynm).or().like(ObjectUtil.isNotEmpty(lynm), Layer.REMARK,
				lynm);
		wrapper.eq(ObjectUtil.isNotEmpty(dpid), Layer.DPID, dpid);
		wrapper.eq(ObjectUtil.isNotEmpty(srctp), Layer.SRCTP, srctp);
		wrapper.eq(ObjectUtil.isNotEmpty(prop), Layer.PROP, prop);
		wrapper.orderByDesc(Layer.MODT);
		IPage<Layer> layers = layerService.page(new Page<>(current, size), wrapper);
		layers.getRecords().forEach(d -> {
			Comssn dp = comssnService.getById(d.getCmnid());
			d.setDpnm(dp == null ? "" : dp.getCmnnm());
		});
		return RespMsg.ok(layers);
	}

	@PostMapping("get")
	@ApiOperation("获取图层数据（单个）")
	@ApiImplicitParam(name = "lyid", required = true, value = "图层ID")
	public RespMsg<Layer> get(Integer lyid) {
		Layer layer = layerService.getOne(Condition.<Layer>create().eq(Layer.LYID, lyid));
		return RespMsg.ok(layer);
	}

	@PostMapping("save")
	@ApiOperation(value = "新增图层数据", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public RespMsg<Layer> saveOne(@RequestBody Layer layer) {
		Optional.ofNullable(layer).ifPresent(s -> {
			s.setCrdt(new Date());
			s.setModt(new Date());
			layerService.save(s);
		});
		return RespMsg.ok(layer);
	}

	@PostMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("修改单个图层数据")
	@Transactional
	public RespMsg<Layer> updateOne(@RequestBody Layer layer) {
		Optional.ofNullable(layer).ifPresent(s -> {
			s.setModt(new Date());
			if (null != s.getDpids() && s.getDpids().length > 0) {
				Integer dpid = s.getDpids()[s.getDpids().length - 1];
				SysDept dept = sysDeptService.getById(dpid);
				s.setDpid(dpid);
				s.setDpnm(dept == null ? "" : dept.getBdnm());
			}
			layerService.updateById(s);
		});
		return RespMsg.ok(layer);
	}

	@PostMapping("removeById")
	@ApiOperation("删除单个图层数据")
	@ApiImplicitParam(name = "lyid", required = true, value = "图层ID")
	public RespMsg<Void> delete(Integer lyid) {
		Optional.ofNullable(lyid).ifPresent(s -> layerService.removeById(lyid));
		return RespMsg.ok();
	}

	@PostMapping("useable")
	@ApiOperation("启用")
	@ApiImplicitParam(name = "lyid", required = true, value = "图层ID")
	public RespMsg<Void> useable(Integer lyid) {
		Optional.ofNullable(lyid).ifPresent(s -> {
			Layer ent = new Layer();
			ent.setLyid(lyid);
			ent.setStat(StateEnum.ACTIVE.getCode());
			ent.setModt(new Date());
			layerService.updateById(ent);
		});
		return RespMsg.ok();
	}

	@PostMapping("disable")
	@ApiOperation("禁用")
	@ApiImplicitParam(name = "lyid", required = true, value = "图层ID")
	public RespMsg<Void> disable(Integer lyid) {
		Optional.ofNullable(lyid).ifPresent(s -> {
			Layer ent = new Layer();
			ent.setLyid(lyid);
			ent.setStat(StateEnum.DISABLE.getCode());
			ent.setModt(new Date());
			layerService.updateById(ent);
		});
		return RespMsg.ok();
	}
}
