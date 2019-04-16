package com.excilys.cdb.persistence.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.excilys.cdb.config.SpringConfig;
import com.excilys.cdb.model.company.Company;

@RunWith(SpringRunner.class)
public class JdbcCompanyDAOTest {

    @Autowired
    private CompanyDAO companyDAO;

    private AnnotationConfigApplicationContext ctx;

    @BeforeTest
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        ctx.refresh();
        companyDAO = ctx.getBean(CompanyDAO.class);
    }

    @Test
    public void notNullInstanceTest() {
        assertNotNull(companyDAO);
    }

    @Test
    public void listCompaniesTest() {
        List<Company> companies = companyDAO.list(0, 10);
        assertTrue(companies.size() <= 10);
    }

    @Test
    public void getCompanyTest() {
        Optional<Company> company = companyDAO.get(10L);
        assertTrue(!company.isPresent() || company.get().getId() == 10L);
    }

    @AfterTest
    public void end() {
        ctx.close();
    }
}
