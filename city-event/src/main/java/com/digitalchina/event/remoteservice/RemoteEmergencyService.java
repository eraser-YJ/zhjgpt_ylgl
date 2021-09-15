package com.digitalchina.event.remoteservice;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.dto.EmergencyInfoDto;
import com.digitalchina.modules.config.FeignMultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <应急辅助系统-远程调用>
 * 
 * @author liuping
 * @since 2019年12月3日
 */
@FeignClient(value = "cityemergency", url = "${cityemergency.url:}", configuration = FeignMultipartSupportConfig.class)
public interface RemoteEmergencyService {

    /**
     *  事件升级推送至应急辅助系统
     * @param emergencyInfoDto  事件信息
     * @return
     */
    @PostMapping("event/save")
    RespMsg pushEvent(@RequestBody EmergencyInfoDto emergencyInfoDto);
}
