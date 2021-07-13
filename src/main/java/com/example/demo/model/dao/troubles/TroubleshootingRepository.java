package com.example.demo.model.dao.troubles;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.bean.troubles.TroubleshootingBean;


public interface TroubleshootingRepository extends JpaRepository<TroubleshootingBean, Integer> {
	
	List<TroubleshootingBean> findByUser_UserId(Integer iD);
	
	List<TroubleshootingBean> findByStatus(String status);

}
