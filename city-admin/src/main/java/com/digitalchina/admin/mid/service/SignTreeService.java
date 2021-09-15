package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.dto.SignTreeDto2;
import com.digitalchina.admin.mid.entity.SignTree;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
public interface SignTreeService extends IService<SignTree> {

    /**
     * 根据版本id复制树
     * @param old 旧版本id
     * @param curr 新版本id
     */
    void copyTree(Integer old,Integer curr);

    /**
     * 生成指标码
     * @param prefix
     * @return
     */
    String getNcode(String prefix);

    /**
     * 根据变动的节点更新路径上的isArea isKey
     * @param old 变动点旧值
     * @param isArea 变动点isArea新值
     * @param isKey 变动点isKey新值
     */
    void updateByChangeNode(SignTree old ,Integer isArea,Integer isKey);

    /**
     * 根据子节点推算IsArea的值
     * @param signTreeLsit
     * @return
     */
    Integer getIsAreaByChild(List<SignTree> signTreeLsit);

    /**
     * 根据子节点推算Iskey的值
     * @param signTreeLsit
     * @return
     */
    Integer getIskeyByChild(List<SignTree> signTreeLsit);

    /**
     * 查询子树，都不传查整个树
     * @param tid 版本id 必传
     * @param root 子树根id 可选
     * @param rootPath 子树根路径 可选
     * @return
     */
    List<SignTreeDto2> queryTreeByroot(Integer tid,Integer root,String rootPath);
}
