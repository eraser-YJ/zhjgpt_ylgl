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
import com.digitalchina.admin.mid.entity.emergency.EmEtype;
import com.digitalchina.admin.mid.entity.event.Bedept;
import com.digitalchina.admin.mid.service.EmEtypeService;
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
 * 应急事件类型 前端控制器
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-16
 */
@Api(tags = "应急事件类型")
@RestController
@RequestMapping("/emetype")
public class EmEtypeController {
	
	@Autowired
	EmEtypeService emEtypeService;
	
	
	/**
	 * 事件类型--选择事件类型
	 * @return
	 */
	@ApiOperation(value = "选择事件类型")
	@GetMapping("/choose")
	public RespMsg<List<EmEtype>> choose(){
		
		List<EmEtype> list = emEtypeService.choose();
		
		return RespMsg.ok(list);
				
	}
	
	/**
	 * 新增
	 * @param emEtype
	 * @return
	 */
	@ApiOperation(value = "新增")
	@PostMapping("save")
	public RespMsg<String> save(@RequestBody(required = true) EmEtype emEtype){
		
		// 获取上级类型id
		Integer etypefk = emEtype.getEtypetid();
		
		// 判断是否是一级节点
		if(etypefk == 0){
			emEtype.setEtypetid(etypefk);

			// 设置创建人
			emEtype.setCruid(UserHelper.getUserId());
			// 设置创建时间
			emEtype.setCrdt(new Date());
			
			emEtypeService.save(emEtype);
			
			return RespMsg.ok("新增成功");
			
		}
		
		// 获取上级类型id的清单数组
		QueryWrapper<EmEtype> wrapper = new QueryWrapper<>();
		wrapper.eq(EmEtype.ETYPEFK, etypefk);
		EmEtype one = emEtypeService.getOne(wrapper);
		
		// 判断处理是否还存在上级的上级
		if(null != one.getEtypetids()){
			Integer[] etypetids = one.getEtypetids();
			// 先将数组转为list
			List<Integer> asList= new ArrayList<>(Arrays.asList(etypetids));
			asList.add(etypefk);
			// 再将list转为数组
			int size = asList.size();
			Integer[] array = asList.toArray(new Integer[size]);
			emEtype.setEtypetids(array);
		}else{
			emEtype.setEtypetids(new Integer[]{etypefk});
		}
		
		// 设置创建人
		emEtype.setCruid(UserHelper.getUserId());
		// 设置创建时间
		emEtype.setCrdt(new Date());
		
		emEtypeService.save(emEtype);
		
		return RespMsg.ok("新增成功");
		
	}
	/**
	 * 删除
	 * @param etypefk 类型id
	 * @return
	 */
	@ApiOperation(value = "删除",notes = "一起删除子节点")
	@GetMapping("/delete")
	public RespMsg<String> delete(@RequestParam(required = true) Integer etypefk){

		EmEtype emEtype = emEtypeService.getById(etypefk);
		if(ObjectUtil.isEmpty(emEtype)){
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "该类型信息不存在，请刷新列表！");
		}
		emEtypeService.removeById(etypefk);
		//删除子节点
		Integer[] pids = emEtype.getPids(emEtype);
		String column = EmEtype.ETYPETIDS +"[:" + pids.length +"]";
		emEtypeService.remove(Condition.<EmEtype>create().eq(column,pids));
		
		return RespMsg.ok("删除成功");
	}
	
	
	/**
	 * 列表
	 * @param current 第几页,默认第一页
	 * @param size 每页条数，默认10条
	 * @param elevelnm 等级名称
	 * @return
	 */
	@ApiOperation(value = "事件类型--查看列表")
	@GetMapping("page")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "current", value = "第几页，默认第一页", dataType = "Integer"),
		@ApiImplicitParam(name = "size", value = "每页条数",dataType = "Integer", defaultValue = "10"),
		@ApiImplicitParam(name = "etypenm", value = "类型名称", dataType = "String", required = false)
	})
	public RespMsg<IPage<EmEtype>> listpage(@RequestParam(defaultValue = "1") Integer current,
			@RequestParam(defaultValue = "10") Integer size, String etypenm) {
		
		// 分页
		Page<EmEtype> page = new Page<>(current,size);
		
		QueryWrapper<EmEtype> wrapper = new QueryWrapper<>();
		wrapper.like(ObjectUtil.isNotEmpty(etypenm), EmEtype.ETYPENM,etypenm);
		wrapper.orderByDesc(EmEtype.CRDT);
		
		IPage<EmEtype> iPage = emEtypeService.page(page, wrapper);
		
		return RespMsg.ok(iPage);
		
	}
	
	/**
	 * 查看详情
	 * @param etypefk 类型id
	 * @return
	 */
	@ApiOperation("查看详情")
	@GetMapping("/detail")
	@ApiImplicitParam(name = "etypefk",value = "类型id",dataType = "Integer",required = true)
	public RespMsg<EmEtype> detail(@RequestParam(required = true) Integer etypefk){
		
		EmEtype emEtype = emEtypeService.getById(etypefk);
		
		return RespMsg.ok(emEtype);
	}
	
	/**
	 * 修改
	 * @param emEtype 事件类型实体对象
	 * @return
	 */
	@ApiOperation(value = "修改")
	@PostMapping("update")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> update(@RequestBody(required = true) EmEtype emEtype){
		
		// 获取上级类型id
		Integer etypefk = emEtype.getEtypetid();
		
		// 判断是否是一级节点
		if(etypefk == 0){
			// 设置修改人
			emEtype.setMouid(UserHelper.getUserId());
			// 设置修改时间
			emEtype.setModt(new Date());
			Optional.ofNullable(emEtype).ifPresent(u -> emEtypeService.updateById(emEtype));
			
			return RespMsg.ok("修改成功");
		}
		
		// 获取上级类型id清单数组
		QueryWrapper<EmEtype> wrapper = new QueryWrapper<>();
		wrapper.eq(EmEtype.ETYPEFK, etypefk);
		EmEtype one = emEtypeService.getOne(wrapper);
		
		// 判断处理是否存在上级的上级
		if(null != one.getEtypetids()){
			Integer[] etypetids = one.getEtypetids();
			// 将数组转化为list
			ArrayList<Integer> list = new ArrayList<>(Arrays.asList(etypetids));
			list.add(etypefk);
			// 再将list转为数组
			Integer[] array = list.toArray(new Integer[list.size()]);
			emEtype.setEtypetids(array);
			
		}else{
			emEtype.setEtypetids(new Integer[]{etypefk});
		}
		
		// 设置修改人
		emEtype.setMouid(UserHelper.getUserId());
		// 设置修改时间
		emEtype.setModt(new Date());
		Optional.ofNullable(emEtype).ifPresent(u -> emEtypeService.updateById(emEtype));
		
		return RespMsg.ok("修改成功");
		
	}
	

}
