package com.digitalchina.zhjg.szss.gis.mapper;

        import com.baomidou.mybatisplus.core.mapper.BaseMapper;
        import org.apache.ibatis.annotations.Param;
        import org.apache.poi.ss.formula.functions.T;
        import org.springframework.stereotype.Component;

        import java.util.List;
        import java.util.Map;

/**
 * 分析评价-统计汇总
 * @author shkstart
 * @create 2020-08-07 10:05
 */
@Component
public interface ZhjgLdFxMapper extends BaseMapper<T> {


    /**
     * 分析评价-绿地分析-绿地率
     * @return
     */
    List<Map<String, String>> selectZhjgLdl();

    /**
     * 分析评价-绿地分析-绿化覆盖率
     * @return
     */
    List<Map<String, String>> selectZhjgLhFgl();

    /**
     * 分析评价-绿地分析-人均绿地面积
     * @return
     */
    List<Map<String, String>> selectZhjgRjLdMj();

    /**
     * 分析评价-绿地分析-每万人拥有公园数
     * @return
     */
    List<Map<String, String>> selectZhjgWrYyGyNum();

    /**
     * 分析评价-年度数据对比分析
     * @return
     */
    List<Map<String, String>> selectZhjgNdSjDb(@Param("dateTime") String dateTime);


}
