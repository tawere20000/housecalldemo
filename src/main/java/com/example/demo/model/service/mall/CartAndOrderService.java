package com.example.demo.model.service.mall;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.mall.CartAndOrder;
import com.example.demo.model.bean.mall.OrderDetail;
import com.example.demo.model.bean.mall.Product;
import com.example.demo.model.bean.user.UserBean;
import com.example.demo.model.dao.mall.CartAndOrderRepository;
import com.example.demo.model.dao.mall.OrderDetailRepository;
import com.example.demo.model.dao.mall.ProductRepository;
import com.example.demo.model.dao.user.UserRepository;


@Service
@Transactional
public class CartAndOrderService {

	@Autowired
	private CartAndOrderRepository caor;
	
	@Autowired
	private ProductRepository pdr;
	
	@Autowired
	private OrderDetailRepository odr;
	
	@Autowired
	private UserRepository ur;
	

	/**
	 * 加入商品至購物車
	 * @param cartAndOrder
	 */
	public String addItemIntoCart(CartAndOrder cartAndOrder) {
		Product product = pdr.findById(cartAndOrder.getTempProductId()).get();
		Integer productStock = product.getProductStock();
		if (productStock > 0) {
			List<CartAndOrder> cartList = caor.findByUser_UserIdAndProductItem_ProductIdAndOrderDetail_OrderNoIsNull(cartAndOrder.getTempUserId(), cartAndOrder.getTempProductId());
			if (cartList.isEmpty()) {
				UserBean user = ur.findById(cartAndOrder.getTempUserId()).get();
				cartAndOrder.setUser(user);
				cartAndOrder.setProductItem(product);
				caor.save(cartAndOrder);
				return "added";
			} else {
				return "already";
			}
		} else {
			return "noStock";
		}
	}

	
	/**
	 * 刪除購物車品項
	 * @param cartId 購物車品項ID
	 */
	public void deleteItemFromCart(Integer cartId) {
		caor.deleteById(cartId);
	}


	/**
	 * 更新購物車品項數量
	 * @param cartAndWishlist
	 */
	public void updateItemAmount(CartAndOrder cartAndOrder) {
		Integer itemStock = pdr.getOne(cartAndOrder.getTempProductId()).getProductStock();
		Integer amount = cartAndOrder.getShoppingAmount();
		if (amount >= itemStock) {
			amount = itemStock;
		} else if (amount < 0) {
			amount = 1;
		}
		caor.updateAmount(cartAndOrder.getCartId(), amount);
	}

	
	/**
	 * 查詢購物車內容
	 * @param userId 會員ID
	 * @return
	 */
	public List<CartAndOrder> queryCartContentById(Integer userId) {
		List<CartAndOrder> cartList = caor.findByUser_UserIdAndOrderDetailIsNull(userId);
		if (!cartList.isEmpty()) {
			for (CartAndOrder cartItem : cartList) {
				Integer stock = cartItem.getProductItem().getProductStock();
				if (stock <= 0) {
					deleteItemFromCart(cartItem.getCartId());
				} else if (stock > 0 && cartItem.getShoppingAmount() > stock) {
					cartItem.setShoppingAmount(stock);
				}
			}
		}
		return cartList;
	}
	
	
	/**
	 * 查詢購物車資訊(含數量、總金額)
	 * @param userId 會員ID
	 * @return
	 */
	public Map<String,Object> queryCartInfoById(Integer userId) {
		List<CartAndOrder> cartList = queryCartContentById(userId);
		Map<String,Object> cartInfo = new HashMap<String,Object>();
		if (!cartList.isEmpty()) {
			Integer cartQuantity = cartList.size();
			Integer totalPrice = 0;
			for (CartAndOrder cartItem : cartList) {
				totalPrice += cartItem.getProductItem().getProductPrice() * cartItem.getShoppingAmount();
			}
			cartInfo.put("cartQuantity", cartQuantity);
			cartInfo.put("totalPrice", totalPrice);
			cartInfo.put("cartList", cartList);
		}
		return cartInfo;
	}
	
	
	/**
	 * 查詢購物車品項數量
	 * @param userId 會員ID
	 * @return
	 */
	public Integer queryCartQuantityById(Integer userId) {
		return caor.countByUser_UserIdAndOrderDetailIsNull(userId);
	}
	
	
	/**
	 * 查詢會員所有訂單
	 * @param userId 會員ID
	 * @return
	 */
	public List<OrderDetail> queryUserOrder(Integer userId) {
		List<Object[]> orderNoList = caor.findOrderNo(userId);
		if (!orderNoList.isEmpty()) {
			List<Long> descOrderNoList = new ArrayList<Long>();
			for (Object[] orderNo : orderNoList) {
				for(int i = 0; i < orderNo.length; i++) {
					BigInteger bigInteger = (BigInteger) orderNo[i];
					descOrderNoList.add(bigInteger.longValue());
				}
			}
			// 將List進行DESC排列
			Collections.sort(descOrderNoList,Collections.reverseOrder());
			
			List<OrderDetail> orderList = new ArrayList<OrderDetail>();
			for (Long descOrderNo : descOrderNoList) {
				orderList.add(odr.findById(descOrderNo).get());
			}
			return orderList;
		} else {
			return null;
		}
		
	}
	
	
	/**
	 * 訂單成立
	 * @param orderDetail
	 * @param cartId 購物車品項ID
	 * @param payerAddress 付款人地址
	 * @param receiverAddress 收貨人地址
	 * @return
	 */
	public OrderDetail addOrder(OrderDetail orderDetail, Integer[] cartId, String payerAddress, String receiverAddress) {
		orderDetail.setPayerAddress(payerAddress);
		orderDetail.setReceiverAddress(receiverAddress);
		orderDetail.setStatusOrder("ordered");
		orderDetail.setStatusPayment("unpaid");
		for(int i = 0; i < cartId.length; i++) {
			CartAndOrder cartAndOrder = caor.findById(cartId[i]).get();
			Product productItem = cartAndOrder.getProductItem();
			productItem.setProductStock(productItem.getProductStock() - cartAndOrder.getShoppingAmount());
			pdr.save(productItem);
			orderDetail.getCartAndOrder().add(cartAndOrder);
			cartAndOrder.setOrderDetail(orderDetail);
		}
		return odr.save(orderDetail);
	}
	
	
	/**
	 * 產生訂單隨機交易識別碼
	 * @param orderDetail
	 * @return
	 */
	public OrderDetail createTradeIdentification(OrderDetail orderDetail) {
		String orderNo = orderDetail.getOrderNo().toString();
		Integer random = (int) Math.floor(Math.random() * 99999 + 1);
		String randomNum = String.format("%06d", random);
		String tradeIdentification = orderNo + randomNum;
		orderDetail.setTradeIdentification(tradeIdentification);
		return odr.save(orderDetail);
	}
	
	
	/**
	 * 根據訂單編號查詢單一訂單詳情
	 * @param orderNo 訂單編號
	 * @return
	 */
	public OrderDetail queryOrderDetailByOrderNo(Long orderNo) {
		return odr.findById(orderNo).get();
	}
	
	
	/**
	 * 變更訂單為取消或退貨狀態
	 * @param orderNo 訂單編號
	 * @return
	 */
	public void editOrderStatus(Long orderNo, String statusOrder, String crReason, String suggestion) {
		OrderDetail orderDetail = odr.findById(orderNo).get();
		orderDetail.setStatusOrder(statusOrder);
		orderDetail.setCrReason(crReason);
		orderDetail.setSuggestion(suggestion);
		odr.save(orderDetail);
	}
	
	
	/**
	 * 將取消之訂單內之商品數量加回至庫存量
	 * @param orderNo 訂單編號
	 */
	public void addStockBack(Long orderNo) {
		OrderDetail orderDetail = odr.findById(orderNo).get();
		Set<CartAndOrder> cartSet = orderDetail.getCartAndOrder();
		for (CartAndOrder cartItem : cartSet) {
			Integer productStock = cartItem.getProductItem().getProductStock();
			Integer shoppingAmount = cartItem.getShoppingAmount();
			cartItem.getProductItem().setProductStock(productStock + shoppingAmount);
		}
	}
	
	
	/**
	 * 變更訂單付款狀態
	 * @param orderNo 訂單編號
	 * @return
	 */
	public OrderDetail editOrderStatusPayment(Long orderNo) {
		OrderDetail orderDetail = odr.findById(orderNo).get();
		orderDetail.setStatusPayment("paid");
		return odr.save(orderDetail);
	}
	
	
	/**
	 * 後台管理：進行中訂單管理
	 * @return
	 */
	public List<OrderDetail> processingOrders(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("finished");
		list.add("cancelled");
		list.add("returning");
		return odr.findByStatusOrderNotIn(list);
	}
	
	/**
	 * 後台管理：當日新增之訂單
	 * @param orderId
	 * @return
	 */
	public Integer countingOrders(Long orderId) {
		return odr.countByOrderNoGreaterThan(orderId);
	}
}
