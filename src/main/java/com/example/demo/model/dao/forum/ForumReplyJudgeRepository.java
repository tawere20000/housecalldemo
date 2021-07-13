package com.example.demo.model.dao.forum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.forum.ForumReplyJudge;

@Repository
public interface ForumReplyJudgeRepository extends JpaRepository<ForumReplyJudge, Integer> {

	Integer countByTextGoodAndForumReplyBean_ReplyId(Integer textGood, Integer ReplyId);

	Integer countByTextBadAndForumReplyBean_ReplyId(Integer textBad, Integer ReplyId);

	List<ForumReplyJudge> findByJudgeReplyUserIdAndForumReplyBean_ReplyId(Integer judgeReplyUserId, Integer ReplyId);

}
