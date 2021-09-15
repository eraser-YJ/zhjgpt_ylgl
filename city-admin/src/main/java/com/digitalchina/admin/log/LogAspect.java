package com.digitalchina.admin.log;

import cn.hutool.core.util.ObjectUtil;
import com.digitalchina.admin.config.security.AdminSecurityUtil;
import com.digitalchina.admin.config.security.AmUserDetail;
import com.digitalchina.admin.mid.entity.AccessLog;
import com.digitalchina.admin.mid.service.AccessLogService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.log.LogResEnum;
import com.digitalchina.modules.utils.SpelUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 日志记录拦截器
 * 
 * @author warrior
 *
 * @since 2019年3月1日
 */
@Aspect
@Component
public  class LogAspect {

	@Autowired
	private AccessLogService als;

	private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

	@Around(value = "@annotation(logger)")
	public Object around(ProceedingJoinPoint point, AccessLogger logger) throws Throwable {
		Object result = null;
		String message = null;
		Integer status = LogResEnum.SUCCESS.getCode();
		try {
			result = point.proceed();
			if (result instanceof RespMsg) {
				RespMsg obj = (RespMsg) result;
				if (!obj.isSuccess()) {
					status = LogResEnum.FAIL.getCode();
					message = obj.getMessage();
				}
			}
		} catch (Throwable e) {
			status = LogResEnum.FAIL.getCode();
			throw e;
		} finally {
			saveLog(point, logger, status, message);
		}
		return result;
	}


	/**
     * 保存日志
	 * @param point
     * @param logger
     * @param status
     * @param message
	 */
	private void saveLog(ProceedingJoinPoint point, AccessLogger logger, Integer status, String message) {
        try {
		AmUserDetail currentUser = AdminSecurityUtil.currentUser();
		AccessLog accesslog = new AccessLog();
		accesslog.setActime(new Date());
		accesslog.setResult(status);
		accesslog.setMessage(message);
		String  actLog = logger.value();
		if(ObjectUtil.isNotEmpty(actLog)){
			accesslog.setActlog(SpelUtil.parseMessage(actLog,point));
		}
		if(null != currentUser) {
			accesslog.setUserid(currentUser.getId());
			accesslog.setUsername(currentUser.getName());
			accesslog.setLogin(currentUser.getLogin());
			accesslog.setRoles(currentUser.getRoles());
		}
			String ipAddr = getIpAddr(
					((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
			accesslog.setIpaddr(ipAddr);
            als.save(accesslog);
		} catch (Throwable e) {
			// 处理异常，不抛出
			LOG.error("记录日志发生错误！", e);
		}
	}

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (StringUtils.isBlank(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
