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
@Table(name="group_detail")
public class Group {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message="You must not blank")
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "non_del",nullable = false,columnDefinition="TINYINT(1) default 1")
	private Boolean nonDel = true;

	@NotBlank(message="You must not blank")
	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "created_at",columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;

	@JsonIgnoreProperties("group")
	@OneToMany(mappedBy = "group",orphanRemoval=true)
	private List<GroupRole> groupRoles;

	@JsonIgnoreProperties("group")
	@OneToMany(mappedBy = "group",orphanRemoval=true)
	private List<UserGroup> userGroups;

	public Group() {
		super();
	}
	public Group(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	

	public Group(Long id, String name, Boolean nonDel, String description, Date createdAt, List<GroupRole> groupRoles,
			List<UserGroup> userGroups) {
		super();
		this.id = id;
		this.name = name;
		this.nonDel = nonDel;
		this.description = description;
		this.createdAt = createdAt;
		this.groupRoles = groupRoles;
		this.userGroups = userGroups;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<GroupRole> getGroupRoles() {
		return groupRoles;
	}

	public void setGroupRoles(List<GroupRole> groupRoles) {
		this.groupRoles = groupRoles;
	}

	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

}
