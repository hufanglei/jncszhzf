package com.java.core.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java.activiti.model.MemberShip;

public class LoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
			if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {  
	            throw new ServletException("OncePerRequestFilter just supports HTTP requests");  
	        }  	
	        HttpServletRequest httpRequest = (HttpServletRequest) request;  
	        HttpServletResponse httpResponse = (HttpServletResponse) response;  
	        HttpSession session = httpRequest.getSession(true);  
	  
	        StringBuffer url = httpRequest.getRequestURL();
	        if(url.indexOf("login")>0||url.indexOf("Login")>0){
	        	chain.doFilter(request, response);
	        	return;
	        }
	        
	        
	        Object object = session.getAttribute("currentMemberShip");  
	        MemberShip user = object == null ? null : (MemberShip) object;  
	        if (user == null) {  
	            httpResponse.sendRedirect("/jncszhzf/login.jsp");
	            return;  
	        }  
	        chain.doFilter(request, response);  
	        return;  
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
