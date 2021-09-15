package com.digitalchina.event.mid.controller;

import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.mid.entity.Bedept;
import com.digitalchina.event.mid.service.EventService;
import com.digitalchina.modules.security.Authorize;
import com.digitalchina.modules.security.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 事件基本操作接口
 *
 * @author lichunlong
 * @since 2019/9/5
 */
@Api(tags = "事件基本接口")
@Authorize
@RestController
public class EventController {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    IdentityService identityService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    EventService eventService;

    @ApiOperation("获取当前用户部门信息")
    @GetMapping("getBedptByUserId")
    public Bedept getBedptByUserId() {
        Integer curuid = SecurityUtil.currentUserId();
        Optional.ofNullable(curuid).orElseThrow(() -> new ServiceException("用户未登录"));
        return eventService.getBedptByUserId(curuid);
    }

    @ApiOperation("获取当前用户部门信息-标准response响应格式")
    @GetMapping("getBedptByUserId2")
    public RespMsg<Bedept> getBedptByUserId2() {
        return RespMsg.ok(getBedptByUserId());
    }


    @ApiOperation("发布普通事件流程")
    @GetMapping("deployNormalEventProcess")
    public String deployNormalEventProcess() {
        Deployment deployment = repositoryService//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .name("普通事件流程")//声明流程的名称
                .addClasspathResource("process/CcNormalEventManager.bpmn")//加载资源文件，一次只能加载一个文件
                .deploy();//完成部署
        System.out.println("部署ID：" + deployment.getId());//1
        System.out.println("部署时间：" + deployment.getDeploymentTime());

        return "deploy normal event process ok";
    }

    @ApiOperation("发布协查事件流程")
    @GetMapping("deployCoopEventProcess")
    public String deployCoopEventProcess() {
        Deployment deployment = repositoryService//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .name("协查事件流程")//声明流程的名称
                .addClasspathResource("process/CcCoopInvestEventManager.bpmn")//加载资源文件，一次只能加载一个文件
                .deploy();//完成部署
        System.out.println("部署ID：" + deployment.getId());//1
        System.out.println("部署时间：" + deployment.getDeploymentTime());
        return "deploy coop event process ok";
    }


}
