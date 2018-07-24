package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.WeibanjuzdBean;


public abstract interface WeibanjuzdMapper {

	public abstract List<WeibanjuzdBean> selectWeibanjuzdByClause(Map map);
	
	public abstract List<WeibanjuzdBean> selectAllWeibanjuzdByClause(Map map);

	public abstract WeibanjuzdBean selectWeibanjuzdByPk(int id);
	
	public abstract int selectWeibanjuzdCountByClause(Map map);

	public abstract int insertWeibanjuzd(WeibanjuzdBean weibanjuzdBean);

	public abstract int deleteWeibanjuzd(int id);

	public abstract int updateWeibanjuzd(WeibanjuzdBean weibanjuzdBean);
	

}
