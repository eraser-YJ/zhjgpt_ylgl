package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.ManagTrack;
import com.digitalchina.zhjg.szss.gis.entity.PartsInfo;
import com.digitalchina.zhjg.szss.gis.entity.PartsWarnInfo;
import com.digitalchina.zhjg.szss.gis.entity.WarnDiscuss;

import java.util.List;
import java.util.Map;

public interface OperationSmanageMapper extends BaseMapper<PartsWarnInfo> {
    List<PartsWarnInfo> selectPartsInfoList(Page<PartsWarnInfo> page,  PartsWarnInfo partsWarnInfo);

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
