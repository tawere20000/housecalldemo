package com.example.demo.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.bean.user.Personal_InformationBean;


public class PersonBeanValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Personal_InformationBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Personal_InformationBean pb = (Personal_InformationBean) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "account", "", "帳號不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "密碼不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordCheck", "", "確認密碼欄不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "", "姓名不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idNumber", "", "身分證字號不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "", "電子郵件不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthday", "", "生日不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "", "手機號碼不能空白");
		ValidationUtils.rejectIfEmpty(errors, "userImage", "", "必須挑選圖片");
		if (pb.getAccount() != null) {
			if (pb.getAccount().length() < 5) {
				errors.rejectValue("account", "", "帳號欄不能小於五個字元");
			}
		}
		
		if (pb.getPassword() != null) {
			if (!pb.getPassword().equals(pb.getPasswordCheck())) {
				errors.rejectValue("password", "", "密碼欄與確認密碼不一致");
			}
		}
//		System.out.println("mb.getMemberMultipartFile().getSize()=" + mb.getMemberMultipartFile().getSize());
//		if (ub.getMemberMultipartFile().getSize() == 0) {
//			errors.rejectValue("memberMultipartFile", "", "必須挑選圖片");
//		}
	}

}
