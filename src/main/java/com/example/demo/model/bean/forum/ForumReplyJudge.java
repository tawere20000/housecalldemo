package com.example.demo.model.bean.forum;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class ForumReplyJudge {
	

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer replyJudgeId;
		@Column(nullable=false)
		private Integer judgeReplyUserId;
		@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
		@JoinColumn(name = "fk_forumReply_Id")
		private ForumReplyBean forumReplyBean;
//		@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
//		@JoinColumn(name = "fk_Article_Id")
//		private CommentArticle commentArticle;
		@Column(nullable=true)
		private Integer judgeReplyArticleId;
		@Column(nullable=true)
		private Integer textGood=0;
		@Column(nullable=true)
		private Integer textBad=0;
		@Column(nullable=true)
		private Integer judgeResult;
		@Column(nullable=true)
		private Integer reportResult;
		
		



		
	}

