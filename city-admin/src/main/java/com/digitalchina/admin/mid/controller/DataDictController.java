package com.digitalchina.admin.mid.controller;

import cn.hutool.core.util.StrUtil;
import com.digitalchina.admin.mid.entity.DataDict;
import com.digitalchina.admin.mid.entity.DataDictGroup;
import com.digitalchina.admin.mid.service.DataDictService;
import com.digitalchina.common.web.RespMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据字典接口
 */
@RestController
@RequestMapping("/dataDict")
@Api(tags= "数据字典相关")
public class DataDictController {

    @Autowired
    private DataDictService dataDictService;

    @GetMapping("/getDictName")
    @ApiOperation("获取数据字典名称")
    @ApiImplicitParam(name = "dataDictCode", value = "字典表ID")
    public RespMsg<Object> dataDict(String dataDictCode) {
        List<DataDict> list = dataDictService.getDictNameList(dataDictCode);
        return RespMsg.ok(list);
    }

    @GetMapping("/getPartsWaringTypeDictForGis")
    public RespMsg<List<DataDict>> getPartsWaringTypeDictForGis() {
        List<DataDict> list = dataDictService.getDictNameList("zhjg_warning_type");
        // 只要 预警和报警
        return RespMsg.ok(
                list.stream().filter(
                        item -> StrUtil.equals(item.getItemValue(), "YJ") || StrUtil.equals(item.getItemValue(), "BJ")
                ).collect(Collectors.toList())
        );
    }

    @ApiOperation("获取字典项分组信息")
    @ApiImplicitParam(name = "module", value = "模块")
    @GetMapping("/groups")
    public RespMsg<List<DataDictGroup>> getDictGroups(String module) {
        return RespMsg.ok(dataDictService.getDictGroups(module));
    }

    @ApiOperation("获取分组内字典项列表")
    @ApiImplicitParam(name = "dictCode", value = "组code")
    @GetMapping("/dictAllItems")
    public RespMsg<List<DataDict>> getAllItems(String dictCode) {
        return RespMsg.ok(dataDictService.getItemsByCode(dictCode));
    }

    @ApiOperation("新增字典项")
    @ApiImplicitParam(name = "dictItem", value = "dictItem对象")
    @PostMapping("/createItem")
    public RespMsg<DataDict> createItem(@RequestBody DataDict dictItem) {
        dataDictService.createItem(dictItem);
        return RespMsg.ok(dictItem);
    }

    @ApiOperation("修改字典项")
    @ApiImplicitParam(name = "dictItem", value = "dictItem对象")
    @PostMapping("/updateItem")
    public RespMsg<Void> updateItem(@RequestBody DataDict dictItem) {

        DataDict param = new DataDict();
        param.setId(dictItem.getId());
        param.setItemName(dictItem.getItemName());
        param.setItemValue(dictItem.getItemName());
        param.setRemark(dictItem.getRemark());

        dataDictService.updatItem(param);
        return RespMsg.ok();
    }

    @ApiOperation("变更字典项状态")
    @ApiImplicitParam(name = "dictItem", value = "dictItem对象")
    @PostMapping("/updateItemStatus")
    public RespMsg<Void> updateItemStatus(@RequestBody DataDict dictItem) {

        DataDict param = new DataDict();
        param.setId(dictItem.getId());
        param.setStatus(dictItem.getStatus());

        dataDictService.updatItem(param);
        return RespMsg.ok();
    }
}
