package com.example.demo.model.dao.statistics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.statisticsData.TotalvisitPercentage;


@Repository
public interface TotalvisitPercentageRepository extends JpaRepository<TotalvisitPercentage, String>{
	
	@Query(value="select * from [totalvisit_percentage] WHERE [date] between ? and ?",nativeQuery=true)
	public List<TotalvisitPercentage> findlastdays(String datestart,String dateend);

}
