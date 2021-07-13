package com.example.demo.model.service.user;

import java.util.List;

import com.example.demo.model.bean.Blacklist;
import com.example.demo.model.bean.PointRule;
import com.example.demo.model.bean.PointsEvent;
import com.example.demo.model.bean.ViolationEvent;
import com.example.demo.model.bean.comment.CommentArticle;
import com.example.demo.model.bean.comment.CommentReply;
import com.example.demo.model.bean.forum.ForumBean;
import com.example.demo.model.bean.forum.ForumReplyBean;
import com.example.demo.model.bean.troubles.Quotation;
import com.example.demo.model.bean.troubles.TroubleshootingBean;
import com.example.demo.model.bean.user.InnerMessage;
import com.example.demo.model.bean.user.Personal_InformationBean;
import com.example.demo.model.bean.user.UserBean;



public interface PersonalService {

	// 查所有會員的資料
	List<Personal_InformationBean> findAllPersonalInfo();

	// 新增會員資料
	void addPresonal_Information(Personal_InformationBean personBean);

	// 註冊時檢查帳號是否重複
	boolean loginCheckAccount(String account);

	// 登入時檢查帳密
//	boolean loginChechAccount(String account);

	// 查詢個人會員資料
	Personal_InformationBean getPersonInfoById(int iD);
	
	//得到UserBean
//	UserBean getUserByAcctPwd(String account, String password);
	
	//更新會員資料
	void updatePersonInfo(Personal_InformationBean personBean);

	//更新會員密碼
	void updatePassword(UserBean usernean);
	
	
	//查詢帳號資料
	UserBean getUserInfo(int iD);
	
	//登入檢查
//	UserBean loginCheck(String account, String password);
	
	//忘記密碼用
	int checkEamilAndPhone(String email, String phoneNumber);
	
	Personal_InformationBean getByEamilAndPhone(String email, String phoneNumber);
	
	//寄新密碼
	void sendSimpleMail(String to, String subject, String content);
	
//	//查黑名單
	List<Blacklist> getBlacklistByPersonalId(Integer iD);
	
	//查評論區發表文章
	List<CommentArticle> getArticleByPersonalId(int iD);
	
	//查評論區留言
	List<CommentReply> getCommentyReplyByPersonalId(int iD);
	
	//查討論區發表文章
	List<ForumBean> getForumArticleByPersonalId(int iD);
	
	//查討論區留言
	List<ForumReplyBean> getForumReplyByPersonalId(int iD);
	
	//顯示積分規則
	List<PointRule> getPointRule();
	
	//查積分獲得的紀錄
	List<PointsEvent> getPointRecord(int iD);
	
	//檢查帳號Ajax
	String accountExists(String account);
	
	//檢查EmailAjax
	String emailExists(String email);
	
	//登入用 拿密碼跟私鑰
	UserBean loginGetInfo(String account);
	
	//按ID查被檢舉次數
	List<ViolationEvent> getViolationById(int id);
	
	//拿ForumBean
	ForumBean getForumBean(int id);
	
	//查user有發過的Troubleshooting
	List<TroubleshootingBean> getTroubleById(int id);
	
	//查user有發過的Quotation
	List<Quotation> getQuotationById(int id);
	
	//拿一個Quotation
	Quotation getQuotationByOrderId(int orderId);
	
	//刪黑名單
	void deleteBlackByUserId(int id);
	
	//更新user
	void updateUserBean(UserBean userBean);
	
	//登入獲得積分
//	void loginPlusPoint(Integer userId);
	
	//測試黑名單
	void saveBlack(Blacklist blacklist);
	
	//站內信
	void sendInnerMessage(InnerMessage innerMessage);
	
	//查收件匣
	List<InnerMessage> getReceiveMessage(int iD,String mailStatus);
	
	
	//商城積分折抵
	public void recalculatePoint(Integer userId);
	
	// 看站內信內容
	InnerMessage getMessageById(int id);

	// 判斷登入是不是隔天了 是的話回傳true
	boolean judgmentLoginPoint(Integer userId, Integer actionId) throws Exception;

	// 增加point event
	void loginPlusPoint(PointsEvent pointsEvent);

	// 拿pointRule
	PointRule getPointRuleById(Integer actionId);
	
	
}
