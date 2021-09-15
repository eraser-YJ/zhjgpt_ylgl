package com.digitalchina.admin.remoteservice;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.digitalchina.admin.mid.dto.warn2.TypeDto;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.config.FeignMultipartSupportConfig;

/**
 * 监测预警-远程调用接口
 *
 * @author Bruce Li
 * @since 2019/10/28
 */
@FeignClient(value = "citywarn", url = "${citywarn.url:}", configuration = FeignMultipartSupportConfig.class)
public interface CityWarnService {

	/**
	 * 同步用户信息至本系统
	 * 
	 */
	@GetMapping("/monitor/device/getspecialandtheme")
	RespMsg<List<TypeDto>> getspecialandtheme();
}
