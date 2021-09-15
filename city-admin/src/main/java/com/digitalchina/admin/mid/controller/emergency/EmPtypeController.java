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
import com.digitalchina.admin.mid.entity.emergency.EmPtype;
import com.digitalchina.admin.mid.service.EmPtypeService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.utils.UserHelper;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 应急预案类型 前端控制器
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-16
 */
@Api(tags = "应急预案类型")
@RestController
@RequestMapping("/emptype")
public class EmPtypeController {
	
	@Autowired
	EmPtypeService emPtypeService;
	
	/**
	 * 选择应急预案类型
	 * @return
	 */
	@ApiOperation(value = "选择应急预案类型")
	@GetMapping("choose")
	public RespMsg<List<EmPtype>> choose(){
		
		// 排序
		QueryWrapper<EmPtype> wrapper = new QueryWrapper<>();
		wrapper.orderByAsc(EmPtype.SORT);
		
		List<EmPtype> list = emPtypeService.list(wrapper);
		
		return RespMsg.ok(list);
		
	}
	
	/**
	 * 新增
	 * @param emPtype 应急预案类型实体对象
	 * @return
	 */
	@ApiOperation(value = "新增")
	@PostMapping("save")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> add(@RequestBody(required = true) EmPtype emPtype){
		
		// 设置创建人
		emPtype.setCruid(UserHelper.getUserId());
		// 设置创建时间
		emPtype.setCrdt(new Date());
		
		emPtypeService.save(emPtype);
		
		return RespMsg.ok("新建成功");
	}
	/**
	 * 删除
	 * @param ptypefk 等级id
	 * @return
	 */
	@ApiOperation(value = "删除")
	@ApiImplicitParam(name = "ptypefk",value = "等级id",dataType = "Integer",required = true)
	@GetMapping("/delete")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> delete(@RequestParam(required = true) Integer ptypefk){
		
		emPtypeService.removeById(ptypefk);
		
		return RespMsg.ok("删除成功");
	}
	
	/**
	 * 列表
	 * @param current
	 * @param size
	 * @param ptypenm
	 * @return
	 */
	@ApiOperation(value = "预案类型--查看列表")
	@GetMapping("page")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "current", value = "第几页，默认第一页", dataType = "Integer"),
		@ApiImplicitParam(name = "size", value = "每页条数",dataType = "Integer", defaultValue = "10"),
		@ApiImplicitParam(name = "ptypenm", value = "类型名称", dataType = "String", required = false)
	})
	public RespMsg<IPage<EmPtype>> listpage(@RequestParam(defaultValue = "1") Integer current,
			@RequestParam(defaultValue = "10") Integer size, String ptypenm) {
		
		// 分页
		Page<EmPtype> page = new Page<>(current,size);
		
		QueryWrapper<EmPtype> wrapper = new QueryWrapper<>();
		wrapper.like(ObjectUtil.isNotEmpty(ptypenm), EmPtype.PTYPENM,ptypenm);
		wrapper.orderByAsc(EmPtype.SORT);
		
		IPage<EmPtype> iPage = emPtypeService.page(page, wrapper);
		
		return RespMsg.ok(iPage);
		
	}
	
	/**
	 * 查看详情
	 * @param ptypefk 类型id
	 * @return
	 */
	@GetMapping("/detail")
	@ApiOperation("查看详情")
	@ApiImplicitParam(name = "ptypefk",value = "类型id",dataType = "Integer",required = true)
	public RespMsg<EmPtype> detail(@RequestParam(required = true) Integer ptypefk){
		
		EmPtype emPtype = emPtypeService.getById(ptypefk);
		
		return RespMsg.ok(emPtype);
	}
	
	/**
	 * 修改
	 * @param emPtype 应急预案类型实体对象
	 * @return
	 */
	@ApiOperation(value = "修改")
	@PostMapping("update")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> update(@RequestBody(required = true) EmPtype emPtype){
		
		// 设置修改人
		emPtype.setMouid(UserHelper.getUserId());
		// 设置修改时间
		emPtype.setModt(new Date());
		Optional.ofNullable(emPtype).ifPresent(u -> emPtypeService.updateById(emPtype));
		
		return RespMsg.ok("修改成功");
		
	}
	
	

}
