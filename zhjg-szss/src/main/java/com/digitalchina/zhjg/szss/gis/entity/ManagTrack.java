package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("SDE.MANAG_TRACK")
@ApiModel("部件预警信息")

public class ManagTrack  implements Serializable {
    private Integer id;
    private Integer warnInfoId;
    private String managMeasures;
    private String managPic;

    public String getManagPic() {
        return managPic;
    }

    public void setManagPic(String managPic) {
        this.managPic = managPic;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date managTime;

    private Integer managType;

    public Integer getManagType() {
        return managType;
    }

    public void setManagType(Integer managType) {
        this.managType = managType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWarnInfoId() {
        return warnInfoId;
    }

    public void setWarnInfoId(Integer warnInfoId) {
        this.warnInfoId = warnInfoId;
    }

    public String getManagMeasures() {
        return managMeasures;
    }

    public void setManagMeasures(String managMeasures) {
        this.managMeasures = managMeasures;
    }

    public Date getManagTime() {
        return managTime;
    }

    public void setManagTime(Date managTime) {
        this.managTime = managTime;
    }
}
