package com.digitalchina.admin.mid.controller.emergency;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.digitalchina.admin.mid.dto.EmPlanDto;
import com.digitalchina.admin.mid.entity.emergency.EmEvent;
import com.digitalchina.admin.mid.entity.emergency.EmPlan;
import com.digitalchina.admin.mid.service.EmEventService;
import com.digitalchina.admin.mid.service.EmPlanService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.enums.MsttypeEnum;
import com.digitalchina.modules.constant.enums.SysCodeEnum;
import com.digitalchina.modules.entity.Enclosed;
import com.digitalchina.modules.service.EnclosedService;
import com.digitalchina.modules.service.SysAppService;
import com.digitalchina.modules.utils.UserHelper;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 应急预案--前端控制器
 * @author Bruce Li
 * @since 2019/12/3
 */
@Api(tags = "应急预案管理")
@RestController
@RequestMapping("/budgetmanage")
public class BudgetManagementController {
	
	
	@Autowired
	private EnclosedService enclosedService;
	
	@Autowired
	private EmPlanService emPlanService;
	
	@Autowired
	private EmEventService emEventService;
	
	@Autowired
    private SysAppService sysAppService;
	
	/**
	 * 应急预案--新建
	 * @param plan 预案实体对象
	 * @return
	 */
	@ApiOperation(value = "应急预案--新建")
	@PostMapping("create")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> creare(@RequestBody(required = true) EmPlan plan){
		
		// 创建时间
		Date date = new Date();
		
		plan.setCruid(UserHelper.getUserId());
		plan.setCrdt(date);
		// 第一步，保存到应急预案表
		emPlanService.save(plan);
		// 主表记录编号
		Integer mstid = plan.getPlanid();

		// 第二步，获取应急预案管理_附件表对象
		Enclosed enclosed = plan.getEnclosed();
		// 所属系统
		SysCodeEnum sysCodeEnum = SysCodeEnum.CITYEMERGENCY;
		// 主表类型
		MsttypeEnum msttypeEnum = MsttypeEnum.EM_PLAN;
		// 主表记录编号
		enclosed.setMstid(mstid);
		// 排序
		enclosed.setEctype(1);
		List<Enclosed> list = new ArrayList<>();
		list.add(enclosed);
		// 第二步，将数据保存到附件表
		enclosedService.saveEncloseds(sysCodeEnum, msttypeEnum, mstid, list);
		
		return RespMsg.ok("新建成功");
		
	}
	
	/**
	 * 应急预案--修改
	 * @param emPlan 预案实体对象
	 * @return
	 */
	@ApiOperation("应急预案--修改")
	@PostMapping("update")
	@Transactional(rollbackFor = Exception.class)
	public RespMsg<String> update(@RequestBody(required = true) EmPlan emPlan) {
		
		// 第一步，修改应急预案表
		emPlanService.updateById(emPlan);
		
		// 获取应急附件的对象
		Enclosed enclosed = emPlan.getEnclosed();
		// 第二步，判断附件是否有更新
		if(StringUtils.isNotBlank(enclosed.getEcnm())&&
			StringUtils.isNotBlank(enclosed.getEckey())&&null!=enclosed.getEctype()){
			// 所属系统
			SysCodeEnum sysCodeEnum = SysCodeEnum.CITYEMERGENCY;
			// 主表类型
			MsttypeEnum msttypeEnum = MsttypeEnum.EM_PLAN;
			// 主表记录编号
			Integer mstid = emPlan.getPlanid();
			enclosed.setMstid(mstid);
			// 排序
			enclosed.setEctype(1);
			List<Enclosed> list = new ArrayList<>();
			list.add(enclosed);
			// 第二步，将数据保存到附件表
			enclosedService.saveEncloseds(sysCodeEnum, msttypeEnum, mstid, list);
		}
		return RespMsg.ok("修改成功");
		
	}
	
	/**
	 * 应急事件名称--列表
	 * @return
	 */
	@ApiOperation(value = "应急事件--选择")
	@GetMapping("eventnames")
	public RespMsg<List<Map<String, Object>>> eventNames(){
		// 条件过滤
		QueryWrapper<EmEvent> wrapper = new QueryWrapper<>();
		wrapper.select("evid","ename");
		// 预案编号为null
		wrapper.isNull(EmEvent.PLANID);
		List<Map<String, Object>> listMaps = emEventService.listMaps(wrapper);
		
		return RespMsg.ok(listMaps);
		
	}

	/**
	 * 应急预案--查看列表
	 * @param current 第几页,默认第一页
	 * @param size 每页条数
	 * @param planType 预案类型
	 * @param planName 预案名称
	 * @return
	 */
	@ApiOperation(value = "应急预案--查看列表")
	@GetMapping("/select")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "current", value = "第几页，默认第一页", dataType = "Integer"),
		@ApiImplicitParam(name = "size", value = "每页条数",dataType = "Integer", defaultValue = "10"),
		@ApiImplicitParam(name = "planType", value = "预案类型", dataType = "String", required = false),
		@ApiImplicitParam(name = "planName", value = "预案名称", dataType = "String", required = false)
	})
	public RespMsg<IPage<EmPlanDto>> select(@RequestParam(defaultValue = "1") Integer current,
			@RequestParam(defaultValue = "10") Integer size, Integer planType, String planName){
		// 分页处理
		Page<EmPlan> page = new Page<>(current,size);
		
		// 所属系统
		SysCodeEnum sysCodeEnum = SysCodeEnum.CITYEMERGENCY;
		Integer appId = sysAppService.getAppId(sysCodeEnum);
		// 主表类型
		MsttypeEnum msttypeEnum = MsttypeEnum.EM_PLAN;
        Integer msttype = msttypeEnum.getCode();
		
		IPage<EmPlanDto> iPage = emPlanService.pageList(page, appId, msttype, planType, planName);
		
		return RespMsg.ok(iPage);
		
	}
	
	/**
	 * 应急预案--删除
	 * @param planid 预案id
	 * @param eckey 附件key
	 * @return
	 */
	@ApiOperation(value = "应急预案--删除")
	@GetMapping("/delete")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "planid",value = "预案id",dataType = "Integer",required = true),
		@ApiImplicitParam(name = "eckey",value = "附件key",dataType = "String",required = false)
	})
	public RespMsg<String> delete(@RequestParam(required = true)Integer planid,
			@RequestParam(required = false) String eckey){
		
		// 1.应急预案表删除
		emPlanService.removeById(planid);
		
		// 2.附件表删除
		QueryWrapper<Enclosed> wrapper = new QueryWrapper<>();
		wrapper.eq(ObjectUtil.isNotEmpty(eckey), Enclosed.ECKEY,eckey);
		// 判断exkey是否为空
		if(StringUtils.isNotBlank(eckey)){
			enclosedService.remove(wrapper);
		}

		return RespMsg.ok("删除成功");
		
	}
	
	
	/**
	 * 参考预案--选择预案
	 * @return
	 */
	@ApiOperation(value = "参考预案--选择预案")
	@GetMapping("/choose")
	public RespMsg<List<EmPlan>> choosePlan(){
		
		List<EmPlan> list = emPlanService.list(null);
		
		return RespMsg.ok(list);
		
	}
	

}