package com.digitalchina.event.mid.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.digitalchina.modules.entity.SysUser;
import com.digitalchina.modules.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.event.mid.entity.Bedept;
import com.digitalchina.event.mid.entity.Bestep;
import com.digitalchina.event.mid.entity.Busievent;
import com.digitalchina.event.mid.mapper.BestepMapper;
import com.digitalchina.event.mid.service.BedeptService;
import com.digitalchina.event.mid.service.BestepService;
import com.digitalchina.event.utils.MapUtil;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 业务事件阶段 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Service
public class BestepServiceImpl extends ServiceImpl<BestepMapper, Bestep> implements BestepService {

	@Autowired
	private BedeptService bedeptService;

	@Autowired
	private SysUserService sysUserService;


	@Override
	public List<Map<String, Object>> eventDepositInfo(Integer beid, String order) {
		List<Map<String, Object>> bestepsMap = baseMapper.selectMaps(Condition.<Bestep>create().eq(Busievent.BEID, beid)
				.select(Bestep.ESOPER, Bestep.ESOPN, Bestep.ESOPMAN,Bestep.ESOPDEPT).orderByDesc(Bestep.ESID));
		if (bestepsMap != null && bestepsMap.size() > 0) {
			List<Map<String, Object>> resultList = new ArrayList<>();
			for (Map<String, Object> info : bestepsMap) {
				String[] esopnInfoArr = info.get(Bestep.ESOPER).toString().split("，", 3);
				// 操作部门
				String dept = "";
				if (StrUtil.isNotEmpty(info.get(Bestep.ESOPDEPT).toString())) {
					Bedept deptEnt = bedeptService.getById(Integer.valueOf(info.get(Bestep.ESOPDEPT).toString()));
					dept = deptEnt.getBdnm();
				}

				//操作人
				String name = "";
				if (StrUtil.isNotEmpty(info.get(Bestep.ESOPMAN).toString())) {
					SysUser amSysUser = sysUserService.getById(Integer.valueOf(info.get(Bestep.ESOPMAN).toString()));
					if(amSysUser!=null){
						name = amSysUser.getName();
					}
				}
				// 结果
				Map stepInfoMap = MapUtil.getHashMap("status", "1", "title", esopnInfoArr[0], "time", esopnInfoArr[1],
						"info", esopnInfoArr[2], "compment", info.get(Bestep.ESOPN), "dept", dept,"name",name);
				resultList.add(stepInfoMap);
			}

			if (order != null && order.equals("desc")) {
				CollectionUtil.reverse(resultList);
			}
			return resultList;
		}
		return bestepsMap;
	}

	@Override
	public Map<String, Object> getEventSummary(Integer beid) {
		Map<String, Object> bestepsMap = baseMapper.getEventSummary(beid);
		return bestepsMap;
	}

	@Override
	public Bestep selectStepByWfnmAndBeid(Integer beid, String wfnm) {
		return baseMapper.selectStepByWfnmAndBeid(beid, wfnm);
	}
}
