package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.bean.troubles.Quotation;
import com.example.demo.model.bean.troubles.TroubleshootingBean;
import com.example.demo.model.bean.user.Personal_InformationBean;
import com.example.demo.model.bean.user.Vendor_InformationBean;
import com.example.demo.model.service.troubles.TroubleshootingService;
import com.example.demo.model.service.user.PersonalService;
import com.example.demo.model.service.user.VendorService;

@Controller
@SessionAttributes({ "Vendor", "Person", "LoginOK", "LoginID" })
public class TroubleController {
	@Autowired
	TroubleshootingService troubleshootingService;
	@Autowired
	ServletContext context;
	@Autowired
	PersonalService personalService;
	@Autowired
	VendorService vendorService;

	// 新增訂單
	@GetMapping("/addTrouble")
	public String addTrouble(Model model) {
//		int iD = (int) model.getAttribute("LoginID");
//		System.out.println(iD);
		if(model.getAttribute("LoginID") != null) {
			TroubleshootingBean troubleshootingBean = new TroubleshootingBean();
			model.addAttribute("tb", troubleshootingBean);
			return "troubles/addTrouble";
			
		}
		System.out.println(model.getAttribute("LoginID"));
		return "troubles/noLogin";
	}
	// 新增訂單
	@PostMapping("/addTrouble")
	public String addTrouble1(@ModelAttribute("tb") TroubleshootingBean troubleshootingBean, Model model) {
		System.out.println("========================");
		int iD = (int) model.getAttribute("LoginID");
		Personal_InformationBean userBean = personalService.getPersonInfoById(iD);
		troubleshootingBean.setUser(userBean);
		MultipartFile picture = troubleshootingBean.getImage();
		String originalFilename = picture.getOriginalFilename();
//		if (originalFilename.length() > 0 && originalFilename.lastIndexOf(".") > -1) {
		troubleshootingBean.setImagePath(originalFilename);
//	}
		troubleshootingBean.setStatus("待處理");
		// 建立Blob物件，交由 Hibernate 寫入資料庫
		if (picture != null && !picture.isEmpty()) {
			try {
				byte[] b = picture.getBytes();
				Blob blob = new SerialBlob(b);
				troubleshootingBean.setTsimage(blob);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
			}
		} else {

		}
		System.out.println("沒照片3");
		// 得到當前時間
//	Timestamp adminTime = new Timestamp(System.currentTimeMillis());
//	personBean.setJoinDate(adminTime);

		// 存入資料庫

		troubleshootingService.addTrouble(troubleshootingBean);
//	personalService.addPresonal_Information(personBean, userBean);
//	memberService.addPresonal_Information(personBean);

		String ext = "";
		if (originalFilename.lastIndexOf(".") > -1) {
			ext = originalFilename.substring(originalFilename.lastIndexOf("."));// 取副檔名
		}

		String rootDirectory = "D:\\Programming\\temp";// 找到應用系統的根目錄
		// 將上傳的檔案移到指定的資料夾
		try {
			File imageFolder = new File(rootDirectory, "images");
			if (!imageFolder.exists())
				imageFolder.mkdirs();
			File file = new File(imageFolder, troubleshootingBean.getBusinessOrderId() + ext);
			picture.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
		}
		System.out.println("沒照片5");

		troubleshootingService.addTrouble(troubleshootingBean);
		model.addAttribute("tb", troubleshootingService.findAllTrouble());
		return "troubles/troubleindex";
	}
	// 文章內容圖片
	@GetMapping("/tsimage/{businessOrderId}")
	public ResponseEntity<byte[]> getPicture(@PathVariable("businessOrderId") Integer businessOrderId) {
		byte[] body = null;
		ResponseEntity<byte[]> re = null;
		MediaType mediaType = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());

		TroubleshootingBean order = troubleshootingService.findById(businessOrderId);
		if (order == null) {
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
		String filename = order.getImagePath();
//		if (filename != null) {
//			if (filename.toLowerCase().endsWith("jfif")) {
//				mediaType = MediaType.valueOf(context.getMimeType("dummy.jpeg"));
//			} else {
//				mediaType = MediaType.valueOf(context.getMimeType(filename));
//				headers.setContentType(mediaType);
//			}
//		}
		Blob blob = order.getTsimage();
		body = blobToByteArray(blob);
		re = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);

		return re;
	}

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
// 訂單列表
	@GetMapping("/troubleindex")
	public String list(Model model) {
		model.addAttribute("tb", troubleshootingService.findAllTrouble());
		return "troubles/troubleindex";
	}
// 單一訂單
	@GetMapping("/OrderInner/{businessOrderId}")
	public String getTroubleshootingByIdAndReply(@PathVariable("businessOrderId") Integer businessOrderId,
			Model model) {
		List<Quotation> addquotation = troubleshootingService.findAllQuaotation(businessOrderId);
//		int iD = (int) model.getAttribute("LoginID");
//		UserBean userBean = personalService.getUserInfo(iD);
//		commentReply.setUser(userBean);
		TroubleshootingBean troubleshooting = troubleshootingService.findById(businessOrderId);
		Personal_InformationBean person = personalService.getPersonInfoById(troubleshooting.getUser().getUserId());
		for (Quotation q : addquotation) {
			List<Quotation> pingjunrating = troubleshootingService.getPingJunRating("2", q.getVendorId().getUserId());
	        System.out.println("拿平均後");
	        System.out.println("aaaaaaaaaaaaaaaaaaaaaa"+pingjunrating.size());
	        System.out.println("隨便");
	        Float count = 0f;
	        for (Quotation q1 : pingjunrating) {
				count+= q1.getBusinessOrderId().getRating();
				System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+q1.getBusinessOrderId().getRating());
			}
	        count/=pingjunrating.size();
	        System.out.printf("%.1f countcountcountcountcountcountcountcount",count);
	        double a = Math.round(count * 10.0) / 10.0;
	        System.out.println(a);
	        q.setAveragerating(a);
		}
		
		
		
		model.addAttribute("person", person);
		model.addAttribute("troubleshooting", troubleshooting);
		model.addAttribute("allquotation", troubleshootingService.findAllQuaotation(businessOrderId));
		model.addAttribute("addquotation", addquotation);
		
		
		
		return "troubles/trouble";
	}
	// 單一訂單
	@PostMapping(value = "/OrderInner/{businessOrderId}")
	public String addNewReply(@ModelAttribute("addquotation") Quotation addquotation,
			@PathVariable("businessOrderId") Integer businessOrderId, BindingResult result, Model model,
			HttpServletRequest request) {
		int iD = (int) model.getAttribute("LoginID");
		Vendor_InformationBean vib = vendorService.getVendorInfo(iD);
		TroubleshootingBean troubleshooting = troubleshootingService.findById(businessOrderId);
		
		addquotation.setBusinessOrderId(troubleshooting);
//		commentReply.setTextGood(0);
//		commentReply.setTextBad(0);
//		commentReply.setTextType(1);
		addquotation.setVendorId(vib);
		addquotation.setSelected("0");
		troubleshootingService.addquotation(addquotation);
//		Personal_InformationBean person = personalService.getPersonInfoById(commentArticle.getUser().getUserId());
//		Personal_InformationBean personR = personalService.getPersonInfoById(commentReply.getUser().getUserId());
//		model.addAttribute("personR", personR);
//		model.addAttribute("commentArticleOne", commentArticle);
//		model.addAttribute("allCommentReplys", commentReplyService.findAllCommentReplys(articleId));
//		model.addAttribute("commentReply", commentReply);
		System.out.println("成功");
		return "redirect:/OrderInner/" + businessOrderId.toString();
	}
	//	更新狀態 ex:0-->1 & 接單日期
	@GetMapping("/status/{businessOrderId}/{businessQuteId}")
    public String ChangeJudgeReply(@PathVariable("businessOrderId") Integer businessOrderId,
            @PathVariable("businessQuteId") Integer businessQuteId, Model model) {
        int iD = (int) model.getAttribute("LoginID");

    
        TroubleshootingBean troubleshooting = troubleshootingService.findById(businessOrderId);
        troubleshooting.setStatus("廠商接單處理中");
        Date orderTakingDate = new Date();
        troubleshooting.setOrderTakingDate(orderTakingDate);
        troubleshootingService.addTrouble(troubleshooting);
       
        Quotation quotation = troubleshootingService.findQuotationById(businessQuteId);
        quotation.setSelected("1");
        troubleshootingService.addquotation(quotation);

        return "redirect:/OrderInner/" + businessOrderId.toString();
    }
	// 評價
	@GetMapping("/comments/{businessOrderId}/{businessQuteId}")
    public String rating(@PathVariable("businessOrderId") Integer businessOrderId, @PathVariable Integer businessQuteId ,Model model) {
        int iD = (int) model.getAttribute("LoginID");
        TroubleshootingBean troubleshooting = troubleshootingService.findById(businessOrderId);
//        Personal_InformationBean person = personalService.getPersonInfoById(commentArticle.getUser().getUserId());
//        model.addAttribute("person", person);
        
        
        System.out.println("GEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEET");
        System.out.println(troubleshooting.getOrderTakingDate());
        Quotation quotation = troubleshootingService.findQuotationById(businessQuteId);
        model.addAttribute("troubleshooting", troubleshooting);
        model.addAttribute("quotation", quotation);
        return "troubles/comment";

    }
//  更新狀態 ex:1-->2 & 完成日期
    @PostMapping("/comments/{businessOrderId}/{businessQuteId}")
    public String rating(@ModelAttribute("troubleshooting") TroubleshootingBean troubleshooting,
             Model model, @PathVariable Integer businessOrderId, @PathVariable Integer businessQuteId) {
    	System.out.println("POOOOOOOOOOOOOST");
        TroubleshootingBean orginal =  troubleshootingService.findById(businessOrderId);
        System.out.println(orginal);
        Quotation quotation = troubleshootingService.findQuotationById(businessQuteId);
        quotation.setSelected("2");
        orginal.setStatus("已完成");
        orginal.setRating(troubleshooting.getRating());
        orginal.setComment(troubleshooting.getComment());
//        Personal_InformationBean userBean = personalService.getPersonInfoById(troubleshooting.getUser().getUserId());
//        troubleshooting.setUser(userBean);
        Date finishedDate = new Date();
        orginal.setFinishedDate(finishedDate);
        troubleshootingService.addTrouble(orginal);
        troubleshootingService.addquotation(quotation);
        System.out.println("拿平均前");
        List<Quotation> pingjunrating = troubleshootingService.getPingJunRating("2", quotation.getVendorId().getUserId());
        System.out.println("拿平均後");
        System.out.println("aaaaaaaaaaaaaaaaaaaaaa"+pingjunrating.size());
        System.out.println("隨便");
        Float count = 0f;
        for (Quotation q : pingjunrating) {
			count+= q.getBusinessOrderId().getRating();
			System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+q.getBusinessOrderId().getRating());
		}
        count/=pingjunrating.size();
        System.out.printf("%.1f countcountcountcountcountcountcountcount",count);
        double a = Math.round(count * 10.0) / 10.0;
        System.out.println(a);
        model.addAttribute("pingjunrating",a);
        
//        return "redirect:/OrderInner/" + businessOrderId.toString();
        return "redirect:/OrderInner/" + businessOrderId.toString();
}
 // 編輯
 	@GetMapping(value = "/UpdateTrouble/{businessOrderId}")
 	public String enterUpdateTrouble(@PathVariable("businessOrderId") Integer businessOrderId, Model model) {
 		int iD = (int) model.getAttribute("LoginID");
 		TroubleshootingBean troubleshooting = troubleshootingService.findById(businessOrderId);
 		Personal_InformationBean person = personalService.getPersonInfoById(troubleshooting.getUser().getUserId());
 		model.addAttribute("person", person);
 		model.addAttribute("troubleshootingOne", troubleshooting);
 		return "troubles/updateTrouble";

 	}

 	@PostMapping("/UpdateTrouble/{businessOrderId}")
 	public String modifyUpdateTrouble(@ModelAttribute("troubleshootingOne") TroubleshootingBean troubleshootingBean,
 			BindingResult result, Model model, @PathVariable Integer businessOrderId) {

 		int iD = (int) model.getAttribute("LoginID");
 		MultipartFile picture = troubleshootingBean.getImage();
 		if (picture.getSize() == 0) {
 			TroubleshootingBean original = troubleshootingService.findById(businessOrderId);
 			troubleshootingBean.setTsimage(original.getTsimage());
 		} else {
 			if (picture != null && !picture.isEmpty()) {
 				try {
 					byte[] b = picture.getBytes();
 					Blob blob = new SerialBlob(b);
 					troubleshootingBean.setTsimage(blob);
 				} catch (Exception e) {
 					e.printStackTrace();
 					throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
 				}
 			}
 		}
 		 TroubleshootingBean orginal =  troubleshootingService.findById(troubleshootingBean.getBusinessOrderId());
 		orginal.setTitle(troubleshootingBean.getTitle());
 		orginal.setTroubleType(troubleshootingBean.getTroubleType());
 		orginal.setAddress(troubleshootingBean.getAddress());
 		orginal.setTextArea(troubleshootingBean.getTextArea());
 		orginal.setTsimage(troubleshootingBean.getTsimage());
 		orginal.setRemark(troubleshootingBean.getRemark());
 		troubleshootingService.updateTrouble(orginal);
 		Personal_InformationBean person = personalService.getPersonInfoById(troubleshootingBean.getUser().getUserId());
 		model.addAttribute("person", person);
 		Quotation quotation = new Quotation();
 		model.addAttribute("troubleshootingOne", troubleshootingService.findById(businessOrderId));
 		model.addAttribute("quotation", quotation);
 		model.addAttribute("allquotation", troubleshootingService.findAllQuaotation(businessOrderId));
 		return "redirect:/OrderInner/" + businessOrderId.toString();
 	}

 	// 刪除文章
 	@GetMapping("/DeleteTrouble/{id}")
 	public String delete(@PathVariable("id") Integer businessOrderId) {
// 		commentReplyService.deleteAllCommentReplys(article_Id);
 		troubleshootingService.deleteTroubleById(businessOrderId);
 		return "redirect:/troubleindex";
 	}
    		
    	
}