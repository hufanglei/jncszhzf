package com.java.activiti.service;

import com.java.activiti.common.dto.BaseOperDTO;
import com.java.activiti.common.dto.BootstrapTableDTO;
import com.java.activiti.controller.login.dto.LoginDTO;
import com.java.activiti.controller.sysmanage.param.UsersTableParam;
import com.java.activiti.model.MemberShip;
import com.java.activiti.model.User;
import com.java.activiti.model.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 根据条件查询用户列表数据
     *
     * @param usersTableParam 用户列表 bootstrap-table参数
     * @return
     */
    BootstrapTableDTO selectUsersTable(UsersTableParam usersTableParam);

    /**
     * 用户存在校验
     *
     * @param userId 用户Id
     * @return 布尔值，已经存在返回TRUE，不存在则返回FALSE
     */
    BaseOperDTO selectUserExists(String userId);

    /**
     * 新增用户
     *
     * @param memberShip 用户关系
     * @return
     */
    BaseOperDTO addUser(MemberShip memberShip);

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    BaseOperDTO updateUser(User user);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    BaseOperDTO deleteUser(String userId);

    /**
     * 重置密码
     * @param userId 用户Id
     * @return
     */
    BaseOperDTO resetPwd(String userId);

    public User findById(String userId);

    /**
     * 登入
     *
     * @return
     */
    public User userLogin(User user);

    public List<User> selectUserByClause(Map<String, Object> map);

    /**
     * 批量删除用户
     *
     * @param id
     * @return
     */
    public int deleteUser(List<String> id);

    List<UserDTO> userDTOPage(Map<String, Object> map);

    LoginDTO selectLoginDTO(Map<String, Object> map);

    Map selectRoleId(String userId);

    /**
     * 查询当前menbership下网格员信息
     *
     * @param map
     * @return
     */
    List<User> selectUserByMenberShip(Map map);

    /**
     * 查询组织机构下的网格员
     * @param groupId
     * @return
     */
    List<User> selectUserByMenberShipId(String groupId);
}
