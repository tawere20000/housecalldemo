package com.example.demo.model.bean.mall;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class DateSeqGenerator implements IdentifierGenerator {

	// 前筆流水號之日期
	private Long day;

	// 下一流水號碼
	private Long next;

	// 流水號碼位數
	private Long length = 4l;

	// 從資料庫查詢最新一筆流水號之SQL語句
	private String sql = "SELECT MAX(order_no) FROM order_detail";

	public DateSeqGenerator() {
		super();
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

		if (sql != null) {
			getNext(session);
		}
		
		Long num = 0l;

		Long currentDay = getCurrentDay();

		if (day.equals(currentDay)) {
			num = (long) (day * Math.pow(10l, length) + (++next));
		} else {
			day = currentDay;
			next = 1l;
			num = (long) (day * Math.pow(10l, length) + next);
		}

		return num;
	}

	private void getNext(SharedSessionContractImplementor session) {

		Connection conn = session.connection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				next = rs.getLong(1);
				if (rs.wasNull()) {
					day = getCurrentDay();
					next = 1l;
				} else {
					day = Long.valueOf((next + "").substring(0, 8));
					next = Long.valueOf((next + "").substring(8));
				}
			} else {
				next = 1l;
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();	
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Long getCurrentDay() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String day = simpleDateFormat.format(date);
		return Long.valueOf(day);
	}

}
