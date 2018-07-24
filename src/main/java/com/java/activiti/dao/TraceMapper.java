package com.java.activiti.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java.activiti.model.TraceBean;


public interface TraceMapper {

	List<TraceBean> selectTraceByClause(Map map);

	TraceBean selectTraceByPk(int id);
	
	int selectTraceCountByClause(Map map);

	int insertTrace(TraceBean traceBean);

	int deleteTrace(int id);

	int updateTrace(TraceBean traceBean);

    List<HashMap<String, Object>> selectTraceByUser(Map map1);
}
