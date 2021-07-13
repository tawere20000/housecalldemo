package com.example.demo.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.model.bean.PointRule;
import com.example.demo.model.bean.PointsEvent;
import com.example.demo.model.bean.ViolationEvent;
import com.example.demo.model.bean.user.Personal_InformationBean;
import com.example.demo.model.bean.user.UserBean;
import com.example.demo.model.bean.user.Vendor_InformationBean;
import com.example.demo.model.service.user.PersonalService;
import com.example.demo.model.service.user.VendorService;
import com.example.demo.util.RSAUtils;
import com.example.demo.validators.LoginValidator;



@Controller
@SessionAttributes({ "Vendor", "Person", "LoginID","Category"})
public class LoginController {

	String loginForm = "user/login/login";
	String loginOut = "login/logout";

	@Autowired
	PersonalService personalService;

	@Autowired
	ServletContext context;

	@Autowired
	VendorService vendorService;
	
	
	@GetMapping("/loginFirst")
	public String loginFirst(HttpServletRequest request, Model model) {

		UserBean bean = new UserBean();
		model.addAttribute("userBean", bean); // UserBean
		model.addAttribute("loginMsg","此功能需要先登入，謝謝您的配合"); // UserBean
		return loginForm;

	}


	// 讀取登入帳號密碼
	@GetMapping("/login")
	public String login00(HttpServletRequest request, Model model) {
		System.out.println("------------***********************");
		String referer = request.getParameter("referer");
		if(referer!=null) {
		String resultlocation = referer.replace("http://localhost:8080","");
		model.addAttribute("referer", resultlocation);
		}
		UserBean bean = new UserBean();
		model.addAttribute("userBean", bean); // UserBean
		return loginForm;

	}

	// 驗證登入帳號密碼

	@PostMapping("/login")
	public String checkAccount(@ModelAttribute("userBean") UserBean bean, BindingResult result, Model model,
			HttpServletRequest request, HttpServletResponse response,@RequestParam String referer) throws Exception {
		
		LoginValidator validator = new LoginValidator();
		validator.validate(bean, result);
		if (result.hasErrors()) {
			model.addAttribute("referer", referer);
			return loginForm;
		}

		
		//帳號有找到true 沒找到false
		if (personalService.loginCheckAccount(bean.getAccount())) {

			
			UserBean userBean = personalService.loginGetInfo(bean.getAccount());
			// RSA密碼解密

			String userPassword = RSAUtils.decrypt(userBean.getPassword(), userBean.getPrivateKey());

			System.out.println("解密後的密碼" + userPassword);
			System.out.println("輸入的密碼" + bean.getPassword());

			if (userPassword.equals(bean.getPassword())) {

				System.out.println("密碼正確");
				
				
				
				//帳號密碼正確 但狀態是停權不能登入
				if(userBean.getStatus().equals("停權中")||userBean.getStatus().equals("停權")) {
					
					result.rejectValue("invalidCredentials", "", "此帳號已被停權，請洽管理員");
					return loginForm;
				}
				
//				違規次數達三次設定停權回登入畫面
				 List<ViolationEvent> personViolation = personalService.getViolationById(userBean.getUserId());
				 
				 if(personViolation.size()>=3) {
					 userBean.setStatus("停權中");
					 personalService.updateUserBean(userBean);
					 result.rejectValue("invalidCredentials", "", "米奇不妙屋提醒您，您的違規次數已達三次，帳號已被停權");
						return loginForm;
				 }
				
				
				Personal_InformationBean personBean = null;
				Vendor_InformationBean vendorBean = null;

				userBean.setRememberMe(bean.isRememberMe());
				if (userBean.getCategory().equals("會員")) {
					System.out.println("帳號是會員");
					personBean = personalService.getPersonInfoById(userBean.getUserId());
					
////////////sp					personalService.loginPlusPoint(personBean.getUserId());
					System.out.println("積分!!!!!!!!!!!!!!!====:"+personBean.getTotalPoints());
					
					boolean checkLoginDay = personalService.judgmentLoginPoint(userBean.getUserId(), 3);

					if (checkLoginDay) {
						PointRule loginRule = personalService.getPointRuleById(3);

						PointsEvent pointsEvent = new PointsEvent();

						pointsEvent.setPointRule(loginRule);
						pointsEvent.setUser(userBean);
						pointsEvent.setDetails("每日登入");
						pointsEvent.setTimes(1);
						personalService.loginPlusPoint(pointsEvent);

					}
					personalService.recalculatePoint(personBean.getUserId());
					model.addAttribute("Person", personBean);
					
					model.addAttribute("Category","會員");
					model.addAttribute("LoginID", userBean.getUserId());
					HttpSession session = request.getSession();
					processCookies(userBean, request, response);

					System.out.println("會員登入成功");

					// 導入登入前頁面
//					String nextPath = (String) session.getAttribute("requestURI");
					// 回首頁
//					if (nextPath == null) {
//						nextPath = request.getContextPath();
//					}
//						return "redirect: " + nextPath;
					return "redirect:"+referer;
					
				}else if(userBean.getCategory().equals("管理員")){
					personBean = personalService.getPersonInfoById(userBean.getUserId());
					model.addAttribute("Category","管理員");
					model.addAttribute("LoginID", userBean.getUserId());
					processCookies(userBean, request, response);
					return "redirect:/backendindex";

				} else {
					System.out.println("帳號是廠商");
					vendorBean = vendorService.getVendorInfo(userBean.getUserId());
					
					boolean checkLoginDay = personalService.judgmentLoginPoint(userBean.getUserId(), 3);

					if (checkLoginDay) {
						PointRule loginRule = personalService.getPointRuleById(3);

						PointsEvent pointsEvent = new PointsEvent();

						pointsEvent.setPointRule(loginRule);
						pointsEvent.setUser(userBean);
						pointsEvent.setDetails("每日登入");
						pointsEvent.setTimes(1);
						personalService.loginPlusPoint(pointsEvent);

					}
					vendorService.recalculatePoint(userBean.getUserId());
					model.addAttribute("Vendor", vendorBean);
					
					model.addAttribute("Category","廠商");
					model.addAttribute("LoginID", userBean.getUserId());
					HttpSession session = request.getSession();
					processCookies(userBean, request, response);

					System.out.println("廠商登入成功");

					// 導入登入前頁面
//					String nextPath = (String) session.getAttribute("requestURI");
					// 回首頁
//					if (nextPath == null) {
//						nextPath = request.getContextPath();
//					}
//						return "redirect: " + nextPath;
					return "redirect:"+referer;

				}
				// 密碼錯誤
			} else {
				System.out.println("密碼錯誤");
				result.rejectValue("invalidCredentials", "", "密碼輸入錯誤，請重新輸入");
				model.addAttribute("referer", referer);
				return loginForm;

			}

			// 帳號錯誤
		} else {
			System.out.println("帳號錯誤");
			result.rejectValue("invalidCredentials", "", "無此帳號，請重新輸入");
			model.addAttribute("referer", referer);
			return loginForm;
		}

	}

	// ================================================================================

	private void processCookies(UserBean userBean, HttpServletRequest request, HttpServletResponse response) {
		Cookie cookieUserId = null;
//		Cookie cookieRememberMe = null;
		String userId = userBean.getUserId().toString();

		HttpSession session = request.getSession();

		// rm存放瀏覽器送來之RememberMe的選項，如果使用者對RememberMe打勾，rm就不會是null
		if (userBean.isRememberMe()) {

			// 蓋掉原本的JSESSIONID
			Cookie cookie = new Cookie("JSESSIONID", session.getId());
			cookie.setMaxAge(7 * 24 * 60 * 60);
			cookie.setPath(request.getContextPath());
			response.addCookie(cookie);

			cookieUserId = new Cookie("UserId", userId);
			cookieUserId.setMaxAge(7 * 24 * 60 * 60); // Cookie的存活期: 七天
			cookieUserId.setPath(request.getContextPath());

			System.out.println("存cookie有勾記得我");

		} else { // 使用者沒有對 RememberMe 打勾
			cookieUserId = new Cookie("UserId", userId);
			cookieUserId.setMaxAge(0); // MaxAge==0 表示要請瀏覽器刪除此Cookie
			cookieUserId.setPath(request.getContextPath());

			System.out.println("cookie沒勾記得我");

		}
		// 有執行
		response.addCookie(cookieUserId);

	}
	
	@GetMapping("/cookieLogin/{userId}")
	public String cookieLogin(@PathVariable("userId") Integer userId,HttpServletRequest request, HttpServletResponse response, Model model) {

		UserBean userBean = personalService.getUserInfo(userId);
		
		if (userBean.getCategory().equals("會員")) {
			System.out.println("帳號是會員");
			Personal_InformationBean personBean = personalService.getPersonInfoById(userBean.getUserId());

			model.addAttribute("Person", personBean);

			model.addAttribute("Category", "會員");
			model.addAttribute("LoginID", userBean.getUserId());
			processCookies(userBean, request, response);

			System.out.println("會員登入成功");

		} else if (userBean.getCategory().equals("管理員")) {
			System.out.println("帳號是管理員");
		model.addAttribute("Category", "管理員");
		model.addAttribute("LoginID", userBean.getUserId());
		processCookies(userBean, request, response);

		System.out.println("管理員登入成功");

		
		
	} else {
		System.out.println("帳號是廠商");
		Vendor_InformationBean	vendorBean = vendorService.getVendorInfo(userBean.getUserId());
		
		model.addAttribute("Vendor", vendorBean);
		
		model.addAttribute("Category", "廠商");
		model.addAttribute("LoginID", userBean.getUserId());
		processCookies(userBean, request, response);
		
		System.out.println("廠商登入成功");
		
		
	}
		
		
		
		return "redirect:/index";

	}

}
