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
import com.digitalchina.admin.mid.entity.emergency.PoliceResource;
import com.digitalchina.admin.mid.service.PoliceResourceService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.utils.UserHelper;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 警力资源管理 前端控制器
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-06
 */
@Api(tags = "警力资源管理")
@RestController
@RequestMapping("/policeResource")
public class PoliceResourceController {
	
	@Autowired
	PoliceResourceService policeResourceService;
	
	
	/**
	 * 警力资源管理--新建
	 * @param policeResource 警力资源实体类
	 * @return
	 */
	@ApiOperation(value = "新建")
	@PostMapping("save")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> save(@RequestBody(required = true) PoliceResource policeResource){
		
		// 设置创建人id
		policeResource.setCruid(UserHelper.getUserId());
		
		// 获取当前时间
		Date now = new Date();
		
		policeResource.setCrdt(now);
		
		//  获取坐标参数
		Gis zb = policeResource.getXy();

		// 第一步插入数据库  先将xy字段设置为null
		policeResource.setXy(null);
		Optional.ofNullable(policeResource).ifPresent(s -> policeResourceService.save(policeResource));
		// 第二步更新经纬度字段
		Optional.ofNullable(zb).ifPresent(
			s -> policeResourceService.updateGis(policeResource.getId(), s.getLongitude(), s.getLatitude()));
		
		return RespMsg.ok("新建成功");
		
	}
	
	/**
	 * 警力资源管理--列表
	 * @param current 第几页，默认第一页
	 * @param size 每页条数，默认10条
	 * @param area 所属区域
	 * @return
	 */
	@ApiOperation(value = "列表")
	@GetMapping("/page")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "current",value = "第几页,默认第一页",defaultValue = "1",dataType = "Integer",required = false),
		@ApiImplicitParam(name = "size",value = "每页条数,默认10条",defaultValue = "10",dataType = "Integer",required = false),
		@ApiImplicitParam(name = "area",value = "负责区域",dataType = "String",required = false)
	})
	public RespMsg<IPage<PoliceResource>> pageList(@RequestParam(defaultValue = "1") Integer current,
			@RequestParam(defaultValue = "10") Integer size, String area){
		
		// 分页处理
		Page<PoliceResource> page = new Page<>(current,size);
		// 条件过滤
		QueryWrapper<PoliceResource> wrapper = new QueryWrapper<>();
		wrapper.like(ObjectUtil.isNotEmpty(area),PoliceResource.AREA, area);
		wrapper.orderByDesc(PoliceResource.CRDT);
		
		IPage<PoliceResource> iPage = policeResourceService.page(page, wrapper);
		
		return RespMsg.ok(iPage);
		
	}
	
	/**
	 * 警力资源管理--修改
	 * @param policeResource 警力资源实体对象
	 * @return
	 */
	@ApiOperation(value = "修改")
	@PostMapping("update")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> update(@RequestBody(required = true) PoliceResource policeResource){
		
		// 获取当前时间
		Date now = new Date();
		// 设置修改时间
		policeResource.setModt(now);
		
		// 获取坐标xy字段的值
		Gis zb = policeResource.getXy();
		
		// 1.更新数据库 先将坐标xy字段设置为null
		policeResource.setXy(null);
		Optional.ofNullable(policeResource).ifPresent(u -> policeResourceService.updateById(policeResource));
		
		// 2.更新经纬度字段
		Optional.ofNullable(zb).ifPresent(
			s -> policeResourceService.updateGis(policeResource.getId(), s.getLongitude(), s.getLatitude()));
		
		return RespMsg.ok("更新成功");
		
	}
	
	/**
	 * 警力资源管理--删除
	 * @param id 主键id
	 * @return
	 */
	@ApiOperation(value = "删除")
	@GetMapping("/delete")
	@ApiImplicitParam(name = "id",value = "资源id",dataType = "Integer",required = true)
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> delete(@RequestParam(required = true) Integer id){
		
		policeResourceService.removeById(id);
		
		return RespMsg.ok("删除成功");
		
	}
	
	

}
