package com.example.demo.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author ThaiLe
 */
@Component
public class VerificationUtil {

	@Autowired
	PasswordEncoder encoder;

	/**
	* @summary generate TokenCode
	* @date Aug 23, 2018
	* @author ThaiLe
	* @param emailAndPassword
	* @return String
	 */
	public String generateVerificationCode(String emailAndPassword) {
		StringBuilder input = new StringBuilder(emailAndPassword);
		Date now = new Date();
		input.append(now.getTime());
        return encoder.encode(input.toString());
	}

	/**
	* @summary calculate expire time
	* @date Aug 23, 2018
	* @author ThaiLe
	* @return Date
	 */
	public Date calculatorExpireTime() {
		Date now = new Date();
		long time = now.getTime() + 86400000;
		Date expireTime = new Date(time);
		return expireTime;
	}
}
