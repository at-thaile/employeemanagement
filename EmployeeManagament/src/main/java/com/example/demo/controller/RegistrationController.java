package com.example.demo.controller;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Role;
import com.example.demo.entity.TokenVerifition;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.response.UserDTOResponse;
import com.example.demo.service.MailService;
import com.example.demo.service.RoleService;
import com.example.demo.service.TokenVerificationService;
import com.example.demo.service.UserRoleService;
import com.example.demo.service.UserService;
import com.example.demo.util.RoleSystem;
import com.example.demo.util.VerificationUtil;

@Controller
public class RegistrationController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private VerificationUtil veritificationUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenVerificationService tokenVerificationService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * @summary show regist page
	 * @author ThaiLe
	 * @param model
	 * @return String
	 */
	@GetMapping(path="/registerAccount")
	public String showRegisterPage() {		
		return "add-user";
	}
	
	/**
	 * @summary register account
	 * @author ThaiLe
	 * @param userModel
	 * @param rs
	 * @param model
	 * @return ResponseEntity<String>
	 */
	
	@PostMapping(path="/registerAccount")	
	public  ResponseEntity<Object> registNewAccount(@Valid @RequestBody UserDTO userModel,BindingResult result) {
		try {
		UserDTOResponse userDTOResponse = new UserDTOResponse();		  
	      if(result.hasErrors()){
	          Map<String, String> errors = result.getFieldErrors().stream()
	                .collect(
	                      Collectors.toMap(FieldError::getField, ObjectError::getDefaultMessage)	                     
	                  );	        
	          if(result.getAllErrors().toString().indexOf("PasswordMatches")!= -1) {
	        	  errors.put("matchingPassword", "Password is not matched");
	          }
	          userDTOResponse.setValidated(false);
	          userDTOResponse.setErrorMessages(errors); 
	          return new ResponseEntity<Object>(userDTOResponse, HttpStatus.BAD_REQUEST);
	      }
		if(userService.checkDuplicateEmail(userModel.getEmail())) {
			return new ResponseEntity<Object> ("Email is existed", HttpStatus.CONFLICT);
		}
		else {
			String registCode = veritificationUtil.generateVerificationCode(userModel.getEmail()+userModel.getPassword());
			Date expireDate = veritificationUtil.calculatorExpireTime();
			String passwordEncode = passwordEncoder.encode(userModel.getPassword());
			userModel.setPassword(passwordEncode);
			try {
				mailService.sendMail("active account","/activeAccount",userModel.getEmail(),registCode,expireDate);
				User user = userService.addUser(userModel);
				TokenVerifition tokenVerifition = new TokenVerifition(user, registCode, expireDate, 0);
				tokenVerificationService.addToken(tokenVerifition);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ResponseEntity<>("Created user successfully", HttpStatus.OK);
		}
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * @summary active account
	 * @author ThaiLe
	 * @param request
	 * @param registCode
	 * @param model
	 * @throws MessagingException
	 * @return ResponseEntity<String>
	 */
	@GetMapping(path="activeAccount")
	public  ResponseEntity<String> activeAccount(HttpServletRequest request, @RequestParam("token")String registCode,Model model) throws MessagingException {

		TokenVerifition tokenVerification = tokenVerificationService.findTokenByTokenCode(registCode);
		if(tokenVerification == null) {
			return new ResponseEntity<String>("Token is not true", HttpStatus.NOT_FOUND);
		}
		User objUsers= tokenVerification.getUser();		
		
		if(objUsers != null) {
			
			Date nowDay = new Date();
			if(tokenVerification.getExpireTime().getTime() < nowDay.getTime()) {
				tokenVerification.setExpireTime(veritificationUtil.calculatorExpireTime());
				tokenVerification.setTokenCode(veritificationUtil.generateVerificationCode(objUsers.getEmail() + objUsers.getPassword()));
				tokenVerificationService.editToken(tokenVerification);
				mailService.sendMail("Active Account","/activeAccount",objUsers.getEmail(),tokenVerification.getTokenCode(),tokenVerification.getExpireTime());
				return new ResponseEntity<>("We sent you a new token", HttpStatus.OK);
			}else {
				boolean check = userService.activeUser(objUsers.getId());
				if(check == true) {
					tokenVerificationService.deleteTokenById(tokenVerification.getId());
					Role role = roleService.findByRoleName(RoleSystem.USER);
					User user = userService.findUserByUserId(objUsers.getId());
					UserRole userRole = new UserRole(user, role);
					userRoleService.addUserWithRole(userRole);
					return new ResponseEntity<>("Active user successfully", HttpStatus.OK);
				}else {
					return new ResponseEntity<>("Active user fail", HttpStatus.BAD_REQUEST);
				}
			}			
		}	
	    return new ResponseEntity<>("Active user fail", HttpStatus.BAD_REQUEST);
	}
	
	
	
}
