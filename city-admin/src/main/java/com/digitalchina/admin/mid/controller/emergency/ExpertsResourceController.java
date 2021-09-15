package com.digitalchina.admin.mid.controller.emergency;


import java.util.Date;
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
import com.digitalchina.admin.mid.entity.Gis;
import com.digitalchina.admin.mid.entity.emergency.ExpertsResource;
import com.digitalchina.admin.mid.service.ExpertsResourceService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.utils.UserHelper;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 专家资料管理 前端控制器
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-06
 */
@Api(tags = "专家资料管理")
@RestController
@RequestMapping("/expertsResource")
public class ExpertsResourceController {
	
	@Autowired
	ExpertsResourceService expertsResourceService;
	
	/**
	 * 新建
	 * @param expertsResource 专家资料管理实体对象
	 * @return
	 */
	@ApiOperation("新建")
	@PostMapping("save")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> save(@RequestBody(required = true) ExpertsResource expertsResource){
			
		// 设置创建人id
		expertsResource.setCruid(UserHelper.getUserId());
		
		// 获取当前时间
		Date now = new Date();
		
		expertsResource.setCrdt(now);
		
		// 获取坐标xy字段的值
		Gis zb = expertsResource.getXy();
		
		// 1.插入数据库 先将坐标 xy字段设置为null
		expertsResource.setXy(null);
		Optional.ofNullable(expertsResource).ifPresent(s -> expertsResourceService.save(expertsResource));
		
		// 2.更新经纬度字段
		Optional.ofNullable(zb).ifPresent(
				s -> expertsResourceService.updateGis(expertsResource.getId(), s.getLongitude(), s.getLatitude()));
		
		return RespMsg.ok("新建成功");
		
	}
	
	/**
	 * 列表
	 * @param current 第几页,默认第1页
	 * @param size 每页条数,默认10条
	 * @param area 负责区域
	 * @return
	 */
	@ApiOperation("列表")
	@GetMapping("/page")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "current",value = "第几页,默认第1页",defaultValue = "1",dataType = "Integer",required = false),
		@ApiImplicitParam(name = "size",value = "每页条数,默认10条",defaultValue = "10",dataType = "Integer",required = false),
		@ApiImplicitParam(name = "area",value = "负责区域",dataType = "String",required = false)
	})
	public RespMsg<IPage<ExpertsResource>> pageList(@RequestParam(defaultValue = "1") Integer current,
			@RequestParam(defaultValue = "10") Integer size, String area){
		// 分页处理
		Page<ExpertsResource> page = new Page<>(current,size);
		// 条件过滤
		QueryWrapper<ExpertsResource> wrapper = new QueryWrapper<>();
		wrapper.like(ObjectUtil.isNotEmpty(area), ExpertsResource.AREA,area);
		wrapper.orderByDesc(ExpertsResource.CRDT);
		
		IPage<ExpertsResource> iPage = expertsResourceService.page(page, wrapper);
		
		return RespMsg.ok(iPage);
		
	}
	
	/**
	 * 修改
	 * @param expertsResource 专家资料管理 实体对象
	 * @return
	 */
	@ApiOperation("修改")
	@PostMapping("update")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> update(@RequestBody(required = true) ExpertsResource expertsResource){
		
		// 获取当前时间
		Date now = new Date();
		// 设置修改时间
		expertsResource.setModt(now);
		
		// 获取坐标xy字段的值
		Gis zb = expertsResource.getXy();
		
		// 1.更新数据库 先将坐标xy字段设置为null
		expertsResource.setXy(null);
		Optional.ofNullable(expertsResource).ifPresent(u -> expertsResourceService.updateById(expertsResource));
		
		// 2.更新经纬度字段 
		Optional.ofNullable(zb).ifPresent(
				s -> expertsResourceService.updateGis(expertsResource.getId(), s.getLongitude(), s.getLatitude()));
		
		return RespMsg.ok("修改成功");
	}
	
	/**
	 * 专家资料管理--删除
	 * @param id 主键id
	 * @return
	 */
	@ApiOperation(value = "删除")
	@GetMapping("/delete")
	@ApiImplicitParam(name = "id",value = "id",dataType = "Integer",required = true)
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> delete(@RequestParam(required = true) Integer id){
		
		expertsResourceService.removeById(id);
		
		return RespMsg.ok("删除成功");
		
		
	}
	

	
	

}
