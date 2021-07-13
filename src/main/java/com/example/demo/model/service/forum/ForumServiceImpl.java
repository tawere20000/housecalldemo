package com.example.demo.model.service.forum;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.model.bean.forum.ForumBean;
import com.example.demo.model.bean.forum.ForumReplyBean;
import com.example.demo.model.dao.forum.ForumReplyRepository;
import com.example.demo.model.dao.forum.ForumRepository;


@Transactional
@Service
public class ForumServiceImpl implements ForumService {
	@Autowired
	ForumRepository forumDao;

	@Autowired
	ForumReplyRepository mDao;
	
        
        
        

	@Override
	public List<ForumBean> getAllForums() {
		return forumDao.findAll();
	}

	@Override
	public ForumBean save(ForumBean bean) {
		return forumDao.save(bean);
	}

	@Override
	public ForumBean getForumById(Integer id) {
		return forumDao.findById(id).get();
	}

	@Override
	public ForumBean updateForum(ForumBean bean) {
		return forumDao.save(bean);
	}

	@Override
	public void deleteForumByPrimaryKey(Integer id) {
		forumDao.deleteById(id);
	}

//	@Override
//	public List<Forum> getAllmessages(Integer id) {
//		forumDao.findById(id);
//
//		return null;
//	}
	
	//留言
	@Override
	public List<ForumReplyBean> findAllMessages(Integer forumId) {
		return mDao.findByForumBean_forumId(forumId);
	}

	@Override
	public ForumReplyBean saveMessage(ForumReplyBean bean) {
		return mDao.save(bean);
	}

	@Override
	public ForumReplyBean getMessageById(Integer id) {

		return mDao.findById(id).get();
	}

	@Override
	public ForumReplyBean updateMessage(ForumReplyBean bean) {
		return mDao.save(bean);
	}

	@Override
	public void deleteMessageByPrimaryKey(Integer id) {
		mDao.deleteById(id);
	}

	@Override
	public List<ForumReplyBean> getAllmessages(Integer id) {
		return mDao.findAllById(null);
	}




	@Override
	public List<ForumBean> getView() {
	
		return forumDao.CheckCountMost();
	}




	@Override
	public List<ForumBean> getTime() {
		return forumDao.CheckRecentMost();
	}




	@Override
	public Page<ForumBean> findAll(Integer pageNo, Integer pageSize) {
		
		
		Pageable pageable=PageRequest.of(pageNo-1, pageSize,Sort.by("forumId").descending());
		return this.forumDao.findAll(pageable);
	}


}
