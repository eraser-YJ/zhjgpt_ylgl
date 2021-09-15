package com.digitalchina.zhjg.szss.log;

import com.digitalchina.modules.log.AbstractLogAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect extends AbstractLogAspect {

	@Value("${spring.application.name}")
	private String applicationName;

	@Override
	public String getAppCode(ProceedingJoinPoint point) {
		return applicationName;
	}

}
