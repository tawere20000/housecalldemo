package com.example.demo.model.bean.forum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumReplyPlusJudge {
	@Id
	private Integer replyId;
	@Column(nullable = true)
	private Integer forumId;
	@Column(nullable = true)
	private Integer userId;
	@Column(nullable = true)
	private String text;
	@Column(nullable = true)
	private Date replyUpdateTime;
//	@Column(nullable=true)
//	private String forumType;
//	@Column(nullable=true)
//	private String title;
	@Column(nullable = true)
	private Integer textGoodTotal;
	@Column(nullable = true)
	private Integer textBadTotal;
//	@Column(nullable=true)
//	private Integer checkCount;
	@Column(nullable = true)
	private String userName;
	@Column(nullable = true)
	private Integer textType;
	@Column(nullable = true)
	private Integer replyJudgeId;
	@Column(nullable = true)
	private Integer judgeReplyUserId;
	@Column(nullable = true)
	private Integer textGood;
	@Column(nullable = true)
	private Integer textBad;
	@Column(nullable = true)
	private Integer judgeResult;
	@Column(nullable = true)
	private Integer reportResult;

}
