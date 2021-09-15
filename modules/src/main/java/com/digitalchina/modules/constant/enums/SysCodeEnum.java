package com.digitalchina.modules.constant.enums;

/**
 * <p>
 * 业务系统码枚举
 * </p>
 *
 * @author liuping
 * @since 2019-09-24
 */
public enum SysCodeEnum {

    CITYSIGN("citysign", "体征系统"),
    CITYWARN("citywarn", "监测预警系统"),
    CITYEVENT("cityevent", "事件系统"),
    CITYEVENTNEW("cityeventNew", "新事件系统"),
    CITYCITIZENS("citycitizens", "市民互动系统"),
    CITYPOLICY("citypolicy", "决策系统"),
    CITYEMERGENCY("cityemergency", "应急辅助系统"),
    CITYAPP("cityapp", "领导决策APP");

    private String code;

    private String desc;

    SysCodeEnum(String code, String desc) {
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
