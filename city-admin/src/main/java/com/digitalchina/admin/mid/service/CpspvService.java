package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.Cpspv;

import java.util.List;

/**
 * <p>
 * 组合属性值 服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
public interface CpspvService extends IService<Cpspv> {

    List<Cpspv> getList(String cpvs);
}
