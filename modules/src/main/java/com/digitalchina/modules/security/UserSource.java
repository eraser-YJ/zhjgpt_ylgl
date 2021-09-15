package com.digitalchina.modules.security;

/**
 * 用户来源枚举类型
 */
public enum UserSource {
    TYUSER(0,"统一平台用户"),GLUSER(1,"管理用户"),QYUSER(2,"企业用户"),ZJBYSER(3,"住建部用户");

    private String name;
    private int code;

    private UserSource(int code,String name){
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
