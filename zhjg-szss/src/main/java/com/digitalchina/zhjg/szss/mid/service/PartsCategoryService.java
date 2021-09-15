package com.digitalchina.zhjg.szss.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.mid.entity.PartsCategory;

import java.util.List;

public interface PartsCategoryService extends IService<PartsCategory> {

    /**
     * 获取rootId下所有后代分类，并以树型结构返回
     *
     * @param rootId
     * @return
     */
    List<PartsCategory> tree(List<String> modules, Integer rootId, Boolean status);

    /**
     * 创建新类别
     * @param partsCategory
     */
    void create(PartsCategory partsCategory);

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
