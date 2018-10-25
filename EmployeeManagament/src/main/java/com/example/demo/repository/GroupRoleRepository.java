package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Group;
import com.example.demo.entity.GroupRole;
import com.example.demo.entity.Role;



/**
 * 
 * @summary 
 * @date Aug 16, 2018
 * @author Tai
 * @return 
 */
@Repository
@Transactional
public interface GroupRoleRepository extends JpaRepository<GroupRole, Long>{
	/**
	 * 
	* @summary find by Group object
	* @date Aug 16, 2018
	* @author Tai
	* @param group
	* @return
	* @return List<GroupRole>
	 */
	List<GroupRole> findByGroup(Group group);
	/**
	 * 
	* @summary find By role Object
	* @date Aug 16, 2018
	* @author Tai
	* @param role
	* @return
	* @return List<GroupRole>
	 */
	List<GroupRole> findByRole(Role role);

	/**
	 * 
	* @summary find by Group id and role id
	* @date Aug 16, 2018
	* @author Tai
	* @param groupId
	* @param roleId
	* @return
	* @return Optional<GroupRole>
	 */
	Optional<GroupRole> findByGroupIdAndRoleId(Long groupId,Long roleId);
	/**
	 * 
	* @summary delete from role group base on group id and role id
	* @date Aug 16, 2018
	* @author Tai
	* @param groupId
	* @param roleId
	* @return
	* @return Integer
	 */
	@Modifying
	@Query(value= "delete from role_group where group_id=?1 and role_id =?2",nativeQuery=true)
	Integer deleteByGroupIdAndRoleId(Long groupId,Long roleId);
	
	/**
	 * @summary delete by group id
	 * @date Aug 23, 2018
	 * @author Tai
	 * @param groupId
	 * @return
	 * @return Integer
	 */
	@Modifying
	@Query(value= "delete from role_group where group_id=?1",nativeQuery=true)
	Integer deleteByGroupId(Long groupId);
	
	/**
	 * @summary delete by roleId
	 * @date Aug 23, 2018
	 * @author Tai
	 * @param roleId
	 * @return Integer
	 */
	@Modifying
	@Query(value= "delete from role_group where role_id=?1",nativeQuery=true)
	Integer deleteByRoleId(Long roleId);

}
