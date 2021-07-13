package com.example.demo.model.dao.mall;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.mall.OrderDetail;

 

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
	
	List<OrderDetail> findByStatusOrderNotIn(List<String> list);
	
	Integer countByOrderNoGreaterThan(Long orderNo);

}
