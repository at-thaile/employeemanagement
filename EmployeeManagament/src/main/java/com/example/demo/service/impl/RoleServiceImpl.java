package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
  
	
	@Autowired
	private UserRoleRepository userRoleRepository;


	@Override
	public List<Role> getAllRoles() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}


	@Override
	public List<Role> getRolesByNameContaining(String roleName) {
		// TODO Auto-generated method stub
		return roleRepository.findByRoleNameContaining(roleName);
	}

	@Transactional
	@Override
	public boolean deleteRoleById(Long id) {
		// TODO Auto-generated method stub
		Optional<Role> optionalRole = roleRepository.findById(id);
		if(!optionalRole.isPresent()) {
			return false;
		}
		userRoleRepository.deleteUserRoleByRoleId(id);
		roleRepository.deleteRole(id);
		return true;
	}

	
	@Override
	public Role addRole(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}


	@Override
	public Role editRole(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}


	@Override
	public Role getRoleById(Long id) {
		// TODO Auto-generated method stub
		Optional<Role> optionalRole = roleRepository.findById(id);
		if(!optionalRole.isPresent()) {
			return null;
		}
		return optionalRole.get();
	}


	@Override
	public Role findByRoleName(String roleName) {
		// TODO Auto-generated method stub
		return roleRepository.findByRoleName(roleName);
	}


	@Override
	public List<Role> getListRoleByScope(String scope) {
		return roleRepository.findByScopeAndNonDel(scope, true);
	}
	
}
