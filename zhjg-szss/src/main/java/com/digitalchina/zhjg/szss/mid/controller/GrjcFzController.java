package com.digitalchina.zhjg.szss.mid.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.GrjcFz;
import com.digitalchina.zhjg.szss.gis.service.GrjcFzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/GrjcFz")
@Api(tags = "供热监测阀值设置")
public class GrjcFzController {


    @Autowired
    private GrjcFzService grjcFzService;

    @GetMapping("/grjcFzList")
    @ApiOperation("供热监测阀值设置列表")
    @ApiImplicitParam(name = "heatSourceType", value = "热源类型",dataType = "String")
    public RespMsg<Page> selectFz(GrjcFz grjcFz,Page page){
        page.setRecords(grjcFzService.selectGrjcFz(page,grjcFz));
        return RespMsg.ok(page);
    }

    @PostMapping("/insertFz")
    @ApiOperation("插入排水管网预警表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "heatSourceType", value = "热源类型: hrz-换热站; glf-锅炉房; gdcwd-固定测温点",dataType = "String"),
            @ApiImplicitParam(name = "tempeType", value = "温度类型",dataType = "String"),
            @ApiImplicitParam(name = "fzlx", value = "阀值类型:大于; 大于等于;等于;小于;小于等于",dataType = "String"),
            @ApiImplicitParam(name = "fzsh", value = "阀值类型",dataType = "Double"),
            @ApiImplicitParam(name = "remark", value = "备注",dataType = "String")
    })
    public RespMsg<Integer> insertFz(GrjcFz grjcFz){
        return RespMsg.ok(grjcFzService.insertGrjcFc(grjcFz));
    }
}
