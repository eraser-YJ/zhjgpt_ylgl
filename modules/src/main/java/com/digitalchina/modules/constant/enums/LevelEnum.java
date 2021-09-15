package com.digitalchina.modules.constant.enums;

/**
 * @description: 用于指标树，区域层级
 * @author: cwc
 * @date: 2019/8/8 14:41
 **/
public enum LevelEnum {

    FIRST_LEV(1, "第一层级"),
    SECONDE_LEV(2, "第二层级"),
    THREE_LEV(3, "第三层级"),
    FOUR_LEV(4, "第四层级");

    private int level;

    private String name;

    private LevelEnum(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
