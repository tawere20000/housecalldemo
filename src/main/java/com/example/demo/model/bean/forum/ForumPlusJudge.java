package com.example.demo.model.bean.forum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForumPlusJudge {
	@Id
	private Integer forumId;
	@Column(nullable=true)
	private Integer userId;
	@Column(nullable=true)
	private String text;
	@Column(nullable=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date liveDateStart;
	@Column(nullable=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date liveDateEnd;
	@Column(nullable=true)
	private Date forumUpdateTime;
	@Column(nullable=true)
	private String title;
	@Column(nullable=true)
	private Integer textGoodTotal;
	@Column(nullable=true)
	private Integer textBadTotal;
	@Column(nullable=true)
	private Integer checkCount;
	@Column(nullable=true)
	private String  userName ; 
	@Column(nullable=true)
	private Integer textType;
	@Column(nullable=true)
	private Integer forumJudgeId;
	@Column(nullable=true)
	private Integer judgeForumUserId;
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

}
