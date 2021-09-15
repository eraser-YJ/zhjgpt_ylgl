package com.digitalchina.event.dto;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import com.digitalchina.event.mid.entity.Bedept;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 部门树
 * </p>
 *
 * @author liuping
 * @since 2019-09-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="部门树", description="")
public class BedeptDto {

    private static final long serialVersionUID = -1166810734652627580L;

    @ApiModelProperty(value = "部门ID")
    private Integer bedid;

    @ApiModelProperty(value = "部门名称")
    private String bdnm;

    @ApiModelProperty(value = "上级部门ID")
    private Integer bdpntid;

    @ApiModelProperty(value = "上级部门ID清单")
    private Integer[] bdpntids;

    @ApiModelProperty(value = "部门类型")
    private Integer bdtype;

    @ApiModelProperty(value = "拥有的子节点列表")
    private List<BedeptDto> children;

    public BedeptDto(Bedept bedept){
        if (bedept == null) {
            return;
        }
        BeanUtil.copyProperties(bedept, this);
    }

    /**
     * 将集合组织成树状对象
     *
     * @param list
     * @return
     */
    @Transient
    public static List<BedeptDto> makeTree(List<BedeptDto> list) {
        List<BedeptDto> treeList = new ArrayList<BedeptDto>();
        for (BedeptDto tree : list) {
            // 找到根
            if (tree.getBdpntid() == null || tree.getBdpntid() == 0) {
                treeList.add(tree);
            }
            // 找到子
            for (BedeptDto item : list) {
                if (null != item.getBdpntid() && null != tree.getBedid() && item.getBdpntid().equals(tree.getBedid())) {
                    if (tree.getChildren() == null) {
                        tree.setChildren(new ArrayList<>());
                    }
                    tree.getChildren().add(item);
                }
            }
        }
        return treeList;
    }
}
