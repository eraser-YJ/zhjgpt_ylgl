package com.digitalchina.modules.log;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.entity.SysApp;
import com.digitalchina.modules.entity.SysLog;
import com.digitalchina.modules.security.SecurityUtil;
import com.digitalchina.modules.security.UserDetail;
import com.digitalchina.modules.service.SysAppService;
import com.digitalchina.modules.service.SysLogService;
import com.digitalchina.modules.utils.SpelUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 日志记录拦截器
 *
 * @author warrior
 * @since 2019年3月1日
 */

public abstract class AbstractLogAspect {

    @Autowired
    private SysAppService sas;
    @Autowired
    private SysLogService sls;

    private static final Logger LOG = LoggerFactory.getLogger(AbstractLogAspect.class);

    @Around(value = "@annotation(logger)")
    public Object around(ProceedingJoinPoint point, SysLogger logger) throws Throwable {
        Object result = null;
        String message = null;
        Integer status = LogResEnum.SUCCESS.getCode();
        String appCode = getAppCode(point);
        String appName = getAppName(appCode);
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
            saveLog(point, appCode, appName, logger, status, message);
        }
        return result;
    }

    /**
     * 获取系统标识
     *
     * @param point
     * @return
     */
    public abstract String getAppCode(ProceedingJoinPoint point);

    /**
     * 获取系统名称
     *
     * @param appCode
     * @return
     */

    private String getAppName(String appCode) {
        if (StrUtil.isBlank(appCode)) {
            return null;
        }
        SysApp app = sas.findAppByCode(appCode);
        if (app == null) {
            return null;
        }
        return app.getName();
    }

    /**
     * 保存日志
     *
     * @param point
     * @param appCode
     * @param appName
     * @param logger
     * @param status
     * @param message
     */
    private void saveLog(ProceedingJoinPoint point, String appCode, String appName, SysLogger logger, Integer status, String message) {
        try {
            UserDetail currentUser = SecurityUtil.currentUser();
            SysLog sysLog = new SysLog();
            sysLog.setSyscode(appCode);
            sysLog.setSysname(appName);
            sysLog.setActime(new Date());
            sysLog.setResult(status);
            sysLog.setMessage(message);
            String actLog = logger.value();
            if (ObjectUtil.isNotEmpty(actLog)) {
                sysLog.setActlog(SpelUtil.parseMessage(actLog, point));
            }
            if (null != currentUser) {
                sysLog.setUserid(currentUser.getId());
                sysLog.setUsername(currentUser.getName());
                sysLog.setLogin(currentUser.getLogin());
                sysLog.setRoles(currentUser.getRoles());
            }
            String ipAddr = getIpAddr(
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
            sysLog.setIpaddr(ipAddr);

            //当输入错误登录名时
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            String reqUrl = request.getRequestURI();
            if (reqUrl.indexOf("login") != -1) {
                Map<String, String[]> parameterMap = request.getParameterMap();
                String[] loginNm = parameterMap.get("login");
                if (loginNm != null && loginNm.length > 0) {
                    sysLog.setLogin(loginNm[0]);
                }
            }

            sls.save(sysLog);
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
