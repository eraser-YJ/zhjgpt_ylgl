package com.digitalchina.modules.aop;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.http.HttpStatus;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.exception.SysSecurityException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 日志配置，拦截所有Controller错误并且记录
 * 
 * @author warrior
 *
 * @date 2018年11月30日
 */
@RestControllerAdvice(basePackages = "com.digitalchina")
public class LogAdvice {

	private static final Logger log = LoggerFactory.getLogger(LogAdvice.class);

	// 捕捉所有异常
	@ExceptionHandler(Throwable.class)
	public Object globalException(HttpServletRequest request, HttpServletResponse resp, Throwable e) {
		log.error("服务异常请求路径为：{}", request.getRequestURI(), e);
		// @Valid 支持
		if (e instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException se = (MethodArgumentNotValidException) e;
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, errorMsg(se.getBindingResult()));
		}
		if (e instanceof BindException) {
			BindException se = (BindException) e;
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, errorMsg(se.getBindingResult()));
		}
		if (e instanceof ValidateException) {
			ValidateException se = (ValidateException) e;
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, se.getMessage());
		}

		Integer code = HttpStatus.HTTP_INTERNAL_ERROR;
		if (e instanceof ServiceException) {
			ServiceException se = (ServiceException) e;
			code = se.getCode();
		}
		return RespMsg.error(code, e.getMessage());
	}

	// 捕捉所有异常
	@ExceptionHandler(SysSecurityException.class)
	public Object securityException(HttpServletRequest request, HttpServletResponse resp, SysSecurityException e) {
		log.warn("用户Id：{}，没有权限访问接口服务接口：{}", e.getUserId(), request.getRequestURI(), e);
		return RespMsg.error(e.getCode(), e.getMessage());
	}

	/**
	 * 错误信息组装
	 * 
	 * @param result
	 * @return
	 */
	protected String errorMsg(BindingResult result) {
		StringBuilder errorMsg = new StringBuilder();
		if (result.getErrorCount() > 0) {
			List<ObjectError> objectErrorList = result.getAllErrors();
			for (ObjectError objectError : objectErrorList) {
				String message = objectError.getDefaultMessage();
				if (!StringUtils.isEmpty(message)) {
					errorMsg.append(message).append(";");
				}
			}
			if(!StringUtils.isEmpty(errorMsg)){
				errorMsg.replace(errorMsg.length()-1,errorMsg.length(),".");
			}
		}
		return errorMsg.toString();
	}
}
