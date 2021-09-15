package com.digitalchina.event.config.dbconfig;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.type.JdbcType;
import org.springframework.core.env.Environment;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.digitalchina.modules.constant.enums.ProfileEnum;

import cn.hutool.core.util.ArrayUtil;

/**
 * mybatis plus公共配置
 * 
 * @author warrior
 *
 * @since 2019年1月25日
 */
public class MyBatisPlusGlobalConfig {

	/**
	 * 公共配置
	 * 
	 * @param env
	 * @return
	 */
	public static MybatisSqlSessionFactoryBean createSqlSessionFactory(Environment env) {
		MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
		sqlSessionFactory.setVfs(SpringBootVFS.class);

		MybatisConfiguration configuration = new MybatisConfiguration();
		configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setCacheEnabled(false);
		sqlSessionFactory.setConfiguration(configuration);

		GlobalConfig conf = GlobalConfigUtils.defaults();
		DbConfig dbConfig = conf.getDbConfig();
		dbConfig.setDbType(DbType.POSTGRE_SQL);
		dbConfig.setIdType(IdType.AUTO);

		List<Interceptor> plugins = new ArrayList<>();
		plugins.add(new PaginationInterceptor());

		// 生产环境
		if (ProfileEnum.PROD.getCode().equals(env.getActiveProfiles()[0].trim())) {
			conf.setRefresh(false);
		} else {
			conf.setRefresh(true);
			plugins.add(new PerformanceInterceptor());
		}
		sqlSessionFactory.setPlugins(ArrayUtil.toArray(plugins, Interceptor.class));
		sqlSessionFactory.setGlobalConfig(conf);

		return sqlSessionFactory;
	}
}
