package com.digitalchina.gateway.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.aop.LogAdvice;

import cn.hutool.core.util.NumberUtil;

/**
 * 自定义错误处理类
 * 
 * @author warrior
 *
 * @since 2019年8月2日
 */
@RestController
public class SysErrorController implements ErrorController {

	private static final Logger log = LoggerFactory.getLogger(LogAdvice.class);

	private final ErrorAttributes errorAttributes;

	@Autowired
	public SysErrorController(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public RespMsg<Object> error(HttpServletRequest request) {
		WebRequest webRequest = new ServletWebRequest(request);
		Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, true);
		String msg = errorAttributes.getOrDefault("error", "internal serve error").toString();
		Integer code = NumberUtil.parseInt(errorAttributes.getOrDefault("status", 500).toString());
		log.error(msg);
		return RespMsg.error(code, msg);
	}

}
