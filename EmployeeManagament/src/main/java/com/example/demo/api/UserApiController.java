package com.example.demo.api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.entity.dto.UserDTOEdit;
import com.example.demo.exception.GroupNotFoundException;
import com.example.demo.exception.RoleNotFoundException;
import com.example.demo.exception.UserAlreadyRoleException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.response.UserDTOResponse;
import com.example.demo.response.UserEditResponse;
import com.example.demo.response.UserResponse;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserRoleService;
import com.example.demo.service.UserService;
import com.example.demo.util.RoleSystem;
import com.example.demo.wrapper.ListIdWrapper;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * @summary api get all user from database
	 * @date Aug 13, 2018
	 * @author ThaiLe
	 * @return ResponseEntity<Object>
	 */
	@GetMapping
	public ResponseEntity<Object> getAllUser() {
		List<UserResponse> listUser = userService.getAllUsers();
		if (listUser.size() == 0) {
			return new ResponseEntity<>(listUser, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listUser, HttpStatus.OK);

	}
	
	/**
	 * 
	 * @summary api get an user from id of user
	 * @date Aug 13, 2018
	 * @author ThaiLe
	 * @param id
	 * @return UserResponse
	 */
	@GetMapping(path = "/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable("id") long id) {
		UserResponse userResponse = userService.findUserById(id);
		if (userResponse == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	/**
	 * @summary api delete a user from database base on id of user
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param id
	 * @return ResponseEntity<Object>
	 */
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> removeUserById(@PathVariable("id") long id) {
		boolean rs = userService.deleteUserById(id);
		if (!rs) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Delete user successfully", HttpStatus.OK);
	}

	/**
	 * @summary api add a user into database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userDTO
	 * @return ResponseEntity<String>
	 */	
	@PostMapping
	public ResponseEntity<Object> createNewUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
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
	       }else {
		   		if (userService.checkDuplicateEmail(userDTO.getEmail())) {
		   			System.out.println("email");
					return new ResponseEntity<Object>("Email is existed", HttpStatus.CONFLICT);
				}
		   		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		   		User objUser = userService.addUser(userDTO, true);
		   		if(objUser == null) {
		   			return new ResponseEntity<Object>("Create User Fail", HttpStatus.BAD_REQUEST);
		   		}
				Role role = roleService.findByRoleName(RoleSystem.USER);
				User user = userService.findUserByUserId(objUser.getId());
				UserRole userRole = new UserRole(user, role);
				userRoleService.addUserWithRole(userRole);		   		
		   		System.out.println("success");
		   		return new ResponseEntity<Object>("Create user sucessfully", HttpStatus.OK);
		   		
	       }	    
	}	

	/**
	 * @summary api edit a user from database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userDTO
	 * @return ResponseEntity<String>
	 */
	@PutMapping
	public ResponseEntity<Object> editUser(@Valid @RequestBody UserDTOEdit userResponse, BindingResult result) {
		  UserEditResponse userEditResponse = new UserEditResponse();	
		   
	      if(result.hasErrors()){
	          Map<String, String> errors = result.getFieldErrors().stream()
	                .collect(
	                      Collectors.toMap(FieldError::getField, ObjectError::getDefaultMessage)	                     
	                  );	        
	         
	          userEditResponse.setValidated(false);
	          userEditResponse.setErrorMessages(errors); 
	          return new ResponseEntity<Object>(userEditResponse, HttpStatus.BAD_REQUEST);
	       }else {	    	   
		   		User oldUser = userService.getUserByEmail(userResponse.getEmail());
				if (oldUser == null) {					
					return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
				}
				User user = userService.editUser(userResponse);
				try {
					userService.upgradeUserRole(user.getId(),userResponse.getId_role());
				} catch (UserNotFoundException e) {
					return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
				} catch(RoleNotFoundException e) {
					return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
				}
		   		return new ResponseEntity<Object>("Edit user sucessfully", HttpStatus.OK);		   		
	       }
	}
	/**
	 * @summary upgrade user to admin
	 * @date Aug 17, 2018
	 * @author Thehap Rok
	 * @param userId
	 * @return ResponseEntity<Object>
	 */
	@PutMapping("/upgrade-user-role/{userId}/{roleId}")
	public ResponseEntity<Object> upgradeUserRole(@PathVariable("userId") Long userId,
			@PathVariable("roleId")Long roleId) {
		UserRole upgradeRole = null;
		try {
			upgradeRole = userService.upgradeUserRole(userId,roleId);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch(RoleNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (UserAlreadyRoleException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(upgradeRole, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<Object> removeListUserFromGroup(@RequestBody ListIdWrapper listIdWapper) {
		try {
			List<Long> userIds = listIdWapper.getIds();
			userService.removeUsers(userIds);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (GroupNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Remove user successful", HttpStatus.OK);
	}	
}
