package com.digitalchina.zhjg.szss.mid.controller;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.mid.entity.PartsCategory;
import com.digitalchina.zhjg.szss.mid.service.PartsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 市政设施分类管理
 */
@RestController
@RequestMapping("/parts_category")
@Api(tags = "市政设施分类")
public class PartsCategoryController {

    @GetMapping("/tree")
    @ApiOperation("获取市政部件分类树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "模块名称: parts|flood|tubenet|hot|park等，表示 市政设施|防汛排水|地下管网|热力|园林绿化等"),
            @ApiImplicitParam(name = "rootId", value = "待获取树顶级ID，若为null，取全部，非null，取以此id为根的子树（不含此根结点）")
    })
    public RespMsg<List<PartsCategory>> tree(String module, Integer rootId) {
        if (module == null) {
            module = "parts";
        }

        List<String> modules;
        if ("parts".equalsIgnoreCase(module)) {
            modules = Arrays.asList("parts"/*, "video"*/);
        } else {
            modules = Arrays.asList(module);
        }

        return RespMsg.ok(partsCategoryService.tree(modules, rootId, true));
    }

    @ApiOperation("树形结构获取分类数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "模块名称: parts|flood|tubenet|hot|park等，表示 市政设施|防汛排水|地下管网|热力|园林绿化等"),
    })
    @GetMapping("/list/{module}")
    public RespMsg<List<PartsCategory>> list(@PathVariable String module) {
        return RespMsg.ok(partsCategoryService.tree(Arrays.asList(module), null, null));
    }

    @PostMapping("/create/{module}")
    @ApiOperation("新增树节点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "模块名称"),
            @ApiImplicitParam(name = "partCategory", value = "新节点对象：包括parentId,code,name,sort,remark, gistype[enums:point,line,polygon]")
    })
    public RespMsg<PartsCategory> create(@PathVariable String module, @RequestBody PartsCategory partsCategory) {
        partsCategory.setModule(module);
        partsCategoryService.create(partsCategory);
        return RespMsg.ok(partsCategory);
    }

    @PostMapping("/update")
    @ApiOperation("更新树节点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partCategory", value = "仅code,name,remark,gistype可修改，按id更新")
    })
    public RespMsg<Void> update(@RequestBody PartsCategory partsCategory) {
        PartsCategory paramter = new PartsCategory();
        paramter.setId(partsCategory.getId());
        paramter.setCode(partsCategory.getCode());
        paramter.setName(partsCategory.getName());
        paramter.setRemark(partsCategory.getRemark());
        if (partsCategory.getGisType() != null) {
            paramter.setGisType(partsCategory.getGisType());
        }
        partsCategoryService.updateById(paramter);
        return RespMsg.ok();
    }

    @PostMapping("/update_status")
    @ApiOperation("更新树节点状态")
    @ApiImplicitParam(name = "partCategory", value = "仅status可修改，按id更新")
    public RespMsg<Void> updateStatus(@RequestBody PartsCategory partsCategory) {
        PartsCategory paramter = new PartsCategory();
        paramter.setId(partsCategory.getId());
        paramter.setStatus(partsCategory.getStatus());

        partsCategoryService.updateById(paramter);
        return RespMsg.ok();
    }

    @Autowired
    private PartsCategoryService partsCategoryService;
}
