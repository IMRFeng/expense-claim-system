package com.leyoho.ecs.dao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import com.leyoho.ecs.entity.Receipt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ReceiptDaoTest {
	@Mock
    private JdbcTemplate jdbcTemplate;
	@InjectMocks
    private MockitoDaoSupport dao;
	
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    	Receipt receipt = createReceipt();
    	
        Mockito.when(jdbcTemplate.queryForObject(anyString(), Matchers.eq(Receipt.class))).thenReturn(receipt);
    }

	private Receipt createReceipt() {
		Receipt receipt = new Receipt();
    	receipt.setReceiptId(1);
    	receipt.setDescription("Over the counter medicine");
    	receipt.setDate("04/11/2015");
    	receipt.setCategory("Health");
    	
    	receipt.setCost(4.0);
    	
    	receipt.setClaim("Yes");
		return receipt;
	}

    @Test
    public void testGetReceiptDesc() {
        Receipt receipt = dao.obtainReceipt();

        assertEquals("Over the counter medicine", receipt.getDescription());

        verify(jdbcTemplate).queryForObject(anyString(), eq(Receipt.class));
    }
}
