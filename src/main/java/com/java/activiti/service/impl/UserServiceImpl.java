package com.java.activiti.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.java.activiti.common.constraint.JncszhzfSysParam;
import com.java.activiti.common.constraint.SysMsg;
import com.java.activiti.common.dto.BaseOperDTO;
import com.java.activiti.common.dto.BootstrapTableDTO;
import com.java.activiti.controller.login.dto.LoginDTO;
import com.java.activiti.controller.sysmanage.dto.GroupTreeParamDTO;
import com.java.activiti.controller.sysmanage.param.UsersTableParam;
import com.java.activiti.dao.GroupDao;
import com.java.activiti.dao.MemberShipDao;
import com.java.activiti.dao.UserDao;
import com.java.activiti.model.Group;
import com.java.activiti.model.MemberShip;
import com.java.activiti.model.User;
import com.java.activiti.model.dto.UserDTO;
import com.java.activiti.util.Constants;
import com.java.activiti.util.JsonUtil;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.java.activiti.service.UserService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;
    @Resource
    MemberShipDao memberShipDao;
    @Resource
    GroupDao groupDao;

    @Override
    public BootstrapTableDTO selectUsersTable(UsersTableParam usersTableParam) {
        Map map = userDao.selectGroupTreeParam(usersTableParam.getUserId());
        GroupTreeParamDTO groupTreeParamDTO = JsonUtil.formatJSON(map, GroupTreeParamDTO.class);
        Map<String, Object> userMap = new HashMap<String, Object>();
        if (JncszhzfSysParam.GroupType.STREET_CENTER.toString().equals(groupTreeParamDTO.getGroupTypeId()) || JncszhzfSysParam.GroupType.BUREAUS
                .toString().equals(groupTreeParamDTO.getGroupTypeId())) {
            userMap.put("groupPid", groupTreeParamDTO.getGroupPid());
        }
        if (StringUtils.isNotBlank(usersTableParam.getGroupId())) {
            userMap.put("groupId", usersTableParam.getGroupId());
        }
        // 取得总页数
        int userCount = userDao.selectUsersTableCount(userMap);
        userMap.put("pageIndex", usersTableParam.getPaginationParam().getPage() * usersTableParam.getPaginationParam().getPageSize());
        userMap.put("pageSize", usersTableParam.getPaginationParam().getPageSize());
        List<User> userList = userDao.selectUsersTable(userMap);
        return new BootstrapTableDTO(userCount, userList);
    }

    @Override
    public BaseOperDTO selectUserExists(String userId) {
        if (StringUtils.isBlank(userId)) {
            return new BaseOperDTO(false);
        }
        Map map = new HashMap();
        map.put("userId", userId);
        int count = userDao.selectUsersTableCount(map);
        if (count > 0) {
            return new BaseOperDTO(true);
        }
        return new BaseOperDTO(false);
    }

    @Override
    public BaseOperDTO addUser(MemberShip memberShip) {
        User user = memberShip.getUser();
        try {
            user.setPassword(Constants.getMD5PassWord(user.getPassword()));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(SysMsg.ENCRYPT_ERROR);
        }
        user.setId(user.getId().trim());
        memberShip.setUser(user);
        int userResult = userDao.addUser(user);
        int memeberResult = memberShipDao.addMemberShip(memberShip);
        if (userResult > 0 && memeberResult > 0) {
            return new BaseOperDTO(true);
        }
        return new BaseOperDTO(false);
    }

    @Override
    public BaseOperDTO updateUser(User user) {
/*        if (StringUtils.isNotBlank(user.getPassword())) {
            try {
                user.setPassword(Constants.getMD5PassWord(user.getPassword()));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(SysMsg.ENCRYPT_ERROR);
            }
        }*/
        int rows = userDao.updateByPrimaryKeySelective(user);
        if (rows > 0) {
            return new BaseOperDTO(true);
        }
        return new BaseOperDTO(false);
    }

    @Override
    public BaseOperDTO deleteUser(String userId) {
        int memberShipResult = memberShipDao.deleteMemberShipByUserId(userId.trim());
        int userResult = userDao.deleteByPrimaryKey(userId.trim());
        if (memberShipResult > 0 && userResult > 0) {
            return new BaseOperDTO(true);
        }
        return new BaseOperDTO(false);
    }

    @Override
    public BaseOperDTO resetPwd(String userId) {
        User user = userDao.selectByPrimaryKey(userId);
        try {
            user.setPassword(Constants.getMD5PassWord("000000"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(SysMsg.ENCRYPT_ERROR);
        }
        int rows = userDao.updateByPrimaryKeySelective(user);
        if (rows > 0) {
            return new BaseOperDTO(true);
        }
        return new BaseOperDTO(false);
    }

    public User findById(String userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    /**
     * 登入
     *
     * @return
     */
    public User userLogin(User user) {
        return userDao.userLogin(user);
    }

    /**
     * 批量删除用户
     *
     * @param id
     * @return
     */
    public int deleteUser(List<String> id) {
        return userDao.deleteUser(id);
    }

    @Override
    public List<UserDTO> userDTOPage(Map<String, Object> map) {
        return userDao.userDTOPage(map);
    }

    @Override
    public LoginDTO selectLoginDTO(Map<String, Object> map) {
        return userDao.selectLoginDTO(map);
    }

    @Override
    public Map selectRoleId(String userId) {
        return userDao.selectRoleId(userId);
    }

    @Override
    public List<User> selectUserByClause(Map<String, Object> map) {
        return userDao.selectUserByClause(map);
    }

    /**
     * 查询当前menbership下网格员信息
     * @param map
     * @return
     */
    @Override
    public List<User> selectUserByMenberShip(Map map) {
        return userDao.selectUserByMenberShip(map);
    }

    @Override
    public List<User> selectUserByMenberShipId(String groupId) {
        Map groupMap = new HashMap();
        groupMap.put("pid",groupId);
        List<Group> groups = groupDao.selectGroupByPid(groupMap);

        if(groups!=null && groups.size()>0) {
            List list = new ArrayList();
            for(Group group:groups){
                list.add(group.getGroupId());
            }

            return userDao.selectUserByGroupIds(list);
        }else{
            return null;
        }
    }
}
