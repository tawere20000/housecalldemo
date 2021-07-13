package com.example.demo.validators.forum;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.bean.forum.ForumReplyBean;

public class ForumReplyValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ForumReplyValidator.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ForumReplyBean forumReply = (ForumReplyBean) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "", "內容不能空白");

		
	}

}
