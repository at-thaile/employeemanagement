package com.example.demo.response;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * @author Thehap Rok
 * @param <T>
 */
public class ListPaging<T> implements Serializable{
	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private List<T> list;

	private Integer totalPage;

	private Integer currentPage;
	
	private String fieldSort;
	
	private String typeSort;
	
	public ListPaging() {
		super();
	}

	/**
	 * @summary constructor handle paging and save data to session
	 * @param list
	 * @param size
	 * @param pageIndex
	 * @param fieldSort
	 * @param request
	 */
	public ListPaging(List<T> list,int size
			,int pageIndex
			,String fieldSort,HttpServletRequest request) {
		String sortType = null;
		HttpSession session = request.getSession();
		String fieldSortSession = (String) session.getAttribute("fieldSort");
        if (!"null".equals(fieldSort)|| !"null".equals(fieldSortSession)) {
        	fieldSort = (!"null".equalsIgnoreCase(fieldSort)) ? fieldSort : fieldSortSession;
        	session.setAttribute("fieldSort", fieldSort);
            sortType = (String) session.getAttribute("sortType");
            if (sortType == null || sortType.equals("ASC")) {
                PropertyComparator.sort(list,new MutableSortDefinition(fieldSort, true, true));
                session.setAttribute("sortType", "DESC");
            } else {
                PropertyComparator.sort(list,new MutableSortDefinition(fieldSort, true, false));
                session.setAttribute("sortType", "ASC");
            }
        }
        
        PagedListHolder<T> pageList = new PagedListHolder<>(list);
        pageList.setPageSize(size);
        pageList.setPage(pageIndex);
        this.list = pageList.getPageList();
        this.totalPage = pageList.getPageCount();
        this.currentPage = pageList.getPage();
        this.fieldSort = fieldSort ;
        this.typeSort = sortType;
        
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getFieldSort() {
		return fieldSort;
	}

	public void setFieldSort(String fieldSort) {
		this.fieldSort = fieldSort;
	}

	public String getTypeSort() {
		return typeSort;
	}

	public void setTypeSort(String typeSort) {
		this.typeSort = typeSort;
	}
	
	/**
	 * @summary clear session of paging
	 * @param request
	 */
	public static void clearSessionPaging(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("fieldSort");
		session.removeAttribute("sortType");
	}
	
}
