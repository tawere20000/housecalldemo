package com.example.demo.model.service.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.bean.user.UserBean;
import com.example.demo.model.bean.user.Vendor_InformationBean;
import com.example.demo.model.dao.user.TwoTableDAO;
import com.example.demo.model.dao.user.UserRepository;
import com.example.demo.model.dao.user.VendorRepository;




@Service
@Transactional
public class VendorServiceImpl implements VendorService {
	
	
	
	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	TwoTableDAO twoTableDAO;

	@Autowired
	UserRepository accountRepository;
	
	
	

	@Override
	public List<Vendor_InformationBean> findAllVendorInfo() {
		
		return vendorRepository.findAll();
	}

//	@Override
//	public void addVendorInfo(Vendor_InformationBean personBean, UserBean userBean) {
//		
//		twoTableDAO.addVendorInfo(personBean, userBean);
//		
//	}
	@Override
	public void addVendorInfo(Vendor_InformationBean personBean) {

		twoTableDAO.addVendorInfo(personBean);
		
	}

	@Override
	public boolean idExists(String account) {
		
		List<UserBean> ub = accountRepository.findByAccount(account);
		if (ub.isEmpty()) {
			return false;
		} 
		return true;
	}

	@Override
	public Vendor_InformationBean getVendorInfo(int iD) {
		
		return vendorRepository.findById(iD).get();
		
	}

	@Override
	public Vendor_InformationBean getByEamilAndPhone(String email, String phoneNumber) {
		
		return vendorRepository.findByEmailAndPhoneNumber(email, phoneNumber).get(0);
	}

	@Override
	public void updateVendorInfo(Vendor_InformationBean vendorBean) {
		vendorRepository.save(vendorBean);
	}
	
	@Override
	 public void recalculatePoint(Integer userId) {
	  vendorRepository.updateVendorUserPoints(userId);
	}

}
