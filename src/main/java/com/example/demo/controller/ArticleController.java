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
import com.example.demo.model.bean.ViolationRef;
import com.example.demo.model.bean.ViolationRule;
import com.example.demo.model.bean.comment.CommentArticle;
import com.example.demo.model.bean.comment.CommentArticleJudge;
import com.example.demo.model.bean.comment.CommentArticlePlusJudge;
import com.example.demo.model.bean.comment.CommentArticleTemp;
import com.example.demo.model.bean.comment.CommentReply;
import com.example.demo.model.bean.comment.CommentReplyJudge;
import com.example.demo.model.bean.user.Personal_InformationBean;
import com.example.demo.model.bean.user.UserBean;
import com.example.demo.model.bean.user.Vendor_InformationBean;
import com.example.demo.model.service.BlacklistService;
import com.example.demo.model.service.PointRuleService;
import com.example.demo.model.service.PointsEventService;
import com.example.demo.model.service.ViolationEventService;
import com.example.demo.model.service.ViolationRefService;
import com.example.demo.model.service.ViolationRuleService;
import com.example.demo.model.service.comment.CommentArticleJudgeService;
import com.example.demo.model.service.comment.CommentArticlePlusJudgeService;
import com.example.demo.model.service.comment.CommentArticleService;
import com.example.demo.model.service.comment.CommentArticleTempService;
import com.example.demo.model.service.comment.CommentReplyJudgeService;
import com.example.demo.model.service.comment.CommentReplyPlusJudgeService;
import com.example.demo.model.service.comment.CommentReplyService;
import com.example.demo.model.service.user.PersonalService;
import com.example.demo.model.service.user.VendorService;
import com.example.demo.validators.comment.PostArticleValidator;
import com.example.demo.validators.comment.PostReplyValidator;



@Controller
@SessionAttributes({ "Vendor", "Person", "LoginID", "Category" })
public class ArticleController {
	@Autowired
	CommentArticleService commentArticleService;
	@Autowired
	CommentArticleTempService commentArticleTempService;
	@Autowired
	CommentReplyPlusJudgeService commentReplyPlusJudgeService;
	@Autowired
	CommentArticlePlusJudgeService commentArticlePlusJudgeService;
	@Autowired
	CommentReplyJudgeService commentReplyJudgeService;
	@Autowired
	CommentArticleJudgeService commentArticleJudgeService;
	@Autowired
	CommentReplyService commentReplyService;
	@Autowired
	ViolationRuleService violationRuleService;
	@Autowired
	ViolationEventService violationEventService;
	@Autowired
	ViolationRefService violationRefService;
	@Autowired
	PointsEventService pointsEventService;
	@Autowired
	PointRuleService pointRuleService;
	@Autowired
	BlacklistService blacklistService;
	@Autowired
	ServletContext context;

	@Autowired
	PersonalService personalService;

	@Autowired
	VendorService vendorService;

//拿全部文章
	@GetMapping("/CommentArticles")
	public String Articlelist(Model model) {
//		int iD = (int) model.getAttribute("LoginID");
		model.addAttribute("commentArticles", commentArticleService.findAllCommentArticles());
		return "Comment/ShowAllCommentArticles";
	}

	// 全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試
	@PostMapping(value = "/CommentArticleschange")
	@ResponseBody
	public Integer Articleschange(@RequestParam(value = "taipeiArea") String taipeiArea) {
		commentArticleTempService.deleteAllTempCommentArticles();
		if (taipeiArea.equals("全部區域")) {
			commentArticleTempService.GetIntoTempArticle();
		} else {
			commentArticleTempService.GetIntoTempArticleByTaipeiArea(taipeiArea);
		}
		return 1;

	}

	@PostMapping(value = "/CommentArticleschangeOnlyPageNo")
	@ResponseBody
	public Page<CommentArticleTemp> ArticleschangeOnlyPageNo(@RequestParam(value = "pageNo") Integer pageNo) {
		Page<CommentArticleTemp> commentArticleTemp = commentArticleTempService.PageForCommentArticleTemp(pageNo, 4);
		return commentArticleTemp;
	}

	// 全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試全文章ajax測試
	// 發佈文章
	@GetMapping(value = "/PostNewArticle")
	public String PostComment(Model model) {
		int iD = (int) model.getAttribute("LoginID");
		CommentArticle commentArticle = new CommentArticle();
		model.addAttribute("commentArticle", commentArticle);
		return "Comment/PostCommentArticle";
	}

	@PostMapping(value = "/PostNewArticle")
	public String addComment(@ModelAttribute("commentArticle") CommentArticle commentArticle, BindingResult result,
			Model model, HttpServletRequest request) throws IllegalStateException, IOException {

		String category = (String) model.getAttribute("Category");
		int iD = (int) model.getAttribute("LoginID");
		UserBean userBean = personalService.getUserInfo(iD);
		if (userBean.getCategory().equals("會員")) {
			Personal_InformationBean pib = personalService.getPersonInfoById(iD);
			commentArticle.setUserName(pib.getName());

		} else {
			Vendor_InformationBean vib = vendorService.getVendorInfo(iD);
			commentArticle.setUserName(vib.getName());

		}
		commentArticle.setUser(userBean);

		PostArticleValidator validator = new PostArticleValidator();
		validator.validate(commentArticle, result);
		if (result.hasErrors()) {
			result.rejectValue("image", "", "請選擇照片");
			return "Comment/PostCommentArticle";
		}

		MultipartFile picture = commentArticle.getImage();
		String originalFilename = picture.getOriginalFilename();
		if (originalFilename.length() > 0 && originalFilename.lastIndexOf(".") > -1) {
			commentArticle.setImagePath(originalFilename);
		}
		if (picture != null && !picture.isEmpty()) {
			try {
				byte[] b = picture.getBytes();
				Blob blob = new SerialBlob(b);
				commentArticle.setArticleImage(blob);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
			}
		}
		if (!picture.isEmpty()) {
			String fileName = picture.getOriginalFilename();
			// 將圖片即相對路徑保存至資料庫
			commentArticle.setImagePath("/temp"+File.separator + fileName);
			// 同時下載到本機存放
			// 1.指定資料夾路徑
			String path = "D:\\Programming\\temp"+ File.separator + fileName;
			System.out.println(path);
			// 2.確定該系統路徑下是否有指定的資料夾
			File file = new File("D:\\Programming\\temp");
			if (!(file.exists() && file.isDirectory())) {
				// 沒有,則建立資料夾
				file.mkdir();
			}
			// 有則直接寫入
			File file1 = new File(path);
			picture.transferTo(file1);
			System.out.println("成功存入");
		}

		commentArticle.setTextGoodTotal(0);
		commentArticle.setTextBadTotal(0);
		commentArticle.setCheckCount(0);
		commentArticle.setTextType(1);
		commentArticleService.postNewArticle(commentArticle);
		CommentArticleJudge commentArticleJudge = new CommentArticleJudge();
		commentArticleJudge.setCommentArticle(commentArticleService.getArticleById(commentArticle.getArticleId()));
		commentArticleJudge.setJudgeArticleUserId(commentArticle.getUser().getUserId());
		commentArticleJudge.setTextGood(0);
		commentArticleJudge.setTextBad(0);
		commentArticleJudge.setJudgeResult(0);
		commentArticleJudge.setStoreResult(0);
		commentArticleJudge.setReportResult(0);
		// 0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
		commentArticleJudge.setBlackResult(0);
		commentArticleJudge.setCheckResult(0);
		// 0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
		commentArticleJudgeService.InsertNewArticleJudge(commentArticleJudge);
		PointsEvent pointsEvent = new PointsEvent();
		PointRule pointRule = pointRuleService.queryPointsRuleById(1);
		pointsEvent.setPointRule(pointRule);
		pointsEvent.setUser(userBean);
		pointsEvent.setDetails(commentArticle.getTitle());
		pointsEvent.setTimes(1);
		pointsEventService.postNewPointsEvent(pointsEvent);
		personalService.recalculatePoint(iD);
		return "redirect:/CommentArticles";
	}

	// 更新文章更新文章更新文章更新文章更新文章更新文章更新文章更新文章更新文章更新文章更新文章更新文章更新文章更新文章更新文章
	@GetMapping(value = "/UpdateArticle/{articleId}")
	public String enterUpdateCommentArticle(@PathVariable("articleId") Integer articleId, Model model) {
		int iD = (int) model.getAttribute("LoginID");
		CommentArticle commentArticle = commentArticleService.getArticleById(articleId);
		model.addAttribute("commentArticleOne", commentArticle);
		return "Comment/UpdateCommentArticle";

	}

	@PostMapping("/UpdateArticle/{articleId}")
	public String modifyUpdateCommentArticle(@ModelAttribute("commentArticleOne") CommentArticle commentArticle,
			BindingResult result, Model model, @PathVariable Integer articleId)
			throws IllegalStateException, IOException {

		int iD = (int) model.getAttribute("LoginID");

		PostArticleValidator validator = new PostArticleValidator();
		validator.validate(commentArticle, result);
		if (result.hasErrors()) {
			return "Comment/UpdateCommentArticle";
		}
		MultipartFile picture = commentArticle.getImage();
		if (picture.getSize() == 0) {
			CommentArticle original = commentArticleService.getArticleById(articleId);
			commentArticle.setArticleImage(original.getArticleImage());
		} else {
			if (picture != null && !picture.isEmpty()) {
				try {
					byte[] b = picture.getBytes();
					Blob blob = new SerialBlob(b);
					commentArticle.setArticleImage(blob);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
				}
			}
		}
		// 0707070707070707070707070707070707070707070707070707070707070707070707070707
		if (!picture.isEmpty()) {
			String fileName = picture.getOriginalFilename();
			// 將圖片即相對路徑保存至資料庫
			commentArticle.setImagePath("/temp"+File.separator + fileName);
			// 同時下載到本機存放
			// 1.指定資料夾路徑
			String path ="D:\\Programming\\temp"+File.separator + fileName;
			System.out.println(path);
			// 2.確定該系統路徑下是否有指定的資料夾
			File file = new File("D:\\Programming\\temp");
			if (!(file.exists() && file.isDirectory())) {
				// 沒有,則建立資料夾
				file.mkdir();
			}
			// 有則直接寫入
			File file1 = new File(path);
			picture.transferTo(file1);
			System.out.println("成功存入");
		}
		// 0707070707070707070707070707070707070707070707070707070707070707070707070707
		UserBean userBean = personalService.getUserInfo(iD);
		commentArticle.setUser(userBean);
		commentArticleService.updateCommentArticle(commentArticle);
		Personal_InformationBean person = personalService.getPersonInfoById(commentArticle.getUser().getUserId());
		model.addAttribute("person", person);
		CommentReply commentReply = new CommentReply();
		model.addAttribute("commentArticleOne", commentArticleService.getArticleById(articleId));
		model.addAttribute("commentReply", commentReply);
		model.addAttribute("allCommentReplys", commentReplyService.findAllCommentReplys(articleId));

		return "redirect:/ArticleInner/" + articleId.toString();
	}

	// 刪除文章
	@GetMapping("/DeleteArticle/{id}")
	public String delete(@PathVariable("id") Integer article_Id, Model model) {
//		commentReplyService.deleteAllCommentReplys(article_Id);
		int iD = (int) model.getAttribute("LoginID");
		pointsEventService
				.deletePointsEventById(pointsEventService
						.getPointsEventByUserIdAndActionId(
								commentArticleService.getArticleById(article_Id).getUser().getUserId(), 1)
						.getPointsEventId());
		commentArticleService.deleteCommentArticleById(article_Id);
		personalService.recalculatePoint(iD);
		return "redirect:/CommentArticles";
	}

	// 發布回覆
	@GetMapping("/ArticleInner/{articleId}")
	public String getCommentArticleByIdAndReply(@PathVariable("articleId") Integer articleId, Model model) {
if(model.getAttribute("LoginID")==null) {
	CommentArticle commentArticle = commentArticleService.getArticleById(articleId);
		commentArticle.setTextType(0);
		commentArticleService.updateCommentArticle(commentArticle);
	List<CommentReply> commentReplyList = commentReplyService.findAllCommentReplys(articleId);
	for (CommentReply c : commentReplyList) {
			c.setTextType(0);
			commentReplyService.updateCommentReply(c);
	}
	CommentReply commentReply = new CommentReply();
	model.addAttribute("mostgood", commentArticleService.GetTextGoodTotalMost());
	model.addAttribute("mostcheck", commentArticleService.GetCheckCountMost());
	model.addAttribute("mostnew", commentArticleService.GetNewMost());
	model.addAttribute("commentArticleOne", commentArticleService.getArticleById(articleId));
	model.addAttribute("allCommentReplys", commentReplyService.findAllCommentReplys(articleId));
	model.addAttribute("commentReply", commentReply);
	return "/Comment/CommentArticleBlockNoLogin";
}else {
	

		int iD = (int) model.getAttribute("LoginID");
		UserBean userBean = personalService.getUserInfo(iD);
		CommentArticle commentArticle = commentArticleService.getArticleById(articleId);
		//07110711071107110711071107110711071107110711071107110711071107110711
        if (commentArticleJudgeService.ArticleJudgeExists(iD, articleId)) {
            CommentArticleJudge commentArticleJudge = commentArticleJudgeService
                    .GetArticleJudgeResaultByUserIdAndArticleId(iD, articleId);
            if (commentArticleJudge.getCheckResult() != 1) {
                commentArticleJudge.setCheckResult(1);
            } else {

                commentArticleJudge.setCheckResult(0);
                commentArticleJudgeService.updateCommentArticleJudge(commentArticleJudge);
                commentArticleJudge.setCheckResult(1);
            }
            commentArticleJudgeService.updateCommentArticleJudge(commentArticleJudge);

        } else {
            CommentArticleJudge commentArticleJudge = new CommentArticleJudge();
            commentArticleJudge.setTextGood(0);
            commentArticleJudge.setTextBad(0);
            commentArticleJudge.setJudgeResult(0);
            commentArticleJudge.setReportResult(0);
            commentArticleJudge.setStoreResult(0);
            commentArticleJudge.setBlackResult(0);
            commentArticleJudge.setCheckResult(1);
            commentArticleJudge.setCommentArticle(commentArticle);
            commentArticleJudge.setJudgeArticleUserId(iD);
            commentArticleJudgeService.InsertNewArticleJudge(commentArticleJudge);
        }
        //07110711071107110711071107110711071107110711071107110711071107110711
		if (blacklistService.BlacklistExists(iD, commentArticle.getUser().getUserId())) {
			commentArticle.setTextType(1);
			commentArticleService.updateCommentArticle(commentArticle);
		} else {
			commentArticle.setTextType(0);
			commentArticleService.updateCommentArticle(commentArticle);
		}

		List<CommentReply> commentReplyList = commentReplyService.findAllCommentReplys(articleId);
		for (CommentReply c : commentReplyList) {

			if (blacklistService.BlacklistExists(iD, c.getUser().getUserId())) {
				c.setTextType(1);
				commentReplyService.updateCommentReply(c);
			} else {
				c.setTextType(0);
				commentReplyService.updateCommentReply(c);
			}
		}
		CommentReply commentReply = new CommentReply();
		commentReply.setUser(userBean);
		commentReply.setCommentArticle(commentArticle);
		// 070707070707070707070707070707070707070707070707070707070707
		model.addAttribute("mostgood", commentArticleService.GetTextGoodTotalMost());
		model.addAttribute("mostcheck", commentArticleService.GetCheckCountMost());
		model.addAttribute("mostnew", commentArticleService.GetNewMost());
		// 070707070707070707070707070707070707070707070707070707070707
		model.addAttribute("commentArticleOne", commentArticlePlusJudgeService.GetArticleAndJudge(articleId, iD));
		model.addAttribute("allCommentReplys", commentReplyPlusJudgeService.GetAllReplysAndJudge(articleId, iD));
		model.addAttribute("commentReply", commentReply);

		return "Comment/CommentArticleBlock";
		}
	}

	// 點入文章區塊連結
	@PostMapping(value = "/ArticleInner/{articleId}")
	public String addNewReply(@ModelAttribute("commentReply") CommentReply commentReply,
			@PathVariable("articleId") Integer articleId, BindingResult result, Model model,
			HttpServletRequest request) {
		Integer iD = (Integer) model.getAttribute("LoginID");
		if(iD==null) {
			return "redirect:/login";
		}else {
		UserBean userBean = personalService.getUserInfo(iD);
		if (userBean.getCategory().equals("會員")) {
			Personal_InformationBean pib = personalService.getPersonInfoById(iD);
			commentReply.setUserName(pib.getName());
		} else {
			Vendor_InformationBean vib = vendorService.getVendorInfo(iD);
			commentReply.setUserName(vib.getName());
		}
		PostReplyValidator validator = new PostReplyValidator();
		validator.validate(commentReply, result);
		if (result.hasErrors()) {
			return "redirect:/ArticleInner/" + articleId.toString();
		}
		commentReply.setUser(userBean);
		CommentArticle commentArticle = commentArticleService.getArticleById(articleId);
		commentReply.setCommentArticle(commentArticle);
		commentReply.setTextGoodTotal(0);
		commentReply.setTextBadTotal(0);
		commentReply.setTextType(1);
		commentReply.setUser(userBean);
		commentReplyService.postNewReply(commentReply);
		// 存入一個至CommentReplyJudge
		CommentReplyJudge commentReplyJudge = new CommentReplyJudge();
		commentReplyJudge.setJudgeReplyArticleId(commentReply.getCommentArticle().getArticleId());
		commentReplyJudge.setCommentReply(commentReplyService.getReplyById(commentReply.getReplyId()));
		commentReplyJudge.setJudgeReplyUserId(commentReply.getUser().getUserId());
		commentReplyJudge.setTextGood(0);
		commentReplyJudge.setTextBad(0);
		commentReplyJudge.setReplyJudgeId(0);
		commentReplyJudge.setJudgeResult(0);
		commentReplyJudge.setReportResult(0);
		commentReplyJudgeService.InsertNewReplyJudge(commentReplyJudge);

		return "redirect:/ArticleInner/" + articleId.toString();
		}
	}

	// 刪除回覆
	@GetMapping("/DeleteReply/{articleId}/{replyId}")
	public String deleteReplyById(@PathVariable("replyId") Integer replyId,
			@PathVariable("articleId") Integer articleId, Model model) {
		int iD = (int) model.getAttribute("LoginID");
		commentReplyService.deleteCommentReplyById(replyId);

		return "redirect:/ArticleInner/" + articleId.toString();
	}

	// 更新回覆
	@GetMapping(value = "/UpdateReply/{articleId}/{replyId}")
	public String enterUpdateCommentReply(@PathVariable("replyId") Integer replyId,
			@PathVariable("articleId") Integer articleId, Model model) {
		int iD = (int) model.getAttribute("LoginID");
		CommentReply commentReply = commentReplyService.getReplyById(replyId);
		model.addAttribute("commentReply", commentReply);
		return "Comment/UpdateCommentReply";

	}

	@PostMapping("/UpdateReply/{articleId}/{replyId}")
	public String modifyUpdateCommentReply(@ModelAttribute("commentReply") CommentReply commentReply,
			BindingResult result, Model model, @PathVariable("replyId") Integer replyId,
			@PathVariable("articleId") Integer articleId) {
		int iD = (int) model.getAttribute("LoginID");
		PostReplyValidator validator = new PostReplyValidator();
		validator.validate(commentReply, result);
		if (result.hasErrors()) {
			return "Comment/UpdateCommentReply";
		}
		UserBean userBean = personalService.getUserInfo(iD);
		commentReply.setUser(userBean);
		CommentArticle commentArticle = commentArticleService.getArticleById(articleId);
		commentReply.setCommentArticle(commentArticle);
		commentReplyService.updateCommentReply(commentReply);

		// 存入一個至CommentReplyJudge
		model.addAttribute("commentArticleOne", commentArticleService.getArticleById(articleId));
		model.addAttribute("commentReply", commentReply);
		model.addAttribute("allCommentReplys", commentReplyService.findAllCommentReplys(articleId));
		return "redirect:/ArticleInner/" + articleId.toString();
	}

	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
//按讚ajax測試按讚ajax測試按讚ajax測試按讚ajax測試按讚ajax測試按讚ajax測試按讚ajax測試按讚ajax測試按讚ajax測試
	@PostMapping("/ArticleJudgeAjax")
	@ResponseBody
	public String JudgeArticle(@RequestParam(value = "ajaxArticleId") Integer ajaxArticleId,
			@RequestParam(value = "ajaxArticleJudgeResult") Integer ajaxArticleJudgeResult, Model model) {
		int iD = (int) model.getAttribute("LoginID");

		CommentArticle commentArticle = commentArticleService.getArticleById(ajaxArticleId);
		PointsEvent pointsEvent = new PointsEvent();
		UserBean userBean = personalService.getUserInfo(commentArticle.getUser().getUserId());
		PointRule pointRule = pointRuleService.queryPointsRuleById(2);
		pointsEvent.setPointRule(pointRule);
		pointsEvent.setUser(userBean);
		pointsEvent.setDetails(commentArticle.getTitle());
		pointsEvent.setTimes(1);

		if (commentArticleJudgeService.ArticleJudgeExists(iD, ajaxArticleId)) {
			CommentArticleJudge commentArticleJudge = commentArticleJudgeService
					.GetArticleJudgeResaultByUserIdAndArticleId(iD, ajaxArticleId);
			if (commentArticleJudge.getJudgeResult() == 0) {
				if (ajaxArticleJudgeResult == 1) {
					commentArticleJudge.setTextGood(1);
					commentArticleJudge.setTextBad(0);
					commentArticleJudge.setJudgeResult(1);
					commentArticleJudgeService.updateCommentArticleJudge(commentArticleJudge);
					pointsEventService.postNewPointsEvent(pointsEvent);
					personalService.recalculatePoint(commentArticle.getUser().getUserId());
				} else if (ajaxArticleJudgeResult == 2) {
					commentArticleJudge.setTextGood(0);
					commentArticleJudge.setTextBad(1);
					commentArticleJudge.setJudgeResult(2);
					commentArticleJudgeService.updateCommentArticleJudge(commentArticleJudge);

				}
			} else if (commentArticleJudge.getJudgeResult() == 1) {
				if (ajaxArticleJudgeResult == 1) {
					commentArticleJudge.setTextGood(0);
					commentArticleJudge.setTextBad(0);
					commentArticleJudge.setJudgeResult(0);
					commentArticleJudgeService.updateCommentArticleJudge(commentArticleJudge);

				} else if (ajaxArticleJudgeResult == 2) {
					commentArticleJudge.setTextGood(0);
					commentArticleJudge.setTextBad(1);
					commentArticleJudge.setJudgeResult(2);
					commentArticleJudgeService.updateCommentArticleJudge(commentArticleJudge);
				}
				pointsEventService.deletePointsEventById(pointsEventService
						.getPointsEventByUserIdAndActionId(userBean.getUserId(), 2).getPointsEventId());
				personalService.recalculatePoint(commentArticle.getUser().getUserId());
			} else if (commentArticleJudge.getJudgeResult() == 2) {
				if (ajaxArticleJudgeResult == 1) {
					commentArticleJudge.setTextGood(1);
					commentArticleJudge.setTextBad(0);
					commentArticleJudge.setJudgeResult(1);
					commentArticleJudgeService.updateCommentArticleJudge(commentArticleJudge);
					pointsEventService.postNewPointsEvent(pointsEvent);
					personalService.recalculatePoint(commentArticle.getUser().getUserId());
				} else if (ajaxArticleJudgeResult == 2) {
					commentArticleJudge.setTextGood(0);
					commentArticleJudge.setTextBad(0);
					commentArticleJudge.setJudgeResult(0);
					commentArticleJudgeService.updateCommentArticleJudge(commentArticleJudge);
				}
			}
		} else {
			CommentArticleJudge commentArticleJudge = new CommentArticleJudge();
			if (ajaxArticleJudgeResult == 1) {
				commentArticleJudge.setTextGood(1);
				commentArticleJudge.setTextBad(0);
				commentArticleJudge.setJudgeResult(1);
				pointsEventService.postNewPointsEvent(pointsEvent);
			} else if (ajaxArticleJudgeResult == 2) {
				commentArticleJudge.setTextGood(0);
				commentArticleJudge.setTextBad(1);
				commentArticleJudge.setJudgeResult(2);
			}
			commentArticleJudge.setCommentArticle(commentArticle);
			commentArticleJudge.setJudgeArticleUserId(iD);
			commentArticleJudge.setReportResult(0);
			commentArticleJudge.setStoreResult(0);
			// 0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
			commentArticleJudge.setBlackResult(0);
			commentArticleJudge.setCheckResult(0);
			// 0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
			// 0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
			commentArticleJudgeService.InsertNewArticleJudge(commentArticleJudge);
		}
		commentArticle.setTextGoodTotal(commentArticleJudgeService.GetArticleByTextGoodAndArticleId(1, ajaxArticleId));
		commentArticle.setTextBadTotal(commentArticleJudgeService.GetArticleByTextBadAndArticleId(1, ajaxArticleId));
		commentArticleService.updateCommentArticle(commentArticle);

		return "OK";
	}

	// 回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆回覆
	// 按讚按讚按讚按讚按讚按讚按讚按讚按讚按讚按讚按讚按讚按讚按讚按讚
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// 回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX
	// 回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX
	@PostMapping("/ReplyJudgeAjax")
	@ResponseBody
	public String ChangeJudgeReplyAjax(@RequestParam("ajaxReplyJudgeResult") Integer ajaxReplyJudgeResult,
			@RequestParam("ajaxReplyId") Integer ajaxReplyId, Model model) {
		int iD = (int) model.getAttribute("LoginID");

		CommentReply commentReply = commentReplyService.getReplyById(ajaxReplyId);
		if (commentReplyJudgeService.ReplyJudgeExists(iD, ajaxReplyId)) {
			CommentReplyJudge commentReplyJudge = commentReplyJudgeService.GetReplyJudgeResaultByUserIdAndReplyId(iD,
					ajaxReplyId);
			if (commentReplyJudge.getJudgeResult() == 0) {
				if (ajaxReplyJudgeResult == 1) {
					commentReplyJudge.setTextGood(1);
					commentReplyJudge.setTextBad(0);
					commentReplyJudge.setJudgeResult(1);
					commentReplyJudgeService.updateCommentReplyJudge(commentReplyJudge);
				} else if (ajaxReplyJudgeResult == 2) {
					commentReplyJudge.setTextGood(0);
					commentReplyJudge.setTextBad(1);
					commentReplyJudge.setJudgeResult(2);
					commentReplyJudgeService.updateCommentReplyJudge(commentReplyJudge);
				}
			} else if (commentReplyJudge.getJudgeResult() == 1) {
				if (ajaxReplyJudgeResult == 1) {
					commentReplyJudge.setTextGood(0);
					commentReplyJudge.setTextBad(0);
					commentReplyJudge.setJudgeResult(0);
					commentReplyJudgeService.updateCommentReplyJudge(commentReplyJudge);
				} else if (ajaxReplyJudgeResult == 2) {
					commentReplyJudge.setTextGood(0);
					commentReplyJudge.setTextBad(1);
					commentReplyJudge.setJudgeResult(2);
					commentReplyJudgeService.updateCommentReplyJudge(commentReplyJudge);
				}
			} else if (commentReplyJudge.getJudgeResult() == 2) {
				if (ajaxReplyJudgeResult == 1) {
					commentReplyJudge.setTextGood(1);
					commentReplyJudge.setTextBad(0);
					commentReplyJudge.setJudgeResult(1);
					commentReplyJudgeService.updateCommentReplyJudge(commentReplyJudge);
				} else if (ajaxReplyJudgeResult == 2) {
					commentReplyJudge.setTextGood(0);
					commentReplyJudge.setTextBad(0);
					commentReplyJudge.setJudgeResult(0);
					commentReplyJudgeService.updateCommentReplyJudge(commentReplyJudge);
				}
			}
		} else {
			CommentReplyJudge commentReplyJudge = new CommentReplyJudge();
			if (ajaxReplyJudgeResult == 1) {
				commentReplyJudge.setTextGood(1);
				commentReplyJudge.setTextBad(0);
				commentReplyJudge.setJudgeResult(1);
			} else if (ajaxReplyJudgeResult == 2) {
				commentReplyJudge.setTextGood(0);
				commentReplyJudge.setTextBad(1);
				commentReplyJudge.setJudgeResult(2);
			}
			commentReplyJudge.setCommentReply(commentReply);
			commentReplyJudge.setReportResult(0);
			commentReplyJudge.setJudgeReplyUserId(iD);
			commentReplyJudge.setJudgeReplyArticleId(commentReply.getCommentArticle().getArticleId());
			commentReplyJudgeService.InsertNewReplyJudge(commentReplyJudge);
		}
		commentReply.setTextGoodTotal(commentReplyJudgeService.GetReplyByTextGoodAndReplyId(1, ajaxReplyId));
		commentReply.setTextBadTotal(commentReplyJudgeService.GetReplyByTextBadAndReplyId(1, ajaxReplyId));
		commentReplyService.updateCommentReply(commentReply);

		return "OK";
	}

	// 回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX
	// 回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX回覆AJAX
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// 計算點擊次數
	@GetMapping("/CheckcountPlus/{articleId}")
	public String CheckcountPlus(@PathVariable("articleId") Integer articleId, Model model) {

		CommentArticle commentArticle = commentArticleService.getArticleById(articleId);
		commentArticle.setCheckCount(commentArticle.getCheckCount() + 1);
		commentArticleService.updateCommentArticle(commentArticle);
		// 07080708070807080708070807080708070807080708070807080708070807080708070807080708
		if (model.getAttribute("LoginID") == null) {
		} else {
			int iD = (int) model.getAttribute("LoginID");
			if (commentArticleJudgeService.ArticleJudgeExists(iD, articleId)) {
				CommentArticleJudge commentArticleJudge = commentArticleJudgeService
						.GetArticleJudgeResaultByUserIdAndArticleId(iD, articleId);
				if (commentArticleJudge.getCheckResult() != 1) {
					commentArticleJudge.setCheckResult(1);
				} else {

					commentArticleJudge.setCheckResult(0);
					commentArticleJudgeService.updateCommentArticleJudge(commentArticleJudge);
					commentArticleJudge.setCheckResult(1);
				}
				commentArticleJudgeService.updateCommentArticleJudge(commentArticleJudge);

			} else {
				CommentArticleJudge commentArticleJudge = new CommentArticleJudge();
				commentArticleJudge.setTextGood(0);
				commentArticleJudge.setTextBad(0);
				commentArticleJudge.setJudgeResult(0);
				commentArticleJudge.setReportResult(0);
				commentArticleJudge.setStoreResult(0);
				commentArticleJudge.setBlackResult(0);
				commentArticleJudge.setCheckResult(1);
				commentArticleJudge.setCommentArticle(commentArticle);
				commentArticleJudge.setJudgeArticleUserId(iD);
				commentArticleJudgeService.InsertNewArticleJudge(commentArticleJudge);
			}

			// 07080708070807080708070807080708070807080708070807080708070807080708070807080708

		}
		return "redirect:/ArticleInner/" + articleId.toString();
	}

	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// 儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試
	@PostMapping("/StoreAjax")
	@ResponseBody
	public String StoreArticleAjax(@RequestParam("ajaxArticleId") Integer ajaxArticleId, Model model) {
		int iD = (int) model.getAttribute("LoginID");

		CommentArticle commentArticle = commentArticleService.getArticleById(ajaxArticleId);
		if (commentArticleJudgeService.ArticleJudgeExists(iD, ajaxArticleId)) {
			CommentArticleJudge commentArticleJudge = commentArticleJudgeService
					.GetArticleJudgeResaultByUserIdAndArticleId(iD, ajaxArticleId);
			if (commentArticleJudge.getStoreResult() != 1) {
				commentArticleJudge.setStoreResult(1);
			} else {
				commentArticleJudge.setStoreResult(0);
			}
			commentArticleJudgeService.updateCommentArticleJudge(commentArticleJudge);

		} else {
			CommentArticleJudge commentArticleJudge = new CommentArticleJudge();
			commentArticleJudge.setTextGood(0);
			commentArticleJudge.setTextBad(0);
			commentArticleJudge.setJudgeResult(0);
			commentArticleJudge.setReportResult(0);
			commentArticleJudge.setStoreResult(1);
			// 0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
			commentArticleJudge.setBlackResult(0);
			commentArticleJudge.setCheckResult(0);
			// 0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
			commentArticleJudge.setCommentArticle(commentArticle);
			commentArticleJudge.setJudgeArticleUserId(iD);
			commentArticleJudgeService.InsertNewArticleJudge(commentArticleJudge);
		}

		return "OK";
	}

	// 儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試儲存ajax測試
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	// 文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX
	@PostMapping("/ReportCommentArticleAjax")
	@ResponseBody
	public String ReportArticleAjax(@RequestParam("ajaxArticleId") Integer ajaxArticleId, Model model,
			@RequestParam("ajaxViolationId") Integer ajaxViolationId) {
		int iD = (int) model.getAttribute("LoginID");

		CommentArticle commentArticle = commentArticleService.getArticleById(ajaxArticleId);
		if (commentArticleJudgeService.ArticleJudgeExists(iD, ajaxArticleId)) {
			CommentArticleJudge commentArticleJudge = commentArticleJudgeService
					.GetArticleJudgeResaultByUserIdAndArticleId(iD, ajaxArticleId);
			commentArticleJudge.setReportResult(1);
			commentArticleJudgeService.updateCommentArticleJudge(commentArticleJudge);

		} else {
			CommentArticleJudge commentArticleJudge = new CommentArticleJudge();
			commentArticleJudge.setTextGood(0);
			commentArticleJudge.setTextBad(0);
			commentArticleJudge.setJudgeResult(0);
			commentArticleJudge.setReportResult(1);
			commentArticleJudge.setStoreResult(0);
			// 0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
			commentArticleJudge.setBlackResult(0);
			commentArticleJudge.setCheckResult(0);
			// 0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
			commentArticleJudge.setCommentArticle(commentArticle);
			commentArticleJudge.setJudgeArticleUserId(iD);
			commentArticleJudgeService.InsertNewArticleJudge(commentArticleJudge);
		}
		UserBean userBean = personalService.getUserInfo(commentArticle.getUser().getUserId());
		ViolationRule violationRule = violationRuleService.getViolationRuleById(ajaxViolationId);
		ViolationRef violationRef = new ViolationRef();
		violationRef.setCommentArticle(commentArticle);
		violationRef.setViolationRule(violationRule);
		violationRef.setUserBean(userBean);
		violationRefService.postNewViolationRef(violationRef);
		ViolationEvent violationEvent = new ViolationEvent();
		violationEvent.setTarget("待處理");
		violationEvent.setViolationRule(violationRule);
		violationEvent.setUser(userBean);
		violationEventService.saveViolationEvent(violationEvent);
		System.out.println(ajaxViolationId);

		return "OK";
	}
	// 文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX文章檢舉AJAX

	// 回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax
	@PostMapping("/ReportCommentReplyAjax")
	@ResponseBody
	public String ReportReplyAjax(@RequestParam("ajaxReplyId") Integer ajaxReplyId,
			@RequestParam("ajaxReplyViolationId") Integer ajaxReplyViolationId, Model model) {
		int iD = (int) model.getAttribute("LoginID");
		CommentReply commentReply = commentReplyService.getReplyById(ajaxReplyId);
		if (commentReplyJudgeService.ReplyJudgeExists(iD, ajaxReplyId)) {
			CommentReplyJudge commentReplyJudge = commentReplyJudgeService.GetReplyJudgeResaultByUserIdAndReplyId(iD,
					ajaxReplyId);
			commentReplyJudge.setReportResult(1);
			commentReplyJudgeService.updateCommentReplyJudge(commentReplyJudge);

		} else {
			CommentReplyJudge commentReplyJudge = new CommentReplyJudge();
			commentReplyJudge.setTextGood(0);
			commentReplyJudge.setTextBad(0);
			commentReplyJudge.setJudgeResult(0);
			commentReplyJudge.setReportResult(1);
			commentReplyJudge.setJudgeReplyArticleId(commentReply.getCommentArticle().getArticleId());
			commentReplyJudge.setCommentReply(commentReply);
			commentReplyJudge.setJudgeReplyUserId(iD);
			commentReplyJudgeService.InsertNewReplyJudge(commentReplyJudge);
		}
		UserBean userBean = personalService.getUserInfo(commentReply.getUser().getUserId());
		ViolationRule violationRule = violationRuleService.getViolationRuleById(ajaxReplyViolationId);
		ViolationRef violationRef = new ViolationRef();
		violationRef.setCommentReply(commentReply);
		violationRef.setViolationRule(violationRule);
		violationRef.setUserBean(userBean);
		violationRefService.postNewViolationRef(violationRef);
		ViolationEvent violationEvent = new ViolationEvent();
		violationEvent.setTarget("待處理");
		violationEvent.setViolationRule(violationRule);
		violationEvent.setUser(userBean);
		violationEventService.saveViolationEvent(violationEvent);
		return "OK";
	}

	// 0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
	// 0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
	// 0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
	@GetMapping("/PostCommentArticles")
	public String PostArticlelist(Model model) {
		int iD = (int) model.getAttribute("LoginID");
		return "Comment/ShowAllPersonPostArticles";
	}

	@GetMapping("/CheckCommentArticles")
	public String CheckArticlelist(Model model) {
		int iD = (int) model.getAttribute("LoginID");
		return "Comment/ShowAllPersonCheckArticles";
	}

	@GetMapping("/StoreCommentArticles")
	public String StoreArticlelist(Model model) {
		int iD = (int) model.getAttribute("LoginID");
		return "Comment/ShowAllPersonStoreArticles";
	}

	@GetMapping("/GoodCommentArticles")
	public String GoodArticlelist(Model model) {
		int iD = (int) model.getAttribute("LoginID");
		return "Comment/ShowAllPersonGoodArticles";
	}

	@GetMapping("/BadCommentArticles")
	public String BadArticlelist(Model model) {
		int iD = (int) model.getAttribute("LoginID");
		return "Comment/ShowAllPersonBadArticles";
	}

	@GetMapping("/ReportCommentArticles")
	public String ReportArticlelist(Model model) {
		int iD = (int) model.getAttribute("LoginID");
		return "Comment/ShowAllPersonReportArticles";
	}

	@PostMapping(value = "/PostCommentArticlesPage")
	@ResponseBody
	public Page<CommentArticleTemp> PostArticlesPageNo(@RequestParam(value = "pageNo") Integer pageNo, Model model) {
		int iD = (int) model.getAttribute("LoginID");
		commentArticleTempService.deleteAllTempCommentArticles();
		commentArticleTempService.GetIntoTempArticleByUserId(iD);
		Page<CommentArticleTemp> commentArticleTemp = commentArticleTempService.PageForCommentArticleTemp(pageNo, 4);
		return commentArticleTemp;
	}

	@PostMapping(value = "/CheckCommentArticlesPage")
	@ResponseBody
	public Page<CommentArticlePlusJudge> CheckArticlesPageNo(@RequestParam(value = "pageNo") Integer pageNo,
			Model model) {
		int iD = (int) model.getAttribute("LoginID");
		commentArticlePlusJudgeService.deleteAllArticlePlusJudge();
		commentArticlePlusJudgeService.GetArticleAndCheckJudge(iD);
		Page<CommentArticlePlusJudge> commentArticlePlusJudge = commentArticlePlusJudgeService
				.PageForCommentArticlePlusJudge(pageNo, 2);
		return commentArticlePlusJudge;
	}

	@PostMapping(value = "/StoreCommentArticlesPage")
	@ResponseBody
	public Page<CommentArticlePlusJudge> StoreArticlesPageNo(@RequestParam(value = "pageNo") Integer pageNo,
			Model model) {
		Integer judgeArticleUserId = (int) model.getAttribute("LoginID");
		System.out.println(judgeArticleUserId);
		commentArticlePlusJudgeService.deleteAllArticlePlusJudge();
		commentArticlePlusJudgeService.GetArticleAndStoreJudge(judgeArticleUserId);
		System.out.println("1111111111");
		Page<CommentArticlePlusJudge> commentArticlePlusJudge = commentArticlePlusJudgeService
				.PageForCommentArticlePlusJudge(pageNo, 2);
		return commentArticlePlusJudge;
	}

	@PostMapping(value = "/GoodCommentArticlesPage")
	@ResponseBody
	public Page<CommentArticlePlusJudge> GoodArticlesPageNo(@RequestParam(value = "pageNo") Integer pageNo,
			Model model) {
		int iD = (int) model.getAttribute("LoginID");
		commentArticlePlusJudgeService.deleteAllArticlePlusJudge();
		commentArticlePlusJudgeService.GetArticleAndGoodJudge(iD);
		Page<CommentArticlePlusJudge> commentArticlePlusJudge = commentArticlePlusJudgeService
				.PageForCommentArticlePlusJudge(pageNo, 2);
		return commentArticlePlusJudge;
	}

	@PostMapping(value = "/BadCommentArticlesPage")
	@ResponseBody
	public Page<CommentArticlePlusJudge> BadArticlesPageNo(@RequestParam(value = "pageNo") Integer pageNo,
			Model model) {
		int iD = (int) model.getAttribute("LoginID");
		commentArticlePlusJudgeService.deleteAllArticlePlusJudge();
		commentArticlePlusJudgeService.GetArticleAndBadJudge(iD);
		Page<CommentArticlePlusJudge> commentArticlePlusJudge = commentArticlePlusJudgeService
				.PageForCommentArticlePlusJudge(pageNo, 2);
		return commentArticlePlusJudge;
	}

	@PostMapping(value = "/ReportCommentArticlesPage")
	@ResponseBody
	public Page<CommentArticlePlusJudge> ReportArticlesPageNo(@RequestParam(value = "pageNo") Integer pageNo,
			Model model) {
		int iD = (int) model.getAttribute("LoginID");
		commentArticlePlusJudgeService.deleteAllArticlePlusJudge();
		commentArticlePlusJudgeService.GetArticleAndReportJudge(iD);
		Page<CommentArticlePlusJudge> commentArticlePlusJudge = commentArticlePlusJudgeService
				.PageForCommentArticlePlusJudge(pageNo, 2);
		return commentArticlePlusJudge;
	}

	// 0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
	// 0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
	// 回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax回覆檢舉ajax
//黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單
	@GetMapping("/InsertBlack/{articleId}/{userId}")
	public String AddBlacklist(@PathVariable("articleId") Integer articleId, @PathVariable("userId") Integer userId,
			Model model) {

		int iD = (int) model.getAttribute("LoginID");
		Blacklist blacklist = new Blacklist();
		blacklist.setUser(personalService.getUserInfo(iD));
		blacklist.setBlackedUser(personalService.getUserInfo(userId));
		blacklistService.postNewBlacklist(blacklist);
		return "redirect:/ArticleInner/" + articleId.toString();

	}

//黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單黑名單
	// 解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖
	@GetMapping("/DeleteBlack/{articleId}/{userId}")
	public String Deletelacklist(@PathVariable("articleId") Integer articleId, @PathVariable("userId") Integer userId,
			Model model) {

		int iD = (int) model.getAttribute("LoginID");
		blacklistService.deleteBlacklistById(blacklistService.getArticleByIdAndUserId(iD, userId).getBlacklistId());
		return "redirect:/ArticleInner/" + articleId.toString();

	}

	// 解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖解除封鎖
	@GetMapping("/articlepicture/{articleId}")
	public ResponseEntity<byte[]> getPicture(@PathVariable("articleId") Integer articleId) {
		byte[] body = null;
		ResponseEntity<byte[]> re = null;
		MediaType mediaType = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());

		CommentArticle commentArticle = commentArticleService.getArticleById(articleId);
		if (commentArticle == null) {
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
		String filename = commentArticle.getImagePath();
		if (filename != null) {
			if (filename.toLowerCase().endsWith("jfif")) {
				mediaType = MediaType.valueOf(context.getMimeType("dummy.jpeg"));
			} else {
				mediaType = MediaType.valueOf(context.getMimeType(filename));
				headers.setContentType(mediaType);
			}
		}

		Blob blob = commentArticle.getArticleImage();
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
}
