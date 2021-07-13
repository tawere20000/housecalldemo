package com.example.demo.model.dao.forum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.forum.ForumReplyPlusJudge;
@Repository
public interface ForumReplyPlusJudgeRepository extends JpaRepository<ForumReplyPlusJudge, Integer>,PagingAndSortingRepository<ForumReplyPlusJudge, Integer>{

	@Query(value = "select a.replyId,a.replyUpdateTime,a.[text],a.[textGoodTotal],a.[textBadTotal] ,\r\n"
			+ "a.textType,a.userName,a.fk_forum_Id as forumId,a.fk_User_Id as userId,replyJudgeId,\r\n"
			+ "		 b.judgeReplyUserId ,judgeResult,textBad,textGood,b.judgeReplyArticleId,reportResult\r\n"
			+ "		 from (select * from ForumReply where fk_Forum_Id=:fk_Forum_Id) a  left join (select * from ForumReplyJudge where judgeReplyUserId = :judgeReplyUserId  ) b on \r\n"
			+ "		a.replyId = b.fk_reply_Id  ", nativeQuery = true)
	 List<ForumReplyPlusJudge> findReplyByForumIdAndJudgeReplyUserId(@Param("fk_Forum_Id") Integer fk_Forum_Id,@Param("judgeReplyUserId") Integer judgeReplyUserId);
}
