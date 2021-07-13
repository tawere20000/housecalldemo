package com.example.demo.model.service.user;

import java.util.List;

import com.example.demo.model.bean.user.Vendor_InformationBean;



public interface VendorService {

	// 查所有會員的資料
	List<Vendor_InformationBean> findAllVendorInfo();

	// 新增會員資料
	void addVendorInfo(Vendor_InformationBean personBean);
//	// 新增會員資料
//	void addVendorInfo(Vendor_InformationBean personBean, UserBean userBean);

	// 註冊時檢查帳號是否重複
	boolean idExists(String account);
	
	
	//查詢廠商個人資料
	
	Vendor_InformationBean getVendorInfo(int iD);
	
	
	//忘記密碼用
	
	Vendor_InformationBean getByEamilAndPhone(String email, String phoneNumber);
	
	
	//更新廠商資料
	void updateVendorInfo(Vendor_InformationBean vendorBean);
	
	public void recalculatePoint(Integer userId);

}
