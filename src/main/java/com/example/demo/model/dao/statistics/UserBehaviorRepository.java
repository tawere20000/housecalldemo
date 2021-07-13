package com.example.demo.model.dao.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.statisticsData.UserBehavior;


@Repository
public interface UserBehaviorRepository extends JpaRepository<UserBehavior,Integer>{
	
	

}
