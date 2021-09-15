package com.digitalchina.modules.remoteservice;

import com.digitalchina.common.dto.FileDto;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.config.FeignMultipartSupportConfig;
import com.digitalchina.modules.dto.JpushDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件服务
 * 
 * @author warrior
 *
 * @since 2019年9月19日
 */
@FeignClient(value = "thirdservice", url = "${thirdservice.url:}", configuration = FeignMultipartSupportConfig.class)
public interface ThirdRemoteService {

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String upload(@RequestPart("file") MultipartFile file);

	/**
	 * 文件下载
	 * 
	 * @param 文件id
	 * @return
	 * @throws IOException
	 */
	@GetMapping("file/download")
	public FileDto download(@RequestParam(value = "id") String id);


	/**
	 * 尽量不要广播，安卓是不区分测试和生产环境的
	 * 向所有平台推送，包括ios和andriod
	 * @param aliass 推送的个体，用list来包装，可以一个可以多个,传null表示所有用户
	 * @param tags 推送的群组，用list来包装，可以一个也可以多个，传null表示不推送群组
	 * @param content 推送内容
	 * @param newsid app推送唯一标识 唤起app内某功能
	 * @param url  app接到消息跳转的连接
	 * @param title 标题 不传时是默认的标题，可不传
	 */
	@PostMapping("jpush/pushMessage")
	 RespMsg<Void> pushMessage(@RequestBody JpushDto jpushDto);

	/**
	 *向指定设备推送消息
	 * @param registrationIds 极光设备id组
	 * @param content
	 * @param newsid
	 * @param url
	 * @param title
	 * @return
	 */
	@PostMapping("jpush/singlePush")
	 RespMsg<Void> singlePush(@RequestBody JpushDto jpushDto);
}
