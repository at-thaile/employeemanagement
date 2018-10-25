package com.example.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @summary validate phone number
 * @author THAILE
 */
public class PasswordValidator implements ConstraintValidator<Password, String>{

	public void initialize(Password paramA) {
	}

	public boolean isValid(String password, ConstraintValidatorContext ctx) {
		if (password == null) {
			return false;
		}
		return password.matches("(?=.*\\d)(?=.*[@$&\\*])(?=.*[A-Z])([A-Za-z0-9@$&\\*]){5,15}");
	}
	
}
