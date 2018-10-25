package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.validation.Phone;

@Entity
@Table(name="user_detail")
public class UserDetail {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fullname", nullable = false)
	private String fullname;

	@Phone
	@Column(name = "phone", columnDefinition = "VARCHAR(15)", nullable = false)
	private String phone;

	@Temporal(TemporalType.DATE)
	@Column(name = "birthday",nullable = false)
	private Date birthDay;
	
	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "gender", columnDefinition = "TINYINT(1)", nullable = false)
	private Boolean gender;
	
	@Column(name ="pathImage", nullable = false)
	private String pathImage = "user.png";

	@Column(name = "created_at",columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;

	@OneToOne(optional = false)
	@PrimaryKeyJoinColumn
	private User user;
	
	public String getPathImage() {
		return pathImage;
	}

	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}

	public UserDetail() {
		super();
	}

	public UserDetail(String fullname, String phone, String address, Boolean gender, Date birthDay) {
		super();
		this.fullname = fullname;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
		this.birthDay = birthDay;
	}

	public UserDetail(Long id, String fullname, String phone, String address, Boolean gender, Date createdAt,
			User user) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
		this.createdAt = createdAt;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
