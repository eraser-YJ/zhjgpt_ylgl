package com.digitalchina.event.utils;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.ArrayTypeHandler;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * <适配postgresql数组类型处理器>
 * 
 * @see ArrayTypeHandler
 * 
 * @author lihui
 * @since 2019年8月5日
 */
@MappedJdbcTypes(JdbcType.ARRAY) /* 数据库中的数据类型 */
@MappedTypes(value = String[].class) /* 转化后的数据类型 */
public class PgArrayTypeHandler extends BaseTypeHandler<String[]> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
		Array array = ps.getConnection().createArrayOf("varchar", parameter);
		ps.setArray(i, array);
	}

	@Override
	public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Array array = rs.getArray(columnName);
		if (null == array)
			return null;
		return (String[]) array.getArray();
	}

	@Override
	public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Array array = rs.getArray(columnIndex);
		if (null == array)
			return null;
		return (String[]) array.getArray();
	}

	@Override
	public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Array array = cs.getArray(columnIndex);
		if (null == array)
			return null;
		return (String[]) array.getArray();
	}
}
