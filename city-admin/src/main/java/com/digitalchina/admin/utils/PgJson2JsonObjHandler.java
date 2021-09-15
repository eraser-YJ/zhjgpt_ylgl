package com.digitalchina.admin.utils;


import com.alibaba.fastjson.JSONObject;
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
@MappedTypes(value = JSONObject.class) /* 转化后的数据类型 */
public class PgJson2JsonObjHandler extends BaseTypeHandler<JSONObject> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONObject jsonObject, JdbcType jdbcType) throws SQLException {
        String str = (jsonObject != null && !jsonObject.isEmpty()) ? jsonObject.toJSONString() : null;
        PGobject pGobject = new PGobject();
        pGobject.setType("json");
        pGobject.setValue(str);
        ps.setObject(i, pGobject);
    }

    @Override
    public JSONObject getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        if (str == null) {
            return null;
        }
        return (JSONObject) JSONObject.parse(str);
    }

    @Override
    public JSONObject getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);
        if (str == null) {
            return null;
        }
        return (JSONObject) JSONObject.parse(str);
    }

    @Override
    public JSONObject getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);
        if (str == null) {
            return null;
        }
        return (JSONObject) JSONObject.parse(str);
    }
}
