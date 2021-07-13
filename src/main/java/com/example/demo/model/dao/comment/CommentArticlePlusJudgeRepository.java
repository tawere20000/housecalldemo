package com.example.demo.model.dao.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.comment.CommentArticlePlusJudge;

@Repository
public interface CommentArticlePlusJudgeRepository extends JpaRepository<CommentArticlePlusJudge, Integer> {

	@Query(value = "select a.[article_id],a.[address] ,a.[article_update_time],a.[check_count],a.[live_date_end]\r\n"
			+ "      ,a.[live_date_start],a.[taipei_area],a.[text],a.[text_bad_total],a.[text_good_total]\r\n"
			+ "      ,a.[text_type],a.[title],a.[user_name],a.[fk_user_id] as [user_id]\r\n"
			+ "	  ,b.[article_judge_id],b.[article_judge_update_time],b.[black_result],b.[check_result]\r\n"
			+ "      ,b.[judge_article_user_id],b.[judge_result],b.[report_result],b.[store_result]\r\n"
			+ "      ,b.[text_bad],b.[text_good] from (select * from comment_article where article_id=:article_id) a  left join (select * from comment_article_judge where judge_article_user_id = :judge_article_user_id  ) b on \r\n"
			+ "		a.article_id = b.fk_article_Id  ", nativeQuery = true)
	 List<CommentArticlePlusJudge> findArticleByArticleIdAndJudgeArticleUserId(@Param("article_id") Integer article_id,@Param("judge_article_user_id") Integer judge_article_user_id);

	
	
	//070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705
	@Transactional
    @Modifying
	@Query(value = "INSERT INTO comment_article_plus_judge ([article_id],[address],[article_update_time],[check_count],[live_date_end]\r\n"
			+ "		   ,[live_date_start],[taipei_area],[text],[text_bad_total],[text_good_total]\r\n"
			+ "		   ,[text_type],[title],[user_name],[user_id]\r\n"
			+ "		   ,[article_judge_id],[article_judge_update_time],[black_result],[check_result]\r\n"
			+ "           ,[judge_article_user_id],[judge_result],[report_result],[store_result]\r\n"
			+ "           ,[text_bad],[text_good] ) select a.[article_id],a.[address] ,a.[article_update_time],a.[check_count],a.[live_date_end]\r\n"
			+ "      ,a.[live_date_start],a.[taipei_area],a.[text],a.[text_bad_total],a.[text_good_total]\r\n"
			+ "      ,a.[text_type],a.[title],a.[user_name],a.[fk_user_id] as [user_id]\r\n"
			+ "	  ,b.[article_judge_id],b.[article_judge_update_time],b.[black_result],b.[check_result]\r\n"
			+ "      ,b.[judge_article_user_id],b.[judge_result],b.[report_result],b.[store_result]\r\n"
			+ "      ,b.[text_bad],b.[text_good] from (select * from comment_article) a  left join  (select * from comment_article_judge  where judge_article_user_id =:judge_article_user_id) b on \r\n"
			+ "						 a.article_id = b.fk_article_id    where check_result=1     ", nativeQuery = true)
	public void GetIntoArticleByUserIdAndJudgeArticleUserIdAndCheckResult(@Param("judge_article_user_id") Integer judge_article_user_id);
	@Transactional
    @Modifying
	@Query(value = "INSERT INTO comment_article_plus_judge ([article_id],[address],[article_update_time],[check_count],[live_date_end]\r\n"
			+ "		   ,[live_date_start],[taipei_area],[text],[text_bad_total],[text_good_total]\r\n"
			+ "		   ,[text_type],[title],[user_name],[user_id]\r\n"
			+ "		   ,[article_judge_id],[article_judge_update_time],[black_result],[check_result]\r\n"
			+ "           ,[judge_article_user_id],[judge_result],[report_result],[store_result]\r\n"
			+ "           ,[text_bad],[text_good] ) select a.[article_id],a.[address] ,a.[article_update_time],a.[check_count],a.[live_date_end]\r\n"
			+ "      ,a.[live_date_start],a.[taipei_area],a.[text],a.[text_bad_total],a.[text_good_total]\r\n"
			+ "      ,a.[text_type],a.[title],a.[user_name],a.[fk_user_id] as [user_id]\r\n"
			+ "	  ,b.[article_judge_id],b.[article_judge_update_time],b.[black_result],b.[check_result]\r\n"
			+ "      ,b.[judge_article_user_id],b.[judge_result],b.[report_result],b.[store_result]\r\n"
			+ "      ,b.[text_bad],b.[text_good] from (select * from comment_article) a  left join  (select * from comment_article_judge  where judge_article_user_id =:judge_article_user_id) b on \r\n"
			+ "						 a.article_id = b.fk_Article_Id    where store_result=1   ", nativeQuery = true)
	public void GetIntoArticleByUserIdAndJudgeArticleUserIdAndStoreResult(@Param("judge_article_user_id") Integer judge_article_user_id);
	@Transactional
    @Modifying
	@Query(value = "INSERT INTO comment_article_plus_judge ([article_id],[address],[article_update_time],[check_count],[live_date_end]\r\n"
			+ "		   ,[live_date_start],[taipei_area],[text],[text_bad_total],[text_good_total]\r\n"
			+ "		   ,[text_type],[title],[user_name],[user_id]\r\n"
			+ "		   ,[article_judge_id],[article_judge_update_time],[black_result],[check_result]\r\n"
			+ "           ,[judge_article_user_id],[judge_result],[report_result],[store_result]\r\n"
			+ "           ,[text_bad],[text_good] ) select a.[article_id],a.[address] ,a.[article_update_time],a.[check_count],a.[live_date_end]\r\n"
			+ "      ,a.[live_date_start],a.[taipei_area],a.[text],a.[text_bad_total],a.[text_good_total]\r\n"
			+ "      ,a.[text_type],a.[title],a.[user_name],a.[fk_user_id] as [user_id]\r\n"
			+ "	  ,b.[article_judge_id],b.[article_judge_update_time],b.[black_result],b.[check_result]\r\n"
			+ "      ,b.[judge_article_user_id],b.[judge_result],b.[report_result],b.[store_result]\r\n"
			+ "      ,b.[text_bad],b.[text_good] from (select * from comment_article) a  left join  (select * from comment_article_judge  where judge_article_user_id =:judge_article_user_id) b on \r\n"
			+ "						 a.article_id = b.fk_Article_Id    where text_good=1      ", nativeQuery = true)
	public void GetIntoArticleByUserIdAndJudgeArticleUserIdAndTextGood(@Param("judge_article_user_id") Integer judge_article_user_id);
	@Transactional
    @Modifying
	@Query(value ="INSERT INTO comment_article_plus_judge ([article_id],[address],[article_update_time],[check_count],[live_date_end]\r\n"
			+ "		   ,[live_date_start],[taipei_area],[text],[text_bad_total],[text_good_total]\r\n"
			+ "		   ,[text_type],[title],[user_name],[user_id]\r\n"
			+ "		   ,[article_judge_id],[article_judge_update_time],[black_result],[check_result]\r\n"
			+ "           ,[judge_article_user_id],[judge_result],[report_result],[store_result]\r\n"
			+ "           ,[text_bad],[text_good] ) select a.[article_id],a.[address] ,a.[article_update_time],a.[check_count],a.[live_date_end]\r\n"
			+ "      ,a.[live_date_start],a.[taipei_area],a.[text],a.[text_bad_total],a.[text_good_total]\r\n"
			+ "      ,a.[text_type],a.[title],a.[user_name],a.[fk_user_id] as [user_id]\r\n"
			+ "	  ,b.[article_judge_id],b.[article_judge_update_time],b.[black_result],b.[check_result]\r\n"
			+ "      ,b.[judge_article_user_id],b.[judge_result],b.[report_result],b.[store_result]\r\n"
			+ "      ,b.[text_bad],b.[text_good] from (select * from comment_article) a  left join  (select * from comment_article_judge  where judge_article_user_id =:judge_article_user_id) b on \r\n"
			+ "						 a.article_id = b.fk_Article_Id   where text_bad=1     ", nativeQuery = true)
	public void GetIntoArticleByUserIdAndJudgeArticleUserIdAndTextBad(@Param("judge_article_user_id") Integer judge_article_user_id);
	@Transactional
    @Modifying
	@Query(value ="INSERT INTO comment_article_plus_judge ([article_id],[address],[article_update_time],[check_count],[live_date_end]\r\n"
			+ "		   ,[live_date_start],[taipei_area],[text],[text_bad_total],[text_good_total]\r\n"
			+ "		   ,[text_type],[title],[user_name],[user_id]\r\n"
			+ "		   ,[article_judge_id],[article_judge_update_time],[black_result],[check_result]\r\n"
			+ "           ,[judge_article_user_id],[judge_result],[report_result],[store_result]\r\n"
			+ "           ,[text_bad],[text_good] ) select a.[article_id],a.[address] ,a.[article_update_time],a.[check_count],a.[live_date_end]\r\n"
			+ "      ,a.[live_date_start],a.[taipei_area],a.[text],a.[text_bad_total],a.[text_good_total]\r\n"
			+ "      ,a.[text_type],a.[title],a.[user_name],a.[fk_user_id] as [user_id]\r\n"
			+ "	  ,b.[article_judge_id],b.[article_judge_update_time],b.[black_result],b.[check_result]\r\n"
			+ "      ,b.[judge_article_user_id],b.[judge_result],b.[report_result],b.[store_result]\r\n"
			+ "      ,b.[text_bad],b.[text_good] from (select * from comment_article) a  left join  (select * from comment_article_judge  where judge_article_user_id =:judge_article_user_id) b on \r\n"
			+ "						 a.article_id = b.fk_Article_Id    where report_result=1    ", nativeQuery = true)
	public void GetIntoArticleByUserIdAndJudgeArticleUserIdAndReportResult(@Param("judge_article_user_id") Integer judge_article_user_id);
	
	


	Page<CommentArticlePlusJudge> findAll(Pageable pageable);
	//070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705070507050705
}
