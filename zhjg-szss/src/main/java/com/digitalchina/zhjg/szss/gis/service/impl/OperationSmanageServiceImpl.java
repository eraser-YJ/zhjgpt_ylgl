package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.ManagTrack;
import com.digitalchina.zhjg.szss.gis.entity.PartsInfo;
import com.digitalchina.zhjg.szss.gis.entity.PartsWarnInfo;
import com.digitalchina.zhjg.szss.gis.entity.WarnDiscuss;
import com.digitalchina.zhjg.szss.gis.mapper.OperationSmanageMapper;
import com.digitalchina.zhjg.szss.gis.service.OperationSmanageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OperationSmanageServiceImpl extends ServiceImpl<OperationSmanageMapper, PartsWarnInfo> implements OperationSmanageService {

    @Autowired
    private OperationSmanageMapper operationSmanageMapper;

    /**
     * 获取预警信息列表
     * @param partsWarnInfo
     * @return
     */
    @Override
    public List<PartsWarnInfo> selectPartsInfoList(Page<PartsWarnInfo> page,PartsWarnInfo partsWarnInfo) {
        return operationSmanageMapper.selectPartsInfoList(page,partsWarnInfo);
    }

    /**
     * 预警基本信息详情
     * @param partsWarnInfo
     * @return
     */
    @Override
    public PartsInfo selectPartsInfo(PartsWarnInfo partsWarnInfo) {
        return operationSmanageMapper.selectPartsInfo(partsWarnInfo);
    }

    @Override
    public WarnDiscuss selectWarnDiscussInfo(Integer partsWarnId) {
        return operationSmanageMapper.selectWarnDiscussInfo(partsWarnId);
    }

    @Override
    public Integer insertManagTrack(ManagTrack managTrack) {
        return operationSmanageMapper.insertManagTrack(managTrack);
    }

    @Override
    public Integer insertManagTrackManagMeasures(ManagTrack managTrack) {
        return operationSmanageMapper.insertManagTrackManagMeasures(managTrack);
    }


    @Override
    public List<ManagTrack> selectManagTrackSure(ManagTrack managTrack) {
        return operationSmanageMapper.selectManagTrackSure(managTrack);
    }

    @Override
    public Integer updatePartsWarnInfo(Map<String, Object> map) {
        return operationSmanageMapper.updatePartsWarnInfo(map);
    }

    @Override
    public Integer updatePartsWarnInfoWarningStatus(Map<String, Object> map) {
        return operationSmanageMapper.updatePartsWarnInfoWarningStatus(map);
    }

    @Override
    public List<PartsWarnInfo> selectPartsInfoListExport(PartsWarnInfo partsWarnInfo) {
        return operationSmanageMapper.selectPartsInfoListExport(partsWarnInfo);
    }
}
