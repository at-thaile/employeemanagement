package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Group;

@Repository
@Transactional
public interface GroupRepository extends JpaRepository<Group, Long> {

	/**
	 * @summary find group by id
	 * @param groupId
	 * @return Optional<Group>
	 */
	Optional<Group> findById(Long groupId);

	/**
	 * @summary search group base on nonDel column
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param nonDel
	 * @return
	 * @return List<Group>
	 */
	List<Group> findByNonDel(Boolean nonDel);

	/**
	 * 
	 * @summary search group object base on id and nonDel
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param nonDel
	 * @return
	 * @return Optional<Group>
	 */
	Optional<Group> findByIdAndNonDel(Long groupId, Boolean nonDel);

	/**
	 * 
	 * @summary search group in GroupRoles object base on role id
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param roleId
	 * @return
	 * @return List<Group>
	 */
	List<Group> findByGroupRoles_Role(Long roleId);

	/**
	 * 
	* @summary delete Group
	* @date Aug 30, 2018
	* @author Tai
	* @param groupId
	* @return
	* @return Integer
	 */
	@Modifying
	@Query(value= "delete from group_detail where id=?1",nativeQuery=true)
	Integer deleteByGroupId(Long groupId);
}
