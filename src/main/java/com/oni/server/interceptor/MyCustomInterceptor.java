package com.oni.server.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyCustomInterceptor implements HandlerInterceptor {

	// unimplemented methods comes here. Define the following method so that it
	// will handle the request before it is passed to the controller.

	Logger logger = (Logger) LoggerFactory.getLogger(MyCustomInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("Request For Url is = " + request.getRequestURI());
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response) {
//		logger.info("Request For Url is = "+ request.getRequestURI());
//		return true;
//	}

//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		System.out.println("MINIMAL: INTERCEPTOR POSTHANDLE CALLED");
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
//			Exception exception) throws Exception {
//		System.out.println("MINIMAL: INTERCEPTOR AFTERCOMPLETION CALLED");
//	}

}