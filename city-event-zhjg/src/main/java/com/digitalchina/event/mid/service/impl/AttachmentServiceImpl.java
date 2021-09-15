package com.digitalchina.event.mid.service.impl;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.digitalchina.event.mid.entity.Attachment;
import com.digitalchina.event.mid.mapper.AttachmentMapper;
import com.digitalchina.event.mid.service.AttachmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 附件信息 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2019-09-12
 */
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {

    @Override
    public String getFilesByBeid(Integer beid) {
        List<Attachment> attachmentList = baseMapper.selectList(Condition.<Attachment>create().eq(Attachment.BEID, beid).orderByAsc(Attachment.FILETYPE, Attachment.CRDT));
        if (attachmentList == null || attachmentList.size() == 0) {
            return null;
        }

        StringBuilder breviaryPaths = new StringBuilder();
        for (int i = 0; i < attachmentList.size(); i++) {
            if (breviaryPaths.length() > 0) {//该步即不会第一位有逗号，也防止最后一位拼接逗号！
                breviaryPaths.append(",");
            }
            breviaryPaths.append(attachmentList.get(i).getFileid() + "_" + attachmentList.get(i).getFiletype());
        }
        return breviaryPaths.toString();
    }
}
