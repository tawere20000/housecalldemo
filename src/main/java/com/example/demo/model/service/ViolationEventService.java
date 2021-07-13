package com.example.demo.model.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.bean.ViolationEvent;
import com.example.demo.model.dao.ViolationEventRepository;

@Service
@Transactional
public class ViolationEventService {
	
	
	@Autowired
	private ViolationEventRepository violationEventRepository;
	
	public void saveViolationEvent(ViolationEvent violationEvent) {
		violationEventRepository.save(violationEvent);
	}
	
	public List<ViolationEvent> findAllviolationRules() {
		return violationEventRepository.findAll();
	}
	
	public ViolationEvent getViolationEventById(int violationId) {

		return violationEventRepository.findById(violationId).get();
	}
	
	public void deleteViolationEventById(Integer violationId) {
		violationEventRepository.deleteById(violationId);
	}
	
}
