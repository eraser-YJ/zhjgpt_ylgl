package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.ManagTrack;
import com.digitalchina.zhjg.szss.gis.entity.PartsInfo;
import com.digitalchina.zhjg.szss.gis.entity.PartsWarnInfo;
import com.digitalchina.zhjg.szss.gis.entity.WarnDiscuss;
import com.digitalchina.zhjg.szss.mid.entity.ZhjgNotice;

import java.util.List;
import java.util.Map;

public interface OperationSmanageService extends IService<PartsWarnInfo> {
    List<PartsWarnInfo> selectPartsInfoList(Page<PartsWarnInfo> page, PartsWarnInfo partsWarnInfo);

    PartsInfo selectPartsInfo(PartsWarnInfo partsWarnInfo);

    WarnDiscuss selectWarnDiscussInfo(Integer partsWarnId);

    Integer insertManagTrack(ManagTrack managTrack);

    Integer insertManagTrackManagMeasures(ManagTrack managTrack);


    List<ManagTrack> selectManagTrackSure(ManagTrack managTrack);

    Integer updatePartsWarnInfo(Map<String,Object> map);

    Integer updatePartsWarnInfoWarningStatus(Map<String,Object> map);

    //导出
    List<PartsWarnInfo> selectPartsInfoListExport(PartsWarnInfo partsWarnInfo);
}
