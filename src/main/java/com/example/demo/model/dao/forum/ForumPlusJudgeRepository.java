package com.example.demo.model.dao.forum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.forum.ForumPlusJudge;
@Repository
public interface ForumPlusJudgeRepository extends JpaRepository<ForumPlusJudge, Integer> {

	@Query(value = "select a.forumId,a.forumUpdateTime,a.[text],a.[textGood],a.[textBad] ,a.title,a.checkCount,\r\n"
			+ "a.forumType,a.userName,a.liveDateStart,a.liveDateEnd,a.fk_User_Id as userId,ForumJudgeId,\r\n"
			+ "		 b.judgeArticleUserId ,judgeResult,textBad,textGood,reportResult,storeResult\r\n"
			+ "		 from (select * from Forum where forumId=:forumId) a  left join (select * from ForumJudge where judgeForumUserId = :judgeForumUserId  ) b on \r\n"
			+ "		a.articleId = b.fk_article_Id  ", nativeQuery = true)
	 List<ForumPlusJudge> findForumByForumIdAndJudgeForumUserId(@Param("forumId") Integer forumId,@Param("judgeForumUserId") Integer judgeForumUserId);

}
