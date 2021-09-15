package com.digitalchina.event.mid.service.impl;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.event.mid.entity.Attachment;
import com.digitalchina.event.mid.entity.AttachmentCoop;
import com.digitalchina.event.mid.mapper.AttachmentCoopMapper;
import com.digitalchina.event.mid.service.AttachmentCoopService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 附件信息-协查件 服务实现类
 * </p>
 *
 * @author liuping
 * @since 2019-11-25
 */
@Service
public class AttachmentCoopServiceImpl extends ServiceImpl<AttachmentCoopMapper, AttachmentCoop> implements AttachmentCoopService {
    @Override
    public String getFilesByBeid(Integer ceid) {
        List<AttachmentCoop> attachmentList = baseMapper.selectList(Condition.<AttachmentCoop>create().eq(Attachment.BEID, ceid).orderByAsc(AttachmentCoop.FILETYPE, AttachmentCoop.CRDT));
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
