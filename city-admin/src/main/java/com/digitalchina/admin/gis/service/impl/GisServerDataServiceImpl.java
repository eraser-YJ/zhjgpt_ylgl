package com.digitalchina.admin.gis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.gis.entity.JTCX_GJZ;
import com.digitalchina.admin.gis.mapper.ConfigMapper;
import com.digitalchina.admin.gis.mapper.GisServerDataMapper;
import com.digitalchina.admin.gis.service.GisServerDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ryan
 * @since 2020-03-11
 */
@Service
@Slf4j
public class GisServerDataServiceImpl extends ServiceImpl<GisServerDataMapper, JTCX_GJZ> implements GisServerDataService {

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public List<Map<String, Object>> listGreenbeltsByYear(int year) {
        String sql = "select objectid, name, to_number(to_char(date_build, 'yyyy')) as year, cate_level_1_name as type , wkt, srid from gyld_ld "
                + " where wkt is not null and "
                + " (date_build is null or to_number(to_char(date_build, 'yyyy')) <= " + year + ")";
        return configMapper.customListAll(sql);
    }
}
