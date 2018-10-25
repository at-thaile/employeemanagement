package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.UserService;

/**
 * @author Thehap Rok
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleRepository userRoleRepository;

	/**
	 * @summary load User By Username
	 * @author Thehap Rok
	 * @param username
	 * @return UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = userService.getUserByEmail(username);
			List<GrantedAuthority> listGrantedAuthority = getAllRoleOfUser(user.getId());
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					user.getEnable(), user.getNonDel(), true, user.getNonLocked(), listGrantedAuthority);
		} catch ( NullPointerException e ) {
			throw new UsernameNotFoundException("User Not Found!!!");
		}
	}

	/**
	 * @summary get all role of user
	 * @author Thehap Rok
	 * @param userId
	 * @return List<GrantedAuthority>
	 */
	public List<GrantedAuthority> getAllRoleOfUser(Long userId) {
		List<GrantedAuthority> listGrantedAuthority = new ArrayList<>();
		List<UserRole> listUserRole = userRoleRepository.findByUserId(userId);
		for (UserRole userRole : listUserRole) {
			String roleName = userRole.getRole().getRoleName();
			listGrantedAuthority.add(new SimpleGrantedAuthority(roleName));
		}
		return listGrantedAuthority;
	}
}
