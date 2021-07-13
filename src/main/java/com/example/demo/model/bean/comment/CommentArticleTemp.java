package com.example.demo.model.bean.comment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DynamicInsert
@DynamicUpdate
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentArticleTemp {
	@Id
	private Integer articleId;
	@Column(nullable=true)
	private Integer userId;
	@Column(nullable=true)
	private String title;
	@Column(nullable=true,columnDefinition = "varchar(max)")
	private String text;
	@Column(nullable=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd ")
	private Date liveDateStart;
	@Column(nullable=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd ")
	private Date liveDateEnd;
	@Column(nullable=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm tt")
	private Date articleUpdateTime;
	@Column(nullable=true)
	private String taipeiArea;
	@Column(nullable=true)
	private Integer textGoodTotal;
	@Column(nullable=true)
	private Integer textBadTotal;
	@Column(nullable=true)
	private Integer checkCount;
	@Column(nullable=true,columnDefinition = "varchar(max)")
	private String address;
	@Column(nullable=true)
	private String  userName ; 
	@Column(nullable=true)
	private Integer textType;
	
	
	
	
	
}
