package com.digitalchina.event.mid.controller;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.event.dto.BusieventUserDto;
import com.digitalchina.event.mid.entity.*;
import com.digitalchina.event.mid.service.*;
import com.digitalchina.modules.security.UserSource;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.consts.EventConst;
import com.digitalchina.modules.constant.TransConstant;
import com.digitalchina.modules.constant.enums.BeFromEnum;
import com.digitalchina.modules.constant.enums.BestatEnum;
import com.digitalchina.modules.constant.enums.EventEnum;
import com.digitalchina.modules.security.Authorize;
import com.digitalchina.modules.security.SecurityUtil;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 业务系统互联
 *
 * @author lzy
 * @since 2019/9/5
 */
@Api(tags = "业务系统互联")
@Authorize
@RequestMapping("businessOSI")
@RestController
public class BusinessOSIController {
	@Autowired
	private IdentityService identityService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private BusieventService busieventService;
	@Autowired
	private CoopeventService coopeventService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private BestepService bestepService;
	@Autowired
	private EventDepositService depositService;
	@Autowired
	private BefromService befromService;
	@Autowired
	private EventService eventService;
	@Autowired
	private BedeptService bedeptService;
	@Autowired
	private DeptUserService deptUserService;
	@Autowired
	private BusieventUserService busieventUserService;
	@Autowired
	private BusieventUserDtoService busieventUserDtoService;

	@Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER)
	@PostMapping("ordinaryEvents/add")
	@ApiOperation("普通事件新增服务(开启流）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "efbh", value = "事项来源代码", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "etbh", value = "事项类型代码", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "adid", value = "区划ID", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "townAdid", value = "乡镇ID", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "benm", value = "事件名称", dataType = "String", required = true),
			@ApiImplicitParam(name = "becnt", value = "事件描述", dataType = "String", required = true),
			@ApiImplicitParam(name = "bepcdpt0", value = "指定承办部门", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "inroom", value = "场所(0室内，1室外，2无)", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "addr", value = "地址", dataType = "String", required = false),
			@ApiImplicitParam(name = "bexy", value = "经纬度", dataType = "String", required = false),
			@ApiImplicitParam(name = "linkman", value = "联系人", dataType = "String", required = false),
			@ApiImplicitParam(name = "linktel", value = "联系人电话", dataType = "String", required = false),
			@ApiImplicitParam(name = "hapndt", value = "发生TS", dataType = "String", required = false),
			@ApiImplicitParam(name = "inoldbh", value = "转入时原编号", dataType = "String", required = false),
			@ApiImplicitParam(name = "type", value = "性质分类", dataType = "Integer", required = false) })
	public RespMsg ordinaryEventsAdd(Integer efbh, Integer etbh, Integer adid, Integer townAdid, String benm,
			String becnt, Integer bepcdpt0, String linkman, Integer inroom, String addr, String bexy, String linktel,
			String hapndt, String inoldbh,String pic, Integer type) {

		if (efbh == null) {
			throw new ServiceException("事件来源[efbh]不能为空！");
		}
		if (benm == null) {
			throw new ServiceException("事件名称[benm]不能为空！");
		}
		if (becnt == null) {
			throw new ServiceException("事件描述[becnt]不能为空！");
		}

		Date now = new Date();
		// 发起事件时的发起人id，应该是一级指挥中心下的用户，从SecurityUtil.currentUserId()获取。非一级指挥中心下的用户则不能发起。
		Integer firstUserId;
		Integer firstDeptId;
		// 如果事件来源是：预警升级、市民互动，则不需登录。从数据库拿一个一级用户和一级指挥中心。
		if (efbh == BeFromEnum.CITY_WARN.getCode() || efbh == BeFromEnum.CITY_ZENS.getCode()) {
			Bedept bedept = bedeptService
					.getOne(Condition.<Bedept>create().eq(Bedept.BDTYPE, EventConst.FIRST_LEVEL_DEP));
			firstDeptId = bedept.getBedid();
			firstUserId = deptUserService.list(Condition.<DeptUser>create().eq(DeptUser.DID, firstDeptId)).get(0)
					.getUid();
		} else {
			firstUserId = SecurityUtil.currentUserId();
			firstDeptId = eventService.getBedptByUserId(firstUserId).getBedid();
		}

		// 开启流程
		identityService.setAuthenticatedUserId(firstUserId.toString());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(EventEnum.NORMAL_EVENT.getDesp());
		// 根据businessKey找到当前任务节点
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.addCandidateGroup(task.getId(), firstDeptId.toString());
		/**
		 * 保存工作流信息
		 */
		Workflow workflow = new Workflow();
		workflow.setWfnm("Start");
		workflow.setWfkey(task.getProcessDefinitionId());
		workflow.setRev(1);
		workflow.setCrdt(now);
		workflow.setModt(now);
		workflowService.save(workflow);

		/**
		 * 保存业务事件信息
		 */
		addr = StringUtils.isEmpty(addr) ? "长春新区管理委员会" : addr;
		hapndt = StringUtils.isEmpty(hapndt) ? DateUtil.format(now, "yyyy-MM-dd HH:mm") : hapndt;
		// 坐标默认值：长春新区管理委员会经度，43.984899,125.422969
		bexy = StringUtils.isEmpty(bexy) ? "125.422969,43.984899" : bexy;
		// TODO 区县id默认值为：高新区(id=5)，但是这个目前还没接数据。接数据之后可能要修改
		adid = ObjectUtil.isEmpty(adid) ? 5 : adid;
		// TODO 事件类型id默认值为：其他(id=7)，但是这个目前还没接数据。接数据之后可能要修改
		etbh = ObjectUtil.isEmpty(etbh) ? 7 : etbh;
		Busievent busievent = new Busievent();
		busievent.setWfid(workflow.getWfid());
		busievent.setEfbh(efbh);
		busievent.setEtbh(etbh);
		busievent.setAdid(adid);
		busievent.setTownAdid(townAdid);
		busievent.setProcInstId(pi.getId());
		String sjfs = "B";
		Format f1 = new DecimalFormat("00");
		String efbhStr = f1.format(efbh);
		String dateStr = DateUtil.format(now, "yyyyMMdd");
		String bebhext = sjfs + efbhStr + dateStr;
		QueryWrapper<Busievent> queryWrapper = Condition.<Busievent>create().likeRight(Busievent.BEBH, bebhext)
				.select("max(substring(bebh,12)) as bebh");
		Busievent busievents = busieventService.getOne(queryWrapper);
		int controlNo = 0;
		if (busievents != null) {
			controlNo = Integer.parseInt(busievents.getBebh());
		}
		Format f2 = new DecimalFormat("0000");
		busievent.setBebh(bebhext + f2.format(controlNo + 1));
		busievent.setBenm(benm);
		busievent.setBecnt(becnt);
		/*
		 * Group group=identityService.createGroupQuery().groupMember(SecurityUtil.
		 * currentUserId().toString()).singleResult(); Integer besrcdpt=
		 * Integer.valueOf(group.getId());
		 */
//        Integer besrcdpt=3;

		busievent.setBesrcdpt(firstDeptId);
		// 发起事件时的指定承办部门，等于一级指挥中心，因为自己要准备转派或者升级或者取消。
		busievent.setBepcdpt0(firstDeptId);
		busievent.setInroom(inroom);
		busievent.setAddr(addr);
		// busievent.setBexy(bexy);
		busievent.setLinkman(linkman);
		busievent.setLinktel(linktel);
		if (StringUtils.isNotBlank(hapndt)) {
			busievent.setHapndt(DateUtil.parse(hapndt));
		}
		busievent.setIndt(now);
		busievent.setInoldbh(inoldbh);
		busievent.setBestat(BestatEnum.PENDING.getCode());
		busievent.setCrdt(now);
		busievent.setModt(now);
		busievent.setType(type);
		//智慧建管手机APP(不影响其他模块)--住建用户上报事件，受理部门直接制定15
		if(SecurityUtil.currentUser().getUserSource() != null){
			busievent.setRecdpt(15);
		}
		busieventService.save(busievent);
		busieventService.updateGis(busievent.getBeid(), bexy);
		/**
		 * 保存业务事件阶段信息
		 */
		Bestep bestep = new Bestep();
		bestep.setWfid(workflow.getWfid());
		bestep.setBeid(busievent.getBeid());
		bestep.setTskey(task.getId());
		bestep.setEsrcvdt(busievent.getIndt());
		bestep.setEstype(0);
		/*
		 * 当前操作人、操作部门、指定承办部门（一级指挥中心下的用户、一级指挥中心、一级指挥中心）
		 * bestep.setEsopman(SecurityUtil.currentUserId()); Group
		 * group=identityService.createGroupQuery().groupMember(SecurityUtil.
		 * currentUserId().toString()).singleResult();
		 * bestep.setEsopdept(Integer.valueOf(group.getId()));
		 */
		bestep.setEsopman(firstUserId);
		bestep.setEsopdept(firstDeptId);
		bestep.setBepcdpt0(firstDeptId);
		String origin = "";
		if (busievent.getEfbh() != null) {
			Befrom befrom = befromService.getOne(Condition.<Befrom>create().eq(Befrom.EFBH, busievent.getEfbh()));
			origin = (befrom == null) ? "" : befrom.getEfnm();
		}
		String esoper = String.format("%s，%s，%s。", "开始", DateUtil.formatDateTime(now), "事件来源：" + origin);
		bestep.setEsoper(esoper);
		bestep.setCrdt(now);
		bestep.setModt(now);
		bestepService.save(bestep);

		// 如果创建时已经指派了二级部门，执行分拨给二级的操作：
		if (bepcdpt0 != null) {
			depositService.firstAllocate(SecurityUtil.currentUserId(), busievent.getBeid(), bepcdpt0, null);
		}

		//智慧建管手机APP(不影响其他模块)-------插入—事件账户表关系
		if(SecurityUtil.currentUser().getUserSource() != null){
			BusieventUser busieventUser = new BusieventUser();
			busieventUser.setBeid(busievent.getBeid());
			busieventUser.setUid(SecurityUtil.currentUserId());
			busieventUser.setUtype(SecurityUtil.currentUser().getUserSource());
			busieventUserService.insertBusieventUser(busieventUser);
			if(!StringUtils.isEmpty(pic)){
				String[] split = pic.split(",");
				for (String picStr: split) {
					busieventUserDtoService.insertAttachmentByBeid(busievent.getBeid(),picStr);
				}
			}
		}
		return RespMsg.ok(busievent.getBeid());
	}

	/**
	 * 根据用户ID和用户类型查询事件--分页查询
	 * @param page
	 * @return
	 */
	@GetMapping("selectBusieventByUser")
	@ApiOperation("根据用户ID和用户类型查询事件--分页查询")
	@ApiImplicitParams({@ApiImplicitParam(name = "page", value = "分页查询条件 包括 size 分页大小 ，current 当前页 total 总页数", dataType = "Integer"),
			@ApiImplicitParam(name = "current", value = "当前页", dataType = "Date"),
			@ApiImplicitParam(name = "size", value = "分页大小", dataType = "Date"),
	})
	public RespMsg<IPage<BusieventUserDto>> selectBusieventByUser(Page<BusieventUserDto> page ){

		page.setRecords(busieventUserDtoService.selectBusieventByUser(page,SecurityUtil.currentUserId(),SecurityUtil.currentUser().getUserSource()));
		return RespMsg.ok(page);
	}

	/**
	 * 根据事件ID查询图片id
	 * @param beid
	 * @return
	 */
	@GetMapping("selectBusieventByBeid")
	@ApiOperation("根据事件ID查询图片id")
	@ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", dataType = "String")})
	public RespMsg<List<Map<String,String>>> selectBusieventByBeid(String beid ){
		return RespMsg.ok(busieventUserDtoService.selectBusieventByBeid(Integer.valueOf(beid)));
	}


	@PostMapping("investigationEvents/add")
	@ApiOperation("协查事件新增服务（开启流）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cenm", value = "事件名称", dataType = "String", required = true),
			@ApiImplicitParam(name = "cecnt", value = "事件描述", dataType = "String"),
			@ApiImplicitParam(name = "cpdesc", value = "协查描述", dataType = "String"),
			@ApiImplicitParam(name = "cpbedid", value = "指定协查部门", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "cepcdpt0", value = "指定承办部门", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "etbh", value = "事项类型", dataType = "Integer"),
			@ApiImplicitParam(name = "efbh", value = "事项来源", dataType = "Integer"),
			@ApiImplicitParam(name = "hapndt", value = "发生时间", dataType = "String", required = true),
			@ApiImplicitParam(name = "addr", value = "发生地点", dataType = "String", required = true),
			@ApiImplicitParam(name = "bexy", value = "经纬度", dataType = "String", required = false),
			@ApiImplicitParam(name = "adid", value = "区划ID", dataType = "Integer"),
			@ApiImplicitParam(name = "townAdid", value = "乡镇ID", dataType = "Integer") })
	public RespMsg investigationEventsAdd(String cenm, String cecnt, String cpdesc, String hapndt, String addr,
			String bexy, Integer cepcdpt0, Integer cpbedid, Integer etbh, Integer efbh, Integer adid,
			Integer townAdid) {
		// 开启流程
		// identityService.setAuthenticatedUserId(SecurityUtil.currentUserId().toString());

		// 发起事件时的发起人id，从SecurityUtil.currentUserId()获取。非业务部门下的用户则不能发起。
		Integer serviceUserId = SecurityUtil.currentUserId();
		// 发起事件时的来源部门id，因为自己就是来源。根据当前用户获取所在的部门，非业务部门则不能发起。
		Integer serviceDeptId = eventService.getBedptByUserId(serviceUserId).getBedid();

		identityService.setAuthenticatedUserId(serviceUserId.toString());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(EventEnum.COOP_EVENT.getDesp());
		// 根据businessKey找到当前任务节点
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.addCandidateGroup(task.getId(), serviceDeptId.toString());

		Date now = new Date();
		Coopevent coopevent = new Coopevent();
		coopevent.setProcInstId(task.getProcessInstanceId());
		coopevent.setWfid(Integer.valueOf(task.getId()));
		coopevent.setCesrcdpt(serviceDeptId);
		coopevent.setCepcdpt(serviceDeptId);
		coopevent.setCpbedid(cpbedid);
		coopevent.setCepcdpt0(cepcdpt0);

		coopevent.setCenm(cenm);
		coopevent.setCecnt(cecnt);
		coopevent.setCpdesc(cpdesc);
		coopevent.setEtbh(etbh);
		coopevent.setEfbh(efbh);
		Format fm = new DecimalFormat("0000");
		String cebh = "C" + DateUtil.format(now, "yyyyMMdd") + fm.format(coopeventService.count(null));
		coopevent.setCebh(cebh);
		coopevent.setCestat((Integer) EventEnum.MODIFY_COOP.getValue());
		coopevent.setAddr(addr);
		coopevent.setHapndt(hapndt == null ? null : DateUtil.parse(hapndt));
		coopevent.setAdid(adid);
		coopevent.setTownAdid(townAdid);
		coopevent.setModt(now);
		coopeventService.save(coopevent);
		coopeventService.updateGis(coopevent.getCeid(), bexy);
		coopeventService.submitSecond(coopevent.getCeid(), null);
		return RespMsg.ok(coopevent.getCeid());
	}

	@GetMapping("getBusieventById")
	@ApiOperation("根据业务事件ID查询事件处理状态")
	@ApiImplicitParams({ @ApiImplicitParam(name = "beid", value = "事件ID", dataType = "Integer", required = true) })
	public RespMsg<Busievent> subjectList(Integer beid) {
		return RespMsg.ok(busieventService.getOne(Condition.<Busievent>create().eq(Busievent.BEID, beid)));
	}

}
