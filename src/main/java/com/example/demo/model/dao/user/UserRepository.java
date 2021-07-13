package com.example.demo.model.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.user.UserBean;


@Repository
public interface UserRepository extends JpaRepository<UserBean, Integer> {

	// 檢查帳號有沒有重複
	List<UserBean> findByAccount(String account);

//	// 登入檢查帳密對不對
//	List<UserBean> findByAccountAndPassword(String account , String password);
	
//	//
//	List<Blacklist> findByUser_ID(Integer iD);
	
	UserBean findByAccountAndPassword(String account, String password);
	

}
