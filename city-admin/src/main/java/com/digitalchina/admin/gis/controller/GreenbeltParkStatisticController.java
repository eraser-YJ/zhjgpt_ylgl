package com.digitalchina.admin.gis.controller;

import com.digitalchina.admin.gis.service.ConfigService;
import com.digitalchina.admin.mid.service.DataDictService;
import com.digitalchina.common.web.RespMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "园林绿化一张图相关统计数据获取接口")
@RequestMapping("/statistic")
@RestController
public class GreenbeltParkStatisticController {

    @Autowired
    private DataDictService dataDictService;

    @Autowired
    private ConfigService configService;


    @ApiOperation("按行政区划统计各类资源数量")
    @ApiImplicitParam(name = "code", value = "资源表名")
    @GetMapping("/cntByDiv/{code}")
    public RespMsg<List<Map<String, Integer>>> countByDiv(@PathVariable("code") String code) {

        final Map<String, String> XZQH_FIELD_NAME_CODE_MAP = new HashMap<String, String>() {{
            put("GYLD_LD", "ADMDIV_NAME");
        }};

        String divFieldName = XZQH_FIELD_NAME_CODE_MAP.get(code);
        if (divFieldName == null) {
            divFieldName = "XZQH"; //默认的
        }

        List<Map<String, Object>> cntResults = configService.cntByDiv(code, divFieldName);

        List<Map<String, Integer>> resultList = new ArrayList<>();
        cntResults.forEach(result -> {
            String divName = (String) result.get("DIV");
            int cnt = ((BigDecimal) result.get("CNT")).intValue();
            resultList.add(new HashMap<String, Integer>() {{
                put(divName, cnt);
            }});
        });

        return RespMsg.ok(resultList);
    }
}
