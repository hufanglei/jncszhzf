package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.Group;

public interface GroupDao {

	/**
	 * 查询所有角色填充下拉框
	 * @return
	 */
	public List<Group> findGroup();


	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	List groupPage(Map<String,Object> map);
	/**
	 * 统计数量
	 * @param map
	 * @return
	 */
	public int groupCount(Map<String,Object> map);

	/**
	 * 批量删除
	 * @param list
	 * @return
	 */
	int deleteGroup(List<String> list);

	/**
	 * 修改
	 * @param group
	 * @return
	 */
	public int updateGroup(Group group);

	/**
	 * 新增
	 * @param group
	 * @return
	 */
	public int addGroup(Group group);

	public List<Group> findByUserId(String id);

	public Group findByUserPk(String id);

    public List<Group> findGroupByType(String typeId);

    public List<Group> findLianbanGroupByType();

	public List<Group> findByType(Map map);
	/**
	 * 根据条件查询角色
	 * @param map
	 * @return
	 */
	List<Group> checkGroup(Map<String, Object> map);

	List<Group> selectGroupsFirstLevel(String groupTypeId);

	/**
	 * 根据ID获取组织机构信息
	 * @param map
	 * @
	 */
	Group findGroupByGroupPk(Map map);

	/**
	 * 归档查询第二级树列表
	 * @param groupMap
	 * @return
	 */
    List<Group> selectArchiveOfGroup(Map groupMap);

	/**
	 * 查询菜单组id
	 * @param userId
	 * @return
	 */
	String getGroupMenuByUserId(String userId);

	/**
	 * 根据group_pid查询上层组织机构信息
	 * @param map
	 * @return
	 */
    List<Group> selectGroupByGid(Map<String, Object> map);

	/**
	 * 根据group_pid查询基层组织机构信息
	 * @param map
	 * @return
	 */
	List<Group> selectGroupByPid(Map map);
	/**
	 * 根据组id 获取组类型
	 */
	String getTypeById(String id);

	List<Group> findLianbanListByType();

	String getGroupMenuByUserName(String id);
}
