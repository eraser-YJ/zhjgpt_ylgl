package com.digitalchina.event.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lzy
 * @since 2019/9/12
 */
@Data
public class AttachmentDto {
    @ApiModelProperty(value = "事件ID", required = true)
    private Integer beid;

    @ApiModelProperty(value = "远程附件ID数组", required = true)
    private String[] fileid;

    @ApiModelProperty(value = "文件类型 1-img,2-vedio", required = true)
    private Integer[] filetype;
}
