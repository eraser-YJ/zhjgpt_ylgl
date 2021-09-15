package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-08-07 10:05
 */
@Component
public interface ZhjgBjtjMapper extends BaseMapper<ZhjgBjtj> {

    /**
     * 查询部件 根据时间
     * @param page
     * @param startTime
     * @param endTime
     * @param partsCateId
     * @return
     */
    List<ZhjgBjtj> selectZhjgBjtj(Page<ZhjgBjtj> page, String startTime, String endTime, Integer partsCateId);


    /**
     * 查询部件导出 根据时间
     * @param startTime
     * @param endTime
     * @param partsCateId
     * @return
     */
    List<ZhjgBjtj> selectZhjgBjtjExport(String startTime, String endTime, Integer partsCateId);
}
