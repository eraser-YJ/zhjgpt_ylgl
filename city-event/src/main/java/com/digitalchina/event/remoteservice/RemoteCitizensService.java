package com.digitalchina.event.remoteservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.config.FeignMultipartSupportConfig;

/**
 * <市民互动远程调用>
 * 
 * @author lihui
 * @since 2020年3月20日
 */
@FeignClient(value = "citycitizens", url = "${citycitizens.url:}", configuration = FeignMultipartSupportConfig.class)
public interface RemoteCitizensService {

	/**
	 * 处理结果
	 *
	 * @param content:结果内容
	 * @param evid:事件id
	 * @param state:事件状态
	 *
	 * @return 事件ID
	 */
	@GetMapping("/problemManage/callback")
	public RespMsg<Integer> handleResult(@RequestParam("content") String content, @RequestParam("beid") Integer beid);

}
