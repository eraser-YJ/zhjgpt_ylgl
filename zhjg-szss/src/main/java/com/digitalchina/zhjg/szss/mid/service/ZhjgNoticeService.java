package com.digitalchina.zhjg.szss.mid.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;
import com.digitalchina.zhjg.szss.mid.entity.ZhjgNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-08-07 10:19
 */
public interface ZhjgNoticeService extends IService<ZhjgNotice> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    List<ZhjgNotice> selectZhjgNotice(@Param("id") Integer id);

    /**
     * 查询全部
     * @param page
     * @return
     */
    List<ZhjgNotice> selectZhjgAllNotice(Page<ZhjgNotice> page);

    /**
     * 新增notice
     * @param zhjgNotice
     * @return
     */
    Integer insetNotice(ZhjgNotice zhjgNotice);

    /**
     * 根据id删除：
     * @param id
     * @return
     */
    Integer deleteNotice(Integer id);

    /**
     * 根据id修改notice
     * @param zhjgNotice
     * @return
     */
    Integer updateNotice(ZhjgNotice zhjgNotice);
}
