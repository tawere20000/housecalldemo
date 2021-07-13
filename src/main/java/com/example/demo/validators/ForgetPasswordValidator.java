package com.example.demo.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.bean.user.Personal_InformationBean;


public class ForgetPasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Personal_InformationBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
//		Personal_InformationBean pb = (Personal_InformationBean) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "", "電子郵件不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "", "手機號碼不能空白");
	}

}
