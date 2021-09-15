package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.GrjcFz;

import java.util.List;

public interface GrjcFzService {
    List<GrjcFz> selectGrjcFz(Page<GrjcFz> page,GrjcFz grjcFz);

    /**
     * 插入供热监测阀值
     * @param grjcFz
     * @return
     */
    Integer insertGrjcFc(GrjcFz grjcFz);
}
