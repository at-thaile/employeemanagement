package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="role")
public class Role {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message="Not Empty!!!")
	@Column(name = "role_name", nullable = false)
	private String roleName;

	@Column(name = "non_del", columnDefinition = "TINYINT(1) default 1", nullable = false)
	private Boolean nonDel = true;
	
	@NotBlank(message="Not Empty!!!")
	@Column(name = "description",nullable = true)
	private String description;

	@Column(name="created_at",nullable=false,columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;
	
	@Column(name = "scope",nullable = false)
	private String scope;
	

//	@JsonIgnoreProperties({"user"})
	@OneToMany(mappedBy = "role")
	private List<UserRole> userRoles;

	@JsonIgnoreProperties({"role"})
	@OneToMany(mappedBy = "role")
	private List<GroupRole> groupRoles;

	public Role() {
		super();
	}

	public Role(String roleName, String description, String scope) {
		super();
		this.roleName = roleName;
		this.description = description;
		this.scope = scope;
	}

	public Role(String roleName, String description) {
		super();
		this.roleName = roleName;
		this.description = description;
	}

	public Role(Long id, String roleName, Boolean nonDel, String description, Date createdAt, String scope,
			List<UserRole> userRoles, List<GroupRole> groupRoles) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.nonDel = nonDel;
		this.description = description;
		this.createdAt = createdAt;
		this.scope = scope;
		this.userRoles = userRoles;
		this.groupRoles = groupRoles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getNonDel() {
		return nonDel;
	}

	public void setNonDel(Boolean nonDel) {
		this.nonDel = nonDel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public List<GroupRole> getGroupRoles() {
		return groupRoles;
	}

	public void setGroupRoles(List<GroupRole> groupRoles) {
		this.groupRoles = groupRoles;
	}

}

