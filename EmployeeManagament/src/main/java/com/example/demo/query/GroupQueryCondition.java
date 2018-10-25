package com.example.demo.query;

public class GroupQueryCondition {
	/**
	 * @summary return string condition search user in group
	 * @param groupId
	 * @return String
	 */
	public static String conditionSearchUserInGroup(Long groupId) {
		StringBuilder builder = new StringBuilder(
				" id in (select distinct ug.user.id from UserGroup ug where ug.group.id=");
		builder.append(groupId);
		builder.append(")");
		return builder.toString();
	}
	
	/**
	 * @summary return string condition search role in group
	 * @param groupId
	 * @return String
	 */
	public static String conditionSearchRoleInGroup(Long groupId) {
		StringBuilder builder = new StringBuilder(
				" id in (select distinct gr.role.id from GroupRole gr where gr.group.id=");
		builder.append(groupId);
		builder.append(")");
		return builder.toString();
	}
	

}
