package com.digitalchina.zhjg.szss.mid.service;

import com.digitalchina.zhjg.szss.mid.dto.JpsdSmsMessage;

public interface SmsSender {

    // 积排水点预警短信发送
    void sendJpsdWarning(JpsdSmsMessage message);
}
