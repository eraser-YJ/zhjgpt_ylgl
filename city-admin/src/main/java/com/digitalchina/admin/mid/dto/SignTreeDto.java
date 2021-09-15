package com.digitalchina.admin.mid.dto;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 非底层节点
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="SignTreeDto对象", description="非底层节点")
public class SignTreeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "树版本")
    @NotNull(groups = {SignTreeDto.root.class,SignTreeDto.node.class},message = "树版本不能为空")
    private Integer tid;

    @ApiModelProperty(value = "父级id")
    @NotNull(groups = {SignTreeDto.node.class},message = "父级id不能为空")
    private Integer tfid;

    @ApiModelProperty(value = "指标码")
    private String ncode;

    @ApiModelProperty(value = "指标名称")
    @NotNull(groups = {SignTreeDto.root.class,SignTreeDto.node.class},message = "指标名称不能为空")
    private String nname;

    @ApiModelProperty(value = "指标描述")
    private String nmeno;


    @ApiModelProperty(value = "分级方法主键")
    @NotNull(groups = {SignTreeDto.root.class,SignTreeDto.node.class},message = "分级方法主键不能为空")
    private Integer gid;

    @ApiModelProperty(value = "分级方法参数")
    private JSONArray gparm;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modt;

    public @interface root{};
    public @interface node{};
}
