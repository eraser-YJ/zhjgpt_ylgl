package com.digitalchina.admin.mid.service;

import com.digitalchina.admin.mid.entity.SignTreeVer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
public interface SignTreeVerService extends IService<SignTreeVer> {

    /**
     * @description 获取树版本
     * @author cwc
     * @date 2019/8/14 10:24
     * @returns : java.lang.Integer
     **/
    Integer getTid();

}
