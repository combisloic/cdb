package com.excilys.cdb.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.excilys.cdb.model.company.Company;

public class JdbcCompanyDAOTest {

    private CompanyDAO jdbcCompanyDAO;

    @BeforeTest
    public void setUp() {
        jdbcCompanyDAO = CompanyDAO.getInstance();
    }

    @Test
    public void notNullInstanceTest() {
        assertNotNull(jdbcCompanyDAO);
    }

    @Test
    public void sharedInstanceTest() {
        assertEquals(jdbcCompanyDAO, CompanyDAO.getInstance());
    }

    @Test
    public void listCompaniesTest() {
        List<Company> companies = jdbcCompanyDAO.list(0, 10);
        assertTrue(companies.size() <= 10);
    }

    @Test
    public void getCompanyTest() {
        Optional<Company> company = jdbcCompanyDAO.get(10L);
        assertTrue(!company.isPresent() || company.get().getId() == 10L);
    }
}