package com.digitalchina.admin.gis.dto;

import com.digitalchina.admin.constant.SRID;
import io.swagger.annotations.ApiModel;

import java.util.HashMap;

@ApiModel("GIS资源对象, 包含Map格式的attributes，用于气泡展示属性。"
        + "包含point或shape，点资源有point属性，结构为[经度,纬度]数组，线和面资源有shape属性，是gis专有格式。"
        + "特殊需求可能包括特有字段，另行约定。例如城市部件有预警状态字段warn_level")
public class GisData extends HashMap<String, Object> {

    public static final String KEY_ATTRIBUTES = "attributes";


//    public void setAttributes(List<PartsAttribute> attributes) {
//        this.put(KEY_ATTRIBUTES, attributes);
//    }

    public void setShape(String shape) {
        this.put("shape", shape);
    }

    public void setSrid(Integer srid) {
        this.put("srid", srid == null ? SRID.Default : srid);
    }
}
