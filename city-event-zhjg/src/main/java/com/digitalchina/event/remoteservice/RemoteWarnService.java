package com.digitalchina.event.remoteservice;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.config.FeignMultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <监测预警-远程调用>
 * 
 * @author lichunlong
 * @since 2019年11月14日
 */
@FeignClient(value = "citywarn", url = "${citywarn.url:}", configuration = FeignMultipartSupportConfig.class)
public interface RemoteWarnService {

	/**
	 * 处理结果
	 *
	 * @param content:结果内容
	 * @param evid:事件id
	 * @param state:事件状态
	 *
	 * @return 事件ID
	 */
	@PostMapping("/event/inform/handle/result")
	public RespMsg<Integer> handleResult(@RequestParam("content") String content,
											  @RequestParam("evid") Integer evid,
                                              @RequestParam("state") Integer state);

}
