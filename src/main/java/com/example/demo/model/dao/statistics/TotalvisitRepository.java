package com.example.demo.model.dao.statistics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.statisticsData.Totalvisit;
import com.example.demo.model.bean.statisticsData.TotalvisitPercentage;

@Repository
public interface TotalvisitRepository extends JpaRepository<Totalvisit,String>{
	

	
	@Query(value="select * from [totalvisit] WHERE [date] between ? and ?",nativeQuery=true)
	public List<Totalvisit> findlast7days(String datestrat ,String dateend);
	
}
