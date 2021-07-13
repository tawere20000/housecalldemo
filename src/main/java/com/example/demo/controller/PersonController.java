package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.sql.rowset.serial.SerialBlob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.bean.Blacklist;
import com.example.demo.model.bean.PointRule;
import com.example.demo.model.bean.PointsEvent;
import com.example.demo.model.bean.ViolationEvent;
import com.example.demo.model.bean.comment.CommentArticle;
import com.example.demo.model.bean.comment.CommentReply;
import com.example.demo.model.bean.forum.ForumBean;
import com.example.demo.model.bean.forum.ForumReplyBean;
import com.example.demo.model.bean.user.InnerMessage;
import com.example.demo.model.bean.user.Personal_InformationBean;
import com.example.demo.model.bean.user.UserBean;
import com.example.demo.model.bean.user.Vendor_InformationBean;
import com.example.demo.model.service.user.PersonalService;
import com.example.demo.model.service.user.VendorService;
import com.example.demo.reCaptch.ReCaptchaValidationService;
import com.example.demo.util.PasswordCreateUtil;
import com.example.demo.util.RSAUtils;
import com.example.demo.validators.PersonBeanValidator;
import com.example.demo.validators.UpdateMemberValidator;
import com.example.demo.validators.UpdatePasswordValidator;



@Controller
@SessionAttributes({ "Vendor", "Person", "LoginID", "Category" })
public class PersonController {

	// 當缺少圖片時傳送下列圖片。它們是相對於應用系統根目錄的路徑
	Logger logger = LoggerFactory.getLogger(PersonController.class);
	String noImage = "/data/images/NoImage.png";
	String noImageFemale = "/data/images/NoImage_Female.jpg";
	String noImageMale = "/data/images/NoImage_Male.png";
	String noFactory = "/data/images/NoFactory.jpg";

	@Autowired
	PersonalService personalService;

	@Autowired
	ServletContext context;

	@Autowired
	VendorService vendorService;

//	<!-- 									新增的07042057 -->
	@Autowired
	ReCaptchaValidationService reCaptchaValidationService;
//	<!-- 									新增的07042057 -->
	
	
	
	@GetMapping("/member/showInnerMessage")
	public String showInnerMessage(Model model) {
		
		int iD = (int) model.getAttribute("LoginID");
		
		List<InnerMessage> noread = personalService.getReceiveMessage(iD, "未讀");

		List<InnerMessage> readed = personalService.getReceiveMessage(iD, "已讀");
		
		model.addAttribute("noread",noread);

		model.addAttribute("readed",readed);

		return "/user/member/showInnerMessage";

	}
	
	
	
	
	
	
	@GetMapping("/member/sendInnerMessage")
	public String sendInnerMessage(Model model) {

		InnerMessage innerMessage = new InnerMessage();

		int iD = (int) model.getAttribute("LoginID");

		innerMessage.setUser(personalService.getUserInfo(iD));

		model.addAttribute("innerMessage", innerMessage);

		return "/user/member/sendInnerMessage";

	}

	@PostMapping("/member/sendInnerMessage")
	public String postSendInnerMessage(@ModelAttribute("innerMessage") InnerMessage innerMessage, Model model) {
		int iD = (int) model.getAttribute("LoginID");

		innerMessage.setUser(personalService.getUserInfo(iD));

		String accountExists = personalService.accountExists(innerMessage.getReceiveAccount());

		if (accountExists.equals("empty")) {

			model.addAttribute("errors", "請輸入正確的帳號");

			return "/user/member/sendInnerMessage";
		}

		UserBean receiveUser = personalService.loginGetInfo(innerMessage.getReceiveAccount());

		innerMessage.setReceiverId(receiveUser.getUserId());

		personalService.sendInnerMessage(innerMessage);

		return "redirect:/index";
	}

//	
//	@GetMapping("testB")
//	public String testsave(Model model) {
//		
//		int iD = (int) model.getAttribute("LoginID");
//		
//		Blacklist bk = new Blacklist();	
//		
//		UserBean user = personalService.getUserInfo(2);
//		
//		bk.setUser(personalService.getUserInfo( iD));
//		
//		bk.setBlackedUser(personalService.getUserInfo(1));
//		
//		personalService.saveBlack(bk);
//		
//		return "index";
//		
//		
//	}

	// 聊天室
	@GetMapping("/member/chat")
	public String chat(Model model) {

		int iD = (int) model.getAttribute("LoginID");

		String category = (String) model.getAttribute("Category");

		UserBean user = new UserBean();

		// 判斷是會員還是廠商
		if (category.equals("會員")) {
			user = personalService.getPersonInfoById(iD);
		} else {
			user = vendorService.getVendorInfo(iD);
		}

		model.addAttribute("user", user);

		return "/user/member/webSocket";
	}

	// 會員個人首頁
	@GetMapping("/member/memberIndex")
	public String memberIndex(Model model) {

		int iD = (int) model.getAttribute("LoginID");

		String category = (String) model.getAttribute("Category");

		UserBean user = new UserBean();

		// 判斷是會員還是廠商
		if (category.equals("會員")) {
			user = personalService.getPersonInfoById(iD);
		} else {
			user = vendorService.getVendorInfo(iD);
		}

		model.addAttribute("user", user);

		return "/user/member/memberIndex";
	}

	// 刪黑名單
	@GetMapping("/delete/{userId}")
	public String deleteBlack(@PathVariable("userId") Integer userId) {

		personalService.deleteBlackByUserId(userId);

		return "redirect:/member/blacklist";
	}

	// 疑難雜症區
	@GetMapping("/member/trouble")
	public String showtrouble(Model model) {

		int iD = (int) model.getAttribute("LoginID");

		UserBean user = personalService.getUserInfo(iD);

		if (user.getCategory().equals("會員")) {

			Personal_InformationBean person = personalService.getPersonInfoById(iD);
			model.addAttribute("trouble", person);
			return "user/personFeatures/troubleTest";

		} else {

			Vendor_InformationBean vendor = vendorService.getVendorInfo(iD);
			model.addAttribute("trouble", vendor);
			return "user/personFeatures/troubleVendor";
		}
		
	}

	// 看積分
	@GetMapping("/member/showViolation")
	public String showViolation(Model model) {

		int iD = (int) model.getAttribute("LoginID");

		List<ViolationEvent> personViolation = personalService.getViolationById(iD);

//		 personViolation.

		model.addAttribute("personViolation", personViolation);

		return "user/member/showViolation";
	}

	// 註冊檢查Email是否重複
	@PostMapping("/checkEmailAjax")
	public @ResponseBody Map<String, String> checkEmailAjax(@RequestParam(value = "emailValue") String emailValue) {
		Map<String, String> map = new HashMap<>();

		String email = personalService.emailExists(emailValue);

		map.put("a", email);
		return map;
	}

	// 註冊檢查帳號是否重複
	@PostMapping("/checkAccountAjax")
	public @ResponseBody Map<String, String> checkAccountAjax(
			@RequestParam(value = "accountValue") String accountValue) {
		Map<String, String> map = new HashMap<>();

		String account = personalService.accountExists(accountValue);

		map.put("a", account);
		return map;
	}

	// ============================查看討論區發過那些文章
	@GetMapping("/member/userPoForumArticle")
	public String showFourmPoAritical(Model model) {

		int iD = (int) model.getAttribute("LoginID");

		// 查所有ID發文 回文刪掉
		List<ForumBean> personPooo = personalService.getForumArticleByPersonalId(iD);

		List<ForumBean> personRePo = new ArrayList<ForumBean>();

		List<ForumBean> personPo = new ArrayList<ForumBean>();

		// 回文加到personRePo裡 把personPo是回文的刪掉
		for (ForumBean f : personPooo) {

			if (f.getRespondForumKey() != null) {

				String reAritcleName = personalService.getForumBean(f.getRespondForumKey()).getTitle();

				// 回復哪篇文章標題暫放到text裡面 自己文章標題在title裡
				f.setText(reAritcleName);

				personRePo.add(f);

			} else {
				// 刪掉回文的 只留發文
				personPo.add(f);
			}

		}

		List<ForumReplyBean> personReplay = personalService.getForumReplyByPersonalId(iD);

		model.addAttribute("personReplay", personReplay);

		model.addAttribute("personRePo", personRePo);

		model.addAttribute("person", personPo);

		return "user/personFeatures/userPoForumArticle";
	}

	// ============================查看評論區發過那些文章
	@GetMapping("/member/poArticle")
	public String showPoAritical(Model model) {

		int iD = (int) model.getAttribute("LoginID");

		List<CommentArticle> personPo = personalService.getArticleByPersonalId(iD);

		List<CommentReply> personReply = personalService.getCommentyReplyByPersonalId(iD);

		model.addAttribute("person", personPo);

		model.addAttribute("personReply", personReply);

		return "user/personFeatures/poArticle";
	}

	// 黑名單
	@GetMapping("/member/blacklist")
	public String showBlacklist(Model model) {

		Integer iD = (Integer) model.getAttribute("LoginID");

		List<Blacklist> personBlack = personalService.getBlacklistByPersonalId(iD);

		ArrayList<UserBean> blackId = new ArrayList<>();

		for (Blacklist b : personBlack) {

			UserBean user = personalService.getUserInfo(b.getBlackedUser().getUserId());

			if (user.getCategory().equals("會員")) {

				Personal_InformationBean person = personalService.getPersonInfoById(user.getUserId());

				blackId.add(person);

			} else {

				Vendor_InformationBean vendor = vendorService.getVendorInfo(user.getUserId());

				blackId.add(vendor);

			}

		}

		model.addAttribute("Blacklist", blackId);

		return "user/personFeatures/blacklist";

	}

	// =======================================忘記密碼=========
	@GetMapping("/forgetPassword")
	public String forgetPassword(Model model) {

		System.out.println("getforget");

//		Personal_InformationBean p = new Personal_InformationBean();
//		
//		model.addAttribute("P",p);

		return "user/login/forgetPassword";
	}
	// =======================================忘記密碼=========

	@PostMapping("/forgetPassword")
//	public String sendMail(@ModelAttribute("P") Personal_InformationBean person,BindingResult result,Model model) {
	public String sendMail(Model model, @RequestParam(value = "email") String email,
			@RequestParam(value = "phoneNumber") String phoneNumber) throws Exception {

		if (email == "" || phoneNumber == "") {
			model.addAttribute("errors", "Email或電話號碼不能空白");

			return "user/login/forgetPassword";
		}

//		0 查無資料
//		1 會員
//		2 廠商

		int number = personalService.checkEamilAndPhone(email, phoneNumber);
		if (number == 1) {

			Personal_InformationBean personBean = personalService.getByEamilAndPhone(email, phoneNumber);

			String defaultPassword = PasswordCreateUtil.createPassWord(8);

			Map<String, String> map = RSAUtils.encryptPassword(defaultPassword);

			String encryptByPublicKey = map.get("encryptByPublicKey");

			String privateKey = map.get("privateKey");

			personBean.setPassword(encryptByPublicKey);

			personBean.setPrivateKey(privateKey);

			personalService.updatePersonInfo(personBean);

			personalService.sendSimpleMail(personBean.getEmail(), "親愛的用戶您好,已幫您重置密碼", "您的初始密碼為：" + defaultPassword);

			return "redirect:/index";

		} else if (number == 2) {

			Vendor_InformationBean vendorBean = vendorService.getByEamilAndPhone(email, phoneNumber);

			String defaultPassword = PasswordCreateUtil.createPassWord(8);

			Map<String, String> map = RSAUtils.encryptPassword(defaultPassword);

			String encryptByPublicKey = map.get("encryptByPublicKey");

			String privateKey = map.get("privateKey");

			vendorBean.setPassword(encryptByPublicKey);

			vendorBean.setPrivateKey(privateKey);

			vendorService.updateVendorInfo(vendorBean);

			personalService.sendSimpleMail(vendorBean.getEmail(), "親愛的用戶您好,已幫您重置密碼", "您的初始密碼為：" + defaultPassword);

			return "redirect:/index";

		} else {
			model.addAttribute("errors", "Email或電話號碼錯誤");

			return "user/login/forgetPassword";
		}

	}

	// -============================================================

	// ------===========================積分積分積分積分積分=========================
	@GetMapping("/member/showPoints")
	public String showPoints(@ModelAttribute("User") UserBean userbean, Model model) {

		int iD = (int) model.getAttribute("LoginID");

		UserBean user = personalService.getUserInfo(iD);

		if (user.getCategory().equals("會員")) {

			Personal_InformationBean person = personalService.getPersonInfoById(iD);

			model.addAttribute("User", person);
		} else {

			Vendor_InformationBean vendor = vendorService.getVendorInfo(iD);
			model.addAttribute("User", vendor);
		}

		List<PointRule> rule = personalService.getPointRule();

		model.addAttribute("rule", rule);

		List<PointsEvent> record = personalService.getPointRecord(iD);

		model.addAttribute("record", record);

		return "user/member/showPoints";
	}

	@GetMapping("/mem/{iD}")
	public String showDataForm(@PathVariable("iD") Integer iD, Model model) {

		UserBean user = personalService.getUserInfo(iD);

		if (user.getCategory().equals("會員")) {

			Personal_InformationBean person = personalService.getPersonInfoById(iD);

			model.addAttribute("Person", person);
		} else {

			Vendor_InformationBean vendor = vendorService.getVendorInfo(iD);
			model.addAttribute("Person", vendor);
		}

		return "user/member/updatePersonStatus";
	}

	@PostMapping("/mem/{iD}")
	public String modifyStatus(@ModelAttribute("Person") UserBean person, Model model) {

		personalService.updateUserBean(person);

		return "redirect:/index";
//		return "redirect:/member/showAllMembers";
	}

	// 更新密碼
	@GetMapping("/member/updatePassword")
	public String showAccountForm(Model model) {

		UserBean userA = new UserBean();

		model.addAttribute("UserA", userA);

		return "user/member/updatePassword";
	}

	// 更新密碼
	@PostMapping("/member/updatePassword")
	public String modifyAccount(@ModelAttribute("UserA") UserBean userBean, BindingResult result, Model model)
			throws Exception {

		UpdatePasswordValidator validator = new UpdatePasswordValidator();
		validator.validate(userBean, result);
		if (result.hasErrors()) {
			return "user/member/updatePassword";
		}

		int iD = (int) model.getAttribute("LoginID");

		UserBean user = personalService.getUserInfo(iD);

		// RSA密碼解密
		String userPassword = RSAUtils.decrypt(user.getPassword(), user.getPrivateKey());

		if (userPassword.equals(userBean.getOriginalPassword())) {

			System.out.println("密碼與舊密碼一致");

			Map<String, String> map = RSAUtils.encryptPassword(userBean.getPassword());

			String encryptByPublicKey = map.get("encryptByPublicKey");

			String privateKey = map.get("privateKey");

			user.setPassword(encryptByPublicKey);

			user.setPrivateKey(privateKey);

			// 更新進資料庫
			personalService.updatePassword(user);

			// 更新session 將個人資料userBean物件放入Session範圍內，識別字串為"User"
			model.addAttribute("User", user);
			return "redirect:/member/memberIndex";
		} else {

			model.addAttribute("wrongPassword", "舊密碼輸入錯誤");
			return "user/member/updatePassword";
		}

	}

//	
//	
//	
//	
//	
	// ------------------------------------------ 更新會員個人資訊

	// 功能：當使用者按下『修改之超連結』準備修改會員資料時，本方法送回含有會員資料的表單讓使用者修改。

	@GetMapping("/member/updateMemberInfo")
	public String showDataForm(Model model) {
		System.out.println("========================================================");
		int iD = (int) model.getAttribute("LoginID");
		Personal_InformationBean member = personalService.getPersonInfoById(iD);
		model.addAttribute("Person", member);
		System.out.println("更新getmammping");
		return "user/member/updateMemberInfo";
	}

	// ----------------------------------------------更新會員個人資訊
	@PostMapping("/member/updateMemberInfo")
	public String modify(@ModelAttribute("Person") Personal_InformationBean personBean, BindingResult result,
			Model model) {
		System.out.println("更新postmammping");
		UpdateMemberValidator validator = new UpdateMemberValidator();
		validator.validate(personBean, result);
		if (result.hasErrors()) {
			// 檢核時發現錯誤
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println("有錯誤：" + error);
				System.out.println("haserror");
			}
			return "user/member/updateMemberInfo";
		}

		// 處理上傳的圖片
		MultipartFile picture = personBean.getUserImage();
		System.out.println("更新postmammping2");

		if (picture.getSize() == 0) {
			// 表示使用者並未挑選圖片
			Personal_InformationBean original = personalService.getPersonInfoById(personBean.getUserId());
			personBean.setHeadShot(original.getHeadShot());
		} else {
//					String originalFilename = picture.getOriginalFilename();
//					if (originalFilename.length() > 0 && originalFilename.lastIndexOf(".") > -1) {
//						personBean.setFileName(originalFilename);
//					}

			// 建立Blob物件
			if (picture != null && !picture.isEmpty()) {
				try {
					byte[] b = picture.getBytes();
					Blob blob = new SerialBlob(b);
					personBean.setHeadShot(blob);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
				}
			}
		}
		personalService.updatePersonInfo(personBean);
		personBean = personalService.getPersonInfoById(personBean.getUserId());

		// 更新session 將個人資料personBean物件放入Session範圍內，識別字串為"person"
		System.out.println("更新會員資料成功");
		model.addAttribute("Person", personBean);
		return "redirect:/member/memberIndex";
	}

	// ----------------- 功能：依照前端送來的Member Id來讀取會員資料(Member物件)，然後傳回位於該物件內的大頭貼
	@GetMapping("/picture/{userId}")
	public ResponseEntity<byte[]> getPicture(@PathVariable("userId") Integer userId) {
		byte[] body = null;
		ResponseEntity<byte[]> re = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());

		String category = personalService.getUserInfo(userId).getCategory();

		if (category.equals("會員")) {

			Personal_InformationBean personBean = personalService.getPersonInfoById(userId);
			if (personBean == null) {
				return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
			}

			Blob blob = personBean.getHeadShot();
			if (blob != null) {
				body = blobToByteArray(blob);
			} else {
				String path = null;
				if (personBean.getGender() == null || personBean.getGender().length() == 0) {
					path = noImageMale;
				} else if (personBean.getGender().equals("男")) {
					path = noImageMale;
				} else {
					path = noImageFemale;
					;
				}
				body = fileToByteArray(path);
			}
			re = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);

			return re;

		} else {
			Vendor_InformationBean vendorBean = vendorService.getVendorInfo(userId);
			if (vendorBean == null) {
				return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
			}

			Blob blob = vendorBean.getHeadShot();
			if (blob != null) {
				body = blobToByteArray(blob);
			} else {
				String path = noFactory;

				body = fileToByteArray(path);
			}
			re = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);

			return re;

		}

	}

	// ----------------------------------------- 註冊會員帳號
	@GetMapping("/registerMember")
	public String showEmptyAccountForm(Model model) {
		Personal_InformationBean memberBean = new Personal_InformationBean();

		model.addAttribute("member", memberBean);

		System.out.println("進到註冊get");

		return "user/member/addMember";
	}

	// ----------------------------------------- 註冊會員帳號
//	==================================================修改存照片路徑過
//	<!-- 									新增的07042057 -->
	@PostMapping("/registerMember")
	public String addUser(@ModelAttribute("member") Personal_InformationBean personBean, BindingResult result,
			@RequestParam(name = "g-recaptcha-response") String captcha) throws Exception {

		if (!reCaptchaValidationService.validateCaptcha(captcha)) {
			result.rejectValue("invalidCredentials", "", "請記得點擊我不是機器人");
			return "user/member/addMember";
		}

//		 <!-- 									新增的07042057 -->	

		System.out.println("=========註冊會員========");

		// 設定新註冊預設值
		personBean.setCategory("會員");

		PersonBeanValidator validator = new PersonBeanValidator();
		validator.validate(personBean, result);
		if (result.hasErrors()) {
//	          下列敘述可以理解Spring MVC如何處理錯誤			
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println("有錯誤：" + error);
			}
			return "user/member/addMember";
		}

		// 檢查 帳號 是否重複
		if (personalService.loginCheckAccount(personBean.getAccount())) {
			result.rejectValue("account", "", "帳號已存在，請重新輸入");
			return "user/member/addMember";
		}

		// RSA加密

		Map<String, String> map = RSAUtils.encryptPassword(personBean.getPassword());

		String encryptByPublicKey = map.get("encryptByPublicKey");

		String privateKey = map.get("privateKey");

		personBean.setPassword(encryptByPublicKey);

		personBean.setPrivateKey(privateKey);

		MultipartFile picture = personBean.getUserImage();
//		String originalFilename = picture.getOriginalFilename();
//		personBean.setP_Path(originalFilename);
//			}

		// 建立Blob物件，交由 Hibernate 寫入資料庫
		if (picture != null && !picture.isEmpty()) {
			try {
				byte[] b = picture.getBytes();
				Blob blob = new SerialBlob(b);
				personBean.setHeadShot(blob);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
			}
		}

		if (!picture.isEmpty()) {
			String fileName =personBean.getAccount()+"_"+picture.getOriginalFilename();
			// 將圖片即相對路徑保存至資料庫
			personBean.setP_Path("/temp"+File.separator + fileName);
			// 同時下載到本機存放
			// 1.指定資料夾路徑
			String path = "D:\\Programming\\temp"+File.separator  + fileName;
			System.out.println(path);
			// 2.確定該系統路徑下是否有指定的資料夾
			File file = new File("D:\\Programming\\temp");
			if (!(file.exists() && file.isDirectory())) {
				// 沒有,則建立資料夾
				System.out.println("沒有資料夾");
				file.mkdir();
			}
			// 有則直接寫入
			File file1 = new File(path);
			picture.transferTo(file1);
			System.out.println("成功存入");
		}
		
		// 存入資料庫

		personalService.addPresonal_Information(personBean);
		
		
		return "redirect:/index";
	}
//	==================================================修改存照片路徑過
	// -----------------------------------功能：顯示所有會員資料
//	@GetMapping("/showAllMembers")
//	public String list(Model model) {
//		List<Personal_InformationBean> personBeans = personalService.findAllPersonalInfo();
////			List<Personal_InformationBean> personBeans = personalService.findTwoTables();
//
//		System.out.println(personBeans);
//		for (Personal_InformationBean p : personBeans) {
//			System.out.println(p.getIdNumber());
//			System.out.println(p.getBirthday());
//			System.out.println(p.getNickName());
//			System.out.println(p.getName());
//		}
//		model.addAttribute("members", personBeans);
//		return "backend/showAllMembers";
//	}

	// -----------------------------------Ajax--------------------------------------------
//		// 查詢所有會員資料
//		@GetMapping("/members")
//		public @ResponseBody List<Personal_InformationBean> queryAllMembers(Model model) {
//			List<Personal_InformationBean> members = personalService.findAllPersonalInfo();
//			return members;
//		}
//		
//		
//		
//		
//		// 送出顯示所有Member紀錄的表單
//		@GetMapping({ "/showAllMembers" })
//		public String showAllMembersFindView() {
//			return "member/showAllMembers";
//		}

	private byte[] fileToByteArray(String path) {
		byte[] result = null;
		try (InputStream is = context.getResourceAsStream(path);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
			byte[] b = new byte[819200];
			int len = 0;
			while ((len = is.read(b)) != -1) {
				baos.write(b, 0, len);
			}
			result = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private byte[] blobToByteArray(Blob blob) {
		byte[] result = null;
		try (InputStream is = blob.getBinaryStream(); ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
			byte[] b = new byte[819200];
			int len = 0;
			while ((len = is.read(b)) != -1) {
				baos.write(b, 0, len);
			}
			result = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	
	@PostMapping("/member/showMailAjax")
	public @ResponseBody Map<String, InnerMessage> showMailAjax(@RequestParam(value = "id") String id) {

		System.out.println("==============Ajax==============");
		Map<String, InnerMessage> map = new HashMap<>();

		InnerMessage innerMessage = personalService.getMessageById(Integer.valueOf(id));
		
		innerMessage.setMailStatus("已讀");
		
		personalService.sendInnerMessage(innerMessage);

		map.put("a", innerMessage);

		return map;
	}
	
	
	// 轉寄站內信
		@GetMapping("/member/forwardInnerMessage/{id}")
		public String forwardInnerMessage(Model model, @PathVariable("id") Integer id) {

			InnerMessage innerMessage = personalService.getMessageById(id);

			model.addAttribute("innerMessage", innerMessage);

			return "/user/member/forwardInnerMessage";

		}
		

}
