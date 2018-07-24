package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.PowerListBean;


public abstract interface PowerListMapper {

	public abstract List<PowerListBean> selectPowerListByClause(Map map);

	/**
	 * 查询数据字典表数据 分页展示
	 * @param map
	 * @return
	 */
	public abstract List<PowerListBean> selectPowerByClause(Map map);

	public abstract PowerListBean selectPowerListByPk(int id);

	public abstract int selectPowerListCountByClause(Map map);

	/**
	 * 数据字典表数据 新增保存
	 * @param powerListBean
	 * @return
	 */
	public abstract int insertPowerList(PowerListBean powerListBean);

	public abstract int deletePowerList(int id);

	/**
	 * 数据字典表数据  更新保存
	 * @param powerListBean
	 * @return
	 */
	public abstract int updatePowerList(PowerListBean powerListBean);

	/**
	 * 数据字典表数据 删除数据
	 * @param id
	 * @return
	 */
	int deletePower(String id);

	/**
	 * 数据字典表数据 查询所属部门下拉框
	 * @return
	 */
    List<PowerListBean> selectSsWeibanju();
}
