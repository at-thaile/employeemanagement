package com.example.demo.exception;

public class UserAlreadyRoleException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyRoleException(String message) {
		super(message);
	}
}
