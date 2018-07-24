package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.ActIdGroupBean;


public abstract interface ActIdGroupMapper {

	public abstract List<ActIdGroupBean> selectActIdGroupByClause(Map map);

	public abstract ActIdGroupBean selectActIdGroupByPk(int id);
	
	public abstract int selectActIdGroupCountByClause(Map map);

	public abstract int insertActIdGroup(ActIdGroupBean actIdGroupBean);

	public abstract int deleteActIdGroup(int id);

	public abstract int updateActIdGroup(ActIdGroupBean actIdGroupBean);

    List<ActIdGroupBean> listGroupInfo(Map map);

	List<ActIdGroupBean> listGroupInfoMenu(Map map);

	List<ActIdGroupBean> listGroupMenuInfo(Map map);

	List<ActIdGroupBean> listGroupMenu(Map map);
}
