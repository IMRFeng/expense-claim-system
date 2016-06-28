package com.leyoho.ecs.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.leyoho.ecs.entity.Receipt;
import com.leyoho.ecs.repository.ReceiptDao;
import com.leyoho.ecs.service.IReceiptService;
import com.leyoho.ecs.service.impl.ReceiptServiceImpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptServiceTest {

	@InjectMocks
	private IReceiptService service = new ReceiptServiceImpl();
	@Mock
	ReceiptDao dao;

	@Before
	public void setup() {
//		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetReceiptById() {
		Receipt entity = getEntityStubData();
		
		when(dao.getReceipt(anyString())).thenReturn(entity);
		
		Receipt ret = service.getReceipt("1");
		verify(dao).getReceipt("1");
		
		assertNotNull("failure - expected not null", ret);
		assertEquals("failure - expected id field match", 1, ret.getReceiptId());
	}

	@Test
	public void testGetUserNames() {

		List<String> userNames = new ArrayList<String>();
		userNames.add("bob");
		Receipt receipt = this.getEntityStubData();
		when(service.getReceipt("1")).thenReturn(receipt);

		String retVal = receipt.getDescription();
		assertEquals("failure - expected description field match", "description", retVal);
	}

	private Receipt getEntityStubData() {
		Receipt entity = new Receipt();
		entity.setReceiptId(1);
		entity.setDescription("description");
		entity.setDollarType("NZD");
		return entity;
	}

}
