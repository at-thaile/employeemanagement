package com.example.demo.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TokenVerifition;
import com.example.demo.repository.TokenVerificationRepository;
import com.example.demo.service.TokenVerificationService;

@Service
public class TokenVerificationServiceImpl implements TokenVerificationService {

	@Autowired
	private TokenVerificationRepository tokenVerificationRepository;


	@Override
	public TokenVerifition addToken(TokenVerifition tokenVerifition) {
		// TODO Auto-generated method stub
		return tokenVerificationRepository.save(tokenVerifition);
	}


	@Override
	public TokenVerifition editToken(TokenVerifition tokenVerifition) {
		// TODO Auto-generated method stub
		return tokenVerificationRepository.save(tokenVerifition);
	}


	@Transactional
	@Override
	public boolean deleteTokenById(Long id) {
		// TODO Auto-generated method stub
		Optional<TokenVerifition> optionalToken = tokenVerificationRepository.findById(id);
		if (!optionalToken.isPresent()) {
			return false;
		}
		tokenVerificationRepository.deleteTokenById(id);
		return true;
	}

	@Override
	public TokenVerifition findTokenByTokenCode(String token) {
		// TODO Auto-generated method stub
		Optional<TokenVerifition> optionalToken = tokenVerificationRepository.findByTokenCode(token);
		if (!optionalToken.isPresent()) {
			return null;
		}
		return optionalToken.get();
	}

}
