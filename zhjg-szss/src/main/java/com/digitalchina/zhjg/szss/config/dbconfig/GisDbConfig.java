package com.digitalchina.zhjg.szss.config.dbconfig;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.digitalchina.modules.constant.TransConstant;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
// 这里的basePackages只能扫描mapper，如果把servie也扫进去，重复生成实例，报错
@MapperScan(basePackages = { "com.digitalchina.zhjg.szss.gis.mapper*" }, sqlSessionTemplateRef = "gisSqlSessionTemplate")
public class GisDbConfig {

    // @Primary 确定此数据源为master
    @Bean(name = "gisDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.gis")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "gisSqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("gisDatasource") DataSource dataSource,
                                                  @Autowired Environment env) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = MyBatisPlusGlobalConfig.createSqlSessionFactory(env,
                DbType.ORACLE);
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setTypeHandlersPackage("com.digitalchina.admin.utils");
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:com/digitalchina/zhjg/szss/gis/mapper/xml/*Mapper.xml"));
        return sqlSessionFactory.getObject();
    }

    // 配置事务管理器
    @Bean(name = TransConstant.GIS_TRANSACTION_MANAGER)
    public DataSourceTransactionManager getTransactionManager(@Qualifier("gisDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "gisSqlSessionTemplate")
    public SqlSessionTemplate getSqlSessionTemplate(
            @Qualifier("gisSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
