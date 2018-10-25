package com.example.demo.response;

import java.util.Map;

import com.example.demo.entity.dto.UserDTO;

public class UserDTOResponse {
	private UserDTO userDTO;
	private boolean validated;
	private Map<String, String> errorMessages;
	public UserDTO getUserDTO() {
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
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
