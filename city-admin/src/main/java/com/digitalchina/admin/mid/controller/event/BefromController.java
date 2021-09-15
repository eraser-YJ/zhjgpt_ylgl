package com.digitalchina.admin.mid.controller.event;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.event.Befrom;
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
 *  事件来源 前端控制器
 * </p>
 *
 * @author liuping
 * @since 2019-09-11
 */
@Api(tags = "事件系统事件来源管理接口")
@RestController
//@Authorize
@RequestMapping("admin/befrom")
@Deprecated
public class BefromController {

    @Autowired
    private CityEventService cityEventService;

    @PostMapping("saveorupdate")
    @ApiOperation(value = "创建或者更新事件来源")
    public RespMsg<Void> saveOrUpdate(@RequestBody @Valid Befrom befrom) {
        return cityEventService.saveOrUpdateBefrom(befrom);
    }

    @GetMapping("find")
    @ApiOperation(value = "查找单个事件来源")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
    public RespMsg<Befrom> find(@RequestParam(required = true) Integer id) {
        return cityEventService.findBefrom(id);
    }

    @GetMapping(value = "delete")
    @ApiOperation(value = "删除单个事件来源",notes = "使用了外键，删除可能会失败")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
    public RespMsg del(@RequestParam(required = true) Integer id) {

        return cityEventService.delBefrom(id);
    }

    @GetMapping("query")
    @ApiOperation(value = "分页查询事件来源")
    @ApiImplicitParams({@ApiImplicitParam(name = "efnm", value = "事项来源名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "rev", value = "版本", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true)})
    public RespMsg<Page<Befrom>> query(@RequestParam(defaultValue = "10", required = true) Integer size,
                                       @RequestParam(defaultValue = "1", required = true) Integer current,
                                       @RequestParam(required = false) String efnm,
                                       @RequestParam(required = false) Integer rev) {
        return cityEventService.queryBefrom(size,current,efnm,rev);
    }

    @GetMapping("listall")
    @ApiOperation(value = "查询事件来源列表（不分页）")
    @ApiImplicitParams({@ApiImplicitParam(name = "efnm", value = "事项来源名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "rev", value = "版本", dataType = "Integer", required = false),})
    public RespMsg<List<Befrom>> listall(@RequestParam(required = false) String efnm,
                                        @RequestParam(required = false) Integer rev) {
        return cityEventService.listallBefrom(efnm,rev);
    }
}
