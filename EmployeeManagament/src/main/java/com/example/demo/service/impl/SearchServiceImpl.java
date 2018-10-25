package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.service.SearchService;
import com.example.demo.util.SearchGeneric;
/**
 * @summary all functional of search implement
 * @author Thehap Rok
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchGeneric searchGeneric;

	/**
	 * @summary search no have condition 
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @param entityName
	 * @param columnName
	 * @param value
	 * @return List<T>
	 */
	@Override
	public <T> List<T> search(String entityName, String columnName, String value) {
		return searchGeneric.searchGeneric(entityName, columnName, value);
	}

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
	@Override
	public <T> List<T> search(String entityName, String columnName,
			String value, String condition) {
		return searchGeneric.searchGeneric(entityName, columnName, value, condition);
	}

}
