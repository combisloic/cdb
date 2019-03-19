package com.excilys.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class CompanyTest {

	private Company company;
	private Company companyWithoutId;
	private Company companyWithoutName;

	private static final String NAME = "Apple";
	private static final String NEW_NAME = "Microsoft";
	private static final long ID = 21L;

	@Before
	public void setUp() {
		company = new Company(ID, NAME);
		companyWithoutId = new Company(NAME);
		companyWithoutName = new Company(ID);
	}

	@Test
	public void getIdTest() {
		assertEquals(company.getId(), ID);
	}

	@Test
	public void getIdWhenNoIdTest() {
		assertEquals(companyWithoutId.getId(), 0L);
	}

	@Test
	public void getNameTest() {
		assertEquals(company.getName(), NAME);
	}

	@Test
	public void getNameWhenNoName() {
		assertNull(companyWithoutName.getName());
	}

	@Test
	public void setNameTest() {
		String newName = NEW_NAME;
		company.setName(newName);
		assertEquals(company.getName(), NEW_NAME);
	}

	@Test
	public void setNullNameTest() {
		company.setName(null);
		assertNull(company.getName());
	}
}