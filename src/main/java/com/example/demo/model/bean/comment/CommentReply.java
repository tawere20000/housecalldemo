package com.example.demo.model.bean.comment;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

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
public class CommentReply implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer replyId;
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_Article_Id")
	private CommentArticle commentArticle;
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name="fk_User_Id")
	private UserBean user;
	@Column(nullable=false,columnDefinition = "varchar(max)")///////////留言限制字數
	private String text;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = true)
	@CreationTimestamp
	private Date replyCreateTime;
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "commentReply",cascade = CascadeType.REMOVE)
	private Set<CommentReplyJudge> commentReplyJudge = new LinkedHashSet<CommentReplyJudge>();
//	
//	@OneToMany(fetch = FetchType.EAGER,mappedBy = "commentReply",cascade = CascadeType.REMOVE)
//	private Set<CommentReplyJudge> commentReplyJudge = new LinkedHashSet<CommentReplyJudge>();
	
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = true)
	@UpdateTimestamp
	private Date replyUpdateTime;
	@Column(nullable=true)
	private Integer textGoodTotal;
	@Column(nullable=true)
	private Integer textBadTotal;
	@Column(nullable=true)
	private Integer textType;
	@Column(nullable=true)
	private String  userName ; 
	
	
}
