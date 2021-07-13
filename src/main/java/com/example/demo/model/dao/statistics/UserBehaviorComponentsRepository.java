package com.example.demo.model.dao.statistics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.statisticsData.UserBehaviorComponents;


@Repository
public interface UserBehaviorComponentsRepository extends JpaRepository<UserBehaviorComponents,Integer>{

	public List<UserBehaviorComponents> findAllByCategoryAndDateBetween(String category,String startdate,String enddate);
	
}
