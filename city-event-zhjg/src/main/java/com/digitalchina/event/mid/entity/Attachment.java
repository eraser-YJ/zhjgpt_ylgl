package com.digitalchina.event.mid.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 附件信息
 * </p>
 *
 * @author lzy
 * @since 2019-09-12
 */
@Data
@TableName("public.attachment")
@ApiModel(value="Attachment对象", description="附件信息")
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "附件ID")
    private Integer atid;

    @ApiModelProperty(value = "事件ID")
    private Integer beid;

    @ApiModelProperty(value = "远程附件ID")
    private String fileid;

    @ApiModelProperty(value = "文件类型 1-img,2-vedio")
    private Integer filetype;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    public static final String ATID = "atid";

    public static final String BEID = "beid";

    public static final String FILEID = "fileid";

    public static final String FILETYPE = "filetype";

    public static final String CRDT = "crdt";

    @Override
    public String toString() {
        return "Attachment{" +
        "atid=" + atid +
        ", beid=" + beid +
        ", fileid=" + fileid +
        ", crdt=" + crdt +
        "}";
    }
}
