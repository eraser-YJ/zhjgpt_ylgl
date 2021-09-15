package com.digitalchina.admin.mid.entity.emergency;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户区域授权表
 * </p>
 *
 * @author Auto
 * @since 2019-12-06
 */
@Data
@TableName("emergency.em_user_area")
@ApiModel(value="EmUserArea对象", description="用户区域授权表")
public class EmUserArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer uid;

    @ApiModelProperty(value = "区域id集合")
    private Integer[] aids;

    public static final String ID = "id";

    public static final String UID = "uid";

    public static final String AIDS = "aids";

}
