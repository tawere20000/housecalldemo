package com.example.demo.model.service.statistics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.bean.statisticsData.UserBehaviorComponentsPer;
import com.example.demo.model.dao.statistics.UserBehaviorComponentsPerRepository;

@Service
@Transactional
public class UserBehaviorComponentsPerService {
	
	
	@Autowired
	UserBehaviorComponentsPerRepository userBehaviorComponentsPerRepository;
	
	public List<Object[]> last7dayscompbyareachart(String category,String start ,String end){
		List<UserBehaviorComponentsPer> list = userBehaviorComponentsPerRepository
				.findAllByCategoryAndDateBetween(category,start,end);
		List<Object[]> listobj = new ArrayList<Object[]>();
		String[] datarange = new String[7];
		Double[] data0 = new Double[7];
		Double[] data1 = new Double[7];
		Double[] data2 = new Double[7];
		Double[] data3 = new Double[7];
		Iterator<UserBehaviorComponentsPer> it = list.iterator();
		int count = 0;
		while(it.hasNext()) {
			UserBehaviorComponentsPer ubcp = it.next();
			datarange[count] = ubcp.getDate().replace("-","").substring(4);
			data0[count] = (double) (Math.round(ubcp.getArticleVisitTimesPer()*10000)/100);
			data1[count] = (double) (Math.round(ubcp.getForumVisitTimesPer()*10000)/100);
			data2[count] = (double) (Math.round(ubcp.getMallVisitTimesPer()*10000)/100);
			data3[count] = (double) (Math.round(ubcp.getTroublesVisitTimesPer()*10000)/100);
			count++;
		}
		
		listobj.add(datarange);
		listobj.add(data0);
		listobj.add(data1);
		listobj.add(data2);
		listobj.add(data3);
		return listobj;
	}
	

}
