package com.digitalchina.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.modules.constant.enums.MsttypeEnum;
import com.digitalchina.modules.constant.enums.SysCodeEnum;
import com.digitalchina.modules.entity.Enclosed;

import java.util.List;

/**
 * <p>
 * 应急预案管理_附件表 服务类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-04
 */
public interface EnclosedService extends IService<Enclosed> {

    /**
     *  获取一条记录对应的附件列表
     * @param sysCodeEnum 系统码枚举
     * @param msttypeEnum 主表枚举
     * @param mstid  记录主键
     * @return
     */
    List<Enclosed> getEncloseds(SysCodeEnum sysCodeEnum, MsttypeEnum msttypeEnum,Integer mstid);


    /**
     *  删除一条记录对应的附件列表
     * @param sysCodeEnum 系统码枚举
     * @param msttypeEnum 主表枚举
     * @param mstid  记录主键
     */
    void deleteEncloseds(SysCodeEnum sysCodeEnum, MsttypeEnum msttypeEnum,Integer mstid);

    /**
     *  保存一条记录对应的附件列表
     * @param sysCodeEnum 系统码枚举
     * @param msttypeEnum 主表枚举
     * @param mstid  记录主键
     */
    void saveEncloseds(SysCodeEnum sysCodeEnum, MsttypeEnum msttypeEnum,Integer mstid,List<Enclosed> enclosed);








}
