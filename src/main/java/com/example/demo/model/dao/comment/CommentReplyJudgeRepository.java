package com.example.demo.model.dao.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.comment.CommentReplyJudge;


@Repository
public interface CommentReplyJudgeRepository extends JpaRepository<CommentReplyJudge, Integer> {

	Integer countByTextGoodAndCommentReply_ReplyId(Integer textGood, Integer ReplyId);

	Integer countByTextBadAndCommentReply_ReplyId(Integer textBad, Integer ReplyId);

	List<CommentReplyJudge> findByJudgeReplyUserIdAndCommentReply_ReplyId(Integer judgeReplyUserId, Integer ReplyId);

//	@Query(value = "select a.replyUpdateTime,a.text,a.textGoodTotal,a.textBadTotal,a.taipeiArea,"
//			+ "a.textType,a.userName,a.fk_Article_Id as articleId,a.fk_User_Id as userId,b.replyJudgeId,"
//			+ " b.judgeReplyUserId,b.judgeResult,b.textBad,b.textGood,"
//			+ "a.replyId from CommentReply a left join CommentReplyJudge b on "
//			+ "a.replyId = b.fk_Reply_Id where a.fk_Article_Id = :fk_Article_Id and b.judgeReplyUserId=:judgeReplyUserId ", nativeQuery = true)
//	List<CommentReplyJudge> findReplyByCommentReply_CommentArticle_ArticleIdAndJudgeReplyUserId(@Param("fk_Article_Id") Integer fk_Article_Id,@Param("judgeReplyUserId") Integer judgeReplyUserId);
}
