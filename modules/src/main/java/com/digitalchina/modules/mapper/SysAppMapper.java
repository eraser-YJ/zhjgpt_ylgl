package com.digitalchina.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.modules.entity.SysApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Warrior
 * @since 2019-08-19
 */
public interface SysAppMapper extends BaseMapper<SysApp> {

	/**
	 * @description 检查编码是否重复
	 * @author cwc
	 * @date 2019/8/29 15:27
	 * @params appId : 系统主键
	 * @params code : 编码
	 **/
	int checkExist(@Param("appId") Integer appId, @Param("code") String code);

	/**
	 * 根据用户ID查找可以访问的系统code
	 * 
	 * @param userId
	 * @return 可以访问的系统code
	 */
	List<String> findAppCodeByUserId(@Param("userId") Integer userId);
}
