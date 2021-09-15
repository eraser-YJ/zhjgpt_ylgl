package com.digitalchina.event.mid.controller;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.mid.entity.Workflow;
import com.digitalchina.event.mid.service.WorkflowService;
import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.security.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * <p>
 *  工作流 前端控制器
 * </p>
 *
 * @author liuping
 * @since 2019-09-12
 */
@Api(tags = "工作流管理接口")
@RestController
@RequestMapping("workflow")
public class WorkflowController {
    @Autowired
    private WorkflowService workflowService;

    @Authorize(SecurityConstant.FORBIDDEN)
    @PostMapping("saveorupdate")
    @ApiOperation(value = "创建或者更新工作流")
    public RespMsg<Void> saveOrUpdate(@RequestBody @Valid Workflow workflow) {
        Date now =new Date();
        if(null == workflow.getWfid()){
            workflow.setCrdt(now);
        }
        workflow.setModt(now);
        workflowService.saveOrUpdate(workflow);
        return RespMsg.ok();
    }

    @Authorize
    @GetMapping(value = "find")
    @ApiOperation(value = "查找单个工作流")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
    public RespMsg<Workflow> find(@RequestParam(required = true) Integer id) {
        return RespMsg.ok(workflowService.getById(id));
    }

    @Authorize(SecurityConstant.FORBIDDEN)
    @GetMapping(value = "delete")
    @ApiOperation(value = "删除单个工作流",notes = "使用了外键，删除可能会失败")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
    public RespMsg del(@RequestParam(required = true) Integer id) {
        try {
            workflowService.removeById(id);
        }catch (Exception e){
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST,"该工作流已被使用，不可删除！");
        }
        return RespMsg.ok();
    }

    @Authorize
    @GetMapping("query")
    @ApiOperation(value = "分页查询工作流")
    @ApiImplicitParams({@ApiImplicitParam(name = "wfnm", value = "流程名称", dataType = "String", required = false),
    @ApiImplicitParam(name = "wfkey", value = "流程Key", dataType = "String", required = false),
            @ApiImplicitParam(name = "rev", value = "版本", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true)})
    public RespMsg<IPage<Workflow>> query(@RequestParam(defaultValue = "10", required = true) Integer size,
                                        @RequestParam(defaultValue = "1", required = true) Integer current,
                                        @RequestParam(required = false) String wfnm,
                                        @RequestParam(required = false) String wfkey,
                                        @RequestParam(required = false) Integer rev) {
        IPage<Workflow> page = new Page<>(current, size);
        LambdaQueryWrapper<Workflow> queryWrapper = Condition.<Workflow>lambda()
                .like(StringUtils.isNotEmpty(wfnm),Workflow::getWfnm, wfnm)
                .eq(StringUtils.isNotEmpty(wfkey),Workflow::getWfkey, wfkey)
                .eq(null != rev,Workflow::getRev, rev)
                .orderByDesc(Workflow::getModt);
        return RespMsg.ok(workflowService.page(page,queryWrapper));
    }
}
