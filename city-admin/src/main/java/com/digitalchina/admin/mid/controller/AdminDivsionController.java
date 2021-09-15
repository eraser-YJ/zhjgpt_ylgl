package com.digitalchina.admin.mid.controller;

import com.digitalchina.admin.mid.entity.AdminDivsion;
import com.digitalchina.admin.mid.service.AdminDivsionService;
import com.digitalchina.common.web.RespMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 行政区划
 */
@RestController
@RequestMapping("/getWarnAdmdiv")
@Api(tags = "行政区划")
public class AdminDivsionController {

    @Autowired
    private AdminDivsionService adminDivsionService;

    @GetMapping("/tree")
    @ApiOperation("获取行政区划树")
    @ApiImplicitParam(name = "aid", value = "待获取树顶级ID，若为null，取全部，非null，取以此id为根的子树（不含此根结点）")
    public RespMsg<Object> tree(Integer aid){
        List<AdminDivsion> list = adminDivsionService.tree(aid);
        return RespMsg.ok(list);
    }
}
