package com.example.demo.entity.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.validation.Password;
import com.example.demo.validation.PasswordMatches;
import com.example.demo.validation.Phone;
import com.example.demo.validation.ValidEmail;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 
 * @author ThaiLe
 * get user infor from request
 *
 */

@PasswordMatches
public class UserDTO implements Serializable{
	
	@JsonIgnore
	private static final long serialVersionUID = 1L;	
	@ValidEmail(message = "Email must not be blank and must valid")	
	private String email;
	@NotBlank(message = "fullname must not blank")
	private String fullname;	
	@Phone(message="Phone must not be blank and must valid")
	private String phone;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull(message = "Birthday must not be null")
	private Date birthday;
	@NotBlank(message = "Address must not be blank")
	private String address;
	@NotNull(message = "Gender must not be null")
	private Boolean gender;
	@Password(message = "Password must be valid, has at least one number, one special character, one uppecase character and has from 6 to 10 character")
	private String password;	
	private String matchingPassword;
	
	public String getMatchingPassword() {
		return matchingPassword;
	}
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	public UserDTO() {
		super();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
