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
import com.digitalchina.admin.mid.entity.emergency.VehicleResource;
import com.digitalchina.admin.mid.service.VehicleResourceService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.utils.UserHelper;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 应急车辆管理 前端控制器
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-06
 */
@Api(tags = "应急车辆管理")
@RestController
@RequestMapping("/vehicleResource")
public class VehicleResourceController {
	
	@Autowired
	VehicleResourceService vehicleResourceService;
	
	/**
	 * 新建
	 * @param vehicleResource 应急车辆管理实体对象
	 * @return
	 */
	@ApiOperation("新建")
	@PostMapping("save")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> save(@RequestBody(required = true) VehicleResource vehicleResource){
	
		// 设置创建人id
		vehicleResource.setCruid(UserHelper.getUserId());
		
		// 获取当前时间
		Date now = new Date();
		
		vehicleResource.setCrdt(now);
		
		// 获取坐标xy字段的值
		Gis zb = vehicleResource.getXy();
		
		// 1.插入数据库 先将坐标xy字段的值设置为null
		vehicleResource.setXy(null);
		Optional.ofNullable(vehicleResource).ifPresent(s -> vehicleResourceService.save(vehicleResource));
		
		// 2.更新经纬度字段
		Optional.ofNullable(zb).ifPresent(
				s -> vehicleResourceService.updateGis(vehicleResource.getId(), s.getLongitude(), s.getLatitude()));
		
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
	public RespMsg<IPage<VehicleResource>> pageList(@RequestParam(defaultValue = "1") Integer current,
			@RequestParam(defaultValue = "10") Integer size, String area){
		// 分页处理
		Page<VehicleResource> page = new Page<>(current,size);
		// 条件过滤
		QueryWrapper<VehicleResource> wrapper = new QueryWrapper<>();
		wrapper.like(ObjectUtil.isNotEmpty(area), VehicleResource.AREA,area);
		wrapper.orderByDesc(VehicleResource.CRDT);
		
		IPage<VehicleResource> iPage = vehicleResourceService.page(page, wrapper);
		
		return RespMsg.ok(iPage);
		
	}
	
	/**
	 * 修改
	 * @param vehicleResource 应急车辆管理 实体对象
	 * @return
	 */
	@ApiOperation("修改")
	@PostMapping("update")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> update(@RequestBody(required = true) VehicleResource vehicleResource){
		
		// 获取当前时间
		Date now = new Date();
		// 设置修改时间
		vehicleResource.setModt(now);
		
		// 1.更新数据库
		Optional.ofNullable(vehicleResource).ifPresent(u -> vehicleResourceService.updateById(vehicleResource));
		// 2.更新经纬度字段 
		Optional.ofNullable(vehicleResource.getXy()).ifPresent(
				s -> vehicleResourceService.updateGis(vehicleResource.getId(), s.getLongitude(), s.getLatitude()));
		
		return RespMsg.ok("修改成功");
	}
	
	/**
	 * 应急车辆管理--删除
	 * @param id 主键id
	 * @return
	 */
	@ApiOperation(value = "删除")
	@GetMapping("/delete")
	@ApiImplicitParam(name = "id",value = "id",dataType = "Integer",required = true)
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> delete(@RequestParam(required = true) Integer id){
		
		vehicleResourceService.removeById(id);
		
		return RespMsg.ok("删除成功");
		
		
	}
	
	
}
