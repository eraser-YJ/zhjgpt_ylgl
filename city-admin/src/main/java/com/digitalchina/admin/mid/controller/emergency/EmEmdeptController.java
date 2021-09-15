package com.digitalchina.admin.mid.controller.emergency;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.emergency.EmEmdept;
import com.digitalchina.admin.mid.entity.event.Bedept;
import com.digitalchina.admin.mid.service.EmEmdeptService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.utils.UserHelper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 应急部门 前端控制器
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-18
 */
@Api(tags = "应急部门管理(弃置)")
@RestController
@RequestMapping("/emEmdept")
@Deprecated
public class EmEmdeptController {
	
	@Autowired
	EmEmdeptService emEmdeptService;
	
	/**
	 * 部门名称--选择部门
	 * @return
	 */
	@ApiOperation(value = "选择部门")
	@GetMapping("/choose")
	public RespMsg<List<EmEmdept>> choose(){
		
		List<EmEmdept> list = emEmdeptService.choose();
		
		return RespMsg.ok(list);
				
	}
	
	/**
	 * 新增
	 * @param emEtype
	 * @return
	 */
	@ApiOperation(value = "新增")
	@PostMapping("save")
	public RespMsg<String> save(@RequestBody(required = true) EmEmdept emEmdept){
		
		// 获取上级类型id
		Integer bdpntid = emEmdept.getBdpntid();
		
		// 判断是否是一级部门
		if(bdpntid==0){
			emEmdept.setBdpntid(bdpntid);
			// 设置创建人
			emEmdept.setCruid(UserHelper.getUserId());
			// 设置创建时间
			emEmdept.setCrdt(new Date());
			
			emEmdeptService.save(emEmdept);
			
			return RespMsg.ok("新增成功");
		}
		
		// 获取上级类型id的清单数组
		QueryWrapper<EmEmdept> wrapper = new QueryWrapper<>();
		wrapper.eq(EmEmdept.DPID, bdpntid);
		EmEmdept one = emEmdeptService.getOne(wrapper);
		
		// 判断处理是否还存在上级的上级
		if(null != one.getBdpntids()){
			Integer[] bdIntegers = one.getBdpntids();
			// 先将数组转为list
			List<Integer> asList= new ArrayList<>(Arrays.asList(bdIntegers));
			asList.add(bdpntid);
			// 再将list转为数组
			int size = asList.size();
			Integer[] array = asList.toArray(new Integer[size]);
			emEmdept.setBdpntids(array);
		}else{
			emEmdept.setBdpntids(new Integer[]{bdpntid});
		}
		
		// 设置创建人
		emEmdept.setCruid(UserHelper.getUserId());
		// 设置创建时间
		emEmdept.setCrdt(new Date());
		
		emEmdeptService.save(emEmdept);
		
		return RespMsg.ok("新增成功");
		
	}
	/**
	 * 删除
	 * @param etypefk 类型id
	 * @return
	 */
	@ApiOperation(value = "删除",notes = "一起删除子节点")
	@GetMapping("/delete")
	public RespMsg<String> delete(@RequestParam(required = true) Integer dpid){
		EmEmdept emEmdept = emEmdeptService.getById(dpid);
		if(ObjectUtil.isEmpty(emEmdept)){
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "该部门信息不存在，请刷新列表！");
		}
		emEmdeptService.removeById(dpid);
		//删除子节点
		Integer[] pids = emEmdept.getPids(emEmdept);
		String column = Bedept.BDPNTIDS +"[:" + pids.length +"]";
		emEmdeptService.remove(Condition.<EmEmdept>create().eq(column,pids));
		return RespMsg.ok("删除成功");
	}
	
	
	/**
	 * 列表
	 * @param current 第几页,默认第一页
	 * @param size 每页条数，默认10条
	 * @param elevelnm 部门名称
	 * @return
	 */
	@ApiOperation(value = "部门名称--查看列表")
	@GetMapping("page")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "current", value = "第几页，默认第一页", dataType = "Integer"),
		@ApiImplicitParam(name = "size", value = "每页条数",dataType = "Integer", defaultValue = "10"),
		@ApiImplicitParam(name = "bdnm", value = "部门名称", dataType = "String", required = false)
	})
	public RespMsg<IPage<EmEmdept>> listpage(@RequestParam(defaultValue = "1") Integer current,
			@RequestParam(defaultValue = "10") Integer size, String bdnm) {
		
		// 分页
		Page<EmEmdept> page = new Page<>(current,size);
		
		QueryWrapper<EmEmdept> wrapper = new QueryWrapper<>();
		wrapper.like(ObjectUtil.isNotEmpty(bdnm), EmEmdept.BDNM,bdnm);
		
		IPage<EmEmdept> iPage = emEmdeptService.page(page, wrapper);
		
		return RespMsg.ok(iPage);
		
	}
	
	/**
	 * 修改
	 * @param emEmdept 部门实体对象
	 * @return
	 */
	@ApiOperation(value = "修改")
	@PostMapping("update")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> update(@RequestBody(required = true) EmEmdept emEmdept){
		
		// 获取上级类型id
		Integer bdpntid = emEmdept.getBdpntid();
		
		// 判断是否是一级部门
		if(bdpntid == 0){
			
			// 设置修改人
			emEmdept.setMouid(UserHelper.getUserId());
			// 设置修改时间
			emEmdept.setModt(new Date());
			Optional.ofNullable(emEmdept).ifPresent(u -> emEmdeptService.updateById(emEmdept));
			
			return RespMsg.ok("修改成功");
			
		}
		
		// 获取上级类型id的清单数组
		QueryWrapper<EmEmdept> wrapper = new QueryWrapper<>();
		wrapper.eq(EmEmdept.DPID, bdpntid);
		EmEmdept one = emEmdeptService.getOne(wrapper);
		
		// 判断处理是否还存在上级的上级
		if(null != one.getBdpntids()){
			Integer[] bdIntegers = one.getBdpntids();
			// 先将数组转为list
			List<Integer> asList= new ArrayList<>(Arrays.asList(bdIntegers));
			asList.add(bdpntid);
			// 再将list转为数组
			int size = asList.size();
			Integer[] array = asList.toArray(new Integer[size]);
			emEmdept.setBdpntids(array);
		}else{
			emEmdept.setBdpntids(new Integer[]{bdpntid});
		}
		
		// 设置修改人
		emEmdept.setMouid(UserHelper.getUserId());
		// 设置修改时间
		emEmdept.setModt(new Date());
		Optional.ofNullable(emEmdept).ifPresent(u -> emEmdeptService.updateById(emEmdept));
		
		return RespMsg.ok("修改成功");
		
	}
	

	
	

}
