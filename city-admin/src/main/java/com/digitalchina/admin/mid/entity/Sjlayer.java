package com.digitalchina.admin.mid.entity;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 主题图层
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Data
@TableName("warn.warn_sjlayer")
@ApiModel(value="Sjlayer对象", description="主题图层")
public class Sjlayer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组合属性值ID")
    private Integer cpvid;

    @ApiModelProperty(value = "图层ID")
    private Integer lyid;

    @ApiModelProperty(value = "组合属性ID")
    private Integer cpid;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    public void setCrdt(String crdt) {
        this.crdt = StrUtil.isEmpty(crdt) ? new Date() : DateUtil.parse(crdt);
    }

    public void setModt(String modt) {
        this.modt = StrUtil.isEmpty(modt) ? new Date() : DateUtil.parse(modt);
    }

    public static final String CPVID = "cpvid";

    public static final String LYID = "lyid";

    public static final String CPID = "cpid";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "Sjlayer{" +
        "cpvid=" + cpvid +
        ", lyid=" + lyid +
        ", cpid=" + cpid +
        ", crdt=" + crdt +
        ", modt=" + modt +
        "}";
    }
}
