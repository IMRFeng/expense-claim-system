package com.leyoho.ecs.service;

import java.util.List;

import com.leyoho.ecs.entity.Receipt;


public interface IReceiptService {
	public void insertData(Receipt receipt);

	public List<Receipt> getReceiptList();

	public void deleteData(String id);

	public Receipt getReceipt(String id);

	public void updateData(Receipt receipt);
}
