package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.entity.UserDetail;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.entity.dto.UserDTOEdit;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.UserResponse;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	private UserRepository userRepository;


	@Override
	public List<UserResponse> convertUserToUserResponse(List<User> listUser) {
		List<UserResponse> listUserResponse = new ArrayList<>();
		for (User user : listUser) {
			UserResponse userResponse = new UserResponse();
			userResponse.addPropertiesFromUser(user);
			listUserResponse.add(userResponse);
		}
		return listUserResponse;
	}

	/**
	 * @summary add one User into database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userDTO
	 * @return User
	 */
	@Modifying
	@Override
	public User addUser(UserDTO userDTO) {
		User user = convertUserDtoToUser(userDTO);
		return userRepository.save(user);
	}

	/**
	 * @summary update infomation of a User based on id of user
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userDTO
	 * @return
	 * @return User
	 */
	@Override
	public User updateUser(UserDTO userDTO) {
		User oldUser = this.getUserByEmail(userDTO.getEmail());
		
		oldUser.setPassword(userDTO.getPassword());
		UserDetail oldUserDetail = oldUser.getUserDetail();
		oldUserDetail.setAddress(userDTO.getAddress());
		oldUserDetail.setBirthDay(userDTO.getBirthday());
		oldUserDetail.setFullname(userDTO.getFullname());
		oldUserDetail.setGender(userDTO.getGender());
		oldUserDetail.setPhone(userDTO.getPhone());
		oldUserDetail.setUser(oldUser);
		oldUser.setUserDetail(oldUserDetail);
		return userRepository.save(oldUser);
	}

	@Transactional
	@Override
	public boolean deleteUserById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			return false;
		}
		userRepository.deleteUser(id);
		return true;
	}

	@Override
	public List<UserResponse> getAllUsers() {
		List<User> listUser = userRepository.findAllUserNotDeleted();
		return convertUserToUserResponse(listUser);
	}
	
	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	public UserResponse findUserById(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			return null;
		}
		UserResponse userResponse = new UserResponse();
		userResponse.addPropertiesFromUser(user.get());
		return userResponse;
	}


	@Override
	public boolean checkDuplicateEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isPresent())
			return true;
		return false;
	}

	@Override
	public User findUserByUserId(Long userID) {
		Optional<User> optionalUser = userRepository.findById(userID);
		if (!optionalUser.isPresent()) {
			return null;
		}
		return optionalUser.get();
	}


	@Override
	public User convertUserDtoToUser(UserDTO userDTO) {
		User user = new User();
		UserDetail userDetail = new UserDetail(userDTO.getFullname(), userDTO.getPhone(), userDTO.getAddress(),
				userDTO.getGender(), userDTO.getBirthday());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		userDetail.setUser(user);
		user.setUserDetail(userDetail);
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (!optionalUser.isPresent()) {
			return null;
		}
		return optionalUser.get();
	}
	

	@Transactional
	@Override
	public boolean activeUser(Long id) {
		Optional<User> optional = userRepository.findById(id);
		if(!optional.isPresent()) {
			return false;
		}
		userRepository.activeUser(id);
		return true;
	}
  

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}



	@Override
	public User saveUser(UserResponse userResponse) {
		
		if (userResponse ==null) {
			return null;
		}
		String fullname = userResponse.getFullname();
		Boolean gender = userResponse.getGender();
		String address = userResponse.getAddress();
		Date birthDay = userResponse.getBirthday();
		String phone = userResponse.getPhone();
		String email = userResponse.getEmail();
		Date createdAt = userResponse.getCreatedAt();
		String pathImage = userResponse.getPathImage();
		
		User user = getUserByEmail(email);
		user.getUserDetail().setAddress(address);
		user.getUserDetail().setBirthDay(birthDay);
		user.getUserDetail().setFullname(fullname);
		user.getUserDetail().setGender(gender);
		user.getUserDetail().setPhone(phone);
		user.getUserDetail().setCreatedAt(createdAt);
		user.getUserDetail().setPathImage(pathImage);
		
		return userRepository.save(user);
	}

	@Override
	public User editUser(User objUser) {
		return userRepository.save(objUser);
	}

	@Override
	public boolean removeUsers(List<Long> userIds) {
		for (Long id_user : userIds) {
			deleteUserById(id_user);
		}
		return true;
	}

	@Override
	public User addUser(UserDTO userDTO, boolean enable) {
		User user = this.convertUserDtoToUser(userDTO, enable);
		System.out.println(user.getEmail() + ", " + user.getPassword());
		return userRepository.save(user);
	}

	@Override
	public User convertUserDtoToUser(UserDTO userDTO, boolean enable) {
		User user = new User();
		UserDetail userDetail = new UserDetail(userDTO.getFullname(), userDTO.getPhone(), userDTO.getAddress(),
				userDTO.getGender(), userDTO.getBirthday());
		user.setEmail(userDTO.getEmail());
		user.setEnable(enable);
		user.setPassword(userDTO.getPassword());
		userDetail.setUser(user);
		user.setUserDetail(userDetail);
		System.out.println("convert: " + user.getEmail() + user.getUserDetail().getFullname());
		return user;
	}

	@Override
	public User editUser(UserDTOEdit userResponse) {
		User user = this.convertUserDTOEditToUser(userResponse);
		System.out.println(user.getEmail() + ", " + user.getPassword());
		return userRepository.save(user);
	}

	private User convertUserDTOEditToUser(UserDTOEdit userResponse) {
		User user = userRepository.findByEmail(userResponse.getEmail()).get();
		UserDetail userDetail = new UserDetail(userResponse.getFullname(), userResponse.getPhone(), userResponse.getAddress(),
				userResponse.getGender(), userResponse.getBirthday());
		userDetail = user.getUserDetail();
		userDetail.setFullname(userResponse.getFullname());
		userDetail.setPhone(userResponse.getPhone());
		userDetail.setGender(userResponse.getGender());
		userDetail.setBirthDay(userResponse.getBirthday());
		userDetail.setAddress(userResponse.getAddress());
		user.setEnable(userResponse.getEnable());
		user.setNonLocked(userResponse.getNonLocked());
		userDetail.setUser(user);
		user.setUserDetail(userDetail);
		return user;
	}

}
