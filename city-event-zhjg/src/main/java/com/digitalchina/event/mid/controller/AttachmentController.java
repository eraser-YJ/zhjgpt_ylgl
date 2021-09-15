package com.digitalchina.event.mid.controller;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.dto.AttachmentDto;
import com.digitalchina.event.mid.entity.Attachment;
import com.digitalchina.event.mid.entity.AttachmentCoop;
import com.digitalchina.event.mid.service.AttachmentCoopService;
import com.digitalchina.event.mid.service.AttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 * 附件信息 前端控制器
 * </p>
 *
 * @author lzy
 * @since 2019-09-12
 */
@Api(tags = "附件保存接口")
@RestController
@RequestMapping("event/attachment")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentCoopService attachmentCoopService;

    @PostMapping("save")
    @ApiOperation("保存附件-普通事件")
    public RespMsg<Void> save(AttachmentDto dto) {
        for (int i = 0; i < dto.getFileid().length; i++) {
            Attachment attachment = new Attachment();
            attachment.setBeid(dto.getBeid());
            attachment.setFileid(dto.getFileid()[i]);
            attachment.setFiletype(dto.getFiletype()[i]);
            attachment.setCrdt(new Date());
            attachmentService.save(attachment);
        }
        return RespMsg.ok();
    }

    @PostMapping("save/coop")
    @ApiOperation("保存附件-协查件")
    public RespMsg<Void> saveForCoop(AttachmentDto dto) {
        for (int i = 0; i < dto.getFileid().length; i++) {
            AttachmentCoop attachment = new AttachmentCoop();
            attachment.setBeid(dto.getBeid());
            attachment.setFileid(dto.getFileid()[i]);
            attachment.setFiletype(dto.getFiletype()[i]);
            attachment.setCrdt(new Date());
            attachmentCoopService.save(attachment);
        }
        return RespMsg.ok();
    }

/*    @GetMapping("getAttachmentByBeid")
    @ApiOperation("根据事件ID获取所有的附件")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件ID", dataType = "Integer", required = true)})
    public RespMsg<List<Attachment>> getAttachmentByBeid(Integer beid) {
        List<Attachment> attachmentList=attachmentService.list(Condition.<Attachment>create().eq(Attachment.BEID, beid).orderByDesc(Attachment.CRDT));
        return RespMsg.ok(attachmentList);
    }*/

}
