package com.example.demo.model.bean.mall;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OrderDetail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.example.demo.model.bean.mall.DateSeqGenerator")
	private Long orderNo;
	
	private String tradeIdentification;
	
	@Column(nullable = false)
	private Integer totalPrice;
	
	private Integer discount;
	
	@Column(nullable = false)
	private Integer deliveryFee;
	
	@Column(nullable = false)
	private Integer netPrice;
	
	@Column(nullable = false)
	private String payerName;
	
	@Column(nullable = false)
	private String payerEmail;
	
	@Column(nullable = false)
	private String payerPhone;
	
	@Column(nullable = false)
	private String payerAddress;
	
	@Column(nullable = false)
	private String receiverName;
	
	@Column(nullable = false)
	private String receiverEmail;
	
	@Column(nullable = false)
	private String receiverPhone;
	
	@Column(nullable = false)
	private String receiverAddress;
	
	@Column(nullable = false)
	private String paymentMethod;
	
	@Column(nullable = false)
	private String statusOrder;
	
	@Column(nullable = false)
	private String statusPayment;
	
	private String crReason;
	
	private String suggestion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	@CreationTimestamp
	private Date orderedTimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date readyForShippingTimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date shippingTimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date shippedTimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishedTimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date crTimestamp;
	
	@OneToMany(mappedBy = "orderDetail", cascade = CascadeType.MERGE)
	Set<CartAndOrder> cartAndOrder = new HashSet<CartAndOrder>();
}
