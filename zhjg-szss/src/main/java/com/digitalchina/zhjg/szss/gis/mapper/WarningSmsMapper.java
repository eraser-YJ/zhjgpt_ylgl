package com.digitalchina.zhjg.szss.gis.mapper;

import com.digitalchina.zhjg.szss.gis.entity.DrainagePoint;
import com.digitalchina.zhjg.szss.gis.entity.PondingPoint;
import com.digitalchina.zhjg.szss.gis.entity.SmsSendLog;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public interface WarningSmsMapper {
    // 按站点编号查询积水点
    PondingPoint findPondingPointByCode(String code);

    // 按站点编号查询排水点
    DrainagePoint findDrainagePointByCode(String code);

    // 查询站点最新短信发送成功时间
    Date findStationLatestSendTime(String code);

    // 插入发送日志
    int insertSendLog(SmsSendLog smsSendLog);
}
