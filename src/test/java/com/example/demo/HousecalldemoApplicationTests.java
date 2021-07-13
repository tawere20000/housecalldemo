package com.example.demo;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.demo.controller.BackEndController;
import com.example.demo.model.bean.user.Personal_InformationBean;
import com.example.demo.model.bean.user.UserBean;
import com.example.demo.model.dao.statistics.UserBehaviorComponentsDao;
import com.example.demo.model.service.comment.CommentArticleService;

@SpringBootTest
class HousecalldemoApplicationTests {
	
//	@Autowired
//	private JavaMailSender mailSender;
//	
//	@Autowired
//	private UserBeanRepository userBeanRepository;
//	
//	@Autowired
//	private Personal_InformationRepository personal_InformationRepository;
//	
//	@Autowired
//	RedisTemplate<String,Object> redisTemplate;
//	
//	@Autowired
//	UserBehaviorComponentsDao  userBehaviorComponentsDao;
//	
//	
//	public Set<String> keyScan(String pattern){
//		Set<String> execute = redisTemplate.execute(new RedisCallback<Set<String>>() {
//
//			@Override
//			public Set doInRedis(RedisConnection connection) throws DataAccessException {
//				Set<String> keys = new HashSet<String>();
//				Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(pattern).count(10).build());
//				while(cursor.hasNext()) {
//					keys.add(new String(cursor.next(),StandardCharsets.UTF_8));
//				}
//				return keys;
//			}
//			
//		});
//		return execute;
//	}
//
//	@Test
//	void contextLoads() {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom("housecalltw@gmail.com");
//		message.setTo("tawere20000@yahoo.com.tw");
//		message.setSubject("米奇不妙屋檢舉審核通知");
//		message.setText("親愛的用戶您好！\n您於"+
//				",\n因...,該內容遭刪除,特此來信通知\n祝您有個美好的一天\n米奇不妙屋後台管理");
//		mailSender.send(message);
//	}
//	
//	@Test
//	void test1() {
//		List<Object[]> list = userBehaviorComponentsDao.findlastdays("2021-06-01","2021-06-07","totalvisit_times");
//		Iterator<Object[]> it = list.iterator();
//		while(it.hasNext()) {
//			System.out.println(it.next()[0]);
//		}
//	}
	

}
