package com.example.demo.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.model.bean.user.UserBean;

//@Slf4j
public class LonginInterceptor implements HandlerInterceptor{
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
//		Log.info();

		if(session.getAttribute("LoginID")!=null) {
			return true;
		}else {
		redirect(request,response);
		return false;
		}
	}
	
	public void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ request.getContextPath() + "/";
//		System.out.println(request.getHeader("Referer"));
		String referer = request.getHeader("Referer");
			if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				response.setHeader("REDIRECT", "REDIRECT");
				response.setHeader("CONTENTPATH","/login?referer="+referer);
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}else {
				response.sendRedirect("/login?referer="+referer);
				
			}
	}


}
