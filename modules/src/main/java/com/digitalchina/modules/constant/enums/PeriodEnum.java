package com.digitalchina.modules.constant.enums;

/**
 * <p>
 *  周期枚举
 * </p>
 *
 * @author liuping
 * @since 2019-09-06
 */
public enum PeriodEnum {

    MINUTE("MINUTE", "分"),
    HOUR("HOUR", "时"),
    DAY("DAY", "天"),
    WEEK("WEEK", "周"),
    MONTH("MONTH", "月"),
    QUARTER("QUARTER", "季"),
    YEAR("YEAR", "年");

    private String code;

    private String desc;

    PeriodEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
