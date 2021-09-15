package com.digitalchina.event;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.digitalchina.event.mid.entity.Admdiv;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 行政区划（已有，无需创建） admin里需要同步修改，被远程调用了
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value="Admdiv对象", description="行政区划（已有，无需创建）")
public class AdmdivDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "区划ID")
    @TableId("adid")
    private Integer adid;

    @ApiModelProperty(value = "父级ID")
    @TableId("adpid")
    private Integer adpid;

    @ApiModelProperty(value = "区划名称")
    private String adnm;

    @ApiModelProperty(value = "区划全称")
    private String adflnm;

    @ApiModelProperty(value = "区划族谱")
    private String[] adupnms;

    @ApiModelProperty(value = "区划级别")
    private Integer adlev;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    @ApiModelProperty(value = "拥有的子节点列表")
    private List<AdmdivDto> children;

    public AdmdivDto(Admdiv admdiv){
        if (admdiv == null) {
            return;
        }
        BeanUtil.copyProperties(admdiv, this);
    }

    /**
     * 将集合组织成树状对象
     *
     * @param list
     * @return
     */
    @Transient
    public static List<AdmdivDto> makeTree(List<AdmdivDto> list) {
        List<AdmdivDto> treeList = new ArrayList<AdmdivDto>();
        for (AdmdivDto tree : list) {
            // 找到根
            if (tree.getAdpid() == null) {
                treeList.add(tree);
            }
            // 找到子
            for (AdmdivDto item : list) {
                if (null != item.getAdpid() && null != tree.getAdid() && item.getAdpid().equals(tree.getAdid())) {
                    if (tree.getChildren() == null) {
                        tree.setChildren(new ArrayList<AdmdivDto>());
                    }
                    tree.getChildren().add(item);
                }
            }
        }
        return treeList;
    }
}
