package com.example.demo.model.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.user.Vendor_InformationBean;

@Repository
public interface VendorRepository extends JpaRepository<Vendor_InformationBean, Integer> {
	
	List<Vendor_InformationBean> findByEmailAndPhoneNumber(String email, String phoneNumber);
	
	@Procedure
	(procedureName = "COUNTINGPOINTSVENDOR")
	 void updateVendorUserPoints(Integer userId);

}
