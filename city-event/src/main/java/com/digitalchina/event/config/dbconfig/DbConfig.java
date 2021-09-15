//package com.digitalchina.event.config.dbconfig;
//
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DbConfig {
//
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory getSqlSessionFactory(DataSource dataSource,
//                                                  @Autowired Environment env) throws Exception {
//        MybatisSqlSessionFactoryBean sqlSessionFactory = MyBatisPlusGlobalConfig.createSqlSessionFactory(env);
//        sqlSessionFactory.setDataSource(dataSource);
//        sqlSessionFactory.setTypeHandlersPackage("com.digitalchina.event.utils");
//        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath*:com/digitalchina/event/mid/mapper/xml/*Mapper.xml"));
//        return sqlSessionFactory.getObject();
//    }
//
//}
