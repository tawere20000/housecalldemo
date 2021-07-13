package com.example.demo.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.bean.user.UserBean;


public class UpdatePasswordValidator  implements Validator {
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UpdatePasswordValidator.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserBean ub = (UserBean) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "密碼不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordCheck", "", "確認密碼欄不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "originalPassword", "", "原始密碼欄不能空白");
		
		
		if (ub.getPassword() != null) {
			if (!ub.getPassword().equals(ub.getPasswordCheck())) {
				errors.rejectValue("invalidCredentials", "", "密碼欄與確認密碼不一致");
			}
		}
	}

}
