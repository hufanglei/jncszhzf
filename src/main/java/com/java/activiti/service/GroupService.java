package com.java.activiti.service;

import com.java.activiti.common.dto.BaseOperDTO;
import com.java.activiti.common.dto.BootstrapTableDTO;
import com.java.activiti.controller.sysmanage.dto.GroupTreeDTO;
import com.java.activiti.controller.sysmanage.param.GroupExistParam;
import com.java.activiti.controller.sysmanage.param.GroupsTableParam;
import com.java.activiti.model.Group;

import java.util.List;
import java.util.Map;

public interface GroupService {

	/**
	 * 转换所有用户组为导航树
	 * @param userId 用户ID
	 * @return
	 */
	List<GroupTreeDTO> selectGroup2Tree(String userId);

	/**
	 * 按组类型查询所有用户组
	 *
	 * @return
	 */
	List<Group> findGroup();

	/**
	 * 根据条件查询用户组列表数据
	 * @param groupsTableParam 用户组bootstrap-table参数
	 * @return
	 */
	BootstrapTableDTO selectGroupsTable(GroupsTableParam groupsTableParam);

	/**
	 * 根据字典表中用户组编号查询一级分组
	 * @param groupTypeId 用户组类型Id
	 * @return 一级分组列表
	 */
	List<Group> selectGroupsFirstLevel(String groupTypeId);

	/**
	 * 用户组存在校验
	 *
	 * @param groupExistParam 查询参数
	 * @return 布尔值，已经存在返回TRUE，不存在则返回FALSE
	 */
	BaseOperDTO selectGroupExists(GroupExistParam groupExistParam);

	/**
	 * 添加
	 *
	 * @param group 用户组信息
	 * @return
	 */
	BaseOperDTO addGroup(Group group);

	/**
	 * 修改
	 *
	 * @param group 用户组信息
	 * @return
	 */
	BaseOperDTO updateGroup(Group group);

	/**
	 * 批量删除
	 *
	 * @param ids 用户组IDs
	 * @return
	 */
	BaseOperDTO deleteGroup(String ids);

	List<Group> findByUserId(String id);
	
    List<Group> findGroupByType(String typeId);
	public List<Group> findLianbanGroupByType();

	List<Group> findByType(Map map);

	/**
	 * 归档查询组织机构树
	 * @param userId
	 * @return
	 */
	List<GroupTreeDTO> archiveOfGroup(String userId);
	/**
	 * 递归获取第一级组织机构
	 * @param group
	 * @return
	 */
	Group getTopGroupInfo(Group group);

	/**
	 * 查询菜单组id
	 * @param userId
	 * @return
	 */
	String getGroupMenuByUserId(String userId);


	/**
	 * 根据ID获取组织机构信息
	 * @param map
	 * @
	 */
	Group findGroupByGroupPk(Map map);

	/**
	 * 根据组id 获取组类型
	 */
	String getTypeById(String id);

	/**
	 * 联办查询
	 * @return
	 */
    List<Group> findLianbanListByType();

	/**
	 *查询菜单组name
	 * @param id
	 * @return
	 */
	String getGroupMenuByUserName(String id);
}
