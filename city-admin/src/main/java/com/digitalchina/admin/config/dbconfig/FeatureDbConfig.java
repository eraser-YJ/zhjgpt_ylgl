//package com.digitalchina.admin.config.dbconfig;
//
//import javax.sql.DataSource;
//
//import com.digitalchina.modules.constant.TransConstant;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import com.zaxxer.hikari.HikariDataSource;
//
//@Configuration
//// 这里的basePackages只能扫描mapper，如果把servie也扫进去，重复生成实例，报错
//@MapperScan(basePackages = "com.digitalchina.admin.feature.mapper*", sqlSessionTemplateRef = "featureSqlSessionTemplate")
//public class FeatureDbConfig {
//
//	@Bean(name = "featureDatasource")
//	@ConfigurationProperties(prefix = "spring.datasource.feature")
//	public DataSource getSDataSource() {
//		return DataSourceBuilder.create().type(HikariDataSource.class).build();
//	}
//
//	@Bean(name = "featureSqlSessionFactory")
//	public SqlSessionFactory getSqlSessionFactory(@Qualifier("featureDatasource") DataSource dataSource,
//			@Autowired Environment env) throws Exception {
//		MybatisSqlSessionFactoryBean sqlSessionFactory = MyBatisPlusGlobalConfig.createSqlSessionFactory(env);
//		sqlSessionFactory.setDataSource(dataSource);
//		sqlSessionFactory.setTypeHandlersPackage("com.digitalchina.admin.utils");
//		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
//				.getResources("classpath*:com/digitalchina/admin/feature/mapper/xml/*Mapper.xml"));
//		return sqlSessionFactory.getObject();
//	}
//
//	// 配置事务管理器
//	@Bean(name = TransConstant.FEATURE_TRANSACTION_MANAGER)
//	public DataSourceTransactionManager getTransactionManager(@Qualifier("featureDatasource") DataSource dataSource) {
//		return new DataSourceTransactionManager(dataSource);
//	}
//
//	@Bean(name = "featureSqlSessionTemplate")
//	public SqlSessionTemplate getSqlSessionTemplate(
//			@Qualifier("featureSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//		return new SqlSessionTemplate(sqlSessionFactory);
//	}
//
//}
