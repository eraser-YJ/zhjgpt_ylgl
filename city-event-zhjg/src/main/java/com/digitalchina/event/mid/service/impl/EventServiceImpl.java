package com.digitalchina.event.mid.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.event.mid.entity.Bedept;
import com.digitalchina.event.mid.entity.DeptUser;
import com.digitalchina.event.mid.service.BedeptService;
import com.digitalchina.event.mid.service.DeptUserService;
import com.digitalchina.event.mid.service.EventService;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    DeptUserService deptUserService;
    @Autowired
    BedeptService bedeptService;

    /**
     * 获取当前用户的部门信息
     * @param curuid 当前用户id
     * @return
     */
    @Override
    public Bedept getBedptByUserId(Integer curuid) {
        Optional.ofNullable(curuid).orElseThrow(()->new ServiceException("当前用户未登录"));
        DeptUser deptUser = deptUserService.getOne(Condition.<DeptUser>create().eq(DeptUser.UID, curuid));
        Optional.ofNullable(deptUser).orElseThrow(()->new ServiceException("当前用户未关联部门"));
        Bedept curBedept = bedeptService.getOne(Condition.<Bedept>create().eq(Bedept.BEDID, deptUser.getDid()));
        Optional.ofNullable(curBedept).orElseThrow(()->new ServiceException("当前用户关联部门不存在"));
        return curBedept;
    }

    /**
     * 获取部门信息
     * @param bedid 部门id
     * @return
     */
    @Override
    public Bedept getBedptByBedId(Integer bedid) {
        Bedept curBedept = bedeptService.getOne(Condition.<Bedept>create().eq(Bedept.BEDID, bedid));
        Optional.ofNullable(curBedept).orElseThrow(()->new ServiceException("当前用户关联部门不存在"));
        return curBedept;
    }
}
