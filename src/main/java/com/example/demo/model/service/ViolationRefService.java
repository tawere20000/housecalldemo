package com.example.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.ViolationRef;
import com.example.demo.model.dao.ViolationRefRepository;

@Service
@Transactional
public class ViolationRefService {
	
	@Autowired
	private ViolationRefRepository violationRefRepository;
	
	public Page<ViolationRef> pageForViolationRef(int pageNo, int pageSize){
		Pageable pageable = PageRequest.of(pageNo-1,pageSize,Sort.by("violationRefId").ascending());
		return violationRefRepository.findAll(pageable);
	}
	
	public ViolationRef queryViolationRefByID(Integer violationRefId) {
		
		return violationRefRepository.findById(violationRefId).get();
	}
	
	public void deleteViolationRef(Integer violationRefId) {
		violationRefRepository.deleteById(violationRefId);
	}
	
	public void postNewViolationRef(ViolationRef violationRef) {

		violationRefRepository.save(violationRef);
	}

}
