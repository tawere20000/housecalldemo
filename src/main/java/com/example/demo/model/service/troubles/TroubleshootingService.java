package com.example.demo.model.service.troubles;

import java.util.List;

import com.example.demo.model.bean.troubles.Quotation;
import com.example.demo.model.bean.troubles.TroubleshootingBean;

public interface TroubleshootingService {

	// 查所有訂單
		List<TroubleshootingBean> findAllTrouble();
		
		TroubleshootingBean findById(int id);
		
		void addTrouble(TroubleshootingBean troubleshootingBean);
		
		List<Quotation> findAllQuaotation(Integer businessOrderId);
		
		void addquotation(Quotation quotation);
		
		Quotation findQuotationById(int id);
		
		List<Quotation> getPingJunRating(String seleted,Integer userId);
		
		public void updateTrouble(TroubleshootingBean troubleshootingBean);
		
		public void deleteTroubleById(Integer businessOrderId);
			
		
}
