package com.example.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @summary validate phone number
 * @author THAILE
 */
public class PhoneValidator implements ConstraintValidator<Phone, String>{

	public void initialize(Phone paramA) {
	}

	public boolean isValid(String phoneNo, ConstraintValidatorContext ctx) {
		if (phoneNo == null) {
			return false;
		}
		return phoneNo.matches("\\d{10,12}");
	}
	
}
