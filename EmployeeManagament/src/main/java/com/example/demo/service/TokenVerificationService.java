package com.example.demo.service;

import com.example.demo.entity.TokenVerifition;

public interface TokenVerificationService {


	TokenVerifition addToken(TokenVerifition tokenVerifition);


	TokenVerifition editToken(TokenVerifition tokenVerifition);

	boolean deleteTokenById(Long id);

	TokenVerifition findTokenByTokenCode(String token);
}
