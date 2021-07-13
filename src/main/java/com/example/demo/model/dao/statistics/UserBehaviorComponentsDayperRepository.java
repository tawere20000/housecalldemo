package com.example.demo.model.dao.statistics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.statisticsData.UserBehaviorComponentsDayper;


@Repository
public interface UserBehaviorComponentsDayperRepository extends JpaRepository<UserBehaviorComponentsDayper,Long>{

	@Query(value="select * from [user_behavior_components_dayper] WHERE [category] = ? AND [date] BETWEEN ? AND ?",nativeQuery=true)
	public List<UserBehaviorComponentsDayper> findlastdays(String category,String datestart,String dateend);
	
}
