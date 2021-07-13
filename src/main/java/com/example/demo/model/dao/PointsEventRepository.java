package com.example.demo.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.PointsEvent;


@Repository
public interface PointsEventRepository extends JpaRepository<PointsEvent,Integer>{
	
List<PointsEvent> findByUser_UserId(Integer iD);

List<PointsEvent> findByUser_UserIdAndPointRule_ActionId(Integer userId,Integer actionId);
	
//	@Procedure(procedureName = "COUNTINGPOINTS")
//	void loginPoint(Integer userId);

	
}
