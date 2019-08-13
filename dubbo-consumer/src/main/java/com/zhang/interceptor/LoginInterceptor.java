/** 
 * <pre>项目名称:computer 
 * 文件名称:LoginInterceptor.java 
 * 包名:com.zhang.interceptor 
 * 创建日期:2019年7月9日下午8:59:37 
 * Copyright (c) 2019, yuxy123@gmail.com All Rights Reserved.</pre> 
 */  
package com.zhang.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/** 
 * <pre>项目名称：computer    
 * 类名称：LoginInterceptor    
 * 类描述：    
 * 创建人：张松光 
 *
 * 励志语录:业精于勤荒于嬉 行成于思毁于随
 *
 * 创建时间：2019年7月9日 下午8:59:37    
 * 修改人：张松光  1005227857@qq.com
 * 修改时间：2019年7月9日 下午8:59:37    
 * 修改备注：       
 * @version </pre>    
 */
public class LoginInterceptor implements HandlerInterceptor {

	/* (non-Javadoc)    
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)    
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)    
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)    
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		Object attribute = session.getAttribute("u");
		if (attribute==null) {
			response.sendRedirect("../login.html");
			return false;
		}
		return true;
	}

}
