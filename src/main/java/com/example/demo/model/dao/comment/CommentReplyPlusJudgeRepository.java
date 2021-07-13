package com.example.demo.model.dao.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.comment.CommentReplyPlusJudge;

@Repository
public interface CommentReplyPlusJudgeRepository extends JpaRepository<CommentReplyPlusJudge, Integer>,PagingAndSortingRepository<CommentReplyPlusJudge, Integer>{

	@Query(value = "select a.[reply_id],a.[reply_update_time],a.[text],a.[text_bad_total],a.[text_good_total],\r\n"
			+ "a.[text_type],a.[user_name],a.[fk_article_id] as [article_id] ,a.[fk_user_id]as [user_id] ,b.[reply_judge_id],\r\n"
			+ "b.[judge_reply_article_id],b.[judge_reply_user_id],b.[judge_result],b.[report_result],b.[text_bad],b.[text_good]"
			+"from (select * from comment_reply where fk_article_id=:fk_article_id) a  left join (select * from comment_reply_judge where judge_reply_user_id = :judge_reply_user_id  ) b on \r\n"
			+ "		a.reply_id = b.fk_reply_id  ", nativeQuery = true)
	 List<CommentReplyPlusJudge> findReplyByArticleIdAndJudgeReplyUserId(@Param("fk_article_id") Integer fk_article_id,@Param("judge_reply_user_id") Integer judge_reply_user_id);
}
