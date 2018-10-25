package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.TokenVerifition;

public interface TokenVerificationRepository extends JpaRepository<TokenVerifition, Long>{

	/**
	* @summary find Token by TokenCode
	* @date Aug 23, 2018
	* @author ThaiLe
	* @param tokenCode
	* @return Optional<TokenVerifition>
	 */
	Optional<TokenVerifition> findByTokenCode(String tokenCode);
	
	
	/**
	* @summary delete Token_Verfication by id of Token_Verfition
	* @date Aug 23, 2018
	* @author ThaiLe
	* @param id
	* @return int
	 */
	@Modifying
	@Query(value = "delete   from token_verfication where id = ?1", nativeQuery = true)
	int  deleteTokenById(Long id);

}
