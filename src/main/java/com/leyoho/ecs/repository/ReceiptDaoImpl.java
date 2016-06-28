package com.leyoho.ecs.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.leyoho.ecs.entity.Receipt;
import com.leyoho.ecs.jdbc.ReceiptRowMapper;

@Repository
public class ReceiptDaoImpl implements ReceiptDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void insertData(Receipt receipt) {
		String sql = "INSERT INTO Receipt "
				+ "(date_stamp, description, category, cost, claim, file_name, pre_cost, dollar_type, image, thumbnail) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql,
				new Object[] { receipt.getDate(), receipt.getDescription(), receipt.getCategory(), receipt.getCost(),
						receipt.getClaim(), receipt.getFileName(), receipt.getPreCost(), receipt.getDollarType(),
						receipt.getImage(), receipt.getThumbnail() });

	}

	public List<Receipt> getReceiptList() {
		List<Receipt> receiptList = new ArrayList<Receipt>();

		String sql = "select * from Receipt order by id desc";

		receiptList = jdbcTemplate.query(sql, new ReceiptRowMapper());
		return receiptList;
	}

	@Override
	public void deleteData(String id) {
		String sql = "delete from Receipt where id=" + id;
		jdbcTemplate.update(sql);

	}

	@Override
	public void updateData(Receipt receipt) {

		String sql = "UPDATE Receipt set date_stamp = ?, description = ?, category = ?, cost = ?, claim = ?"
				+ ", file_name = ?, pre_cost = ?, dollar_type = ?, image = ?, thumbnail = ? where id = ?";

		jdbcTemplate.update(sql,
				new Object[] { receipt.getDate(), receipt.getDescription(), receipt.getCategory(), receipt.getCost(),
						receipt.getClaim(), receipt.getFileName(), receipt.getPreCost(), receipt.getDollarType(), receipt.getImage(), receipt.getThumbnail(),
						receipt.getReceiptId() });

	}

	@Override
	public Receipt getReceipt(String id) {
		List<Receipt> receiptList = new ArrayList<Receipt>();
		String sql = "select * from Receipt where id= " + id;
		receiptList = jdbcTemplate.query(sql, new ReceiptRowMapper());
		return receiptList.get(0);
	}

}
