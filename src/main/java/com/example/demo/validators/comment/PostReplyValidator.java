package com.example.demo.validators.comment;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.bean.comment.CommentReply;


public class PostReplyValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PostReplyValidator.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CommentReply commentReply = (CommentReply) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "", "內容不能空白");

		
	}

}
