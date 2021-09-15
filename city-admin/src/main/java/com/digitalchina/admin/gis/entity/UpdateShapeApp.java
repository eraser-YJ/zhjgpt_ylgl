package com.digitalchina.admin.gis.entity;

import lombok.Data;

@Data
public class UpdateShapeApp {
    private String code;
    private Long objectId;
    private Double longitude;
    private Double latitude;
    private Integer srid;
}
