package com.example.demo.controller;

import java.io.File;
import java.sql.Blob;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.bean.user.Vendor_InformationBean;
import com.example.demo.model.service.user.VendorService;
import com.example.demo.reCaptch.ReCaptchaValidationService;
import com.example.demo.util.RSAUtils;
import com.example.demo.validators.UpdateVendorValidator;
import com.example.demo.validators.VendorBeanValidator;



@Controller
@SessionAttributes({ "Vendor", "Person","LoginID" })
public class VendorController {
	
	@Autowired
	VendorService vendorService;
	

	@Autowired
	ServletContext context;
	
	
//	<!-- 									新增的07042057 -->
	@Autowired
	ReCaptchaValidationService reCaptchaValidationService;
//	<!-- 									新增的07042057 -->
	
	
	
	
	
	
	
	
	
	
	
	
	
	//------------------------------------------ 更新會員個人資訊

			// 功能：當使用者按下『修改之超連結』準備修改會員資料時，本方法送回含有會員資料的表單讓使用者修改。

			@GetMapping("/member/updateVendorInfo")
			public String showDataForm(Model model) {
				int iD = (int)model.getAttribute("LoginID");
				 Vendor_InformationBean vendor = vendorService.getVendorInfo(iD);
				model.addAttribute("Vendor", vendor);
				System.out.println("更新getmammping");
				return "user/member/updateVendorInfo";
			}
		
		
		
			// ----------------------------------------------更新會員個人資訊
			@PostMapping("/member/updateVendorInfo")
				public String modify(@ModelAttribute("Vendor") Vendor_InformationBean vendorBean, BindingResult result,
						Model model) {
				System.out.println("更新postmammping");
				UpdateVendorValidator validator = new UpdateVendorValidator();
					validator.validate(vendorBean, result);
					if (result.hasErrors()) {
						// 檢核時發現錯誤
						List<ObjectError> list = result.getAllErrors();
						for (ObjectError error : list) {
							System.out.println("有錯誤：" + error);
						}
						return "user/member/updateVendorInfo";
					}

				// 處理上傳的圖片
				MultipartFile picture = vendorBean.getUserImage();
				System.out.println("更新postmammping2");

				if (picture.getSize() == 0) {
					// 表示使用者並未挑選圖片
					 Vendor_InformationBean original = vendorService.getVendorInfo(vendorBean.getUserId());
					 vendorBean.setHeadShot(original.getHeadShot());
				} else {
//						String originalFilename = picture.getOriginalFilename();
//						if (originalFilename.length() > 0 && originalFilename.lastIndexOf(".") > -1) {
//							personBean.setFileName(originalFilename);
//						}

					// 建立Blob物件
					if (picture != null && !picture.isEmpty()) {
						try {
							byte[] b = picture.getBytes();
							Blob blob = new SerialBlob(b);
							vendorBean.setHeadShot(blob);
						} catch (Exception e) {
							e.printStackTrace();
							throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
						}
					}
				}
				vendorService.updateVendorInfo(vendorBean);
				vendorBean = vendorService.getVendorInfo(vendorBean.getUserId())	;

				// 更新session 將個人資料personBean物件放入Session範圍內，識別字串為"person"
				model.addAttribute("Vendor", vendorBean);
				return "redirect:/member/memberIndex";
			}
		
		
	
	
	
	
	
	
	
	
	
	// 註冊會員帳號
		@GetMapping("/registerVendor")
		public String showEmptyAccountForm(Model model) {
			Vendor_InformationBean vendorBean = new Vendor_InformationBean();
			
			model.addAttribute("vendor", vendorBean);
			
			return "user/member/addVendor";
		}
		
		
		
//		==================================================修改存照片路徑過
		// 註冊會員帳號
//		 <!-- 									新增的recaptcha07042057 -->	
		@PostMapping("/registerVendor")
		public String addUser(@ModelAttribute("vendor") Vendor_InformationBean vendorBean,
				BindingResult result,@RequestParam(name="g-recaptcha-response")String captcha)
						throws Exception {
			
			
			
			 if(!reCaptchaValidationService.validateCaptcha(captcha))
		        {    
				 result.rejectValue("invalidCredentials", "", "請記得點擊我不是機器人");
					return "user/member/addMember";
				 } 
			     
//			 <!-- 									新增的07042057 -->	

			//設定新註冊預設值
			vendorBean.setCategory("廠商");

			VendorBeanValidator validator = new VendorBeanValidator();
			validator.validate(vendorBean, result);
			if (result.hasErrors()) {
//	          下列敘述可以理解Spring MVC如何處理錯誤			
				List<ObjectError> list = result.getAllErrors();
				for (ObjectError error : list) {
					System.out.println("有錯誤：" + error);
				}
				return "user/member/addVendor";
			}

			// 檢查 帳號 是否重複
			if (vendorService.idExists(vendorBean.getAccount())) {
				result.rejectValue("account", "", "帳號已存在，請重新輸入");
				return "user/member/addVendor";
			}
			
			
			// RSA加密

			

				Map<String, String> map = RSAUtils.encryptPassword(vendorBean.getPassword());

				String encryptByPublicKey = map.get("encryptByPublicKey");

				String privateKey = map.get("privateKey");

				vendorBean.setPassword(encryptByPublicKey);

				vendorBean.setPrivateKey(privateKey);
			

			MultipartFile picture = vendorBean.getUserImage();
			String originalFilename = picture.getOriginalFilename();
//				if (originalFilename.length() > 0 && originalFilename.lastIndexOf(".") > -1) {
			vendorBean.setP_Path(originalFilename);
//			}

			// 建立Blob物件，交由 Hibernate 寫入資料庫
			if (picture != null && !picture.isEmpty()) {
				try {
					byte[] b = picture.getBytes();
					Blob blob = new SerialBlob(b);
					vendorBean.setHeadShot(blob);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
				}
			}
			
			if (!picture.isEmpty()) {
				String fileName =vendorBean.getAccount()+"_"+picture.getOriginalFilename();
				// 將圖片即相對路徑保存至資料庫
				vendorBean.setP_Path("/temp"+File.separator + fileName);
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

			vendorService.addVendorInfo(vendorBean);

			return "redirect:/index";
		}

	
	
//		==================================================修改存照片路徑過
	
	
	
	
//	
//	// 功能：顯示所有廠商資料
//		@GetMapping("/showAllVendors")
//		public String list(Model model) {
//			List<Vendor_InformationBean> vendorBean = vendorService.findAllVendorInfo();
//			
//			model.addAttribute("vendors", vendorBean);
//			return "backend/showAllVendors";
//		}
		


}
