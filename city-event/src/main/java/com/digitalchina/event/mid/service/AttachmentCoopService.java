package com.digitalchina.event.mid.service;

import com.digitalchina.event.mid.entity.AttachmentCoop;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 附件信息-协查件 服务类
 * </p>
 *
 * @author liuping
 * @since 2019-11-25
 */
public interface AttachmentCoopService extends IService<AttachmentCoop> {

    String getFilesByBeid(Integer ceid);

}
