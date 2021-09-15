package com.digitalchina.modules.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.modules.dto.BedeptDto;
import com.digitalchina.modules.entity.SysDept;

/**
 * <p>
 * 统一部门 服务类
 * </p>
 *
 * @author Warrior
 * @since 2020-03-24
 */
public interface SysDeptService extends IService<SysDept> {

	void removeTreeById(Integer bedid);

	List<BedeptDto> tree();

}
