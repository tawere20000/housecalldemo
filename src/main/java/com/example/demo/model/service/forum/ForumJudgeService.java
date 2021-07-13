package com.example.demo.model.service.forum;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.forum.ForumJudge;
import com.example.demo.model.dao.forum.ForumJudgeRepository;
@Service
@Transactional
public class ForumJudgeService {

	@Autowired
	private ForumJudgeRepository forumJudgeRepository;

	public List<ForumJudge> findAllCommentArticleJudgeJudges() {
		return forumJudgeRepository.findAll();
	}

	public void InsertNewForumJudge(ForumJudge forumJudge) {

		forumJudgeRepository.save(forumJudge);
	}

	public ForumJudge getArticleById(int forum_Id) {

		return forumJudgeRepository.findById(forum_Id).get();
	}

	public void updateForumJudge(ForumJudge forumJudge) {
		forumJudgeRepository.save(forumJudge);
	}

	public void deleteForumJudgeById(Integer forum_Id) {
		forumJudgeRepository.deleteById(forum_Id);
	}

	public Integer GetForumByTextGoodAndForumId(Integer textGood,Integer forumId) {
		return forumJudgeRepository
				.countByTextGoodAndForumBean_forumId(textGood, forumId);

	};

	public Integer GetForumByTextBadAndForumId(Integer textBad,Integer forumId) {
		return forumJudgeRepository
				.countByTextBadAndForumBean_forumId(textBad, forumId);

	};

	public ForumJudge GetForumJudgeResaultByUserIdAndForumId(Integer judgeForumUserId,Integer ForumId) {
		return forumJudgeRepository.findByJudgeForumUserIdAndForumBean_forumId(judgeForumUserId, ForumId).get(0);

	};
	public boolean ForumJudgeExists(Integer judgeArticleUserId,Integer articleId) {
		List<ForumJudge> forumJudge = forumJudgeRepository.findByJudgeForumUserIdAndForumBean_forumId(judgeArticleUserId, articleId);
		if (forumJudge.isEmpty()) {
			return false;
		}
		return true;
	}
	
}
