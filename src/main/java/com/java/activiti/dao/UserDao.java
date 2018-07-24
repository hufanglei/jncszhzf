package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.controller.login.dto.LoginDTO;
import com.java.activiti.model.Group;
import com.java.activiti.model.User;
import com.java.activiti.model.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
	int deleteByPrimaryKey(String id);

	User selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(User user);

	/**
	 * 新增用户
	 *
	 * @param user
	 * @return
	 */
	int addUser(User user);

	/**
	 * 根据条件分页查询用户列表数据
	 *
	 * @param map
	 * @return
	 */
	List<User> selectUsersTable(Map<String, Object> map);

	/**
	 * 查询总用户数
	 *
	 * @param map
	 * @return
	 */
	int selectUsersTableCount(Map<String, Object> map);


	/**
	 * 登入
	 * 
	 * @return
	 */
	public User userLogin(User user);
	
	/**
	 * 批量删除用户
	 * @param id
	 * @return
	 */
	public int deleteUser(List<String> id);
	
	public List<User> getUsersByOrgNumber(Map<String, Object> map);
	
	public List<User> selectUserByClause(Map<String, Object> map);

	List<UserDTO> userDTOPage(Map<String, Object> map);

	LoginDTO selectLoginDTO(Map<String, Object> map);

	Map selectRoleId(@Param("userId") String userId);

	Map selectGroupTreeParam(@Param("userId") String userId);

	/**
	 * 查询当前menbership下网格员信息
	 * @param map
	 * @return
	 */
    List<User> selectUserByMenberShip(Map map);

	/**
	 * 查询组织机构下的网格员
	 * @param groups
	 * @return
	 */
    List<User> selectUserByGroupIds(List<Group> groups);
}
