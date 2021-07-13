package com.example.demo.model.bean.troubles;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.demo.model.bean.user.Vendor_InformationBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Quotation implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer businessQuteId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name= "userId")
	private Vendor_InformationBean vendorId;//廠商
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name= "businessOrderId")
	private TroubleshootingBean businessOrderId;//訂單
	@Column(nullable=true,columnDefinition = "varchar(max)")
	private String text;//備註
	
	@Column(nullable=false)
	private Integer price;//報價
	
	@Temporal(TemporalType.DATE)
	@Column(updatable = false)
	@CreationTimestamp
	private Date quotationCreateDate;//報價日期
	
	private String selected;//是否被選中

	@Transient
	private double averagerating;//新增的Bean
	

}
