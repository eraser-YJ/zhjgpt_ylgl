package com.digitalchina.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.modules.constant.enums.SysCodeEnum;
import com.digitalchina.modules.entity.SysApp;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-19
 */
public interface SysAppService extends IService<SysApp> {

	/**
	 * 根据code获取系统对象
	 *
	 * @param appCode
	 * @return
	 */
	SysApp findAppByCode(String appCode);

	/**
	 * 获取对应系统码id
	 * @param sysCodeEnum 系统码枚举
	 * @return
	 */
	Integer getAppId(SysCodeEnum sysCodeEnum);

	/**
	 * @description 检查编码是否重复
	 * @author cwc
	 * @date 2019/8/29 15:27
	 * @params appId : 系统主键
	 * @params code : 编码
	 **/
	int checkExist(Integer appId, String code);

	/**
	 * 根据用户ID查找可以访问的系统code
	 * 
	 * @param userId
	 * @return 可以访问的系统code
	 */
	List<String> findAppCodeByUserId(Integer userId);
}
