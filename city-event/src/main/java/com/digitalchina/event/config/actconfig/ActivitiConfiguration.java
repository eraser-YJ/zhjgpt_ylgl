package com.digitalchina.event.config.actconfig;

import org.activiti.engine.*;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 配置activiti相关服务注解：
 * RepositoryService 流程仓库Service,可以管理流程仓库例如部署删除读取流程资源
 * RuntimeService 运行时Service可以处理所有运行状态的流程实例流程控制(开始,暂停,挂起等)
 * TaskService 任务Service用于管理、查询任务，例如签收、办理、指派等
 * IdentityService 身份Service可以管理查询用户、组之间的关系
 * FormService 表单Service用于读取和流程、任务相关的表单数据
 * HistoryService 历史Service用于查询所有的历史数据
 * ManagementService 引擎管理Service，和具体业务无关，主要查询引擎配置，数据库作业
 * DynamicBpmService 动态bpm服务
 */
@Configuration
public class ActivitiConfiguration {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        SpringProcessEngineConfiguration spec = new SpringProcessEngineConfiguration();
        spec.setDataSource(dataSource);
        spec.setTransactionManager(platformTransactionManager);
        spec.setDatabaseSchemaUpdate("true");
        Resource[] resources = null;
        // 启动自动部署流程
//        try {
//            resources = new PathMatchingResourcePatternResolver().getResources("classpath*:process/*.bpmn");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        spec.setDeploymentResources(resources);
        return spec;
    }

    @Bean
    public ProcessEngineFactoryBean processEngine() {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(springProcessEngineConfiguration());
        return processEngineFactoryBean;
    }

    @Bean
    public RepositoryService repositoryService() throws Exception {
        return processEngine().getObject().getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService() throws Exception {
        return processEngine().getObject().getRuntimeService();
    }

    @Bean
    public TaskService taskService() throws Exception {
        return processEngine().getObject().getTaskService();
    }

    @Bean
    public IdentityService identityService() throws Exception {
        return processEngine().getObject().getIdentityService();
    }

    @Bean
    public FormService formService() throws Exception {
        return processEngine().getObject().getFormService();
    }

    @Bean
    public HistoryService historyService() throws Exception {
        return processEngine().getObject().getHistoryService();
    }

    @Bean
    public ManagementService managementService() throws Exception {
        return processEngine().getObject().getManagementService();
    }

    @Bean
    public DynamicBpmnService dynamicBpmnService() throws Exception {
        return processEngine().getObject().getDynamicBpmnService();
    }


}