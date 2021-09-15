package com.digitalchina.event.mid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.digitalchina.modules.security.UserSource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author shkstart
 * @create 2020-10-10 9:54
 */
@Data
@TableName("public.busievent_user")
@ApiModel(value="BusieventUser对象", description="事件账户关系表")
@NoArgsConstructor
@AllArgsConstructor
public class BusieventUser implements Serializable {

    @ApiModelProperty(value = "编号")
    @TableId("objectid")
    private Integer objectid;

    @ApiModelProperty(value = "账户ID")
    @TableId("uid")
    private Integer uid;

    @ApiModelProperty(value = "账户类型")
    @TableId("utype")
    private UserSource utype;

    @ApiModelProperty(value = "事件编号")
    @TableId("beid")
    private Integer beid;


}
