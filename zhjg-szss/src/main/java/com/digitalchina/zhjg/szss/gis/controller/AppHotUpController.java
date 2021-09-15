package com.digitalchina.zhjg.szss.gis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.aop.LogAdvice;
import com.digitalchina.modules.security.SecurityUtil;
import com.digitalchina.modules.security.UserDetail;
import com.digitalchina.zhjg.szss.gis.entity.HotUpData;
import com.digitalchina.zhjg.szss.gis.service.AppHotUpServcie;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供热上报接口
 */
@RestController
@RequestMapping("/appHotUp")
@Api(tags = "app供热上报接口")
public class AppHotUpController {

    @Autowired
    private AppHotUpServcie appHotUpServcie;

    private static final Logger log = LoggerFactory.getLogger(LogAdvice.class);


    @GetMapping("/selectHotUpData")
    @ApiOperation("查询供热上报信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "日志上报开始时间", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "日志上报结束时间", dataType = "String")
    })
    public RespMsg<Page> selectHotUpData(HotUpData hotUpData, Page page,String startTime,String endTime){
        Integer id = SecurityUtil.currentUserId();
        hotUpData.setUserId(id);
        page.setRecords(appHotUpServcie.selectHotUpData(page,hotUpData,startTime,endTime));
        return RespMsg.ok(page);
    }

    @GetMapping("/selecQYData")
    @ApiOperation("查询企业和企业下锅炉房")
    public RespMsg selectQY(){
        HotUpData hotUpData = new HotUpData();
        //查询用户id
        Integer id = SecurityUtil.currentUserId();
        hotUpData.setUserId(id);
        List<HotUpData> list = appHotUpServcie.selectQYGLF(hotUpData);
        Map<String,Object> map = new HashMap<>();
        if(list.size()>0){
            HotUpData hotUpData1 = list.get(0);
            map.put("enName",hotUpData1.getEnName());
            map.put("enterId",hotUpData1.getEnterId());
            List<Map<String,Object>> resList = new ArrayList<>();
            for(HotUpData hotUpData2 : list){
                Map<String,Object> map1 = new HashMap<>();
                map1.put("mc",hotUpData2.getMc());
                map1.put("glfId",hotUpData2.getGlfId());
                resList.add(map1);
            }
            map.put("glf",resList);
        }

        return RespMsg.ok(map);
    }

    @PostMapping("/insertHotLOG")
    @ApiOperation("运行状态上报")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enterId", value = "供热企业id",dataType = "Integer"),
            @ApiImplicitParam(name = "glfId", value = "锅炉房id",dataType = "Integer"),
            @ApiImplicitParam(name = "yxqk", value = "锅炉房状态",dataType = "String"),
            @ApiImplicitParam(name = "explain", value = "情况说明",dataType = "String"),
            @ApiImplicitParam(name = "uptime", value = "上报时间",dataType = "String"),
            @ApiImplicitParam(name = "lxr", value = "联系人",dataType = "String"),
            @ApiImplicitParam(name = "lxphone", value = "联系电话",dataType = "String"),
            @ApiImplicitParam(name = "pic", value = "图片",dataType = "String")
    })
    public RespMsg<Integer> insertLOG(@RequestBody HotUpData hotUpData){
        UserDetail userDetail = SecurityUtil.currentUser();
        hotUpData.setUserName(userDetail.getName());
        hotUpData.setUserId(userDetail.getId());

        //更新SZSS_GLF表中的YXQK
        appHotUpServcie.updateGLFType(hotUpData.getGlfId(),hotUpData.getYxqk());

     return   RespMsg.ok(appHotUpServcie.insertHotLog(hotUpData));

    }
}
