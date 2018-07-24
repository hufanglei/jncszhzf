package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.PermissionBean;


public interface PermissionMapper {

	List<Integer> selectPermissionIdsByGroupTag(Map map);

	List<PermissionBean> selectPermissionByClause(Map map);

	int selectPermissionCountByClause(Map map);
	
	List<PermissionBean> selectPermissionByGroupTags(Map map);

	public abstract PermissionBean selectPermissionByPk(int id);

	public abstract int insertPermission(PermissionBean permissionBean);

	public abstract int deletePermission(int id);

	public abstract int updatePermission(PermissionBean permissionBean);
}
