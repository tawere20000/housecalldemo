package com.example.demo.model.dao.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.comment.CommentArticle;


@Repository
public interface CommentArticleRepository extends JpaRepository<CommentArticle, Integer>,PagingAndSortingRepository<CommentArticle, Integer> {
	
	List<CommentArticle> findByUser_UserId(Integer article_Id);
	
	
	List<CommentArticle> findByTaipeiArea(String taipeiArea);
	//07070707070707070707070707070707070707070707070707070707v0707
	@Query(value = "SELECT TOP(2) * FROM  comment_article ORDER BY text_good_total DESC", nativeQuery = true)
	List<CommentArticle> TextGoodTotalMost();

	@Query(value = "SELECT TOP(2) * FROM  comment_article ORDER BY check_count DESC", nativeQuery = true)
	List<CommentArticle> CheckCountMost();
	
	@Query(value = "SELECT TOP(2) * FROM  comment_article ORDER BY article_id DESC", nativeQuery = true)
    List<CommentArticle> NewMost();

	
	//07070707070707070707070707070707070707070707070707070707v0707
}
