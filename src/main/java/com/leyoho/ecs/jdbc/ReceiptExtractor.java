package com.leyoho.ecs.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.leyoho.ecs.entity.Receipt;

public class ReceiptExtractor implements ResultSetExtractor<Receipt> {

	public Receipt extractData(ResultSet resultSet) throws SQLException,
			DataAccessException {

		Receipt receipt = new Receipt();

		receipt.setReceiptId(resultSet.getInt(1));
		receipt.setDate(resultSet.getString(2));
		receipt.setDescription(resultSet.getString(3));
		receipt.setCategory(resultSet.getString(4));
		receipt.setCost(resultSet.getDouble(5));
		receipt.setClaim(resultSet.getString(6));
		receipt.setFileName(resultSet.getString(7));
		receipt.setPreCost(resultSet.getDouble(8));
		receipt.setDollarType(resultSet.getString(9));
		receipt.setImage(resultSet.getBinaryStream(10));
		receipt.setThumbnail(resultSet.getBinaryStream(11));

		return receipt;
	}

}
