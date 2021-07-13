package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.bean.mall.CartAndOrder;
import com.example.demo.model.bean.mall.Product;
import com.example.demo.model.bean.user.Personal_InformationBean;
import com.example.demo.model.bean.user.Vendor_InformationBean;
import com.example.demo.model.service.mall.CartAndOrderService;
import com.example.demo.model.service.mall.InventoryService;
import com.example.demo.model.service.user.PersonalService;
import com.example.demo.model.service.user.VendorService;

@Controller
public class MallController {

	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	CartAndOrderService cartAndOrderService;
	
	@Autowired
	PersonalService personalService;
	
	@Autowired
	VendorService vendorService;

	/**
	 * 顯示商城首頁
	 */
	@GetMapping(value = "mallindex.html")
	public String mallIndex(HttpSession session, Model model) {
		Integer userId =  (Integer) session.getAttribute("LoginID");
		if (userId != null) {
			Integer cartQuantity = cartAndOrderService.queryCartQuantityById(userId);
			model.addAttribute("cartQuantity", cartQuantity);
			String userCategory = (String) session.getAttribute("Category");
			if (userCategory.equals("會員")) {
				Personal_InformationBean userInfo = personalService.getPersonInfoById(userId);
				model.addAttribute("userInfo", userInfo);
			} else {
				Vendor_InformationBean userInfo = vendorService.getVendorInfo(userId);
				model.addAttribute("userInfo", userInfo);
			}
		}
		List<Product> randomProduct = inventoryService.getRandomProduct();
		model.addAttribute("randomProduct", randomProduct);
		List<Product> randomProductDown = new ArrayList<Product>();
		for (int i = 2;i < randomProduct.size(); i++) {
			randomProductDown.add(randomProduct.get(i));
		}
		model.addAttribute("randomProductDown", randomProductDown);
		List<Product> newestProduct = inventoryService.queryNewestProduct();
		model.addAttribute("newestProduct", newestProduct);
		return "/mall/mallindex";
	}
	
	
	/**
	 * 顯示商品頁面
	 */
	@GetMapping(value = "/shop.html")
	public String sellingList(@RequestParam(defaultValue = "1")Integer pageNo, @RequestParam(defaultValue = "1")Integer pageSize, String productType, String subType, @RequestParam(defaultValue = "0")Integer minPrice, @RequestParam(defaultValue = "1000000")Integer maxPrice, String keyword, HttpSession session, Model model, HttpServletRequest request) {
		Page<Product> productList;
		if (productType != null) {
			productList = inventoryService.pageForSearchingTypeAndStateAndPrice(pageNo, pageSize, productType, minPrice, maxPrice);
			model.addAttribute("productType", productType);
		} else if (subType != null) {
			productList = inventoryService.pageForSearchingSubTypeAndStateAndPrice(pageNo, pageSize, subType, minPrice, maxPrice);
			model.addAttribute("subType", subType);
		} else if (keyword != null && keyword != "") {
			productList = inventoryService.pageForSearchingKeywordAndStateAndPrice(pageNo, pageSize, keyword, minPrice, maxPrice);
			model.addAttribute("keyword", keyword);
		} else {
			productList = inventoryService.pageForMallAndPrice(pageNo, pageSize, minPrice, maxPrice);
		}
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);
		Integer userId =  (Integer) session.getAttribute("LoginID");
		if (userId != null) {
			Map<String, Object> cartInfo = cartAndOrderService.queryCartInfoById(userId);
			Integer cartQuantity = (Integer) cartInfo.get("cartQuantity");
			if (cartQuantity != null) {
				model.addAttribute("cartQuantity", cartQuantity);
				@SuppressWarnings("unchecked")
				List<CartAndOrder> cartList = (List<CartAndOrder>) cartInfo.get("cartList");
				for (CartAndOrder cartItem : cartList) {
					for (Product product : productList) {
						if (cartItem.getProductItem().getProductId() == product.getProductId()) {
							product.setProductStock(product.getProductStock() - cartItem.getShoppingAmount());
							break;
						}
					}
				}
			} 
		}
		model.addAttribute("productList", productList);

		String isAjax = request.getHeader("X-Requested-With");
		if (isAjax == null) {
			if (userId != null) {
				String userCategory = (String) session.getAttribute("Category");
				if (userCategory.equals("會員")) {
					Personal_InformationBean userInfo = personalService.getPersonInfoById(userId);
					model.addAttribute("userInfo", userInfo);
				} else {
					Vendor_InformationBean userInfo = vendorService.getVendorInfo(userId);
					model.addAttribute("userInfo", userInfo);
				}
			}
			return "/mall/shop";
		} else {
			return "/mall/shop :: product_list";
		}
		
	}
	
	
	/**
	 * 顯示單一商品Quickview
	 */
	@GetMapping(value = "/quickView/{productId}")
	public String getProductQuickView(@PathVariable("productId") String productId, Model model) {
		Product product = inventoryService.queryProductByID(Integer.valueOf(productId));
		model.addAttribute("product", product);
		model.addAttribute("briefDescription", product.getBriefDescription().replaceAll(System.getProperty("line.separator"), "</br>"));
		return "/mall/mallcommon :: quick_view";
	}
	
	
	/**
	 * 顯示單一商品頁面
	 */
	@GetMapping(value = "/singleProduct/{productId}")
	public String getProductInfo(@PathVariable("productId") String productId, Model model, HttpSession session) {
		Product product = inventoryService.queryProductByID(Integer.valueOf(productId));
		model.addAttribute("product", product);
		String property = System.getProperty("line.separator");
		model.addAttribute("briefDescription", product.getBriefDescription().replaceAll(property, "</br>"));
		model.addAttribute("productDescription", product.getProductDescription().replaceAll(property, "</br>"));
		model.addAttribute("spec", product.getSpec().replaceAll(property, "</br>"));
		Integer userId =  (Integer) session.getAttribute("LoginID");
		if (userId != null) {
			Integer cartQuantity = cartAndOrderService.queryCartQuantityById(userId);
			model.addAttribute("cartQuantity", cartQuantity);
		}
		String userCategory = (String) session.getAttribute("Category");
		if (userCategory != null) {
			if (userCategory.equals("會員")) {
				Personal_InformationBean userInfo = personalService.getPersonInfoById(userId);
				model.addAttribute("userInfo", userInfo);
			} else {
				Vendor_InformationBean userInfo = vendorService.getVendorInfo(userId);
				model.addAttribute("userInfo", userInfo);
			}
		}
		return "/mall/productdetail";
	}
}
