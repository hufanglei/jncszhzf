package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.WorkdayBean;

public abstract interface WorkdayDao {

	public abstract List<String> selectWorkdayByClause(Map<String, Object> map);

	public abstract WorkdayBean selectWorkdayByPk(int id);
	
	public abstract int selectWorkdayCountByClause(Map<String, Object> map);

	public abstract int insertWorkday(WorkdayBean workdayBean);

	public abstract int deleteWorkday(int id);
	
	public abstract int deleteWorkdayByYear(String year);

	public abstract int updateWorkday(WorkdayBean workdayBean);

	int selectWorkdayCountByWorkDay(String temp);
}
