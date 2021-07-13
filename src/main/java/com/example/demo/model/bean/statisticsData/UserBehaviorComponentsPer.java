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
public class UserBehaviorComponentsPer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ubcpId;
	
	private String date;
	
	private String category;
	
	private Double mallVisitTimesPer;
	
	private Double articleVisitTimesPer;
	
	private Double forumVisitTimesPer;
	
	private Double troublesVisitTimesPer;
	
	private Double totalvisitTimesPer;
	
	

}
