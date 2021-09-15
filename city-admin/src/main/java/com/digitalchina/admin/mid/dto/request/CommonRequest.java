package com.digitalchina.admin.mid.dto.request;

import com.digitalchina.admin.mid.dto.layer.LayerProp;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CommonRequest implements Serializable {

    private String code;
    private Integer size;
    private Integer current;

    private Map<String, RequestCondition> filtrate;

    private List<LayerProp> props;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Map<String, RequestCondition> getFiltrate() {
        return filtrate;
    }

    public void setFiltrate(Map<String, RequestCondition> filtrate) {
        this.filtrate = filtrate;
    }

    public List<LayerProp> getProps() {
        return props;
    }

    public void setProps(List<LayerProp> props) {
        this.props = props;
    }

    /**
     * 构造查询条件，这个方法暂时复制原城市运行代码，功能简单，后期再完善增强
     *
     * @return
     */
    public String buildConditions() {
        StringBuilder sb = new StringBuilder();
        sb.append(" where 1=1 ");

        // 没有设置条件，结束返回
        if (this.getFiltrate() == null) {
            return sb.toString();
        }

        for (Map.Entry<String, RequestCondition> conditionEntry : this.getFiltrate().entrySet()) {

            String field = conditionEntry.getKey();
            RequestCondition condition = conditionEntry.getValue();
            if (condition != null) {
                condition.setName(field);
            }

            if (field == null || condition == null || condition.getValue() == null || condition.getValue().length == 0) {
                continue;
            }

            sb.append(condition.build());
        }

        return sb.toString();
    }

    public String buildCols() {
        StringBuilder builder = new StringBuilder();

        for (LayerProp prop : props) {
            builder.append(prop.getField()).append(", ");
        }

        if (builder.lastIndexOf(",") > -1) {
            builder.deleteCharAt(builder.lastIndexOf(","));
        }

        return builder.toString();
    }
}
