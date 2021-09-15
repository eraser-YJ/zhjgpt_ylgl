package com.digitalchina.modules.constant.enums;

/**
 * <p>
 * 任务状态
 * </p>
 *
 * @author liuping
 * @since 2019-08-19
 */
public enum TaskStatusEnum {

    WAITING(0,"等待运算"),
    RUNNING(1,"运算中"),
    COMPLETE(2,"运算完成"),
    ABNORMAL(9,"运算异常");

    private Integer code;

    private String desc;

    private TaskStatusEnum(Integer code, String desc) {
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
