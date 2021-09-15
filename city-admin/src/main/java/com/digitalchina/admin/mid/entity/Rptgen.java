package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 生成的报告
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Data
@TableName("warn.warn_rptgen")
@ApiModel(value = "Rptgen对象", description = "生成的报告")
public class Rptgen implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "报告ID")
    private Integer rgid;

    @ApiModelProperty(value = "报告模板ID")
    private Integer rdid;

    @ApiModelProperty(value = "报告名称")
    private String rgnm;

    @ApiModelProperty(value = "报告状态")
    private Integer rgstat;

    @ApiModelProperty(value = "报告存储路径")
    private String rgpath;

    @ApiModelProperty(value = "日志信息")
    private String lginfo;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "报告周期（10日报，20周报，30月报，40季报，50半年报，60年报）")
    @TableField(exist = false)
    private Integer rpitv;

    public static final String RGID = "rgid";

    public static final String RDID = "rdid";

    public static final String RGNM = "rgnm";

    public static final String RGSTAT = "rgstat";

    public static final String RGPATH = "rgpath";

    public static final String LGINFO = "lginfo";

    public static final String CRDT = "crdt";

    @Override
    public String toString() {
        return "Rptgen{" +
                "rgid=" + rgid +
                ", rdid=" + rdid +
                ", rgnm=" + rgnm +
                ", rgstat=" + rgstat +
                ", rgpath=" + rgpath +
                ", lginfo=" + lginfo +
                ", crdt=" + crdt +
                "}";
    }
}
