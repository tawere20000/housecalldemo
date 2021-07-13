package com.example.demo.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes({ "Vendor", "Person", "LoginID" })
public class LogoutController {
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, SessionStatus status, RedirectAttributes redirectAtt) {
		System.out.println("登出Logout");
		// 登出時執行下列兩敘述
		Cookie[] cookies = request.getCookies();
        for (Cookie cookie :cookies){//遍歷所有Cookie
            if(cookie.getName().equals("UserId") || cookie.getName().equals("JSESSIONID")){//找到對應的cookie
                cookie.setMaxAge(0);//Cookie並不能根本意義上刪除，只需要這樣設定為0即可
                cookie.setPath("/");//很關鍵，設定成跟寫入cookies一樣的，全路徑共享Cookie
                response.addCookie(cookie);//重新響應
                System.out.println("登出 cookie刪除");
            }
        }
		
		status.setComplete(); // 移除@SessionAttributes({"LoginOK"}) 標示的屬性物件
		session.invalidate(); // 此敘述不能省略
		return "redirect:/index"; // 跳轉回http://localhost:8080/Context_Path/
	}
}
