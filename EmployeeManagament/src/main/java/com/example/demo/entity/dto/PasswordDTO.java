package com.example.demo.entity.dto;

import javax.validation.constraints.NotBlank;

import com.example.demo.validation.Password;

public class PasswordDTO {
	
	private String token;
	private String email;
	
	@NotBlank
	private String passwordCurrent;
	
	@NotBlank
	@Password(message="Password has at least one number, one special character, one uppecase character and has from 6 to 10 character")
	private String newPassword;	
	
	private String newMatchingPassword;
	public PasswordDTO() {
		super();
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordCurrent() {
		return passwordCurrent;
	}
	public void setPasswordCurrent(String passwordCurrent) {
		this.passwordCurrent = passwordCurrent;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewMatchingPassword() {
		return newMatchingPassword;
	}
	public void setNewMatchingPassword(String newMatchingPassword) {
		this.newMatchingPassword = newMatchingPassword;
	}
	
	
	
	
}
