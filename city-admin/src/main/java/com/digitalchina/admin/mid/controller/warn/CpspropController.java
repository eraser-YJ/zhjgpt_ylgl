package com.digitalchina.admin.mid.controller.warn;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.Cpsprop;
import com.digitalchina.admin.mid.service.CpspropService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

/**
 * <p>
 * 组合属性 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
@Api(tags = "组合属性管理")
@Authorize
@RestController
@RequestMapping("/admin/cpsprop")
public class CpspropController {

	@Autowired
	private CpspropService cpspropService;

	@PostMapping("list")
	@ApiOperation("获取组合属性数据（列表）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cpnm", value = "组合属性名称"),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer") })
	public RespMsg<IPage<Cpsprop>> list(String cpnm, @RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "1") Integer current) {
		IPage<Cpsprop> page = new Page<>(current, size);
		QueryWrapper<Cpsprop> wrapper = new QueryWrapper<Cpsprop>();
		wrapper.like(ObjectUtil.isNotEmpty(cpnm), Cpsprop.CPNM, cpnm);
		wrapper.orderByDesc(Cpsprop.CPID);
		IPage<Cpsprop> cpsprops = cpspropService.page(page, wrapper);
		return RespMsg.ok(cpsprops);
	}

	@PostMapping("get")
	@ApiOperation("获取组合属性数据（单个）")
	@ApiImplicitParam(name = "cpid", required = true, value = "组合属性ID")
	public RespMsg<Cpsprop> get(Integer cpid) {
		Cpsprop cpsprop = cpspropService.getOne(Condition.<Cpsprop>create().eq(Cpsprop.CPID, cpid));
		return RespMsg.ok(cpsprop);
	}

	@PostMapping("save")
	@ApiOperation("新增组合属性数据")
	@Transactional
	public RespMsg<Cpsprop> saveOne(Cpsprop Cpsprop) {
		Optional.ofNullable(Cpsprop).ifPresent(s -> cpspropService.save(s));
		return RespMsg.ok(Cpsprop);
	}

	@PostMapping("update")
	@ApiOperation("修改单个组合属性数据")
	@Transactional
	public RespMsg<Cpsprop> updateOne(Cpsprop Cpsprop) {
		Optional.ofNullable(Cpsprop).ifPresent(s -> {
			s.setModt(DateUtil.formatDateTime(new Date()));
			cpspropService.updateById(s);
		});
		return RespMsg.ok(Cpsprop);
	}

	@PostMapping("removeById")
	@ApiOperation("删除单个组合属性数据")
	@ApiImplicitParam(name = "cpid", required = true, value = "组合属性清单ID")
	public RespMsg<Void> delete(Integer cpid) {
		Optional.ofNullable(cpid)
				.ifPresent(s -> cpspropService.remove(Condition.<Cpsprop>create().eq(Cpsprop.CPID, s)));
		return RespMsg.ok();
	}

}
