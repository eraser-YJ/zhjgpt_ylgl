package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.WarnHandle;
import com.digitalchina.zhjg.szss.gis.entity.WarnHandleStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2020-08-27 14:43
 */
@Component
public interface WarnHandleStatusMapper extends BaseMapper<WarnHandleStatus> {
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
    List<Map<String,String>>  selectWarnHandleStatus(String sjbh, String jcdlx, String status);
}
