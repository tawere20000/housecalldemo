package com.example.demo.model.dao.user;

import com.example.demo.model.bean.user.Personal_InformationBean;
import com.example.demo.model.bean.user.Vendor_InformationBean;

public interface TwoTableDAO {

//	// 新增會員資料
//	void addPresonal_Information(Personal_InformationBean personBean, UserBean userBean);
//	
//	
//	// 新增廠商資料
//	void addVendorInfo(Vendor_InformationBean vendorBean, UserBean userBean);
//	
	// 新增會員資料
	void addPresonal_Information(Personal_InformationBean personBean);

	
	// 新增廠商資料
	void addVendorInfo(Vendor_InformationBean vendorBean);
	
	

	
	
	
}
