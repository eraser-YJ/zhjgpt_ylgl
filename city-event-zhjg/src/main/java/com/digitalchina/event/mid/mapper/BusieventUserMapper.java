package com.digitalchina.event.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.event.dto.BusieventUserDto;
import com.digitalchina.event.dto.EmergencyInfoDto;
import com.digitalchina.event.dto.HomeBusieventDto;
import com.digitalchina.event.dto.eventdeposit.BusieventBasicInfoDto;
import com.digitalchina.event.dto.eventdeposit.BusieventDto;
import com.digitalchina.event.mid.entity.Bedept;
import com.digitalchina.event.mid.entity.Busievent;
import com.digitalchina.event.mid.entity.BusieventUser;
import com.digitalchina.modules.security.UserSource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 业务事件 Mapper 接口
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Component
public interface BusieventUserMapper extends BaseMapper<BusieventUser> {

	/**
	 * 插入
	 * @param busieventUser
	 * @return
	 */
	int insertBusieventUser (BusieventUser busieventUser);
}
