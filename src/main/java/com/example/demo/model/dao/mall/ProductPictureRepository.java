package com.example.demo.model.dao.mall;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.mall.Product;
import com.example.demo.model.bean.mall.ProductPicture;

@Repository
public interface ProductPictureRepository extends JpaRepository<ProductPicture,Integer>{

	public List<ProductPicture> findAllByProduct(Product product);
	
}
