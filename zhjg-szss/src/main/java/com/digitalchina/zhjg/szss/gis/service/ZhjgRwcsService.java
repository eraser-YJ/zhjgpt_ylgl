package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgGLF;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgRltj;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * 分析评价-统计汇总
 * @author shkstart
 * @create 2020-08-07 10:19
 */
public interface ZhjgRwcsService extends IService<ZhjgRltj> {


    /**
     * 智慧供热-统计查询-热网参数分析
     * @return
     */

    List<ZhjgRltj> selectZhjgRwcsTj(Page<ZhjgRltj> page, String code,String glqy, String glfmc,String hrzmc);

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
    List<ZhjgGLF> selectZhjgGlfda(Page<ZhjgGLF> page, String code,String glqy,String glfmc,String startTime, String endTime);

    List<ZhjgGLF> selectZhjgGlfda( String code,String glqy,String glfmc,String startTime, String endTime);

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
    List<Map<String,Object>> selectZhjgHRZ(Page page,String code,String glqy,String ssry,String hrzmc,String startTime,String endTime);

    /**
     * 换热站档案导出
     * @param code
     * @param glqy
     * @param ssry
     * @param hrzmc
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String,Object>> selectZhjgHRZ(String code,String glqy,String ssry,String hrzmc,String startTime,String endTime);

    /**
     * 供热企业档案
     * @param page
     * @param code
     * @param glqy
     * @return
     */
    List<Map<String,Object>> selectZhjgGrqyDA(Page page,String code,String glqy);

    /**
     * 供热企业档案导出
     * @param code
     * @param glqy
     * @return
     */
    List<Map<String,Object>> selectZhjgGrqyDA(String code,String glqy);


    List<Map<String,Object>> selectZhjgGdcwd(Page page,String code,String glqy,String ssxq,String yhmc,String sbzt);

    /**
     * 固定测温点导出
     * @param code
     * @param glqy
     * @param ssxq
     * @param yhmc
     * @param sbzt
     * @return
     */
    List<Map<String,Object>> selectZhjgGdcwd(String code,String glqy,String ssxq,String yhmc,String sbzt);

    List<Map<String,Object>> selectZhjgRyhxx(Page page,String code,String glqy,String ssxq,String yhmc,String ssry,String sfgdcw);

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
    List<Map<String,Object>> selectZhjgRyhxx(String code,String glqy,String ssxq,String yhmc,String ssry,String sfgdcw);

}
