package com.example.demo.model.service.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.comment.CommentArticleTemp;
import com.example.demo.model.dao.comment.CommentArticleTempRepository;



@Service
@Transactional
public class CommentArticleTempService {
	@Autowired
	private CommentArticleTempRepository commentArticleTempRepository;

	public void deleteAllTempCommentArticles() {

		commentArticleTempRepository.deleteAll();
	}

	public void InserAllTempCommentArticles(List<CommentArticleTemp> commentArticleTemp) {

		commentArticleTempRepository.saveAll(commentArticleTemp);

	}

	public List<CommentArticleTemp> findAllTempCommentArticles() {
		List<CommentArticleTemp> commentArticleTemp = commentArticleTempRepository.findAll();


		return commentArticleTemp;
	}

	public void GetIntoTempArticleByTaipeiArea(String taipeiArea) {
//		List<CommentArticleTemp> commentArticleTemp = commentArticleTempRepository
//				.findTempArticleByTaipeiArea(taipeiArea);
		commentArticleTempRepository.GetIntoTempArticleByTaipeiArea(taipeiArea);

	};

	public void GetIntoTempArticle() {
//		List<CommentArticleTemp> commentArticleTemp = commentArticleTempRepository
//				.findAllTempArticle();

		commentArticleTempRepository.GetIntoTempArticle();
	};
	
	
	public Page<CommentArticleTemp> PageForCommentArticleTemp(int pageNo, int pageSize){
		Pageable pageable = PageRequest.of(pageNo-1,pageSize,Sort.by("articleId").descending());
		return commentArticleTempRepository.findAll(pageable);
	}
	//070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705
	public void GetIntoTempArticleByUserId(Integer fk_User_Id) {
		commentArticleTempRepository.GetIntoTempArticleByUserId(fk_User_Id);

	};
	//070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705

}
