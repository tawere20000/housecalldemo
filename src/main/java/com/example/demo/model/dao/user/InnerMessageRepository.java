package com.example.demo.model.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.bean.user.InnerMessage;


public interface InnerMessageRepository extends JpaRepository<InnerMessage, Integer> {
	
	
	List<InnerMessage> findByReceiverIdAndMailStatus(int iD,String mailStatus);

}
