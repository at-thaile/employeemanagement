package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlller {

	@GetMapping("/users")
	public String showAllUser() {
		return "user";
	}
	
	@GetMapping("/user/edit/{userId}")
	public String editUser(@PathVariable Long userId, Model model) {
		model.addAttribute("id_user", userId);
		return "edit-user";
	}
	
	@GetMapping("/user/view/{userId}")
	public String viewUser(@PathVariable Long userId, Model model) {
		model.addAttribute("id_user", userId);
		return "view-user";
	}	

}
