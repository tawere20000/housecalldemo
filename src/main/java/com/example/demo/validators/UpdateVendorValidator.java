package com.example.demo.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.bean.user.Vendor_InformationBean;


public class UpdateVendorValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Vendor_InformationBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Vendor_InformationBean pb = (Vendor_InformationBean) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "", "姓名不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "", "電子郵件不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "addressDetail", "", "地址不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "", "手機號碼不能空白");
//		}
	}

}
