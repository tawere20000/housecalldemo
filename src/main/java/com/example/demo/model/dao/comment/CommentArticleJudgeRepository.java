package com.example.demo.model.dao.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.comment.CommentArticleJudge;

@Repository
public interface CommentArticleJudgeRepository extends JpaRepository<CommentArticleJudge, Integer> {

	Integer countByTextGoodAndCommentArticle_articleId(Integer textGood,Integer articleId);
	
	Integer countByTextBadAndCommentArticle_articleId(Integer textBad,Integer articleId);
	
	List<CommentArticleJudge> findByJudgeArticleUserIdAndCommentArticle_articleId(Integer judgeArticleUserId,Integer articleId);
}
