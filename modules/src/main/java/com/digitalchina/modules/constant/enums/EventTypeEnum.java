package com.digitalchina.modules.constant.enums;

/**
 * <p>
 *  事件类型分类
 * </p>
 *
 * @author liuping
 * @since 2019-09-09
 */
public enum EventTypeEnum {

    SOURCE("SOURCE","来源"),
    AREA("AREA","地区"),
    TYPE("TYPE","类型"),
    DEPT("DEPT","部门");

    private String code;

    private String desc;

    EventTypeEnum(String code, String desc) {
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
