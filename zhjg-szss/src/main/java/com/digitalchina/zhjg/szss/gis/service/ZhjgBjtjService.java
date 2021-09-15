package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-08-07 10:19
 */
public interface ZhjgBjtjService extends IService<ZhjgBjtj> {

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
