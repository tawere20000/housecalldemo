package com.example.demo.model.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.demo.model.bean.comment.CommentArticle;
import com.example.demo.model.bean.comment.CommentReply;
import com.example.demo.model.bean.forum.ForumBean;
import com.example.demo.model.bean.forum.ForumReplyBean;
import com.example.demo.model.bean.user.UserBean;

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
public class ViolationRef implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer violationRefId;
	
	@ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.MERGE)
	@JoinColumn(name = "violationId")
	private ViolationRule violationRule;
	
	@ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.MERGE)
	@JoinColumn(name = "userId")
	private UserBean userBean;
	
	@ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
	@JoinColumn(name = "article_Id")
	private CommentArticle commentArticle;
	
	@ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
	@JoinColumn(name = "reply_Id")
	private CommentReply commentReply;
	
	
	@ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
	@JoinColumn(name = "forumId")
	private ForumBean forumBean;
	
	@ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
	@JoinColumn(name = "ForumReplayId")
	private ForumReplyBean forumReplyBean;
	
	
	
}
