package com.digitalchina.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 文件服务对象
 * 
 * @author warrior
 *
 * @since 2019年9月18日
 */
@Data
public class FileDto implements Serializable{

	private static final long serialVersionUID = 3328646567669820615L;

	/**
	 * 文件名
	 */
	private String name;

	/**
	 * 文件内容
	 */
	private byte[] content;

	/**
	 * 扩展名
	 */
	private String type;

	/**
	 * 文件大小
	 */
	private long size;
	
	/**
	 * 创建时间
	 */
	private String creatTime;
	
	public void setContent(byte[] content){
		this.content = content;
		this.size = content.length;
	}
}
