package com.digitalchina.admin.mid.controller.event;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalchina.admin.mid.dto.AdmdivDto;
import com.digitalchina.admin.mid.entity.Admdiv;
import com.digitalchina.admin.mid.service.AdmdivService;
import com.digitalchina.admin.remoteservice.CityEventService;
import com.digitalchina.common.web.RespMsg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "事件系统行政区划管理")
@RestController
//@Authorize
@RequestMapping("admin/eventAdmdiv/")
@Deprecated
public class EventAdmdivController {

	@Autowired
	private CityEventService cityEventService;
	@Autowired
	private AdmdivService admdivService;

	/*
	 * 
	 * 
	 * 
	 * 统一管理行政区划
	 * 
	 * 
	 */
	@ApiOperation(value = "展示区域树")
	@GetMapping("all")
	public RespMsg<List<AdmdivDto>> findAll() {
		return cityEventService.findAdmdivTree();
	}

	@ApiOperation(value = "编辑区域")
	@PostMapping(value = "edit")
	@ApiImplicitParams({ @ApiImplicitParam(name = "adid", value = "区域ID", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "adnm", value = "区域名称", dataType = "String", required = false),
			@ApiImplicitParam(name = "adflnm", value = "区域全程", dataType = "String", required = false) })
	public RespMsg<Void> edit(Integer adid, String adnm, String adflnm) {
		Admdiv ent = new Admdiv();
		ent.setAdid(adid);
		ent.setAdnm(adnm);
		ent.setAdflnm(adflnm);
		admdivService.updateById(ent);
		cityEventService.admedit(adid, adnm, adflnm);
		return RespMsg.ok();
	}

	@ApiOperation(value = "移除区域")
	@PostMapping("remove")
	@ApiImplicitParam(name = "adid", value = "区域ID", dataType = "Integer", required = false)
	public RespMsg<Void> remove(Integer adid) {
		admdivService.removeById(adid);
		cityEventService.admremove(adid);
		return RespMsg.ok();
	}

	@ApiOperation(value = "添加子区域")
	@GetMapping("add")
	@ApiImplicitParams({ @ApiImplicitParam(name = "adpid", value = "上级区域ID", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "adnm", value = "区域名称", dataType = "String", required = false),
			@ApiImplicitParam(name = "adflnm", value = "区域全称", dataType = "String", required = false) })
	public RespMsg<Void> add(Integer adpid, String adnm, String adflnm) {
		Admdiv adm = admdivService.getById(adpid);
		Integer[] admids = addArrary(adm.getAdupnms(), adm.getAdid());// 累加上级

		// 保存
		Admdiv newnt = new Admdiv();
		newnt.setAdpid(adpid);
		newnt.setAdupnms(admids);
		newnt.setAdnm(adnm);
		newnt.setAdflnm(adflnm);
		newnt.setAdlev(adm.getAdlev() + 1);// 层级+1
		admdivService.save(newnt);

		cityEventService.admadd(adpid, adnm, adflnm, newnt.getAdid());
		return RespMsg.ok();
	}

	@ApiOperation(value = "添加一级区域")
	@GetMapping("addfirst")
	@ApiImplicitParams({ @ApiImplicitParam(name = "adnm", value = "区域名称", dataType = "String", required = false),
			@ApiImplicitParam(name = "adflnm", value = "区域全称", dataType = "String", required = false) })
	public RespMsg<Void> add(String adnm, String adflnm) {
		// 保存
		Admdiv newnt = new Admdiv();
		newnt.setAdnm(adnm);
		newnt.setAdflnm(adflnm);
		newnt.setAdlev(1);
		admdivService.save(newnt);
		cityEventService.admaddfirst(adnm, adflnm, newnt.getAdid());
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
