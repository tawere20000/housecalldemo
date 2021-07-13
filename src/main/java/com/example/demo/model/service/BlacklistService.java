package com.example.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.Blacklist;
import com.example.demo.model.dao.BlacklistRepository;


@Service
@Transactional
public class BlacklistService {
	@Autowired
	private BlacklistRepository blacklistRepository;
	
	public List<Blacklist> findAllBlacklist() {
		return blacklistRepository.findAll();
	}

	public void postNewBlacklist(Blacklist blacklist) {

		blacklistRepository.save(blacklist);
	}
	public Blacklist getArticleById(Integer blacklistId) {

		return blacklistRepository.findById(blacklistId).get();
	}
	public void updateBlacklist(Blacklist blacklist) {
		blacklistRepository.save(blacklist);
	}
	public void deleteBlacklistById(Integer blacklistId) {
		blacklistRepository.deleteById(blacklistId);
	}
	
	public Blacklist getArticleByIdAndUserId(Integer iD,Integer userId) {

		return blacklistRepository.findByUser_UserIdAndBlackedUser_UserId(iD, userId).get(0);
	}
	
	public boolean BlacklistExists(Integer iD,Integer userId) {
		List<Blacklist> blacklist = blacklistRepository.findByUser_UserIdAndBlackedUser_UserId(iD, userId);
		if (blacklist.isEmpty()) {
			return false;
		}
		return true;
	}
}
