package com.example.demo.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.bean.user.Personal_InformationBean;


public class UpdateMemberValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Personal_InformationBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Personal_InformationBean pb = (Personal_InformationBean) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "", "姓名不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idNumber", "", "身分證字號不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "", "電子郵件不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthday", "", "生日不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "addressDetail", "", "詳細地址不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "", "手機號碼不能空白");
//		ValidationUtils.rejectIfEmpty(errors, "userImage", "", "必須挑選圖片");
	}

}
