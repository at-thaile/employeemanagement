package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	/**
	 * @summary check user have role by userId And roleId
	 * @author TaiTruong
	 * @param userId
	 * @param roleId
	 * @return Boolean
	 */
	Boolean existsByUserIdAndRoleId(Long userId, Long roleId);

	/**
	 * @summary find role user by userId
	 * @author TaiTruong
	 * @param userId
	 * @return List<UserRole>
	 */
	List<UserRole> findByUserId(Long userId);

	/**
	 * @summary delete role of user by userId and roleId
	 * @author Thehap Rok
	 * @param userId
	 * @param roleId
	 * @return Integer
	 */
	@Transactional
	@Modifying
	@Query(value = "delete from user_role where user_id=?1 and role_id =?2", nativeQuery = true)
	Integer deleteByUserIdAndRoleId(Long userId, Long roleId);

	/**
	 * @summary delete role of User by userId and roleId
	 * @author Thehap Rok
	 * @param userId
	 * @return void
	 */
	@Transactional
	@Modifying
	@Query(value = "delete from user_role where user_id=?1", nativeQuery = true)
	void deleteUserRoleByUserId(Long userId);

	/**
	 * 
	 * @summary
	 * @date Sep 5, 2018
	 * @author Tai
	 * @param roleId
	 * @return void
	 */
	@Transactional
	@Modifying
	@Query(value = "delete from user_role where role_id=?1", nativeQuery = true)
	void deleteUserRoleByRoleId(Long roleId);

	/**
	 * @summary delete all role system of user
	 * @author Thehap Rok
	 * @param userId
	 * @param roleId
	 * @return void
	 */
	@Transactional
	@Modifying

	@Query(value="delete from user_role where user_role.user_id = ?1 and user_role.role_id in (select id from role where scope='SYSTEM')",nativeQuery=true)
	void deleteByUserIdAndRoleIdOfSystem(Long userId);
	
	/**
	 * @param roleName
	 * @param userEmail
	 * @return Boolean
	 */
	Boolean existsByRoleRoleNameAndUserEmail(String roleName,String userEmail);

}
