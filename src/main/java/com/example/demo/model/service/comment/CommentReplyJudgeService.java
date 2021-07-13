package com.example.demo.model.service.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.comment.CommentReplyJudge;
import com.example.demo.model.dao.comment.CommentReplyJudgeRepository;



@Service
@Transactional
public class CommentReplyJudgeService {
	@Autowired
	private CommentReplyJudgeRepository commentReplyJudgeRepository;

	public List<CommentReplyJudge> findAllCommentReplyJudgeJudges() {
		return commentReplyJudgeRepository.findAll();
	}

	public void InsertNewReplyJudge(CommentReplyJudge commentReplyJudge) {

		commentReplyJudgeRepository.save(commentReplyJudge);
	}

	public CommentReplyJudge getReplyById(int Reply_Id) {

		return commentReplyJudgeRepository.findById(Reply_Id).get();
	}

	public void updateCommentReplyJudge(CommentReplyJudge commentReplyJudge) {
		commentReplyJudgeRepository.save(commentReplyJudge);
	}

	public void deleteCommentReplyJudgeById(Integer Reply_Id) {
		commentReplyJudgeRepository.deleteById(Reply_Id);
	}

	public Integer GetReplyByTextGoodAndReplyId(Integer textGood,Integer judgeReplyId) {
		return commentReplyJudgeRepository
				.countByTextGoodAndCommentReply_ReplyId(textGood, judgeReplyId);

	};

	public Integer GetReplyByTextBadAndReplyId(Integer textBad,Integer ReplyId) {
		return commentReplyJudgeRepository
				.countByTextBadAndCommentReply_ReplyId(textBad, ReplyId);

	};
	
	
	
	public CommentReplyJudge GetReplyJudgeResaultByUserIdAndReplyId(Integer judgeReplyUserId,Integer replyId) {
		return commentReplyJudgeRepository.findByJudgeReplyUserIdAndCommentReply_ReplyId(judgeReplyUserId, replyId).get(0);

	};
	

	public boolean ReplyJudgeExists(Integer judgeReplyUserId,Integer replyId) {
		List<CommentReplyJudge> commentReplyJudge = commentReplyJudgeRepository.findByJudgeReplyUserIdAndCommentReply_ReplyId(judgeReplyUserId, replyId);
		if (commentReplyJudge.isEmpty()) {
			return false;
		}
		return true;
	}
}
