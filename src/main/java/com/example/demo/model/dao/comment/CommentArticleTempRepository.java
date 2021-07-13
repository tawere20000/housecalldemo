package com.example.demo.model.dao.comment;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.comment.CommentArticleTemp;

@Repository
public interface CommentArticleTempRepository extends JpaRepository<CommentArticleTemp, Integer> {
	 @Transactional
	    @Modifying
	@Query(value = "INSERT INTO comment_article_temp ([article_id],[address],[article_update_time],[check_count],[live_date_end],[live_date_start]\r\n"
			+ "           ,[taipei_area],[text],[text_bad_total],[text_good_total],[text_type],[title],[user_id],[user_name])"
			+ "    select [article_id],[address],[article_update_time],[check_count],[live_date_end],[live_date_start]"
			+ ",[taipei_area],[text],[text_bad_total],[text_good_total],[text_type],[title],[fk_user_id],[user_name]\r\n"
			+ "           　from comment_article where taipei_area=:taipei_area  ", nativeQuery = true)
	 public void GetIntoTempArticleByTaipeiArea(@Param("taipei_area") String taipei_area);
	
	 @Transactional
	    @Modifying
	@Query(value = "INSERT INTO comment_article_temp ([article_id],[address],[article_update_time],[check_count],[live_date_end],[live_date_start]\r\n"
			+ "           ,[taipei_area],[text],[text_bad_total],[text_good_total],[text_type],[title],[user_id],[user_name])"
			+ "    select [article_id],[address],[article_update_time],[check_count],[live_date_end],[live_date_start]"
			+ ",[taipei_area],[text],[text_bad_total],[text_good_total],[text_type],[title],[fk_user_id],[user_name]\r\n"
			+ "           　from comment_article", nativeQuery = true)
	public void GetIntoTempArticle();
	//070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705 
	 @Transactional
	    @Modifying
	@Query(value = "INSERT INTO comment_article_temp ([article_id],[address],[article_update_time],[check_count],[live_date_end],[live_date_start]\r\n"
			+ "           ,[taipei_area],[text],[text_bad_total],[text_good_total],[text_type],[title],[user_id],[user_name])"
			+ "    select [article_id],[address],[article_update_time],[check_count],[live_date_end],[live_date_start]"
			+ ",[taipei_area],[text],[text_bad_total],[text_good_total],[text_type],[title],[fk_user_id],[user_name]\r\n"
			+ "           　from comment_article where fk_user_id=:fk_user_id  ", nativeQuery = true)
	 public void GetIntoTempArticleByUserId(@Param("fk_user_id") Integer fk_user_id);
	//070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705
	Page<CommentArticleTemp> findAll(Pageable pageable);
}
