package com.example.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.ViolationRule;
import com.example.demo.model.dao.ViolationRuleRepository;



@Service
@Transactional
public class ViolationRuleService {
	@Autowired
	private ViolationRuleRepository violationRuleRepository;
	
	public List<ViolationRule> findAllviolationRules() {
		return violationRuleRepository.findAll();
	}

	public ViolationRule getViolationRuleById(int violationId) {

		return violationRuleRepository.findById(violationId).get();
	}

}
