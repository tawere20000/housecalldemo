package com.example.demo.model.bean.forum;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.bean.user.UserBean;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ForumBean implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer forumId;
	@JsonIgnoreProperties
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="forumBean")
	private List<ForumReplyBean> ForumReplyBean = new ArrayList<ForumReplyBean>();
	
	@JsonIgnoreProperties
	@OneToMany(mappedBy = "forumBean",cascade = CascadeType.REMOVE)
	private Set<ForumJudge> forumJudge = new LinkedHashSet<ForumJudge>();
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name="fk_User_Id")
	private UserBean user;
	
//	@OneToMany(fetch = FetchType.LAZY,mappedBy = "forumBean",cascade = CascadeType.REMOVE)
//	private Set<ViolationRef> violationRef = new LinkedHashSet<ViolationRef>();
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false,columnDefinition = "varchar(max)")
	private String text;
	
	@Column(nullable=true)
	private Integer textGood=0;
	
	@Column(nullable=true)
	private Integer textBad=0;
	@Column(nullable=true)
	private Blob forumImage;
	@Column(nullable=true)
	private String imagePath;
	
	@Column(nullable=true)
	private Integer checkCount=0;
	@Column(nullable=true)
	private String forumType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	@CreationTimestamp
	private Date forumCreateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	@UpdateTimestamp
	private Date forumUpdateTime;
	
	private Integer respondForumKey;//哪篇文章下的回文,填入文章pk

	
	@Transient
	MultipartFile image;

	@Column(nullable=true)
	private String  userName ;

	@Column(nullable=true)
	private Integer textType=0;
	
	
	@Transient
	String invalidCredentials;
	}

	
	
	
	
	
	



