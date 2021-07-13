package com.example.demo.model.service.mall;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.bean.mall.Product;
import com.example.demo.model.bean.mall.ProductPicture;
import com.example.demo.model.dao.mall.ProductPictureRepository;

@Service
@Transactional
public class ProductPictureService {
	
	@Autowired
	ProductPictureRepository productPictureRepository;
	
	public void productPicOnstock(ProductPicture productPicture) {
		productPictureRepository.save(productPicture);
	}
	
	public void deletepic(Integer productpicId) {
		productPictureRepository.deleteById(productpicId);
	}
	
	public List<ProductPicture> productPicList(Product product){
		  return productPictureRepository.findAllByProduct(product);
		 }

}
