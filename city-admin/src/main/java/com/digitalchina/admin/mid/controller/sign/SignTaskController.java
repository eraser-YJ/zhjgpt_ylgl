package com.digitalchina.admin.mid.controller.sign;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.SignTask;
import com.digitalchina.admin.mid.service.SignTaskService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.TransConstant;
import com.digitalchina.modules.constant.enums.DateRateEnum;
import com.digitalchina.modules.constant.enums.TaskStatusEnum;
import com.digitalchina.modules.constant.enums.TaskTypeEnum;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 体征运算任务 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@RestController
@RequestMapping("admin/signTask")
//@Authorize
@Api(tags = "体征汇总运算任务管理接口")
public class SignTaskController {

	@Autowired
	private SignTaskService signTaskService;

	@PostMapping(value = "list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation("获取体征汇总运算任务列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "tid", value = "树版本id", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "status", value = "状态 0：等待运算、1：运算中、 2：运算完成、9：运算异常", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "modtStart", value = "创建任务开始时间yyyy-MM-dd HH:mm:ss", dataType = "String", required = false),
			@ApiImplicitParam(name = "modtEnd", value = "创建任务结束时间yyyy-MM-dd HH:mm:ss", dataType = "String", required = false),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10", required = false),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer", required = false), })
	public RespMsg<IPage<SignTask>> list(@RequestParam(required = false) Integer tid,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) String modtStart,
			@RequestParam(required = false) String modtEnd, @RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "1") Integer current) {
		IPage<SignTask> page = new Page<>(current, size);
		return RespMsg.ok(signTaskService.queryPages(page, tid, status, modtStart, modtEnd));
	}

	@PostMapping("save")
	@ApiOperation("新增体征汇总运算任务")
	@Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
	public RespMsg saveOne(@Valid @RequestBody SignTask entity) {
		try {
			Date start = DateUtil.parse(entity.getStarttime());
			Date end = DateUtil.parse(entity.getEndtime());
			if (start.after(end)) {
				return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "开始时间必须不晚于结束时间！");
			}
			if (end.after(new Date())) {
				return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "结束时间不能超过当前日期！");
			}
		} catch (Exception e) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "请填写有效的时间！");
		}
		entity.setType(TaskTypeEnum.SUMMARY.getCode());
		entity.setRate(DateRateEnum.DAY.getCode());
		entity.setStatus(TaskStatusEnum.WAITING.getCode());
		entity.setModt(new Date());
		signTaskService.save(entity);
		return RespMsg.ok(entity);
	}

	@GetMapping("reStart")
	@ApiOperation("重新计算异常的汇总任务")
	@ApiImplicitParam(name = "id", value = "id主键", dataType = "Integer", required = true)
	public RespMsg<Void> reStart(Integer id) {
		SignTask signTask = signTaskService.getById(id);
		if (ObjectUtil.isEmpty(signTask)) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "记录不存在，请刷新列表！");
		}
		if (!signTask.getStatus().equals(TaskStatusEnum.ABNORMAL.getCode())) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "非异常任务不能重新计算！");
		}
		signTask.setStatus(TaskStatusEnum.WAITING.getCode());
		signTask.setModt(new Date());
		signTaskService.updateById(signTask);
		return RespMsg.ok();
	}

}
