package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.TQwgDataBean;
import com.java.activiti.model.TQwgStatusBean;


public abstract interface TQwgStatusMapper {

	public abstract List<TQwgStatusBean> selectTQwgStatusByClause(Map map);

	public abstract TQwgStatusBean selectTQwgStatusByPk(int orderno);
	
	public abstract int selectTQwgStatusCountByClause(Map map);

	public abstract int insertTQwgStatus(TQwgStatusBean tQwgStatusBean);

	public abstract int deleteTQwgStatus(int orderno);

	public abstract int updateTQwgStatus(TQwgStatusBean tQwgStatusBean);

	List<TQwgStatusBean> selectTQwgStatusByOrderNo(Map map);
}
