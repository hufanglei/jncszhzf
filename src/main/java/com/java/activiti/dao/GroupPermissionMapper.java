package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.GroupPermissionBean;


public abstract interface GroupPermissionMapper {

	public abstract List<GroupPermissionBean> selectGroupPermissionByClause(Map map);

	public abstract GroupPermissionBean selectGroupPermissionByPk(int id);
	
	public abstract int selectGroupPermissionCountByClause(Map map);

	public abstract int insertGroupPermission(GroupPermissionBean groupPermissionBean);

	public abstract int deleteGroupPermission(int id);
	
	public abstract int deleteGroupPermissionByGroup_Type(String group_type);

	public abstract int updateGroupPermission(GroupPermissionBean groupPermissionBean);

}
