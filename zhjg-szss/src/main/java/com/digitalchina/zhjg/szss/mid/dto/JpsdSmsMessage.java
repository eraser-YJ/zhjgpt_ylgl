package com.digitalchina.zhjg.szss.mid.dto;

import lombok.Data;

@Data
public class JpsdSmsMessage {

    public enum StationType {JSD, PSD}

    // 站点类型
    private StationType stationType;
    // 站点编号
    private String stationCode;
    // 积水深度
    private String depth;
}
