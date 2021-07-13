package com.example.demo.scheduled;

import java.beans.PropertyDescriptor;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.model.bean.statisticsData.DaysRegister;
import com.example.demo.model.bean.statisticsData.Totalvisit;
import com.example.demo.model.bean.statisticsData.UserBehavior;
import com.example.demo.model.bean.user.UserBean;
import com.example.demo.model.service.statistics.DaysRegisterService;
import com.example.demo.model.service.statistics.TotalvisitService;
import com.example.demo.model.service.statistics.UserBehaviorService;
import com.example.demo.model.service.user.UserBeanService;


//@Component
public class ScheduledTasks {
	
//	@Autowired
//	RedisTemplate<String,Object> redisTemplate;
//	
//	@Autowired
//	TotalvisitService totalvisitService;
//	
//	@Autowired
//	UserBeanService userBeanService;
//	
//	@Autowired
//	UserBehaviorService userBehaviorService;
//	
//	@Autowired
//	DaysRegisterService daysRegisterService;
//	
//	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	Date date = new Date();
//	String today = simpleDateFormat.format(date);
//	
//	
//	public void countViolation() {
//		
//	}
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
//	
//	@Scheduled(cron="0 43 0 * * ?")
//	public void saveStatisticsData() throws Exception{
//		Totalvisit totalvisit = new Totalvisit();
//		totalvisit.setDate(today);
//		Set<TypedTuple<Object>> zsetresult = redisTemplate.opsForZSet().rangeWithScores("totalvisit", 0, -1);
//		for(TypedTuple<Object> result : zsetresult) {
//			PropertyDescriptor pd = new PropertyDescriptor((String)result.getValue(),Totalvisit.class);
//			pd.getWriteMethod().invoke(totalvisit,result.getScore().longValue());
//		}
//		totalvisitService.addTotalVisitData(totalvisit);
//		redisTemplate.delete("totalvisit");
//	}
//	
//	
//	@Scheduled(cron="0 43 0 * * ?")
//	public void saveUserBehavior() throws Exception {
//		Set<String> keys = keyScan("uservisit:*");
//		for(String key : keys) {
//			UserBehavior userBehavior = new UserBehavior();
//			userBehavior.setDate(today);
//			Cursor<Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key,ScanOptions.NONE);
//			while(cursor.hasNext()) {
//				Entry<Object, Object> entry = cursor.next();
//				PropertyDescriptor pd = new PropertyDescriptor((String) entry.getKey(),UserBehavior.class);
//				pd.getWriteMethod().invoke(userBehavior,((Integer)entry.getValue()).longValue());
//			}
//			String userIdstr = key.replace("uservisit:","");
//			UserBean user = userBeanService.getUser(Integer.valueOf(userIdstr));
//			userBehavior.setUserBean(user);
//			userBehaviorService.saveUserBehavior(userBehavior);
//		}
//		
//		redisTemplate.delete(keys);
//	}
//	
//	
//	@Scheduled(cron="0 43 0 * * ?")
//	public void savetodaysNewRegist() {
//	
//		Integer todayregist = (Integer) redisTemplate.opsForValue().get("todayregist");
//		DaysRegister daysRegister = new DaysRegister();
//		daysRegister.setDate(date);
//		daysRegister.setNewRegister(todayregist);
//		daysRegisterService.savetodaysNewRegist(daysRegister);
//		redisTemplate.delete("todayregist");
//	}
//	
//	
//	@Scheduled(cron="0 43 0 * * ?")
//	public void deleteTodayComp() {
//		redisTemplate.delete("user");
//		redisTemplate.delete("vendor");
//	}

}
