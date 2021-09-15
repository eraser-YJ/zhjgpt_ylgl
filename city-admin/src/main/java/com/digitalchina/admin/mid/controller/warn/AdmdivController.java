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
import com.digitalchina.admin.mid.entity.Admdiv;
import com.digitalchina.admin.mid.service.AdmdivService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "行政区划管理（弃置）")
@Authorize
@RestController
@RequestMapping("/admin/admdiv")
@Deprecated
public class AdmdivController {

	@Autowired
	private AdmdivService admdivService;

	@PostMapping("list")
	@ApiOperation("获取行政区划数据（列表）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "adnm", value = "区划名称"),
			@ApiImplicitParam(name = "adflnm", value = "区划全称"), @ApiImplicitParam(name = "adlev", value = "行政区级别"),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer") })
	public RespMsg<IPage<Admdiv>> list(String adnm, String adflnm, Integer adlev,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current) {

		QueryWrapper<Admdiv> wrapper = Condition.<Admdiv>create();
		wrapper.like(ObjectUtil.isNotEmpty(adnm), Admdiv.ADNM, adnm);
		wrapper.like(ObjectUtil.isNotEmpty(adflnm), Admdiv.ADFLNM, adflnm);
		wrapper.eq(ObjectUtil.isNotEmpty(adlev), Admdiv.ADLEV, adlev);
		wrapper.orderByDesc(Admdiv.ADID);
		IPage<Admdiv> admdivs = admdivService.page(new Page<>(current, size), wrapper);
		return RespMsg.ok(admdivs);
	}

	@PostMapping("get")
	@ApiOperation("获取行政区划数据（单个）")
	@ApiImplicitParam(name = "adid", required = true, value = "行政区划ID")
	public RespMsg<Admdiv> get(Integer adid) {

		Admdiv admdiv = admdivService.getOne(Condition.<Admdiv>create().eq(Admdiv.ADID, adid));
		return RespMsg.ok(admdiv);
	}

	@PostMapping("save")
	@ApiOperation("新增行政区划数据")
	@Transactional
	public RespMsg<Admdiv> saveOne(Admdiv admdiv) {

		admdiv.setAdupnms(
				(admdiv.getAdupnms() == null || admdiv.getAdupnms().length == 0) ? null : admdiv.getAdupnms());
		Optional.ofNullable(admdiv).ifPresent(s -> admdivService.save(s));
		return RespMsg.ok(admdiv);
	}

	@PostMapping("update")
	@ApiOperation("修改单个行政区划数据")
	@Transactional
	public RespMsg<Admdiv> updateOne(Admdiv admdiv) {
		Optional.ofNullable(admdiv).ifPresent(s -> {
			s.setModt(DateUtil.formatDateTime(new Date()));
			admdivService.updateById(s);
		});
		return RespMsg.ok(admdiv);
	}

	@PostMapping("removeById")
	@ApiOperation("删除单个行政区划数据")
	@ApiImplicitParam(name = "adid", required = true, value = "行政区划ID")
	public RespMsg<Void> delete(Integer adid) {
		Optional.ofNullable(adid).ifPresent(s -> admdivService.remove(Condition.<Admdiv>create().eq(Admdiv.ADID, s)));
		return RespMsg.ok();
	}

}
