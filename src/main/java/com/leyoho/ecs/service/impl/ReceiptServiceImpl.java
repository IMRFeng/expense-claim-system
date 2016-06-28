package com.leyoho.ecs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leyoho.ecs.entity.Receipt;
import com.leyoho.ecs.repository.ReceiptDao;
import com.leyoho.ecs.service.IReceiptService;

@Service(value = "receiptService")
public class ReceiptServiceImpl implements IReceiptService {

	@Autowired
	private ReceiptDao receiptdao;

	@Override
	public void insertData(Receipt receipt) {
		receiptdao.insertData(receipt);
	}

	@Override
	public List<Receipt> getReceiptList() {
		return receiptdao.getReceiptList();
	}

	@Override
	public void deleteData(String id) {
		receiptdao.deleteData(id);

	}

	@Override
	public Receipt getReceipt(String id) {
		return receiptdao.getReceipt(id);
	}

	@Override
	public void updateData(Receipt receipt) {
		receiptdao.updateData(receipt);

	}
}
