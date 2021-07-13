package com.example.demo.validators.forum;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.bean.forum.ForumBean;


public class ForumUpdateValidator2 implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ForumUpdateValidator2.class.isAssignableFrom(clazz);
	}
	

	@Override
	public void validate(Object target, Errors errors) {
		ForumBean forumBean  = (ForumBean ) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "", "標題不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "", "內容不能空白");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "forumType", "", "文章種類空白");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "", "地址可寫附近但不能空白");
//		ValidationUtils.rejectIfEmpty(errors, "image", "", "必須挑選圖片");
//
//		if (forumBean .getForumType() == "選擇類型") {
//			
//				errors.rejectValue("forumType", "", "選擇類型");
//			
//		}
	}

}
