package com.digitalchina.admin.mid.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@TableName("sign.sign_tree")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="SignTree对象", description="")
public class SignTree implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String PATH_SEPARATOR = ",";
    @TableId
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "树版本")
    private Integer tid;

    @ApiModelProperty(value = "父级id")
    private Integer tfid;

    @ApiModelProperty(value = "父编号列表，用,隔开")
    private String tfids;

    @ApiModelProperty(value = "指标码")
    private String ncode;

    @ApiModelProperty(value = "指标名称")
    private String nname;

    @ApiModelProperty(value = "指标描述")
    private String nmeno;

    @ApiModelProperty(value = "指标层级")
    private Integer nlevel;

    @ApiModelProperty(value = "权重")
    private BigDecimal weight;

    @ApiModelProperty(value = "是否区域性指标(0 城市性 1 区域性 2 复合型)")
    private Integer isarea;

    @ApiModelProperty(value = "是否重点指标(0 否 1 是)")
    private Integer iskey;

    @ApiModelProperty(value = "量化方法主键")
    private Integer qid;

    @ApiModelProperty(value = "量化方法参数")
    private JSONObject qparm;

    @ApiModelProperty(value = "分级方法主键")
    private Integer gid;

    @ApiModelProperty(value = "分级方法参数")
    private JSONArray gparm;

    @ApiModelProperty(value = "启用状态(0 否 1 是)")
    private Integer status;

    @ApiModelProperty(value = "更新频率")
    private String frequency;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modt;

    @ApiModelProperty(value = "序号")
    private Integer sort;

    @ApiModelProperty(value = "是否底层指标（0否 1是）")
    private Integer type;

    public static final String ID = "id";

    public static final String TID = "tid";

    public static final String TFID = "tfid";

    public static final String TFIDS = "tfids";

    public static final String NCODE = "ncode";

    public static final String NNAME = "nname";

    public static final String NMENO = "nmeno";

    public static final String NLEVEL = "nlevel";

    public static final String WEIGHT = "weight";

    public static final String ISAREA = "isarea";

    public static final String ISKEY = "iskey";

    public static final String QID = "qid";

    public static final String QPARM = "qparm";

    public static final String GID = "gid";

    public static final String GPARM = "gparm";

    public static final String STATUS = "status";

    public static final String FREQUENCY = "frequency";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    public static final String SORT = "sort";

    public static final String TYPE = "type";

    /**
     * 获取自身的路径
     *
     * @return
     */
    @Transient
    public String getOwnPath() {
        return getOwnPath(tfids, id);
    }

    @Transient
    public static String getOwnPath(String tfids, Integer id) {
        String pathSuffix = id + PATH_SEPARATOR;
        return StringUtils.isEmpty(tfids) ? pathSuffix : tfids + pathSuffix;
    }


}
