package com.digitalchina.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.modules.constant.enums.SysCodeEnum;
import com.digitalchina.modules.entity.Enumcfg;

import java.util.List;

/**
 * <p>
 * 枚举配置 服务类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
public interface EnumcfgService extends IService<Enumcfg> {

    List<Enumcfg> getEnumcfgList(SysCodeEnum sysCodeEnum,String clsnm);

    Enumcfg getEnumcfg(SysCodeEnum sysCodeEnum,String clsnm,Integer eikey);

    Enumcfg getEnumcfg(SysCodeEnum sysCodeEnum,String clsnm,String etkey);

}
