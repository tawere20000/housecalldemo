package com.example.demo.model.dao.forum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.forum.ForumJudge;
@Repository
public interface ForumJudgeRepository extends JpaRepository<ForumJudge, Integer> {

	Integer countByTextGoodAndForumBean_forumId(Integer textGood,Integer forumId);
	
	Integer countByTextBadAndForumBean_forumId(Integer textBad,Integer forumId);
	
	List<ForumJudge> findByJudgeForumUserIdAndForumBean_forumId(Integer judgeForumUserId,Integer forumId);
}
