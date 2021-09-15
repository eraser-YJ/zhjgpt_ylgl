package com.digitalchina.admin.mid.entity;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.digitalchina.admin.utils.PgArrayTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 组合属性值
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Data
@TableName("warn.warn_cpspv")
@ApiModel(value = "Cpspv对象", description = "组合属性值")
public class Cpspv implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组合属性值ID")
    @TableId("cpvid")
    private Integer cpvid;

    @ApiModelProperty(value = "组合属性ID")
    private Integer cpid;

    /**
     * @see PgArrayTypeHandler
     */
    @ApiModelProperty(value = "属性值", required = true)
    private String[] cpvs;

//	/**
//	 * @see SourceTypeEnum
//	 */
//	@ApiModelProperty(value = "数据来源类型")
//	private Integer srctype;

    @ApiModelProperty(value = "禁用启用（-1禁用，0启用但不处理，1启用且处理）", required = true)
    private Integer stat;

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

    public static final String CPID = "cpid";

    public static final String CPVS = "cpvs";

    public static final String STAT = "stat";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "Cpspv{" + "cpvid=" + cpvid + ", cpid=" + cpid + ", cpvs=" + cpvs + ", stat=" + stat + ", crdt=" + crdt + ", modt=" + modt + "}";
    }
}
