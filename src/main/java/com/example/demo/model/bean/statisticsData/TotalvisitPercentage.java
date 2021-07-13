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
public class TotalvisitPercentage {
	
	@Id
	private String date;
	
	private Double mallVisitPer;
	
	private Double articleVisitPer;
	
	private Double forumVisitPer;
	
	private Double troublesVisitPer;
	
	private Double totalVisitPer;

}
