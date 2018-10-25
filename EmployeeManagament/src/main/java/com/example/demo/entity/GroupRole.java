package com.example.demo.entity; 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "role_group")
public class GroupRole {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnoreProperties({"groupRoles","userRoles"})
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@JsonIgnoreProperties({"groupRoles","userGroups"})
	@ManyToOne
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	public GroupRole() {
		super();
	}

	public GroupRole(Role role, Group groupId) {
		super();
		this.role = role;
		this.group = groupId;
	}

	public GroupRole(Long id, Role role, Group groupId) {
		super();
		this.id = id;
		this.role = role;
		this.group = groupId;
	}
	
	@JoinTable()
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
}
