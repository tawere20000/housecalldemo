package com.example.demo.model.dao.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.comment.CommentReply;

@Repository
public interface CommentReplyRepository extends JpaRepository<CommentReply, Integer> {
	
	List<CommentReply> findByUser_UserId(Integer reply_Id);

	
	List<CommentReply> findByCommentArticle_articleId(Integer articleId);
	
	
}
