package com.digitalchina.event.mid.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.consts.EventConst;
import com.digitalchina.event.dto.EmergencyInfoDto;
import com.digitalchina.event.dto.eventdeposit.BusieventBasicInfoDto;
import com.digitalchina.event.dto.eventdeposit.BusieventDto;
import com.digitalchina.event.mid.entity.*;
import com.digitalchina.event.mid.mapper.AdmdivMapper;
import com.digitalchina.event.mid.mapper.BedeptMapper;
import com.digitalchina.event.mid.mapper.BusieventMapper;
import com.digitalchina.event.mid.service.*;
import com.digitalchina.event.remoteservice.RemoteCitizensService;
import com.digitalchina.event.remoteservice.RemoteEmergencyService;
import com.digitalchina.event.remoteservice.RemoteWarnService;
import com.digitalchina.event.utils.GPSFormatUtils;
import com.digitalchina.event.utils.MapUtil;
import com.digitalchina.modules.constant.TransConstant;
import com.digitalchina.modules.constant.enums.BeFromEnum;
import com.digitalchina.modules.constant.enums.EventEnum;
import com.digitalchina.modules.security.SecurityUtil;
import com.digitalchina.modules.security.UserDetail;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventDepositServiceImpl extends ServiceImpl<BusieventMapper, Busievent> implements EventDepositService {

    private static final Logger log = LoggerFactory.getLogger(EventDepositServiceImpl.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private BusieventService busieventService;
    @Autowired
    private CoopeventService coopeventService;
    @Autowired
    private BestepService bestepService;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private BedeptService bedeptService;
    @Autowired
    private EventService eventService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private RemoteWarnService remoteWarnService;
    @Autowired
    private RemoteCitizensService remoteCitizensService;
    @Autowired
    private RemoteEmergencyService remoteEmergencyService;
    @Autowired
    private DeptUserService deptUserService;
    @Autowired
    private BusieventMapper busieventMapper;

    @Autowired
    private AdmdivMapper admdivMapper;

    @Autowired
    private BedeptMapper bedeptMapper;

    // 不可直接内部调用，开了事物不走代理，导致事物不回滚
    @Autowired
    private EventDepositService eventDepositService;

    @Override
    public List getFirstLevelEventCount() {
        // 待处理（一级未指派）
        Map undoTask = MapUtil.getHashMap("title", EventEnum.UNHANDLE.getValue(), "value", busieventService
                .count(Condition.<Busievent>create().eq(Busievent.BESTAT, EventEnum.UNHANDLE_STAT.getValue())));
        // 转派中（包括一级指派和二级指派、业务部门拒绝）
        Map alloTask = MapUtil.getHashMap("title", EventEnum.ALLOCATING.getValue(), "value",
                busieventService.count(Condition.<Busievent>create().in(Busievent.BESTAT,
                        new Object[]{EventEnum.FIRST_LEVEL_ALLOCATING_STAT.getValue(),
                                EventEnum.SECOND_LEVEL_ALLOCATING_STAT.getValue(),
                                EventEnum.SERVICE_REFUSE_STAT.getValue()})));
        // 处理中（业务部门受理）
        Map handleTask = MapUtil.getHashMap("title", EventEnum.HANDLING.getValue(), "value",
                busieventService.count(Condition.<Busievent>create().in(Busievent.BESTAT,
                        new Object[]{EventEnum.HANDLING_STAT.getValue(), EventEnum.FIRST_TURNBACK_STAT.getValue(),
                                EventEnum.SECOND_TURNBACK_STAT.getValue(),
                                EventEnum.SECOND_LEVEL_CHECK_STAT.getValue()})));
        // 待审批（协查件相关）
        int unAuditCount = coopeventService
                .count(Condition.<Coopevent>create().eq(Coopevent.CESTAT, EventEnum.FIRST_AUDIT_COOP.getValue()));
        Map unAuditTask = MapUtil.getHashMap("title", EventEnum.UNAUDIT.getValue(), "value", unAuditCount);
        // 待核查（二级审批完成）
        Map unCheckTask = MapUtil.getHashMap("title", EventEnum.UNCHECKED.getValue(), "value", busieventService.count(
                Condition.<Busievent>create().eq(Busievent.BESTAT, EventEnum.FIRST_LEVEL_CHECK_STAT.getValue())));
        // 拒绝件（二级拒绝）
        Map refuseTask = MapUtil.getHashMap("title", EventEnum.REFUSE.getValue(), "value", busieventService
                .count(Condition.<Busievent>create().eq(Busievent.BESTAT, EventEnum.SECOND_REFUSE_STAT.getValue())));
        // 上报应急系统
        Map upgradeTask = MapUtil.getHashMap("title", EventEnum.UPGRADE.getValue(), "value", busieventService
                .count(Condition.<Busievent>create().eq(Busievent.BESTAT, EventEnum.EMERGENCY_STAT.getValue())));
        // 取消（待处理可以取消）
        Map cancelTask = MapUtil.getHashMap("title", EventEnum.CANCEL.getValue(), "value", busieventService
                .count(Condition.<Busievent>create().eq(Busievent.BESTAT, EventEnum.CANCEL_STAT.getValue())));
        // 一级事件列表
        List resultList = CollectionUtil.toList(undoTask, alloTask, handleTask, unAuditTask, unCheckTask, refuseTask,
                upgradeTask, cancelTask);
        return resultList;
    }

    @Override
    public List getHomeFirstLevelEventCount(String keyword, Integer[] adids, Integer[] besrcdpts, Integer[] etbhs) {
        List<Integer> allodpts = new ArrayList<>();
        List<Integer> recdpts = new ArrayList<>();

        Integer[] quadids = adids;
        for (Integer adid:quadids) {
            String adidString = admdivMapper.getChildList(adid);
            if(adidString != null){
                adids=ArrayUtils.addAll(adids, (Integer[]) ConvertUtils.convert(adidString.split(","), Integer.class));
            }
        }
        adids = distinct(adids);
        Integer[] bepts = besrcdpts;
        for (Integer bedid:bepts) {
            String bedidString = bedeptMapper.getChildList(bedid);
            if(bedidString != null){
                besrcdpts=ArrayUtils.addAll(besrcdpts, (Integer[]) ConvertUtils.convert(bedidString.split(","), Integer.class));
            }
        }
        besrcdpts = distinct(besrcdpts);
        if (ObjectUtil.isNotEmpty(besrcdpts)) {
            List<Integer> ids = new ArrayList<>(Arrays.asList(besrcdpts));
            Collection<Bedept> bedepts = bedeptService.listByIds(ids);
            if (ObjectUtil.isNotEmpty(bedepts)) {
                // 是否全选
                boolean isCheckAll = bedepts.stream().anyMatch(q -> EventConst.FIRST_LEVEL_DEP.equals(q.getBdtype()));

                if (!isCheckAll) {
                    // 全勾的二级部门
                    allodpts = bedepts.stream().filter(q -> EventConst.SECOND_LEVEL_DEP.equals(q.getBdtype()))
                            .map(Bedept::getBedid).collect(Collectors.toList());

                    final List<Integer> allodptsTemp = allodpts;
                    // 非全勾的二级部门的三级部门
                    recdpts = bedepts.stream().filter(q -> {
                        if (!EventConst.SERVICE_LEVEL_DEP.equals(q.getBdtype())) {
                            return false;
                        }
                        if (null != allodptsTemp && allodptsTemp.contains(q.getBdpntid())) {
                            return false;
                        }
                        return true;
                    }).map(Bedept::getBedid).collect(Collectors.toList());
                }
            }
        }

        // 待处理（一级未指派）
        Map undoTask = MapUtil.getHashMap("title", EventEnum.UNHANDLE.getValue(), "value",
                busieventMapper.getFirstLevelEventCount(keyword, adids, besrcdpts, etbhs, allodpts, recdpts,
                        CollUtil.newArrayList(EventEnum.UNHANDLE_STAT.getValue())));

        // 转派中（包括一级指派和二级指派、业务部门拒绝）
        Map alloTask = MapUtil.getHashMap("title", EventEnum.ALLOCATING.getValue(), "value",
                busieventMapper.getFirstLevelEventCount(keyword, adids, besrcdpts, etbhs, allodpts, recdpts,
                        CollUtil.newArrayList(EventEnum.FIRST_LEVEL_ALLOCATING_STAT.getValue(),
                                EventEnum.SECOND_LEVEL_ALLOCATING_STAT.getValue(),
                                EventEnum.SERVICE_REFUSE_STAT.getValue())));
        // 处理中（业务部门受理）
        Map handleTask = MapUtil.getHashMap("title", EventEnum.HANDLING.getValue(), "value",
                busieventMapper.getFirstLevelEventCount(keyword, adids, besrcdpts, etbhs, allodpts, recdpts,
                        CollUtil.newArrayList(EventEnum.HANDLING_STAT.getValue(),
                                EventEnum.FIRST_TURNBACK_STAT.getValue(),
                                EventEnum.SECOND_TURNBACK_STAT.getValue(),
                                EventEnum.SECOND_LEVEL_CHECK_STAT.getValue())));
//		// 待审批（协查件相关）
//		int unAuditCount = coopeventService
//				.count(Condition.<Coopevent>create().eq(Coopevent.CESTAT, EventEnum.FIRST_AUDIT_COOP.getValue()));
//		Map unAuditTask = MapUtil.getHashMap("title", EventEnum.UNAUDIT.getValue(), "value", unAuditCount);
        // 待核查（二级审批完成）
        Map unCheckTask = MapUtil.getHashMap("title", EventEnum.UNCHECKED.getValue(), "value",
                busieventMapper.getFirstLevelEventCount(keyword, adids, besrcdpts, etbhs, allodpts, recdpts,
                        CollUtil.newArrayList(EventEnum.FIRST_LEVEL_CHECK_STAT.getValue())));
        // 拒绝件（二级拒绝）
        Map refuseTask = MapUtil.getHashMap("title", EventEnum.REFUSE.getValue(), "value",
                busieventMapper.getFirstLevelEventCount(keyword, adids, besrcdpts, etbhs, allodpts, recdpts,
                        CollUtil.newArrayList(EventEnum.SECOND_REFUSE_STAT.getValue())));
        // 上报应急系统
        Map upgradeTask = MapUtil.getHashMap("title", EventEnum.UPGRADE.getValue(), "value",
                busieventMapper.getFirstLevelEventCount(keyword, adids, besrcdpts, etbhs, allodpts, recdpts,
                        CollUtil.newArrayList(EventEnum.EMERGENCY_STAT.getValue())));
        // 取消（待处理可以取消）
        Map cancelTask = MapUtil.getHashMap("title", EventEnum.CANCEL.getValue(), "value",
                busieventMapper.getFirstLevelEventCount(keyword, adids, besrcdpts, etbhs, allodpts, recdpts,
                        CollUtil.newArrayList(EventEnum.CANCEL_STAT.getValue())));
        // 已关闭
        Map finishTask = MapUtil.getHashMap("title", EventEnum.FINISH.getValue(), "value",
                busieventMapper.getFirstLevelEventCount(keyword, adids, besrcdpts, etbhs, allodpts, recdpts,
                        CollUtil.newArrayList(EventEnum.FINISH_STAT.getValue())));

        // 一级事件列表
        List resultList = CollectionUtil.toList(undoTask, alloTask, handleTask, /*unAuditTask,*/ unCheckTask, refuseTask,
                upgradeTask, cancelTask, finishTask);

        return resultList;
    }

    public static <T> T[] distinct(T[] array) {
        if(array==null || array.length==0){
            return array;
        }
        // 将数组中的元素循环遍历，放入集合中，并且去重
        List<T> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (!list.contains(array[i])) {
                list.add(array[i]);
            }
        }
        // 将去重后的list集合转换为数组，并且返回（如果有重复，数组的长度会减少，需要重新定义一个数组）
        T[] newInstance = (T[]) Array.newInstance(array[0].getClass(), list.size());
        T[] result = list.toArray(newInstance);
        return result;

    }

    @Override
    public List getSecondLevelEventCount(Integer bedid) {
        // 待处理（一级分拨完成，且转派的部门是bedid）
        Map undoTask = MapUtil.getHashMap("title", EventEnum.UNHANDLE.getValue(), "value",
                busieventService.count(Condition.<Busievent>create()
                        .eq(Busievent.BESTAT, EventEnum.FIRST_LEVEL_ALLOCATING_STAT.getValue())
                        .eq(Busievent.BEPCDPT0, bedid)));
        // 转派中（二级指派，且是由bedid的部门转派）
        Map alloTask = MapUtil.getHashMap("title", EventEnum.ALLOCATING.getValue(), "value",
                busieventService.count(Condition.<Busievent>create()
                        .eq(Busievent.BESTAT, EventEnum.SECOND_LEVEL_ALLOCATING_STAT.getValue())
                        .eq(Busievent.BESRCDPT, bedid)));
        // 处理中（业务部门受理、一级驳回、二级驳回，且关联bedid的部门）
        int secondHandingCount = busieventService.count(Condition.<Busievent>create()
                .in(Busievent.BESTAT, new Object[]{EventEnum.HANDLING_STAT.getValue(), EventEnum.FIRST_TURNBACK_STAT.getValue(), EventEnum.SECOND_TURNBACK_STAT.getValue()})
                .eq(Busievent.ALLODPT, bedid));
        Map handleTask = MapUtil.getHashMap("title", EventEnum.HANDLING.getValue(), "value", secondHandingCount);
        // 待审批（协查件相关）
        int unAuditCount = coopeventService.count(Condition.<Coopevent>create()
                .in(Coopevent.CESTAT, new Object[]{EventEnum.SECOND_AUDIT_COOP.getValue()})
                .eq(Coopevent.CEPCDPT0, bedid));
        Map unAuditTask = MapUtil.getHashMap("title", EventEnum.UNAUDIT.getValue(), "value", unAuditCount);
        // 被驳回（协查相关）
        int tunrnBackCount = coopeventService.count(Condition.<Coopevent>create()
                .eq(Coopevent.CESTAT, EventEnum.FIRST_REFUSE_COOP.getValue())
                .eq(Coopevent.CEPCDPT0, bedid));
        Map tunrnBackTask = MapUtil.getHashMap("title", EventEnum.TURNBACK.getValue(), "value", tunrnBackCount);
        // 待核查（业务部门完成）
        Map unCheckTask = MapUtil.getHashMap("title", EventEnum.UNCHECKED.getValue(), "value",
                busieventService.count(
                        Condition.<Busievent>create().eq(Busievent.BESTAT, EventEnum.SECOND_LEVEL_CHECK_STAT.getValue())
                                .eq(Busievent.BEPCDPT0, bedid)));
        // 拒绝件（业务部门拒绝）
        Map refuseTask = MapUtil.getHashMap("title", EventEnum.REFUSE.getValue(), "value",
                busieventService.count(Condition.<Busievent>create()
                        .eq(Busievent.BESTAT, EventEnum.SERVICE_REFUSE_STAT.getValue())
                        .eq(Busievent.BEPCDPT0, bedid)));
        // 完成件（二级部门完成）
        int finishCount = busieventService.count(Condition.<Busievent>create()
                .in(Busievent.BESTAT, new Object[]{EventEnum.FIRST_LEVEL_CHECK_STAT.getValue(), EventEnum.FINISH_STAT.getValue()})
                .eq(Busievent.ALLODPT, bedid));
        Map finishTask = MapUtil.getHashMap("title", EventEnum.FINISH.getValue(), "value", finishCount);
        // 二级事件列表
        List resultList = CollectionUtil.toList(undoTask, alloTask, handleTask, unAuditTask, tunrnBackTask, unCheckTask, refuseTask, finishTask);
        return resultList;
    }

    @Override
    public List getServiceLevelEventCount(Integer bedid) {
        // 待处理（一级未指派）
        Map undoTask = MapUtil.getHashMap("title", EventEnum.UNHANDLE.getValue(), "value",
                busieventService.count(Condition.<Busievent>create()
                        .eq(Busievent.BESTAT, EventEnum.SECOND_LEVEL_ALLOCATING_STAT.getValue())
                        .eq(Busievent.BEPCDPT0, bedid)));
        // 处理中（业务部门受理）
        Map handleTask = MapUtil.getHashMap("title", EventEnum.HANDLING.getValue(), "value",
                busieventService.count(Condition.<Busievent>create()
                        .eq(Busievent.BESTAT, EventEnum.HANDLING_STAT.getValue())
                        .eq(Busievent.BEPCDPT0, bedid)));
        // 协查件（协查相关：发起件 + 协办件）
        int createCount = coopeventService.count(Condition.<Coopevent>create()
                .eq(Coopevent.CESRCDPT, bedid)
                .ne(Coopevent.CESTAT, EventEnum.SECOND_REFUSE_COOP.getValue()));
        int coopCount = coopeventService.count(Condition.<Coopevent>create()
                .eq(Coopevent.CPBEDID, bedid)
                .eq(Coopevent.CESTAT, EventEnum.HANDLE_COOP.getValue())
                .isNull(Coopevent.CPFBINFO));
        Map coopTask = MapUtil.getHashMap("title", EventEnum.COOPERATE.getValue(), "value", createCount + coopCount);
        // 被驳回（协查相关）
        int tunrnBackCount = coopeventService.count(Condition.<Coopevent>create()
                .eq(Coopevent.CESTAT, EventEnum.SECOND_REFUSE_COOP.getValue())
                .eq(Coopevent.CESRCDPT, bedid));
        Map tunrnBackTask = MapUtil.getHashMap("title", EventEnum.TURNBACK.getValue(), "value", tunrnBackCount);
        // 复查件（一级、二级复查）
        Map refuseTask = MapUtil.getHashMap("title", EventEnum.RECHECK.getValue(), "value",
                busieventService.count(Condition
                        .<Busievent>create().in(Busievent.BESTAT, new Object[]{
                                EventEnum.FIRST_TURNBACK_STAT.getValue(), EventEnum.SECOND_TURNBACK_STAT.getValue(),})
                        .eq(Busievent.RECDPT, bedid)));
        // 完成件（一级核查、二级核查、已完成）
        int serviceFinishCount = busieventService.count(Condition.<Busievent>create()
                .in(Busievent.BESTAT,
                        new Object[]{EventEnum.SECOND_LEVEL_CHECK_STAT.getValue(),
                                EventEnum.FIRST_LEVEL_CHECK_STAT.getValue(), EventEnum.FINISH_STAT.getValue()})
                .eq(Busievent.RECDPT, bedid));
        Map finishTask = MapUtil.getHashMap("title", EventEnum.FINISH.getValue(), "value", serviceFinishCount);
        // 业务事件列表
        List resultList = CollectionUtil.toList(undoTask, handleTask, coopTask, tunrnBackTask, refuseTask, finishTask);
        return resultList;
    }

    @Override
    public IPage<BusieventDto> getFirstLevelEventList(String bestat, Integer efbh, Integer etbh, String benm,
                                                      String becnt, Integer size, Integer current) {
        IPage<Busievent> page = new Page<>(current, size);
        if (bestat.equals("待审批")) {
            IPage<BusieventDto> coopList = getFirstCoopEventList(efbh, etbh, benm, becnt, size, current);
            return coopList;
        }
        IPage<BusieventDto> taskList = baseMapper.getFirstLevelEventList(page, bestat, efbh, etbh, benm, becnt);
        addSeqForBusieventDto(taskList, size, current);
        return taskList;
    }

    @Override
    public IPage<BusieventDto> getSecondLevelEventList(String bestat, Integer efbh, Integer etbh, String benm,
                                                       String becnt, Integer size, Integer current) {
        IPage<Busievent> page = new Page<>(current, size);
        Bedept bedept = eventService.getBedptByUserId(SecurityUtil.currentUserId());
        if (!EventConst.SECOND_LEVEL_DEP.equals(bedept.getBdtype())) {
            throw new ServiceException("当前用户非二级部门用户，无法获取事件列表");
        }
        Integer bedid = bedept.getBedid();
        if (bestat.equals("待审批") || bestat.equals("被驳回")) {
            IPage<BusieventDto> taskList = baseMapper.getSecondCoopEventList(page, bedid, bestat, efbh, etbh, benm, becnt);
            addSeqForBusieventDto(taskList, size, current);
            return taskList;
        } else {
            IPage<BusieventDto> taskList = baseMapper.getSecondLevelEventList(page, bedid, bestat, efbh, etbh, benm, becnt);
            addSeqForBusieventDto(taskList, size, current);
            return taskList;
        }
    }

    @Override
    public IPage<BusieventDto> getServiceLevelEventList(String bestat, Integer efbh, Integer etbh, String benm,
                                                        String becnt, Integer size, Integer current) {
        IPage<Busievent> page = new Page<>(current, size);
        Bedept bedept = eventService.getBedptByUserId(SecurityUtil.currentUserId());
        if (!EventConst.SERVICE_LEVEL_DEP.equals(bedept.getBdtype())) {
            throw new ServiceException("当前用户非业务部门用户，无法获取事件列表");
        }
        Integer bedid = bedept.getBedid();
        if (bestat.equals("发起件") || bestat.equals("协办件") || bestat.equals("被驳回")) {
            IPage<BusieventDto> coopList = getServiceCoopEventList(bedid, bestat, efbh, etbh, benm, becnt, size,
                    current);
            return coopList;
        }
        IPage<BusieventDto> taskList = baseMapper.getServiceLevelEventList(page, bedid, bestat, efbh, etbh, benm,
                becnt);
        addSeqForBusieventDto(taskList, size, current);
        return taskList;
    }

    /**
     * 普通事件工作流处理
     *
     * @param beid      普通事件id
     * @param handleStr 操作
     * @param bepcdpt0  指定承办部门
     * @param eventEnum 事件状态、描述枚举
     * @param esoperPre 事件步骤描述头部
     * @param esoperSuf 事件步骤描述尾部
     * @param reason    步骤原因
     */
    @Override
    @Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public void handleBevent(Integer beid, String handleStr, Integer bepcdpt0, EventEnum eventEnum, String esoperPre,
                             String esoperSuf, String reason) {

        // 1）获取指定事件
        Busievent busievent = busieventService
                .getOne(Condition.<Busievent>create().eq(Busievent.BEID, beid).select(Busievent.BEID,
                        Busievent.PROC_INST_ID, Busievent.BESTAT, Busievent.CRDT, Busievent.EFBH, Busievent.BEPCDPT0));
        if (busievent.getBestat().toString().equals(eventEnum.getValue()) && !EventConst.YJJS.equals(handleStr)) {
            throw new ServiceException(
                    String.format("该事件已经处理！[事件id：%s，事件名称：%s]", busievent.getBeid(), busievent.getBenm()));
        }

        // 2）获取事件对应节点的位置，并由当前用户认领、完成当前任务。
        Task task = taskService.createTaskQuery().processInstanceId(busievent.getProcInstId()).singleResult();
        if (log.isDebugEnabled()) {
            log.debug("当前节点: " + task.getName());
        }
        Optional.ofNullable(task).orElseThrow(() -> new ServiceException(String.format("当前事件已结束！事件id:%s", beid)));
        // 暂时不用认领
        // taskService.claim(task.getId(),
        // SecurityUtil.currentUserId().toString());

        String nextTaskParam = EventConst.YJFK.equals(handleStr) ? null : handleStr;
        taskService.complete(task.getId(), MapUtil.getHashMap("handle", nextTaskParam));

        // 3）新增、修改事件相关的表
        insertOrUpdateRelatedTable(task, busievent, handleStr, bepcdpt0, eventEnum, esoperPre, esoperSuf, reason);
    }

    /**
     * 协查事件工作流处理
     *
     * @param ceid      协查事件id
     * @param handleStr 操作
     * @param cedid     指定承办部门
     * @param eventEnum 事件状态、描述枚举
     * @param reason    步骤原因
     */
    @Override
    @Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public void handleCevent(Integer ceid, String handleStr, String proc, Integer cedid, EventEnum eventEnum,
                             String reason) {
        // 1）获取指定协查事件
        Coopevent coopevent = coopeventService.getOne(Condition.<Coopevent>create().eq(Coopevent.CEID, ceid));
        if (coopevent.getCestat() == eventEnum.getValue()) {
            throw new ServiceException(
                    String.format("该事件已经处理！[事件id：%s，事件名称：%s]", coopevent.getCeid(), coopevent.getCenm()));
        }

        // 2）获取事件对应节点的位置，并由当前用户认领、完成当前任务。
        Task task = taskService.createTaskQuery().processInstanceId(coopevent.getProcInstId()).singleResult();
        Optional.ofNullable(task).orElseThrow(() -> new ServiceException(String.format("当前事件已结束！事件id:%s", ceid)));
        // taskService.claim(task.getId(),
        // SecurityUtil.currentUserId().toString());
        if (reason != null) {
            taskService.addComment(task.getId(), null, reason);
        }
        taskService.complete(task.getId(), MapUtil.getHashMap("handle", handleStr));
        String[] originProc = coopevent.getCeproc();
        String[] newPorcArr = new String[]{proc};
        String[] ceproc = originProc != null ? ArrayUtils.addAll(originProc, newPorcArr) : newPorcArr;
        coopevent.setCeproc(ceproc);
        coopevent.setCepcdpt0(cedid);
        coopevent.setModt(new Date());
        coopevent.setCestat((Integer) eventEnum.getValue());
        coopevent.setWfid(Integer.valueOf(task.getId()));
        setWhenCoopeventClose(coopevent, reason);
        coopeventService.updateById(coopevent);

    }

    private void setWhenCoopeventClose(Coopevent coopevent, String reason) {
        if (coopevent.getCestat() == EventEnum.FINISH_COOP.getValue()) {
            coopevent.setClsdt(coopevent.getModt());
        }
    }

    private void insertOrUpdateRelatedTable(Task task, Busievent busievent, String handleStr, Integer bepcdpt0,
                                            EventEnum eventEnum, String esoperPre, String esoperSuf, String reason) {
        Date now = new Date();
        // 1)保存工作流信息
        Workflow workflow = getWorkflow(task, eventEnum, now);

        // 2）指定事件进入下一阶段，需要修改事件：转入时间、事件状态、来源部门、指定承办部门
        busievent.setWfid(workflow.getWfid());
        busievent.setIndt(now);
        // 执行应急系统相关操作
        operateWhenEventInEmg(busievent, handleStr, now, reason);

        busievent.setBestat((Integer) eventEnum.getValue());
        // 新的来源部门=上一步的指定承办部门=最后承办部门，新的指定承办部门=传来的指定承办部门
        // "besrcdpt" IS '来源部门'; "bepcdpt" IS '最后承办部门'; "bepcdpt0" IS '指定承办部门';
        busievent.setBesrcdpt(busievent.getBepcdpt0());
        busievent.setBepcdpt(busievent.getBepcdpt0());
        busievent.setBepcdpt0(bepcdpt0);
        busievent.setModt(now);
        // 设置经派机构、受理部门
        setAlloAndReceiveDept(busievent, handleStr, bepcdpt0);

        busieventService.updateById(busievent);

        // 3）新增一条业务事件阶段记录
        Bestep bestep = new Bestep();
        bestep.setWfid(workflow.getWfid());
        bestep.setBeid(busievent.getBeid());
        bestep.setTskey(task.getId());
        bestep.setEsrcvdt(now);
        bestep.setEstype(0);
        // 阶段描述如：转派，2019-09-09 09:09:09，一级指挥中心转派给二级指挥中心。
        String originDeptName = "应急辅助系统";
        // 应急系统回调是没有用户身份的
        if (null != SecurityUtil.currentUserId()) {
            originDeptName = eventService.getBedptByUserId(SecurityUtil.currentUserId()).getBdnm();
        } else {
            // 拿一个一级用户给它
            Bedept bedept = bedeptService
                    .getOne(Condition.<Bedept>create().eq(Bedept.BDTYPE, EventConst.FIRST_LEVEL_DEP));
            Integer firstUserId = deptUserService.list(Condition.<DeptUser>create().eq(DeptUser.DID, bedept.getBedid()))
                    .get(0).getUid();
            UserDetail userDetail = new UserDetail();
            userDetail.setId(firstUserId);
            SecurityUtil.setCurrentUser(userDetail);
        }
        // 设置阶段中的上游来源人、上游来源部门、当前操作人、当前操作部门、指定承办部门
        setBestepDeptAndMan(bestep, busievent.getBeid(), bepcdpt0, task);

        String esoper = String.format("%s，%s，%s%s。", esoperPre, DateUtil.formatDateTime(now), originDeptName,
                esoperSuf);

        bestep.setEsoper(esoper);
        bestep.setEsopn(reason);
        bestep.setCrdt(now);
        bestep.setModt(now);
        bestepService.save(bestep);

        // 4）如果已经指定承办部门，则为任务指定承办部门
        // operateWhenEventAssignGroup(busievent.getProcInstId(), bepcdpt0);

        // 5）如果事件关闭，则进行其他处理。
        operateWhenEventClose(handleStr, busievent, workflow, bestep, originDeptName);
    }

    private void setAlloAndReceiveDept(Busievent busievent, String handleStr, Integer bepcdpt0) {
        if (handleStr == null) {
            return;
        }
        if (handleStr.equals(EventConst.FBGEJ)) {
            busievent.setAllodpt(bepcdpt0);
            busievent.setAllodt(new Date());
        } else if (handleStr.equals(EventConst.FBGYWBM)) {
            busievent.setRecdpt(bepcdpt0);
            busievent.setRecdt(new Date());
        } else if (handleStr.equals(EventConst.ZCL)) {
            busievent.setRecdpt(bepcdpt0);
            busievent.setRecdt(new Date());
        }
    }

    private Workflow getWorkflow(Task task, EventEnum eventEnum, Date now) {
        Workflow workflow = new Workflow();
        workflow.setWfnm(eventEnum.getDesp());
        workflow.setWfkey(task.getProcessDefinitionId());
        workflow.setRev(1);
        workflow.setCrdt(now);
        workflow.setModt(now);
        workflowService.save(workflow);
        return workflow;
    }

    private void operateWhenEventAssignGroup(String procInstId, Integer bepcdpt0) {
        if (bepcdpt0 != null) {
            Task task = taskService.createTaskQuery().processInstanceId(procInstId).singleResult();
            Optional.ofNullable(task).ifPresent(t -> taskService.addCandidateGroup(t.getId(), bepcdpt0.toString()));
        }
    }

    private void setBestepDeptAndMan(Bestep bestep, Integer beid, Integer bepcdpt0, Task task) {
        // 上游来源人、上游来源部门等于上一阶段的操作人、操作部门；
        Bestep lastStep = bestepService
                .getOne(Condition.<Bestep>create().eq(Bestep.BEID, beid).orderByDesc(Bestep.ESID).last("limit 1"));
        if (lastStep != null) {
            bestep.setEsupman(lastStep.getEsopman());
            bestep.setEsupdept(lastStep.getEsopdept());
        }
        // 操作人、操作部门等于当前操作人、操作部门，本阶段中指定的指定承办部门（指定接下来由哪个部门办理）
        bestep.setEsopman(SecurityUtil.currentUserId());
        bestep.setEsopdept(eventService.getBedptByUserId(SecurityUtil.currentUserId()).getBedid());
        bestep.setBepcdpt0(bepcdpt0);
    }

    private void operateWhenEventInEmg(Busievent busievent, String handleStr, Date now, String reason) {

        if (EventConst.SJSJ.equals(handleStr)) {
            // 事件升级
            EmergencyInfoDto emergencyInfoDto = busieventService.getEmergencyInfo(busievent.getBeid());
            if (null == emergencyInfoDto) {
                log.error("事件升级时,推送至应急辅助系统失败！事件id:{}！该事件关联查询不到！", busievent.getBeid());
                throw new ServiceException(String.format("事件升级时,推送至应急辅助系统失败！事件id:%s", busievent.getBeid()));
            }
            emergencyInfoDto.setRpdt(now);
            emergencyInfoDto.setReason(reason);
            List<Attachment> attachmentList = attachmentService.list(Condition.<Attachment>create()
                    .eq(Attachment.BEID, busievent.getBeid()).orderByAsc(Attachment.FILETYPE, Attachment.CRDT));
            emergencyInfoDto.setAttachmentList(attachmentList);

            RespMsg respMsg = remoteEmergencyService.pushEvent(emergencyInfoDto);
            if (!respMsg.getCode().equals(HttpStatus.HTTP_OK)) {
                log.error("事件升级时,推送至应急辅助系统失败！事件id:{}！推送失败！", busievent.getBeid());
                throw new ServiceException(String.format("事件升级时,推送至应急辅助系统失败！事件id:%s", busievent.getBeid()));
            }
        }
        if (EventConst.YJJS.equals(handleStr)) {
            // 应急接收
            busievent.setUpemgdt(now);
            busievent.setEmgdowndt(now);
        }
        if (EventConst.YJJJ.equals(handleStr)) {
            // 应急拒绝
            busievent.setEmgdowndt(now);
        }
        if (EventConst.YJFK.equals(handleStr)) {
            // 应急反馈
            busievent.setEmgdowndt(now);
        }
    }

    private void operateWhenEventClose(String handleStr, Busievent busievent, Workflow workflow, Bestep bestep,
                                       String originDeptName) {
        // 如果事件关闭、取消时，记录事件处理时长。
        if (handleStr != null && (handleStr.equals(EventConst.WXCL) || handleStr.equals(EventConst.YJHCTG)
                || handleStr.equals(EventConst.YJFK))) {
            Date closeDate = new Date();
            // 处理时长，按分钟记，精确到小数点一位，如1.5h。
            Long alldura = DateUtil.between(busievent.getCrdt(), closeDate, DateUnit.MINUTE);
            busievent.setAlldura(Integer.valueOf((alldura == 0) ? "1" : alldura.toString()));
            busievent.setClsdt(closeDate);
            busievent.setModt(closeDate);
            busieventService.updateById(busievent);

            workflow.setWfid(null);
            workflow.setWfnm("End");
            workflowService.save(workflow);

            bestep.setEsid(null);
            bestep.setWfid(workflow.getWfid());
            String closeEsoper = String.format("%s，%s，%s。", "关闭", DateUtil.formatDateTime(closeDate),
                    originDeptName + "关闭事件");
            bestep.setEsoper(closeEsoper);
            bestep.setEsopn(null);
            bestepService.save(bestep);

            // 如果是来源是监测系统，则回调监测预警的接口
            if (BeFromEnum.CITY_WARN.getCode().equals(busievent.getEfbh())) {
                try {
                    remoteWarnService.handleResult(closeEsoper, busievent.getBeid(), busievent.getBestat());
                } catch (Exception e) {
                    throw new ServiceException(String.format("回调监测预警接口异常！[content：%s，evid：%s，state：%s]", closeEsoper,
                            busievent.getBeid(), busievent.getBestat()));
                }
            }
            // 如果是来源是监测系统，则回调监测预警的接口
            if (BeFromEnum.CITY_ZENS.getCode().equals(busievent.getEfbh())) {
                try {
                    remoteCitizensService.handleResult(closeEsoper, busievent.getBeid());
                } catch (Exception e) {
                    throw new ServiceException(String.format("回调市民互动异常！[content：%s，evid：%s，state：%s]", closeEsoper,
                            busievent.getBeid(), busievent.getBestat()), e);
                }
            }
        }
    }

    @Override
    public void cancel(Integer curid, Integer beid, String reason) {
        if (eventService.getBedptByUserId(curid).getBdtype() != EventConst.FIRST_LEVEL_DEP) {
            throw new ServiceException("非一级部门无法取消事件。");
        }
        String esoperPrefix = "取消";
        String esoperSuffix = "取消事件";
        eventDepositService.handleBevent(beid, EventConst.WXCL, getFirstDeptId(), EventEnum.CANCEL_STAT, esoperPrefix,
                esoperSuffix, reason);
    }

    @Override
    public void upgrade(Integer curid, Integer beid, String reason) {
//        if (eventService.getBedptByUserId(curid).getBdtype() != EventConst.FIRST_LEVEL_DEP) {
//            throw new ServiceException("非一级部门无法升级事件。");
//        }
        String esoperPrefix = "升级";
        String esoperSuffix = "升级事件";
        eventDepositService.handleBevent(beid, EventConst.SJSJ, getFirstDeptId(), EventEnum.EMERGENCY_STAT,
                esoperPrefix, esoperSuffix, reason);
    }

    @Override
    public void firstRecheck(Integer beid, String reason) {
        Integer serviceDepId = getEventServiceDepid(beid);
        if (serviceDepId == null) {
            throw new ServiceException(String.format("无法找到处理该事件的业务实施部门。事件id:%s", beid));
        }
        String esoperPrefix = "核查";
        String esoperSuffix = "核查结果不合格，要求部门进行复查";
        eventDepositService.handleBevent(beid, EventConst.YJHCBTG, serviceDepId, EventEnum.FIRST_TURNBACK_STAT,
                esoperPrefix, esoperSuffix, reason);
    }

    @Override
    public void secondRecheck(Integer beid, String reason) {
        Integer serviceDepId = getEventServiceDepid(beid);
        if (serviceDepId == null) {
            throw new ServiceException(String.format("无法找到处理该事件的业务实施部门。事件id:%s", beid));
        }
        String esoperPrefix = "核查";
        String esoperSuffix = "核查结果不合格，要求部门进行复查";
        eventDepositService.handleBevent(beid, EventConst.EJHCBTG, serviceDepId, EventEnum.SECOND_TURNBACK_STAT,
                esoperPrefix, esoperSuffix, reason);
    }

    @Override
    public void close(Integer curid, Integer beid, String reason) {
        if (eventService.getBedptByUserId(curid).getBdtype() != EventConst.FIRST_LEVEL_DEP) {
            throw new ServiceException("非一级部门无法关闭事件。");
        }
        String esoperPrefix = "核查";
        String esoperSuffix = "核查结果合格";
        eventDepositService.handleBevent(beid, EventConst.YJHCTG, getFirstDeptId(), EventEnum.FINISH_STAT, esoperPrefix,
                esoperSuffix, reason);
    }

    @Override
    public void refuse(Integer beid, String reason, String handleStr) {
        String esoperPrefix = "拒绝";
        String esoperSuffix = "拒绝受理该事件";
        switch (handleStr) {
            case EventConst.EJTJ:
                eventDepositService.handleBevent(beid, handleStr, getFirstDeptId(), EventEnum.SECOND_REFUSE_STAT,
                        esoperPrefix, esoperSuffix, reason);
                break;
            case EventConst.YWBMTJ:
                Integer secondBedid = getEventSecondDepid(beid);
                Optional.ofNullable(secondBedid)
                        .orElseThrow(() -> new ServiceException(String.format("无法找到处理该事件的二级指挥中心。事件id:%s", beid)));
                eventDepositService.handleBevent(beid, handleStr, secondBedid, EventEnum.SERVICE_REFUSE_STAT, esoperPrefix,
                        esoperSuffix, reason);
                break;
            default:
                break;
        }
    }

    @Override
    public void pass(Integer beid) {
        String esoperPrefix = "二级审批协查";
        String esoperSuffix = "二级批准协查";
        eventDepositService.handleBevent(beid, EventConst.EJHCTG, null, EventEnum.FIRST_LEVEL_CHECK_STAT, esoperPrefix,
                esoperSuffix, null);
    }

    @Override
    public void turnback(Integer beid, String reason) {
        String esoperPrefix = "驳回";
        String esoperSuffix = "驳回协查申请";
        eventDepositService.handleBevent(beid, EventConst.EJHCBTG, null, EventEnum.HANDLING_STAT, esoperPrefix,
                esoperSuffix, reason);
    }

    @Override
    public void firstAllocate(Integer curid, Integer beid, Integer bepcdpt0, String reason) {
        if (!isFirstDept(curid)) {
            throw new ServiceException("当前用户非一级指挥中心下的用户，无法转派！");
        }
        if (!isSecondDept(bepcdpt0)) {
            throw new ServiceException("指定的转派部门非二级指挥中心，无法转派！");
        }
        String esoperPrefix = "转派";
        String esoperSuffix = "转派给" + eventService.getBedptByBedId(bepcdpt0).getBdnm();
        eventDepositService.handleBevent(beid, EventConst.FBGEJ, bepcdpt0, EventEnum.FIRST_LEVEL_ALLOCATING_STAT,
                esoperPrefix, esoperSuffix, reason);
    }

    private boolean isSecondDept(Integer bepcdpt0) {
        // 判断指派的部门是否为二级指挥中心
        if (eventService.getBedptByBedId(bepcdpt0).getBdtype() == EventConst.SECOND_LEVEL_DEP) {
            return true;
        }
        return false;
    }

    private boolean isFirstDept(Integer curid) {
        // 判断当前用户是否为一级指挥中心下的用户
        if (eventService.getBedptByUserId(curid).getBdtype() == EventConst.FIRST_LEVEL_DEP) {
            return true;
        }
        return false;
    }

    @Override
    public void secondAllocate(Integer curid, Integer beid, Integer bepcdpt0) {
        if (!isSecondDeptUser(curid)) {
            throw new ServiceException("当前用户非二级指挥中心下的用户，无法转派！");
        }
        if (!isServiceDept(bepcdpt0)) {
            throw new ServiceException("指定的转派部门非业务部门，无法转派！");
        }
        String esoperPrefix = "转派";
        String depName = getDeptNameByBedid(bepcdpt0);
        String esoperSuffix = "受理事件，转派到" + depName;
        eventDepositService.handleBevent(beid, EventConst.FBGYWBM, bepcdpt0, EventEnum.SECOND_LEVEL_ALLOCATING_STAT,
                esoperPrefix, esoperSuffix, null);
    }

    private boolean isServiceDept(Integer bepcdpt0) {
        // 判断指派的部门是否为业务部门
        if (eventService.getBedptByBedId(bepcdpt0).getBdtype() == EventConst.SERVICE_LEVEL_DEP) {
            return true;
        }
        return false;
    }

    private boolean isSecondDeptUser(Integer curid) {
        // 判断当前用户是否为一级指挥中心下的用户
        if (eventService.getBedptByUserId(curid).getBdtype() == EventConst.SECOND_LEVEL_DEP) {
            return true;
        }
        return false;
    }

    @Override
    public void serviceReceive(Integer beid) {
        String esoperPrefix = "受理";
        String esoperSuffix = "受理事件，已开始处理";
        eventDepositService.handleBevent(beid, EventConst.NBCL,
                eventService.getBedptByUserId(SecurityUtil.currentUserId()).getBedid(), EventEnum.HANDLING_STAT,
                esoperPrefix, esoperSuffix, null);
    }

    @Override
    public void serviceFinish(Integer beid, String reason) {
        String esoperPrefix = "完成";
        String esoperSuffix = "已处理完成事件，完成反馈";
        // 指派回给上级部门，即谁指派给你的，就指派回给谁
        Integer secondBedid = getEventSecondDepid(beid);
        Optional.ofNullable(secondBedid)
                .orElseThrow(() -> new ServiceException(String.format("无法找到处理该事件的二级指挥中心。事件id:%s", beid)));
        eventDepositService.handleBevent(beid, null, secondBedid, EventEnum.SECOND_LEVEL_CHECK_STAT, esoperPrefix,
                esoperSuffix, reason);
    }

    @Override
    public void secondFinish(Integer beid, String reason) {
        String esoperPrefix = "核查";
        String esoperSuffix = "核查结果合格，完成反馈";
        eventDepositService.handleBevent(beid, EventConst.EJHCTG, getFirstDeptId(), EventEnum.FIRST_LEVEL_CHECK_STAT,
                esoperPrefix, esoperSuffix, reason);
    }

    @Override
    public void emergencyRefuse(Integer beid, String reason) {
        String esoperPrefix = "应急拒绝";
        String esoperSuffix = "拒绝受理该事件";
        String handleStr = EventConst.YJJJ;
        eventDepositService.handleBevent(beid, handleStr, getFirstDeptId(), EventEnum.SECOND_REFUSE_STAT, esoperPrefix,
                esoperSuffix, reason);
    }

    @Override
    public void emergencyReceive(Integer beid) {
        // 这里状态没有变更还是升级件->升级件，不是重复处理 防重由应急系统处理，事件系统不限制
        String esoperPrefix = "应急接收";
        String esoperSuffix = "升级该事件为应急事件";
        eventDepositService.handleBevent(beid, EventConst.YJJS, getFirstDeptId(), EventEnum.EMERGENCY_STAT,
                esoperPrefix, esoperSuffix, null);
    }

    @Override
    public void emergencyFinish(Integer beid, String reason) {
        String esoperPrefix = "应急反馈";
        String esoperSuffix = "已处理，完成反馈";
        String handleStr = EventConst.YJFK;
        eventDepositService.handleBevent(beid, handleStr, getFirstDeptId(), EventEnum.FINISH_STAT, esoperPrefix,
                esoperSuffix, reason);
    }

    @Override
    public BusieventBasicInfoDto getEventBasicInfo(Integer beid) {
        BusieventBasicInfoDto busieventBasicInfoDto = baseMapper.getEventBasicInfo(beid);
        if (busieventBasicInfoDto != null && busieventBasicInfoDto.getBexy() != null) {
            busieventBasicInfoDto.setLon(GPSFormatUtils.DDtoDMS(busieventBasicInfoDto.getBexy().getLongitude()));
            busieventBasicInfoDto.setLat(GPSFormatUtils.DDtoDMS(busieventBasicInfoDto.getBexy().getLatitude()));
        }
        // 设置处置状态
        busieventBasicInfoDto.setBestat(getDtoBestatName(busieventBasicInfoDto.getBestat()));
        // 设置当前阶段时长、累计时长
        setDtoProdura(busieventBasicInfoDto);
        setDtoLjProdura(busieventBasicInfoDto);
        // 设置受理信息
        setDtoSlInfo(busieventBasicInfoDto);
        // 设置图片信息
        busieventBasicInfoDto.setFileids(attachmentService.getFilesByBeid(busieventBasicInfoDto.getBeid()));
        return busieventBasicInfoDto;
    }

    // 设置受理信息
    private void setDtoSlInfo(BusieventBasicInfoDto dto) {
        Bestep bestep = bestepService.selectStepByWfnmAndBeid(dto.getBeid(),
                EventEnum.SECOND_LEVEL_ALLOCATING_STAT.getDesp());
        Optional.ofNullable(bestep).ifPresent(stp -> {
            // 受理时间
            dto.setSlsj(DateUtil.formatDateTime(stp.getCrdt()));
            // 受理部门
            dto.setBepcdptName(getDeptNameByBedid(stp.getBepcdpt0()));
        });
    }

    private void setDtoLjProdura(BusieventBasicInfoDto busieventBasicInfoDto) {
        Long alldura = DateUtil.between(DateUtil.parse(busieventBasicInfoDto.getCrdt()), new Date(), DateUnit.MINUTE);
        if (ObjectUtil.isNotEmpty(busieventBasicInfoDto.getLjProcessingTime())) {
            alldura = Long.valueOf(busieventBasicInfoDto.getLjProcessingTime());
        }
        // 处理时长，按小时记，精确到小数点一位，如1.5h。
        double data = Double.valueOf(alldura);
        // 利用字符串格式化的方式实现四舍五入,保留1位小数
        String result = String.format("%.1f小时", data / 60.0);
        busieventBasicInfoDto.setLjProcessingTime(result);
    }

    private String getDtoBestatName(String stat) {
        Integer currentDeptid = eventService.getBedptByUserId(SecurityUtil.currentUserId()).getBdtype();
        if (currentDeptid == EventConst.FIRST_LEVEL_DEP) {
            Map statMap = MapUtil.getHashMap(0, "未处理", 1, "转派中", 2, "转派中", 4, "拒绝件", 10, "处理中", 11, "转派中", 20, "应急处理中",
                    100, "待核查", 101, "处理中", 102, "处理中", "103", "处理中", 1000, "已关闭", 1001, "已取消");
            return (String) statMap.get(stat);
        }
        if (currentDeptid == EventConst.SECOND_LEVEL_DEP) {
            Map statMap = MapUtil.getHashMap(1, "未处理", 2, "转派中", 10, "处理中", 11, "处理中", 102, "处理中", 103, "处理中",
                    101, "待核查", 100, "已完成", 1000, "已完成");
            return (String) statMap.get(stat);
        }

        if (currentDeptid == EventConst.SERVICE_LEVEL_DEP) {
            Map statMap = MapUtil.getHashMap(2, "未处理", 10, "处理中", 100, "已完成", 101, "已完成", 102, "处理中", 103, "处理中",
                    1000, "已完成");
            return (String) statMap.get(stat);
        }
        return null;
    }

    private void setDtoProdura(BusieventBasicInfoDto dto) {
        if (dto == null) {
            return;
        }

        if (ObjectUtil.isNotEmpty(dto.getLjProcessingTime())) {
            dto.setProcessingTime(null);
            return;
        }

        // 处理时长，按小时记，精确到小数点一位，如1.5h。
        Long pcdura = DateUtil.between(DateUtil.parse(dto.getProcessingTime()), new Date(), DateUnit.MINUTE);
        double data = Double.valueOf(pcdura);
        // 利用字符串格式化的方式实现四舍五入,保留1位小数
        String result = String.format("%.1f小时", data / 60.0);
        //
        dto.setProcessingBegin(dto.getProcessingTime());
        dto.setProcessingTime(result);
    }

    @Override
    public Integer getEventSecondDepid(Integer beid) {
        Bedept secondDept = baseMapper.getEventSecondDept(beid);
        Integer secondDepid = (secondDept == null) ? null : secondDept.getBedid();
        return secondDepid;
    }

    @Override
    public Integer getEventServiceDepid(Integer beid) {
        Bedept serviceDept = baseMapper.getEventServiceDept(beid);
        Integer serviceDepid = (serviceDept == null) ? null : serviceDept.getBedid();
        return serviceDepid;
    }

    @Override
    public Integer getFirstDeptId() {
        Bedept bedept = bedeptService.getOne(Condition.<Bedept>create().eq(Bedept.BDTYPE, EventConst.FIRST_LEVEL_DEP));
        if (bedept == null) {
            throw new ServiceException("无法找到一级指挥中心！");
        }
        return bedept.getBedid();
    }

    @Override
    public IPage<BusieventDto> getFirstCoopEventList(Integer efbh, Integer etbh, String benm, String becnt,
                                                     Integer size, Integer current) {
        IPage<Busievent> page = new Page<>(current, size);
        IPage<BusieventDto> taskList = baseMapper.getFirstCoopEventList(page, efbh, etbh, benm, becnt);
        addSeqForBusieventDto(taskList, size, current);
        return taskList;
    }

    @Override
    public IPage<BusieventDto> getSecondCoopEventList(Integer bedid, String bestat, Integer efbh, Integer etbh,
                                                      String benm, String becnt, Integer size, Integer current) {
        IPage<Busievent> page = new Page<>(current, size);
        IPage<BusieventDto> taskList = baseMapper.getSecondCoopEventList(page, bedid, bestat, efbh, etbh, benm, becnt);
        addSeqForBusieventDto(taskList, size, current);
        return taskList;
    }

    @Override
    public IPage<BusieventDto> getServiceCoopEventList(Integer bedid, String bestat, Integer efbh, Integer etbh,
                                                       String benm, String becnt, Integer size, Integer current) {
        IPage<Busievent> page = new Page<>(current, size);
        IPage<BusieventDto> taskList = baseMapper.getServiceCoopEventList(page, bedid, bestat, efbh, etbh, benm, becnt);
        addSeqForBusieventDto(taskList, size, current);
        return taskList;
    }

    private String getDeptNameByBedid(Integer bedid) {
        Bedept dept = (bedid != null)
                ? bedeptService.getOne(Condition.<Bedept>create().eq(Bedept.BEDID, bedid).select(Bedept.BDNM)) : null;
        String deptName = (dept != null) ? dept.getBdnm() : "";
        return deptName;
    }

    private void addSeqForBusieventDto(IPage<BusieventDto> undoTaskList, Integer size, Integer current) {
        Optional.ofNullable(undoTaskList).ifPresent(dtoList -> {
            int i = 1;
            for (BusieventDto dto : dtoList.getRecords()) {
                dto.setSeq((current - 1) * size + i);
                i++;
            }
        });
    }

    @Override
    public void deal(Integer beid, String reason, String handleStr) {
        String esoperPrefix = "完成";
        String esoperSuffix = "自处理完成事件，完成反馈";
        // 指派回给上级部门，即谁指派给你的，就指派回给谁
        Integer secondBedid = getEventSecondDepid(beid);
        Optional.ofNullable(secondBedid)
                .orElseThrow(() -> new ServiceException(String.format("无法找到处理该事件的上级指挥中心。事件id:%s", beid)));
        eventDepositService.handleBevent(beid, handleStr, secondBedid, EventEnum.FIRST_LEVEL_CHECK_STAT, esoperPrefix,
                esoperSuffix, reason);

    }
}
