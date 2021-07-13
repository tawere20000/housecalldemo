package com.example.demo.model.service.mall;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.mall.Product;
import com.example.demo.model.bean.mall.Wishlist;
import com.example.demo.model.bean.user.UserBean;
import com.example.demo.model.dao.mall.ProductRepository;
import com.example.demo.model.dao.mall.WishlistRepository;
import com.example.demo.model.dao.user.UserRepository;

@Service
@Transactional
public class WishlistService {

	@Autowired
	private WishlistRepository wr;
	
	@Autowired
	private ProductRepository pdr;
	
	@Autowired
	private UserRepository ur;
	
	/**
	 * 加入商品至願望清單
	 * @param wishlist
	 */
	public boolean addItemIntoWishlist(Wishlist wishlist) {
		Wishlist wishList = wr.findByPrimaryKeyUserUserIdAndPrimaryKeyProductProductId(wishlist.getTempUserId(), wishlist.getTempProductId());
		if (wishList == null) {
			UserBean user = ur.findById(wishlist.getTempUserId()).get();
			Product product = pdr.findById(wishlist.getTempProductId()).get();
			wishlist.setUser(user);
			wishlist.setProduct(product);
			wr.save(wishlist);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 刪除願望清單品項
	 * @param userId 會員ID
	 * @param productId 商品ID
	 */
	public void deleteItemFromWishlist(Integer userId, Integer productId) {
		wr.deleteByPrimaryKeyUserUserIdAndPrimaryKeyProductProductId(userId, productId);
	}
	
	/**
	 * 查詢願望清單內容
	 * @param userId 會員ID
	 * @return
	 */
	public List<Wishlist> queryWishlistByUserId(Integer userId) {
		return wr.findByPrimaryKeyUserUserId(userId);
	}
	
}
