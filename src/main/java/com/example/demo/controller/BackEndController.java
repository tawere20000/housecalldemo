package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.bean.statisticsData.DaysRegister;
import com.example.demo.model.bean.statisticsData.Totalvisit;
import com.example.demo.model.bean.statisticsData.TotalvisitPercentage;
import com.example.demo.model.bean.statisticsData.UserBehaviorComponents;
import com.example.demo.model.bean.statisticsData.UserBehaviorComponentsDayper;
import com.example.demo.model.service.mall.CartAndOrderService;
import com.example.demo.model.service.statistics.DaysRegisterService;
import com.example.demo.model.service.statistics.TotalvisitPercentageService;
import com.example.demo.model.service.statistics.TotalvisitService;
import com.example.demo.model.service.statistics.UserBehaviorComponentsDayperService;
import com.example.demo.model.service.statistics.UserBehaviorComponentsPerService;
import com.example.demo.model.service.statistics.UserBehaviorComponentsService;


//@Controller
public class BackEndController {
	
//	@Autowired
//	RedisTemplate<String,Object> redisTemplate;
//	
//	@Autowired
//	TotalvisitPercentageService totalvisitPercentageService;
//	
//	@Autowired
//	TotalvisitService totalvisitService;
//	
//	@Autowired
//	UserBehaviorComponentsDayperService userBehaviorComponentsDayperService;
//
//	@Autowired
//	UserBehaviorComponentsService userBehaviorComponentsService;
//	
//	@Autowired
//	UserBehaviorComponentsPerService userBehaviorComponentsPerService;
//	
//	@Autowired
//	CartAndOrderService cartAndOrderService;
//	
//	@Autowired
//	DaysRegisterService daysRegisterService;
//	
//	Integer[] todaycomparea = {0,0,0,0};
//	
//	Integer totalVisitAll = null;
//	
//	
//	@RequestMapping(value = "/statisticsDatabycomp")
//	public String statisticsDatabycomp() {
//		return "statistics/visitorchart-bycomp";
//	}
//	
//	@RequestMapping(value = "/statisticsDatabyarea")
//	public String statisticsDatabyarea() {
//		return "statistics/visitorchart-byarea";
//	}
//	
//	@RequestMapping(value = "/statisticsDataarea-bycomp")
//	public String statisticsDataareabycomp() {
//		return "statistics/area-bycomp";
//	}
//	
//	@RequestMapping(value = "/statisticsDataarea-byarea")
//	public String statisticsDataareabyarea() {
//		return "statistics/comp-byarea";
//	}
//	
//		public String getYesterdayDate() {
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(new Date());
//			cal.add(Calendar.DATE,-1);
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			return simpleDateFormat.format(cal.getTime());
//		}
//		
//		public String getYester7dayDate() {
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(new Date());
//			cal.add(Calendar.DATE,-8);
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			return simpleDateFormat.format(cal.getTime());
//		}
//		
//		public void totalVisitAll() {
//			Set<TypedTuple<Object>> zsetresult = redisTemplate.opsForZSet().rangeWithScores("totalvisit", 0, -1);
//			for(TypedTuple<Object> result : zsetresult) {
//				if(result.getValue().equals("articleVisit")) {
//					todaycomparea[0] = result.getScore().intValue();
//				}else if(result.getValue().equals("forumVisit")) {
//					todaycomparea[1] = result.getScore().intValue();
//				}else if(result.getValue().equals("mallVisit")) {
//					todaycomparea[2] = result.getScore().intValue();
//				}else if(result.getValue().equals("troublesVisit")) {
//					todaycomparea[3] = result.getScore().intValue();
//				}
//			}
//			totalVisitAll = todaycomparea[0]+todaycomparea[1]+todaycomparea[2]+todaycomparea[3];
//		}
//	
//	
//	@RequestMapping(value = "/backendindex")
//	public String getquickview(Model model) {
//		Set<TypedTuple<Object>> viewcount = redisTemplate.opsForZSet().reverseRangeWithScores("totalvisit", 0,1);
//		Iterator<TypedTuple<Object>> it = viewcount.iterator();
//		if(it.hasNext()) {
//			TypedTuple<Object> result = it.next();
//			if(result.getValue().equals("mallVisit")) {
//				model.addAttribute("hotarea","商城");
//			}else if(result.getValue().equals("articleVisit")) {
//				model.addAttribute("hotarea","評論區");
//			}else if(result.getValue().equals("forumVisit")) {
//				model.addAttribute("hotarea","討論區");
//			}else if(result.getValue().equals("troublesVisit")) {
//				model.addAttribute("hotarea","疑難雜症區");
//			}
//			model.addAttribute("hotvisitors", result.getScore().intValue());
//		}
//		
//		Integer registcount = (Integer)redisTemplate.opsForValue().get("todayregist");
//		model.addAttribute("registcount", registcount);
//		
//		Integer todayorders = cartAndOrderService.countingOrders(202107090000L);///////////
//		model.addAttribute("todayorders", todayorders);
//		
//		return "backendIndex";
//	}
//	
//	@RequestMapping(value = "/todaycompbyarea")
//	@ResponseBody
//	public Integer[] todaycompbyarea(){
//		if(totalVisitAll==null) {
//			totalVisitAll();
//			return todaycomparea;
//		}else {
//			return todaycomparea;
//		}
//	}
//	
//	@RequestMapping(value = "/todaycomp")
//	@ResponseBody
//	public List<Map<String,Object>> todaycomp(){
//		Integer user = 0;
//		Integer vendor = 0;
//		if(redisTemplate.hasKey("user")) {
//		user = (Integer) redisTemplate.opsForValue().get("user");
//		}
//		if(redisTemplate.hasKey("vendor")) {
//		vendor = (Integer) redisTemplate.opsForValue().get("vendor");
//		}
//		if(totalVisitAll==null) {
//			totalVisitAll();
//		}
//		Integer vistor = totalVisitAll-user-vendor;
//		Map<String,Object> map1 = new HashMap<String,Object>();
//		map1.put("label","廠商");
//		map1.put("value",vendor);
//		Map<String,Object> map2 = new HashMap<String,Object>();
//		map2.put("label","會員");
//		map2.put("value",user);
//		Map<String,Object> map3 = new HashMap<String,Object>();
//		map3.put("label","訪客");
//		map3.put("value",vistor);
//		List<Map<String,Object>> listmap = new ArrayList<Map<String,Object>>();
//		listmap.add(map1);
//		listmap.add(map2);
//		listmap.add(map3);
//		return listmap;
//	}
//	
//	
//	@RequestMapping(value = "/backend7daystotalvisit")
//	@ResponseBody
//	public List<Map<String,Object>> backend7daystotalvisit() {
//		List<Map<String,Object>> listmap = new ArrayList<Map<String,Object>>();
//		List<Totalvisit> list = totalvisitService.getlast7daysdata(getYester7dayDate(),getYesterdayDate());
//		Iterator<Totalvisit> it = list.iterator();
//		while(it.hasNext()) {
//			Totalvisit obj = it.next();
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("period",obj.getDate().replace("-","").substring(4));
//			map.put("總數",obj.getTotalVisit());
//			listmap.add(map);
//		}
//		return listmap;
//	}
//	
//	
//	@RequestMapping(value = "/backend7daysnewreg")
//	@ResponseBody
//	public List<Map<String,Object>> backend7daysnewreg(Model model){
//		List<Map<String,Object>> listmap = new ArrayList<Map<String,Object>>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date nowDate = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, -7);
//        calendar.getTime();
//		List<DaysRegister> list = daysRegisterService.last7daysnewregist(calendar.getTime(),nowDate);
//		Iterator<DaysRegister> it = list.iterator();
//		while(it.hasNext()) {
//			DaysRegister obj = it.next();
//			Map<String,Object> map = new HashMap<String,Object>();
//			String date = sdf.format(obj.getDate()).replace("-","").substring(4);
//			map.put("y",date);
//			map.put("人次",obj.getNewRegister());
//			listmap.add(map);
//		}
//		return listmap;
//	}
//	
//	
//	@RequestMapping(value = "/ordermanage")
//	public String ordermanagetable(Model model) {
//		model.addAttribute("orderlist",cartAndOrderService.processingOrders());
//		return "table/ordermanage";
//	}
//	
//	
//	@RequestMapping(value = "/last7daystotalvisit")
//	public String last7daystotalvisit(Model model) {
//		List<Totalvisit> last7daystotalvisit = totalvisitService.getlast7daysdata(getYester7dayDate(),getYesterdayDate());
//		model.addAttribute("last7daystotalvisit", last7daystotalvisit);
//		return "statistics/visitorchart-byarea :: last7daystotalvisit";
//	}
//
//	
//	@RequestMapping(value = "/last7daystotalvisitper")
//	public String last7daystotalvisitper(Model model) {
//		List<TotalvisitPercentage> last7daystotalvisitper = totalvisitPercentageService.getlast7daysdata(getYester7dayDate(),getYesterdayDate());
//		model.addAttribute("last7daystotalvisitper", last7daystotalvisitper);
//		return "statistics/visitorchart-byarea :: last7daystotalvisitper";
//	}
//	
//	
//	@RequestMapping(value = "/chartbyarea")
//	@ResponseBody
//	public Map<String,Object[]>  weekvisitareadata() {
//		return totalvisitPercentageService.last7daysdata(getYester7dayDate(),getYesterdayDate());
//	}
//	
//	
//	@RequestMapping(value = "/chartbycomponent/{area}")
//	@ResponseBody
//	public List<Map<String,Object>> weekvisitcomponentdata(@PathVariable("area") String area) {
//		return userBehaviorComponentsDayperService.findlast7daysComponent(area,getYester7dayDate(),getYesterdayDate());
//	}
//	
//	
//	@RequestMapping(value = "/last7daysvisitcompertab")
//	public String last7daystotalvisitcompertab(Model model) {
//		List<UserBehaviorComponentsDayper> last7daysvisitcompertab = userBehaviorComponentsDayperService.findlast7daysComponentperTab("總計",getYester7dayDate(),getYesterdayDate());
//		model.addAttribute("last7daysvisitcompertab", last7daysvisitcompertab);
//		return "statistics/visitorchart-bycomp :: last7daysvisitcompertab";
//	}
//	
//	@RequestMapping(value = "/last7daysvisitcomptab/{components}")
//	public String last7daystotalvisitcomtab(@PathVariable("components") String components,Model model) {
//		List<Object[]> last7daysvisitcomptab = userBehaviorComponentsService.findlast7daysComponentTab(components,getYester7dayDate(),getYesterdayDate());
//		model.addAttribute("comptabtalvisit_times", last7daysvisitcomptab);
//		if(components.equals("totalvisit_times")) {
//		return "statistics/visitorchart-bycomp :: comptabtalvisit_times";
//		}
//		else {
//			return "statistics/area-bycomp :: comptabtalvisit_times";
//		}
//	}
//	
//	@RequestMapping(value = "/last7daysvisitcompbyarea/{components}")
//	@ResponseBody
//	public List<Object[]> last7daysvisitcompbyarea(@PathVariable("components")String components) {
//		return userBehaviorComponentsPerService.last7dayscompbyareachart(components,getYester7dayDate(),getYesterdayDate());
//	}
//	
//	
//	@RequestMapping(value = "/last7daysvisitcompbyareatab/{components}")
//	public String last7daysvisitcompbyareatab(@PathVariable("components")String components,Model model) {
//		List<UserBehaviorComponents> list = userBehaviorComponentsService.last7dayscompbyarea(components,getYester7dayDate(),getYesterdayDate());
//		model.addAttribute("visitcompbyareatab",list);
//		return "statistics/comp-byarea :: visitcompbyareatab";
//	}
	


}
