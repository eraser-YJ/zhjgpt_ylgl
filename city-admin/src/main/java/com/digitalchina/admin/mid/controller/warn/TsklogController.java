package com.digitalchina.admin.mid.controller.warn;

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
import com.digitalchina.admin.mid.entity.Tsklog;
import com.digitalchina.admin.mid.service.TsklogService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.enums.StateEnum;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 任务日志 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-09-04
 */
@RestController
@RequestMapping("admin/tsklog")
@Authorize
@Api(tags = "任务日志查看")
public class TsklogController {

	@Autowired
	private TsklogService tsklogService;

	@PostMapping(value = "list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation("任务日志列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tsktp", value = "日志类型：0预警，1报告", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "status", value = "状态(-1：未结束 0：成功1：失败)", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "autoab", value = "是否是自动任务", dataType = "Boolean", required = false),
			@ApiImplicitParam(name = "crdt", value = "开始时间yyyy-MM-dd HH:mm:ss", dataType = "String", required = false),
			@ApiImplicitParam(name = "endt", value = "结束时间yyyy-MM-dd HH:mm:ss", dataType = "String", required = false),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10", required = false),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer", required = false), })
	public RespMsg<IPage<Tsklog>> list(@RequestParam(required = false) Integer tsktp,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Boolean autoab,
			@RequestParam(required = false) String crdt, @RequestParam(required = false) String endt,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current) {
		IPage<Tsklog> page = new Page<>(current, size);
		@SuppressWarnings("unchecked")
		LambdaQueryWrapper<Tsklog> queryWrapper = Condition.<Tsklog>lambda()
				.eq(Tsklog::getState, StateEnum.NOT_DELETED.getCode())
				.eq(!ObjectUtil.isEmpty(tsktp), Tsklog::getTsktp, tsktp)
				.eq(!ObjectUtil.isEmpty(status), Tsklog::getStatus, status)
				.eq(!ObjectUtil.isEmpty(autoab), Tsklog::getAutoab, autoab)
				.ge(!ObjectUtil.isEmpty(crdt), Tsklog::getCrdt, DateUtil.parse(crdt))
				.le(!ObjectUtil.isEmpty(crdt), Tsklog::getEndt, DateUtil.parse(endt)).orderByDesc(Tsklog::getCrdt);
		return RespMsg.ok(tsklogService.page(page, queryWrapper));
	}

	@ApiOperation("任务日志删除")
	@PostMapping(value = "delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RespMsg<Void> delete(@RequestParam List<Integer> logids) {
		if (CollUtil.isEmpty(logids))
			return RespMsg.ok();

		List<Tsklog> ents = logids.stream().map(id -> {
			return Tsklog.builder().logid(id).state(StateEnum.DELETED.getCode()).build();
		}).collect(Collectors.toList());
		tsklogService.updateBatchById(ents);
		return RespMsg.ok();
	}
}
