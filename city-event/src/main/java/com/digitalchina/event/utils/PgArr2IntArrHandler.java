package com.digitalchina.event.utils;

import org.apache.ibatis.type.*;

import java.sql.*;

/**
 * <适配postgresql数组类型处理器>
 * 
 * @see ArrayTypeHandler
 * 
 * @author lichunlong
 * @since 2019年8月5日
 */
@MappedJdbcTypes(JdbcType.ARRAY) /* 数据库中的数据类型 */
@MappedTypes(value = Integer[].class) /* 转化后的数据类型 */
public class PgArr2IntArrHandler extends BaseTypeHandler<Integer[]> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Integer[] parameter, JdbcType jdbcType) throws SQLException {
		Array array = ps.getConnection().createArrayOf("int", parameter);
		ps.setArray(i, array);
	}

	@Override
	public Integer[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Array array = rs.getArray(columnName);
		if (null == array)
			return null;
		return (Integer[]) array.getArray();
	}

	@Override
	public Integer[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Array array = rs.getArray(columnIndex);
		if (null == array)
			return null;
		return (Integer[]) array.getArray();
	}

	@Override
	public Integer[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Array array = cs.getArray(columnIndex);
		if (null == array)
			return null;
		return (Integer[]) array.getArray();
	}
}
