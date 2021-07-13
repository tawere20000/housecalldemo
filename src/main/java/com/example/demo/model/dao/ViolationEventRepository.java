package com.example.demo.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.ViolationEvent;

@Repository
public interface ViolationEventRepository extends JpaRepository<ViolationEvent,Integer>{
	
	List<ViolationEvent> findByUser_UserId(Integer iD);

}
