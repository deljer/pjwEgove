package com.pjw.pjwEgove.cmm.utill;

import org.aspectj.lang.ProceedingJoinPoint;

public class ControllAspect {

	
	public Object loggerAop(ProceedingJoinPoint joinPoint)throws Throwable{
		joinPoint.getSignature().toShortString();
		
		Object obj = joinPoint.proceed();
		return obj;
		
	}
		
		
}
