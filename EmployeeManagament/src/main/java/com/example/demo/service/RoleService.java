package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Role;

public interface RoleService {


	List<Role> getAllRoles();


	List<Role> getRolesByNameContaining(String roleName);


	boolean deleteRoleById(Long id);


	Role addRole(Role role);

	Role editRole(Role role);


	Role getRoleById(Long id);


	Role findByRoleName(String roleName);

	List<Role> getListRoleByScope(String scope);

}
