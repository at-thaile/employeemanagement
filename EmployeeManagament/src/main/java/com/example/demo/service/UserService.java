package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Group;
import com.example.demo.entity.User;
import com.example.demo.entity.UserGroup;
import com.example.demo.entity.UserRole;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.entity.dto.UserDTOEdit;
import com.example.demo.response.UserResponse;


public interface UserService {
	



	List<UserResponse> convertUserToUserResponse(List<User> listUser);


	User addUser(UserDTO userDTO);


	User updateUser(UserDTO userDTO);


	boolean deleteUserById(Long id);


	List<UserResponse> getAllUsers();


	UserResponse findUserById(Long userId);


	User findUserByUserId(Long userID);


	boolean checkDuplicateEmail(String email);

	
	User convertUserDtoToUser(UserDTO userDTO);


	User getUserByEmail(String email);



	boolean activeUser(Long id);





	User editUser(User objUser);


	User saveUser(User user);

	User saveUser(UserResponse userResponse);


	boolean removeUsers(List<Long> userIds);
	

	User addUser(UserDTO userDTO, boolean enable);
	

	User convertUserDtoToUser(UserDTO userDTO, boolean enable);
	
	public User editUser(UserDTOEdit userResponse);

}
