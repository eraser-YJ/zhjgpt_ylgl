package com.digitalchina.admin.utils;


import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.type.*;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <适配postgresqlJson类型处理器>
 *
 * @see ArrayTypeHandler
 * @since 2019年8月5日
 */
@MappedJdbcTypes(JdbcType.OTHER) /* 数据库中的数据类型 */
@MappedTypes(value = JSONArray.class) /* 转化后的数据类型 */
public class PgJson2JsonArrayObjHandler extends BaseTypeHandler<JSONArray> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONArray jsonArray, JdbcType jdbcType) throws SQLException {
        String str = (jsonArray != null && !jsonArray.isEmpty()) ? jsonArray.toJSONString() : null;
        PGobject pGobject = new PGobject();
        pGobject.setType("json");
        pGobject.setValue(str);
        ps.setObject(i, pGobject);
    }

    @Override
    public JSONArray getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        if (str == null) {
            return null;
        }
        return JSONArray.parseArray(str);
    }

    @Override
    public JSONArray getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);
        if (str == null) {
            return null;
        }
        return JSONArray.parseArray(str);
    }

    @Override
    public JSONArray getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);
        if (str == null) {
            return null;
        }
        return JSONArray.parseArray(str);
    }
}
