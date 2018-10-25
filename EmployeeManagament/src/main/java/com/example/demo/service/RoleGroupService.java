package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Group;
import com.example.demo.entity.GroupRole;
import com.example.demo.entity.Role;

public interface RoleGroupService {

	/**
	 * @summary search all role in group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @return List<GroupRole>
	 */
	List<GroupRole> findAllRoleByGroup(Long groupId);

	/**
	 * @summary search all group in role
	 * @date Aug 22, 2018
	 * @author Tai
	 * @param roleId
	 * @return List<GroupRole>
	 */
	List<GroupRole> findAllGroupByRole(Long roleId);

	/**
	 * @summary check group have role ?
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @return boolean
	 */
	Boolean existsByGroup(Long groupId);

	/**
	 * @summary check role have group ?
	 * @date Aug 22, 2018
	 * @author Tai
	 * @param roleId
	 * @return Boolean
	 */
	Boolean existsByRole(long roleId);

	/**
	 * @summary convert list groupRole object to role object
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupRole
	 * @return List<Role>
	 */
	List<Role> convertGroupRoleToRole(List<GroupRole> groupRole);

	/**
	 * @summary convert list groupRole object to group object
	 * @date Aug 22, 2018
	 * @author Tai
	 * @param groupRole
	 * @return List<Group>
	 */
	List<Group> convertGroupRoleToGroup(List<GroupRole> groupRole);

	/**
	 * @summary check role have in group ?
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return boolean
	 */
	Boolean existsByGroupAndRole(Long groupId, Long roleId);

	/**
	 * @summary add role to group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return GroupRole
	 */
	GroupRole addRoleToGroup(Long groupId, Long roleId);

	/**
	 * @summary add group to role
	 * @date Aug 22, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return GroupRole
	 */
	GroupRole addGroupToRole(Long groupId, Long roleId);

	/**
	 * @summary delete role in group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return boolean
	 */
	Boolean deleteRoleFormGroup(Long groupId, Long roleId);

	/**
	 * @summary delete group in role
	 * @date Aug 22, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return Boolean
	 */
	Boolean deleteGroupFormRole(Long groupId, Long roleId);

	/**
	 * @summary delete all role in group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return Integer
	 */
	Boolean deleteAllRoleFromGroup(Long groupId, List<Role> role);

	/**
	 * @summary delete list role from group
	 * @date Aug 22, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleIds
	 * @return Integer
	 */
	Integer deleteListRoleFromGroup(Long groupId, List<Long> roleIds);

	/**
	 * @summary delete list group from group
	 * @date Aug 22, 2018
	 * @author Tai
	 * @param roleId
	 * @param groupIds
	 * @return Integer
	 */
	Integer deleteListGroupFromRole(Long roleId, List<Long> groupIds);
}
