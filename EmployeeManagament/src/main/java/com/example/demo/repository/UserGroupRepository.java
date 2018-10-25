package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserGroup;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

	/**
	 * @summary find all user of group by groupId
	 * @author Thehap Rok
	 * @param groupId
	 * @return List<UserGroup>
	 */
	@Query("select ug from UserGroup ug where ug.group.id = ?1 and ug.group.nonDel = 1 and ug.user.nonLocked=1")
	List<UserGroup> findAllUserOfGroupId(Long groupId);

	/**
	 * @summary find User in group By userId and groupId
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return Optional<UserGroup>
	 */
	@Query("select ug from UserGroup ug where ug.group.id = ?1 and ug.user.id = ?2 "
			+ "and ug.group.nonDel = 1 and ug.user.nonDel = 1")
	Optional<UserGroup> findUserById(Long groupId, Long userId);

	/**
	 * @summary delete User From Group by groupId and userId
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return Integer
	 */
	@Transactional
	@Modifying
	@Query("delete from UserGroup ug where ug.group.id=?1 and ug.user.id=?2")
	Integer deleteUserFromGroup(Long groupId, Long userId);
	
	/**
	 * @summary check user exists in group by groupId and userId
	 * @author TaiTruong
	 * @param groupId
	 * @param userId
	 * @return boolean
	 */
	boolean existsByGroupIdAndUserId(Long groupId,Long userId);	

	/**
	 * @summary delete User in Group by userId
	 * @author Thehap Rok
	 * @param userId
	 * @return void
	 */
	@Transactional
	@Modifying
	@Query(value="delete from user_group where user_id=?1",nativeQuery=true)
	void deleteUserGroupByUserId(Long userId);
	
	@Transactional
	@Modifying
	@Query(value="delete from user_group where group_id=?1",nativeQuery=true)
	void deleteUserGroupByGroupId(Long userId);
}
