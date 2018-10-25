package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo.repository.GroupRoleRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.RoleService;
import com.example.demo.wrapper.ListIdWrapper;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
  
	@Autowired
	private GroupRoleRepository groupRoleRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	/**
	* @summary return list of all role in database
	* @date Aug 15, 2018
	* @author ThaiLe
	* @return List<Role>
	*/
	@Override
	public List<Role> getAllRoles() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}

	/**
	* @summary return list of role base on roleName
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param roleName
	* @return List<Role>
	*/
	@Override
	public List<Role> getRolesByNameContaining(String roleName) {
		// TODO Auto-generated method stub
		return roleRepository.findByRoleNameContaining(roleName);
	}

	/**
	* @summary delete role base id
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param id
	* @return boolean
	*/
	@Transactional
	@Override
	public boolean deleteRoleById(Long id) {
		// TODO Auto-generated method stub
		Optional<Role> optionalRole = roleRepository.findById(id);
		if(!optionalRole.isPresent()) {
			return false;
		}
		userRoleRepository.deleteUserRoleByRoleId(id);
		groupRoleRepository.deleteByRoleId(id);
		roleRepository.deleteRole(id);
		return true;
	}

	/**
	* @summary add a role into database
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param role
	* @return Role
	 */
	@Override
	public Role addRole(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

	/**
	* @summary edit a role from database
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param role
	* @return Role
	 */	
	@Override
	public Role editRole(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

	/**
	* @summary return Role base on id of role
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param id
	* @return Role
	 */
	@Override
	public Role getRoleById(Long id) {
		// TODO Auto-generated method stub
		Optional<Role> optionalRole = roleRepository.findById(id);
		if(!optionalRole.isPresent()) {
			return null;
		}
		return optionalRole.get();
	}

	/**
	* @summary find Role base roleName of role
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param roleName
	* @return Role
	 */
	@Override
	public Role findByRoleName(String roleName) {
		// TODO Auto-generated method stub
		return roleRepository.findByRoleName(roleName);
	}

	/**
	* @summary List all Role by group id
	* @date Aug 17, 2018
	* @author Tai
	* @param group
	* @return List<Role>
	 */
	@Override
	public List<Role> findByGroupRolesGroupId(long group) {
		return roleRepository.findByGroupRolesGroupId(group);
	}
	
	/**
	 * @summary get list role base on scope
	 * @date Aug 17, 2018
	 * @author Thehap Rok
	 * @param scope
	 * @return List<Role>
	 */
	@Override
	public List<Role> getListRoleByScope(String scope) {
		return roleRepository.findByScopeAndNonDel(scope, true);
	}
	
	/**
	 * @summary delete multi role by role id
	 * @author Thehap Rok
	 * @param listIdWrapper
	 * @return Boolean
	 */
	@Override
	public Boolean deleteMultiRole(ListIdWrapper listIdWrapper) {
		List<Long> listId = listIdWrapper.getIds();
		for (Long id : listId) {
			roleRepository.deleteRole(id);
		}
		return true;
	}

	@Override
	public List<Role> getNameRoleAndGroupid(String name, Long groupId) {
		List<Role> roles = roleRepository.findRoleNotInGroupByName(groupId, name);
		
		return roles;
	}
}
