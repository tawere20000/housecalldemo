package com.example.demo.model.dao.troubles;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.bean.troubles.Quotation;


public interface QuotationRepository extends JpaRepository<Quotation, Integer> {
	
	List<Quotation> findByVendorId_UserId(Integer iD);

	Quotation findByBusinessOrderId_BusinessOrderId(Integer iD);
	
	List<Quotation> findByBusinessOrderId_businessOrderId(Integer businessOrderId);

	List<Quotation> findBySelectedAndVendorId_userId(String seleted,Integer userId);


}
