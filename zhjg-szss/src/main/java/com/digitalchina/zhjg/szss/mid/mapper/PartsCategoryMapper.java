package com.digitalchina.zhjg.szss.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.zhjg.szss.mid.entity.PartsCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

public interface PartsCategoryMapper extends BaseMapper<PartsCategory> {
    List<PartsCategory> selectPartsCate(@Param("modules") List<String> modules, @Param("rootId") Integer rootId, @Param("status") Boolean status);

    @Update("update szss.parts_category set id_path=#{cate.idPath, typeHandler=com.digitalchina.zhjg.szss.utils.PgArr2IntArrHandler} where id=#{cate.id}")
    void updateIdPaths(@Param("cate") PartsCategory partsCategory);

    /**
     * 查询植物品种
     */

    List<String> selectSzData();

    /**
     * 查询生长状况
     * @return
     */

    List<String> selectSzzkData();

    /**
     * 查询养护等级
     * @return
     */
    List<String> selectYhdjData();

    /**
     * 查询道路等级
     * @return
     */
    List<String> selectDldjData();

    /**
     * 查询绿地类别
     * @return
     */
    List<String> selectLdlbData();

    /**
     * 查询绿地性质
     * @return
     */
    List<String> selectLdxzData();

    /**
     * 查询绿地等级
     * @return
     */
    List<String> selectLddjData();


    /**
     * 查询绿地养护类型
     * @return
     */
    List<String> selectLdyhlxData();

    /**
     * 查询绿地养护单位
     * @return
     */
    List<String> selectLdyhdwData();

    /**
     * 查询公园等级
     */
    List<String> selectgydjData();

    /**
     * 查询公园开放情况
     */
    List<String> selectgykfqkData();

    /**
     * 查询公园养护单位
     */
    List<String> selectgyyhdwData();



}
