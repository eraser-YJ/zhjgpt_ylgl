package com.digitalchina.admin.mid.controller.event;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.event.Workflow;
import com.digitalchina.admin.remoteservice.CityEventService;
import com.digitalchina.common.web.RespMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  工作流 前端控制器
 * </p>
 *
 * @author liuping
 * @since 2019-09-12
 */
@Api(tags = "事件系统工作流管理接口")
@RestController
//@Authorize
@RequestMapping("workflow")
@Deprecated
public class WorkflowController {
    @Autowired
    private CityEventService cityEventService;

    @PostMapping("saveorupdate")
    @ApiOperation(value = "创建或者更新工作流")
    public RespMsg<Void> saveOrUpdate(@RequestBody @Valid Workflow workflow) {
        return cityEventService.saveOrUpdateWorkflow(workflow);
    }

    @GetMapping(value = "find")
    @ApiOperation(value = "查找单个工作流")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
    public RespMsg<Workflow> find(@RequestParam(required = true) Integer id) {
        return cityEventService.findWorkflow(id);

    }

    @GetMapping(value = "delete")
    @ApiOperation(value = "删除单个工作流",notes = "使用了外键，删除可能会失败")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
    public RespMsg del(@RequestParam(required = true) Integer id) {
        return cityEventService.delWorkflow(id);
    }


    @GetMapping("query")
    @ApiOperation(value = "分页查询工作流")
    @ApiImplicitParams({@ApiImplicitParam(name = "wfnm", value = "流程名称", dataType = "String", required = false),
    @ApiImplicitParam(name = "wfkey", value = "流程Key", dataType = "String", required = false),
            @ApiImplicitParam(name = "rev", value = "版本", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true)})
    public RespMsg<Page<Workflow>> query(@RequestParam(defaultValue = "10", required = true) Integer size,
                                         @RequestParam(defaultValue = "1", required = true) Integer current,
                                         @RequestParam(required = false) String wfnm,
                                         @RequestParam(required = false) String wfkey,
                                         @RequestParam(required = false) Integer rev) {
        return cityEventService.queryWorkflow(size, current, wfnm, wfkey,rev);
    }
}
