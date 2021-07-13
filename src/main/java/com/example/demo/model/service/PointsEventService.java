package com.example.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.PointsEvent;
import com.example.demo.model.bean.user.UserBean;
import com.example.demo.model.dao.PointsEventRepository;
import com.example.demo.model.service.user.PersonalService;
import com.example.demo.model.service.user.VendorService;

@Service
@Transactional
public class PointsEventService {

	@Autowired
	PointsEventRepository pointsEventRepository;
	
	@Autowired
	PointRuleService prs;
	
	@Autowired
	PersonalService ps;
	
	@Autowired
	VendorService vs;
	
	
	/**
	 * 事件新增:使用會員積分折抵商城訂單金額
	 */
	public void discountOrder(UserBean user, Integer times) {
		PointsEvent pointsEvent = new PointsEvent();
		pointsEvent.setUser(user);
		pointsEvent.setTimes(times);
		pointsEvent.setPointRule(prs.queryPointsRuleById(4));
		pointsEvent.setDetails("使用積分於商城折抵" + times + "元");
		pointsEventRepository.save(pointsEvent);
		if (user.getCategory().equals("會員")) {
			ps.recalculatePoint(user.getUserId());
		} else {
			vs.recalculatePoint(user.getUserId());
		}
	}
	
	public List<PointsEvent> findAllpointsEventRepository() {
		return pointsEventRepository.findAll();
	}

	public void postNewPointsEvent(PointsEvent pointsEvent) {

		pointsEventRepository.save(pointsEvent);
	}
	public PointsEvent getPointsEventByUserId(Integer userId) {
		
		return pointsEventRepository.findByUser_UserId(userId).get(0);
	}
	public PointsEvent getPointsEventByUserIdAndActionId(Integer userId,Integer actionId) {
		return pointsEventRepository.findByUser_UserIdAndPointRule_ActionId(userId,actionId).get(0);
	}
	public PointsEvent getPointsEventById(Integer pointsEventId) {

		return pointsEventRepository.findById(pointsEventId).get();
	}
	public void updatePointsEvent(PointsEvent pointsEvent) {
		pointsEventRepository.save(pointsEvent);
	}
	public void deletePointsEventById(Integer pointsEventId) {
		pointsEventRepository.deleteById(pointsEventId);
	}
	//07080708070807080708070807080708070807080708070807080708070807080708070807080708070807080708
	public boolean PointsEventExists(Integer userId,Integer actionId) {
		List<PointsEvent> pointsEvent = pointsEventRepository.findByUser_UserIdAndPointRule_ActionId(userId,actionId);
		if (pointsEvent .isEmpty()) {
			return false;
		}
		return true;
	}

}
