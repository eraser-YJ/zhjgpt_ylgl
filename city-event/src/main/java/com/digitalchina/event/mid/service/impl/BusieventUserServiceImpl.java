package com.digitalchina.event.mid.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.event.consts.EventConst;
import com.digitalchina.event.dto.EmergencyInfoDto;
import com.digitalchina.event.dto.HomeBusieventDto;
import com.digitalchina.event.dto.eventdeposit.BusieventBasicInfoDto;
import com.digitalchina.event.mid.entity.Bedept;
import com.digitalchina.event.mid.entity.Busievent;
import com.digitalchina.event.mid.entity.BusieventUser;
import com.digitalchina.event.mid.mapper.BusieventMapper;
import com.digitalchina.event.mid.mapper.BusieventUserMapper;
import com.digitalchina.event.mid.service.*;
import com.digitalchina.event.utils.MapUtil;
import com.digitalchina.modules.constant.enums.EventEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务事件 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Service
public class BusieventUserServiceImpl extends ServiceImpl<BusieventUserMapper, BusieventUser> implements BusieventUserService {

	@Autowired
	private  BusieventUserMapper busieventUserMapper;

	@Override
	public int insertBusieventUser(BusieventUser busieventUser) {
		return busieventUserMapper.insertBusieventUser(busieventUser);
	}
}
