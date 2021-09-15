package com.digitalchina.event.mid.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 附件信息-协查件
 * </p>
 *
 * @author liuping
 * @since 2019-11-25
 */
@Data
@TableName("public.attachment_coop")
@ApiModel(value="AttachmentCoop对象", description="附件信息-协查件")
public class AttachmentCoop implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "附件ID")
    private Integer atid;

    @ApiModelProperty(value = "事件ID")
    private Integer beid;

    @ApiModelProperty(value = "远程附件ID")
    private String fileid;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "文件类型 1-img,2-vedio")
    private Integer filetype;

    public static final String ATID = "atid";

    public static final String BEID = "beid";

    public static final String FILEID = "fileid";

    public static final String FILETYPE = "filetype";

    public static final String CRDT = "crdt";

}
