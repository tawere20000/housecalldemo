package com.example.demo.model.service.comment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.comment.CommentArticlePlusJudge;
import com.example.demo.model.dao.comment.CommentArticlePlusJudgeRepository;


@Service
@Transactional
public class CommentArticlePlusJudgeService {
	@Autowired
	private CommentArticlePlusJudgeRepository commentArticlePlusJudgeRepository;
	
	 public CommentArticlePlusJudge GetArticleAndJudge(Integer articleId,Integer judgeArticleUserId) {
		return commentArticlePlusJudgeRepository.findArticleByArticleIdAndJudgeArticleUserId(articleId,judgeArticleUserId).get(0);
		
	};
	//070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705
	public void GetArticleAndCheckJudge(Integer judgeArticleUserId) {
		commentArticlePlusJudgeRepository.GetIntoArticleByUserIdAndJudgeArticleUserIdAndCheckResult(judgeArticleUserId);
			
		};
	public void GetArticleAndStoreJudge(Integer judgeArticleUserId) {
		commentArticlePlusJudgeRepository.GetIntoArticleByUserIdAndJudgeArticleUserIdAndStoreResult(judgeArticleUserId);
		
	};
	public void GetArticleAndGoodJudge(Integer judgeArticleUserId) {
		 commentArticlePlusJudgeRepository.GetIntoArticleByUserIdAndJudgeArticleUserIdAndTextGood(judgeArticleUserId);
		
	};
	public void GetArticleAndBadJudge(Integer judgeArticleUserId) {
		 commentArticlePlusJudgeRepository.GetIntoArticleByUserIdAndJudgeArticleUserIdAndTextBad(judgeArticleUserId);
		
	};
	public void GetArticleAndReportJudge(Integer judgeArticleUserId) {
		 commentArticlePlusJudgeRepository.GetIntoArticleByUserIdAndJudgeArticleUserIdAndReportResult(judgeArticleUserId);
		
	};
	
	public Page<CommentArticlePlusJudge> PageForCommentArticlePlusJudge(int pageNo, int pageSize){
		Pageable pageable = PageRequest.of(pageNo-1,pageSize,Sort.by("articleJudgeUpdateTime").descending());
		return commentArticlePlusJudgeRepository.findAll(pageable);
	}
	
	public void deleteAllArticlePlusJudge() {

		commentArticlePlusJudgeRepository.deleteAll();
	}
	//070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705
}

