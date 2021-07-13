package com.example.demo.model.service.forum;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.forum.ForumReplyJudge;
import com.example.demo.model.dao.forum.ForumReplyJudgeRepository;

@Service
@Transactional
public class ForumReplyJudgeService {
	@Autowired
	private ForumReplyJudgeRepository forumReplyJudgeRepository;

	public List<ForumReplyJudge> findAllForumReplyJudgeJudges() {
		return forumReplyJudgeRepository.findAll();
	}

	public void InsertNewReplyJudge(ForumReplyJudge forumReplyJudge) {

		forumReplyJudgeRepository.save(forumReplyJudge);
	}

	public ForumReplyJudge getReplyById(int Reply_Id) {

		return forumReplyJudgeRepository.findById(Reply_Id).get();
	}

	public void updateForumReplyJudge(ForumReplyJudge forumReplyJudge){
		forumReplyJudgeRepository.save(forumReplyJudge);
	}

	public void deleteForumReplyJudgeById(Integer Reply_Id) {
		forumReplyJudgeRepository.deleteById(Reply_Id);
	}

	public Integer GetReplyByTextGoodAndReplyId(Integer textGood,Integer judgeReplyId) {
		return forumReplyJudgeRepository
				.countByTextGoodAndForumReplyBean_ReplyId(textGood, judgeReplyId);

	};

	public Integer GetReplyByTextBadAndReplyId(Integer textBad,Integer ReplyId) {
		return forumReplyJudgeRepository
				.countByTextBadAndForumReplyBean_ReplyId(textBad, ReplyId);

	};
	
	public ForumReplyJudge GetReplyJudgeResaultByUserIdAndReplyId(Integer judgeReplyUserId,Integer replyId) {
		return forumReplyJudgeRepository.findByJudgeReplyUserIdAndForumReplyBean_ReplyId(judgeReplyUserId, replyId).get(0);

	};
	
	
	

	public boolean ReplyJudgeExists(Integer judgeReplyUserId,Integer replyId) {
		List<ForumReplyJudge> forumReplyJudge = forumReplyJudgeRepository.findByJudgeReplyUserIdAndForumReplyBean_ReplyId(judgeReplyUserId, replyId);
		if (forumReplyJudge.isEmpty()) {
			return false;
		}
		return true;
	}
}
