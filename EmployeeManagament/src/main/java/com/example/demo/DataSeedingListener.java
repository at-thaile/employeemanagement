package com.example.demo;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserDetail;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.util.RoleScope;
import com.example.demo.util.RoleSystem;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository; 
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@SuppressWarnings("deprecation")
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Role role;
		if (roleRepository.findByRoleName("ADMIN") == null) {
			role = new Role(RoleSystem.ADMIN,"Admin Role",RoleScope.SYSTEM);
			roleRepository.save(role);
		}
		
		if (roleRepository.findByRoleName("USER") == null) {
			role = new Role(RoleSystem.USER,"User Role",RoleScope.SYSTEM);
			roleRepository.save(role);
		}
		
		User user;
		if(!userRepository.findByEmail("usermanagementsummer2018@gmail.com").isPresent()) {
			user = new User();
			user.setEmail("usermanagementsummer2018@gmail.com");
			user.setEnable(true);
			user.setNonDel(true);
			user.setNonLocked(true);
			user.setPassword(passwordEncoder.encode("admin"));
			UserDetail userDetail = new UserDetail("ADMIN", "01223546613", "103 Nguyen Luong Bang", false, new Date("1996/01/01"));
			user.setUserDetail(userDetail);
			userRepository.save(user);
		}
		
		if(!userRoleRepository.existsByRoleRoleNameAndUserEmail("ADMIN","usermanagementsummer2018@gmail.com")) {
			user = userRepository.findByEmail("usermanagementsummer2018@gmail.com").get();
			role = roleRepository.findByRoleName("ADMIN");
			UserRole userRole = new UserRole(user, role);
			userRoleRepository.save(userRole);
		}
	}

}
