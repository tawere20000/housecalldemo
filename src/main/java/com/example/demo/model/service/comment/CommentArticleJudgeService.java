package com.example.demo.model.service.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.comment.CommentArticleJudge;
import com.example.demo.model.dao.comment.CommentArticleJudgeRepository;


@Service
@Transactional
public class CommentArticleJudgeService {

	@Autowired
	private CommentArticleJudgeRepository commentArticleJudgeRepository;

	public List<CommentArticleJudge> findAllCommentArticleJudgeJudges() {
		return commentArticleJudgeRepository.findAll();
	}

	public void InsertNewArticleJudge(CommentArticleJudge commentArticleJudge) {

		commentArticleJudgeRepository.save(commentArticleJudge);
	}

	public CommentArticleJudge getArticleById(int article_Id) {

		return commentArticleJudgeRepository.findById(article_Id).get();
	}

	public void updateCommentArticleJudge(CommentArticleJudge commentArticleJudge) {
		commentArticleJudgeRepository.save(commentArticleJudge);
	}

	public void deleteCommentArticleJudgeById(Integer article_Id) {
		commentArticleJudgeRepository.deleteById(article_Id);
	}

	public Integer GetArticleByTextGoodAndArticleId(Integer textGood,Integer articleId) {
		return commentArticleJudgeRepository
				.countByTextGoodAndCommentArticle_articleId(textGood, articleId);

	};

	public Integer GetArticleByTextBadAndArticleId(Integer textBad,Integer articleId) {
		return commentArticleJudgeRepository
				.countByTextBadAndCommentArticle_articleId(textBad, articleId);

	};

	public CommentArticleJudge GetArticleJudgeResaultByUserIdAndArticleId(Integer judgeArticleUserId,Integer articleId) {
		return commentArticleJudgeRepository.findByJudgeArticleUserIdAndCommentArticle_articleId(judgeArticleUserId, articleId).get(0);

	};
	public boolean ArticleJudgeExists(Integer judgeArticleUserId,Integer articleId) {
		List<CommentArticleJudge> commentArticleJudge = commentArticleJudgeRepository.findByJudgeArticleUserIdAndCommentArticle_articleId(judgeArticleUserId, articleId);
		if (commentArticleJudge.isEmpty()) {
			return false;
		}
		return true;
	}
	
}
