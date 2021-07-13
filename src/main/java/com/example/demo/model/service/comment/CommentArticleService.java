package com.example.demo.model.service.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.comment.CommentArticle;
import com.example.demo.model.dao.comment.CommentArticleRepository;




@Service
@Transactional
public class CommentArticleService {
	
	@Autowired
	private CommentArticleRepository commentArticleRepository;
	
	public List<CommentArticle> findAllCommentArticles() {
		return commentArticleRepository.findAll();
	}

	public void postNewArticle(CommentArticle commentArticle) {

		commentArticleRepository.save(commentArticle);
	}
	public CommentArticle getArticleById(int article_Id) {

		return commentArticleRepository.findById(article_Id).get();
	}
	public void updateCommentArticle(CommentArticle commentArticle) {
		commentArticleRepository.save(commentArticle);
	}
	public void deleteCommentArticleById(Integer article_Id) {
		commentArticleRepository.deleteById(article_Id);
	}
//	public Page<CommentArticle> getCommentArticleListByPage(int pageNum, int pageSize) {
//	  Sort sort =Sort.by(Sort.Direction.ASC , "ID");
//	  Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
//	  Page<CommentArticle> commentArticle = commentArticleRepository.findAll(pageable);
//	  return commentArticle;
//	 }
	public Page<CommentArticle> pageForInventory(int pageNo, int pageSize){
		Pageable pageable = PageRequest.of(pageNo-1,pageSize,Sort.by("productId").ascending());
		return commentArticleRepository.findAll(pageable);
	}
	
	public List<CommentArticle> GetCommentArticlesByTaipeiArea(String taipeiArea) {
		return commentArticleRepository.findByTaipeiArea(taipeiArea);
	}
	//0707070707070707070707070707070707070707070707070707070707070707070707070707
	public List<CommentArticle> GetTextGoodTotalMost() {
		return commentArticleRepository.TextGoodTotalMost();
	}
	
	public List<CommentArticle> GetCheckCountMost() {
		return commentArticleRepository.CheckCountMost();
	}
	
	
	public List<CommentArticle> GetNewMost() {
        return commentArticleRepository.NewMost();
    }
	//0707070707070707070707070707070707070707070707070707070707070707070707070707
	
	
}
