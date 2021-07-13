package com.example.demo.model.bean.mall;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="Product")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId ;
	@Column(nullable=false)
    private String productName ;
	@Column(nullable=false)
    private String productType ;
	
	private String subType;
	
	@Column(nullable=false)
    private Integer productPrice ;
	@Column(nullable=false,columnDefinition = "nvarchar(max)")
    private String productDescription ;
	@Column(nullable=false)
    private Integer productStock ;
	
//	@Column(nullable=false ,columnDefinition = "varchar(max)")
//    private byte[] productPhoto ;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "product")
	@JsonIgnore
	private List<ProductPicture> productPicture = new ArrayList<ProductPicture>();
	
	@Column(nullable=false,columnDefinition = "nvarchar(200)")
	private String briefDescription;
	
	@Column(nullable=false,columnDefinition = "nvarchar(200)")
	private String spec;
	
//	@Column(nullable=false)
//    private String productPath;
	@Column(nullable=false)
    private String productState;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="productItem")
	@JsonIgnore
	private List<CartAndOrder> cartlist = new ArrayList<CartAndOrder>();
	
	@Temporal(TemporalType.DATE)
	@Column(updatable = false)
	@CreationTimestamp
    private Date productCreateTime ;
	@Temporal(TemporalType.DATE)
	@Column(insertable = false)
	@UpdateTimestamp
    private Date productUpdateTime;
    
    

}
