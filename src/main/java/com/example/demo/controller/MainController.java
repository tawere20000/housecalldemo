package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.example.demo.model.bean.ViolationEvent;
import com.example.demo.model.bean.ViolationRef;
import com.example.demo.model.bean.ViolationRule;
import com.example.demo.model.bean.forum.ForumBean;
import com.example.demo.model.bean.forum.ForumJudge;
import com.example.demo.model.bean.forum.ForumReplyBean;
import com.example.demo.model.bean.forum.ForumReplyJudge;
import com.example.demo.model.bean.user.Personal_InformationBean;
import com.example.demo.model.bean.user.UserBean;
import com.example.demo.model.bean.user.Vendor_InformationBean;
import com.example.demo.model.service.ViolationEventService;
import com.example.demo.model.service.ViolationRefService;
import com.example.demo.model.service.ViolationRuleService;
import com.example.demo.model.service.forum.ForumJudgeService;
import com.example.demo.model.service.forum.ForumPlusJudgeService;
import com.example.demo.model.service.forum.ForumReplyJudgeService;
import com.example.demo.model.service.forum.ForumReplyPlusJudgeService;
import com.example.demo.model.service.forum.ForumService;
import com.example.demo.model.service.user.PersonalService;
import com.example.demo.model.service.user.VendorService;
import com.example.demo.validators.forum.ForumBeanValidator;
import com.example.demo.validators.forum.ForumReplyValidator;
import com.example.demo.validators.forum.ForumUpdateValidator2;

@Controller
@SessionAttributes({ "Vendor", "Person", "LoginID", "Category" })
public class MainController {

	Logger logger = LoggerFactory.getLogger(PersonController.class);
	String noImage = "/data/images/NoImage.png";
	String noImageFemale = "/data/images/NoImage_Female.jpg";
	String noImageMale = "/data/images/NoImage_Male.png";
	String noFactory = "/data/images/NoFactory.jpg";

	@Autowired
	ServletContext ctx;
	@Autowired
	PersonalService personalService;
	@Autowired
	ForumService service;
	@Autowired
	ViolationRuleService violationRuleService;
	@Autowired
	ViolationEventService violationEventService;
	@Autowired
	ViolationRefService violationRefService;
	@Autowired
	VendorService vendorService;
	@Autowired
	ForumJudgeService forumJudgeService;
	@Autowired
	ForumReplyJudgeService forumReplyJudgeService;
	@Autowired
	ForumPlusJudgeService forumPlusJudgeService;
	@Autowired
	ForumReplyPlusJudgeService forumReplyOlusJudgeService;

	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value ="pageNo")Integer pageNo,Model model) 
			{
		int pageSize=5;
		Page<ForumBean>page=service.findAll(pageNo, pageSize);
		List<ForumBean>listForum=page.getContent();
		
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalForum",page.getTotalElements());
		model.addAttribute("forums",listForum);
		
		
		return "Forum/AllForumsP";
		
	}
	@GetMapping("/AllForumsP")
	public String paged(Model model) {
		return findPaginated(1, model);
		
	}
	
	
	
	
	// 全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試
//	@PostMapping("/pageForums")
//	@ResponseBody
//	public Map<String, Page<ForumBean>> pagedForums(@RequestParam(value = "pageNo") Integer pageNo) {
////	int pageSize = 5;
//
//		Map<String, Page<ForumBean>> map = new HashMap<>();
//		Page<ForumBean> forumBean = service.getPagedForum(pageNo, 3);
//
//		map.put("forums", forumBean);
//
//		return map;
//
//	}

//	@GetMapping("/AllForumsP")
//	public String listP(Model model) {
////	int iD = (int) model.getAttribute("LoginID");
//		model.addAttribute("forums", service.getAllForums());
//		return "Forum/AllForumsP";
//	}
//==============================================================================
	// 論壇首頁拿所有文章
	@GetMapping("/AllForums")
	public String list(Model model) {
//		int iD = (int) model.getAttribute("LoginID");
		model.addAttribute("forums", service.getAllForums());
		return "Forum/AllForums";
	}

//送新增文章表單
	@GetMapping("/member/AddForum")
	public String add(Model model) {

//		if(model.getAttribute("LoginID")==null) {
//			return"/login";
//		}else {
		ForumBean fb = new ForumBean();
		model.addAttribute("fb", fb);
		return "Forum/AddForum";
//		}
	}

//儲存新增文章表單
	@PostMapping("/SaveForum")
	public String save(@ModelAttribute("fb") ForumBean forumBean, BindingResult result, Model model,
			HttpServletRequest request) throws IllegalStateException, IOException {

		int iD = (int) model.getAttribute("LoginID");
		UserBean userBean = personalService.getUserInfo(iD);
		if (userBean.getCategory().equals("會員")) {
			Personal_InformationBean pib = personalService.getPersonInfoById(iD);
			forumBean.setUserName(pib.getName());
		} else {
			Vendor_InformationBean vib = vendorService.getVendorInfo(iD);
			forumBean.setUserName(vib.getName());
		}
		forumBean.setUser(userBean);

		ForumBeanValidator validator = new ForumBeanValidator();
		validator.validate(forumBean, result);
		if (result.hasErrors()) {
			result.rejectValue("image", "", "請選擇照片");
			List<ObjectError> list = result.getAllErrors();
            for (ObjectError error : list) {
                System.out.println("有錯誤：" + error);
                System.out.println("haserror");}
			
			System.out.println("====表單空白走這");
			return "Forum/AddForum";
		}
//存圖片老師版

		MultipartFile picture = forumBean.getImage();
		String originalFilename = picture.getOriginalFilename();
		if (originalFilename.length() > 0 && originalFilename.lastIndexOf(".") > -1) {
			forumBean.setImagePath(originalFilename);
		}
		if (picture != null && !picture.isEmpty()) {
			try {
				byte[] b = picture.getBytes();
				Blob blob = new SerialBlob(b);
				forumBean.setForumImage(blob);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
			}
		}
		if (!picture.isEmpty()) {
			String fileName = picture.getOriginalFilename();
			// 將圖片即相對路徑保存至資料庫
			forumBean.setImagePath(File.separator + fileName);
			// 同時下載到本機存放
			// 1.指定資料夾路徑
			String path = "C:\\housecallimage" + File.separator + fileName;
			System.out.println(path);
			// 2.確定該系統路徑下是否有指定的資料夾
			File file = new File("C:\\housecallimage");
			if (!(file.exists() && file.isDirectory())) {
				// 沒有,則建立資料夾
				file.mkdir();
			}
			// 有則直接寫入
			File file1 = new File(path);
			picture.transferTo(file1);
			System.out.println("成功存入");
		}

		forumBean.setTextGood(0);
		forumBean.setTextBad(0);
		forumBean.setCheckCount(0);
		forumBean.setTextType(1);
		service.save(forumBean);
		ForumJudge forumJudge = new ForumJudge();
		forumJudge.setForumBean(service.getForumById(forumBean.getForumId()));
//		forumJudge.setForumBean(service.save(forumBean));
		forumJudge.setJudgeForumUserId(forumBean.getUser().getUserId());
		forumJudge.setTextGood(0);
		forumJudge.setTextBad(0);
		forumJudge.setJudgeResult(0);
		forumJudge.setStoreResult(0);
		forumJudge.setReportResult(0);
		forumJudge.setBlackResult(0);
		forumJudge.setCheckResult(0);
		forumJudgeService.InsertNewForumJudge(forumJudge);
		System.out.println("成功設定評分");
//		return String.valueOf(saveBean);
		return "redirect:/AllForumsP";
	}

//查詢單一文章
	@GetMapping("/forum/{forumId}")
	public String getForum(@ModelAttribute ForumReplyBean forumReplyBean, BindingResult result, Model model,
			@PathVariable("forumId") Integer Id, HttpServletRequest request) {

		if (model.getAttribute("LoginID") == null) {
			ForumBean forumBean = service.getForumById(Id);
			forumBean.setTextType(0);
			service.updateForum(forumBean);
			List<ForumReplyBean> ForumReplyList = service.findAllMessages(Id);
			for (ForumReplyBean c : ForumReplyList) {
				c.setTextType(0);
				service.updateMessage(c);
			}
			ForumReplyBean forumReply = new ForumReplyBean();
			model.addAttribute("most", service.getView());
			model.addAttribute("recent", service.getTime());
			model.addAttribute("forums", service.getForumById(Id));
			return "Forum/ForumNoLogin";
		} else {
			int iD = (int) model.getAttribute("LoginID");
			UserBean userBean = personalService.getUserInfo(iD);

			if (userBean.getCategory().equals("會員")) {
				Personal_InformationBean pib = personalService.getPersonInfoById(iD);
				forumReplyBean.setUserName(pib.getName());
			} else {
				Vendor_InformationBean vib = vendorService.getVendorInfo(iD);
				forumReplyBean.setUserName(vib.getName());
			}
			forumReplyBean.setUser(userBean);
			ForumBean forumBean = service.getForumById(Id);
			
			model.addAttribute("most", service.getView());
			model.addAttribute("recent", service.getTime());
			model.addAttribute("mb", forumReplyBean);
			model.addAttribute("forums", forumBean);
			return "Forum/Forum";
		}
//		return "redirect:/forum/" + Id.toString();
	}

//送出編輯表單
	@GetMapping("/editForum/{forumid}")
	public String editForum(@PathVariable("forumid") Integer Id, Model model) {
		int iD = (int) model.getAttribute("LoginID");

		model.addAttribute("forums", service.getForumById(Id));
		return "Forum/UpdateForum";
	}

// 儲存編輯表單
	@PostMapping("/updateForum/{forumid}")
	public String update(@ModelAttribute("forums") ForumBean forumBean, BindingResult result, Model model,
			@PathVariable("forumid") Integer Id) {
		ForumUpdateValidator2 validator = new ForumUpdateValidator2();
		validator.validate(forumBean, result);
		if (result.hasErrors()) {
			return "Forum/UpdateForum";
		}
		MultipartFile picture = forumBean.getImage();
		if (picture.getSize() == 0) {
			ForumBean old=service.getForumById(Id);
			forumBean.setForumImage(old.getForumImage());
		} else {
			if (picture != null && !picture.isEmpty()) {
				try {
					byte[] b = picture.getBytes();
					Blob blob = new SerialBlob(b);
					forumBean.setForumImage(blob);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
				}
			}
		}
		int iD = (int) model.getAttribute("LoginID");
		UserBean userBean = personalService.getUserInfo(iD);
		forumBean.setUser(userBean);
//		forumBean.setForumId(Id);
		service.updateForum(forumBean);
		Personal_InformationBean person = personalService.getPersonInfoById(forumBean.getUser().getUserId());
		model.addAttribute("person", person);
		ForumReplyBean forumReplyBean = new ForumReplyBean();
		model.addAttribute("commentArticleOne", service.getForumById(Id));
		model.addAttribute("commentReply", forumReplyBean);
		model.addAttribute("allCommentReplys", service.findAllMessages(Id));

		return "redirect:/forum/" + Id.toString();

	}

//刪除文章
	@GetMapping("/deleteForum/{forumId}")
	private String deletForum(@PathVariable("forumId") Integer Id, Model model) {
		int iD = (int) model.getAttribute("LoginID");
		service.deleteForumByPrimaryKey(Id);
		return "redirect:/AllForumsP";

	}

// 留言區
//新增留言
	// AJAX
//	@PostMapping("/member/saveA/{forumId}")
//	@ResponseBody
//	public ForumReplyBean saveAjaxMessage(@ModelAttribute("mb") ForumReplyBean forumReplyBean, BindingResult result,
//			@PathVariable("forumId") Integer forumId, Model model, HttpServletRequest request) {
//
//		int iD = (int) model.getAttribute("LoginID");
//		UserBean userBean = personalService.getUserInfo(iD);
//		if (userBean.getCategory().equals("會員")) {
//			Personal_InformationBean pib = personalService.getPersonInfoById(iD);
//			forumReplyBean.setUserName(pib.getName());
//		} else {
//			Vendor_InformationBean vib = vendorService.getVendorInfo(iD);
//			forumReplyBean.setUserName(vib.getName());
//		}
//		ForumReplyValidator replyValidator =  new ForumReplyValidator();
//		replyValidator.validate(forumReplyBean, result);
//		if (result.hasErrors()) {
//			result.rejectValue("image", "", "請選擇照片");
//			List<ObjectError> list = result.getAllErrors();
//            for (ObjectError error : list) {
//                System.out.println("有錯誤：" + error);
//                System.out.println("haserror");}
//			
//			System.out.println("====表單空白走這");
//			return "Forum/AddForum";
//		}
//		
//		
//		
//		ForumBean fb = service.getForumById(forumId);
//		forumReplyBean.setForumBean(fb);
//		forumReplyBean.setUser(userBean);
//		service.saveMessage(forumReplyBean);
//		return service.saveMessage(forumReplyBean);
//
//	}

	@PostMapping("/member/save/{forumId}")
	public String saveMessage(@ModelAttribute("mb") ForumReplyBean forumReplyBean, BindingResult result,
			@PathVariable("forumId") Integer forumId, Model model, HttpServletRequest request) {
		System.out.println("====================forumId.toString()" + forumId.toString());
		ForumReplyValidator validator = new ForumReplyValidator();
		validator.validate(forumReplyBean, result);
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
            for (ObjectError error : list) {
                System.out.println("有錯誤：" + error);
                System.out.println("haserror");}
			
			System.out.println("====表單空白走這");
//			return "Forum/Forum";
			return "redirect:/forum/" + forumId.toString();
		}
		int iD = (int) model.getAttribute("LoginID");
		UserBean userBean = personalService.getUserInfo(iD);

		if (userBean.getCategory().equals("會員")) {
			Personal_InformationBean pib = personalService.getPersonInfoById(iD);
			forumReplyBean.setUserName(pib.getName());
		} else {
			Vendor_InformationBean vib = vendorService.getVendorInfo(iD);
			forumReplyBean.setUserName(vib.getName());
		}

		ForumBean fb = service.getForumById(forumId);
		forumReplyBean.setForumBean(fb);
		forumReplyBean.setUser(userBean);
		service.saveMessage(forumReplyBean);
//		model.addAttribute("forumOne", forumPlusJudgeService.GetForumArticleAndJudge(forumId, iD));
//		model.addAttribute("mb", );
//		model.addAttribute("forums", fb);
		System.out.println("forumId.toString()" + forumId.toString());
		return "redirect:/forum/" + forumId.toString();
	}

	// 刪除留言
	@GetMapping("/deleteMessage/{replyId}")
	private String deletMessage(@PathVariable("replyId") Integer Id, Model model) {
		int iD = (int) model.getAttribute("LoginID");
		ForumReplyBean mb = service.getMessageById(Id);
		Integer forumId = mb.getForumBean().getForumId();
		service.deleteMessageByPrimaryKey(Id);

		return "redirect:/forum/" + forumId.toString();
	}

//	@GetMapping("/deleteMessage/{replyId}")
//	private String deletAJAXMessage(@PathVariable("replyId") Integer Id, Model model) {
//		int iD = (int) model.getAttribute("LoginID");
//		ForumReplyBean mb = service.getMessageById(Id);
//		Integer forumId = mb.getForumBean().getForumId();
//		service.deleteMessageByPrimaryKey(Id);
//
//		return "redirect:/forum/" + forumId.toString();
//	}
	// 編輯留言表單
	@GetMapping("/editMessage/{replyId}")
	public String editMessage(@PathVariable("replyId") Integer Id, Model model) {
		int iD = (int) model.getAttribute("LoginID");
		model.addAttribute("mb", service.getMessageById(Id));
		return "Forum/UpdateReply";
	}

	// 編輯留言表單送出
	@PostMapping("/saveReply/{replyId}")
	public String updateMessage(@ModelAttribute("mb") ForumReplyBean forumReplyBean, BindingResult result, Model model,
			@PathVariable("replyId") Integer replyId) {
		int iD = (int) model.getAttribute("LoginID");
		Integer forumId = forumReplyBean.getForumBean().getForumId();
		ForumReplyValidator validator = new ForumReplyValidator();
		validator.validate(forumReplyBean, result);
		forumReplyBean.setForumBean(service.getForumById(forumId));
		if (result.hasErrors()) {
			return "redirect:/forum/" + forumId.toString();
		}
		UserBean userBean = personalService.getUserInfo(iD);
		forumReplyBean.setUser(userBean);
		ForumBean forumBean = service.getForumById(forumId);
		forumReplyBean.setForumBean(forumBean);
		service.updateMessage(forumReplyBean);
		return "redirect:/forum/" + forumId.toString();
	}

	/*
	 * =============================================================================
	 * ====
	 */


	// 計算點擊次數
	@GetMapping("/Checkcount/{forumId}")
	public String Checkcount(@PathVariable("forumId") Integer forumId, Model model) {
//		int iD = (int) model.getAttribute("LoginID");
		ForumBean forumBean = service.getForumById(forumId);
		forumBean.setCheckCount(forumBean.getCheckCount() + 1);
		service.updateForum(forumBean);
		return "redirect:/forum/" + forumId.toString();
	}

	// 儲存點擊次數
	@PostMapping("/forumStoreAjax")
	@ResponseBody
	public String StoreArticleAjax(@RequestParam("ajaxArticleId") Integer ajaxArticleId, Model model) {
		int iD = (int) model.getAttribute("LoginID");

		ForumBean forumBean = service.getForumById(ajaxArticleId);
		if (forumJudgeService.ForumJudgeExists(iD, ajaxArticleId)) {
			ForumJudge forumJudge = forumJudgeService.GetForumJudgeResaultByUserIdAndForumId(iD, ajaxArticleId);
			if (forumJudge.getStoreResult() != 1) {
				forumJudge.setStoreResult(1);
			} else {
				forumJudge.setStoreResult(0);
			}
			forumJudgeService.updateForumJudge(forumJudge);

			} else {
				ForumJudge forumJudge = new ForumJudge();
				forumJudge.setTextGood(0);
				forumJudge.setTextBad(0);
				forumJudge.setJudgeResult(0);
				forumJudge.setReportResult(0);
				forumJudge.setStoreResult(1);
				forumJudge.setForumBean(forumBean);
				forumJudge.setJudgeForumUserId(iD);
				forumJudgeService.InsertNewForumJudge(forumJudge);
			}

			return "OK";
		}
	

	/*
	 * =============================================================================
	 * ====
	 */
	
	// 圖片

	@GetMapping("/forumPicture/{forumId}")
	public ResponseEntity<byte[]> getPicture(@PathVariable("forumId") Integer forumId) {
		byte[] body = null;
		ResponseEntity<byte[]> re = null;
		MediaType mediaType = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());

		ForumBean forumBean = service.getForumById(forumId);
		if (forumBean == null) {
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
		String filename = forumBean.getImagePath();
		if (filename != null) {
			if (filename.toLowerCase().endsWith("jfif")) {
				mediaType = MediaType.valueOf(ctx.getMimeType("dummy.jpeg"));
			} else {
				mediaType = MediaType.valueOf(ctx.getMimeType(filename));
				headers.setContentType(mediaType);
			}
		}
		Blob blob = forumBean.getForumImage();
		body = blobToByteArray(blob);
		re = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);

		return re;
	}

//	private byte[] fileToByteArray(String path) {
//		byte[] result = null;
//		try (InputStream is = ctx.getResourceAsStream(path);
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
//			byte[] b = new byte[819200];
//			int len = 0;
//			while ((len = is.read(b)) != -1) {
//				baos.write(b, 0, len);
//			}
//			result = baos.toByteArray();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}

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
}

//	@ModelAttribute
//	public ForumBean editforumBean(@RequestParam(value="forumid", required = false) Integer id) {
//		ForumBean cbean = new ForumBean();
//		if (id != null) {
//			cbean = service.getForumById(id).get();
//			System.out.println("在@ModelAttribute修飾的方法 getCustomerBean()中，讀到物件:" + cbean);
//		} else {
//			System.out.println("在@ModelAttribute修飾的方法 getCustomerBean()中，無法讀取物件:" + cbean);
//		}
//		return cbean;
//	}
