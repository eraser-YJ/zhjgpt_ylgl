package com.digitalchina.event.mid.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.AdmdivDto;
import com.digitalchina.event.mid.entity.Admdiv;
import com.digitalchina.event.mid.entity.Busievent;
import com.digitalchina.event.mid.service.AdmdivService;
import com.digitalchina.event.mid.service.BusieventService;
import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.constant.TransConstant;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "行政区划管理")
@RestController
@RequestMapping("/admdiv")
public class AdmdivController {

	@Autowired
	private AdmdivService admdivService;
	@Autowired
	private BusieventService busieventService;

	@Authorize
	@GetMapping("list")
	@ApiOperation("获取行政区划数据（列表）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "adnm", value = "区划名称"),
			@ApiImplicitParam(name = "adflnm", value = "区划全称"), @ApiImplicitParam(name = "adlev", value = "行政区级别"),
			@ApiImplicitParam(name = "adpid", value = "父级ID"),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer") })
	public RespMsg<IPage<Admdiv>> list(String adnm, String adflnm, Integer adlev, Integer adpid,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current) {

		QueryWrapper<Admdiv> wrapper = Condition.<Admdiv>create();
		wrapper.like(ObjectUtil.isNotEmpty(adnm), Admdiv.ADNM, adnm);
		wrapper.like(ObjectUtil.isNotEmpty(adflnm), Admdiv.ADFLNM, adflnm);
		wrapper.eq(ObjectUtil.isNotEmpty(adlev), Admdiv.ADLEV, adlev);
		wrapper.eq(ObjectUtil.isNotEmpty(adpid), Admdiv.ADPID, adpid);
		wrapper.orderByDesc(Admdiv.ADID);
		IPage<Admdiv> admdivs = admdivService.page(new Page<>(current, size), wrapper);
		return RespMsg.ok(admdivs);
	}

	@Authorize
	@GetMapping("listall")
	@ApiOperation("获取行政区划数据（不分页）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "adnm", value = "区划名称"),
			@ApiImplicitParam(name = "adflnm", value = "区划全称"), @ApiImplicitParam(name = "adlev", value = "行政区级别"),
			@ApiImplicitParam(name = "adpid", value = "父级ID") })
	public RespMsg<List<Admdiv>> listall(String adnm, String adflnm, Integer adlev, Integer adpid) {

		QueryWrapper<Admdiv> wrapper = Condition.<Admdiv>create();
		wrapper.like(ObjectUtil.isNotEmpty(adnm), Admdiv.ADNM, adnm);
		wrapper.like(ObjectUtil.isNotEmpty(adflnm), Admdiv.ADFLNM, adflnm);
		wrapper.eq(ObjectUtil.isNotEmpty(adlev), Admdiv.ADLEV, adlev);
		wrapper.eq(ObjectUtil.isNotEmpty(adpid), Admdiv.ADPID, adpid);
		wrapper.orderByDesc(Admdiv.ADID);
		List<Admdiv> admdivs = admdivService.list(wrapper);
		return RespMsg.ok(admdivs);
	}

	@Authorize
	@GetMapping("get")
	@ApiOperation("获取行政区划数据（单个）")
	@ApiImplicitParam(name = "adid", required = true, value = "行政区划ID")
	public RespMsg<Admdiv> get(Integer adid) {
		Admdiv admdiv = admdivService.getOne(Condition.<Admdiv>create().eq(Admdiv.ADID, adid));
		return RespMsg.ok(admdiv);
	}

	@Authorize(SecurityConstant.FORBIDDEN)
	@PostMapping("save")
	@ApiOperation("新增行政区划数据")
	@Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER)
	public RespMsg<Admdiv> saveOne(@RequestBody Admdiv admdiv) {

		admdiv.setAdupnms(
				(admdiv.getAdupnms() == null || admdiv.getAdupnms().length == 0) ? null : admdiv.getAdupnms());
		Optional.ofNullable(admdiv).ifPresent(s -> admdivService.save(s));
		return RespMsg.ok(admdiv);
	}

	@Authorize(SecurityConstant.FORBIDDEN)
	@PostMapping("update")
	@ApiOperation("修改单个行政区划数据")
	@Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER)
	public RespMsg<Admdiv> updateOne(@RequestBody Admdiv admdiv) {
		Optional.ofNullable(admdiv).ifPresent(s -> {
			s.setModt(new Date());
			admdivService.updateById(s);
		});
		return RespMsg.ok(admdiv);
	}

	@Authorize(SecurityConstant.FORBIDDEN)
	@GetMapping("removeById")
	@ApiOperation("删除单个行政区划数据")
	@ApiImplicitParam(name = "adid", required = true, value = "行政区划ID")
	public RespMsg<Void> delete(Integer adid) {
		LambdaQueryWrapper<Busievent> queryWrapper = Condition.<Busievent>create().lambda().eq(Busievent::getAdid,
				adid);
		if (busieventService.count(queryWrapper) > 0) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "该行政区划数据已被使用，不可删除！");
		}
		Optional.ofNullable(adid).ifPresent(s -> admdivService.remove(Condition.<Admdiv>create().eq(Admdiv.ADID, s)));
		return RespMsg.ok();
	}

	/*
	 * 
	 * 
	 * 统一管理行政
	 * 
	 * 
	 * 
	 */
	@ApiOperation(value = "展示区域树")
	@GetMapping("all")
	public RespMsg<List<AdmdivDto>> findAll() {
		List<Admdiv> list = admdivService.list(Condition.<Admdiv>create().orderByAsc(Admdiv.ADID));
		return RespMsg
				.ok(AdmdivDto.makeTree(list.stream().map(item -> new AdmdivDto(item)).collect(Collectors.toList())));
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
		return RespMsg.ok();
	}

	@ApiOperation(value = "移除区域")
	@PostMapping("remove")
	@ApiImplicitParam(name = "adid", value = "区域ID", dataType = "Integer", required = false)
	public RespMsg<Void> remove(Integer adid) {
		admdivService.removeById(adid);
		return RespMsg.ok();
	}

	@ApiOperation(value = "添加子区域")
	@GetMapping("add")
	@ApiImplicitParams({ @ApiImplicitParam(name = "adpid", value = "上级区域ID", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "adnm", value = "区域名称", dataType = "String", required = false),
			@ApiImplicitParam(name = "adflnm", value = "区域全称", dataType = "String", required = false),
			@ApiImplicitParam(name = "adid", value = "同步主键id", dataType = "Integer", required = false)})
	public RespMsg<Void> add(Integer adpid, String adnm, String adflnm, Integer adid) {
		Admdiv adm = admdivService.getById(adpid);
		Integer[] admids = addArrary(adm.getAdupnms(), adm.getAdid());// 累加上级

		// 保存
		Admdiv newnt = new Admdiv();
		newnt.setAdpid(adpid);
		newnt.setAdupnms(admids);
		newnt.setAdnm(adnm);
		newnt.setAdflnm(adflnm);
		newnt.setAdlev(adm.getAdlev() + 1);// 层级+1
		newnt.setAdid(adid);
		admdivService.save(newnt);
		return RespMsg.ok();
	}

	@ApiOperation(value = "添加一级区域")
	@GetMapping("addfirst")
	@ApiImplicitParams({ @ApiImplicitParam(name = "adnm", value = "区域名称", dataType = "String", required = false),
			@ApiImplicitParam(name = "adflnm", value = "区域全称", dataType = "String", required = false),
			@ApiImplicitParam(name = "adid", value = "同步主键id", dataType = "Integer", required = false)})
	public RespMsg<Void> add(String adnm, String adflnm, Integer adid) {
		// 保存
		Admdiv newnt = new Admdiv();
		newnt.setAdnm(adnm);
		newnt.setAdflnm(adflnm);
		newnt.setAdlev(1);
		newnt.setAdid(adid);
		admdivService.save(newnt);
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
