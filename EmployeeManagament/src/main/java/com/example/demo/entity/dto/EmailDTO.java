package com.example.demo.entity.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 
 * @author TaiTruong
 *
 */
public class EmailDTO {

	@Email
	@NotBlank(message = "You must not be blank")
	private String email;

	public EmailDTO() {
		super();
	}

	public EmailDTO(String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
