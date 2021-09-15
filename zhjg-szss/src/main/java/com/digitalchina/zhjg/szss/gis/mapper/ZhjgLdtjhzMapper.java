package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;
import io.swagger.models.auth.In;
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
public interface ZhjgLdtjhzMapper extends BaseMapper<T> {


    /**
     * 分析评价-统计汇总-人口、面积、绿地统计查询
     * @return
     */
    Map<String, String> selectZhjgZhXxTj();

    /**
     * 分析评价-统计汇总-绿地类型统计
     * @return
     */
    List<Map<String, String>> selectZhjgLdLxTj();

    /**
     * 分析评价-统计汇总-各区域绿地面积分析
     * @return
     */

    Map<String, String> selectZhjgGqyLdmjFx(String dateTime);

    /**
     * 分析评价-统计汇总-行道数统计
     * @return
     */

    List<Map<String, String>> selectZhjgHdstj();


    /**
     *分析评价-统计汇总-绿地类型查询
     * @return
     */
    List<Map<String, String>> selectZhjgLdLx();

    /**
     *分析评价-统计汇总-绿地类型树种数量统计
     * @return
     */
    List<Map<String, String>> selectZhjgLdLxSzNum(@Param("ldlxid")Integer ldlxid);

    /**
     * 分析评价-统计汇总-各区域绿地树种统计
     * @return
     */
    List<Map<String, String>> selectZhjgGqyLdSzTj(@Param("ldlxid") Integer ldlxid);

    /**
     * 分析评价-统计汇总-古树名数数量排名
     * @return
     */
    List<Map<String, String>> selectZhjgGsmsSlPm();


}
