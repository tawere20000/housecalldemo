package com.example.demo.model.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.user.UserBean;
import com.example.demo.model.dao.user.UserRepository;


@Service
@Transactional
public class UserBeanService {
	
	@Autowired
	private UserRepository userBeanRepository ;
	
	public Page<UserBean> pageForUserBean(int pageNo, int pageSize){
		Pageable pageable = PageRequest.of(pageNo-1, pageSize, Sort.by("userId").descending());
		return userBeanRepository.findAll(pageable);
	}
	
	public UserBean getUser(Integer userId) {
		return userBeanRepository.findById(userId).get();
	}
	
	public UserBean getUserByAccountAndPassword(String account,String password) {
		return userBeanRepository.findByAccountAndPassword(account, password);
	}
	
}
