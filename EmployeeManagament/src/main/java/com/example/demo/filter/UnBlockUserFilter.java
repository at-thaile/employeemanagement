package com.example.demo.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.example.demo.entity.BlockUser;
import com.example.demo.entity.User;
import com.example.demo.service.BlockUserService;
import com.example.demo.service.UserService;

/**
 * @summary un block for user when user login again and time login more than
 *          expire time
 * @author Thehap Rok
 *
 */
@Component
public class UnBlockUserFilter extends GenericFilterBean {

	@Autowired
	private UserService userService;

	@Autowired
	private BlockUserService blockUserService;
	
	/**
	 * Get email from request check time expire and now to un block user
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String email = request.getParameter("email");
		if (email != null) {
			User user = userService.getUserByEmail(email);
			if (user != null) {
				BlockUser blockUser = user.getBlockUser();
				if (!user.getNonLocked()) {
					Date now = new Date();
					Date expireTimeBlock = blockUser.getBlockTime();
					if (expireTimeBlock.getTime() < now.getTime()) {
						blockUserService.deleteBlockUser(blockUser.getId());
						user.setNonLocked(true);
						userService.saveUser(user);
					}
				}
			}
		}
		chain.doFilter(request, response);
	}

}
