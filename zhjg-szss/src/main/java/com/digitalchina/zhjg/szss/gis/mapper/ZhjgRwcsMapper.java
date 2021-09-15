package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgGLF;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgRltj;
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
public interface ZhjgRwcsMapper extends BaseMapper<ZhjgRltj> {


    /**
     * 智慧供热-统计查询-热网参数分析
     * @return
     */

    List<ZhjgRltj> selectZhjgRwcsTj(Page<ZhjgRltj> page, @Param("code") String code,@Param("glqy") String glqy,@Param("glfmc") String glfmc,@Param("hrzmc") String hrzmc);

    /**
     * 锅炉房档案
     * @param page
     * @param code
     * @param glqy
     * @param glfmc
     * @param startTime
     * @param endTime
     * @return
     */
    List<ZhjgGLF> selectZhjgGlfda(Page<ZhjgGLF> page, @Param("code")String code, @Param("glqy")String glqy, @Param("glfmc")String glfmc, @Param("startTime") String startTime, @Param("endTime")String endTime);

    List<ZhjgGLF> selectZhjgGlfda(@Param("code")String code, @Param("glqy")String glqy, @Param("glfmc")String glfmc, @Param("startTime") String startTime, @Param("endTime")String endTime);

    /**
     * 换热站档案
     * @param page
     * @param code
     * @param glqy
     * @param ssry
     * @param hrzmc
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String,Object>> selectZhjgHRZ(Page page,@Param("code")String code,@Param("glqy")String glqy,@Param("ssry")String ssry,@Param("hrzmc")String hrzmc, @Param("startTime")String startTime,@Param("endTime")String endTime);

    List<Map<String,Object>> selectZhjgHRZ(@Param("code")String code,@Param("glqy")String glqy,@Param("ssry")String ssry,@Param("hrzmc")String hrzmc, @Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 供热企业档案
     * @param page
     * @param code
     * @param glqy
     * @return
     */
    List<Map<String,Object>> selectZhjgGrqyDA(Page page,@Param("code")String code,@Param("glqy")String glqy);

    /**
     * 供热企业档案导出
     * @param code
     * @param glqy
     * @return
     */
    List<Map<String,Object>> selectZhjgGrqyDA(@Param("code")String code,@Param("glqy")String glqy);

    /**
     * 固定测温点
     * @param page
     * @param code
     * @param glqy
     * @param ssxq
     * @param yhmc
     * @param sbzt
     * @return
     */
    List<Map<String,Object>> selectZhjgGdcwd(Page page,@Param("code")String code,@Param("glqy")String glqy,@Param("ssxq")String ssxq,@Param("yhmc")String yhmc,@Param("sbzt")String sbzt);

    /**
     * 固定测温点导出
     * @param code
     * @param glqy
     * @param ssxq
     * @param yhmc
     * @param sbzt
     * @return
     */
    List<Map<String,Object>> selectZhjgGdcwd(@Param("code")String code,@Param("glqy")String glqy,@Param("ssxq")String ssxq,@Param("yhmc")String yhmc,@Param("sbzt")String sbzt);

    /**
     * 热用户信息
     * @param page
     * @param code
     * @param glqy
     * @param ssxq
     * @param yhmc
     * @param ssry
     * @param sfgdcw
     * @return
     */
    List<Map<String,Object>> selectZhjgRyhxx(Page page,@Param("code")String code,@Param("glqy")String glqy,@Param("ssxq")String ssxq,@Param("yhmc")String yhmc,@Param("ssry")String ssry,@Param("sfgdcw")String sfgdcw);

    /**
     * 热用户信息导出
     * @param code
     * @param glqy
     * @param ssxq
     * @param yhmc
     * @param ssry
     * @param sfgdcw
     * @return
     */
    List<Map<String,Object>> selectZhjgRyhxx(@Param("code")String code,@Param("glqy")String glqy,@Param("ssxq")String ssxq,@Param("yhmc")String yhmc,@Param("ssry")String ssry,@Param("sfgdcw")String sfgdcw);
}
