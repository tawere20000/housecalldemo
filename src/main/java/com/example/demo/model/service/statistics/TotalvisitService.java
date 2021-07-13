package com.example.demo.model.service.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.statisticsData.Totalvisit;
import com.example.demo.model.dao.statistics.TotalvisitRepository;

@Service
@Transactional
public class TotalvisitService {
	
	@Autowired
	TotalvisitRepository totalvisitRepository;
	
	
	public void addTotalVisitData(Totalvisit totalvisit) {
		
		totalvisitRepository.save(totalvisit);
	}
	
	
	
	public List<Totalvisit> getlast7daysdata(String start,String end){
		return totalvisitRepository.findlast7days(start,end);
	}
	
}
