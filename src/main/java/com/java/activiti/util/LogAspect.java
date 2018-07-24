package com.java.activiti.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class LogAspect {

	public void doAfter(JoinPoint jp) {
	}

	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		String params = "";
		String request_ip = "";
		HttpServletRequest request = null;
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof HttpServletRequest) {
				request = (HttpServletRequest) args[i];
				Enumeration<String> enu = request.getParameterNames(); // 获取参数名
				// 获取参数
				while (enu.hasMoreElements()) {
					String paramName = enu.nextElement(); // 参数名
					params += paramName + ":" + request.getParameter(paramName) + ",";

				}
				request_ip = request.getLocalAddr(); // 获取ip

			}

		}
		long time = System.currentTimeMillis();
		Object retVal = pjp.proceed();
		time = System.currentTimeMillis() - time;

		Constants.logger.warn("#################################\r" + "request_ip: " + request_ip + "\r" + "method: " + pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName()
				+ "\r" + "params: " + params + "\r" + "process time: " + time + " ms");
		return retVal;
	}

	public void doBefore(JoinPoint jp) {

	}

	public void doThrowing(JoinPoint jp, Throwable ex) {
		Constants.logger.warn("method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + " throw exception");
		Constants.logger.warn(ex.getMessage());
	}
}