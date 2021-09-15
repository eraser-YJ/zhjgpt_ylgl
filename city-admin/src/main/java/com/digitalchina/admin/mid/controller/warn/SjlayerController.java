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
import com.digitalchina.admin.mid.entity.Sjlayer;
import com.digitalchina.admin.mid.service.SjlayerService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "主题图层管理")
@Authorize
@RestController
@RequestMapping("/admin/sjlayer")
public class SjlayerController {

	@Autowired
	private SjlayerService sjlayerService;

	@PostMapping("list")
	@ApiOperation("获取主题图层数据（列表）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "lyid", value = "图层id"),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer") })
	public RespMsg<IPage<Sjlayer>> list(Integer lyid, @RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "1") Integer current) {

		QueryWrapper<Sjlayer> wrapper = new QueryWrapper<Sjlayer>();
		wrapper.eq(ObjectUtil.isNotEmpty(lyid), Sjlayer.LYID, lyid);
		wrapper.orderByDesc(Sjlayer.LYID);
		IPage<Sjlayer> sjlayers = sjlayerService.page(new Page<>(current, size), wrapper);
		return RespMsg.ok(sjlayers);
	}

	@PostMapping("save")
	@ApiOperation("新增主题图层数据")
	@Transactional
	public RespMsg<Sjlayer> saveOne(Sjlayer sjlayer) {
		Optional.ofNullable(sjlayer).ifPresent(s -> sjlayerService.save(s));
		return RespMsg.ok(sjlayer);
	}

	@PostMapping("update")
	@ApiOperation("修改单个主题图层数据")
	@Transactional
	public RespMsg<Sjlayer> updateOne(Sjlayer sjlayer) {
		QueryWrapper<Sjlayer> wrapper = new QueryWrapper<Sjlayer>();
		wrapper.eq(Sjlayer.LYID, sjlayer.getLyid());
		wrapper.eq(Sjlayer.CPVID, sjlayer.getCpvid());
		Optional.ofNullable(sjlayer).ifPresent(s -> {
			s.setModt(DateUtil.formatDateTime(new Date()));
			sjlayerService.update(sjlayer, wrapper);
		});
		return RespMsg.ok(sjlayer);
	}

	@PostMapping("removeById")
	@ApiOperation("删除单个主题图层数据")
	@ApiImplicitParam(name = "lyid", required = true, value = "主题图层ID")
	public RespMsg<Void> delete(Integer lyid) {
		Optional.ofNullable(lyid)
				.ifPresent(s -> sjlayerService.remove(Condition.<Sjlayer>create().eq(Sjlayer.LYID, s)));
		return RespMsg.ok();
	}

}
