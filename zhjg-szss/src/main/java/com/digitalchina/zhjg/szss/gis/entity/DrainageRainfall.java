package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 排水雨量表
 * SDE.SZSS_PSYL
 * @author shkstart
 * @create 2020-08-25 10:43
 */
@ApiModel("排水雨量表")
@Data
public class DrainageRainfall {

    private Integer objectId;//主键

    private String zdbh;//测站编码

    private String tm;//时间

    private String drp;//雨量

    private String intv;//时段长小时

    private String pdr;//降水历时

    private String dyp;//日降水量

    private String wth;//天气状况

    private String voltage;//电压

    private String zdmc;//站点名称

}
