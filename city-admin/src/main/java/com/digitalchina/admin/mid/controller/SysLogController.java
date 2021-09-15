package com.digitalchina.admin.mid.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.enums.StateEnum;
import com.digitalchina.modules.entity.SysLog;
import com.digitalchina.modules.service.SysLogService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 登录日志表 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-08-29
 */
@RestController
@RequestMapping("/admin/sysLog")
//@Authorize
@Api(tags = "系统日志-登录日志接口")
public class SysLogController {

	@Autowired
	private SysLogService sysLogService;

	@PostMapping(value = "list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation("登录日志列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "syscode", value = "系统码", dataType = "String", required = false),
			@ApiImplicitParam(name = "result", value = "状态(操作结果)", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "username", value = "用户名", dataType = "String", required = false),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10", required = false),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer", required = false), })
	public RespMsg<IPage<SysLog>> list(@RequestParam(required = false) String syscode,
			@RequestParam(required = false) Integer result, @RequestParam(required = false) String username,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current) {
		IPage<SysLog> page = new Page<>(current, size);
		@SuppressWarnings("unchecked")
		LambdaQueryWrapper<SysLog> queryWrapper = Condition.<SysLog>lambda()
				.eq(SysLog::getState, StateEnum.NOT_DELETED.getCode())
				.like(!ObjectUtil.isEmpty(syscode), SysLog::getSyscode, syscode)
				.eq(!ObjectUtil.isEmpty(result), SysLog::getResult, result)
				.like(!ObjectUtil.isEmpty(username), SysLog::getUsername, username).orderByDesc(SysLog::getActime);
		return RespMsg.ok(sysLogService.page(page, queryWrapper));
	}

	@ApiOperation("登陆日志删除")
	@PostMapping(value = "delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RespMsg<Void> delete(@RequestParam List<Integer> logids) {
		if (CollUtil.isEmpty(logids))
			return RespMsg.ok();

		List<SysLog> ents = logids.stream().map(id -> {
			return SysLog.builder().id(id).state(StateEnum.DELETED.getCode()).build();
		}).collect(Collectors.toList());
		sysLogService.updateBatchById(ents);
		return RespMsg.ok();
	}
}
