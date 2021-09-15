package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.WarnHandle;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-08-27 14:43
 */
@Component
public interface WarnHandleMapper extends BaseMapper<WarnHandle> {
    /**
     * 插入预警处置表
     * @param warnHandle
     * @return
     */
    Integer insertWarnHandle(WarnHandle warnHandle);

    /**
     * 更新预警处置表--根据objectId
     * @param sjbh
     * @param status
     * @param zxclsj
     * @return
     */

    Integer updateWarnHandle(String sjbh, String status, String zxclsj,String jcdlx);

    Integer updateWarnHandleNew(WarnHandle warnHandle);

    /**
     * 查询预警处置表 -根据条件-分页查询
     * @param page
     * @param sjbh
     * @param zdbh
     * @param zdmc
     * @param yjdj
     * @param jcdlx
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    List<WarnHandle>  selectWarnHandle(Page<WarnHandle> page, String sjbh, String zdbh, String zdmc, String yjdj, String jcdlx, String status,String startTime, String endTime);
}
