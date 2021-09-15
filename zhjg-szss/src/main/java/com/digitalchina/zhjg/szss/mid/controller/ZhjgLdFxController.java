package com.digitalchina.zhjg.szss.mid.controller;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.service.ZhjgLdFxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 分析评价-绿地分析
 * @author shkstart
 * @create 2020-08-07 10:24
 */
@RestController
@RequestMapping("/ZhjgLdFx")
@Api(tags = "分析评价-绿地分析")
public class ZhjgLdFxController {

    @Autowired
    private ZhjgLdFxService zhjgLdFxService;



    /**
     * 绿地率
     * @return
     */
    @GetMapping("/selectZhjgLdl")
    @ApiOperation("绿地率")
    @ApiImplicitParams({@ApiImplicitParam(name = "DATANUMPER", value = "百分比",dataType = "String"),
            @ApiImplicitParam(name = "AREANAME", value = "区域名称",dataType = "String")
    })
    public RespMsg<List<Map<String, String>>> selectZhjgLdl() {
        return RespMsg.ok(zhjgLdFxService.selectZhjgLdl());
    }

    /**
     * 绿化覆盖率
     * @return
     */
    @GetMapping("/selectZhjgLhFgl")
    @ApiOperation("绿化覆盖率")
    @ApiImplicitParams({@ApiImplicitParam(name = "DATANUMPER", value = "百分比",dataType = "String"),
            @ApiImplicitParam(name = "AREANAME", value = "区域名称",dataType = "String")
    })
    public RespMsg<List<Map<String, String>>> selectZhjgLhFgl() {
        return RespMsg.ok(zhjgLdFxService.selectZhjgLhFgl());
    }

    /**
     * 人均绿地面积
     * @return
     */
    @GetMapping("/selectZhjgRjLdMj")
    @ApiOperation("人均绿地面积")
    @ApiImplicitParams({@ApiImplicitParam(name = "DATANUM", value = "人均绿地面积",dataType = "String")})
    public RespMsg<List<Map<String, String>>> selectZhjgRjLdMj() {
        return RespMsg.ok(zhjgLdFxService.selectZhjgRjLdMj());
    }

    /**
     * 每万人拥有公园数
     * @return
     */
    @GetMapping("/selectZhjgWrYyGyNum")
    @ApiOperation("每万人拥有公园数")
    @ApiImplicitParams({@ApiImplicitParam(name = "DATANUM", value = "每万人拥有公园数",dataType = "String")})
    public RespMsg<List<Map<String, String>>> selectZhjgWrYyGyNum() {
        return RespMsg.ok(zhjgLdFxService.selectZhjgWrYyGyNum());
    }



    /**
     * 年度数据对比分析-根据时间
     * @return
     */
    @GetMapping("/selectZhjgNdSjDb")
    @ApiOperation("年度数据对比分析-根据时间")
    @ApiImplicitParams({@ApiImplicitParam(name = "LDMJ", value = "绿地面积",dataType = "String"),
            @ApiImplicitParam(name = "LHLPER", value = "绿化率",dataType = "String"),
            @ApiImplicitParam(name = "LDLPER", value = "绿地率",dataType = "String")
    })
    public RespMsg<List<Map<String,Object>>> selectZhjgNdSjDb() {
        String thisYear =String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String lastYear =String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-1);
        String beforeYear =String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-2);
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> thisYearMap = new HashMap<>();
        Map<String,Object> lastYearMap = new HashMap<>();
        Map<String,Object> beforeYearMap = new HashMap<>();
        thisYearMap.put("year",thisYear);
        thisYearMap.put("data",zhjgLdFxService.selectZhjgNdSjDb(thisYear));
        lastYearMap.put("year",lastYear);
        lastYearMap.put("data",zhjgLdFxService.selectZhjgNdSjDb(lastYear));
        beforeYearMap.put("year",beforeYear);
        beforeYearMap.put("data",zhjgLdFxService.selectZhjgNdSjDb(beforeYear));
        list.add(beforeYearMap);
        list.add(lastYearMap);
        list.add(thisYearMap);
        return RespMsg.ok(list);
    }
}
