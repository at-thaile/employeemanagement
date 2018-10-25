package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	/**
	 * @summary login page
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @param model
	 * @param request
	 * @return String
	 */
	@GetMapping(path = "/login")
	public String login(Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/home";
		}
		Object error =  request.getSession().getAttribute("error");
		if (error instanceof String && (error != null || "".equals(error))) {
			request.getSession().removeAttribute("error");
			model.addAttribute("error", error);
		}
		return "login-page";
	}

	/**
	 * @summary home page 
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @return String
	 */
	@GetMapping(path = {"/","/home"})
	public String home() {
		return "user-home";
	}
	
}
