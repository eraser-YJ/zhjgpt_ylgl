package com.digitalchina.admin.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@TableName("CONFIG")
@ApiModel(value = "Config对象", description = "")
@NoArgsConstructor
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据表")
    private String tb;

    @ApiModelProperty(value = "数据表中文名")
    private String tbnm;

    @ApiModelProperty(value = "数据字段")
    private String fd;

    @ApiModelProperty(value = "数据字段中文")
    private String fdnm;

    @ApiModelProperty(value = "顺序")
    private Double sort;

    @ApiModelProperty(value = "是否为条件")
    private Double condition;

    @ApiModelProperty(value = "是否可编辑")
    private Double edit;

    @ApiModelProperty(value = "类型（text文本、date日期、dept部门下拉）")
    private String type;

    @ApiModelProperty(value = "是否列表展示")
    private Double list;

    @ApiModelProperty("字段在不同系统可见性掩码，系统码:城市运行1,智慧建管2，要两个系统都可见则 1 | 2 =3")
    private Integer visibleMask;

    @ApiModelProperty("字段显示顺序")
    private Integer fdsort;

    @ApiModelProperty("condition相关，查询操作方法，如eq,like等")
    private String op;

    @ApiModelProperty("condition相关，tree或dict类型时，此字段表示前端取相关数据的api uri")
    private String source;

    @ApiModelProperty("condition相关，tree或dict类型时，前端取回的相关数据用作回传id的字段名")
    private String sourceDataIdField;

    @ApiModelProperty("condition相关，tree或dict类型时，前端取回的相关数据用作显示label文本的字段名")
    private String sourceDataLabelField;

    @ApiModelProperty("字典值类数据code和name都在表中存储，此字段定义了此code对应的name字段名，前端code变化后同步将相关name写入这个字段")
    private String relateNameField;

    @ApiModelProperty("新增时是否在前端显示成组件接受输入")
    private Double addable;

    public Config(String fd, String fdnm) {
        this.fd = fd;
        this.fdnm = fdnm;
    }

    public Config(String fd, String fdnm, String type) {
        this(fd, fdnm);
        this.setType(type);
    }

    public static final String TB = "TB";

    public static final String TBNM = "TBNM";

    public static final String FD = "FD";

    public static final String FDNM = "FDNM";

    public static final String SORT = "SORT";

    public static final String CONDITION = "CONDITION";

    public static final String EDIT = "EDIT";

    public static final String TYPE = "TYPE";

    public static final String LIST = "LIST";

}
