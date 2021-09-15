package com.digitalchina.admin.mid.dto.layer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <图层条件>
 *
 * @author lihui
 * @since 2020年3月10日
 */
@Data
@ApiModel("图层条件")
@NoArgsConstructor
@AllArgsConstructor
public class LayerCondition {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("字段")
    private String field;

    @ApiModelProperty("类型（text文本、date日期、number、dict、tree）")
    private String type;

    @ApiModelProperty("值（多个日期逗号隔开）")

    private String data;

    @ApiModelProperty("查询操作方法，如eq,like等")
    private String op;

    @ApiModelProperty("tree或dict类型时，此字段表示前端取相关数据的api uri")
    private String source;

    @ApiModelProperty("tree或dict类型时，前端取回的相关数据用作回传id的字段名")
    private String sourceDataIdField;

    @ApiModelProperty("tree或dict类型时，前端取回的相关数据用作显示label文本的字段名")
    private String sourceDataLabelField;

    public LayerCondition(String name, String field, String type) {
        super();
        this.name = name;
        this.field = field;
        this.type = type;
    }

    public LayerCondition(String name, String field, String type, String op) {
        this.name = name;
        this.field = field;
        this.type = type;
        this.op = op;
    }

    public LayerCondition(String name, String field, String type, String op, String source, String sourceDataIdField, String sourceDataLabelField) {
        this.name = name;
        this.field = field;
        this.type = type;
        this.op = op;
        this.source = source;
        this.sourceDataIdField = sourceDataIdField;
        this.sourceDataLabelField = sourceDataLabelField;
    }
}
