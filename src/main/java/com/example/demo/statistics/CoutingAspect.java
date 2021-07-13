package com.example.demo.statistics;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


//@Aspect
//@Component
public class CoutingAspect {
	
//	@Autowired
//	RedisTemplate<String,Object> redisTemplate;
//	@Autowired
//	HttpSession session;
//	
//	
//	public void zsetcountplusone(String key,String value) {
//		if(redisTemplate.hasKey(key)) {
//			redisTemplate.opsForZSet().incrementScore(key,value, 1);
//		}else {
//			redisTemplate.opsForZSet().add(key, value, 1);
//		}
//	}
//	
//	public void hashcountplusone(String key ,String hashKey) {
//		if(redisTemplate.hasKey(key)) {
//			if(redisTemplate.opsForHash().hasKey(key, hashKey)) {
//				redisTemplate.opsForHash().increment(key, hashKey, 1);
//			}
//		}else {
//			redisTemplate.opsForHash().put(key, hashKey,1);
//		}
//	}
//	
//	public void stringcountplusone(String key) {
//		String keyfortodayregist = key;
//		if(redisTemplate.hasKey(keyfortodayregist)) {
//			redisTemplate.opsForValue().increment(keyfortodayregist);
//		}else {
//			redisTemplate.opsForValue().set(keyfortodayregist,1);
//		}
//	}
//	
//	public void countingVisitor(String area) {
//		String keyforvisittoday = "totalvisit";
//		String valueforvisitplace = area;
//		zsetcountplusone(keyforvisittoday,valueforvisitplace);
//		
//		Integer userId = (Integer) session.getAttribute("LoginID");
//		String category = (String) session.getAttribute("Category");
//		if(userId!=null) {
//			//session取得是否為廠商登入或一般用戶
//		String keyforuservisittoday ="uservisit:"+userId;
//		String valueforuservisitplace = area;
//		hashcountplusone(keyforuservisittoday,valueforuservisitplace);
//			if(category.equals("會員")) {
//				stringcountplusone("user");
//			}else if(category.equals("廠商")) {
//				stringcountplusone("vendor");
//			}
//		}
//	}
//	
//	@After("execution(public * com.example.demo.controller.MallController.sellingList(..))")
//	public void countingMallVisitor() {
//		countingVisitor("mallVisit");
//	}
//	
//	@After("execution(public * com.example.demo.controller.ArticleController.Articlelist(..))")
//	public void countingCommentVisitor() {
//		countingVisitor("articleVisit");
//	}
//	
//	@After("execution(public * com.example.demo.controller.MainController.paged(..))")
//	public void countingForumVisitor() {
//		countingVisitor("forumVisit");
//	}
//	
//	@After("execution(public * com.example.demo.controller.TroubleController.list(..))")
//	public void countingTroubleVisitor() {
//		countingVisitor("troublesVisit");
//	}
//	
//	@After("execution(public * com.example.demo.controller.PersonController.addUser(..))")
//	public void countingNewRegist() {
//		stringcountplusone("todayregist");
//	}
//	
//	@After("execution(public * com.example.demo.controller.VendorController.addUser(..))")
//	public void countingNewRegistVendor() {
//		stringcountplusone("todayregist");
//	}

}
