package com.digitalchina.event.config.dbconfig;

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
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 这里的basePackages只能扫描mapper，如果把servie也扫进去，重复生成实例，报错
@MapperScan(basePackages = { "com.digitalchina.modules.mapper*",
		"com.digitalchina.event.midwarn.mapper*" }, sqlSessionTemplateRef = "midSqlSessionTemplate")
public class MidDbConfig {

	// @Primary 确定此数据源为master
	@Bean(name = "midDatasource")
	@ConfigurationProperties(prefix = "spring.datasource.mid")
	public DataSource getDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = "midSqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactory(@Qualifier("midDatasource") DataSource dataSource,
			@Autowired Environment env) throws Exception {
		MybatisSqlSessionFactoryBean sqlSessionFactory = MyBatisPlusGlobalConfig.createSqlSessionFactory(env);
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setTypeHandlersPackage("com.digitalchina.event.utils");
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath*:com/digitalchina/event/midwarn/mapper/xml/*Mapper.xml"));
		return sqlSessionFactory.getObject();
	}

	// 配置事务管理器
	@Bean(name = TransConstant.MID_TRANSACTION_MANAGER)
	public DataSourceTransactionManager getTransactionManager(@Qualifier("midDatasource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "midSqlSessionTemplate")
	public SqlSessionTemplate getSqlSessionTemplate(
			@Qualifier("midSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
