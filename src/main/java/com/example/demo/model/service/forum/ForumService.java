package com.example.demo.model.service.forum;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.model.bean.forum.ForumBean;
import com.example.demo.model.bean.forum.ForumReplyBean;

public interface ForumService {
	List<ForumBean> getAllForums();
	//=================================
//	List<ForumBean> getAllForumType();
	//=================================
	List<ForumReplyBean> getAllmessages(Integer id);
	
	Page<ForumBean>findAll(Integer pageNo,Integer pageSize);
	
	List<ForumBean> getView();
	
	List<ForumBean> getTime();
	

	ForumBean save(ForumBean bean);

	ForumBean getForumById(Integer id);

	ForumBean updateForum(ForumBean bean);

	void deleteForumByPrimaryKey(Integer id);
	
	ForumReplyBean saveMessage(ForumReplyBean mbean);
	
	ForumReplyBean getMessageById(Integer id);

	ForumReplyBean updateMessage(ForumReplyBean mbean);

	void deleteMessageByPrimaryKey(Integer id);
	
	List<ForumReplyBean> findAllMessages(Integer forumId);
//	Page<Message> findByForumId(Pageable pageable);

	
}
