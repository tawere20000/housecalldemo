package com.example.demo.model.bean.forum;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.demo.model.bean.user.UserBean;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumReplyBean implements Serializable {

	 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer replyId;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_forum_Id")
	private ForumBean forumBean;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name="fk_User_Id")
	private UserBean user;
	
//	@OneToMany(fetch = FetchType.LAZY,mappedBy = "message",cascade = CascadeType.REMOVE)
//	private Set<ViolationRef> violationRef = new LinkedHashSet<ViolationRef>();
	@JsonIgnoreProperties
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "forumReplyBean",cascade = CascadeType.REMOVE)
	private Set<ForumReplyJudge> forumReplyJudge = new LinkedHashSet<ForumReplyJudge>();
	
	
	@Column(nullable=true,columnDefinition = "varchar(max)")///////////留言限制字數
	private String text;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	@CreationTimestamp
	private Date replyDate;
	
	@Column(nullable=true )
	private Integer textGood=0;
	
	@Column(nullable=true)
	private Integer textBad=0;
	
	@Column(nullable=true)
	private String  userName ;

	@Column(nullable=true)
	private Integer textType=0;
	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	


