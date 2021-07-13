package com.example.demo.model.dao.forum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.bean.forum.ForumBean;

public interface ForumRepository extends JpaRepository< ForumBean,Integer> {

List<ForumBean>	findByForumId(Integer forumId);
	


List<ForumBean> findByUser_UserId(int iD);	

@Query(value = "SELECT TOP(3) * FROM  forum_bean ORDER BY check_count DESC", nativeQuery = true)
List<ForumBean> CheckCountMost();

@Query(value = "SELECT TOP(3) * FROM  forum_bean ORDER BY forum_id DESC", nativeQuery = true)
List<ForumBean> CheckRecentMost();

}
