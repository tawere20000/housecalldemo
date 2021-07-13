package com.example.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.PointRule;
import com.example.demo.model.dao.PointsRuleRepository;




@Service
@Transactional
public class PointRuleService {

	@Autowired
	private PointsRuleRepository pointRuleRepository;
	
	public List<PointRule> findAllPointRuleRepository() {
		return pointRuleRepository.findAll();
	}


	
	/**
	 * 依積分規則ID查詢規則物件
	 * @param actionId 積分規則ID
	 * @return 積分規則物件
	 */
	public PointRule queryPointsRuleById(Integer actionId) {
		return pointRuleRepository.findById(actionId).get();
	}
	

}
