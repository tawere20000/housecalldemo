package com.example.demo.model.service.statistics;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.bean.statisticsData.DaysRegister;
import com.example.demo.model.dao.statistics.DaysRegisterRepository;

@Service
@Transactional
public class DaysRegisterService {
	
	@Autowired
	DaysRegisterRepository daysRegisterRepository;
	
	public void savetodaysNewRegist(DaysRegister daysRegister) {
		daysRegisterRepository.save(daysRegister);
	}
	
	public List<DaysRegister> last7daysnewregist(Date start,Date end) {
		return daysRegisterRepository.findByDateBetween(start,end);
	}

}
