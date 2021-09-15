package com.digitalchina.event.mid.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.consts.EventConst;
import com.digitalchina.event.dto.eventdeposit.BusieventBasicInfoDto;
import com.digitalchina.event.dto.eventdeposit.BusieventDto;
import com.digitalchina.event.mid.entity.Bestep;
import com.digitalchina.event.mid.entity.Busievent;
import com.digitalchina.event.mid.service.BestepService;
import com.digitalchina.event.mid.service.BusieventService;
import com.digitalchina.event.mid.service.EventDepositService;
import com.digitalchina.event.mid.service.EventService;
import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.constant.enums.EventEnum;
import com.digitalchina.modules.security.Authorize;
import com.digitalchina.modules.security.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api(tags = "事件处置接口")
@Authorize
@RestController
@RequestMapping("event/deposit")
public class EventDepositController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private EventDepositService depositService;
    @Autowired
    private BestepService bestepService;
    @Autowired
    private EventService eventService;
    @Autowired
    private BusieventService busieventService;

    @PostMapping("/getTasks")
    public Object getTaskS(String userid) {
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey(EventEnum.NORMAL_EVENT.getDesp())
                .orderByTaskCreateTime().desc();
        if (userid != null)
            taskQuery.taskAssignee(userid);
        List<Task> taskList = taskQuery.list();

        for (Task task : taskList) {
            System.out.println("任务id: " + task.getId());
            System.out.println("任务名字: " + task.getName());
            System.out.println("任务创建时间: " + task.getCreateTime());
            System.out.println("办理人: " + task.getAssignee());
        }
        return "getTasks ok";
    }

    /**
     * 事件处置-一级指挥中心-事件统计
     */
    @GetMapping("getFirstLevelEventCount")
    @ApiOperation("事件处置-一级指挥中心-事件统计")
    public RespMsg<List> getFirstLevelEventCount() {
        List list = depositService.getFirstLevelEventCount();
        return RespMsg.ok(list);
    }

    /**
     * 事件处置-一级指挥中心-事件列表
     */
    @GetMapping(value = "getFirstLevelEventList")
    @ApiOperation("事件处置-一级部门-事件列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "bestat", value = "事件状态，-未处理，-转派中，-处理中，-待审批，-待核查，-拒绝件，-取消件，-升级件"),
            @ApiImplicitParam(name = "efbh", value = "事件来源"), @ApiImplicitParam(name = "etbh", value = "事件类型"),
            @ApiImplicitParam(name = "benm", value = "事件名称/事件描述/事件编号"),
            @ApiImplicitParam(name = "becnt", value = "事件描述"),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer")})
    public RespMsg<IPage<BusieventDto>> getFirstLevelEventList(String bestat, Integer efbh, Integer etbh, String benm,
                                                               String becnt, @RequestParam(defaultValue = "10") Integer size,
                                                               @RequestParam(defaultValue = "1") Integer current) {
        bestat = Optional.ofNullable(bestat).filter(ObjectUtil::isNotEmpty).orElse("未处理");
        IPage<BusieventDto> eventList = depositService.getFirstLevelEventList(bestat, efbh, etbh, benm, becnt, size,
                current);
        return RespMsg.ok(eventList);
    }

    /**
     * 事件处置-二级指挥中心-事件统计
     */
    @GetMapping(value = "getSecondLevelEventCount")
    @ApiOperation("事件处置-二级指挥中心-事件统计")
    @ApiImplicitParams({@ApiImplicitParam(name = "bedid", value = "部门id", required = true),})
    public RespMsg<List> getSecondLevelEventCount(Integer bedid) {
        List list = depositService.getSecondLevelEventCount(bedid);
        return RespMsg.ok(list);
    }

    /**
     * 事件处置-二级指挥中心-事件
     */
    @GetMapping(value = "getSecondLevelEventList")
    @ApiOperation("事件处置-二级部门-事件列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "bestat", value = "事件种类，默认-未处理，其它：转派中，-处理中，-待核查，-待审批，-待核查，-拒绝件，-完成件"),
            @ApiImplicitParam(name = "efbh", value = "事件类型"), @ApiImplicitParam(name = "etbh", value = "事件来源"),
            @ApiImplicitParam(name = "benm", value = "事件名称"), @ApiImplicitParam(name = "becnt", value = "事件描述"),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer")})
    public RespMsg<IPage<BusieventDto>> getSecondLevelEventList(String bestat, Integer efbh, Integer etbh, String benm,
                                                                String becnt, @RequestParam(defaultValue = "10") Integer size,
                                                                @RequestParam(defaultValue = "1") Integer current) {
        bestat = Optional.ofNullable(bestat).filter(ObjectUtil::isNotEmpty).orElse("未处理");
        IPage<BusieventDto> eventList = depositService.getSecondLevelEventList(bestat, efbh, etbh, benm, becnt, size,
                current);
        return RespMsg.ok(eventList);
    }

    /**
     * 事件处置-一级指挥中心-未处理
     */
    @GetMapping(value = "getServiceLevelEventCount")
    @ApiOperation("事件处置-业务部门-事件统计")
    @ApiImplicitParams({@ApiImplicitParam(name = "bedid", value = "部门id", required = true),})
    public RespMsg<List> getServiceLevelEventCount(Integer bedid) {
        List list = depositService.getServiceLevelEventCount(bedid);
        return RespMsg.ok(list);
    }

    /**
     * 事件处置-二级指挥中心-事件
     */
    @GetMapping(value = "getServiceLevelEventList")
    @ApiOperation("事件处置-业务部门-事件列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "bestat", value = "事件种类，默认-未处理，其它：-处理中，-协查件（发起件|协办件），-被驳回，-复查件，-完成件"),
            @ApiImplicitParam(name = "efbh", value = "事件类型"), @ApiImplicitParam(name = "etbh", value = "事件来源"),
            @ApiImplicitParam(name = "benm", value = "事件名称"), @ApiImplicitParam(name = "becnt", value = "事件描述"),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer")})
    public RespMsg<IPage<BusieventDto>> getServiceLevelEventList(String bestat, Integer efbh, Integer etbh, String benm,
                                                                 String becnt, @RequestParam(defaultValue = "10") Integer size,
                                                                 @RequestParam(defaultValue = "1") Integer current) {
        bestat = Optional.ofNullable(bestat).filter(ObjectUtil::isNotEmpty).orElse("未处理");
        IPage<BusieventDto> eventList = depositService.getServiceLevelEventList(bestat, efbh, etbh, benm, becnt, size,
                current);
        return RespMsg.ok(eventList);
    }

    /**
     * 事件处置-一级转派
     */
    @GetMapping(value = "first/allocate")
    @ApiOperation("事件处置-一级部门-转派")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "bedid", value = "经派机构（受理的二（N）级指挥中心）", required = true),
            @ApiImplicitParam(name = "reason", value = "转派说明", required = false)})
    public RespMsg<Void> firstAllocate(Integer beid, Integer bedid, String reason) {
        try {
            depositService.firstAllocate(SecurityUtil.currentUserId(), beid, bedid, reason);
        } catch (Exception e) {
            throw new ServiceException("一级转派操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-二级转派
     */
    @GetMapping(value = "second/allocate")
    @ApiOperation("事件处置-二级部门-转派")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "bedid", value = "经派机构（受理的二（N）级指挥中心）", required = true)})
    public RespMsg<Void> secondAllocate(Integer beid, Integer bedid) {
        try {
            depositService.secondAllocate(SecurityUtil.currentUserId(), beid, bedid);
        } catch (Exception e) {
            throw new ServiceException("二级转派操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-详情查看
     */
    @GetMapping(value = "eventDepositDetail")
    @ApiOperation("事件处置-详情查看-处置信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "order", value = "顺序", dataType = "String", defaultValue = "asc")})
    public RespMsg<List<Map<String, Object>>> eventDepositDetail(Integer beid,
                                                                 @RequestParam(defaultValue = "asc") String order) {
        return RespMsg.ok(bestepService.eventDepositInfo(beid, order));
    }

    /**
     * 事件处置-详情查看-结案信息
     */
    @GetMapping(value = "summary")
    @ApiOperation("事件处置-详情查看-结案信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true)})
    public RespMsg<Map<String, Object>> eventDepositSummary(Integer beid) {
        return RespMsg.ok(bestepService.getEventSummary(beid));
    }

    /**
     * 事件处置-详情查看-基本信息
     */
    @GetMapping(value = "basicInfo")
    @ApiOperation("事件处置-详情查看-基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true)})
    public RespMsg<BusieventBasicInfoDto> getEventBasicInfo(Integer beid) {
        // 如果是当前阶段读取该信息，则标识为已读。
        signRead(beid);
        return RespMsg.ok(depositService.getEventBasicInfo(beid));
    }

    private void signRead(Integer beid) {
        Bestep currentStep = bestepService
                .getOne(Condition.<Bestep>create().eq(Bestep.BEID, beid).orderByDesc(Bestep.ESID).last("limit 1"));
        if (currentStep != null && currentStep.getEsreadt() == null) {
            if (currentStep.getBepcdpt0() != null && currentStep.getBepcdpt0() == eventService
                    .getBedptByUserId(SecurityUtil.currentUserId()).getBedid()) {
                currentStep.setEsreadt(new Date());
                bestepService.updateById(currentStep);
            }
        }
    }

    /**
     * 事件处置-取消
     */
    @GetMapping(value = "cancel")
    @ApiOperation("事件处置-一级部门-取消")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "取消原因", required = true)})
    public RespMsg<Void> eventCancel(Integer beid, String reason) {
        try {
            depositService.cancel(SecurityUtil.currentUserId(), beid, reason);
        } catch (Exception e) {
            throw new ServiceException("一级部门取消事件操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-升级
     */
    @GetMapping(value = "upgrade")
    @ApiOperation("事件处置-一级部门-升级")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "升级原因", required = true)})
    public RespMsg<Void> upgrade(Integer beid, String reason) {
        try {
            depositService.upgrade(SecurityUtil.currentUserId(), beid, reason);
        } catch (Exception e) {
            throw new ServiceException("一级部门升级事件操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-升级
     */
    @GetMapping(value = "upgrade1")
    @ApiOperation("事件处置-一级部门-升级")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "升级原因", required = true)})
    public RespMsg<Void> upgrade1(Integer beid, String reason) {
        Integer curid = SecurityUtil.currentUserId();
        if (!EventConst.FIRST_LEVEL_DEP.equals(eventService.getBedptByUserId(curid).getBdtype())) {
            throw new ServiceException("非一级部门无法升级事件！");
        }
        Busievent busievent = busieventService
                .getOne(Condition.<Busievent>create().eq(Busievent.BEID, beid).select(Busievent.BEID, Busievent.BESTAT));
        if (busievent != null
                && !(EventEnum.UNHANDLE_STAT.getValue().equals(busievent.getBestat()))
                || EventEnum.FIRST_LEVEL_ALLOCATING_STAT.getValue().equals(busievent.getBestat())) {
            throw new ServiceException("非待处理事件无法升级！");
        }
        try {
            depositService.upgrade(curid, beid, reason);
        } catch (Exception e) {
            throw new ServiceException("一级部门升级事件操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-应急系统接收并处理
     */
    @Authorize(SecurityConstant.FORBIDDEN)
    @GetMapping(value = "emergency/receive")
    @ApiOperation(value = "事件处置-应急系统接受并处理", notes = "禁止外部访问")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true)})
    public RespMsg<Void> emergencyReceive(Integer beid) {
        try {
            depositService.emergencyReceive(beid);
        } catch (Exception e) {
            throw new ServiceException("应急系统接受并处理操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-应急系统拒绝处理
     */
    @Authorize(SecurityConstant.FORBIDDEN)
    @GetMapping(value = "emergency/refuse")
    @ApiOperation(value = "事件处置-应急系统拒绝处理", notes = "禁止外部访问")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "应急系统拒绝原因", required = true)})
    public RespMsg<Void> emergencyRefuse(Integer beid, String reason) {
        try {
            depositService.emergencyRefuse(beid, reason);
        } catch (Exception e) {
            throw new ServiceException("应急系统拒绝处理操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-应急处理完成
     */
    @Authorize(SecurityConstant.FORBIDDEN)
    @GetMapping(value = "emergency/finish")
    @ApiOperation(value = "事件处置-应急处理完成", notes = "禁止外部访问")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "应急完成原因", required = true)})
    public RespMsg<Void> emergencyFinish(Integer beid, String reason) {
        try {
            depositService.emergencyFinish(beid, reason);
        } catch (Exception e) {
            throw new ServiceException("应急系统处理完成操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-一级复查
     */
    @GetMapping(value = "first/recheck")
    @ApiOperation("事件处置-一级部门-复查")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "复查原因", required = true)})
    public RespMsg<Void> recheck(Integer beid, String reason) {
        try {
            depositService.firstRecheck(beid, reason);
        } catch (Exception e) {
            throw new ServiceException("一级部门复查事件操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-关闭
     */
    @GetMapping(value = "close")
    @ApiOperation("事件处置-一级部门-关闭")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "关闭原因", required = true)})
    public RespMsg<Void> close(Integer beid, String reason) {
        try {
            depositService.close(SecurityUtil.currentUserId(), beid, reason);
        } catch (Exception e) {
            throw new ServiceException("事件关闭操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-拒绝
     */
    @GetMapping(value = "second/refuse")
    @ApiOperation("事件处置-二级部门-拒绝")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "拒绝原因", required = true)})
    public RespMsg<Void> secondRefuse(Integer beid, String reason) {
        try {
            depositService.refuse(beid, reason, EventConst.EJTJ);
        } catch (Exception e) {
            throw new ServiceException("事件拒绝操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-自处置
     */
    @GetMapping(value = "second/dispose")
    @ApiOperation("事件处置-二级部门-自处置")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "处理内容", required = true)})
    public RespMsg<Void> secondDispose(@RequestParam Integer beid, @RequestParam String reason) {
        try {
            depositService.deal(beid, reason, EventConst.ZCL);
        } catch (Exception e) {
            throw new ServiceException("事件拒绝操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-驳回
     */
    @GetMapping(value = "turnback")
    @ApiOperation("事件处置-二级部门-驳回")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "驳回原因", required = true)})
    public RespMsg<Void> turnback(Integer beid, String reason) {
        try {
            depositService.turnback(beid, reason);
        } catch (Exception e) {
            throw new ServiceException("事件驳回操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-拒绝
     */
    @GetMapping(value = "service/refuse")
    @ApiOperation("事件处置-业务部门-拒绝")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "拒绝原因", required = true)})
    public RespMsg<Void> serviceRefuse(Integer beid, String reason) {
        try {
            depositService.refuse(beid, reason, EventConst.YWBMTJ);
        } catch (Exception e) {
            throw new ServiceException("业务部门拒绝事件操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-受理
     */
    @GetMapping(value = "service/receive")
    @ApiOperation("事件处置-业务部门-受理")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true)})
    public RespMsg<Void> serviceReceive(Integer beid) {
        try {
            depositService.serviceReceive(beid);
        } catch (Exception e) {
            throw new ServiceException("业务部门受理事件操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-处理完成
     */
    @GetMapping(value = "service/finish")
    @ApiOperation("事件处置-业务部门-处理完成")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "完成原因", required = true)})
    public RespMsg<Void> serviceFinish(Integer beid, String reason) {
        try {
            depositService.serviceFinish(beid, reason);
        } catch (Exception e) {
            throw new ServiceException("业务部门处理事件完成操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-二级通过（协查）
     */
    @GetMapping(value = "service/pass")
    @ApiOperation("事件处置-二级部门-通过")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true)})
    public RespMsg<Void> pass(Integer beid) {
        try {
            depositService.pass(beid);
        } catch (Exception e) {
            throw new ServiceException("二级部门事件核查通过操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-二级完成（核查通过）
     */
    @GetMapping(value = "second/finish")
    @ApiOperation("事件处置-二级部门-完成")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "完成原因", required = true)})
    public RespMsg<Void> secondFinish(Integer beid, String reason) {
        try {
            depositService.secondFinish(beid, reason);
        } catch (Exception e) {
            throw new ServiceException("二级部门事件核查通过操作失败！", e);
        }
        return RespMsg.ok();
    }

    /**
     * 事件处置-二级复查
     */
    @GetMapping(value = "second/recheck")
    @ApiOperation("事件处置-二级部门-复查")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "复查原因", required = true)})
    public RespMsg<Void> secondRecheck(Integer beid, String reason) {
        try {
            depositService.secondRecheck(beid, reason);
        } catch (Exception e) {
            throw new ServiceException("二级部门事件复查操作失败！", e);
        }
        return RespMsg.ok();
    }

}
