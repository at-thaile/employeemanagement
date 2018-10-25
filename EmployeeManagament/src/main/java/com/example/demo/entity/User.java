package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	/*@JsonIgnore*/
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "enable", nullable = false, columnDefinition = "TINYINT(1) default 0")
	private Boolean enable = false;

	@Column(name = "non_del", nullable = false, columnDefinition = "TINYINT(1) default 1")
	private Boolean nonDel = true;

	@Column(name = "non_locked", nullable = false, columnDefinition = "TINYINT(1) default 1")
	private Boolean nonLocked = true;

	@JsonIgnoreProperties("user")
	@OneToOne(mappedBy = "user",cascade=CascadeType.ALL,orphanRemoval=true)
	private UserDetail userDetail;

	@JsonIgnore
	@OneToOne(mappedBy = "user",orphanRemoval=true)
	private TokenVerifition tokenVerifition;

	@JsonIgnore
	@OneToMany(mappedBy = "user",orphanRemoval=true)
	private List<UserGroup> userGroups;

	@JsonIgnoreProperties("user")
	@OneToMany(mappedBy = "user",orphanRemoval=true)
	private List<UserRole> userRoles;

	public User() {
		super();
	}

	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public User(Long id, String email, String password, Boolean enable, Boolean nonDel, Boolean nonLocked,
			UserDetail userDetail, TokenVerifition tokenVerifition, List<UserGroup> userGroups,
			List<UserRole> userRoles) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.enable = enable;
		this.nonDel = nonDel;
		this.nonLocked = nonLocked;
		this.userDetail = userDetail;
		this.tokenVerifition = tokenVerifition;
		this.userGroups = userGroups;
		this.userRoles = userRoles;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Boolean getNonDel() {
		return nonDel;
	}

	public void setNonDel(Boolean nonDel) {
		this.nonDel = nonDel;
	}

	public Boolean getNonLocked() {
		return nonLocked;
	}

	public void setNonLocked(Boolean nonLocked) {
		this.nonLocked = nonLocked;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public TokenVerifition getTokenVerifition() {
		return tokenVerifition;
	}

	public void setTokenVerifition(TokenVerifition tokenVerifition) {
		this.tokenVerifition = tokenVerifition;
	}


	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

}
