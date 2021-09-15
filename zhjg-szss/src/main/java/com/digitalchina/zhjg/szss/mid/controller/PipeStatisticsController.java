package com.digitalchina.zhjg.szss.mid.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePoint;
import com.digitalchina.zhjg.szss.gis.entity.PipeStatistics;
import com.digitalchina.zhjg.szss.gis.service.PipeStatisticsService;
import com.digitalchina.zhjg.szss.mid.service.PipeSelectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 地下管网统计查询
 */
@RestController
@RequestMapping("/PipeStatistics")
@Api(tags = "地下管网统计")
public class PipeStatisticsController {

    @Autowired
    private PipeSelectService pipeSelectService;

    @Autowired
    private PipeStatisticsService pipeStatisticsService;

    @GetMapping("/pipeSelect")
    @ApiOperation("地下管网统计查询")
    @ApiImplicitParam(name = "xzqhCode", value = "行政区划代码",dataType = "String")
    public RespMsg<Page> PipeSelect(Page page, PipeStatistics pipeStatistics){
        List<PipeStatistics> resList = new ArrayList<>();
        //查询parts_category中module为tubenet和level=2的code(表名),name(分类)
        List<Map<String,String>> listMap = pipeSelectService.pipeSelect();
        List<PipeStatistics> list = pipeStatisticsService.selectPipeList(page,listMap,pipeStatistics.getXzqhCode());
        if(listMap.size()>0){
//            for(Map<String,String> resMap : listMap){
//                String code = resMap.get("code");
//                String name = resMap.get("name");
//                pipeStatistics.setCode(code);
//                pipeStatistics.setName(name);
//                List<PipeStatistics> list =pipeStatisticsService.selectPipeList(page,pipeStatistics);
//                for(PipeStatistics pip : list){
//                    resList.add(pip);
//                }
//            }
        }
        page.setRecords(list);
        return RespMsg.ok(page);
    }

}
