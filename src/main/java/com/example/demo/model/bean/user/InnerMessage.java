package com.example.demo.model.bean.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "InnerMessage")

public class InnerMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name="senderId")
	@JsonIgnore
	private UserBean user;
	
	private int receiverId;
	
	private String mailTitle;
	
	@Column(nullable=false,columnDefinition = "varchar(max)")
	private String mailContent;
	
	private String mailStatus="未讀";  //未讀,已讀,刪除
	
	@Temporal(TemporalType.DATE)
	@Column(updatable = false)
	@CreationTimestamp
	private Date sendDate; 

	@Transient
	private String receiveAccount;
	
	
}
