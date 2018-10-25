package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Group;
import com.example.demo.entity.GroupRole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserGroup;
import com.example.demo.entity.UserRole;
import com.example.demo.exception.GroupNotFoundException;
import com.example.demo.exception.RoleNotFoundException;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.GroupRoleRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.RoleGroupService;
import com.example.demo.util.RoleScope;

@Service
public class RoleGroupServiceImpl implements RoleGroupService {
	@Autowired
	GroupRepository groupRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	GroupRoleRepository groupRoleRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;

	/**
	 * @summary check group have role ?
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @return boolean
	 */
	@Override
	public Boolean existsByGroup(Long groupId) {
		Optional<Group> exist = groupRepository.findByIdAndNonDel(groupId, false);
		if (!exist.isPresent()) {
			return false;
		}
		return true;
	}

	/**
	 * @summary search all role in group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @return List<GroupRole>
	 */
	@Override
	public List<GroupRole> findAllRoleByGroup(Long groupId) {
		Optional<Group> groupOptional = groupRepository.findByIdAndNonDel(groupId, true);
		List<GroupRole> list = groupRoleRepository.findByGroup(groupOptional.get());
//		for (GroupRole groupRole : list) {
//			System.out.println(groupRole.getRole().getRoleName());
//		}
		return list;
	}

	/**
	 * @summary convert list groupRole object to role object
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupRole
	 * @return List<Role>
	 */
	@Override
	public List<Role> convertGroupRoleToRole(List<GroupRole> groupRole) {

		List<Role> role = new ArrayList<>();
		for (GroupRole groupRole2 : groupRole) {
			role.add(groupRole2.getRole());
		}
		return role;
	}

	/**
	 * @summary check role have in group ?
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return boolean
	 */
	@Override
	public Boolean existsByGroupAndRole(Long groupId, Long roleId) {
		Optional<GroupRole> exist = groupRoleRepository.findByGroupIdAndRoleId(groupId, roleId);
		if (!exist.isPresent()) {
			return false;
		}
		return true;
	}

	/**
	 * @summary add role to group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return GroupRole
	 */
	@Override
	public GroupRole addRoleToGroup(Long groupId, Long roleId) {
		Optional<GroupRole> exist = groupRoleRepository.findByGroupIdAndRoleId(groupId, roleId);
		Optional<Group> group = groupRepository.findByIdAndNonDel(groupId, true);
		Optional<Role> role = roleRepository.findByIdAndNonDel(roleId, true);
		if (exist.isPresent()) {
			return null;
		}
		if (!group.isPresent()) {
			throw new GroupNotFoundException("Group Not Found id: " + groupId);
		} 
		if (!role.isPresent()) {
			throw new RoleNotFoundException("Role Not Found id: " + roleId);
		}
		if (!role.get().getScope().equals(RoleScope.GROUP)) {
//			throw new
		}
		
		GroupRole groupRole = new GroupRole();
		groupRole.setGroup(group.get());
		groupRole.setRole(role.get());
//		System.err.println(role.get().getRoleName());
		List<UserGroup> userGroups = groupRole.getGroup().getUserGroups();
		
		for (UserGroup userGroup : userGroups) {
			
			User user = userGroup.getUser();
//			System.out.println(user.getId());
			boolean checkUserHasRole = userRoleRepository.existsByUserIdAndRoleId(user.getId(), roleId);
			if(!checkUserHasRole) {
				UserRole userRole = new UserRole(user, role.get());
				userRoleRepository.save(userRole);
			}
		}
		
		return groupRoleRepository.save(groupRole);
	}

	/**
	 * @summary delete role in group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return boolean
	 */
	@Override
	public Boolean deleteRoleFormGroup(Long groupId, Long roleId) {
		Optional<Group> group = groupRepository.findByIdAndNonDel(groupId, true);
		Optional<Role> role = roleRepository.findByIdAndNonDel(roleId, true);
		Boolean exist = existsByGroupAndRole(groupId, roleId);
		if (!exist) {
			return false;
		}
		
		GroupRole groupRole = new GroupRole();
		groupRole.setGroup(group.get());
		groupRole.setRole(role.get());
		System.err.println(role.get().getRoleName());
		List<UserGroup> userGroups = groupRole.getGroup().getUserGroups();
		if (userGroups.size()==1) {
		for (UserGroup userGroup : userGroups) {
			
			User user = userGroup.getUser();
			System.out.println(user.getId());
			boolean checkUserHasRole = userRoleRepository.existsByUserIdAndRoleId(user.getId(), roleId);
			
			if(checkUserHasRole) {
//				UserRole userRole = new UserRole(user, role.get());
				
				Integer o =userRoleRepository.deleteByUserIdAndRoleId(user.getId(), roleId);
				System.err.println(o);
//				userRoleRepository.save(userRole);
			}
		}}
		groupRoleRepository.deleteByGroupIdAndRoleId(groupId, roleId);
		
		return true;
	}

	/**
	 * @summary delete all role in group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return Integer
	 */
	@Override
	@Transactional
	public Boolean deleteAllRoleFromGroup(Long groupId, List<Role> role) {
		Optional<Group> groupOptional = groupRepository.findByIdAndNonDel(groupId,true);
		if (!groupOptional.isPresent()) {
			return false;
		}
		for ( Role deletedRole : role) {
			
			groupRoleRepository.deleteByGroupIdAndRoleId(groupId, deletedRole.getId());
		}
		return true;
	}

	/**
	 * @summary delete list role from group
	 * @date Aug 22, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleIds
	 * @return Integer
	 */
	@Override
	public Integer deleteListRoleFromGroup(Long groupId, List<Long> roleIds) {
		Optional<Group> groupOptional = groupRepository.findByIdAndNonDel(groupId,true);
		if (!groupOptional.isPresent()) {
			return 0;
		}
		for (Long roleId : roleIds) {
			deleteRoleFormGroup(groupId, roleId);
		}
		return 1;
	}

	/**
	 * @summary search all group in role
	 * @date Aug 22, 2018
	 * @author Tai
	 * @param roleId
	 * @return List<GroupRole>
	 */
	@Override
	public List<GroupRole> findAllGroupByRole(Long roleId) {
		Optional<Role> roleOptional = roleRepository.findByIdAndNonDel(roleId,true);
		List<GroupRole> list = groupRoleRepository.findByRole(roleOptional.get());
//		for (GroupRole groupRole : list) {
//			System.out.println(groupRole.getGroup().getName());
//		}
		return list;
		
	}

	/**
	 * @summary check role have group ?
	 * @date Aug 22, 2018
	 * @author Tai
	 * @param roleId
	 * @return Boolean
	 */
	@Override
	public Boolean existsByRole(long roleId) {
		Optional<Role> exist = roleRepository.findByIdAndNonDel(roleId, false);
		if (!exist.isPresent()) {
			return false;
		}
		return true;
	}

	/**
	 * @summary convert list groupRole object to group object
	 * @date Aug 22, 2018
	 * @author Tai
	 * @param groupRole
	 * @return List<Group>
	 */
	@Override
	public List<Group> convertGroupRoleToGroup(List<GroupRole> groupRole) {
		List<Group> group = new ArrayList<>();
		for (GroupRole groupRole2 : groupRole) {
			group.add(groupRole2.getGroup());
		}
		return group;
	}

	/**
	 * @summary add group to role
	 * @date Aug 22, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return GroupRole
	 */
	@Override
	public GroupRole addGroupToRole(Long groupId, Long roleId) {
		return addRoleToGroup(groupId, roleId);
	}

	/**
	 * @summary delete role in group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return boolean
	 */
	@Override
	public Boolean deleteGroupFormRole(Long groupId, Long roleId) {
		Optional<Group> group = groupRepository.findByIdAndNonDel(groupId, true);
		Optional<Role> role = roleRepository.findByIdAndNonDel(roleId, true);
		Boolean exist = existsByGroupAndRole(groupId, roleId);
		if (!exist) {
			return false;
		}
		
		GroupRole groupRole = new GroupRole();
		groupRole.setGroup(group.get());
		groupRole.setRole(role.get());
		System.err.println(role.get().getRoleName());
		List<UserGroup> userGroups = groupRole.getGroup().getUserGroups();
		List<GroupRole> groupRoles = groupRoleRepository.findByRole(role.get());
		if (groupRoles.size()==1) {
			for (UserGroup userGroup : userGroups) {
				
				User user = userGroup.getUser();
				System.out.println(user.getId());
				boolean checkUserHasRole = userRoleRepository.existsByUserIdAndRoleId(user.getId(), roleId);
				if(checkUserHasRole) {
//					UserRole userRole = new UserRole(user, role.get());
					
					Integer o =userRoleRepository.deleteByUserIdAndRoleId(user.getId(), roleId);
					System.err.println(o);
//					userRoleRepository.save(userRole);
				}
			}
		}
		
		groupRoleRepository.deleteByGroupIdAndRoleId(groupId, roleId);
		
		return true;
	}

	/**
	 * @summary delete list group from role
	 * @date Aug 22, 2018
	 * @author Tai
	 * @param roleId
	 * @param groupIds
	 * @return Integer
	 */
	@Override
	public Integer deleteListGroupFromRole(Long roleId, List<Long> groupIds) {
		Optional<Role> roleOptional = roleRepository.findByIdAndNonDel(roleId,true);
		if (!roleOptional.isPresent()) {
			return 0;
		}
		for (Long groupId : groupIds) {
//			System.out.println(groupId);
			deleteGroupFormRole(groupId, roleId);
		}
		return 1;
	}

}
