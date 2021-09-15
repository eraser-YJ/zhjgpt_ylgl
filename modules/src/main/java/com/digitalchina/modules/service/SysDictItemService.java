package com.digitalchina.modules.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.modules.dto.SysDictDto;
import com.digitalchina.modules.entity.SysDictItem;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Warrior
 * @since 2020-03-19
 */
public interface SysDictItemService extends IService<SysDictItem> {

	/**
	 * 根据字典code查找字典列表
	 * 
	 * @param code
	 * @return
	 */
	List<SysDictDto> findByCode(String code);

}
