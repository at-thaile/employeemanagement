package com.example.demo.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Group;
import com.example.demo.entity.GroupRole;
import com.example.demo.entity.Role;
import com.example.demo.query.RoleQueryCondition;
import com.example.demo.service.RoleGroupService;
import com.example.demo.service.RoleService;
import com.example.demo.service.SearchService;
import com.example.demo.util.EntityName;
import com.example.demo.util.RoleScope;
import com.example.demo.wrapper.ListIdWrapper;

@RestController
@RequestMapping("/api/roles")
public class RoleApiController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleGroupService roleGroupService;
	
	@Autowired
	private SearchService searchService;

	/**
	 * @summary api get all role from database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @return ResponseEntity<Object>
	 */
	@GetMapping
	public ResponseEntity<Object> getAllRole() {
		List<Role> listRole = roleService.getAllRoles();
		if (listRole.size() == 0) {
			return new ResponseEntity<>(listRole, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listRole, HttpStatus.OK);
	}

	/**
	 * @summary get all role have scope `system` 
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @return ResponseEntity<Object>
	 */
	@GetMapping("/sys")
	public ResponseEntity<Object> getAllRoleScopeSystem() {
		List<Role> listRole = roleService.getListRoleByScope(RoleScope.SYSTEM);
		if (listRole.size() == 0) {
			return new ResponseEntity<>("No data", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listRole, HttpStatus.OK);
	}

	/**
	 * @summary get all role have scope `group`
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @return ResponseEntity<Object>
	 */
	@GetMapping("/groups")
	public ResponseEntity<Object> getAllRoleScopeGroup() {
		List<Role> listRole = roleService.getListRoleByScope(RoleScope.GROUP);
		if (listRole.size() == 0) {
			return new ResponseEntity<>("No data", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listRole, HttpStatus.OK);
	}

	/**
	 * @summary api get 1 role from database base on id of role
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param id
	 * @return ResponseEntity<Object>
	 */
	@GetMapping(path = "/{id}")
	public ResponseEntity<Object> getRoleById(@PathVariable("id") long id) {
		Role objRole = roleService.getRoleById(id);
		if (objRole == null) {
			return new ResponseEntity<>("Not found role", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(objRole, HttpStatus.OK);
	}

	/**
	 * @summary delete a role from database base on id of role
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param id
	 * @return ResponseEntity<String>
	 */
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<String> deleteRoleById(@PathVariable("id") long id) {
		Role objRole = roleService.getRoleById(id);
		if (objRole == null) {
			return new ResponseEntity<String>("Not found role", HttpStatus.NOT_FOUND);
		}
		roleService.deleteRoleById(id);
		return new ResponseEntity<String>("Delete role successfully", HttpStatus.OK);
	}

	/**
	 * @summary api add a role into database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param role
	 * @return ResponseEntity<String>
	 */
	@PostMapping
	public ResponseEntity<String> addNewRole(@Valid @RequestBody Role role,BindingResult result) {
		if(result.hasErrors()) {
			String fieldName = result.getFieldError().getField();
			String error = result.getFieldError().getDefaultMessage();
			return new ResponseEntity<String>(fieldName+": "+error, HttpStatus.BAD_REQUEST);
		}
		Role oldRole = roleService.findByRoleName(role.getRoleName());
		if (oldRole != null) {
			return new ResponseEntity<String>("Role was existed", HttpStatus.CONFLICT);
		}

		try {
			roleService.addRole(role);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return new ResponseEntity<String>("Add role successfully", HttpStatus.OK);
	}

	/**
	 * @summary api edit a role from database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param role
	 * @return ResponseEntity<String>
	 */
	@PutMapping
	public ResponseEntity<String> editRole(@Valid @RequestBody Role role) {
		Role oldRole = roleService.getRoleById(role.getId());
		if (oldRole == null) {
			return new ResponseEntity<String>("Not found role", HttpStatus.NOT_FOUND);
		}
		role.setGroupRoles(oldRole.getGroupRoles());
		role.setUserRoles(oldRole.getUserRoles());
		role.setCreatedAt(oldRole.getCreatedAt());
		role.setScope(oldRole.getScope());
		Role editRole = null;
		try {
			editRole = roleService.editRole(role);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<String>("Can't edit role", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Edit role successfully " + editRole.getRoleName(), HttpStatus.OK);
	}
	
	/**
	 * 
	* @summary view all list groups in role
	* @date Aug 20, 2018
	* @author Tai
	* @param id
	* @return ResponseEntity<Object>
	*/
	@GetMapping("/{id}/groups")
	public ResponseEntity<Object> viewRolesOfGroup(@PathVariable("id") long id) {
		if (roleGroupService.existsByRole(id)) {
			return new ResponseEntity<>("Role Not Found id: " + id,HttpStatus.NOT_FOUND);
		}
		List<GroupRole> list = roleGroupService.findAllGroupByRole(id);
		List<Group> listGroup = roleGroupService.convertGroupRoleToGroup(list);
		if (list.isEmpty()) {
			return new ResponseEntity<>("no role", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listGroup, HttpStatus.OK);
	}

	/**
	 * 
	* @summary add a group in role
	* @date Aug 20, 2018
	* @author Tai
	* @param roleId
	* @param groupId
	* @return ResponseEntity<Object>
	 */
	@PostMapping("/{roleId}/groups/{groupId}")
	public ResponseEntity<Object> creadRoleGroup(@PathVariable("roleId") long roleId,
			@PathVariable("groupId") long groupId) {
		GroupRole groupRole = roleGroupService.addGroupToRole(groupId, roleId);
		if (groupRole == null) {
			return new ResponseEntity<>("Group da co trong role", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("add Group successful", HttpStatus.CREATED);
	}

	/**
	 * 
	* @summary delete a group in role
	* @date Aug 20, 2018
	* @author Tai
	* @param groupId
	* @param roleId
	* @return ResponseEntity<Object>
	 */
	@DeleteMapping("/{roleId}/groups/{groupId}")
	public ResponseEntity<Object> removeRoleFromGroup(@PathVariable("groupId") long groupId,
			@PathVariable("roleId") long roleId) {
		boolean deleted = roleGroupService.deleteGroupFormRole(groupId, roleId);
		if (!deleted) {
			return new ResponseEntity<>("RoleId not found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("delete successful", HttpStatus.OK);
	}

	/**
	 * 
	* @summary delete a list group in role
	* @date Aug 20, 2018
	* @author Tai
	* @param roleId
	* @param listIdWapper
	* @return ResponseEntity<Object>
	 */
	@DeleteMapping("/{roleId}/groups")
	public ResponseEntity<Object> removeListRoleFromGroup(@PathVariable("roleId") long roleId,
			@RequestBody ListIdWrapper listIdWapper) {
		List<Long> groupIds = listIdWapper.getIds();
		Integer result = roleGroupService.deleteListGroupFromRole(roleId, groupIds);
		if (result == 0) {
			return new ResponseEntity<>("Group not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("delete successful", HttpStatus.OK);
	}
	/**
	* @summary search group in role
	* @date Aug 20, 2018
	* @author Tai
	* @param roleId
	* @param searchValue
	* @param fieldSearch
	* @return ResponseEntity<Object>
	 */
	@GetMapping("/{roleId}/groups/search")
	public ResponseEntity<Object> findRoleInGroup(@PathVariable(name = "roleId") Long roleId,
			@RequestParam(name = "searchValue") String searchValue,
			@RequestParam(name = "searchField") String fieldSearch) {
		List<Group> groups = searchService.search(EntityName.GROUP, fieldSearch, searchValue,
				RoleQueryCondition.conditionSearchGroupInRole(roleId));

		if (groups.size() == 0) {
			return new ResponseEntity<>(groups, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(groups, HttpStatus.OK);
	}
	
	/**
	 * @summary delete multi role by list role id
	 * @author Thehap Rok
	 * @param listIdWrapper
	 * @return ResponseEntity<Object>
	 */
	@DeleteMapping
	public ResponseEntity<Object> deleteMultiRole(@RequestBody ListIdWrapper listIdWrapper){
		try {
			roleService.deleteMultiRole(listIdWrapper);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("Delete Multi Role Success !!!",HttpStatus.OK);
	}
	
}
