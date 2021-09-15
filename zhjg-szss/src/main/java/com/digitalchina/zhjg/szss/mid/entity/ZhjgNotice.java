package com.digitalchina.zhjg.szss.mid.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 新闻通知
 * public.zhjg_notice
 * @author shkstart
 * @create 2020-08-07 9:48
 */
@ApiModel("新闻通知")
@Data
public class ZhjgNotice {

    private Integer id;//序列号

    private String title;//标题

    private String content;//内容

    private Date createtime;//创建时间
}
