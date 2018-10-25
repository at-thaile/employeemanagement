package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	// @summary find by group id in table GroupRole
	List<Role> findByGroupRolesGroupId(long group);

	// @summary find role by id
	Optional<Role> findByIdAndNonDel(long id, boolean nonDel);

	// @summary find role by id
	Optional<Role> findById(Long roleId);

	// @summary find by non del
	List<Role> findByNonDel(Boolean nonDel);

	// @summary find by role name containing
	List<Role> findByRoleNameContaining(String name);

	// @summary find by role
	Role findByRoleName(String roleName);

	/**
	 * 
	 * @summary
	 * @date Sep 5, 2018
	 * @author Tai
	 * @param groupId
	 * @param name
	 * @return
	 * @return List<Role>
	 */
	@Query("select r from Role r " + "where r.id not in " + "(select ur.role.id from GroupRole ur "
			+ "where ur.group.id=:groupId) " + "and r.nonDel=true and (r.roleName like %:param% ) and r.scope='GROUP'")
	List<Role> findRoleNotInGroupByName(@Param("groupId") Long groupId, @Param("param") String name);

	/**
	 * @summary
	 * @date Aug 17, 2018
	 * @author Thehap Rok
	 * @param id
	 * @return int
	 */
	@Transactional
	@Modifying
	@Query(value = "update role set non_del = 0 where id=?1", nativeQuery = true)
	int deleteRole(Long id);

	/**
	 * @summary find role by scope (system or group)
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param scope
	 * @return List<Role>
	 */
	List<Role> findByScopeAndNonDel(String scope, Boolean nonDel);
}
