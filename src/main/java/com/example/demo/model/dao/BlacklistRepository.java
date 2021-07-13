package com.example.demo.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.Blacklist;


@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist, Integer> {

////	@Query("select b from Blacklist b where b.")
	List<Blacklist> findByUser_UserId(Integer iD);
	
	void deleteByBlackedUser_userId(Integer id);
	

	List<Blacklist> findByUser_UserIdAndBlackedUser_UserId(Integer iD,Integer userId);
}
