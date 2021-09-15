package com.digitalchina.admin.mid.controller.emergency;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.emergency.EmErank;
import com.digitalchina.admin.mid.service.EmErankService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.utils.UserHelper;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 应急事件等级 前端控制器
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-16
 */
@Api(tags = "应急事件等级")
@RestController
@RequestMapping("/emerank")
public class EmErankController {
	
	@Autowired
	EmErankService emErankService;
	
	/**
	 * 选择事件等级
	 * @return
	 */
	@ApiOperation("选择事件等级")
	@GetMapping("/choose")
	public RespMsg<List<EmErank>> choose(){
		
		// 排序
		QueryWrapper<EmErank> wrapper = new QueryWrapper<>();
		wrapper.orderByAsc(EmErank.SORT);
		
		List<EmErank> list = emErankService.list(wrapper);
		
		return RespMsg.ok(list);
	}
	
	/**
	 * 新增
	 * @param emErank 事件等级实体对象
	 * @return
	 */
	@ApiOperation(value = "新增")
	@PostMapping("save")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> save(@RequestBody(required = true) EmErank emErank){
		
		// 设置创建人
		emErank.setCruid(UserHelper.getUserId());
		// 设置创建时间
		emErank.setCrdt(new Date());
		
		emErankService.save(emErank);
		
		return RespMsg.ok("新增成功");
	}
	
	/**
	 * 删除
	 * @param elevelfk 等级id
	 * @return
	 */
	@ApiOperation(value = "删除")
	@ApiImplicitParam(name = "elevelfk",value = "等级id",dataType = "Integer",required = true)
	@GetMapping("/delete")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> delete(@RequestParam(required = true) Integer elevelfk){
		
		emErankService.removeById(elevelfk);
		
		return RespMsg.ok("删除成功");
	}
	
	/**
	 * 列表
	 * @param current 第几页,默认第一页
	 * @param size 每页条数，默认10条
	 * @param elevelnm 等级名称
	 * @return
	 */
	@ApiOperation(value = "预案类型--查看列表")
	@GetMapping("page")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "current", value = "第几页，默认第一页", dataType = "Integer"),
		@ApiImplicitParam(name = "size", value = "每页条数",dataType = "Integer", defaultValue = "10"),
		@ApiImplicitParam(name = "elevelnm", value = "等级名称", dataType = "String", required = false)
	})
	public RespMsg<IPage<EmErank>> listpage(@RequestParam(defaultValue = "1") Integer current,
			@RequestParam(defaultValue = "10") Integer size, String elevelnm) {
		
		// 分页
		Page<EmErank> page = new Page<>(current,size);
		
		QueryWrapper<EmErank> wrapper = new QueryWrapper<>();
		wrapper.like(ObjectUtil.isNotEmpty(elevelnm), EmErank.ELEVELNM,elevelnm);
		wrapper.orderByAsc(EmErank.SORT);
		
		IPage<EmErank> iPage = emErankService.page(page, wrapper);
		
		return RespMsg.ok(iPage);
		
	}
	
	/**
	 * 查看详情
	 * @param elevelfk 等级ID
	 * @return
	 */
	@ApiOperation(value = "查看详情")
	@GetMapping("/detail")
	@ApiImplicitParam(name = "elevelfk",value = "等级id",dataType = "Integer",required = true)
	public RespMsg<EmErank> detail(@RequestParam(required = true) Integer elevelfk){
		
		EmErank emErank = emErankService.getById(elevelfk);
		
		return RespMsg.ok(emErank);
		
	}
	
	/**
	 * 修改
	 * @param emErank 事件等级实体对象
	 * @return
	 */
	@ApiOperation(value = "修改")
	@PostMapping("update")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> update(@RequestBody(required = true) EmErank emErank){
		
		// 设置修改人
		emErank.setMouid(UserHelper.getUserId());
		// 设置修改时间
		emErank.setModt(new Date());
		Optional.ofNullable(emErank).ifPresent(u -> emErankService.updateById(emErank));
		
		return RespMsg.ok("修改成功");
		
	}
	
	

}
