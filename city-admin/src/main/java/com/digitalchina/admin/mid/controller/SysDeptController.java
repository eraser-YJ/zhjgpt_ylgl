package com.digitalchina.admin.mid.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalchina.admin.remoteservice.CityEventService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.dto.BedeptDto;
import com.digitalchina.modules.entity.SysDept;
import com.digitalchina.modules.service.SysDeptService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 统一部门 前端控制器
 * </p>
 *
 * @author Ryan
 * @since 2020-01-16
 */
//@Authorize
@RestController
@Api(tags = "统一部门")
@RequestMapping("/sysDept")
public class SysDeptController {

	@Autowired
	private SysDeptService service;
	@Autowired
	private CityEventService eventService;

	@ApiOperation(value = "展示部门树")
	@GetMapping("all")
	public RespMsg<List<BedeptDto>> findAll() {
		return RespMsg.ok(service.tree());
	}

	@ApiOperation(value = "编辑部门")
	@PostMapping(value = "edit")
	@ApiImplicitParams({ @ApiImplicitParam(name = "bedid", value = "部门ID", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "bdnm", value = "部门名称", dataType = "String", required = false) })
	public RespMsg<Void> edit(Integer bedid, String bdnm) {
		SysDept ent = new SysDept(bedid, bdnm);
		service.updateById(ent);
		eventService.edit(bedid, bdnm);// 同步事件系统
		return RespMsg.ok();
	}

	@ApiOperation(value = "移除部门")
	@PostMapping("remove")
	@ApiImplicitParam(name = "bedid", value = "部门ID", dataType = "Integer", required = false)
	public RespMsg<Void> remove(Integer bedid) {
		service.removeById(bedid);
		service.removeTreeById(bedid);// 移除下级所有部门
		eventService.remove(bedid);// 同步事件系统
		return RespMsg.ok();
	}

	@ApiOperation(value = "添加子部门")
	@GetMapping("add")
	@ApiImplicitParams({ @ApiImplicitParam(name = "bdpntid", value = "上级部门ID", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "bdnm", value = "部门名称", dataType = "String", required = false) })
	public RespMsg<Void> add(Integer bdpntid, String bdnm) {
		SysDept pnt = service.getById(bdpntid);
		Integer[] pntids = addArrary(pnt.getBdpntids(), pnt.getDpid());// 累加上级

		// 保存
		SysDept newnt = new SysDept();
		newnt.setBdpntid(bdpntid);
		newnt.setBdpntids(pntids);
		newnt.setBdnm(bdnm);
		newnt.setBdtype(pnt.getBdtype() + 1);// 层级+1
		service.save(newnt);
		eventService.add(bdpntid, bdnm);// 同步事件系统
		return RespMsg.ok();

	}

	@ApiOperation(value = "添加一级部门")
	@GetMapping("addfirst")
	@ApiImplicitParam(name = "bdnm", value = "部门名称", dataType = "String", required = false)
	public RespMsg<Void> addfirst(String bdnm) {
		// 保存
		SysDept newnt = new SysDept();
		newnt.setBdnm(bdnm);
		newnt.setBdtype(1);// 1级
		service.save(newnt);
		eventService.addfirst(bdnm);// 同步事件系统
		return RespMsg.ok();

	}

	private Integer[] addArrary(Integer[] bdpntids, Integer dpid) {
		if (null == bdpntids || bdpntids.length < 1) {
			return new Integer[] { dpid };
		}
		Integer[] result = Arrays.copyOf(bdpntids, bdpntids.length + 1);
		result[result.length - 1] = dpid;
		return result;
	}
}
