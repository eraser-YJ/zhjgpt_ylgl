package com.digitalchina.modules.constant.enums;

public enum SignQuotaStatus {

    ACTIVE(1, "启用"),
    DISABLE(0, "禁用");

    private Integer code;

    private String desc;

    private SignQuotaStatus(Integer code, String desc) {
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
