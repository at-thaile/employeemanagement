package com.example.demo.service;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Group;
import com.example.demo.entity.User;
import com.example.demo.entity.UserGroup;
import com.example.demo.response.UserResponse;

/**
 * @summary interface group service
 * @date Aug 16, 2018
 * @author tai
 * @return
 */
public interface GroupService {
	/**
	 * @summary add user to group
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return UserGroup
	 */
	UserGroup addNewUserToGroup(Long groupId, Long userId);

	/**
	 * @summary add all role of group to user role
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param group
	 * @param user
	 * @return void
	 */
	void addAllRoleOfGroupToUserRole(Group group, User user);

	/**
	 * @summary remove user in group
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return Integer
	 */
	Integer removeUserFromGroup(Long groupId, Long userId);

	/**
	 * @summary remove multi user in group
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userIds
	 * @return Integer
	 */
	Integer removeListUseFromGroup(Long groupId, List<Long> userIds);

	/**
	 * @summary
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param nameOrEmail
	 * @return List<UserResponse>
	 */
	List<UserResponse> findUserNotInGroupByNameOrEmail(Long groupId, String nameOrEmail);

	/**
	 * @summary get infor of user in group
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return
	 * @return UserResponse
	 */
	UserResponse getInforOfUser(Long groupId, Long userId);

	/**
	 * @summary view all group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @return List<Group>
	 */
	List<Group> viewAllGroup();

	/**
	 * @summary view a group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @return Optional<Group>
	 */
	Optional<Group> viewGroup(Long groupId);

	/**
	 * @summary add a group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param group
	 * @return Group
	 */
	Group addGroup(Group group);

	/**
	 * @summary save a group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param group
	 * @return Group
	 */
	Group saveGroup(Group group);

	/**
	 * @summary delete a group base on id
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @return Optional<Group>
	 */
	Optional<Group> deleteGroup(Long groupId);
	
	/**
	 * 
	* @summary delete list group 
	* @date Aug 30, 2018
	* @author Tai
	* @param groupIds
	* @return
	* @return Optional<Group>
	 */
	Integer deleteListGroup(List<Long> groupIds);

	/**
	 * @summary add User To Group By Email
	 * @author Thehap Rok
	 * @param email
	 * @return UserGroup
	 */
	UserGroup addUserToGroupByEmail(Long groupId,String email);
}
