package com.digitalchina.admin.gis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import oracle.sql.TIMESTAMP;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * 组件的一个属性
 */
@Data
@AllArgsConstructor
public class PartsAttribute implements Serializable {

    private String key;
    private String name;
    private Object value;

    public Object getValue() {
        if (value != null && value instanceof TIMESTAMP) {
            TIMESTAMP v = (TIMESTAMP) value;
            try {
                return v.dateValue();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return  value;
    }
}
