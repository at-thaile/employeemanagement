package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Role;
import com.example.demo.wrapper.ListIdWrapper;


/**
 * 
 * @summary handle service of role
 * @author ThaiLe
 *
 */
public interface RoleService {

	/**
	 * @summary return list of all role in database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @return List<Role>
	 */
	List<Role> getAllRoles();

	/**
	 * @summary return list of role base on roleName
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param roleName
	 * @return List<Role>
	 */
	List<Role> getRolesByNameContaining(String roleName);

	/**
	 * @summary delete role base id
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param id
	 * @return boolean
	 */
	boolean deleteRoleById(Long id);

	/**
	 * @summary add a role into database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param role
	 * @return Role
	 */
	Role addRole(Role role);

	/**
	 * @summary edit a role from database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param role
	 * @return Role
	 */
	Role editRole(Role role);

	/**
	 * @summary return Role base on id of role
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param id
	 * @return Role
	 */
	Role getRoleById(Long id);

	/**
	 * @summary find Role base roleName of role
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param roleName
	 * @return Role
	 */
	Role findByRoleName(String roleName);

	/**
	 * @summary List all Role by group id
	 * @date Aug 17, 2018
	 * @author Tai
	 * @param group
	 * @return List<Role>
	 */
	List<Role> findByGroupRolesGroupId(long group);

	/**
	 * @summary get list role base on scope
	 * @date Aug 17, 2018
	 * @author Thehap Rok
	 * @param scope
	 * @return List<Role>
	 */
	List<Role> getListRoleByScope(String scope);

	/**
	 * @summary delete multi role by role id
	 * @author Thehap Rok
	 * @param listIdWrapper
	 * @return Boolean
	 */
	Boolean deleteMultiRole(ListIdWrapper listIdWrapper);

	/**
	 * 
	 * @summary get NameRole And G roupid
	 * @date Sep 5, 2018
	 * @author Tai
	 * @param name
	 * @param groupId
	 * @return
	 * @return List<Role>
	 */
	List<Role> getNameRoleAndGroupid(String name, Long groupId);
}
