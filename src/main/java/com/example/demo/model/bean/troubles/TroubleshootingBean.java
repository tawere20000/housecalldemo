package com.example.demo.model.bean.troubles;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.bean.user.Personal_InformationBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Troubleshooting")
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TroubleshootingBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer businessOrderId;//單號
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "userId")
	private Personal_InformationBean user; // 會員
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="businessOrderId")
	private List<Quotation> quotationlist = new ArrayList<Quotation>();//報價

	@Column(nullable=false)
	private String troubleType;//問題種類
	@Column(nullable=false)
	private String title;//訂單標題
	@Column(nullable=false)
	private String address;//需求地址
	@Column(nullable=false,columnDefinition = "varchar(max)")
	private String textArea;//問題內容描述
	@Column(nullable=true)
	private Blob tsimage;//照片
	@Column(nullable=false)
	private String imagePath;//照片路徑
	
	@Column(nullable=false)
	private String remark;//備註
	
	@Temporal(TemporalType.DATE)
	@Column(updatable = false)
	@CreationTimestamp
	private Date orderCreateDate;//訂單建立日期
	
	@Column(nullable=false)
	private String status;//訂單狀態:分成 待處理,廠商接單處理中,已完成 三種狀態
	@Temporal(TemporalType.DATE)
	private Date orderTakingDate;//廠商接單日期
	@Temporal(TemporalType.DATE)
	private Date finishedDate;//處理完成日期
	
	/////////////////////////////////////////////以下為會員評價
	
	private Integer rating;//評分
	
	private String comment;//評價
	
	
	@Transient
	MultipartFile image;
	
}
	
	
	
	
	
	
	

