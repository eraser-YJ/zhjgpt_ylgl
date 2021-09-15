package com.digitalchina.zhjg.szss.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.mid.entity.ZhjgNotice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-08-07 10:05
 */
@Component
public interface ZhjgNoticeMapper  extends BaseMapper<ZhjgNotice> {

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
     * 根据id删除
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
