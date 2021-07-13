package com.example.demo.model.bean.statisticsData;

import javax.persistence.Entity;
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
public class Totalvisit {
	
	@Id
	private String date;
	
	private Long mallVisit = 0L;
	
	private Long articleVisit  = 0L;
	
	private Long forumVisit  = 0L;
	
	private Long troublesVisit  = 0L;
	
	private Long totalVisit  = 0L;
	
}
