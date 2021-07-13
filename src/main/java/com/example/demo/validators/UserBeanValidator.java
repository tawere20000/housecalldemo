package com.example.demo.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.bean.user.UserBean;


public class UserBeanValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserBean ub = (UserBean) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "account", "", "帳號不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "密碼不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordCheck", "", "確認密碼欄不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "originalPassword", "", "原始密碼欄不能空白");
		
		
		if (ub.getPassword() != null) {
			if (!ub.getPassword().equals(ub.getPasswordCheck())) {
				errors.rejectValue("invalidCredentials", "", "密碼欄與確認密碼不一致");
			}
		}
//		System.out.println("mb.getMemberMultipartFile().getSize()=" + mb.getMemberMultipartFile().getSize());
//		if (ub.getMemberMultipartFile().getSize() == 0) {
//			errors.rejectValue("memberMultipartFile", "", "必須挑選圖片");
//		}
	}

}
