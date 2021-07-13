package com.example.demo.model.service.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.statisticsData.UserBehavior;
import com.example.demo.model.dao.statistics.UserBehaviorRepository;

@Service
@Transactional
public class UserBehaviorService {
	
	@Autowired
	UserBehaviorRepository userBehaviorRepository;
	
	public void saveUserBehavior(UserBehavior userBehavior) {
		userBehaviorRepository.save(userBehavior);
	}

}
