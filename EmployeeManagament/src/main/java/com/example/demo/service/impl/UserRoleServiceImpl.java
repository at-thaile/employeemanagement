package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserRole;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	/**
	 * @summary add user with role
	 * @author TaiTruong
	 * @param userRole
	 * @return UserRole
	 */
	@Override
	public UserRole addUserWithRole(UserRole userRole) {
		// TODO Auto-generated method stub
		return userRoleRepository.save(userRole);
	}

}
