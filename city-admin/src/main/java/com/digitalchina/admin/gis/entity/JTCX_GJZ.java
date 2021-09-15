package com.digitalchina.admin.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Ryan
 * @since 2020-03-11
 */
@Data
@TableName("JTCX_GJZ")
@ApiModel(value = "JTCX_GJZ对象", description = "")
public class JTCX_GJZ implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "objectid")
    private Integer objectid;

    @ApiModelProperty(value = "shape")
    private String shape;

    @ApiModelProperty(value = "id")
    private int id;

    @ApiModelProperty(value = "站名")
    private String zm;

    @ApiModelProperty(value = "线路名")
    private String xlm;

    @ApiModelProperty(value = "经度")
    private Double jd;

    @ApiModelProperty(value = "纬度")
    private Double wd;

    @ApiModelProperty(value = "xllx")
    private String xllx;

    @ApiModelProperty(value = "xlcd")
    private Double xlcd;

    @ApiModelProperty("pjzj")
    private Double pjzj;

    @ApiModelProperty("zdlx")
    private String zdlx;

    public static final String OBJECTID = "OBJECTID";

    public static final String SHAPE = "SHAPE";

    public static final String ID = "ID";

    public static final String ZM = "ZM";

    public static final String XLM = "XLM";

    public static final String JD = "JD";

    public static final String WD = "WD";

    public static final String XLLX = "XLLX";

    public static final String XLCD = "XLCD";
    public static final String PJZJ = "PJZJ";
    public static final String ZDLX = "ZDLX";

}
