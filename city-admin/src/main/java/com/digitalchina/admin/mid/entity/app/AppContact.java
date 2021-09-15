package com.digitalchina.admin.mid.entity.app;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * app通讯录
 * </p>
 *
 * @author liu ping
 * @since 2019-11-05
 */
@Data
@TableName("app.app_contact")
@ApiModel(value="AppContact对象", description="app通讯录")
public class AppContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "姓名拼音")
    private String pinYin;

    @ApiModelProperty(value = "首字母大写")
    private String letter;

    @ApiModelProperty(value = "电话号码")
    private String tel;

    @ApiModelProperty(value = "部门名称")
    private String departName;

    @ApiModelProperty(value = "职位名称")
    private String jobTitle;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;
    
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    public Date getCrdt(){
    	return crdt;
    }
    public void setCrdt(String crdt){
    	this.crdt = StrUtil.isEmpty(crdt) ? new Date() : DateUtil.parse(crdt);
    }
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    public Date getModt(){
    	return modt;
    }
    public void setModt(String modt){
    	this.modt = StrUtil.isEmpty(modt) ? new Date() : DateUtil.parse(modt);
    }
    

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String AVATAR = "avatar";

    public static final String PIN_YIN = "pin_yin";

    public static final String LETTER = "letter";

    public static final String TEL = "tel";

    public static final String DEPART_NAME = "depart_name";

    public static final String JOB_TITLE = "job_title";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    
	@Override
	public String toString() {
		return "AppContact [id=" + id + ", name=" + name + ", avatar=" + avatar + ", pinYin=" + pinYin + ", letter="
				+ letter + ", tel=" + tel + ", departName=" + departName + ", jobTitle=" + jobTitle + ", crdt=" + crdt
				+ ", modt=" + modt + "]";
	}
    
    

}
