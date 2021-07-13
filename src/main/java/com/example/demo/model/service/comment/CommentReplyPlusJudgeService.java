package com.example.demo.model.service.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.comment.CommentReplyPlusJudge;
import com.example.demo.model.dao.comment.CommentReplyPlusJudgeRepository;






@Service
@Transactional
public class CommentReplyPlusJudgeService {
	
	@Autowired
	private CommentReplyPlusJudgeRepository commentReplyPlusJudgeRepository;
	
	 public List<CommentReplyPlusJudge> GetAllReplysAndJudge(Integer articleId,Integer judgeReplyUserId) {
		return commentReplyPlusJudgeRepository.findReplyByArticleIdAndJudgeReplyUserId(articleId,judgeReplyUserId);
		
	};
//	public Page<CommentReplyPlusJudge> getCommentReplyPlusJudgeListByPage(int pageNum, int pageSize) {
//		  Sort sort =Sort.by(Sort.Direction.ASC , "ID");
//		  Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
//		  Page<CommentReplyPlusJudge> commentReplyPlusJudge = commentReplyPlusJudgeRepository.findAll(pageable);
//		  return commentReplyPlusJudge;
//		 }
}
