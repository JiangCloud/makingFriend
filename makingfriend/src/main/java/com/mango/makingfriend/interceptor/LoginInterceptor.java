package com.mango.makingfriend.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor implements  HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(true);
		// 从session 里面获取用户名的信息
		Object obj = session.getAttribute("loginer");
		// 判断如果没有取到用户信息，就跳转到登陆页面，提示用户进行登陆
		if (obj == null || "".equals(obj.toString())) {
			response.sendRedirect("/auto_office_test/login.jsp");
		}

		System.out.println("mylogininterceptor:"+handler);
	
		return true;
		
	}



	

}
