package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.UserGroupBean;


public abstract interface UserGroupMapper {

	public abstract List<UserGroupBean> selectUserGroupByClause(Map map);

//	public abstract UserGroupBean selectUserGroupByPk(int id);
	
	public abstract int selectUserGroupCountByClause(Map map);

	public abstract int insertUserGroup(UserGroupBean userGroupBean);

//	public abstract int deleteUserGroup(int id);
	
	public abstract int deleteUserGroupByUserID(String user_id);

	public abstract int updateUserGroup(UserGroupBean userGroupBean);

}
