package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.QhzdBean;


public interface QhzdDao {

	public List<QhzdBean> selectQhzdByClause(Map<String, Object> map);

	public List<QhzdBean> selectQhzdTreeDataByOrgPrefix(Map<String, Object> map);

	public QhzdBean selectQhzdByPk(int id);
	
	public int selectQhzdCountByClause(Map<String, Object> map);

	public int insertQhzd(QhzdBean qhzdBean);

	public int deleteQhzd(int id);

	public int updateQhzd(QhzdBean qhzdBean);

}
