package com.digitalchina.zhjg.szss.gis.entity;

import com.digitalchina.zhjg.szss.mid.dto.JpsdSmsMessage;
import lombok.Data;

import java.util.Date;

@Data
public class SmsSendLog {

    public enum STATUS {
        SUCCESS, ERROR, IGNORE
    }

    private Integer id;
    private JpsdSmsMessage.StationType zdlx; // 站点类型
    private String zdbh; // 站点编号
    private String sw; // 水位
    private Date sendTime; // 发送时间
    private STATUS status; // 短信发送状态
    private String smsResp; // 平台响应信息
}
