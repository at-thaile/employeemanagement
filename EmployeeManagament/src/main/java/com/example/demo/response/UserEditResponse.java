package com.example.demo.response;

import java.util.Map;

import com.example.demo.entity.dto.UserDTOEdit;

public class UserEditResponse {
	private UserDTOEdit userDTOEdit;
	private boolean validated;
	private Map<String, String> errorMessages;
	public UserDTOEdit getUserResponse() {
		return userDTOEdit;
	}
	public void setUserResponse(UserDTOEdit userDTOEdit) {
		this.userDTOEdit = userDTOEdit;
	}
	public boolean isValidated() {
		return validated;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	public Map<String, String> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(Map<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}	
}
