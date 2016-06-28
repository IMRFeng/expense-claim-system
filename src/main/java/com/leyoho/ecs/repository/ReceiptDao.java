package com.leyoho.ecs.repository;

import java.util.List;

import com.leyoho.ecs.entity.Receipt;

public interface ReceiptDao {
	public void insertData(Receipt receipt);

	public List<Receipt> getReceiptList();

	public void updateData(Receipt receipt);

	public void deleteData(String id);

	public Receipt getReceipt(String id);

}
