package com.example.demo.model.bean.user;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.bean.troubles.TroubleshootingBean;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper=true) 
@DynamicInsert
@DynamicUpdate
@PrimaryKeyJoinColumn(name = "user_ID")
@Table(name="Personal_Information")
public class Personal_InformationBean extends UserBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(nullable=false)
	private String  name ;
	
	@Column(nullable=false)
	private String  nickName ;
	
	@Column(nullable=false)
	private String  gender;
	
	@Column(nullable=false)
	private String  birthday;
	
	@Column(nullable=false)
	private String  phoneNumber;
	
	@Column(nullable=false)
	private String  email;
	
	@Column(nullable=false)
	private String  idNumber;
	
	
	//@Column(nullable=false)
	private Blob   headShot;
	
	@Column(nullable=false)
	private String  p_Path;
	
	//@Column(nullable=false)
	private String  addressDetail;
	
	//@Column(nullable=false)
	private String  addressCounty;
	
	//@Column(nullable=false)
	private String  addressDistrict;
	
	//@Column(nullable=false)
	private String  addressZipcode;
	
	@Column(nullable=false)
	private Integer totalPoints = 0;
	
	
	@Temporal(TemporalType.DATE)
	@Column(updatable = false)
	@CreationTimestamp
	private Date joinDate;
	
	
	@Transient
	MultipartFile userImage;
	
	@Transient
	boolean contract ;
	
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="user")
	private List<TroubleshootingBean> troubleshootingBean = new ArrayList<TroubleshootingBean>();
	


}
