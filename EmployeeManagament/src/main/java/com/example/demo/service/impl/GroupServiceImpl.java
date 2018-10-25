package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Group;
import com.example.demo.entity.GroupRole;
import com.example.demo.entity.User;
import com.example.demo.entity.UserGroup;
import com.example.demo.entity.UserRole;
import com.example.demo.exception.GroupNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.GroupRoleRepository;
import com.example.demo.repository.UserGroupRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.response.UserResponse;
import com.example.demo.service.GroupService;
import com.example.demo.service.UserService;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private UserGroupRepository userGroupRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupRoleRepository groupRoleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserService userService;

	/**
	 * @summary add user to group
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return UserGroup
	 */
	@Override
	public UserGroup addNewUserToGroup(Long groupId, Long userId) throws GroupNotFoundException, UserNotFoundException {
		Optional<Group> groupOptional = groupRepository.findByIdAndNonDel(groupId, true);
		Optional<User> userOptional = userRepository.findById(userId);
		if (!groupOptional.isPresent()) {
			throw new GroupNotFoundException("Group Not found!!!");
		}
		if (!userOptional.isPresent() || !userOptional.get().getNonDel()) {
			throw new UserNotFoundException("User Not found!!!");
		}
		boolean checkExist = userGroupRepository.existsByGroupIdAndUserId(groupId, userId);
		if (checkExist) {
			return null;
		}
		UserGroup userGroup = new UserGroup(userOptional.get(), groupOptional.get());
		addAllRoleOfGroupToUserRole(groupOptional.get(), userOptional.get());
		return userGroupRepository.save(userGroup);
	}

	/**
	 * @summary view all group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @return List<Group>
	 */
	@Override
	public List<Group> viewAllGroup() {
		return groupRepository.findByNonDel(true);
	}

	/**
	 * @summary add a group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param group
	 * @return Group
	 */
	@Override
	public Group addGroup(Group group) {
		return groupRepository.save(group);
	}

	/**
	 * @summary view a group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @return Optional<Group>
	 */
	@Override
	public Optional<Group> viewGroup(Long groupId) {
		// TODO Auto-generated method stub
		return groupRepository.findByIdAndNonDel(groupId, true);
	}

	/**
	 * @summary remove multi user in group
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userIds
	 * @return Integer
	 */
	@Transactional
	public Integer removeListUseFromGroup(Long groupId, List<Long> userIds) throws GroupNotFoundException {
		Optional<Group> groupOptional = groupRepository.findById(groupId);
		if (!groupOptional.isPresent()) {
			throw new GroupNotFoundException("Group not found!!!");
		}
		for (Long userId : userIds) {
			userGroupRepository.deleteUserFromGroup(groupId, userId);
		}
		return 1;

	}

	/**
	 * @summary remove user in group
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return Integer
	 */
	@Override
	@Transactional
	public Integer removeUserFromGroup(Long groupId, Long userId) throws GroupNotFoundException, UserNotFoundException {
		Optional<Group> groupOptional = groupRepository.findById(groupId);
		Optional<User> userOptional = userRepository.findById(userId);
		if (!groupOptional.isPresent()) {
			throw new GroupNotFoundException("Group Not found!!!");
		}
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found!!!");
		}
		userGroupRepository.deleteUserFromGroup(groupId, userId);
		userService.removeRoleOfGroupFromUserRole(groupOptional.get(), userOptional.get());
		return 1;
	}

	/**
	 * @summary
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param nameOrEmail
	 * @return List<UserResponse>
	 */
	@Override
	public List<UserResponse> findUserNotInGroupByNameOrEmail(Long groupId, String nameOrEmail) {
		List<User> listUser = userRepository.findUserNotInGroupByNameOrEmail(groupId, nameOrEmail);
		List<UserResponse> users = userService.convertUserToUserResponse(listUser);
		return users;
	}

	/**
	 * @summary get infor of user in group
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return
	 * @return UserResponse
	 */
	@Override
	public UserResponse getInforOfUser(Long groupId, Long userId) throws GroupNotFoundException, UserNotFoundException {
		Optional<Group> groupOptional = groupRepository.findByIdAndNonDel(groupId, true);
		Optional<User> userOptional = userRepository.findById(userId);
		if (!groupOptional.isPresent() || groupOptional.get().getNonDel() != true) {
			throw new GroupNotFoundException("Group not found");
		}
		if (!userOptional.isPresent() || userOptional.get().getNonDel() != true) {
			throw new UserNotFoundException("User not found");
		}
		Optional<UserGroup> userGroupOptional = userGroupRepository.findUserById(groupId, userId);
		if (!userGroupOptional.isPresent()) {
			return null;
		}

		UserGroup ug = userGroupOptional.get();
		User user = ug.getUser();
		UserResponse userResponse = new UserResponse();
		userResponse.addPropertiesFromUser(user);
		return userResponse;
	}

	/**
	 * @summary save a group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param group
	 * @return Group
	 */
	@Override
	public Group saveGroup(Group group) {
		return groupRepository.save(group);
	}

	/**
	 * @summary delete a group base on id
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @return Optional<Group>
	 */
	@Override
	public Optional<Group> deleteGroup(Long groupId) {
		Optional<Group> group = groupRepository.findByIdAndNonDel(groupId, true);
		int userSize = userService.getAllUserOfGroup(groupId).size();
		if (userSize == 0) {
			group.get().setNonDel(false);

			groupRoleRepository.deleteByGroupId(groupId);
			userGroupRepository.deleteUserGroupByGroupId(groupId);

			return Optional.ofNullable(groupRepository.save(group.get()));
		}
		return Optional.ofNullable(new Group());
	}
	/**
	 * @summary add all role of group to user role
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param group
	 * @param user
	 * @return void
	 */
	@Override
	public void addAllRoleOfGroupToUserRole(Group group, User user) {
		List<GroupRole> groupRoles = group.getGroupRoles();
		for (GroupRole groupRole : groupRoles) {
			Boolean checkExists = userRoleRepository.existsByUserIdAndRoleId(user.getId(), groupRole.getRole().getId());
			if (!checkExists) {
				UserRole ur = new UserRole(user, groupRole.getRole());
				userRoleRepository.save(ur);
			}
		}
	}

	@Override
	public Integer deleteListGroup(List<Long> groupIds) {
		if (groupIds.isEmpty()) {
			return 0;
		}
		for (Long groupId : groupIds) {
			Optional<Group> group = groupRepository.findByIdAndNonDel(groupId, true);
			group.get().setNonDel(false);
			groupRoleRepository.deleteByGroupId(groupId);
			userGroupRepository.deleteUserGroupByGroupId(groupId);
			groupRepository.save(group.get());

		}
		return 1;
	}

	/**
	 * @summary add User To Group By Email
	 * @author Thehap Rok
	 * @param email
	 * @return UserGroup
	 */
	@Override
	public UserGroup addUserToGroupByEmail(Long groupId, String email) {
		Optional<Group> group = groupRepository.findById(groupId);
		if (!group.isPresent()) {
			throw new GroupNotFoundException("Group not found !!!");
		}
		Optional<User> user = userRepository.findByEmail(email);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User not found!!!");
		}
		boolean checkUserInGroup = userGroupRepository.existsByGroupIdAndUserId(group.get().getId(),
				user.get().getId());
		if (checkUserInGroup) {
			throw new RuntimeException("User is exist in group!!!");
		}
		UserGroup userGroup = new UserGroup(user.get(), group.get());
		return userGroupRepository.save(userGroup);
	}

}
