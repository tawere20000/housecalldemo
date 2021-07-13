package com.example.demo.model.bean.comment;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.bean.user.UserBean;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class CommentArticle implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer articleId;
	
	@OneToMany(mappedBy = "commentArticle",cascade = CascadeType.REMOVE)
	@JsonIgnore
	private Set<CommentReply> commentReply = new LinkedHashSet<CommentReply>();
	
//	@OneToMany(fetch = FetchType.EAGER,mappedBy = "commentArticle",cascade = CascadeType.REMOVE)
//	private Set<CommentReplyPlusJudge> commentReplyPlusJudge = new LinkedHashSet<CommentReplyPlusJudge>();
	//**************************************************************
	@OneToMany(mappedBy = "commentArticle",cascade = CascadeType.REMOVE)
	@JsonIgnore
	private Set<CommentArticleJudge> commentArticleJudge = new LinkedHashSet<CommentArticleJudge>();
	//**************************************************************
	
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="fk_User_Id")
	private UserBean user;
	
//	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
//	@JoinColumn(name="fk_articleJudge_Id")
//	private CommentArticleJudge commentArticleJudge;
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false,columnDefinition = "varchar(max)")
	private String text;
	
	@Column(nullable=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date liveDateStart;
	
	@Column(nullable=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date liveDateEnd;
	//新增新增新增新增新增新增新增新增新增新增新增新增新增新增新增
	//新增新增新增新增新增新增新增新增新增新增新增新增新增新增新增
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = true)
	@CreationTimestamp
	private Date articleCreateTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = true)
	@UpdateTimestamp
	private Date articleUpdateTime;
	//新增新增新增新增新增新增新增新增新增新增新增新增新增新增新增
	@Column(nullable=true)
	private Blob articleImage;
	
	@Column(nullable=true)
	private String imagePath;
	
	@Column(nullable=true)
	private Integer textGoodTotal;
	
	@Column(nullable=true)
	private Integer textBadTotal;
	
	@Column(nullable=true)
	private Integer checkCount;
	
	@Column(nullable=true)
	private String taipeiArea;
	

	@Column(nullable=true,columnDefinition = "varchar(max)")
	private String address;
//	@Column(nullable=false)
//	String mapConnection;/////////////////////////////
	
	@Transient
	MultipartFile image;
	@Column(nullable=true)
	private String  userName ; 
	
	@Column(nullable=true)
	private Integer textType;
	
	@Transient
	String invalidCredentials;
	
	

}
