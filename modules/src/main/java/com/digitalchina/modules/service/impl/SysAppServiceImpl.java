package com.digitalchina.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.modules.constant.enums.SysCodeEnum;
import com.digitalchina.modules.entity.SysApp;
import com.digitalchina.modules.mapper.SysAppMapper;
import com.digitalchina.modules.service.SysAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-19
 */
@Service
public class SysAppServiceImpl extends ServiceImpl<SysAppMapper, SysApp> implements SysAppService {
	//强制走代理,利用缓存
	@Autowired
	private  SysAppService sysAppService;

	@Override
	@Cacheable(value = "sysCodes", key = "#appCode")
	public SysApp findAppByCode(String appCode) {
		return this.getOne(Condition.<SysApp>create().eq(SysApp.CODE, appCode));
	}

	@Override
	public Integer getAppId(SysCodeEnum sysCodeEnum) {
		SysApp appByCode = sysAppService.findAppByCode(sysCodeEnum.getCode());
		Optional.ofNullable(appByCode).orElseThrow(
				()->new ServiceException(String.format("未配置系统码:%s", sysCodeEnum.getCode())));
		return appByCode.getId();
	}

	@Override
	public int checkExist(Integer appId, String code) {
		return baseMapper.checkExist(appId, code);
	}

	@Override
	public List<String> findAppCodeByUserId(Integer userId) {
		return baseMapper.findAppCodeByUserId(userId);
	}
}
