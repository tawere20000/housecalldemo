package com.example.demo.model.dao.statistics;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.bean.statisticsData.DaysRegister;

public interface DaysRegisterRepository extends JpaRepository<DaysRegister, Date>{
	
	public List<DaysRegister> findByDateBetween(Date start,Date end);

}
