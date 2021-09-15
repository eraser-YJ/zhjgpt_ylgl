package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 积水点预警表
 * SDE.SZSS_JSD_WARN
 * @author shkstart
 * @create 2020-08-26 10:19
 */
@ApiModel("积水点预警表")
@Data
public class PondingPointWarn {

    private Integer objectId;//编号

    private String zdbh;//站点编号

    private String zdmc;//站点名称

    private String yc;//遥测

    private String sp;//视频

    private String jssd;//积水深度

    private String jsmj;//积水面积

    private String jyl;//降雨量

    private String sbsj;//上报时间

    private String sxfz;//上线阀值

    private String yjdj;//预警等级

    private String fzr;//负责人

    private String lxdh;//联系电话

    private String zrdw;//责任单位

    private String szwz;//所在位置

    private String szqy;//所在区域

    private String status;//处置状态：0 处置状态，1 关闭

}
