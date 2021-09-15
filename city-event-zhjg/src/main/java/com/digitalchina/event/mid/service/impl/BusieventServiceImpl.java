package com.digitalchina.event.mid.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.event.consts.EventConst;
import com.digitalchina.event.dto.EmergencyInfoDto;
import com.digitalchina.event.dto.HomeBusieventDto;
import com.digitalchina.event.dto.eventdeposit.BusieventBasicInfoDto;
import com.digitalchina.event.mid.entity.Bedept;
import com.digitalchina.event.mid.entity.Busievent;
import com.digitalchina.event.mid.mapper.BusieventMapper;
import com.digitalchina.event.mid.service.AttachmentService;
import com.digitalchina.event.mid.service.BedeptService;
import com.digitalchina.event.mid.service.BusieventService;
import com.digitalchina.event.mid.service.EventDepositService;
import com.digitalchina.event.utils.MapUtil;
import com.digitalchina.modules.constant.enums.EventEnum;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * <p>
 * 业务事件 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Service
public class BusieventServiceImpl extends ServiceImpl<BusieventMapper, Busievent> implements BusieventService {
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private EventDepositService depositService;

	@Autowired
	private BedeptService bedeptService;

	@Override
	public List<HomeBusieventDto> getHomeBusieventList(String keyword, Integer[] adids, Integer[] besrcdpts,
			Integer[] etbhs, String[] status) {
		List<Integer> allodpts = new ArrayList<>();
		List<Integer> recdpts = new ArrayList<>();

		if (ObjectUtil.isNotEmpty(besrcdpts)) {
			List<Integer> ids = new ArrayList<>(Arrays.asList(besrcdpts));
			Collection<Bedept> bedepts = bedeptService.listByIds(ids);
			if (ObjectUtil.isNotEmpty(bedepts)) {
				// 是否全选
				boolean isCheckAll = bedepts.stream().anyMatch(q -> q.getBdtype() == EventConst.FIRST_LEVEL_DEP);

				if (!isCheckAll) {
					// 全勾的二级部门
					allodpts = bedepts.stream().filter(q -> q.getBdtype() == EventConst.SECOND_LEVEL_DEP)
							.map(Bedept::getBedid).collect(Collectors.toList());

					final List<Integer> allodptsTemp = allodpts;
					// 非全勾的二级部门的三级部门
					recdpts = bedepts.stream().filter(q -> {
						if (q.getBdtype() != EventConst.SERVICE_LEVEL_DEP) {
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
		List<Object> statusdb = converStatus(status);
		List<HomeBusieventDto> resultList = baseMapper.getHomeBusieventList(keyword, adids, besrcdpts, etbhs, allodpts,
				recdpts, statusdb);
		decorateResultDto(resultList);
		return resultList;
	}

	private List<Object> converStatus(String[] status) {
		if (null != status && status.length > 0) {
			List<Object> sdb = new ArrayList<>();
			for (int i = 0; i < status.length; i++) {
				sdb.addAll(converStatus(status[i]));
			}
			return sdb;
		}
		return null;
	}

	private List<Object> converStatus(String status) {
		return EventEnum.switchs(status);
	}

	@Override
	public IPage<HomeBusieventDto> getHomeBusieventList(IPage page, String keyword, Integer[] adids,
			Integer[] besrcdpts, Integer[] etbhs) {
		List<Integer> allodpts = new ArrayList<>();
		List<Integer> recdpts = new ArrayList<>();

		if (ObjectUtil.isNotEmpty(besrcdpts)) {
			List<Integer> ids = new ArrayList<>(Arrays.asList(besrcdpts));
			Collection<Bedept> bedepts = bedeptService.listByIds(ids);
			if (ObjectUtil.isNotEmpty(bedepts)) {
				// 是否全选
				boolean isCheckAll = bedepts.stream().anyMatch(q -> q.getBdtype() == EventConst.FIRST_LEVEL_DEP);

				if (!isCheckAll) {
					// 全勾的二级部门
					allodpts = bedepts.stream().filter(q -> q.getBdtype() == EventConst.SECOND_LEVEL_DEP)
							.map(Bedept::getBedid).collect(Collectors.toList());

					final List<Integer> allodptsTemp = allodpts;
					// 非全勾的二级部门的三级部门
					recdpts = bedepts.stream().filter(q -> {
						if (q.getBdtype() != EventConst.SERVICE_LEVEL_DEP) {
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
		IPage<HomeBusieventDto> resultList = baseMapper.getHomeBusieventList(page, keyword, adids, besrcdpts, etbhs,
				allodpts, recdpts, null);
		decorateResultDto(resultList.getRecords());
		return resultList;
	}

	private void decorateResultDto(List<HomeBusieventDto> resultList) {
		for (HomeBusieventDto dto : resultList) {
			dto.setPcdura(null);
			setDtoAlldura(dto);
			setDtoBestatName(dto);
			setDtoFileids(dto);
		}
	}

	private void setDtoBestatName(HomeBusieventDto dto) {
		Map statMap = MapUtil.getHashMap(0, "未处理", 1, "转派中", 2, "转派中", 4, "拒绝件", 10, "处理中", 11, "转派中", 20, "应急处理中", 100,
				"待核查", 101, "处理中", 102, "处理中", "103", "处理中", 1000, "已关闭", 1001, "已取消");
		Optional.ofNullable(dto).ifPresent(s -> s.setBestatName(statMap.get(dto.getBestat()).toString()));
	}

	private void setDtoPcdura(HomeBusieventDto dto) {
		if (dto == null) {
			return;
		}

		if (ObjectUtil.isNotEmpty(dto.getAlldura())) {
			dto.setPcdura(null);
			return;
		}

		// 处理时长，按小时记，精确到小数点一位，如1.5h。
		Long pcdura = DateUtil.between(DateUtil.parse(dto.getPcdura()), new Date(), DateUnit.MINUTE);
		double data = Double.valueOf(pcdura);
		// 利用字符串格式化的方式实现四舍五入,保留1位小数
		String result = String.format("%.1f小时", data / 60.0);
		dto.setPcdura(result);
	}

	private void setDtoAlldura(HomeBusieventDto dto) {

		Long alldura = DateUtil.between(DateUtil.parse(dto.getCrdt()), new Date(), DateUnit.MINUTE);
		if (ObjectUtil.isNotEmpty(dto.getAlldura())) {
			alldura = Long.valueOf(dto.getAlldura());
		}
		// 处理时长，按小时记，精确到小数点一位，如1.5h。
		double data = Double.valueOf(alldura);
		// 利用字符串格式化的方式实现四舍五入,保留1位小数
		String result = String.format("%.1f小时", data / 60.0);
		dto.setAlldura(result);
	}

	@Override
	public HomeBusieventDto getBusieventById(Integer beid) {
		HomeBusieventDto dto = baseMapper.getBusieventById(beid);
		setDtoBestatName(dto);
		setDtoPcdura(dto);
		setDtoAlldura(dto);
		setDtoFileids(dto);
		return dto;
	}

	@Override
	public BusieventBasicInfoDto getBusieventBasicInfoDtoById(Integer beid) {
		BusieventBasicInfoDto busieventBasicInfoDto = depositService.getEventBasicInfo(beid);
		return busieventBasicInfoDto;
	}

	@Override
	public void updateGis(Integer beid, String bexy) {
		if (StringUtils.isEmpty(bexy)) {
			return;
		}
		baseMapper.updateGis(beid, bexy.split(",")[0], bexy.split(",")[1]);
	}

	private void setDtoFileids(HomeBusieventDto dto) {
		dto.setFileids(attachmentService.getFilesByBeid(dto.getBeid()));
	}

	@Override
	public EmergencyInfoDto getEmergencyInfo(Integer beid) {
		return baseMapper.getEmergencyInfo(beid);
	}
}
