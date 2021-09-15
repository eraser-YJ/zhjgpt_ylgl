package com.digitalchina.admin.mid.controller.event;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.event.Betype;
import com.digitalchina.admin.remoteservice.CityEventService;
import com.digitalchina.common.web.RespMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *事件类型 前端控制器
 * </p>
 *
 * @author liuping
 * @since 2019-09-11
 */
@Api(tags = "事件系统事件类型管理接口")
@RestController
//@Authorize
@RequestMapping("betype")
@Deprecated
public class BetypeController {
    @Autowired
    private CityEventService cityEventService;

    @PostMapping("saveorupdate")
    @ApiOperation(value = "创建或者更新事件类型")
    public RespMsg<Void> saveOrUpdate(@RequestBody @Valid Betype betype) {
        return cityEventService.saveOrUpdateBetype(betype);
    }

    @GetMapping("find")
    @ApiOperation(value = "查找单个事件类型")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
    public RespMsg<Betype> find(@RequestParam(required = true) Integer id) {
        return cityEventService.findBetype(id);
    }

    @GetMapping(value = "delete")
    @ApiOperation(value = "删除单个事件类型",notes = "使用了外键，删除可能会失败")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
    public RespMsg del(@RequestParam(required = true) Integer id) {
        return cityEventService.delBetype(id);
    }


    @GetMapping("query")
    @ApiOperation(value = "分页查询事件类型")
    @ApiImplicitParams({@ApiImplicitParam(name = "etnm", value = "事项类型名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "rev", value = "版本", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true)})
    public RespMsg<Page<Betype>> query(@RequestParam(defaultValue = "10", required = true) Integer size,
                                       @RequestParam(defaultValue = "1", required = true) Integer current,
                                       @RequestParam(required = false) String etnm,
                                       @RequestParam(required = false) Integer rev) {
        return cityEventService.queryBetype(size, current, etnm, rev);
    }

    @GetMapping("listall")
    @ApiOperation(value = "查询事件类型列表（不分页）")
    @ApiImplicitParams({@ApiImplicitParam(name = "etnm", value = "事项类型名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "rev", value = "版本", dataType = "Integer", required = false),})
    public RespMsg<List<Betype>> listall(@RequestParam(required = false) String etnm,
                                        @RequestParam(required = false) Integer rev) {
        return cityEventService.listallBetype( etnm, rev);
    }
}
