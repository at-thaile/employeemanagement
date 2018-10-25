package com.example.demo.service;

import com.example.demo.entity.TokenVerifition;

public interface TokenVerificationService {

	/**
	 * @summary add TokenVerfition
	 * @date Aug 23, 2018
	 * @author ThaiLe
	 * @param tokenVerifition
	 * @return TokenVerifition
	 */
	TokenVerifition addToken(TokenVerifition tokenVerifition);

	/**
	 * @summary add TokenVerfition
	 * @date Aug 23, 2018
	 * @author ThaiLe
	 * @param tokenVerifition
	 * @return TokenVerifition
	 */
	TokenVerifition editToken(TokenVerifition tokenVerifition);

	/**
	 * @summary delete Token By id
	 * @date Aug 23, 2018
	 * @author ThaiLe
	 * @param id
	 * @return boolean
	 */
	boolean deleteTokenById(Long id);

	/**
	 * @summary find TokenVerfition by TokenCode
	 * @date Aug 23, 2018
	 * @author ThaiLe
	 * @param token
	 * @return TokenVerifition
	 */
	TokenVerifition findTokenByTokenCode(String token);
}
