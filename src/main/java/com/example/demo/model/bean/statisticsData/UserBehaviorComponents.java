package com.example.demo.model.bean.statisticsData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBehaviorComponents {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ubcId;
	
	private String date;
	
	private String category;
	
	private Long mallVisitTimes;
	
	private Long articleVisitTimes;
	
	private Long forumVisitTimes;
	
	private Long troublesVisitTimes;
	
	private Long totalvisitTimes;
	
	
}
