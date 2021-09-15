
package com.digitalchina.zhjg.szss.aop;

import cn.hutool.core.util.StrUtil;
import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.security.SecurityUtil;
import com.digitalchina.modules.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenProvider tp;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader(SecurityConstant.AUTH_TOKEN);
        if (StrUtil.isNotBlank(token)) {
            tp.refreshToken(token);
            SecurityUtil.setCurrentToken(token);
        }
        return true;
    }
}