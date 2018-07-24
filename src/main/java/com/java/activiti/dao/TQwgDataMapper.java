package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.TQwgDataBean;


public abstract interface TQwgDataMapper {

	public abstract List<TQwgDataBean> selectTQwgDataByClause(Map map);

	public abstract TQwgDataBean selectTQwgDataByPk(int orderno);
	
	public abstract int selectTQwgDataCountByClause(Map map);

	public abstract int insertTQwgData(TQwgDataBean tQwgDataBean);

	public abstract int insertTQwgDataBatch(List<TQwgDataBean> tQwgDataBean);

	public abstract int deleteTQwgData(int orderno);

	public abstract int updateTQwgData(TQwgDataBean tQwgDataBean);

    List<TQwgDataBean> selectTQwgDataByOrderNo(Map map);
}
