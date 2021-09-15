package com.digitalchina.zhjg.szss.gis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author shkstart
 * @create 2020-08-27 11:10
 */
@Data
public class WarnHandle {

    private Integer objectId;//编号

    private String zdbh;//站点编号

    private String zdmc;//站点名称

    private String jcdlx;//监测点类型

    private String status;//处置状态：0 开始 1 处置中 2关闭

    private String czdw;//处置单位

    private String czr;//处置人

    private String sbsj;//上报时间

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date zxclsj;//最新处理时间

    private String yjdj;//预警等级

    private String yjlx;//预警类型

    private String jjcd;//紧急程度

    private String fj;//附件

    private String sjbh;//事件编号
}
