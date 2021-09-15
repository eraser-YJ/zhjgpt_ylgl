package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.dto.NfAreaDto;
import com.digitalchina.admin.mid.entity.emergency.EmUserArea;

import java.util.List;

/**
 * <p>
 * 用户区域授权表 服务类
 * </p>
 *
 * @author Auto
 * @since 2019-12-06
 */
public interface EmUserAreaService extends IService<EmUserArea> {

    List<NfAreaDto> getAreaDictByUser(Integer uid);

}
