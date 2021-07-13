package com.example.demo.model.dao.mall;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.mall.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{
	
	Page<Product> findByProductStateAndProductPriceBetween(String productState, Integer minPrice, Integer maxPrice, Pageable pageable);
	
	Page<Product> findByProductTypeAndProductStateAndProductPriceBetween(String productType, String productState, Integer minPrice, Integer maxPrice, Pageable pageable);
	
	Page<Product> findBySubTypeAndProductStateAndProductPriceBetween(String subType, String productState, Integer minPrice, Integer maxPrice, Pageable pageable);
	
	Page<Product> findByproductNameContainingAndProductStateAndProductPriceBetween(String keyword, String productState, Integer minPrice, Integer maxPrice, Pageable pageable);
	
	@Query(value = "SELECT TOP (7) * FROM product WHERE product_state = ? ORDER BY NEWID()", nativeQuery = true)
	List<Product> findRandomProduct(String productState);
	
	@Query(value = "SELECT TOP (8) * FROM product WHERE product_state = ? ORDER BY product_create_time DESC", nativeQuery = true)
	List<Product> findNewestProduct(String productState);
	
	default Integer updateProduct(Integer productId, Map<String, Object> updateitems) {
		Product product = findById(productId).get();
		try {
			for (Entry<String, Object> e : updateitems.entrySet()) {
				PropertyDescriptor pd = new PropertyDescriptor(e.getKey(),Product.class);
				pd.getWriteMethod().invoke(product, e.getValue());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		save(product);
		return 1;
	}
	
}
