package com.example.demo.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.bean.mall.CartAndOrder;
import com.example.demo.model.bean.mall.OrderDetail;
import com.example.demo.model.bean.mall.Wishlist;
import com.example.demo.model.bean.user.Personal_InformationBean;
import com.example.demo.model.bean.user.Vendor_InformationBean;
import com.example.demo.model.service.PointsEventService;
import com.example.demo.model.service.mall.CartAndOrderService;
import com.example.demo.model.service.mall.InventoryService;
import com.example.demo.model.service.mall.WishlistService;
import com.example.demo.model.service.user.PersonalService;
import com.example.demo.model.service.user.VendorService;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;



@Controller
public class CartAndOrderController {
	
	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	CartAndOrderService cartAndOrderService;
	
	@Autowired
	WishlistService wishlistService;
	
	@Autowired
	PersonalService personalService;
	
	@Autowired
	PointsEventService pointsEventService;
	
	@Autowired
	VendorService vendorService;
	
	
	/**
	 * 查詢資料庫內之購物車商品數量
	 */
	@GetMapping(value = "/shoppingCartQuantity")
	public String getCartQuantity(HttpSession session, Model model) {
		Integer userId =  (Integer) session.getAttribute("LoginID");
		if (userId != null) {
			Integer cartQuantity = cartAndOrderService.queryCartQuantityById(userId);
			model.addAttribute("cartQuantity", cartQuantity);
		}
		return "/mall/mallcommon :: cart_Quantity";
	}
	
	
	/**
	 * 查詢資料庫內之購物車內容
	 */
	@GetMapping(value = "/shoppingCart/{pageCategory}")
	public String getCartInfo(HttpSession session, HttpServletRequest request, Model model, @PathVariable("pageCategory")String pageCategory) throws ServletException, IOException {
		Integer userId =  (Integer) session.getAttribute("LoginID");
		if (pageCategory.equals("quickview")) {
			if (userId != null) {
				Map<String, Object> cartInfo = cartAndOrderService.queryCartInfoById(userId);
				model.addAllAttributes(cartInfo);
			}
			return "/mall/mallcommon :: cart_Info";
		} else {
			Map<String, Object> cartInfo = cartAndOrderService.queryCartInfoById(userId);
			model.addAllAttributes(cartInfo);
			String isAjax = request.getHeader("X-Requested-With");
			if (isAjax == null) {
				model.addAttribute("pageCategory", pageCategory);
				String userCategory = (String) session.getAttribute("Category");
				if (userCategory.equals("會員")) {
					Personal_InformationBean userInfo = personalService.getPersonInfoById(userId);
					model.addAttribute("userInfo", userInfo);
					List<Wishlist> wishlist = userInfo.getWishlist();
					model.addAttribute("wishlist", wishlist);
				} else {
					Vendor_InformationBean userInfo = vendorService.getVendorInfo(userId);
					model.addAttribute("userInfo", userInfo);
					List<Wishlist> wishlist = userInfo.getWishlist();
					model.addAttribute("wishlist", wishlist);
				}
				return "/mall/cart";
			} else {
				if (pageCategory.equals("wishlist")) {
					List<Wishlist> wishlist = wishlistService.queryWishlistByUserId(userId);
					model.addAttribute("wishlist", wishlist);
					return "/mall/cart :: wishlist";
				} else {
					String userCategory = (String) session.getAttribute("Category");
					if (userCategory.equals("會員")) {
						Personal_InformationBean userInfo = personalService.getPersonInfoById(userId);
						model.addAttribute("userInfo", userInfo);
					} else {
						Vendor_InformationBean userInfo = vendorService.getVendorInfo(userId);
						model.addAttribute("userInfo", userInfo);
					}
					return "/mall/cart :: cart";
				}
			}
		}	
	}
	
	
	/**
	 * 加入商品至購物車
	 */
	@PostMapping(value = "/shoppingCart")
	@ResponseBody
	public String addItem(CartAndOrder cartAndOrder, HttpSession session) throws ServletException, IOException {
		cartAndOrder.setTempUserId((Integer) session.getAttribute("LoginID"));
		return cartAndOrderService.addItemIntoCart(cartAndOrder);
	}
	

	/**
	 * 刪除購物車品項
	 */
	@DeleteMapping(value = "/shoppingCart/{cartId}")
	@ResponseBody
	public void deleteItem(@PathVariable("cartId")Integer cartId) throws ServletException, IOException {	
		cartAndOrderService.deleteItemFromCart(cartId);
	}
	

	/**
	 * 修改購物車品項購買數量
	 */
	@PutMapping(value = "/shoppingCart/{cartId}/{productId}/{amount}")
	@ResponseBody
	public void updateAmount(@PathVariable("cartId")Integer cartId, @PathVariable("productId")Integer productId, @PathVariable("amount")Integer amount, CartAndOrder cartAndOrder) throws ServletException, IOException {
		cartAndOrder.setCartId(cartId);
		cartAndOrder.setTempProductId(productId);
		cartAndOrder.setShoppingAmount(amount);
		cartAndOrderService.updateItemAmount(cartAndOrder);
	}
	
	
	/**
	 * 提交購物車內容至結帳頁面
	 */
	@PostMapping(value = "/checkout.html")
	public String sendCartInfoToOrder(HttpSession session, Model model, OrderDetail orderDetail) {
		Integer userId =  (Integer) session.getAttribute("LoginID");
		List<CartAndOrder> cartList = cartAndOrderService.queryCartContentById(userId);
		model.addAttribute("cartList", cartList);
		String userCategory = (String) session.getAttribute("Category");
		if (userCategory.equals("會員")) {
			Personal_InformationBean userInfo = personalService.getPersonInfoById(userId);
			model.addAttribute("userInfo", userInfo);
		} else {
			Vendor_InformationBean userInfo = vendorService.getVendorInfo(userId);
			model.addAttribute("userInfo", userInfo);
		}
		model.addAttribute("orderDetail", orderDetail);
		return "/mall/checkout";
	}
	
	
	/**
	 * 送出訂單(貨到付款)
	 */
	@PostMapping(value = "/placeorder.html")
	public String placeOrderByCash(OrderDetail orderDetail, Integer[] cartId, String payerCounty, String payerDistrict, String payerZipcode, String payerAddressDetail, String receiverCounty, String receiverDistrict, String receiverZipcode, String receiverAddressDetail, Model model, HttpSession session) {
		String payerAddress = payerZipcode + payerCounty + payerDistrict + payerAddressDetail;
		String receiverAddress = receiverZipcode + receiverCounty + receiverDistrict + receiverAddressDetail;
		OrderDetail addedOrder = cartAndOrderService.addOrder(orderDetail, cartId, payerAddress, receiverAddress);
		model.addAttribute("orderDetail", addedOrder);
		Integer userId =  (Integer) session.getAttribute("LoginID");
		Integer cartQuantity = cartAndOrderService.queryCartQuantityById(userId);
		model.addAttribute("cartQuantity", cartQuantity);
		String userCategory = (String) session.getAttribute("Category");
		Integer discount = orderDetail.getDiscount();
		if (userCategory.equals("會員")) {
			Personal_InformationBean userInfo = personalService.getPersonInfoById(userId);
			model.addAttribute("userInfo", userInfo);
			if (discount != null) {
				pointsEventService.discountOrder(userInfo, discount);
			}
		} else {
			Vendor_InformationBean userInfo = vendorService.getVendorInfo(userId);
			model.addAttribute("userInfo", userInfo);
			if (discount != null) {
				pointsEventService.discountOrder(userInfo, discount);
			}
		}
		model.addAttribute("msg", "已經收到您的訂單，感謝您");
		return "/mall/ordercomplete";
	}
	
	
	/**
	 * 送出訂單(信用卡付款)
	 */
	@PutMapping(value = "/placeorder.html")
	@ResponseBody
	public String placeOrderByCredit(OrderDetail orderDetail, Integer[] cartId, String payerCounty, String payerDistrict, String payerZipcode, String payerAddressDetail, String receiverCounty, String receiverDistrict, String receiverZipcode, String receiverAddressDetail, HttpSession session) {
		String payerAddress = payerZipcode + payerCounty + payerDistrict + payerAddressDetail;
		String receiverAddress = receiverZipcode + receiverCounty + receiverDistrict + receiverAddressDetail;
		OrderDetail addedOrder = cartAndOrderService.addOrder(orderDetail, cartId, payerAddress, receiverAddress);
		addedOrder = cartAndOrderService.createTradeIdentification(addedOrder);
		Integer userId =  (Integer) session.getAttribute("LoginID");
		String userCategory = (String) session.getAttribute("Category");
		Integer discount = orderDetail.getDiscount();
		if (userCategory.equals("會員")) {
			Personal_InformationBean userInfo = personalService.getPersonInfoById(userId);
			if (discount != null) {
				pointsEventService.discountOrder(userInfo, discount);
			}
			return toEcPay(addedOrder, userInfo.getName(), userInfo.getP_Path(), userId.toString(), userCategory);
		} else {
			Vendor_InformationBean userInfo = vendorService.getVendorInfo(userId);
			if (discount != null) {
				pointsEventService.discountOrder(userInfo, discount);
			}
			return toEcPay(addedOrder, userInfo.getName(), userInfo.getP_Path(), userId.toString(), userCategory);
		}
	}
	
	
	/**
	 * 對未付款之訂單連線至綠界系統進行付款(信用卡)
	 */
	@PostMapping(value = "/payByCredit")
	@ResponseBody
	public String payByCredit(Long orderNo, HttpSession session) {
		OrderDetail orderDetail = cartAndOrderService.queryOrderDetailByOrderNo(orderNo);
		orderDetail = cartAndOrderService.createTradeIdentification(orderDetail);
		Integer userId =  (Integer) session.getAttribute("LoginID");
		String userCategory = (String) session.getAttribute("Category");
		if (userCategory.equals("會員")) {
			Personal_InformationBean userInfo = personalService.getPersonInfoById(userId);
			return toEcPay(orderDetail, userInfo.getName(), userInfo.getP_Path(), userId.toString(), userCategory);
		} else {
			Vendor_InformationBean userInfo = vendorService.getVendorInfo(userId);
			return toEcPay(orderDetail, userInfo.getName(), userInfo.getP_Path(), userId.toString(), userCategory);
		}
	}
	
	
	/**
	 * 信用卡付款後導向訂單完成頁面
	 */
	@PostMapping(value = "/ordercomplete.html")
	public String orderCheck(@RequestParam(name = "MerchantTradeNo")String tradeIdentification, @RequestParam(name = "RtnCode")Integer tradeResult, String CustomField1, String CustomField2, String CustomField3, String CustomField4, Model model, HttpSession session, Personal_InformationBean pi, Vendor_InformationBean vi) {
		Integer userId = Integer.valueOf(CustomField3);
		session.setAttribute("LoginID", userId);
		session.setAttribute("Category", CustomField4);
		Integer cartQuantity = cartAndOrderService.queryCartQuantityById(userId);
		model.addAttribute("cartQuantity", cartQuantity);
		if (CustomField4.equals("會員")) {
			// 發出請求至綠界時設置自定義參數1為用戶name，於此處設置提供予ordercomplete頁面使用
			pi.setName(CustomField1);
			// 發出請求至綠界時設置自定義參數2為用戶p_Path，於此處設置提供予ordercomplete頁面使用
			pi.setP_Path(CustomField2);
			model.addAttribute("userInfo", pi);
		} else {
			// 發出請求至綠界時設置自定義參數1為用戶name，於此處設置提供予ordercomplete頁面使用
			vi.setName(CustomField1);
			// 發出請求至綠界時設置自定義參數2為用戶p_Path，於此處設置提供予ordercomplete頁面使用
			vi.setP_Path(CustomField2);
			model.addAttribute("userInfo", vi);
		}
		if (tradeResult == 1) {
			OrderDetail orderDetail = cartAndOrderService.editOrderStatusPayment(Long.parseLong(tradeIdentification.substring(0, 12)));
			model.addAttribute("orderDetail", orderDetail);
			model.addAttribute("msg", "已經收到您的訂單，感謝您");
		} else {
			OrderDetail orderDetail = cartAndOrderService.queryOrderDetailByOrderNo(Long.parseLong(tradeIdentification.substring(0, 12)));
			model.addAttribute("orderDetail", orderDetail);
			model.addAttribute("msg", "已經收到您的訂單，但付款流程可能發生錯誤，請重新進行付款");
		}
		return "/mall/ordercomplete";
	}
	
	
	/**
	 * 查詢會員所有訂單
	 */
	@GetMapping(value = "/orderlist.html")
	public String getMemberOrder(HttpSession session, Model model) {
		Integer userId =  (Integer) session.getAttribute("LoginID");
		List<OrderDetail> userOrder = cartAndOrderService.queryUserOrder(userId);
		model.addAttribute("userOrder", userOrder);
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
		return "/mall/orderlist";
	}
	
	
	/**
	 * 跳轉至取消訂單/退貨申請頁面
	 */
	@PostMapping(value = "/crconfirm.html")
	public String sendCRRequest(Long orderNo, String statusOrder, Model model, HttpSession session) {
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("statusOrder", statusOrder);
		Integer userId =  (Integer) session.getAttribute("LoginID");
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
		return "/mall/crconfirm";
	}
	
	
	/**
	 * 送出取消訂單/退貨申請
	 */
	@PostMapping(value = "/cancelOrderAndReturn")
	@ResponseBody
	public void cancelOrderAndReturn(Long orderNo, String statusOrder, String crReason, String suggestion) {
		cartAndOrderService.editOrderStatus(orderNo, statusOrder, crReason, suggestion);
		cartAndOrderService.addStockBack(orderNo);
	}
	
	
	/**
	 * 進入綠界信用卡付款流程
	 * @param orderDetail 欲進行付款之訂單詳情
	 * @return
	 */
	public String toEcPay(OrderDetail orderDetail, String name, String p_path, String userId, String userCategory) {
		AllInOne aio = new AllInOne("");
		AioCheckOutALL acoa = new AioCheckOutALL();
		acoa.setMerchantID("2000132");
		acoa.setMerchantTradeNo(orderDetail.getTradeIdentification());
		Date dtoday = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		acoa.setMerchantTradeDate(sdf.format(dtoday));
		acoa.setTotalAmount(orderDetail.getNetPrice().toString());
		acoa.setTradeDesc("米奇不妙屋商城購物");
		acoa.setItemName("米奇不妙屋商城商品一批 X1");
		acoa.setReturnURL("http://localhost:8080/ordercomplete1.html");
		acoa.setOrderResultURL("http://localhost:8080/ordercomplete.html");
		// 設置自定義屬性1為用戶name
		acoa.setCustomField1(name);
		// 設置自定義屬性2為用戶p_path
		acoa.setCustomField2(p_path);
		// 設置自定義屬性3為用戶id
		acoa.setCustomField3(userId);
		// 設置自定義屬性4為用戶category
		acoa.setCustomField4(userCategory);
		return aio.aioCheckOut(acoa, null);
	}
	
}
