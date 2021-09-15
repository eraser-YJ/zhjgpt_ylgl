package com.digitalchina.admin.mid.service.warn2.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.dto.warn2.WarnMesuDetailDto;
import com.digitalchina.admin.mid.dto.warn2.WarnRiskDeptDto;
import com.digitalchina.admin.mid.dto.warn2.WarnRiskDetailDto;
import com.digitalchina.admin.mid.entity.WrnlRisk;
import com.digitalchina.admin.mid.entity.WrnlRiskRelevance;
import com.digitalchina.admin.mid.mapper.WrnlRiskMapper;
import com.digitalchina.admin.mid.service.WrnlRiskRelevanceService;
import com.digitalchina.admin.mid.service.warn2.WarnMesuService;
import com.digitalchina.admin.mid.service.warn2.WrnlRiskService;
import com.digitalchina.common.utils.CollUtils;
import com.digitalchina.modules.constant.enums.SourceTypeEnum;
import com.digitalchina.modules.entity.SysDept;
import com.digitalchina.modules.service.SysDeptService;

import cn.hutool.core.collection.CollUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2020-01-02
 */
@Service
public class WrnlRiskServiceImpl extends ServiceImpl<WrnlRiskMapper, WrnlRisk> implements WrnlRiskService {

	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private WrnlRiskService wrnlRiskService;
	@Autowired
	private WrnlRiskRelevanceService wrnlRiskRelevanceService;
	@Autowired
	private WarnMesuService warnMesuService;

	@Override
	public Page<WrnlRisk> iotlist(Page<WrnlRisk> page, String special, String topic, Integer status, String keyword) {
		page.setRecords(baseMapper.iotlist(page, special, topic, status, keyword));
		return page;
	}

	@Override
	public Page<WrnlRisk> nflist(Page<WrnlRisk> page, String special, String topic, Integer status, String keyword) {
		page.setRecords(baseMapper.nflist(page, special, topic, status, keyword));
		return page;
	}

	@Override
	public WarnRiskDetailDto detail(Integer id) {
		// 基本信息
		WrnlRisk risk = super.getById(id);
		WarnRiskDetailDto result = new WarnRiskDetailDto(risk);
		// 指标和指标
		List<WrnlRiskRelevance> rels = wrnlRiskRelevanceService.getMesuByRisk(risk.getId());
		result.setRelevances(rels);
		result.setMesus(getMesus(rels));
		// 周期/频率转换
		result.setIntervaldays(risk.getIntervaldays());
		result.setFixdays(risk.getFixdays());
		result.setWeek(risk.getWeek());
		result.setMonth(risk.getMonth());
		result.setTime(risk.getTime());
		// 提醒用户列表
		result.setDepts(converDept(risk.getDeptid()));
		return result;
	}

	private List<WarnMesuDetailDto> getMesus(List<WrnlRiskRelevance> rels) {
		if (CollUtils.isEmpty(rels))
			return null;

		List<WarnMesuDetailDto> result = new ArrayList<WarnMesuDetailDto>();
		rels.forEach(r -> {
			if (SourceTypeEnum.WLW.getCode().equals(r.getType())) {
				WarnMesuDetailDto dto = warnMesuService.iotDetail(r.getItemid());
				result.add(dto);
			} else if (SourceTypeEnum.HLW.getCode().equals(r.getType())) {
				WarnMesuDetailDto dto = warnMesuService.nfDetail(r.getItemid());
				result.add(dto);
			} else
				;
		});
		return result;
	}

	// 转换用户信息
	private List<WarnRiskDeptDto> converDept(Integer[] deptids) {
		if (null == deptids || deptids.length < 1)
			return null;

		List<SysDept> depts = sysDeptService.list(Condition.<SysDept>create().in(SysDept.DPID, Arrays.asList(deptids)));
		if (CollUtil.isEmpty(depts))
			return null;

		List<WarnRiskDeptDto> result = depts.stream().map(u -> {
			return new WarnRiskDeptDto(u.getDpid(), u.getBdnm());
		}).collect(Collectors.toList());
		return result;
	}

	@Override
	public void save(WarnRiskDetailDto data) {
		// 更新基本信息表
		saveInfo(data);
		// 更新关联关系表
		saveRelevance(data);
	}

	private void saveRelevance(WarnRiskDetailDto data) {
		if (null != data.getId()) {
			QueryWrapper<WrnlRiskRelevance> cd = Condition.<WrnlRiskRelevance>create();
			wrnlRiskRelevanceService.remove(cd.eq(WrnlRiskRelevance.RISKID, data.getId()));
		}
		data.getRelevances().forEach(r -> {
			r.setId(null);// 移除id，保证新增
			r.setRiskid(data.getId());// 关联当前风险id
		});
		wrnlRiskRelevanceService.saveOrUpdateBatch(data.getRelevances());
	}

	// 保存基本信息
	private void saveInfo(WarnRiskDetailDto data) {
		WrnlRisk saveEnt = WrnlRisk.conver(data);
		// 周期
		saveEnt.setIntervaldays(data.getIntervaldays());
		saveEnt.setFixdays(data.getFixdays());
		saveEnt.setWeek(data.getWeek());
		saveEnt.setMonth(data.getMonth());
		saveEnt.setTime(data.getTime());
		// 提醒用户
		if (CollUtil.isNotEmpty(data.getDepts())) {
			List<Integer> deptids = data.getDepts().stream().map(dept -> {
				return dept.getId();
			}).collect(Collectors.toList());
			saveEnt.setDeptid(deptids.toArray(new Integer[deptids.size()]));
			saveEnt.setUserremind(Boolean.TRUE); // 用户不为空就提醒
		}
		wrnlRiskService.saveOrUpdate(saveEnt);
		data.setId(saveEnt.getId());
	}

	@Override
	public List<SysDept> depts() {
		return sysDeptService.list(Condition.<SysDept>create().orderByAsc(SysDept.DPID));
	}

}
