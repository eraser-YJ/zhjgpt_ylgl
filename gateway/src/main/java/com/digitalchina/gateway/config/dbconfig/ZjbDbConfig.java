package com.digitalchina.gateway.config.dbconfig;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.digitalchina.modules.constant.TransConstant;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

// @Configuration
// 这里的basePackages只能扫描mapper，如果把servie也扫进去，重复生成实例，报错
@MapperScan(basePackages = { "com.digitalchina.gateway.zjb.mapper*" }, sqlSessionTemplateRef = "zjbSqlSessionTemplate")
public class ZjbDbConfig {
    // @Primary 确定此数据源为master
    @Bean(name = "zjbDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.zjb")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "zjbSqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("zjbDatasource") DataSource dataSource,
                                                  @Autowired Environment env) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = MyBatisPlusGlobalConfig.createSqlSessionFactory(env,
                DbType.MYSQL);
        sqlSessionFactory.setDataSource(dataSource);
        //sqlSessionFactory.setTypeHandlersPackage("com.digitalchina.admin.utils");
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:com/digitalchina/gateway/zjb/mapper/xml/*Mapper.xml"));
        return sqlSessionFactory.getObject();
    }

    // 配置事务管理器
    @Bean(name = TransConstant.ZJB_TRANSACTION_MANAGER)
    public DataSourceTransactionManager getTransactionManager(@Qualifier("zjbDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "zjbSqlSessionTemplate")
    public SqlSessionTemplate getSqlSessionTemplate(
            @Qualifier("zjbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
