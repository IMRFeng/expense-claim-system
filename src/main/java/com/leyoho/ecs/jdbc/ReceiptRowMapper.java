package com.leyoho.ecs.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.leyoho.ecs.entity.Receipt;


public class ReceiptRowMapper implements RowMapper<Receipt> {

	@Override
	public Receipt mapRow(ResultSet resultSet, int line) throws SQLException {
		ReceiptExtractor receiptExtractor = new ReceiptExtractor();
		return receiptExtractor.extractData(resultSet);
	}

}
