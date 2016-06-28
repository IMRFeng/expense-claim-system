package com.leyoho.ecs.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.leyoho.ecs.entity.Receipt;


public class MockitoDaoSupport extends NamedParameterJdbcDaoSupport {
	public String query(String sql) {
        return getJdbcTemplate().queryForObject(sql, String.class);
    }
	
	public Receipt obtainReceipt(){
		return getJdbcTemplate().queryForObject("select * from receipt", Receipt.class);
	}
}
