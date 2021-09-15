package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.zhjg.szss.gis.entity.GyldldDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-11-02 14:12
 */
@Component
public interface GyldInfoMapper extends BaseMapper<GyldldDto>{

    /**
     * 绿地信息管理管理--EXCEL 导入SQL
     * @param list
     * @return
     */
   int insertGyldInfoList(List<GyldldDto> list);

    /**
     * 绿地编号查询
     * @return
     */
   List<String> selectLdData();

    /**
     *  区域代码查询
     */
    List<String> selectQyData();
}
