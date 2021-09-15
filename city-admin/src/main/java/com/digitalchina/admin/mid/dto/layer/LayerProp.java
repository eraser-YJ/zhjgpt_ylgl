package com.digitalchina.admin.mid.dto.layer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <图层属性>
 *
 * @author lihui
 * @since 2020年3月10日
 */
@Data
@ApiModel("图层属性")
@NoArgsConstructor
@AllArgsConstructor
public class LayerProp {

    public static final String TYPE_TEXT = "text";
    public static final String TYPE_NUMBER = "number";
    public static final String TYPE_DATE = "date";
    public static final String TYPE_DATE_RANGE = "date_range";
    //  public static final String TYPE_THIS_DATE = "this_date";
    public static final String TYPE_DICT = "dict";
    public static final String TYPE_TREE = "tree";
    public static final String TYPE_FILE = "file";

    @ApiModelProperty("文本")
    private String text;

    @ApiModelProperty("字段")
    private String field;

    @ApiModelProperty("字段类型")
    private String type;

    @ApiModelProperty("是否以表格展出")
    private Boolean table = true;

    @ApiModelProperty("能否编辑")
    private Boolean edit = false;

    @ApiModelProperty("值")
    private String val;

    @ApiModelProperty("tree或dict类型时，此字段表示前端取相关数据的api uri")
    private String source;

    @ApiModelProperty("tree或dict类型时，前端取回的相关数据用作回传id的字段名")
    private String sourceDataIdField;

    @ApiModelProperty("tree或dict类型时，前端取回的相关数据用作显示label文本的字段名")
    private String sourceDataLabelField;

    @ApiModelProperty("字典值类数据code和name都在表中存储，此字段定义了此code对应的name字段名，前端code变化后同步将相关name写入这个字段")
    private String relateNameField;

    @ApiModelProperty("新增时是否在前端显示成组件接受输入")
    private Boolean addable = false;

    @ApiModelProperty("是否为parts_base表中的属性")
    private String baseTable;

    public LayerProp(String field, String type, String val) {
        this.field = field;
        this.type = type;
        this.val = val;
    }

    public LayerProp(String text, String field, String type, Double table, Double edit, Double addable, String baseTable) {
        super();
        this.text = text;
        this.field = field;
        this.type = type;
        this.table = Double.valueOf(1).equals(table);
        this.edit = Double.valueOf(1).equals(edit);
        this.addable = Double.valueOf(1).equals(addable);
        this.baseTable = baseTable;
    }

    public LayerProp(String text, String field, String type, Double table,
                     Double edit, Double addable, String baseTable, String source, String sourceDataIdField,
                     String sourceDataLabelField, String relateNameField) {
        this(text, field, type, table, edit, addable, baseTable);
        this.source = source;
        this.sourceDataIdField = sourceDataIdField;
        this.sourceDataLabelField = sourceDataLabelField;
        this.relateNameField = relateNameField;
    }

    @JsonIgnore
    public String getSqlCause() {

        if (getVal() == null) {
            return null;
        }

        switch (getType()) {
            case LayerProp.TYPE_DATE:
                //case LayerProp.TYPE_THIS_DATE:
                return "to_date('" + getVal() + "', 'yyyy-mm-dd')";
            case LayerProp.TYPE_DICT:
            case LayerProp.TYPE_TREE:
            case LayerProp.TYPE_NUMBER:
                return getVal();
            case LayerProp.TYPE_TEXT:
            case LayerProp.TYPE_FILE:
                return "'" + getVal() + "'";
            default:
                return null;
        }
    }
}
