package com.example.demo.service;

import java.util.List;

/**
 * @summary Search interface
 * @author Thehap Rok
 */
public interface SearchService {
	/**
	 * @summary search no have condition 
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @param entityName
	 * @param columnName
	 * @param value
	 * @return List<T>
	 */
	<T> List<T> search(String entityName,String columnName,String value);
	
	/**
	 * @summary search have condition 
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @param entityName
	 * @param columnName
	 * @param value
	 * @param condition
	 * @return List<T>
	 */
	<T> List<T> search(String entityName,String columnName,String value,String condition);
}
