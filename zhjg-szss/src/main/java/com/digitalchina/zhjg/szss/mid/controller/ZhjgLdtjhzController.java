package com.digitalchina.zhjg.szss.mid.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.service.ZhjgLdtjhzService;
import com.digitalchina.zhjg.szss.mid.entity.ZhjgNotice;
import com.digitalchina.zhjg.szss.mid.service.ZhjgNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 分析评价-统计汇总
 * @author shkstart
 * @create 2020-08-07 10:24
 */
@RestController
@RequestMapping("/ZhjgLdtjhz")
@Api(tags = "分析评价-统计汇总")
public class ZhjgLdtjhzController {

    @Autowired
    private ZhjgLdtjhzService zhjgLdtjhzService;

    /**
     *人口、面积、绿地统计查询
     * @return
     */
    @GetMapping("/selectZhjgZhXxTj")
    @ApiOperation("人口、面积、绿地统计查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "TOTALAREANUM", value = "区域总面积",dataType = "String"),
            @ApiImplicitParam(name = "RKNUM", value = "总人口",dataType = "String"),
            @ApiImplicitParam(name = "LDAREANUM", value = "总绿地面积",dataType = "String"),
            @ApiImplicitParam(name = "GYNUM", value = "公园数量",dataType = "String"),
            @ApiImplicitParam(name = "GYLDAREANUM", value = "公园绿地面积",dataType = "String"),
            @ApiImplicitParam(name = "LHVNUM", value = "绿化率",dataType = "String")
    })
    public RespMsg<Map<String, String>> selectZhjgZhXxTj() {
        return RespMsg.ok(zhjgLdtjhzService.selectZhjgZhXxTj());
    }


    /**
     * 绿地类型统计
     * @return
     */
    @GetMapping("/selectZhjgLdLxTj")
    @ApiOperation("绿地类型统计")
    @ApiImplicitParams({@ApiImplicitParam(name = "LDLX", value = "绿地类型",dataType = "String"),
            @ApiImplicitParam(name = "TOTAL", value = "绿地类型百分比",dataType = "String")
    })
    public RespMsg<List<Map<String, String>>> selectZhjgLdLxTj() {
        return RespMsg.ok(zhjgLdtjhzService.selectZhjgLdLxTj());
    }


    /**
     * 各区域绿地面积分析-根据时间
     * @return
     */
    @GetMapping("/selectZhjgGqyLdmjFx")
    @ApiOperation("各区域绿地面积分析-根据时间")
    @ApiImplicitParams({@ApiImplicitParam(name = "LDLX", value = "绿地类型",dataType = "String"),
            @ApiImplicitParam(name = "TOTAL", value = "年度绿地总面积",dataType = "String"),
            @ApiImplicitParam(name = "BHSUM", value = "年度北湖绿地总面积",dataType = "String"),
            @ApiImplicitParam(name = "CDSUM", value = "年度长德绿地总面积",dataType = "String"),
            @ApiImplicitParam(name = "GXSUM", value = "年度高新绿地总面积",dataType = "String"),
            @ApiImplicitParam(name = "KGSUM", value = "年度空港绿地总面积",dataType = "String")
    })
    public RespMsg<List<Map<String,Object>>> selectZhjgGqyLdmjFx() {
        String thisYear =String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String lastYear =String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-1);
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> thisYearMap = new HashMap<>();
        Map<String,Object> lastYearMap = new HashMap<>();
        thisYearMap.put("year",thisYear);
        thisYearMap.put("data",zhjgLdtjhzService.selectZhjgGqyLdmjFx(thisYear));
        lastYearMap.put("year",lastYear);
        lastYearMap.put("data",zhjgLdtjhzService.selectZhjgGqyLdmjFx(lastYear));
        list.add(thisYearMap);
        list.add(lastYearMap);
        return RespMsg.ok(list);
    }


    /**
     * 行道数统计
     * @return
     */
    @GetMapping("/selectZhjgHdstj")
    @ApiOperation("行道数统计")
    @ApiImplicitParams({@ApiImplicitParam(name = "DLDJ", value = "道路等级",dataType = "String"),
            @ApiImplicitParam(name = "DLDJPER", value = "道路等级百分比",dataType = "String"),
    })
    public RespMsg<List<Map<String, String>>> selectZhjgHdstj() {
        return RespMsg.ok(zhjgLdtjhzService.selectZhjgHdstj());
    }

    /**
     * 绿地类型树种数量统计
     * @return
     */
    @GetMapping("/selectZhjgLdLxSzNum")
    @ApiOperation("绿地类型树种数量统计")
    @ApiImplicitParams({@ApiImplicitParam(name = "NUM", value = "数量",dataType = "String"),
            @ApiImplicitParam(name = "ZWPZ", value = "树种",dataType = "String"),
    })
    public RespMsg<List<Map<String, String>>> selectZhjgLdLxSzNum(String ldlxid) {
        return RespMsg.ok(zhjgLdtjhzService.selectZhjgLdLxSzNum(Integer.valueOf(ldlxid)));
    }

    /**
     * 各区域绿地树种统计
     * @return
     */
    @GetMapping("/selectZhjgGqyLdSzTj")
    @ApiOperation("各区域绿地树种统计")
    @ApiImplicitParams({@ApiImplicitParam(name = "NUMPER", value = "所占百分比",dataType = "String"),
            @ApiImplicitParam(name = "ZWPZ", value = "树种",dataType = "String"),
    })
    public RespMsg<List<Map<String, String>>> selectZhjgGqyLdSzTj(String ldlxid) {
        return RespMsg.ok(zhjgLdtjhzService.selectZhjgGqyLdSzTj(Integer.valueOf(ldlxid)));
    }

    /**
     * 古树名数数量排名
     * @return
     */
    @GetMapping("/selectZhjgGsmsSlPm")
    @ApiOperation("古树名数数量排名")
    @ApiImplicitParams({@ApiImplicitParam(name = "NUM", value = "数量",dataType = "String"),
            @ApiImplicitParam(name = "ZWPZ", value = "树种",dataType = "String"),
    })
    public RespMsg<List<Map<String, String>>> selectZhjgGsmsSlPm() {
        return RespMsg.ok(zhjgLdtjhzService.selectZhjgGsmsSlPm());
    }

    /**
     * 绿地类型查询
     * @return
     */
    @GetMapping("/selectZhjgLdLx")
    @ApiOperation("绿地类型查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "LDLX ", value = "绿地类型",dataType = "String")})
    public RespMsg<List<Map<String, String>>> selectZhjgLdLx() {
        return RespMsg.ok(zhjgLdtjhzService.selectZhjgLdLx());
    }
}
