package com.example.demo.model.bean.forum;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DynamicInsert
@DynamicUpdate
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ForumJudge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer forumJudgeId;
	@Column(nullable=false)
	private Integer judgeForumUserId;
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_forum_Id")
	private ForumBean forumBean;
	@Column(nullable=true)
	private Integer textGood=0;
	@Column(nullable=true)
	private Integer textBad=0;
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = true)
	@UpdateTimestamp
	private Date forumJudgeUpdateTime;
	

	
}
