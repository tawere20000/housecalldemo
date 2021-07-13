package com.example.demo.model.service.troubles;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.bean.troubles.Quotation;
import com.example.demo.model.bean.troubles.TroubleshootingBean;
import com.example.demo.model.dao.troubles.QuotationRepository;
import com.example.demo.model.dao.troubles.TroubleshootingRepository;
@Service
@Transactional
public class TroubleshootingServiceImpl implements TroubleshootingService {
@Autowired
TroubleshootingRepository troubleshootingRepository;
@Autowired
QuotationRepository quotationRepository;
	@Override
	public List<TroubleshootingBean> findAllTrouble() {
		
		List<TroubleshootingBean> troubleshooting = troubleshootingRepository.findAll();
		
		return troubleshooting;
	}
	@Override
	public TroubleshootingBean findById(int id) {
		
		return troubleshootingRepository.findById(id).get();
	}
	@Override
	public void addTrouble(TroubleshootingBean troubleshootingBean) {

		troubleshootingRepository.save(troubleshootingBean);
	}
	@Override
	public List<Quotation> findAllQuaotation(Integer businessOrderId) {
		return quotationRepository.findByBusinessOrderId_businessOrderId(businessOrderId);
	}
	@Override
	public void addquotation(Quotation quotation) {
		quotationRepository.save(quotation);
	}
	@Override
	public Quotation findQuotationById(int id) {
		
		return quotationRepository.findById(id).get();
	}
	@Override
	public List<Quotation> getPingJunRating(String seleted, Integer userId) {
		
		return quotationRepository.findBySelectedAndVendorId_userId(seleted, userId);
	}
	@Override
	public void updateTrouble(TroubleshootingBean troubleshootingBean) {

		troubleshootingRepository.save(troubleshootingBean);
	}
	@Override
	public void deleteTroubleById(Integer businessOrderId) {

		troubleshootingRepository.deleteById(businessOrderId);
	}
	
	
	

}
