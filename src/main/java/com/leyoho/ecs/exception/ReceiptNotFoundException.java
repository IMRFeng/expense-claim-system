package com.leyoho.ecs.exception;

public class ReceiptNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ReceiptNotFoundException(String receiptId) {
		super("Receipt not found with id: " + receiptId);
	}
}
