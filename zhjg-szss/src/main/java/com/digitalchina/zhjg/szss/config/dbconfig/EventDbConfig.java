package com.digitalchina.zhjg.szss.config.dbconfig;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.digitalchina.modules.constant.TransConstant;
import com.digitalchina.zhjg.szss.utils.PgArr2IntArrHandler;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 这里的basePackages只能扫描mapper，如果把servie也扫进去，重复生成实例，报错
@MapperScan(basePackages = { "com.digitalchina.modules.mapper*","com.digitalchina.zhjg.szss.event.mapper*" }, sqlSessionTemplateRef = "eventSqlSessionTemplate")
public class EventDbConfig {
    // @Primary 确定此数据源为master
    @Bean(name = "eventDatasource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.event")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "eventSqlSessionFactory")
    @Primary
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("eventDatasource") DataSource dataSource,
                                                  @Autowired Environment env) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = MyBatisPlusGlobalConfig.createSqlSessionFactory(env, null);
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setTypeHandlersPackage("com.digitalchina.admin.utils");
        sqlSessionFactory.setTypeHandlers(new TypeHandler[]{new PgArr2IntArrHandler()});
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:com/digitalchina/zhjg/szss/event/mapper/xml/*Mapper.xml"));
        return sqlSessionFactory.getObject();
    }

    // 配置事务管理器
    @Bean(name = TransConstant.EVENT_TRANSACTION_MANAGER)
    @Primary
    public DataSourceTransactionManager getTransactionManager(@Qualifier("eventDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "eventSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate getSqlSessionTemplate(
            @Qualifier("eventSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
