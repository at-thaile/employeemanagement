package com.example.demo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SuccessLoginHandler implements AuthenticationSuccessHandler{
	


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		Object principal = authentication.getPrincipal();
		String email = null;
		if (principal instanceof UserDetails) {
		     email = ((UserDetails) principal).getUsername();
		} else {
		     email = principal.toString();
		}
		response.sendRedirect("/home");
	}

}
