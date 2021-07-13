package com.example.demo.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class PointRule implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer actionId;
	@Column(nullable=false)
	private String useraction;
	@Column(nullable=false)
	private String area;
	@Column(nullable=false)
	private String periodrange;
	@Column(nullable=false)
	private String maxNumberOfTimes;
	@Column(nullable=false)
	private String targetuser;
	@Column(nullable=false)
	private Integer points;
	@Column(nullable=false)
	private String remarks;
	

}
