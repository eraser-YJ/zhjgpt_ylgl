package com.digitalchina.event.mid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 行政区划（已有，无需创建） admin里需要同步修改，被远程调用了
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Data
@TableName("public.admdiv")
@ApiModel(value="Admdiv对象", description="行政区划（已有，无需创建）")
public class Admdiv implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "区划ID")
    @TableId(value="adid",type=IdType.INPUT)
    private Integer adid;

    @ApiModelProperty(value = "父级ID")
    @TableId("adpid")
    private Integer adpid;

    @ApiModelProperty(value = "区划名称")
    private String adnm;

    @ApiModelProperty(value = "区划全称")
    private String adflnm;

    @ApiModelProperty(value = "区划族谱")
    private Integer[] adupnms;

    @ApiModelProperty(value = "区划级别")
    private Integer adlev;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    public static final String ADID = "adid";

    public static final String ADPID = "adpid";

    public static final String ADNM = "adnm";

    public static final String ADFLNM = "adflnm";

    public static final String ADUPNMS = "adupnms";

    public static final String ADLEV = "adlev";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "Admdiv{" +
        "adid=" + adid +
        ", adnm=" + adnm +
        ", adflnm=" + adflnm +
        ", adupnms=" + adupnms +
        ", adlev=" + adlev +
        ", crdt=" + crdt +
        ", modt=" + modt +
        "}";
    }
}
