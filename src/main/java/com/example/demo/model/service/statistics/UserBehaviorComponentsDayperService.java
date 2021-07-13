package com.example.demo.model.service.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.statisticsData.UserBehaviorComponentsDayper;
import com.example.demo.model.dao.statistics.UserBehaviorComponentsDayperRepository;

@Service
@Transactional
public class UserBehaviorComponentsDayperService {
	
	@Autowired
	UserBehaviorComponentsDayperRepository userBehaviorComponentsDayperRepository;
	
	public List<Map<String,Object>> findlast7daysComponent(String category,String start,String end){
		List<Map<String,Object>> mapp = new ArrayList<Map<String,Object>>();
		List<UserBehaviorComponentsDayper> list = userBehaviorComponentsDayperRepository.findlastdays(category,start,end);
		 Iterator<UserBehaviorComponentsDayper> it = list.iterator();
		while(it.hasNext()) {
			UserBehaviorComponentsDayper obj = it.next();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("period",obj.getDate().substring(5).replace("-",""));
			map.put("廠商",(double)Math.round(obj.getVendor()*10000)/100);
			map.put("會員",(double)Math.round(obj.getPersonal()*10000)/100);
			map.put("訪客",(double)Math.round(obj.getVisitor()*10000)/100);
			mapp.add(map);
		}
		return mapp;
	}
	
	public List<UserBehaviorComponentsDayper> findlast7daysComponentperTab(String category,String start,String end){
		return userBehaviorComponentsDayperRepository.findlastdays(category,start,end);
	}

}
