package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.TQwgWbjBean;


public abstract interface TQwgWbjMapper {

	public abstract List<TQwgWbjBean> selectTQwgWbjByClause(Map map);

	public abstract TQwgWbjBean selectTQwgWbjByPk(int wbjid);
	
	public abstract int selectTQwgWbjCountByClause(Map map);

	public abstract int insertTQwgWbj(TQwgWbjBean tQwgWbjBean);

	public abstract int deleteTQwgWbj(int wbjid);

	public abstract int updateTQwgWbj(TQwgWbjBean tQwgWbjBean);

}
