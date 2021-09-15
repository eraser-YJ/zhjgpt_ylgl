package com.digitalchina.admin.mid.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.app.AppFeedback;
import com.digitalchina.admin.mid.service.AppFeedbackService;
import com.digitalchina.common.web.RespMsg;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  领导决策App管理后台-前端控制器
 * </p>
 * 
 * @author Bruce Li
 * @sice 2019/11/5
 */
@Api(tags = "领导决策App系统后台管理接口")
@RestController
@RequestMapping("admin/myapp")
public class MyAppController{
	
	@Autowired
	private AppFeedbackService appFeedbackService;
	
	/**
	 * 
	 * @param current 第几页,默认第一页
	 * @param size 每页条数,默认10条
	 * @return
	 */
	@GetMapping("look")
	@ApiOperation(value = "我的-意见反馈-查看")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer"),
		@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
		@ApiImplicitParam(name = "cont", value = "意见反馈关键字", dataType = "String", required = false)
	})
	public RespMsg<IPage<AppFeedback>> feedbackLook(@RequestParam(defaultValue = "1") Integer current, 
			@RequestParam(defaultValue = "10") Integer size,
			String cont){
		
		// 分页
		Page<AppFeedback> page = new Page<>(current,size);
		
		// 过滤条件
		QueryWrapper<AppFeedback> appFeedback = new QueryWrapper<>();
		
		appFeedback.like(ObjectUtil.isNotEmpty(cont), AppFeedback.CONTENT, cont);
		appFeedback.orderByDesc(AppFeedback.CRDT);
		
		IPage<AppFeedback> iPage = appFeedbackService.page(page, appFeedback);
		
		return RespMsg.ok(iPage);
		
	}
	
	
}