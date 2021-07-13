package com.example.demo.model.bean.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.demo.model.bean.Blacklist;
import com.example.demo.model.bean.PointsEvent;
import com.example.demo.model.bean.ViolationEvent;
import com.example.demo.model.bean.comment.CommentArticle;
import com.example.demo.model.bean.forum.ForumBean;
import com.example.demo.model.bean.mall.CartAndOrder;
import com.example.demo.model.bean.mall.Wishlist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "Users")
public class UserBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@Column(nullable=false)
	private String account;
	
	@Column(nullable=false,columnDefinition = "varchar(max)")
	private String password;
	
	@Column(nullable=false)
	private String category ;
	
	@Column(nullable=false)
	private String status = "正常";
	
	
	//---------------------------------新增的-----------------------------------
	@Column(nullable=false,columnDefinition = "varchar(max)")
	private String privateKey;
	//---------------------------------新增的-----------------------------------
	
	@Transient
	private String passwordCheck;
	
	@Transient
	private boolean rememberMe;
	
	@Transient
	String invalidCredentials;
	
	@Transient
	private String originalPassword;
	
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="user")
	private List<ForumBean> forumBeanlist = new ArrayList<ForumBean>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="user")
	private List<CommentArticle> commentArticlelist = new ArrayList<CommentArticle>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="user")
	private List<PointsEvent> pointsEventlist = new ArrayList<PointsEvent>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="user")
	private List<CartAndOrder> cartlist = new ArrayList<CartAndOrder>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="user")
	private List<ViolationEvent> violationlist = new ArrayList<ViolationEvent>();
	
	//要問是不是有mappedby錯
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="user")
	private List<Blacklist> blacklist = new ArrayList<Blacklist>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="primaryKey.user")
	private List<Wishlist> wishlist = new ArrayList<Wishlist>();
	
//	================================新增
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="user")
	@JsonIgnoreProperties
	private List<InnerMessage> innerMessage = new ArrayList<InnerMessage>();
//	================================新增

}
