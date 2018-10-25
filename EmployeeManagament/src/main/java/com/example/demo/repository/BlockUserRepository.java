package com.example.demo.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BlockUser;


@Repository
@Transactional
public interface BlockUserRepository extends JpaRepository<BlockUser, Long> {
	/**
	* @summary delete BlockUser
	* @date Aug 23, 2018
	* @author ThaiLe
	* @param id
	* @return int
	 */
	@Modifying
	@Query(value = "delete from block_user where id = ?1", nativeQuery = true)
	int  deleteBlockUserById(Long id);
}
