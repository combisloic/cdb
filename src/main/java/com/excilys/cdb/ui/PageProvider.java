package com.excilys.cdb.ui;

import java.util.List;

/**
 * Interface Responsible for defining the contract a PageProvider must respect.
 * 
 * @author excilys
 *
 */
public interface PageProvider {

	/**
	 * Requests for data of a specific page.
	 * 
	 * @param c    Class <?> - Type of the data to be fetched.
	 * @param page int - Page number
	 * @return List<?>
	 */
	public List<?> fetchDataFor(Class<?> c, int page);
}