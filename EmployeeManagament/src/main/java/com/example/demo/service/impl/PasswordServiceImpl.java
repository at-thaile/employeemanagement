
package com.example.demo.service.impl;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.response.UserResponse;
import com.example.demo.service.PasswordService;
import com.example.demo.service.UserService;

@Service
public class PasswordServiceImpl implements PasswordService  {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public UserResponse viewCurrentUserResponse(Principal principal) {
		String email = principal.getName();
		User user = userService.getUserByEmail(email);
		UserResponse userRespone = new UserResponse();
		userRespone.addPropertiesFromUser(user);
		return userRespone;
	}

	@Override
	public User viewCurrentUsers(Principal principal) {
		User user = userService.getUserByEmail(principal.getName());
		return user;
	}

	@Override
	public Boolean checkDuplicatePasswordCurrent(String existingPassword, String dbPassword) {
		return passwordEncoder.matches(existingPassword, dbPassword);
	}

	@Override
	public Boolean checkDuplicateNewPasswords(String newPassword, String existingPassword) {
		return newPassword.equals(existingPassword);
	}

	@Override
	public Boolean checkDuplicateMatchingPassword(String newPassword, String newMatchingPassword) {
		return newPassword.equals(newMatchingPassword);
	}

	@Override
	public User saveNewPasswords(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		userService.saveUser(user);
		return user;
	}

}
