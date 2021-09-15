package com.digitalchina.event.mid.service;

import com.digitalchina.event.mid.entity.Attachment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 附件信息 服务类
 * </p>
 *
 * @author lzy
 * @since 2019-09-12
 */
public interface AttachmentService extends IService<Attachment> {
    String getFilesByBeid(Integer beid);
}
