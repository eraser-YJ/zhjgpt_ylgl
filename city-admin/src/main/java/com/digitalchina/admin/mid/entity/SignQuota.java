package com.digitalchina.admin.mid.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 体征树指标表(仅叶子节点)
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@TableName("sign.sign_quota")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="SignQuota对象", description="体征树指标表(仅叶子节点)")
public class SignQuota implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "指标码")
    @NotNull(message = "指标码不能为空")
    private String ncode;

    @ApiModelProperty(value = "指标名称")
    @NotNull(message = "指标名称不能为空")
    private String nname;

    @ApiModelProperty(value = "指标描述")
    private String nmeno;

    @ApiModelProperty(value = "是否区域性指标(0 城市性 1 区域性 2 复合型)")
    @NotNull(message = "是否区域性指标不能为空")
    private Integer isarea;

    @ApiModelProperty(value = "是否重点指标(0 否 1 是)")
    @NotNull(message = "是否重点指标不能为空")
    private Integer iskey;

    @ApiModelProperty(value = "量化方法主键")
    @NotNull(message = "量化方法主键不能为空")
    private Integer qid;

    @ApiModelProperty(value = "量化方法参数")
    private JSONObject qparm;

    @ApiModelProperty(value = "分级方法主键")
    @NotNull(message = "分级方法主键不能为空")
    private Integer gid;

    @ApiModelProperty(value = "分级方法参数")
    private JSONArray gparm;

    @ApiModelProperty(value = "启用状态(0 否 1 是)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modt;

    public static final String ID = "id";

    public static final String NCODE = "ncode";

    public static final String NNAME = "nname";

    public static final String NMENO = "nmeno";

    public static final String ISAREA = "isarea";

    public static final String ISKEY = "iskey";

    public static final String QID = "qid";

    public static final String QPARM = "qparm";

    public static final String GID = "gid";

    public static final String GPARM = "gparm";

    public static final String STATUS = "status";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

}
