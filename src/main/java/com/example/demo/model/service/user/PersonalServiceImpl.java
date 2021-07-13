package com.example.demo.model.service.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
import com.example.demo.model.bean.user.Vendor_InformationBean;
import com.example.demo.model.dao.BlacklistRepository;
import com.example.demo.model.dao.PointRuleRopository;
import com.example.demo.model.dao.PointsEventRepository;
import com.example.demo.model.dao.ViolationEventRepository;
import com.example.demo.model.dao.comment.CommentArticleRepository;
import com.example.demo.model.dao.comment.CommentReplyRepository;
import com.example.demo.model.dao.forum.ForumReplyRepository;
import com.example.demo.model.dao.forum.ForumRepository;
import com.example.demo.model.dao.troubles.QuotationRepository;
import com.example.demo.model.dao.troubles.TroubleshootingRepository;
import com.example.demo.model.dao.user.InnerMessageRepository;
import com.example.demo.model.dao.user.PersonalRepository;
import com.example.demo.model.dao.user.TwoTableDAO;
import com.example.demo.model.dao.user.UserRepository;
import com.example.demo.model.dao.user.VendorRepository;



@Service
@Transactional
public class PersonalServiceImpl implements PersonalService {

//	@Autowired
//	UserRepository userDAO;

	@Autowired
	PersonalRepository personalRepository;

	@Autowired
	TwoTableDAO twoTableDAO;

	@Autowired
	UserRepository userRepository;

	@Autowired
	BlacklistRepository blacklistRepository;

	@Autowired
	CommentArticleRepository commentArticleRepository;

	@Autowired
	CommentReplyRepository commentReplyRepository;

	@Autowired
	ForumRepository forumRepository;

	@Autowired
	ForumReplyRepository forumReplyRepository;

	@Autowired
	PointRuleRopository pointRuleRopository;

	@Autowired
	PointsEventRepository pointsEventRepository;
	
	@Autowired
	VendorRepository vendorRepository;
	
	@Autowired
	ViolationEventRepository violationEventRepository;
	
	@Autowired
	TroubleshootingRepository troubleshootingBeanRepository;
	
	@Autowired
	QuotationRepository quotationRepository;
	
	@Autowired
	InnerMessageRepository innerMessageRepository;

	@Resource
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")

	private String from;

	@Override
	public List<Personal_InformationBean> findAllPersonalInfo() {

		return personalRepository.findAll();
	}

	@Override
	public void addPresonal_Information(Personal_InformationBean personBean) {

		twoTableDAO.addPresonal_Information(personBean);
		
	}

	@Override
	public boolean loginCheckAccount(String account) {
		List<UserBean> ub = userRepository.findByAccount(account);
		if (ub.isEmpty()) {
			return false;
		}
		return true;
	}


	@Override
	public Personal_InformationBean getPersonInfoById(int iD) {

		return personalRepository.findById(iD).get();
	}


	@Override
	public void updatePersonInfo(Personal_InformationBean personBean) {

		personalRepository.save(personBean);

	}

	@Override
	public void updatePassword(UserBean usernean) {

		userRepository.save(usernean);

	}

	@Override
	public UserBean getUserInfo(int iD) {

		return userRepository.findById(iD).get();

	}


	@Override

	public int checkEamilAndPhone(String email, String phoneNumber) {
		
//		0 查無資料
//		1 會員
//		2 廠商
		
		
		
	 List<Personal_InformationBean> person = personalRepository.findByEmailAndPhoneNumber(email, phoneNumber);
	
	if (person.isEmpty()) {
		
		List<Vendor_InformationBean> vendor = vendorRepository.findByEmailAndPhoneNumber(email, phoneNumber);

		if(vendor.isEmpty()) {
			return 0 ;
		}else {
			return 2 ;
		}

	} else {

		return 1;
	}


	}

	@Override

	public void sendSimpleMail(String to, String subject, String content) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);

		message.setTo(to);

		message.setSubject(subject);

		message.setText(content);

		mailSender.send(message);

	}

	@Override
	public List<Blacklist> getBlacklistByPersonalId(Integer iD) {

		return blacklistRepository.findByUser_UserId(iD);
	}

	@Override
	public List<CommentArticle> getArticleByPersonalId(int iD) {
		return commentArticleRepository.findByUser_UserId(iD);
	}

	@Override
	public List<CommentReply> getCommentyReplyByPersonalId(int iD) {
		return commentReplyRepository.findByUser_UserId(iD);
	}

	@Override
	public List<ForumBean> getForumArticleByPersonalId(int iD) {
		return forumRepository.findByUser_UserId(iD);
	}

	@Override
	public List<ForumReplyBean> getForumReplyByPersonalId(int iD) {
		return forumReplyRepository.findByUser_UserId(iD);
	}

	@Override
	public List<PointRule> getPointRule() {
		return pointRuleRopository.findAll();
	}

	@Override
	public List<PointsEvent> getPointRecord(int iD) {
		return pointsEventRepository.findByUser_UserId(iD);
	}

	@Override
	public String accountExists(String account) {

		List<UserBean> exist = userRepository.findByAccount(account);

		if (exist.isEmpty()) {

			return "empty";

		} else {

			return exist.get(0).getAccount();
		}
	}

	@Override
	public String emailExists(String email) {
		List<Personal_InformationBean> exist = personalRepository.findByEmail(email);
		if (exist.isEmpty()) {

			return "empty";

		} else {

			return exist.get(0).getEmail();
		}
	}

	@Override
	public Personal_InformationBean getByEamilAndPhone(String email, String phoneNumber) {
		return personalRepository.findByEmailAndPhoneNumber(email, phoneNumber).get(0);
	}

	@Override
	public UserBean loginGetInfo(String account) {
		return userRepository.findByAccount(account).get(0);
	}

	@Override
	public List<ViolationEvent> getViolationById(int id) {
		return violationEventRepository.findByUser_UserId(id);
	}

	@Override
	public ForumBean getForumBean(int id) {
		return forumRepository.findById(id).get();
	}

	@Override
	public List<TroubleshootingBean> getTroubleById(int id) {
		return troubleshootingBeanRepository.findByUser_UserId(id);
	}

	@Override
	public List<Quotation> getQuotationById(int id) {
		return quotationRepository.findByVendorId_UserId(id);
	}

	@Override
	public Quotation getQuotationByOrderId(int orderId) {
		return quotationRepository.findByBusinessOrderId_BusinessOrderId(orderId);
	}

	@Override
	public void deleteBlackByUserId(int id) {
		blacklistRepository.deleteByBlackedUser_userId(id);
	}

	@Override
	public void updateUserBean(UserBean userBean) {
		userRepository.save(userBean);	
	}

//	@Override
//	public void loginPlusPoint(Integer userId) {
//		pointsEventRepository.loginPoint(userId);		
//	}

	@Override
	public void saveBlack(Blacklist blacklist) {
		blacklistRepository.save(blacklist);
		
	}

	@Override
	public void sendInnerMessage(InnerMessage innerMessage) {

		innerMessageRepository.save(innerMessage);
	}

	@Override
	public List<InnerMessage> getReceiveMessage(int iD, String mailStatus) {
		return innerMessageRepository.findByReceiverIdAndMailStatus(iD, mailStatus);
	}
	
	/**
	 * 執行預存程序重新計算會員積分
	 * @param userId
	 */
	@Override
	 public void recalculatePoint(Integer userId) {
	  personalRepository.updatePersonalUserPoints(userId);
	 }
	
	@Override
	public void loginPlusPoint(PointsEvent pointsEvent) {
		pointsEventRepository.save(pointsEvent);
	}

	@Override
	public boolean judgmentLoginPoint(Integer userId, Integer actionId) throws Exception {

		List<PointsEvent> loginPointByDay = pointsEventRepository.findByUser_UserIdAndPointRule_ActionId(userId,
				actionId);

		if (loginPointByDay.size() == 0) {
			return true;
		}

		Date recentLoginDay = loginPointByDay.get(loginPointByDay.size() - 1).getEventDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(new Date());
		Date now = sdf.parse(s);

		int a = (int) ((now.getTime() - recentLoginDay.getTime()) / (1000 * 3600 * 24));

		if (a < 1)
			return false;
		else
			return true;

	}
	@Override
	public InnerMessage getMessageById(int id) {
		return innerMessageRepository.findById(id).get();
	}
	
	@Override
	public PointRule getPointRuleById(Integer actionId) {
		return pointRuleRopository.findById(actionId).get();
	}
}
