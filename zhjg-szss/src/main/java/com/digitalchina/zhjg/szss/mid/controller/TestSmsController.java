package com.digitalchina.zhjg.szss.mid.controller;

import com.digitalchina.zhjg.szss.mid.dto.JpsdSmsMessage;
import com.digitalchina.zhjg.szss.mid.service.SmsSender;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testSms")
public class TestSmsController {

    @Autowired
    private SmsSender smsSender;

    @GetMapping("sms")
    public void testSendSms(){
        JpsdSmsMessage message = new JpsdSmsMessage();
        message.setStationType(JpsdSmsMessage.StationType.JSD);
        message.setStationCode("0004310001");
        message.setDepth("0.62");
        smsSender.sendJpsdWarning(message);
    }
}
