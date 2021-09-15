package com.digitalchina.gateway.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.digitalchina.common.dto.FileDto;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.remoteservice.ThirdRemoteService;

import cn.hutool.core.io.FileUtil;

//@RestController
//@RequestMapping("test")
public class TestController {

	@Autowired
	private ThirdRemoteService trs;

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RespMsg<String> upload(@RequestParam MultipartFile file) throws IOException {
		return RespMsg.ok(trs.upload(file));

	}

	/**
	 * 本地文件上传例子
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "uploadfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RespMsg<String> test() throws IOException {
		File file = new File("C:\\Users\\warrior\\Pictures\\img\\纳税证明.png");
		// DiskFileItem 的第一个参数一定要是“file”，要跟服务提供的名字保持一致
		FileItem fileItem = new DiskFileItem("file", null, false, file.getName(), (int) file.length(),
				file.getParentFile());
		FileUtil.writeToStream(file, fileItem.getOutputStream());
		return RespMsg.ok(trs.upload(new CommonsMultipartFile(fileItem)));

	}

	/**
	 * 文件下载 例子
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@GetMapping("get")
	public ResponseEntity<byte[]> downloadFile(String id) throws IOException {
		FileDto file = trs.download(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", URLEncoder.encode(file.getName(), "utf-8"));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		// 返回数据
		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(file.getContent(), headers, HttpStatus.OK);
		return entity;

	}
}
