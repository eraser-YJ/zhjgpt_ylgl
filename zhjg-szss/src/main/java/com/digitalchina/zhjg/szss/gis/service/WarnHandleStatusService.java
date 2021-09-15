package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.WarnHandle;
import com.digitalchina.zhjg.szss.gis.entity.WarnHandleStatus;

import java.util.List;
import java.util.Map;

/**
 * 预警处置
 * @author shkstart
 * @create 2020-08-07 10:19
 */
public interface WarnHandleStatusService extends IService<WarnHandleStatus> {
    /**
     * 插入预警处置状态表
     * @param warnHandleStatus
     * @return
     */
    Integer insertWarnHandleStatus(WarnHandleStatus warnHandleStatus);

    /**
     * 查询预警处置状态表 -根据条件-分页查询
     * @param sjbh
     * @param jcdlx
     * @param status
     * @return
     */
    List<Map<String,String>> selectWarnHandleStatus(String sjbh, String jcdlx, String status);
}


