package com.example.demo.model.dao.statistics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.statisticsData.UserBehaviorComponentsPer;


@Repository
public interface UserBehaviorComponentsPerRepository extends JpaRepository<UserBehaviorComponentsPer, Integer>{

	public List<UserBehaviorComponentsPer> findAllByCategoryAndDateBetween(String category,String startdate,String enddate);
}
