package com.example.demo.model.service.mall;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.bean.mall.Product;
import com.example.demo.model.dao.mall.ProductRepository;


@Service
@Transactional
public class InventoryService {
	
	@Autowired
	private ProductRepository productRepository;

	public void productOnStock(Product product) {
		productRepository.save(product);
	}

	public void productRemovedFromStock(int productId) {
		productRepository.deleteById(productId);
	}

	public void updateProductInfo(Integer pkid, Map<String, Object> updateitems) {
		productRepository.updateProduct(pkid,updateitems);
	}
	
	public boolean productExit(int productId) {
		if(productRepository.existsById(productId)) {
			return true;
		}else {
			return false;
		}
	}
	
	public Product queryProductByID(int productId) {
		return productRepository.findById( productId).get();
	}

	
	public Page<Product> pageForInventory(int pageNo, int pageSize){
		Pageable pageable = PageRequest.of(pageNo-1,pageSize,Sort.by("productId").ascending());
		return productRepository.findAll(pageable);
	}
	
	
	/********** 以下為商城使用 ************/
	public Page<Product> pageForMallAndPrice(int pageNo, int pageSize, Integer minPrice, Integer maxPrice) {
		Pageable pageable = PageRequest.of(pageNo-1,pageSize,Sort.by("productId").descending());
		return productRepository.findByProductStateAndProductPriceBetween("上架", minPrice, maxPrice, pageable);
	}
	
	public Page<Product> pageForSearchingTypeAndStateAndPrice(int pageNo, int pageSize,String productType, Integer minPrice, Integer maxPrice){
		Pageable pageable = PageRequest.of(pageNo-1,pageSize,Sort.by("productId").ascending());
		return productRepository.findByProductTypeAndProductStateAndProductPriceBetween(productType, "上架", minPrice, maxPrice, pageable);
	}
	
	public Page<Product> pageForSearchingSubTypeAndStateAndPrice(int pageNo, int pageSize,String subType, Integer minPrice, Integer maxPrice){
		Pageable pageable = PageRequest.of(pageNo-1,pageSize,Sort.by("productId").ascending());
		return productRepository.findBySubTypeAndProductStateAndProductPriceBetween(subType, "上架", minPrice, maxPrice, pageable);
	}
	
	public Page<Product> pageForSearchingKeywordAndStateAndPrice(int pageNo, int pageSize,String keyword, Integer minPrice, Integer maxPrice){
		Pageable pageable = PageRequest.of(pageNo-1,pageSize,Sort.by("productId").ascending());
		return productRepository.findByproductNameContainingAndProductStateAndProductPriceBetween(keyword, "上架", minPrice, maxPrice, pageable);
	}
	
	public List<Product> getRandomProduct(){
		return productRepository.findRandomProduct("上架");
	}

	public List<Product> queryNewestProduct() {
		return productRepository.findNewestProduct("上架");
	}

}
