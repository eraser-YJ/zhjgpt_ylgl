package com.digitalchina.zhjg.szss.mid.service.impl;

import com.digitalchina.zhjg.szss.gis.entity.DrainagePoint;
import com.digitalchina.zhjg.szss.gis.entity.PondingPoint;
import com.digitalchina.zhjg.szss.gis.entity.SmsSendLog;
import com.digitalchina.zhjg.szss.gis.mapper.WarningSmsMapper;
import com.digitalchina.zhjg.szss.mid.dto.JpsdSmsMessage;
import com.digitalchina.zhjg.szss.mid.service.SmsSender;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SmsSenderImpl implements SmsSender {

    @Value("${sms.cred.id}")
    private String secretId;
    @Value("${sms.cred.key}")
    private String secretKey;
    @Value("${sms.proxy.enabled}")
    private Boolean proxyEnabled;
    @Value("${sms.proxy.host}")
    private String proxyHost;
    @Value("${sms.proxy.port}")
    private Integer proxyPort;
    @Value("${sms.event.jpsdWarning.sdkAppId}")
    private String sdkAppId;
    @Value("${sms.event.jpsdWarning.signName}")
    private String signName;
    @Value("${sms.event.jpsdWarning.tempId}")
    private String tempId;

    @Autowired
    private WarningSmsMapper smsMapper;

    @Override
    public void sendJpsdWarning(JpsdSmsMessage message) {

        Assert.notNull(message, "message cann't null");
        Assert.notNull(message.getStationCode(), "????????????????????????");
        Assert.notNull(message.getStationType(), "????????????????????????");
        Assert.notNull(message.getDepth(), "????????????????????????");

        SmsSendLog sendLog = new SmsSendLog();
        sendLog.setZdlx(message.getStationType());
        sendLog.setZdbh(message.getStationCode());
        sendLog.setSw(message.getDepth());
        sendLog.setSendTime(new Date());

        // ????????????????????????????????????????????????
        Date latestSendTime = smsMapper.findStationLatestSendTime(message.getStationCode());
        if (latestSendTime != null && (System.currentTimeMillis() - latestSendTime.getTime()) < 60 * 60 * 1000) {
            sendLog.setStatus(SmsSendLog.STATUS.IGNORE);
            smsMapper.insertSendLog(sendLog);
            return;
        }

        List<String> smsContent = new ArrayList<>();

        if (message.getStationType() == JpsdSmsMessage.StationType.JSD) {
            PondingPoint pondingPoint = smsMapper.findPondingPointByCode(message.getStationCode());
            Assert.notNull(pondingPoint, "??????????????????????????????????????????????????????");
            Assert.state(StringUtils.isNotBlank(pondingPoint.getLxdh()), "??????????????????????????????????????????");
            smsContent.add("[???????????????]");
            smsContent.add(pondingPoint.getZdmc());
            smsContent.add(message.getDepth());
            sendSms(smsContent, pondingPoint.getLxdh().trim(), sendLog);
        } else {
            DrainagePoint drainagePoint = smsMapper.findDrainagePointByCode(message.getStationCode());
            Assert.notNull(drainagePoint, "??????????????????????????????????????????????????????");
            Assert.state(StringUtils.isBlank(drainagePoint.getLxdh()), "??????????????????????????????????????????");
            smsContent.add("[???????????????]");
            smsContent.add(drainagePoint.getZdmc());
            smsContent.add(message.getDepth());
            sendSms(smsContent, drainagePoint.getLxdh(), sendLog);
        }
    }

    private void sendSms(List<String> smsContent, String phoneNums, SmsSendLog sendLog) {

        Credential cred = new Credential(secretId, secretKey);

        HttpProfile httpProfile = new HttpProfile();
        // ????????????
        if (proxyEnabled) {
            httpProfile.setProxyHost(proxyHost);
            httpProfile.setProxyPort(proxyPort);
        }


        httpProfile.setEndpoint("sms.tencentcloudapi.com");

        // ????????????????????????????????????????????????????????????????????????
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        /* ????????????????????????(???sms??????)???client??????
         * ????????????????????????????????????????????????????????????ap-guangzhou?????????????????????????????? */
        SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);

        //  ??????????????????????????????????????????????????????????????????????????????????????????????????????
        SendSmsRequest req = new SendSmsRequest();
        req.setSmsSdkAppid(sdkAppId);
        req.setSign(signName);
        req.setTemplateID(tempId);

        String[] phoneNumberSet = phoneNums.split(";");
        for (int i = 0; i < phoneNumberSet.length; ++i) {
            phoneNumberSet[i] = "+86" + phoneNumberSet[i];
        }
        req.setPhoneNumberSet(phoneNumberSet);

        String[] params = new String[smsContent.size()];
        req.setTemplateParamSet(smsContent.toArray(params));
        try {
            // ?????? client ???????????? SendSms ??????????????????
            SendSmsResponse res = client.SendSms(req);
            if (Arrays.stream(res.getSendStatusSet()).anyMatch(status -> "Ok".equalsIgnoreCase(status.getCode()))) {
                sendLog.setStatus(SmsSendLog.STATUS.SUCCESS);
            } else {
                sendLog.setStatus(SmsSendLog.STATUS.ERROR);
            }
            sendLog.setSmsResp(SendSmsResponse.toJsonString(res));
            smsMapper.insertSendLog(sendLog);
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }

    }
}
