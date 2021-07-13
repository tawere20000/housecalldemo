package com.example.demo.model.service.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.statisticsData.UserBehaviorComponents;
import com.example.demo.model.dao.statistics.UserBehaviorComponentsDao;
import com.example.demo.model.dao.statistics.UserBehaviorComponentsRepository;

@Service
@Transactional
public class UserBehaviorComponentsService {
	
	@Autowired
	UserBehaviorComponentsDao userBehaviorComponentsDao;
	
	@Autowired
	UserBehaviorComponentsRepository userBehaviorComponentsRepository;
	
	public List<Object[]> findlast7daysComponentTab(String column,String start,String end){
		return userBehaviorComponentsDao.findlastdays(start,end,column);
		
	}
	
	public List<UserBehaviorComponents> last7dayscompbyarea(String category,String start,String end){
		return userBehaviorComponentsRepository.findAllByCategoryAndDateBetween(category,start,end);
	}

}
