package com.digitalchina.admin.mid.controller.warn;

import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.Wnrl;
import com.digitalchina.admin.mid.service.WnrlService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 警告规则 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-09-02
 */
@RestController
@RequestMapping("admin/wnrl")
@Authorize
@Api(tags = "警告规则管理")
public class WnrlController {

	@Autowired
	private WnrlService wnrlService;

	@PostMapping("saveorupdate")
	@ApiOperation(value = "创建或者更新警告规则")
	public RespMsg<Void> saveOrUpdate(@RequestBody @Valid Wnrl wnrl) {
		// 规则校验 TODO
		if (StringUtils.isNotEmpty(wnrl.getFtbnm())) {
			// 常规预警规则
		} else {
			// 物联网预警规则
		}
		Date now = new Date();
		if (null == wnrl.getRlid()) {
			wnrl.setCrdt(now);
		}
		wnrl.setModt(now);
		wnrlService.saveOrUpdate(wnrl);
		return RespMsg.ok();
	}

	@GetMapping("find")
	@ApiOperation(value = "查找单个警告规则")
	@ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
	public RespMsg<Wnrl> find(@RequestParam(required = true) Integer id) {
		return RespMsg.ok(wnrlService.getById(id));
	}

	@PostMapping("delete")
	@ApiOperation(value = "删除单个警告规则")
	@ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
	public RespMsg<Void> del(@RequestParam(required = true) Integer id) {
		wnrlService.removeById(id);
		return RespMsg.ok();
	}

	@PostMapping("deletes")
	@ApiOperation(value = "批量删除警告规则")
	@ApiImplicitParam(name = "ids", value = "主键ID数组", dataType = "Integer[]", required = true)
	public RespMsg<Void> dels(Integer[] ids) {
		wnrlService.removeByIds(CollUtil.newArrayList(ids));
		return RespMsg.ok();
	}

	@GetMapping("query")
	@ApiOperation(value = "分页查询规则")
	@ApiImplicitParams({ @ApiImplicitParam(name = "rlnm", value = "规则名称", dataType = "String", required = false),
			@ApiImplicitParam(name = "stat", value = "状态:-1禁用，0启用但不处理，1启用且处理", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "type", value = "规则类型：不传默认全部;1-常规预警规则，2-物联网预警规则 ", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
			@ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true) })
	public RespMsg<IPage<Wnrl>> query(@RequestParam(defaultValue = "10", required = true) Integer size,
			@RequestParam(defaultValue = "1", required = true) Integer current,
			@RequestParam(required = false) String rlnm, @RequestParam(required = false) Integer stat,
			@RequestParam(required = false) Integer type) {

		QueryWrapper<Wnrl> wrapper = Condition.<Wnrl>create().like(StringUtils.isNotEmpty(rlnm), Wnrl.RLNM, rlnm);
		// 根据表名判断规则类型, 表名为空是物联网预警规则, 非空是常规预警规则
		wrapper.isNotNull(null != type && type.intValue() == 1, Wnrl.FTBNM);
		wrapper.isNull(null != type && type.intValue() == 2, Wnrl.FTBNM);
		wrapper.eq(null != stat, Wnrl.STAT, stat).orderByDesc(Wnrl.MODT);
		return RespMsg.ok(wnrlService.page(new Page<>(current, size), wrapper));
	}

}
