package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Configuration
public class RedisConfig {
	
	//自定義template
//	@SuppressWarnings("deprecation")
//	@Bean
//	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
//		RedisTemplate<String,Object> template= new RedisTemplate<String,Object>();
//		template.setConnectionFactory(redisConnectionFactory);
//		
//		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
//		ObjectMapper om = new ObjectMapper();
//		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//		jackson2JsonRedisSerializer.setObjectMapper(om);
//		
//		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//		template.setKeySerializer(stringRedisSerializer);
//		template.setHashKeySerializer(stringRedisSerializer);
//		template.setValueSerializer(jackson2JsonRedisSerializer);
//		template.setHashValueSerializer(jackson2JsonRedisSerializer);
//		
//		template.afterPropertiesSet();
//		
//		return template;
//		
//	}

}
