package com.example.demo.model.service.forum;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.forum.ForumPlusJudge;
import com.example.demo.model.dao.forum.ForumPlusJudgeRepository;

@Service
@Transactional
public class ForumPlusJudgeService {
	@Autowired
	private ForumPlusJudgeRepository forumPlusJudgeRepository;
	
	 public ForumPlusJudge GetForumArticleAndJudge(Integer articleId,Integer judgeArticleUserId) {
		return forumPlusJudgeRepository.findForumByForumIdAndJudgeForumUserId(
				articleId, judgeArticleUserId).get(0);
		
	};
}

