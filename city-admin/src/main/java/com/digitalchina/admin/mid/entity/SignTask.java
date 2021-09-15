package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@TableName("sign.sign_task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="SignTask对象", description="")
public class SignTask implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    private Integer id;

    @ApiModelProperty(value = "实时(0)、汇总(1)")
    private Integer type;

    @ApiModelProperty(value = "year、month、day、hour、minute、second")
    private String rate;

    @NotNull(message = "开始时间不能为空")
    @Pattern(regexp = "^(\\d{4})-(\\d{2})-(\\d{2})$",message = "开始时间日期格式应为:yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String starttime;

    @NotNull(message = "结束时间不能为空")
    @Pattern(regexp = "^(\\d{4})-(\\d{2})-(\\d{2})$",message = "结束时间日期格式应为:yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String endtime;

    @NotNull(message = "树版本不能为空")
    @ApiModelProperty(value = "树版本")
    private Integer tid;

    @ApiModelProperty(value = "变动结果表ID")
    private Integer hid;

    @ApiModelProperty(value = "0：等待运算、1：运算中、 2：运算完成、9：运算异常")
    private Integer status;

    @ApiModelProperty(value = "异常信息")
    private String error;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modt;

    @TableField(exist = false)
    @ApiModelProperty(value = "树版本名称，拓展字段，查询列表中展示用")
    private String treeVerName;

    public static final String ID = "id";

    public static final String TYPE = "type";

    public static final String RATE = "rate";

    public static final String STARTTIME = "starttime";

    public static final String ENDTIME = "endtime";

    public static final String TID = "tid";

    public static final String HID = "hid";

    public static final String STATUS = "status";

    public static final String ERROR = "error";

    public static final String MODT = "modt";

}
