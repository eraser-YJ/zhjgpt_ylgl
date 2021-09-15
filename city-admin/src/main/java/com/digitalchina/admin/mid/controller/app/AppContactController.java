package com.digitalchina.admin.mid.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.app.AppContact;
import com.digitalchina.admin.mid.service.AppContactService;
import com.digitalchina.common.utils.PinyinUtils;
import com.digitalchina.common.web.RespMsg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 管理后台-app通讯录 前端控制器
 * @author Bruce Li
 * @sice 2019/11/5
 */
@Api(tags = "领导决策App通讯录管理接口")
@RestController
@RequestMapping("admin/appcontact")
public class AppContactController {
	
	@Autowired
	private AppContactService appContactService;
	
	/**
	 * 新增
	 * @param appContact
	 * @return
	 */
	@GetMapping("insert")
	@ApiOperation(value = "新增")
	@Transactional
	public RespMsg<?> insert(AppContact appContact){
		
		// 判断是否为空
		if(null == appContact){
			return RespMsg.error("参数不能为空");
		}
		String name = appContact.getName();
		
		// 设置姓名拼音
		String pinYin = PinyinUtils.toPinyinString(name);
		appContact.setPinYin(pinYin);
		
		// 设置首字母大写
		String letter = pinYin.substring(0, 1).toUpperCase();
		appContact.setLetter(letter);
		
		appContactService.save(appContact);
		
		return RespMsg.ok();
		
	}
	
	
	/**
	 * 更新
	 * @param appContact
	 * @return
	 */
	@GetMapping("update")
	@ApiOperation(value = "更新")
	@Transactional
	public RespMsg<?> update(AppContact appContact){
		
		if(appContact==null){
			return RespMsg.error("参数不能为空");
		}
		
		appContactService.updateById(appContact);
		
		return RespMsg.ok();
		
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("delete")
	@ApiOperation(value = "删除")
	@ApiImplicitParam(name = "id",value = "主键id", required = true)
	@Transactional
	public RespMsg<?> delete(Integer id){
		
		if(id == null){
			return RespMsg.error("请输入要删除的内容");
		}
		
		appContactService.removeById(id);
		
		return RespMsg.ok();
	}
	
	/**
	 * 查询
	 * @return
	 */
	@GetMapping("select")
	@ApiOperation(value = "查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "current", value = "第几页,默认第一页",dataType = "Integer",required = false),
		@ApiImplicitParam(name= "size", value = "每页条数,默认10条",dataType = "Integer", required = false)
	})
	public RespMsg<IPage<AppContact>> select(@RequestParam(defaultValue = "1") Integer current,
			@RequestParam(defaultValue = "10") Integer size){
		// 分页
		Page<AppContact> page = new Page<>(current,size);
		
		QueryWrapper<AppContact> wrapper = new QueryWrapper<>();
		
		wrapper.orderByAsc(AppContact.LETTER);
		
		IPage<AppContact> iPage = appContactService.page(page, wrapper);
		
		return RespMsg.ok(iPage);
		
	}
	
	

}
