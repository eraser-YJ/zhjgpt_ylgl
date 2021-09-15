package com.digitalchina.zhjg.szss.mid.controller;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.service.PartsAnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parts_analysis")
@Api(tags = "部件分析")
public class PartsAnalysisController {

    @Autowired
    private PartsAnalysisService partsAnalysisService;

    @GetMapping("/parts_type")
    @ApiOperation("部件类型分布")
    public RespMsg waringStatist(){
        List<Map<String,Object>> listMap = partsAnalysisService.selectPartsType();
        return RespMsg.ok(listMap);
    }

    @GetMapping("/owner_distribution")
    @ApiOperation("部件权属单位分布")
    public RespMsg ownerDistribution(){
        return RespMsg.ok(partsAnalysisService.selectOwnerDistribution());
    }

    @GetMapping("/parts_num")
    @ApiOperation("部件权属单位分布")
    public RespMsg partsNum(){
        return RespMsg.ok(partsAnalysisService.selectPartsNum());
    }

    @GetMapping("/parts_video_warn_num")
    @ApiOperation("部件数量,视频数量,预警数量查询")
    @ApiImplicitParam(name = "admdivCode", value = "行政区划编码")
    public RespMsg partsVideWwarnN(String admdivCode){
        return RespMsg.ok(partsAnalysisService.selectPartsVideWwarnN(admdivCode));
    }

    @GetMapping("/parts_trend_analysis")
    @ApiOperation("主要部件预警趋势分析")
    public RespMsg partsTrendAnalysis(){
        return RespMsg.ok(partsAnalysisService.selectPartsTrendAnalysis());
    }
}
