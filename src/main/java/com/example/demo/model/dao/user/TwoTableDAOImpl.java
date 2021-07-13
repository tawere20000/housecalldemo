package com.example.demo.model.dao.user;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.user.Personal_InformationBean;
import com.example.demo.model.bean.user.Vendor_InformationBean;



@Repository
public class TwoTableDAOImpl implements TwoTableDAO {

	@Autowired
	EntityManager entityManager;

	@Autowired
	UserRepository accountRepository;
	
	@Autowired
	PersonalRepository personalRepository;

//	@Override
//	public void addPresonal_Information(Personal_InformationBean personBean, UserBean userBean) {
//		
//		userBean.setPersonBean(personBean);
//		
//		accountRepository.save(userBean);
//		
//	}
//	
//	@Override
//	public void addVendorInfo(Vendor_InformationBean vendorBean, UserBean userBean) {
//		
//		userBean.setVendorBean(vendorBean);
//		
//		accountRepository.save(userBean);
//		
//	}
//	
	@Override
	public void addPresonal_Information(Personal_InformationBean personBean) {


		accountRepository.save(personBean);

	}

	@Override
	public void addVendorInfo(Vendor_InformationBean vendorBean) {


		accountRepository.save(vendorBean);

	}
	

}
