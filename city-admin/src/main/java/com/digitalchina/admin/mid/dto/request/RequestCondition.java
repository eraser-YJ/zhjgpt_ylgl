package com.digitalchina.admin.mid.dto.request;

import cn.hutool.core.util.StrUtil;

public class RequestCondition {

    private String name;
    private String[] value;
    private String op;
    private String type;
    private String grouping;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }

    public String build() {

        StringBuilder sb = new StringBuilder();

        // null判断，与类型无关
        if ("is null".equalsIgnoreCase(op) || "is not null".equalsIgnoreCase(op)) {
            sb.append(" and " + name + " " + op);
        } else {

            switch (type) {
                case "date":
                case "date_range":
                    if (StrUtil.isNotBlank(value[0])) { // 时间范围左值
                        sb.append(" and " + "TO_CHAR(" + name + ",'yyyy-MM-dd')" + ">=" + "'" + value[0] + "'");
                    }
                    if (StrUtil.isNotBlank(value[1])) { // 时间范围右值
                        sb.append(" and " + "TO_CHAR(" + name + ",'yyyy-MM-dd')" + "<=" + "'" + value[1] + "'");
                    }
                    break;
                case "text":
                    switch (op) {
                        case "eq":
                            sb.append(" and " + name + "='" + value[0] + "'");
                            break;
                        case "like":
                            sb.append(" and " + name + " like '%" + value[0] + "%'");
                            break;
                    }
                    break;
                case "number":
                case "tree":
                case "dict":
                    sb.append(" and " + name + "=" + value[0]);
                    break;
            }
        }

        return sb.toString();
    }
}
