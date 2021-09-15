package com.digitalchina.admin.mid.controller.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.app.AppVersion;
import com.digitalchina.admin.mid.service.AppVersionService;
import com.digitalchina.common.web.RespMsg;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * app版本信息 前端控制器
 * </p>
 *
 * @author Bruce Li
 * @since 2019-11-07
 */
@Api(tags = "app版本信息接口")
@RestController
@RequestMapping("/appVersion")
public class AppVersionController {
	
	@Autowired
	private AppVersionService appVersionService;
	
	/**
	 * 查询
	 * @param current 第几页,默认第一页
	 * @param size 每页显示条数,默认10条
	 * @param version App版本号
	 * @return
	 */
	@GetMapping("select")
	@ApiOperation(value = "app版本信息查看")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer", defaultValue = "1", required = false),
		@ApiImplicitParam(name = "size", value = "每页显示条数,默认10条", dataType = "Integer", defaultValue = "10", required = false),
		@ApiImplicitParam(name = "version", value = "App版本号", dataType = "String", required = false)
	})
	public RespMsg<IPage<AppVersion>> select(@RequestParam(defaultValue = "1") Integer current,@RequestParam(defaultValue = "10") Integer size,
			String version){
		// 分页处理
		Page<AppVersion> page = new Page<>(current,size);
		// 条件过滤
		QueryWrapper<AppVersion> wrapper = new QueryWrapper<>();
		
		wrapper.like(ObjectUtil.isNotEmpty(version), AppVersion.APP_VERSION,version);
		
		IPage<AppVersion> iPage = appVersionService.page(page, wrapper);
		
		return RespMsg.ok(iPage);
		
	}
	
	/**
	 * 新增
	 * @param appVersion
	 * @return
	 */
	@PostMapping("add")
	@ApiOperation(value = "app版本信息新增")
	@Transactional
	public RespMsg<?> add(AppVersion appVersion){
		// 判断是否为null
		if(ObjectUtil.isEmpty(appVersion)){
		
			return RespMsg.error("参数不能为空");
		}
		
		appVersionService.save(appVersion);
		
		return RespMsg.ok();
		
	}
	
	/**
	 * 更新
	 * @param appVersion
	 * @return
	 */
	@PostMapping("update")
	@ApiOperation(value = "app版本更新")
	@Transactional
	public RespMsg<?> update(AppVersion appVersion){
		// 判断处理
		if(ObjectUtil.isEmpty(appVersion)){
			
			return RespMsg.error("参数不能为空");
		}
		
		appVersionService.updateById(appVersion);
		
		return RespMsg.ok();
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@GetMapping("delete")
	@ApiOperation(value = "App版本删除")
	@ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
	@Transactional
	public RespMsg<?> delete(Integer id){

		appVersionService.removeById(id);
		
		return RespMsg.ok();
		
	}
	

}
