package com.example.demo.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

/**
 * @summary search for all field,all entity
 * @author HaNguyen
 */
@Component
public class SearchGeneric {

	@PersistenceContext
	EntityManager entityManager;

	/**
	 * 
	 * @summary search for entity, all field no condition
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param tableName
	 * @param fieldSearch
	 * @param searchValue
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> searchGeneric(String tableName, String fieldSearch, String searchValue) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("from ");
		sqlQuery.append(tableName);
		if ((!fieldSearch.isEmpty() || fieldSearch != null) 
				&& (!searchValue.isEmpty() || searchValue != null)) {
			sqlQuery.append(" where ");
			sqlQuery.append(fieldSearch);
			sqlQuery.append(" like '%");
			sqlQuery.append(searchValue);
			sqlQuery.append("%' and nonDel=1 ");
		}
		Query query = entityManager.createQuery(sqlQuery.toString());
		return query.getResultList();
	}

	/**
	 * 
	 * @summary search for entity, all field have condition
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param tableName
	 * @param fieldSearch
	 * @param searchValue
	 * @param condition
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> searchGeneric(String tableName, String fieldSearch, 
			String searchValue, String condition) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("from ");
		sqlQuery.append(tableName);
		if ((!fieldSearch.isEmpty() || fieldSearch != null) 
				&& (!searchValue.isEmpty() || searchValue != null)) {
			sqlQuery.append(" where ");
			sqlQuery.append(fieldSearch);
			sqlQuery.append(" like '%");
			sqlQuery.append(searchValue);
			sqlQuery.append("%' and nonDel=1 ");
		}
		if (condition != null && !condition.isEmpty()) {
			sqlQuery.append("and ");
			sqlQuery.append(condition);
		}
		Query query = entityManager.createQuery(sqlQuery.toString());
		return query.getResultList();
	}
}
