package com.digitalchina.admin.mid.entity.app;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * app版本信息
 * </p>
 *
 * @author Bruce Li
 * @since 2019-11-07
 */
@TableName("app.app_version")
@ApiModel(value = "AppVersion对象", description = "app版本信息")
public class AppVersion implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private Integer id;

	@ApiModelProperty(value = "App系统 IOS，Android")
	private String appOs;

	@ApiModelProperty(value = "App版本号")
	private String appVersion;

	@ApiModelProperty(value = "Android下载链接")
	private String downloadUrl;

	@ApiModelProperty(value = "0-不必要，1-必须更新")
	private Integer isNecessary;

	@ApiModelProperty(value = "版本介绍")
	private String content;

	@ApiModelProperty(value = "创建时间")
	private Date crdt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppOs() {
		return appOs;
	}

	public void setAppOs(String appOs) {
		this.appOs = appOs;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public Integer getIsNecessary() {
		return isNecessary;
	}

	public void setIsNecessary(Integer isNecessary) {
		this.isNecessary = isNecessary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getCrdt() {
		return crdt;
	}

	public void setCrdt(String crdt) {
		this.crdt = StrUtil.isEmpty(crdt) ? new Date() : DateUtil.parseDateTime(crdt);
	}

	public static final String ID = "id";

	public static final String APP_OS = "app_os";

	public static final String APP_VERSION = "app_version";

	public static final String DOWNLOAD_URL = "download_url";

	public static final String IS_NECESSARY = "is_necessary";

	public static final String CONTENT = "content";

	public static final String CRDT = "crdt";

	@Override
	public String toString() {
		return "AppVersion{" + "id=" + id + ", appOs=" + appOs + ", appVersion=" + appVersion + ", downloadUrl="
				+ downloadUrl + ", isNecessary=" + isNecessary + ", content=" + content + ", crdt=" + crdt + "}";
	}
}
