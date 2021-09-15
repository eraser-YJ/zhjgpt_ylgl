package com.digitalchina.gateway.log;

import com.digitalchina.gateway.dto.UserDto;
import com.digitalchina.modules.log.AbstractLogAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect extends AbstractLogAspect {

	@Override
	public String getAppCode(ProceedingJoinPoint point) {

		Object[] obj = point.getArgs();
		if (obj != null && obj.length > 0) {
			Object o = obj[0];
			if(o == null){
				return null;
			}
			if( o instanceof UserDto){
				return ((UserDto) o).getApp();
			}
			return o.toString();
		}
		return null;
	}

}
