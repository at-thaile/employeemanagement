package com.example.demo.wrapper;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Thehap Rok
 *
 */
public class ListIdWrapper implements Serializable {
	@JsonIgnore
	private static final long serialVersionUID = 1L;

	/**
	 * List id param from request
	 */
	private List<Long> ids;

	public ListIdWrapper(List<Long> ids) {
		super();
		this.ids = ids;
	}

	public ListIdWrapper() {
		super();
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

}
