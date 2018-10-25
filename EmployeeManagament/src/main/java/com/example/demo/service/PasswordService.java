package com.example.demo.service;

import java.security.Principal;

import com.example.demo.entity.User;
import com.example.demo.response.UserResponse;

public interface PasswordService {
	UserResponse viewCurrentUserResponse(Principal principal);
	User viewCurrentUsers(Principal principal);
	Boolean checkDuplicatePasswordCurrent(String existingPassword, String dbPassword);
	Boolean checkDuplicateNewPasswords(String newPassword,String existingPassword);
	Boolean checkDuplicateMatchingPassword(String newPassword, String NewMatchingPassword);
	User saveNewPasswords(User user, String newPassword);
}
