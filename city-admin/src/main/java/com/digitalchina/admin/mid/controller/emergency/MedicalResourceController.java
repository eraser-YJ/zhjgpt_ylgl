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
import com.digitalchina.admin.mid.entity.emergency.MedicalResource;
import com.digitalchina.admin.mid.service.MedicalResourceService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.utils.UserHelper;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 医疗卫生资源管理 前端控制器
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-06
 */
@Api(tags = "医疗卫生资源管理")
@RestController
@RequestMapping("/medicalResource")
public class MedicalResourceController {
	
	@Autowired
	MedicalResourceService medicalResourceService;
	
	
	/**
	 * 新建
	 * @param medicalResource 医疗卫生资源管理实体类
	 * @return
	 */
	@ApiOperation("新建")
	@PostMapping("save")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> add(@RequestBody MedicalResource medicalResource){
		
		// 设置创建人id
		medicalResource.setCruid(UserHelper.getUserId());
		// 获取当前时间
		Date now = new Date();
		medicalResource.setCrdt(now);
		medicalResource.setModt(now);
		
		// 获取坐标xy字段的值
		Gis zb = medicalResource.getXy();
		// 1.插入数据库 先将坐标xy字段设置为null
		medicalResource.setXy(null);
		Optional.ofNullable(medicalResource).ifPresent(save->medicalResourceService.save(medicalResource));
		
		// 2.更新经纬度字段
		Optional.ofNullable(zb).ifPresent(
				s -> medicalResourceService.updateGis(medicalResource.getId(), s.getLongitude(), s.getLatitude()));
		
		return RespMsg.ok("新建成功");
		
	}
	
	/**
	 * 列表
	 * @param current 第几页，默认第一页
	 * @param size  每页显示条数，默认10条
	 * @param area 负责区域
	 * @return
	 */
	@ApiOperation("列表")
	@GetMapping("page")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "current",value = "第几页,默认第一页",defaultValue = "1",dataType = "Integer",required = false),
		@ApiImplicitParam(name = "size",value = "每页显示条数",defaultValue = "10",dataType = "Integer",required = false),
		@ApiImplicitParam(name = "area",value = "负责区域",dataType = "String",required = false)
	})
	public RespMsg<IPage<MedicalResource>> pageList(@RequestParam(defaultValue = "1") Integer current,
			@RequestParam(defaultValue = "10") Integer size, String area){
		// 分页处理
		Page<MedicalResource> page = new Page<>(current,size);
		// 条件过滤
		QueryWrapper<MedicalResource> wrapper = new QueryWrapper<>();
		wrapper.like(ObjectUtil.isNotEmpty(area), MedicalResource.AREA,area);
		wrapper.orderByDesc(MedicalResource.CRDT);
		
		IPage<MedicalResource> iPage = medicalResourceService.page(page, wrapper);
		
		return RespMsg.ok(iPage);
		
	}
	
	/**
	 * 修改
	 * @param medicalResource 医疗资源管理实体类对象
	 * @return
	 */
	@ApiOperation(value = "修改")
	@PostMapping("update")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> update(@RequestBody(required = true) MedicalResource medicalResource){
		
		// 获取当前时间
		Date now = new Date();
		// 设置修改时间
		medicalResource.setModt(now);
		
		// 获取坐标xy字段的值
		Gis zb = medicalResource.getXy();
		
		// 1.更新数据库 先将坐标xy字段设置为null
		medicalResource.setXy(null);
		Optional.ofNullable(medicalResource).ifPresent(u -> medicalResourceService.updateById(medicalResource));
		// 2.更新经纬度字段
		Optional.ofNullable(zb).ifPresent(
				s -> medicalResourceService.updateGis(medicalResource.getId(), s.getLongitude(), s.getLatitude()));
		
		return RespMsg.ok("更新成功");
		
	}
	
	/**
	 * 医疗资源管理--删除
	 * @param id 主键id
	 * @return
	 */
	@ApiOperation(value = "删除")
	@GetMapping("/delete")
	@ApiImplicitParam(name = "id",value = "id",dataType = "Integer",required = true)
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> delete(@RequestParam(required = true) Integer id){
		
		medicalResourceService.removeById(id);
		
		return RespMsg.ok("删除成功");
		
		
	}
	

}
