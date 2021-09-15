package com.digitalchina.admin.mid.entity.app;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * app意见反馈
 * </p>
 *
 * @author Bruce Li
 * @since 2019-11-01
 */
@TableName("app.app_feedback")
@ApiModel(value="AppFeedback对象", description="app意见反馈")
public class AppFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "意见描述")
    private String content;

    @ApiModelProperty(value = "提出人id")
    private Integer uid;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    public static final String ID = "id";

    public static final String CONTENT = "content";

    public static final String UID = "uid";

    public static final String CRDT = "crdt";

    @Override
    public String toString() {
        return "AppFeedback{" +
        "id=" + id +
        ", content=" + content +
        ", uid=" + uid +
        ", crdt=" + crdt +
        "}";
    }
}
