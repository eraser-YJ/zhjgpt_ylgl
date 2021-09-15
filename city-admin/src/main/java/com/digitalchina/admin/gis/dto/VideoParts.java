package com.digitalchina.admin.gis.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoParts implements Serializable {
    private Serializable objectid; // 主键
    private Serializable id; // 摄像头id
    private String shape; //位置数据
    private Integer srid; // 坐标系
    private String type; // 监控设备类别（公安/市政等，实际对应表名)
    private String location; // 位置信息
}
