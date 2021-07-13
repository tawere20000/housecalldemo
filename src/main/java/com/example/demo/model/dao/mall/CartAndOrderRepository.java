package com.example.demo.model.dao.mall;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.mall.CartAndOrder;


@Repository
public interface CartAndOrderRepository extends JpaRepository<CartAndOrder,Integer>{
	
	/**
	 * 查詢購物車內單一商品資訊
	 * @param userId 會員ID
	 * @param productId 商品ID
	 * @return
	 */
	List<CartAndOrder> findByUser_UserIdAndProductItem_ProductIdAndOrderDetail_OrderNoIsNull(Integer userId, Integer productId);
	
	
	/**
	 * 查詢購物車內容
	 * @param userId 會員ID
	 * @return
	 */
	List<CartAndOrder> findByUser_UserIdAndOrderDetailIsNull(Integer userId);
	
	
	/**
	 * 更新購物車品項數量
	 * @param cartId 購物車品項ID
	 * @param shoppingAmount 修改後之商品數量
	 * @return
	 */
	default Integer updateAmount(Integer cartId, Integer shoppingAmount) {
		CartAndOrder updateCartAndOrder = findById(cartId).get();
		updateCartAndOrder.setShoppingAmount(shoppingAmount);
		save(updateCartAndOrder);
		return 1;
	}
	
	
	/**
	 * 查詢購物車品項數量
	 * @param userId 會員ID
	 * @return
	 */
	Integer countByUser_UserIdAndOrderDetailIsNull(Integer userId);
	
	/**
	 * 查詢所有訂單
	 * @param userId 會員ID
	 * @return
	 */
	@Query(value = "SELECT fk_order_no FROM cart_and_order WHERE fk_user_id = ? AND fk_order_no IS NOT NULL GROUP BY fk_order_no", nativeQuery = true)
	List<Object[]> findOrderNo(Integer userId);
	
}
