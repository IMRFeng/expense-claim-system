package com.leyoho.ecs;

import java.sql.SQLException;

import org.junit.*;
import org.springframework.dao.DataAccessException;

import com.leyoho.ecs.entity.Receipt;
import com.leyoho.ecs.jdbc.ReceiptExtractor;

import static org.junit.Assert.*;

public class ReceiptExtractorTest {

	private MockResultSet resultSet;

	@Before
	public void setup() {
		resultSet = new MockResultSet();
		resultSet.setReceiptID(1);
		resultSet.setDate("08/07/2014");
		resultSet.setDescription("Over the counter medicine");
		resultSet.setCategory("Health");
		resultSet.setCost(4.0);
		resultSet.setClaim("Yes");
	}

	@Test
	public void testExtractData() throws DataAccessException, SQLException {
		Receipt receipt = new ReceiptExtractor().extractData(resultSet);
		assertEquals(1, receipt.getReceiptId());
		assertEquals("08/07/2014", receipt.getDate());
		assertEquals("Over the counter medicine", receipt.getDescription());
		assertEquals("Health", receipt.getCategory());
		assertEquals(4.0, receipt.getCost(), 0.0);
		assertEquals("Yes", receipt.getClaim());
	}

	@Test
	public void testExtractDataNotNull() throws DataAccessException, SQLException {
		Receipt receipt = new ReceiptExtractor().extractData(resultSet);
		assertNotNull(receipt);
	}

	// This method fails if it does not throw the named exception
	@Test(expected = NullPointerException.class)
	public void testExtractDataThrowsNullPointer() throws DataAccessException, SQLException {
		resultSet = null;
		new ReceiptExtractor().extractData(resultSet);
	}

	@Test
	public void testExtractDataInvalidReceiptID() {
		boolean invalidReceiptID = false;
		resultSet = new MockResultSet();
		resultSet.setReceiptID(-1);
		try {
			new ReceiptExtractor().extractData(resultSet);
		} catch (SQLException e) {
			invalidReceiptID = true;
		} catch (DataAccessException e) {
			fail("Unexpected DataAccessException: " + e.getMessage());
		}
		assertTrue("Invalid Receipt ID, SQLException is expected to be thrown", invalidReceiptID);
	}

	@Test
	public void testExtractDataAssertTrue() throws DataAccessException, SQLException {
		resultSet = new MockResultSet();
		resultSet.setCategory("Education");

		Receipt receipt = new ReceiptExtractor().extractData(resultSet);
		assertTrue("Category should be equal", "Education".equalsIgnoreCase(receipt.getCategory()));
	}

}
