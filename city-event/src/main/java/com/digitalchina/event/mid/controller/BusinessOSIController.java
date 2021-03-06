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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * ??????????????????
 *
 * @author lzy
 * @since 2019/9/5
 */
@Api(tags = "??????????????????")
@Authorize
@RequestMapping("businessOSI")
@RestController
public class BusinessOSIController {

	private static final Logger log = LoggerFactory.getLogger(BusinessOSIController.class);
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
	@ApiOperation("????????????????????????(????????????")
	@ApiImplicitParams({ @ApiImplicitParam(name = "efbh", value = "??????????????????", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "etbh", value = "??????????????????", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "adid", value = "??????ID", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "townAdid", value = "??????ID", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "benm", value = "????????????", dataType = "String", required = true),
			@ApiImplicitParam(name = "becnt", value = "????????????", dataType = "String", required = true),
			@ApiImplicitParam(name = "bepcdpt0", value = "??????????????????", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "inroom", value = "??????(0?????????1?????????2???)", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "addr", value = "??????", dataType = "String", required = false),
			@ApiImplicitParam(name = "bexy", value = "?????????", dataType = "String", required = false),
			@ApiImplicitParam(name = "linkman", value = "?????????", dataType = "String", required = false),
			@ApiImplicitParam(name = "linktel", value = "???????????????", dataType = "String", required = false),
			@ApiImplicitParam(name = "hapndt", value = "??????TS", dataType = "String", required = false),
			@ApiImplicitParam(name = "inoldbh", value = "??????????????????", dataType = "String", required = false),
			@ApiImplicitParam(name = "enventappstatus", value = "????????????", dataType = "String", required = false),
			@ApiImplicitParam(name = "type", value = "????????????", dataType = "Integer", required = false) })
	public RespMsg ordinaryEventsAdd(Integer efbh, Integer etbh, Integer adid, Integer townAdid, String benm,
			String becnt, Integer bepcdpt0, String linkman, Integer inroom, String addr, String bexy, String linktel,
			String hapndt, String inoldbh,String pic,String enventappstatus, Integer type) {

		if (efbh == null) {
			throw new ServiceException("????????????[efbh]???????????????");
		}
		if (benm == null) {
			throw new ServiceException("????????????[benm]???????????????");
		}
		if (becnt == null) {
			throw new ServiceException("????????????[becnt]???????????????");
		}

		Date now = new Date();
		// ???????????????????????????id????????????????????????????????????????????????SecurityUtil.currentUserId()????????????????????????????????????????????????????????????
		Integer firstUserId;
		Integer firstDeptId;
		// ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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

		// ????????????
		identityService.setAuthenticatedUserId(firstUserId.toString());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(EventEnum.NORMAL_EVENT.getDesp());
		// ??????businessKey????????????????????????
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.addCandidateGroup(task.getId(), firstDeptId.toString());
		/**
		 * ?????????????????????
		 */
		Workflow workflow = new Workflow();
		workflow.setWfnm("Start");
		workflow.setWfkey(task.getProcessDefinitionId());
		workflow.setRev(1);
		workflow.setCrdt(now);
		workflow.setModt(now);
		workflowService.save(workflow);

		/**
		 * ????????????????????????
		 */
		addr = StringUtils.isEmpty(addr) ? "???????????????????????????" : addr;
		hapndt = StringUtils.isEmpty(hapndt) ? DateUtil.format(now, "yyyy-MM-dd HH:mm") : hapndt;
		// ??????????????????????????????????????????????????????43.984899,125.422969
		bexy = StringUtils.isEmpty(bexy) ? "125.422969,43.984899" : bexy;
		// TODO ??????id????????????????????????(id=5)?????????????????????????????????????????????????????????????????????
		adid = ObjectUtil.isEmpty(adid) ? 5 : adid;
		// TODO ????????????id?????????????????????(id=7)?????????????????????????????????????????????????????????????????????
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
		// ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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
		//??????????????????APP(?????????????????????)--???????????????????????????
        if("zjenvent".equals(enventappstatus)&&!("".equals(enventappstatus)||enventappstatus==null)){
			busievent.setRecdpt(15);//??????????????????????????????????????????????????????????????????
		}
		busieventService.save(busievent);
		busieventService.updateGis(busievent.getBeid(), bexy);
		/**
		 * ??????????????????????????????
		 */
		Bestep bestep = new Bestep();
		bestep.setWfid(workflow.getWfid());
		bestep.setBeid(busievent.getBeid());
		bestep.setTskey(task.getId());
		bestep.setEsrcvdt(busievent.getIndt());
		bestep.setEstype(0);
		/*
		 * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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
		String esoper = String.format("%s???%s???%s???", "??????", DateUtil.formatDateTime(now), "???????????????" + origin);
		bestep.setEsoper(esoper);
		bestep.setCrdt(now);
		bestep.setModt(now);
		bestepService.save(bestep);

		// ??????????????????????????????????????????????????????????????????????????????
		if (bepcdpt0 != null) {
			depositService.firstAllocate(SecurityUtil.currentUserId(), busievent.getBeid(), bepcdpt0, null);
		}

		//??????????????????APP(?????????????????????)-------??????????????????????????????
		if(SecurityUtil.currentUser()!=null && SecurityUtil.currentUser().getUserSource() != null){
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
	 * ????????????ID???????????????????????????--????????????
	 * @param page
	 * @return
	 */
	@GetMapping("selectBusieventByUser")
	@ApiOperation("????????????ID???????????????????????????--????????????")
	@ApiImplicitParams({@ApiImplicitParam(name = "page", value = "?????????????????? ?????? size ???????????? ???current ????????? total ?????????", dataType = "Integer"),
			@ApiImplicitParam(name = "current", value = "?????????", dataType = "Date"),
			@ApiImplicitParam(name = "size", value = "????????????", dataType = "Date"),
	})
	public RespMsg<IPage<BusieventUserDto>> selectBusieventByUser(Page<BusieventUserDto> page ){

		page.setRecords(busieventUserDtoService.selectBusieventByUser(page,SecurityUtil.currentUserId(),SecurityUtil.currentUser().getUserSource()));
		return RespMsg.ok(page);
	}

	/**
	 * ????????????ID????????????id
	 * @param beid
	 * @return
	 */
	@GetMapping("selectBusieventByBeid")
	@ApiOperation("????????????ID????????????id")
	@ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "??????id", dataType = "String")})
	public RespMsg<List<Map<String,String>>> selectBusieventByBeid(String beid ){
		return RespMsg.ok(busieventUserDtoService.selectBusieventByBeid(Integer.valueOf(beid)));
	}


	@PostMapping("investigationEvents/add")
	@ApiOperation("???????????????????????????????????????")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cenm", value = "????????????", dataType = "String", required = true),
			@ApiImplicitParam(name = "cecnt", value = "????????????", dataType = "String"),
			@ApiImplicitParam(name = "cpdesc", value = "????????????", dataType = "String"),
			@ApiImplicitParam(name = "cpbedid", value = "??????????????????", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "cepcdpt0", value = "??????????????????", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "etbh", value = "????????????", dataType = "Integer"),
			@ApiImplicitParam(name = "efbh", value = "????????????", dataType = "Integer"),
			@ApiImplicitParam(name = "hapndt", value = "????????????", dataType = "String", required = true),
			@ApiImplicitParam(name = "addr", value = "????????????", dataType = "String", required = true),
			@ApiImplicitParam(name = "bexy", value = "?????????", dataType = "String", required = false),
			@ApiImplicitParam(name = "adid", value = "??????ID", dataType = "Integer"),
			@ApiImplicitParam(name = "townAdid", value = "??????ID", dataType = "Integer") })
	public RespMsg investigationEventsAdd(String cenm, String cecnt, String cpdesc, String hapndt, String addr,
			String bexy, Integer cepcdpt0, Integer cpbedid, Integer etbh, Integer efbh, Integer adid,
			Integer townAdid) {
		// ????????????
		// identityService.setAuthenticatedUserId(SecurityUtil.currentUserId().toString());

		// ???????????????????????????id??????SecurityUtil.currentUserId()??????????????????????????????????????????????????????
		Integer serviceUserId = SecurityUtil.currentUserId();
		// ??????????????????????????????id?????????????????????????????????????????????????????????????????????????????????????????????????????????
		Integer serviceDeptId = eventService.getBedptByUserId(serviceUserId).getBedid();

		identityService.setAuthenticatedUserId(serviceUserId.toString());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(EventEnum.COOP_EVENT.getDesp());
		// ??????businessKey????????????????????????
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
	@ApiOperation("??????????????????ID????????????????????????")
	@ApiImplicitParams({ @ApiImplicitParam(name = "beid", value = "??????ID", dataType = "Integer", required = true) })
	public RespMsg<Busievent> subjectList(Integer beid) {
		return RespMsg.ok(busieventService.getOne(Condition.<Busievent>create().eq(Busievent.BEID, beid)));
	}

}
