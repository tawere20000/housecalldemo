package com.example.demo.model.bean.comment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

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
public class CommentArticlePlusJudge {
	@Id
	private Integer articleId;
	@Column(nullable=true)
	private Integer userId;
	@Column(nullable=true,columnDefinition = "varchar(max)")
	private String text;
	@Column(nullable=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd ")
	private Date liveDateStart;
	@Column(nullable=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd ")
	private Date liveDateEnd;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Column(nullable=true)
	private Date articleUpdateTime;
	@Column(nullable=true)
	private String taipeiArea;
	@Column(nullable=true)
	private String title;
	@Column(nullable=true)
	private Integer textGoodTotal;
	@Column(nullable=true)
	private Integer textBadTotal;
	@Column(nullable=true)
	private Integer checkCount;
	@Column(nullable=true,columnDefinition = "varchar(max)")
	private String address;
	@Column(nullable=true)
	private String  userName ; 
	@Column(nullable=true)
	private Integer textType;
	@Column(nullable=true)
	private Integer articleJudgeId;
	@Column(nullable=true)
	private Integer judgeArticleUserId;
	@Column(nullable=true)
	private Integer textGood;
	@Column(nullable=true)
	private Integer textBad;
	@Column(nullable=true)
	private Integer judgeResult;
	@Column(nullable=true)
	private Integer reportResult;
	@Column(nullable=true)
	private Integer storeResult;
	@Column(nullable=true)
	private Integer blackResult;
	@Column(nullable=true)
	private Integer checkResult;
	@Column(nullable=true)
	private Date articleJudgeUpdateTime;
	
}
