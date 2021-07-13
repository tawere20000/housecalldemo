package com.example.demo.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.bean.user.UserBean;


public class LoginValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
//		UserBean pb = (UserBean) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "account", "", "帳號不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "密碼不能空白");
	}

}
