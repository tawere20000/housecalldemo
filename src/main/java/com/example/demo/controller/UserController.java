package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.bean.ViolationEvent;
import com.example.demo.model.bean.ViolationRef;
import com.example.demo.model.bean.ViolationRule;
import com.example.demo.model.bean.user.UserBean;
import com.example.demo.model.service.ViolationEventService;
import com.example.demo.model.service.ViolationRefService;
import com.example.demo.model.service.user.PersonalService;
import com.example.demo.model.service.user.UserBeanService;
import com.example.demo.model.service.user.VendorService;

@Controller
public class UserController {
	
	@Autowired
	private UserBeanService userBeanService;
	
	@Autowired
	private ViolationRefService violationRefService;
	
	@Autowired
	private ViolationEventService violationEventService;
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private PersonalService personal_InformationService;
	
	@Autowired
	private VendorService vendorService;
	
	/**
	 * 後台管理:跳轉至會員政策頁面
	 * @return
	 */
	@RequestMapping(value = "/userPolicy")
	public String userPolicy() {
		return "info/userPolicy";
	}
	
	/**
	 * 後台管理:會員總攬
	 * @param pageNo
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/userstable")
	public String usersList(@RequestParam(defaultValue="1") Integer pageNo, Model model,HttpServletRequest request) {
		Page<UserBean> usersPage = userBeanService.pageForUserBean(pageNo, 10);
		List<UserBean> usersList = usersPage.getContent();
		Integer usersTotalPage = usersPage.getTotalPages();
		long usersTotalElement = usersPage.getTotalElements();
		model.addAttribute("currentPage",usersPage.getNumber()+1);
		model.addAttribute("usersList", usersList);
		model.addAttribute("usersTotalPage", usersTotalPage);
		model.addAttribute("usersTotalElement", usersTotalElement);
		String isAjax = request.getHeader("X-Requested-With");
		if(isAjax == null) {
			return "table/usertable";
		}else {
		return "table/usertable :: userlist";
		}
	}
	
	/**
	 * 後台管理:待檢舉事件總攬
	 * @param pageNo
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/violationhandling")
	public String violationhandling(@RequestParam(defaultValue="1") Integer pageNo, Model model,HttpServletRequest request) {
		Page<ViolationRef> violationRefPage = violationRefService.pageForViolationRef(pageNo, 10);
		List<ViolationRef> violationList = violationRefPage.getContent();
		Integer violationTotalPage = violationRefPage.getTotalPages();
		long violationTotalElement = violationRefPage.getTotalElements();
		model.addAttribute("currentPage",violationRefPage.getNumber()+1);
		model.addAttribute("violationList", violationList);
		model.addAttribute("violationTotalPage", violationTotalPage);
		model.addAttribute("violationTotalElement", violationTotalElement);
		String isAjax = request.getHeader("X-Requested-With");
		if(isAjax == null) {
			return "table/violationtable";
		}else {
			return "table/violationtable :: violationList";
		}
	}
	
	/**
	 * 後台管理:取得檢舉事件詳細資訊
	 * @param id
	 * @param map
	 * @return
	 */
	@GetMapping(value = "/getViolationInfo/{violationRefId}")
	public String getViolationInfo(@PathVariable("violationRefId") Integer id, Map<String, Object> map) {
		ViolationRef violationRef = violationRefService.queryViolationRefByID(id);
		map.put("violation", violationRef);
		return "table/violationinfo";
	}
	
	
	/**
	 * 後台管理:檢舉事件審核通過,刪除文章,並發信通知該違規會員
	 * @param violationRefId
	 * @param violationEvent
	 * @param userId
	 * @return
	 */
	@DeleteMapping(value="/violationPass")
	@ResponseBody
	public String violationPassArticleDelete(Integer violationRefId,ViolationEvent violationEvent,Integer userId) {
		try {
		ViolationRef violationRef = violationRefService.queryViolationRefByID(violationRefId);
		ViolationRule violationRule = violationRef.getViolationRule();
		UserBean userBean = userBeanService.getUser(userId);
		
		violationEvent.setUser(userBean);
		violationEvent.setViolationRule(violationRule);
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("housecalltw@gmail.com");
		if(userBean.getCategory().equals("會員")) {
//			message.setTo(personal_InformationService.getPersonInfoById(userId).getEmail());
			message.setTo("tawere20000@gmail.com");
		}else if(userBean.getCategory().equals("廠商")) {
			message.setTo(vendorService.getVendorInfo(userId).getEmail());
		}
		message.setSubject("米奇不妙屋檢舉審核通知");
		message.setText("親愛的用戶您好！\n您於本站 "+violationRule.getViolationArea()+" 之 "+violationRule.getViolationEvent()+" : "+violationEvent.getTarget()+
				",\n因"+violationRule.getViolationReason()+",該內容遭刪除,特此來信通知\n祝您有個美好的一天\n\n\n米奇不妙屋後台管理");
		mailSender.send(message);
		
		violationEventService.saveViolationEvent(violationEvent);
		
		violationRefService.deleteViolationRef(violationRefId);
		return "true";
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
	/**
	 * 後台管理:檢舉事件駁回(審核不通過),刪除該檢舉事件
	 * @param violationRefId
	 * @return
	 */
	@DeleteMapping(value="/violationCancle")
	@ResponseBody
	public String violationCancle(Integer violationRefId) {
		try {
		violationRefService.deleteViolationRef(violationRefId);
		return "true";
		}catch(Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	/**
	 * 後台管理:去登入頁面
	 * @return
	 */
//	@GetMapping(value="/backendlogin")
//	public String login(HttpServletRequest request,Model model) {
//		String referer = request.getHeader("Referer");
//		if(referer!=null) {
//		String resultlocation = referer.replace("http://localhost:8080","");
//		model.addAttribute("referer", resultlocation);
//		}
//		return "page-login";
//	}
//	
//	//後台管理:測試管理員登入攔截
//	@PostMapping(value="/loginCheck")
//	public String loginCheck(HttpSession session,@RequestParam String account,@RequestParam String password,@RequestParam String referer) {
//		UserBean loginuser = userBeanService.getUserByAccountAndPassword(account, password);
//		if(loginuser!=null&&referer!="") {
//			session.setAttribute("loginUser", loginuser);
//			return "redirect:"+referer;
//		}else if(loginuser!=null&&referer=="") {
//			session.setAttribute("loginUser", loginuser);
//			return "redirect:/backendindex";
//		}else {
//			return "page-login";
//		}
//	}
//	

}
