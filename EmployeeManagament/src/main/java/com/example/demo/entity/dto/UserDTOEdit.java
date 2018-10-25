package com.example.demo.entity.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.demo.validation.Phone;
import com.example.demo.validation.ValidEmail;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Thehap Rok
 *
 */
public class UserDTOEdit implements Serializable {

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;private Long id;
	@ValidEmail(message="Email must be valid")
	private String email;
	private Boolean enable;
	private Boolean nonLocked;	
	@NotBlank(message = "Full name must not be blank")
	private String fullname;
	
	@Phone(message="Phone number must be valid")
	private String phone;
	
	@NotNull(message = "Birthday must not be null")
	private Date birthday;
	
	@NotBlank(message = "Address must not be blank")
	private String address;
	
	@NotNull(message = "Gender must not be null")
	private Boolean gender;
	
	@NotNull(message = "Role must not be null")
	private Long id_role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Boolean getNonLocked() {
		return nonLocked;
	}

	public void setNonLocked(Boolean nonLocked) {
		this.nonLocked = nonLocked;
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

	public Long getId_role() {
		return id_role;
	}

	public void setId_role(Long id_role) {
		this.id_role = id_role;
	}

}
