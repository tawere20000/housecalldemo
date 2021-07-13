package com.example.demo.validators.comment;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.bean.comment.CommentArticle;



public class PostArticleValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return PostArticleValidator.class.isAssignableFrom(clazz);
	}
	

	@Override
	public void validate(Object target, Errors errors) {
		CommentArticle commentArticle = (CommentArticle) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "", "不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "", "內容不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taipeiArea", "", "住的區域不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "", "地址可寫附近但不能空白");
		ValidationUtils.rejectIfEmpty(errors, "image", "", "必須挑選圖片");

		if (commentArticle.getTaipeiArea() == "選擇區域") {
			
				errors.rejectValue("taipeiArea", "", "請選擇區域");
			
		}
	}

}
