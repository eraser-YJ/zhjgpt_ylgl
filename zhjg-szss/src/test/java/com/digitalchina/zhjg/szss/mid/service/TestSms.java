package com.digitalchina.zhjg.szss.mid.service;

import com.digitalchina.zhjg.szss.mid.dto.JpsdSmsMessage;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSms {

    @Autowired
    private SmsSender smsSender;

    // @Test
    public void testSendSms() {
        JpsdSmsMessage message = new JpsdSmsMessage();
        message.setStationType(JpsdSmsMessage.StationType.JSD);
        message.setStationCode("0004310001");
        message.setDepth("0.62");
        smsSender.sendJpsdWarning(message);
    }
}
