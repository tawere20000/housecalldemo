package com.example.demo.model.service.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.comment.CommentReply;
import com.example.demo.model.dao.comment.CommentReplyRepository;



@Service
@Transactional
public class CommentReplyService {
	@Autowired
	private CommentReplyRepository commentReplyRepository;
	
	public List<CommentReply> findAllCommentReplys(Integer articleId) {
		return commentReplyRepository.findByCommentArticle_articleId(articleId);
	}
	public void postNewReply(CommentReply commentReply) {
		commentReplyRepository.save(commentReply);
	}
	public CommentReply getReplyById(int replyId) {
		return commentReplyRepository.findById(replyId).get();
	}
	public void deleteCommentReplyById(Integer replyId) {
		commentReplyRepository.deleteById(replyId);
	}
	public void updateCommentReply(CommentReply commentReply) {
		commentReplyRepository.save(commentReply);
	}
	
}
