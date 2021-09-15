package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.zhjg.szss.gis.entity.GyldldDto;
import com.digitalchina.zhjg.szss.gis.entity.GyldxdsDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-11-02 14:12
 */
@Component
public interface GyldxdsInfoMapper extends BaseMapper<GyldxdsDto>{

    /**
     * 行道数管理--EXCEL 导入SQL
     * @param list
     * @return
     */
   int insertGyldxdsInfoList(List<GyldxdsDto> list);
}
