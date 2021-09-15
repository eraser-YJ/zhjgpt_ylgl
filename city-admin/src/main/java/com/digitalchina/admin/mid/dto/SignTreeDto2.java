package com.digitalchina.admin.mid.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.digitalchina.admin.mid.entity.SignTree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *体征树
 * </p>
 *
 * @author liuping
 * @since 2019-10-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="SignTreeDto2对象", description="体征树")
public class SignTreeDto2 extends SignTree {

    private static final long serialVersionUID = -8874995890459127691L;

    @ApiModelProperty(value = "子节点总权重,叶子节点为空")
    private BigDecimal totalWeight;

    @ApiModelProperty(value = "是否可以调整权重 (0 否 1 是)")
    private Integer hasAdjustWeight;

    @ApiModelProperty(value = "是否可以编辑 (0 否 1 是)")
    private Integer hasEdit;

    @ApiModelProperty(value = "是否可以查看 (0 否 1 是)")
    private Integer hasVeiw;

    @ApiModelProperty(value = "是否可以删除 (0 否 1 是)")
    private Integer hasRemove;

    @ApiModelProperty(value = "是否可以增加子节点 (0 否 1 是)")
    private Integer hasAddNode;

    @ApiModelProperty(value = "是否可以管理关联指标 (0 否 1 是)")
    private Integer hasLinkNode;

    @ApiModelProperty(value = "拥有的子节点列表")
    private List<SignTreeDto2> children;

    /**
     * 组织树或子树
     * @param list
     * @return
     */
    @Transient
    public static  List<SignTreeDto2> makeTree(List<SignTreeDto2> list) {
        Integer rootLevel = -1;
        if(ObjectUtil.isNotEmpty(list)){
            //计算子树根所在层级
            rootLevel = list.stream().map(SignTreeDto2::getNlevel).reduce(Integer::min).get();
        }
        List<SignTreeDto2> treeList = new ArrayList<SignTreeDto2>();
        for (SignTreeDto2 tree : list) {
            // 找到根
            if (tree.getTfid() == null || tree.getTfid().equals(0)||tree.getNlevel().equals(rootLevel)) {
                treeList.add(tree);
            }
            // 找到子
            for (SignTreeDto2 item : list) {
                if (null != item.getTfid() && null != tree.getId() && item.getTfid().equals(tree.getId())) {
                    if (tree.getChildren() == null) {
                        tree.setChildren(new ArrayList<SignTreeDto2>());
                    }
                    tree.getChildren().add(item);
                }
            }
        }
        return treeList;
    }

    public SignTree toSignTree(){
        SignTree signTree = new SignTree();
        BeanUtil.copyProperties(this,signTree);
        return signTree;
    }
}
