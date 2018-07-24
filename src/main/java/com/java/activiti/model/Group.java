package com.java.activiti.model;

import java.io.Serializable;

/**
 * 角色实体扩展
 * @author user
 *
 */
public class Group implements Serializable {

	private String groupId; // 主键 角色名
	private String groupName; // 名称
	private String groupTag;//标识。不同的角色，其标识可相同。
	private String groupTypeId; // 组类型ID
	private String groupPid;//pid。
	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupTag() {
		return groupTag;
	}
	public void setGroupTag(String groupTag) {
		this.groupTag = groupTag;
	}

	public String getGroupTypeId() {
		return groupTypeId;
	}

	public void setGroupTypeId(String groupTypeId) {
		this.groupTypeId = groupTypeId;
	}

	public String getGroupPid() {
		return groupPid;
	}

	public void setGroupPid(String groupPid) {
		this.groupPid = groupPid;
	}
}
