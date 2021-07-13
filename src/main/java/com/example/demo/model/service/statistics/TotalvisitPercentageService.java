package com.example.demo.model.service.statistics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.bean.statisticsData.TotalvisitPercentage;
import com.example.demo.model.dao.statistics.TotalvisitPercentageRepository;


@Service
@Transactional
public class TotalvisitPercentageService {
	
	@Autowired
	TotalvisitPercentageRepository totalvisitPercentageRepository;
	
	public Map<String,Object[]> last7daysdata(String start,String end){
		List<TotalvisitPercentage> totalvisit = totalvisitPercentageRepository.findlastdays(start,end);
		String[] daterange = new String[7];
		Double[] data0 = new Double[7];
		Double[] data1 = new Double[7];
		Double[] data2 = new Double[7];
		Double[] data3 = new Double[7];
		Map<String,Object[]> map = new HashMap<String,Object[]>();
		Iterator<TotalvisitPercentage> it = totalvisit.iterator();
		int count = 0;
		while(it.hasNext()) {
			TotalvisitPercentage tp = it.next();
			String datetrim = tp.getDate().substring(tp.getDate().length()-5);
			daterange[count] = datetrim.replace("-", "");
			data0[count] = (double) Math.round(tp.getArticleVisitPer()*10000)/100;
			data1[count] = (double) Math.round(tp.getForumVisitPer()*10000)/100;
			data2[count] = (double) Math.round(tp.getMallVisitPer()*10000)/100;
			data3[count] = (double) Math.round(tp.getTroublesVisitPer()*10000)/100;
			count++;
		}
		map.put("data0", data0);
		map.put("data1", data1);
		map.put("data2", data2);
		map.put("data3", data3);
		map.put("daterange", daterange);
		return map;
	}
	
	public List<TotalvisitPercentage> getlast7daysdata(String start,String end){
		return totalvisitPercentageRepository.findlastdays(start,end);
	}

}
