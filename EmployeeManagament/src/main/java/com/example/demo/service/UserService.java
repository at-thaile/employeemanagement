package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Group;
import com.example.demo.entity.User;
import com.example.demo.entity.UserGroup;
import com.example.demo.entity.UserRole;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.entity.dto.UserDTOEdit;
import com.example.demo.response.UserResponse;



/**
 * 
 * @summary handle service of user
 * @author Thehap Rok
 *
 */
public interface UserService {
	
	/**
	 * @summary convert list object UserGroup to list object User
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param userGroups
	 * @return List<User>
	 */
	List<User> convertUserGroupsToUsers(List<UserGroup> userGroups);

	/**
	 * @summary convert list object UserRole to list object User
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param userRoles
	 * @return List<User>
	 */
	List<User> convertUserRolesToUsers(List<UserRole> userRoles);

	/**
	 * @summary convert list object User to list object UserResponse
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param listUser
	 * @return List<UserResponse>
	 */
	List<UserResponse> convertUserToUserResponse(List<User> listUser);

	/**
	 * @summary get all user of group from database
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @return
	 * @return List<UserResponse>
	 */
	List<UserResponse> getAllUserOfGroup(Long groupId);

	/**
	 * @summary add one User into database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userDTO
	 * @return User
	 */
	User addUser(UserDTO userDTO);

	/**
	 * @summary update infomation of a User based on id of user
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userDTO
	 * @return
	 * @return User
	 */
	User updateUser(UserDTO userDTO);

	/**
	 * @summary delete User from database based on id of user
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param id
	 * @return boolean
	 */
	boolean deleteUserById(Long id);

	/**
	 * @summary return list of all user from database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @return List<UserResponse>
	 */
	List<UserResponse> getAllUsers();

	/**
	 * @summary find a user base on id of user, return object of type UserResponse
	 *          class
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userId
	 * @return UserResponse
	 */
	UserResponse findUserById(Long userId);

	/**
	 * @summary find a user base on id of user, return object of type User class
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userID
	 * @return User
	 */
	User findUserByUserId(Long userID);

	/**
	 * @summary check whether duplicate email existed of a user in database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param email
	 * @return boolean
	 */
	boolean checkDuplicateEmail(String email);

	/**
	 * @summary convert UserDTO object to User object
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userDTO
	 * @return User
	 */
	User convertUserDtoToUser(UserDTO userDTO);

	/**
	 * @summary return User base email of User
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param email
	 * @return User
	 */
	User getUserByEmail(String email);

	/**
	 * @summary remove all role of group when reomve user
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @param group
	 * @param user
	 * @return void
	 */
	void removeRoleOfGroupFromUserRole(Group group, User user);

	/**
	 * @summary upgrate role user to admin
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @param userId
	 * @return UserRole
	 */
	UserRole upgradeUserRole(Long userId,Long roleId);

	/**
	 * @summary active User
	 * @date Aug 22, 2018
	 * @author ThaiLe
	 * @param id
	 * @return boolean
	 */
	boolean activeUser(Long id);

	/**
	 * @summary delete all role of user
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @param userId
	 * @return void
	 */
	void deleteAllRoleOfUser(Long userId);

	/**
	 * @summary delete all group of user
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @param userId
	 * @return void
	 */
	void deleteUserGroup(Long userId);

	/**
	 * @summary edit User
	 * @date Aug 22, 2018
	 * @author ThaiLe
	 * @param objUser
	 * @return User
	 */
	User editUser(User objUser);

	/**
	 * @summary Save User
	 * @date Aug 21, 2018
	 * @author Tai
	 * @param user
	 * @return User
	 */
	User saveUser(User user);

	User saveUser(UserResponse userResponse);

	/**
	* @summary delete List Users base on id of user
	* @date Sep 3, 2018
	* @author ThaiLe
	* @param userIds
	* @return boolean
	 */
	boolean removeUsers(List<Long> userIds);
	
	/**
	* @summary add User and enable User
	* @date Sep 3, 2018
	* @author ThaiLe
	* @param userDTO
	* @param enable
	* @return User
	 */
	User addUser(UserDTO userDTO, boolean enable);
	
	/**
	* @summary convert UserDTO to User and enable User
	* @date Sep 3, 2018
	* @author ThaiLe
	* @param userDTO
	* @param enable
	* @return
	* @return User
	 */
	User convertUserDtoToUser(UserDTO userDTO, boolean enable);
	
	/**
	* @summary admin edit User
	* @date Sep 4, 2018
	* @author ThaiLe
	* @param userResponse
	* @return
	* @return User
	 */
	public User editUser(UserDTOEdit userResponse);

}
