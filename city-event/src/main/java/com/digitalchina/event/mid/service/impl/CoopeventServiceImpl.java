package com.digitalchina.event.mid.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.event.dto.eventdeposit.CoopeventBasicDto;
import com.digitalchina.event.dto.eventdeposit.CoopeventDto;
import com.digitalchina.event.mid.entity.Coopevent;
import com.digitalchina.event.mid.mapper.CoopeventMapper;
import com.digitalchina.event.mid.service.AttachmentCoopService;
import com.digitalchina.event.mid.service.CoopeventService;
import com.digitalchina.event.mid.service.EventService;
import com.digitalchina.event.utils.MapUtil;
import com.digitalchina.modules.constant.enums.EventEnum;
import com.digitalchina.modules.entity.SysUser;
import com.digitalchina.modules.security.SecurityUtil;
import com.digitalchina.modules.service.SysDeptService;
import com.digitalchina.modules.service.SysUserService;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

/**
 * <p>
 * 协查事件 服务实现类
 * </p>
 *
 * @author lichunlong
 * @since 2019-09-15
 */
@Service
public class CoopeventServiceImpl extends ServiceImpl<CoopeventMapper, Coopevent> implements CoopeventService {

	@Autowired
	private EventDepositServiceImpl depositService;
	@Autowired
	private EventService eventService;
	@Autowired
	private AttachmentCoopService attachmentCoopService;
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private SysUserService sysUserService;

	@Override
	public IPage<CoopeventDto> countCreateCoop(Integer curid, Integer size, Integer current) {
		IPage<CoopeventDto> page = new Page<>(current, size);
		IPage<CoopeventDto> coopeventDtos = baseMapper.countCreateCoop(page, curid);
		addSeqForCoopDto(coopeventDtos, size, current);
		return coopeventDtos;
	}

	@Override
	public IPage<CoopeventDto> countReceiveCoop(Integer curid, Integer size, Integer current) {
		IPage<CoopeventDto> page = new Page<>(current, size);
		IPage<CoopeventDto> coopeventDtos = baseMapper.countReceiveCoop(page, curid);
		addSeqForCoopDto(coopeventDtos, size, current);
		return coopeventDtos;
	}

	@Override
	public List<Map<String, Object>> eventDepositInfo(Integer ceid) {
		Coopevent coopevent = baseMapper.selectOne(
				Condition.<Coopevent>create().eq(Coopevent.CEID, ceid).select(Coopevent.CEPROC, Coopevent.CPFBINFO));
		if (coopevent != null && coopevent.getCeproc() != null) {
			List<Map<String, Object>> resultList = new ArrayList<>();
			String[] procs = coopevent.getCeproc();
			for (String proc : procs) {
				String[] esopnInfoArr = proc.split("，", 3);
				Map stepInfoMap = MapUtil.getHashMap("status", "1", "title", esopnInfoArr[0], "time", esopnInfoArr[1],
						"info", esopnInfoArr[2], "compment", coopevent.getCpfbinfo());
				resultList.add(stepInfoMap);
			}
			CollectionUtil.reverse(resultList);
			return resultList;
		}
		return null;
	}

	@Override
	public void submitSecond(Integer beid, String reason) {
		reason = "提交二级审批";
		String handleStr = "tjejsp";
		String bdnm = eventService.getBedptByUserId(SecurityUtil.currentUserId()).getBdnm();
		String proc = String.format("开始，%s，%s创建协查申办事件，提交二级指挥中心审批。", DateUtil.formatDateTime(new Date()), bdnm);
		depositService.handleCevent(beid, handleStr, proc, getSecondDeptId(SecurityUtil.currentUserId()),
				EventEnum.SECOND_AUDIT_COOP, reason);

	}

	@Override
	public void secondPass(Integer ceid, String reason) {
		String handleStr = "ejpzxc";
		reason = "二级批准协查";
		Integer coopDeptid = getCoopDeptid(ceid);
		String proc = String.format("审批，%s，二级指挥中心审批通过。", DateUtil.formatDateTime(new Date()));
		Optional.ofNullable(coopDeptid)
				.orElseThrow(() -> new ServiceException(String.format("无法找到处理该协查事件的部门。协查事件id:%s", ceid)));
		depositService.handleCevent(ceid, handleStr, proc, coopDeptid, EventEnum.HANDLE_COOP, reason);
	}

	@Override
	public void firstPass(Integer beid, String reason) {
		String handleStr = "yjpzxc";
		reason = "一级批准协查";
		Integer coopDeptid = getCoopDeptid(beid);
		String proc = String.format("审批，%s，一级指挥中心审批通过。", DateUtil.formatDateTime(new Date()));
		Optional.ofNullable(coopDeptid)
				.orElseThrow(() -> new ServiceException(String.format("无法找到处理该协查事件的部门。协查事件id:%s", beid)));
		depositService.handleCevent(beid, handleStr, proc, coopDeptid, EventEnum.HANDLE_COOP, reason);
	}

	@Override
	public void submitFirst(Integer beid, String reason) {
		String handleStr = "tjyjsp";
		reason = "提交一级审批";
		String proc = String.format("审批，%s，二级指挥中心提交一级指挥中心审批。", DateUtil.formatDateTime(new Date()));
		depositService.handleCevent(beid, handleStr, proc, getFirstDeptId(), EventEnum.FIRST_AUDIT_COOP, reason);
	}

	private Integer getFirstDeptId() {
		return depositService.getFirstDeptId();
	}

	@Override
	public void secondRefuse(Integer beid, String reason) {
		String handleStr = "ejbhxc";
		Integer coopDeptid = getCoopSrcDeptid(beid);
		String proc = String.format("审批，%s，二级指挥中心审批驳回。", DateUtil.formatDateTime(new Date()));
		Optional.ofNullable(coopDeptid)
				.orElseThrow(() -> new ServiceException(String.format("无法找到处理该协查事件的部门。协查事件id:%s", beid)));
		depositService.handleCevent(beid, handleStr, proc, coopDeptid, EventEnum.SECOND_REFUSE_COOP, reason);
	}

	@Override
	public void firstRefuse(Integer beid, String reason) {
		String handleStr = "yjbhxc";
		String proc = String.format("审批，%s，一级指挥中心审批驳回。", DateUtil.formatDateTime(new Date()));
		depositService.handleCevent(beid, handleStr, proc, getSecondDeptId(beid), EventEnum.FIRST_REFUSE_COOP, reason);
	}

	@Override
	public void serviceFallback(Integer ceid, String reason) {
		Coopevent coopevent = baseMapper.selectOne(Condition.<Coopevent>create().eq(Coopevent.CEID, ceid));
		coopevent.setCpfbinfo(reason);
		String bdnm = eventService.getBedptByBedId(coopevent.getCpbedid()).getBdnm();
		String proc = String.format("反馈，%s，%s进行协办件反馈。", DateUtil.formatDateTime(new Date()), bdnm);
		String[] originProc = coopevent.getCeproc();
		String[] newPorcArr = new String[] { proc };
		String[] ceproc = ArrayUtils.addAll(originProc, newPorcArr);
		coopevent.setCeproc(ceproc);
		baseMapper.updateById(coopevent);
	}

	@Override
	public void serviceCancel(Integer beid, String reason) {
		String handleStr = "fqbcxc";
		String bdnm = eventService.getBedptByUserId(SecurityUtil.currentUserId()).getBdnm();
		String proc = String.format("取消，%s，%s取消协查事件。", DateUtil.formatDateTime(new Date()), bdnm);
		depositService.handleCevent(beid, handleStr, proc, getSecondDeptId(beid), EventEnum.CANCEL_COOP, reason);
	}

	@Override
	public void serviceClose(Integer ceid, String reason) {
		String bdnm = eventService.getBedptByUserId(SecurityUtil.currentUserId()).getBdnm();
		String proc = String.format("关闭，%s，%s关闭协查事件。", DateUtil.formatDateTime(new Date()), bdnm);
		depositService.handleCevent(ceid, null, proc, getCoopSrcDeptid(ceid), EventEnum.FINISH_COOP, reason);
	}

	@Override
	public CoopeventBasicDto getBasicinfo(Integer ceid) {
		CoopeventBasicDto dto = baseMapper.getBasicinfo(ceid);
		setCoopEventClTime(dto);
		setCoopEventLjTime(dto);
		setCoopCestatName(dto);
		dto.setFileids(attachmentCoopService.getFilesByBeid(ceid));
		return dto;
	}

	@Override
	public void updateGis(Integer ceid, String bexy) {
		if (StringUtils.isEmpty(bexy)) {
			return;
		}
		baseMapper.updateGis(ceid, bexy.split(",")[0], bexy.split(",")[1]);
	}

	private void setCoopCestatName(CoopeventBasicDto dto) {
		Map statMap = MapUtil.getHashMap("0", "修改协查申请", "1", "关闭（取消）", "10", "二级审批协查", "11", "二级驳回协查", "20", "一级审批协查",
				"21", "一级驳回协查", "30", "处理协查申请", "40", "关闭（办结）");
		dto.setCestatName(statMap.get(dto.getCestat()).toString());
	}

	private void setCoopEventLjTime(CoopeventBasicDto dto) {
		if (dto == null) {
			return;
		}

		Date ljDate = new Date();
		if (dto.getCestat().equals(EventEnum.FINISH_COOP.toString())) {
			ljDate = DateUtil.parse(dto.getProcessingTime());
		}

		// 时长，按小时记，精确到小数点一位，如1.5h。
		Long ljdura = DateUtil.between(DateUtil.parse(dto.getLjProcessingTime()), ljDate, DateUnit.MINUTE);
		double data = Double.valueOf(ljdura);
		// 利用字符串格式化的方式实现四舍五入,保留1位小数
		String result = String.format("%.1f小时", data / 60.0);
		dto.setLjProcessingTime(result);
	}

	private void setCoopEventClTime(CoopeventBasicDto dto) {
		if (dto == null) {
			return;
		}

		if (dto.getCestat().equals(EventEnum.FINISH_COOP.toString())) {
			dto.setProcessingTime(null);
			return;
		}

		// 处理时长，按小时记，精确到小数点一位，如1.5h。
		Long pcdura = DateUtil.between(DateUtil.parse(dto.getProcessingTime()), new Date(), DateUnit.MINUTE);
		double data = Double.valueOf(pcdura);
		// 利用字符串格式化的方式实现四舍五入,保留1位小数
		String result = String.format("%.1f小时", data / 60.0);
		dto.setProcessingTime(result);
	}

	private Integer getCoopDeptid(Integer ceid) {
		Coopevent coopevent = baseMapper.selectById(ceid);
		Integer coopDeptid = (coopevent == null) ? null : coopevent.getCpbedid();
		return coopDeptid;
	}

	private Integer getCoopSrcDeptid(Integer ceid) {
		Coopevent coopevent = baseMapper.selectById(ceid);
		Integer coopDeptid = (coopevent == null) ? null : coopevent.getCesrcdpt();
		return coopDeptid;
	}

	private Integer getSecondDeptId(Integer userid) {
		SysUser user = sysUserService.getById(userid);
		return sysDeptService.getById(user.getDpid()).getBdpntid();
	}

	private void addSeqForCoopDto(IPage<CoopeventDto> taskList, Integer size, Integer current) {
		Optional.ofNullable(taskList).ifPresent(dtoList -> {
			int i = 1;
			for (CoopeventDto dto : dtoList.getRecords()) {
				dto.setSeq((current - 1) * size + i);
				i++;
			}
		});
	}
}
