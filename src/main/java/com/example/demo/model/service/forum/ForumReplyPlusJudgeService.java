package com.example.demo.model.service.forum;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.forum.ForumReplyPlusJudge;
import com.example.demo.model.dao.forum.ForumReplyPlusJudgeRepository;




@Service
@Transactional
public class ForumReplyPlusJudgeService {
	
	@Autowired
	private ForumReplyPlusJudgeRepository forumReplyPlusJudgeRepository;
	
	 public List<ForumReplyPlusJudge> GetAllReplysAndJudge(Integer forumId,Integer judgeReplyUserId) {
		return forumReplyPlusJudgeRepository
				.findReplyByForumIdAndJudgeReplyUserId(forumId,judgeReplyUserId);
		
	};
//	public Page<CommentReplyPlusJudge> getCommentReplyPlusJudgeListByPage(int pageNum, int pageSize) {
//		  Sort sort =Sort.by(Sort.Direction.ASC , "ID");
//		  Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
//		  Page<CommentReplyPlusJudge> commentReplyPlusJudge = commentReplyPlusJudgeRepository.findAll(pageable);
//		  return commentReplyPlusJudge;
//		 }
}
