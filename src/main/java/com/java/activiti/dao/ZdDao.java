package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.ZdBean;


public interface ZdDao {

	List<ZdBean> selectZdByClause(Map<String, Object> map);

	ZdBean selectZdByPk(int id);
	
	int selectZdCountByClause(Map<String, Object> map);

	int insertZd(ZdBean zdBean);

	int deleteZd(int id);

	int updateZd(ZdBean zdBean);
	
	List<ZdBean> selectZdxxByZdmc(Map<String, Object> map);

	/**
	 * 查询数据字典信息
	 * @param map 数据字典代码名称
	 * @return
	 */
    List<ZdBean> listDictionary(Map map);

	/**
	 * 根据代码名称和编号查询字典信息
	 * @param zdMap
	 * @return
	 */
	List<ZdBean> selectZdByMcandBh(Map zdMap);

	/**
	 * 根据dmmc 查询案件来源字典
	 * @param dmmc
	 * @return
	 */
	List<Map<String,Object>> getDictionary(String dmmc);
}
