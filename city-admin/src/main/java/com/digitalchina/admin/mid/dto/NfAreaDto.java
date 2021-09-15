package com.digitalchina.admin.mid.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Auto
 * @since 2019-12-05
 */
@Data
@ApiModel(value="NfArea对象", description="")
public class NfAreaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "父级id")
    private Integer ofid;

    @ApiModelProperty(value = "区域码")
    private String ocode;

    @ApiModelProperty(value = "区域名称全称")
    private String oname;

    @ApiModelProperty(value = "区域层级")
    private Integer olevel;

    @ApiModelProperty(value = "区域名称简称")
    private String sname;

    @ApiModelProperty(value = "是否选中 0-否 ，1-是 ")
    private Integer state;

    @ApiModelProperty(value = "拥有的子节点列表 ")
    private List<NfAreaDto> children;

    /**
     * 将集合组织成树状对象
     *
     * @param list
     * @return
     */
    @Transient
    public static List<NfAreaDto> makeTree(List<NfAreaDto> list) {
        List<NfAreaDto> treeList = new ArrayList<NfAreaDto>();
        for (NfAreaDto tree : list) {
            // 找到根
            if (tree.getOfid() == null || tree.getOfid().equals(0)) {
                treeList.add(tree);
            }
            // 找到子
            for (NfAreaDto item : list) {
                if (null != item.getOfid() && null != tree.getId() && item.getOfid().equals(tree.getId())) {
                    if (tree.getChildren() == null) {
                        tree.setChildren(new ArrayList<NfAreaDto>());
                    }
                    tree.getChildren().add(item);
                }
            }
        }
        return treeList;
    }

}
