package com.digitalchina.event.mid.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.event.dto.BusieventUserDto;
import com.digitalchina.event.mid.entity.BusieventUser;
import com.digitalchina.event.mid.mapper.BusieventUserDtoMapper;
import com.digitalchina.event.mid.mapper.BusieventUserMapper;
import com.digitalchina.event.mid.service.BusieventUserDtoService;
import com.digitalchina.event.mid.service.BusieventUserService;
import com.digitalchina.modules.security.UserSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 业务事件 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Service
public class BusieventUserDtoServiceImpl extends ServiceImpl<BusieventUserDtoMapper, BusieventUserDto> implements BusieventUserDtoService {

	@Autowired
	private BusieventUserDtoMapper busieventUserDtoMapper;

	@Override
	public List<BusieventUserDto> selectBusieventByUser(Page<BusieventUserDto> page, Integer uid, UserSource userSource) {
		return busieventUserDtoMapper.selectBusieventByUser(page,uid,userSource);
	}

	@Override
	public int insertAttachmentByBeid(Integer beid, String fileid) {
		return busieventUserDtoMapper.insertAttachmentByBeid(beid, fileid);
	}

	@Override
	public List<Map<String, String>> selectBusieventByBeid(Integer beid) {
		return busieventUserDtoMapper.selectBusieventByBeid(beid);
	}
}
