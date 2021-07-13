package com.example.demo.model.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.user.Personal_InformationBean;


@Repository
public interface PersonalRepository extends JpaRepository<Personal_InformationBean, Integer> {
	

	List<Personal_InformationBean> findByEmailAndPhoneNumber(String email, String phoneNumber);
	
	List<Personal_InformationBean> findByEmail(String email);
	
	
	@Procedure(procedureName = "COUNTINGPOINTSMEMBER")
	 void updatePersonalUserPoints(Integer userId);


}
