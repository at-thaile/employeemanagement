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

	/**
	 * @summary add TokenVerfition
	 * @date Aug 23, 2018
	 * @author ThaiLe
	 * @param tokenVerifition
	 * @return TokenVerifition
	 */
	@Override
	public TokenVerifition addToken(TokenVerifition tokenVerifition) {
		// TODO Auto-generated method stub
		return tokenVerificationRepository.save(tokenVerifition);
	}

	/**
	 * @summary add TokenVerfition
	 * @date Aug 23, 2018
	 * @author ThaiLe
	 * @param tokenVerifition
	 * @return TokenVerifition
	 */
	@Override
	public TokenVerifition editToken(TokenVerifition tokenVerifition) {
		// TODO Auto-generated method stub
		return tokenVerificationRepository.save(tokenVerifition);
	}

	/**
	 * @summary delete Token By id
	 * @date Aug 23, 2018
	 * @author ThaiLe
	 * @param id
	 * @return boolean
	 */
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

	/**
	 * @summary find TokenVerfition by TokenCode
	 * @date Aug 23, 2018
	 * @author ThaiLe
	 * @param token
	 * @return TokenVerifition
	 */
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
