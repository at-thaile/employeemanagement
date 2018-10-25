package com.example.demo.entity.dto;

import org.springframework.web.multipart.MultipartFile;

public class UserTest {
	private String username;
	private String password;
	private MultipartFile file;
	public UserTest(String username, String password, MultipartFile file) {
		super();
		this.username = username;
		this.password = password;
		this.file = file;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
