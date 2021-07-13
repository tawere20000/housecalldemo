package com.example.demo.model.bean.mall;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.demo.model.bean.user.UserBean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@AssociationOverrides({
	@AssociationOverride(name = "primaryKey.user", joinColumns = @JoinColumn(name = "userId")),
	@AssociationOverride(name = "primaryKey.product", joinColumns = @JoinColumn(name = "productId"))
})
public class Wishlist {
	
	@EmbeddedId
	private WishlistId primaryKey = new WishlistId();
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	@CreationTimestamp
	private Date createDate;
	
	@Transient
	private Integer tempProductId;
	
	@Transient
	private Integer tempUserId;

	@Transient
	public UserBean getUser() {
		return getPrimaryKey().getUser();
	}
	
	public void setUser(UserBean user) {
		getPrimaryKey().setUser(user);
	}
	
	@Transient
	public Product getProduct() {
		return getPrimaryKey().getProduct();
	}
	
	public void setProduct(Product product) {
		getPrimaryKey().setProduct(product);
	}

}
