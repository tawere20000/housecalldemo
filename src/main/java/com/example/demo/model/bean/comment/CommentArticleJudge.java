package com.example.demo.model.bean.comment;

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
public class CommentArticleJudge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer articleJudgeId;
	@Column(nullable=false)
	private Integer judgeArticleUserId;
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_Article_Id")
	private CommentArticle commentArticle;
	@Column(nullable=false)
	private Integer textGood;
	@Column(nullable=false)
	private Integer textBad;
	@Column(nullable=false)
	private Integer judgeResult;
	@Column(nullable=true)
	private Integer reportResult;
	@Column(nullable=true)
	private Integer storeResult;
	//0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改
	@Column(nullable=true)
	private Integer blackResult;
	@Column(nullable=true)
	private Integer checkResult;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = true)
	@UpdateTimestamp
	private Date articleJudgeUpdateTime;
	//0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改0705改	
	
	
}
