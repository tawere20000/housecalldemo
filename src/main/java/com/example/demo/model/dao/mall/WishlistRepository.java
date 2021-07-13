package com.example.demo.model.dao.mall;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.mall.Wishlist;
import com.example.demo.model.bean.mall.WishlistId;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,WishlistId> {

	List<Wishlist> findByPrimaryKeyUserUserId(Integer userId);
	
	Wishlist findByPrimaryKeyUserUserIdAndPrimaryKeyProductProductId(Integer userId, Integer productId);
	
	void deleteByPrimaryKeyUserUserIdAndPrimaryKeyProductProductId(Integer userId, Integer productId);
	
}
