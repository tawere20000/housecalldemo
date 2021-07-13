package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.bean.mall.Wishlist;
import com.example.demo.model.service.mall.WishlistService;

@Controller
public class WishlistController {

	@Autowired
	WishlistService wishlistService;
	
	
	/**
	 * 加入商品至願望清單
	 */
	@PostMapping(value = "/wishlist")
	@ResponseBody
	public boolean addItem(Wishlist wishlist, HttpSession session) throws ServletException, IOException {
		wishlist.setTempUserId((Integer) session.getAttribute("LoginID"));
		return wishlistService.addItemIntoWishlist(wishlist);
	}
	
	/**
	 * 刪除願望清單品項
	 */
	@DeleteMapping(value = "/wishlist/{userId}/{productId}")
	@ResponseBody
	public void deleteItem(@PathVariable("userId")Integer userId, @PathVariable("productId")Integer productId) throws ServletException, IOException {	
		wishlistService.deleteItemFromWishlist(userId, productId);
	}
}
