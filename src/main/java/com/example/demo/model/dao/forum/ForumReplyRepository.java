package com.example.demo.model.dao.forum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.forum.ForumReplyBean;

@Repository
public interface ForumReplyRepository extends JpaRepository<ForumReplyBean, Integer> {

	List<ForumReplyBean> findByUser_UserId(Integer Id);

	List<ForumReplyBean> findByForumBean_forumId(Integer Id);
}
