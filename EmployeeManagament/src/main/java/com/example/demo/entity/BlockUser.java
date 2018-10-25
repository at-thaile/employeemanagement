package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "block_user")
public class BlockUser {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", 
				unique = true, 
				nullable = false)
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "block_time", nullable = true)
	private Date blockTime;

	@Column(name = "number_fail", nullable = false)
	private Integer numberFail = 0;

	public BlockUser() {
		super();
	}

	public BlockUser(User user, Date blockTime, Integer numberFail) {
		super();
		this.user = user;
		this.blockTime = blockTime;
		this.numberFail = numberFail;
	}

	public BlockUser(Long id, User user, Date blockTime, Integer numberFail) {
		super();
		this.id = id;
		this.user = user;
		this.blockTime = blockTime;
		this.numberFail = numberFail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getBlockTime() {
		return blockTime;
	}

	public void setBlockTime(Date blockTime) {
		this.blockTime = blockTime;
	}

	public Integer getNumberFail() {
		return numberFail;
	}

	public void setNumberFail(Integer numberFail) {
		this.numberFail = numberFail;
	}

}
