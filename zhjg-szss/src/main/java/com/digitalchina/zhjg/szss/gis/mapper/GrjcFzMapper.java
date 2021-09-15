package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.GrjcFz;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrjcFzMapper extends BaseMapper<GrjcFz> {

    List<GrjcFz> selectGrjcFz(Page<GrjcFz> page, GrjcFz grjcFz);

    /**
     * 插入供热监测阀值
     * @param grjcFz
     * @return
     */
    Integer insertGrjcFc(GrjcFz grjcFz);
}
