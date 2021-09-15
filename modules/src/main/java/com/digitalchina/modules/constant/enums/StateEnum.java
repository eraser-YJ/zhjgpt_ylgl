package com.digitalchina.modules.constant.enums;

/**
 * <状态枚举>
 *
 * @author lihui
 * @since 2019年8月2日
 */
public enum StateEnum {

    DELETED(1, "已删除"),
    NOT_DELETED(0, "未删除"),
    DISABLE(1, "禁用"),
    ACTIVE(0, "激活");

    private Integer code;

    private String desc;

    private StateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
