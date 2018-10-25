package com.example.demo.api;

import java.util.List;
import java.util.Optional;

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
import com.example.demo.entity.User;
import com.example.demo.entity.UserGroup;
import com.example.demo.entity.dto.EmailDTO;
import com.example.demo.exception.GroupNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.query.GroupQueryCondition;
import com.example.demo.response.UserResponse;
import com.example.demo.service.GroupService;
import com.example.demo.service.RoleGroupService;
import com.example.demo.service.RoleService;
import com.example.demo.service.SearchService;
import com.example.demo.service.UserService;
import com.example.demo.util.EntityName;
import com.example.demo.wrapper.ListIdWrapper;

@RestController
@RequestMapping("/api/groups")
public class GroupApiController {

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private RoleGroupService roleGroupService;

	@Autowired
	private SearchService searchService;

	@Autowired
	private RoleService roleService;

	/**
	 * @summary api get all user of group base on group id
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @return ResponseEntity<Object>
	 */
	@GetMapping(path = "/{groupId}/users")
	public ResponseEntity<Object> getListUserInGroup(@PathVariable(name = "groupId") Long groupId) {
		List<UserResponse> listUser = userService.getAllUserOfGroup(groupId);
		if (listUser.size() == 0) {
			return new ResponseEntity<>(listUser, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listUser, HttpStatus.OK);
	}

	/**
	 * @summary view list all group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @return ResponseEntity<Object>
	 */
	@GetMapping
	public ResponseEntity<Object> getAllGroup() {
		List<Group> listGroup = groupService.viewAllGroup();
		if (listGroup.size() == 0) {
			return new ResponseEntity<>(listGroup, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listGroup, HttpStatus.OK);
	}

	/**
	 * @summary search role in group
	 * @date Aug 20, 2018
	 * @author Tai
	 * @param searchValue
	 * @param fieldSearch
	 * @return ResponseEntity<Object>
	 */
	@GetMapping(path = "/search")
	public ResponseEntity<Object> findRoleInGroup(@RequestParam(name = "searchValue") String searchValue,
			@RequestParam(name = "searchField") String fieldSearch) {
		List<Group> groups = searchService.search(EntityName.GROUP, fieldSearch, searchValue);

		if (groups.size() == 0) {
			return new ResponseEntity<>(groups, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(groups, HttpStatus.OK);
	}

	/**
	 * 
	 * @summary get information of group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param id
	 * @return ResponseEntity<Object>
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> getGroup(@PathVariable("id") long id) {
		Optional<Group> group = groupService.viewGroup(id);
		if (!group.isPresent()) {
			return new ResponseEntity<>(group, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(group, HttpStatus.OK);
	}

	/**
	 * 
	 * @summary create a group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param group
	 * @return ResponseEntity<Object>
	 */
	@PostMapping
	public ResponseEntity<Object> createGroup(@Valid @RequestBody Group group, BindingResult result) {
		if (result.hasErrors()) {
			String fieldName = result.getFieldError().getField();
			String message = result.getFieldError().getDefaultMessage();
			return new ResponseEntity<>(fieldName + " : " + message, HttpStatus.BAD_REQUEST);
		}
		groupService.addGroup(group);
		return new ResponseEntity<>("Create group successful", HttpStatus.CREATED);
	}

	/**
	 * @summary update a group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param group
	 * @param id
	 * @return ResponseEntity<Object>
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateGroup(@PathVariable("id") long id, @Valid @RequestBody Group group,
			BindingResult result) {
		if (result.hasErrors()) {
			String fieldName = result.getFieldError().getField();
			String message = result.getFieldError().getDefaultMessage();
			return new ResponseEntity<>(fieldName + " : " + message, HttpStatus.BAD_REQUEST);
		}
		Optional<Group> groupOptional = groupService.viewGroup(id);
		if (!groupOptional.isPresent()) {
			return new ResponseEntity<>("Group Not Found id: " + id, HttpStatus.NOT_FOUND);
		}
		group.setCreatedAt(groupOptional.get().getCreatedAt());
		group.setUserGroups(groupOptional.get().getUserGroups());
		group.setGroupRoles(groupOptional.get().getGroupRoles());
		group.setId(id);
		groupService.saveGroup(group);
		return new ResponseEntity<>("Update group successful", HttpStatus.OK);

	}

	/**
	 * 
	 * @summary delete list group
	 * @date Aug 30, 2018
	 * @author Tai
	 * @param listIdWapper
	 * @return
	 * @return ResponseEntity<Object>
	 */
	@DeleteMapping
	public ResponseEntity<Object> deleteListGroup(@RequestBody ListIdWrapper listIdWapper) {
		List<Long> groupIdList = listIdWapper.getIds();
		Integer result = groupService.deleteListGroup(groupIdList);
		if (result == 0) {
			return new ResponseEntity<>("Not list", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Deleted list group successful", HttpStatus.OK);
	}

	/**
	 * @summary delete a group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param id
	 * @return ResponseEntity<Object>
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteGroup(@PathVariable("id") long id) {
		
		Optional<Group> deletedGroup = groupService.deleteGroup(id);
		if(deletedGroup.get().getId() == null) {
			return new ResponseEntity<Object>("Delete All user to delete group!!!", HttpStatus.BAD_REQUEST);
		}
		if (!deletedGroup.isPresent()) {
			return new ResponseEntity<Object>("Group Not Found id: " + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Dedelte group successful", HttpStatus.OK);
	}

	/**
	 * @summary list all roles in a group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param id
	 * @return ResponseEntity<Object>
	 */
	@GetMapping("/{id}/roles")
	public ResponseEntity<Object> viewRolesOfGroup(@PathVariable("id") long id) {
		if (roleGroupService.existsByGroup(id)) {
			return new ResponseEntity<>("Group Not Found id: " + id, HttpStatus.NOT_FOUND);
		}
		List<GroupRole> list = roleGroupService.findAllRoleByGroup(id);
		List<Role> listRole = roleGroupService.convertGroupRoleToRole(list);
		if (list.isEmpty()) {
			return new ResponseEntity<>("no role", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listRole, HttpStatus.OK);
	}

	/**
	 * @summary add role in group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return ResponseEntity<Object>
	 */
	@PostMapping("/{groupId}/roles/{roleId}")
	public ResponseEntity<Object> creadRoleGroup(@PathVariable("groupId") long groupId,
			@PathVariable("roleId") long roleId) {
		GroupRole groupRole = roleGroupService.addRoleToGroup(groupId, roleId);
		if (groupRole == null) {
			return new ResponseEntity<>("role da co trong group", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("add role successful", HttpStatus.CREATED);
	}

	/**
	 * 
	 * @summary find Role name NotIn Group By Name
	 * @date Sep 5, 2018
	 * @author Tai
	 * @param groupId
	 * @param searchParam
	 * @return
	 * @return ResponseEntity<Object>
	 */
	@GetMapping(path = "/{groupId}/roles/search-not-in")
	public ResponseEntity<Object> findRoleNotInGroupByName(@PathVariable("groupId") Long groupId,
			@RequestParam("searchParam") String searchParam) {
		List<Role> roles = roleService.getNameRoleAndGroupid(searchParam, groupId);
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

	/**
	 * @summary api add new user to group base on user id and group id
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return ResponseEntity<Object>
	 */
	@PostMapping(path = "/{groupId}/users/{userId}")
	public ResponseEntity<Object> addUserToGroup(@PathVariable(name = "groupId") Long groupId,
			@PathVariable(name = "userId") Long userId) {
		try {
			UserGroup userGroup = groupService.addNewUserToGroup(groupId, userId);
			if (userGroup == null) {
				return new ResponseEntity<>("user not in group", HttpStatus.NOT_FOUND);
			}
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (GroupNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Add user successful", HttpStatus.CREATED);
	}

	/**
	 * @summary Api delete role form group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return ResponseEntity<Object>
	 */
	@DeleteMapping("/{groupId}/roles/{roleId}")
	public ResponseEntity<Object> removeRoleFromGroup(@PathVariable("groupId") long groupId,
			@PathVariable("roleId") long roleId) {
		boolean deleted = roleGroupService.deleteRoleFormGroup(groupId, roleId);
		if (!deleted) {
			return new ResponseEntity<>("Group not found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("delete user successful", HttpStatus.OK);
	}

	/**
	 * @summary delete All Role in Group
	 * @date Aug 17, 2018
	 * @author Tai
	 * @param groupId
	 * @return ResponseEntity<Object>
	 */
	@DeleteMapping("/{groupId}/roles")
	public ResponseEntity<Object> removeListRoleFromGroup(@PathVariable("groupId") long groupId,
			@RequestBody ListIdWrapper listIdWapper) {
		List<Long> roleIds = listIdWapper.getIds();
		Integer result = roleGroupService.deleteListRoleFromGroup(groupId, roleIds);
		if (result == 0) {
			return new ResponseEntity<>("Group not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("delete successful", HttpStatus.OK);

	}

	/**
	 * @summary find Role In Group
	 * @date Aug 17, 2018
	 * @author Tai
	 * @param groupId
	 * @param searchValue
	 * @param fieldSearch
	 * @return ResponseEntity<Object>
	 */
	@GetMapping(path = "/{groupId}/roles/search")
	public ResponseEntity<Object> findRoleInGroup(@PathVariable(name = "groupId") Long groupId,
			@RequestParam(name = "searchValue") String searchValue,
			@RequestParam(name = "searchField") String fieldSearch) {
		List<Role> roles = searchService.search(EntityName.ROLE, fieldSearch, searchValue,
				GroupQueryCondition.conditionSearchRoleInGroup(groupId));
		if (roles.size() == 0) {
			return new ResponseEntity<>(roles, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

	/**
	 * @summary api remove many user in group
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param listIdWapper
	 * @return ResponseEntity<Object>
	 */
	@DeleteMapping(path = "/{groupId}/users")
	public ResponseEntity<Object> removeListUserFromGroup(@RequestBody ListIdWrapper listIdWapper,
			@PathVariable("groupId") Long groupId) {
		try {
			List<Long> userIds = listIdWapper.getIds();
			groupService.removeListUseFromGroup(groupId, userIds);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (GroupNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Remove user successful", HttpStatus.OK);
	}

	/**
	 * @summary api remove one user in group
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return ResponseEntity<Object>
	 */
	@DeleteMapping(path = "/{groupId}/users/{userId}")
	public ResponseEntity<Object> removeUserFromGroup(@PathVariable("groupId") Long groupId,
			@PathVariable("userId") Long userId) {
		try {
			groupService.removeUserFromGroup(groupId, userId);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (GroupNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Delete User Successfull", HttpStatus.OK);
	}

	/**
	 * @summary api find user not in group by fullname of email
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param searchParam
	 * @return ResponseEntity<Object>
	 */
	@GetMapping(path = "/{groupId}/users/search-not-in")
	public ResponseEntity<Object> findUserNotInGroupByNameOrEmail(@PathVariable("groupId") Long groupId,
			@RequestParam("searchParam") String searchParam) {
		List<UserResponse> users = groupService.findUserNotInGroupByNameOrEmail(groupId, searchParam);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/**
	 * @summary api search user in group base on any field
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param searchValue
	 * @param fieldSearch
	 * @return ResponseEntity<Object>
	 */
	@GetMapping(path = "/{groupId}/users/search")
	public ResponseEntity<Object> findUserInGroup(@PathVariable(name = "groupId") Long groupId,
			@RequestParam(name = "searchValue") String searchValue,
			@RequestParam(name = "searchField") String fieldSearch) {
		List<User> users = searchService.search(EntityName.USER, fieldSearch, searchValue,
				GroupQueryCondition.conditionSearchUserInGroup(groupId));
		List<UserResponse> listUser = userService.convertUserToUserResponse(users);
		if (listUser.size() == 0) {
			return new ResponseEntity<>(listUser, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listUser, HttpStatus.OK);
	}

	/**
	 * @summary api get infor of user in group base on group id and user id
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return ResponseEntity<Object>
	 */
	@GetMapping(path = "/{groupId}/users/{userId}")
	public ResponseEntity<Object> getInforUserInGroup(@PathVariable(name = "groupId") Long groupId,
			@PathVariable(name = "userId") Long userId) {
		UserResponse user = null;
		try {
			user = groupService.getInforOfUser(groupId, userId);
			if (user == null) {
				return new ResponseEntity<>("User not in group!!!", HttpStatus.NOT_FOUND);
			}
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (GroupNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/{groupId}/users")
	public ResponseEntity<Object> addUserToGroupByEmail(@PathVariable(name = "groupId") Long groupId,
			@Valid @RequestBody EmailDTO email, BindingResult result) {
		if (result.hasErrors()) {
			String error = result.getFieldError().getDefaultMessage();
			String fieldError = result.getFieldError().getField();
			System.out.println(fieldError + ": " + error);
			return new ResponseEntity<>(fieldError + ": " + error, HttpStatus.BAD_REQUEST);
		}
		try {
			groupService.addUserToGroupByEmail(groupId, email.getEmail());
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (GroupNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>("Add User To Group Success!!!", HttpStatus.OK);
	}

}