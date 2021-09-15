package com.digitalchina.admin.utils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.StringTypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digitalchina.admin.mid.entity.Gis;
import com.digitalchina.common.utils.JsonMapper;

/**
 * <适配postgresql 坐标类型处理器>
 * 
 * @see StringTypeHandler
 * 
 * @author lihui
 * @since 2019年8月5日
 */
@MappedJdbcTypes(JdbcType.VARCHAR) /* 数据库中的数据类型, 坐标是对象转换json后以varchar来处理 */
@MappedTypes(value = Gis.class) /* 转化后的数据类型 */
public class PgGisTypeHandler extends BaseTypeHandler<Gis> {

	private static final Logger log = LoggerFactory.getLogger(PgGisTypeHandler.class);

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Gis parameter, JdbcType jdbcType) throws SQLException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("PgGisTypeHandler>>>getNullableResult parameter:{%s}",
					JsonMapper.toJsonString(parameter)));
		}
		// 这里把4326坐标系下经纬度转换成point点对象,
		String format = "st_geometryfromtext ('POINT(%s %s)', 4326), null)";
		String str = String.format(format, parameter.getLongitude(), parameter.getLatitude());
		ps.setString(i, str);
	}

	@Override
	public Gis getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String str = rs.getString(columnName);
		if (log.isDebugEnabled()) {
			log.debug(String.format("PgGisTypeHandler>>>getNullableResult result:{%s}", str));
		}
		Gis gis = JsonMapper.fromJsonString(str, Gis.class);
		gis.setLongitude(gis.getCoordinates()[0]);
		gis.setLatitude(gis.getCoordinates()[1]);
		return gis;
	}

	@Override
	public Gis getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String str = rs.getString(columnIndex);
		if (log.isDebugEnabled()) {
			log.debug(String.format("PgGisTypeHandler>>>getNullableResult result:{%s}", str));
		}
		Gis gis = JsonMapper.fromJsonString(str, Gis.class);
		gis.setLongitude(gis.getCoordinates()[0]);
		gis.setLatitude(gis.getCoordinates()[1]);
		return gis;
	}

	@Override
	public Gis getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String str = cs.getString(columnIndex);
		if (log.isDebugEnabled()) {
			log.debug(String.format("PgGisTypeHandler>>>getNullableResult result:{%s}", str));
		}
		Gis gis = JsonMapper.fromJsonString(str, Gis.class);
		gis.setLongitude(gis.getCoordinates()[0]);
		gis.setLatitude(gis.getCoordinates()[1]);
		return gis;
	}
}
