package com.digitalchina.sign;

import java.io.IOException;

import org.junit.Test;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * mybatis plus 代码生成器
 * 
 * @author Warrior 2018年10月7日
 */
public class MidGeneratorServiceEntity {

	private static final String ENTITY_NAME = "entity";
	private static final String CONTROLLER_NAME = "controller";
	// private static final String OUTPUT_DIR =
	// "D:\\project\\city-manage-mid\\trunk\\city-admin\\src\\main\\java";
	private static final String OUTPUT_DIR = "C:\\Work\\Workspace\\changchun\\citi-manage-mid\\city-admin\\src\\main\\java";
	private static final String PACKAGE_NAME = "com.digitalchina.admin.mid";
	private static final String AUTHOR = "Ryan";

	private static final DbType DB_TYPE = DbType.POSTGRE_SQL;
	private static final String DB_URL = "jdbc:postgresql://120.24.195.24:15432/city-manage-mid-test";
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_USER_NAME = "postgres";
	private static final String DB_PASSWORD = "dgchinapgsql@171026";

//	private static final DbType DB_TYPE = DbType.ORACLE;
//	private static final String DB_URL = "jdbc:oracle:thin:@47.112.44.6:1521/orcl";
//	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
//	private static final String DB_USER_NAME = "gis";
//	private static final String DB_PASSWORD = "gis";

	private static final String TABLE_PREFIX = "";
//	private static final String SCHEMA_NAME = "";
	private static final String SCHEMA_NAME = null;

	@Test
	public void generateCode() {
//		generateByTables(false, "JTCX_GJZ", "JTCX_HLD", "JTCX_JTZSP", "JTCX_TCC", "JTCX_XRXHD", "SZSS_DLJ", "SZSS_JSJ",
//				"SZSS_JYZ", "SZSS_LJT", "SZSS_LMP", "SZSS_RLJ", "SZSS_RQJ", "SZSS_TRQDY", "SZSS_TXJ", "SZSS_WSJ",
//				"SZSS_YSJ", "SZSS_YX", "SZSS_ZDXF");

		generateByTables(false, "sys_role_user");
	}

	@Test
	public void deleteCode() throws IOException {
//		deleteEntity("sysOrg");
	}

	/**
	 * @param serviceNameStartWithI user -> UserService, 设置成true: user ->
	 *                              IUserService
	 * @param tableNames
	 */
	private void generateByTables(boolean serviceNameStartWithI, String... tableNames) {
		GlobalConfig config = new GlobalConfig();
		config.setActiveRecord(false).setEnableCache(false) // XML 二级缓存
				.setBaseResultMap(true).setBaseColumnList(true).setAuthor(AUTHOR).setOutputDir(OUTPUT_DIR)
				.setFileOverride(true).setSwagger2(true);
		if (!serviceNameStartWithI) {
			config.setServiceName("%sService");
		}

		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DB_TYPE).setUrl(DB_URL).setSchemaName(SCHEMA_NAME).setUsername(DB_USER_NAME)
				.setPassword(DB_PASSWORD).setDriverName(DB_DRIVER);

		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig.setCapitalMode(true).setEntityLombokModel(false).setNaming(NamingStrategy.underline_to_camel)
				.setTablePrefix(TABLE_PREFIX)// 此处可以修改为您的表前缀
				.setEntityColumnConstant(true) // 生成字段常量
				.setInclude(tableNames)// 修改替换成你需要的表名，多个表名传数组
				.setRestControllerStyle(true);

		new AutoGenerator().setTemplateEngine(new FreemarkerTemplateEngine()).setGlobalConfig(config)
				.setDataSource(dataSourceConfig).setStrategy(strategyConfig).setPackageInfo(new PackageConfig()
						.setParent(PACKAGE_NAME).setController(CONTROLLER_NAME).setEntity(ENTITY_NAME))
				.execute();
	}
}