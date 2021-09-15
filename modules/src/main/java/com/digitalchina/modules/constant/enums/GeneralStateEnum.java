package com.digitalchina.modules.constant.enums;

/**
 * <p>
 * 通用状态枚举
 * </p>
 *
 * @author liuping
 * @since 2019-08-07
 */
public enum GeneralStateEnum {

    YES(1, "是"),
    NO(0, "否");

    private Integer code;

    private String desc;

    private GeneralStateEnum(Integer code, String desc) {
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
