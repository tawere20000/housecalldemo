package com.example.demo.model.dao.statistics;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class UserBehaviorComponentsDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	

	public List<Object[]> findlastdays(String datestart,String dateend,String column){
		String sql = "select * FROM\n"
				+ "(SELECT [date],[category],["+column+"] \n"
				+ "    from [user_behavior_components] WHERE [date] between ? and ?) t \n"
				+ "PIVOT(\n"
				+ "    SUM(["+column+"])\n"
				+ "    FOR [category] IN ([廠商],[會員],[訪客])\n"
				+ ") p;";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1,datestart);
		query.setParameter(2,dateend);
		return (List<Object[]>)query.getResultList();
	
	}; 
	

}
