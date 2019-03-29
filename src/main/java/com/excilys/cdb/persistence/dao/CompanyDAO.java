package com.excilys.cdb.persistence.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.CompanySQLMapper;
import com.excilys.cdb.model.company.Company;

/**
 * Singleton. Responsible for bonding the application to the database thanks to
 * JDBC.
 *
 * @author excilys
 *
 */
public class CompanyDAO {

    /**
     * instance JdbcCompanyDAO - Unique instance of JdbcCompanyDAO.
     */
    private static CompanyDAO instance;

    /**
     * mapper CompanyMapper.
     */
    private CompanySQLMapper mapper = new CompanySQLMapper();

    /**
     * LOGGER Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    /**
     * String base SQL request.
     */
    private static final String LIST_REQUEST = "SELECT * FROM company %s";
    private static final String FIND_BY_ID = "SELECT * FROM company WHERE id = %d";

    /**
     * Constructor.
     *
     * Prevents from being instantiated outside the class.
     */
    private CompanyDAO() {
    }

    /**
     * Creates or returns the unique instance of JdbcCompanyDAO.
     *
     * {@link CompanyDAO#instance}
     *
     * @return JdbcCompanyDAO
     */
    public static CompanyDAO getInstance() {
        if (instance == null) {
            instance = new CompanyDAO();
            LOGGER.debug("JdbcComputerDAO instantiated.");
        }
        return instance;
    }

    /**
     * Fetch the specified computer from the persistence.
     *
     * @param id Long.
     * @return Optional<Company>
     */
    public Optional<Company> get(Long id) {
        // TODO Auto-generated method stub
        Company company = null;
        try (Connection conn = DAOFactory.getConnection()) {

            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery(String.format(FIND_BY_ID, id));
            company = result.next() ? mapper.queryResultToObject(result) : null;
            result.close();
            state.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
        }

        return Optional.ofNullable(company);
    }

    /**
     * Fetch a specific range of companies.
     *
     * @param page        int
     * @param itemPerPage int
     * @return List<Company>
     */
    public List<Company> list(int page, int itemPerPage) {
        // TODO Auto-generated method stub
        LinkedList<Company> companies = new LinkedList<Company>();

        try (Connection conn = DAOFactory.getConnection()) {
            Statement state = conn.createStatement();
            String offsetClause = itemPerPage > 0 ? "LIMIT " + itemPerPage : "";
            offsetClause += (page > 1 && itemPerPage > 0) ? " OFFSET " + ((page - 1) * itemPerPage) : "";

            ResultSet result = state.executeQuery(String.format(LIST_REQUEST, offsetClause));
            while (result.next()) {
                Company c = mapper.queryResultToObject(result);
                if (c != null) {
                    companies.add(c);
                }
            }
            result.close();
            state.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
        }

        return companies;
    }
}