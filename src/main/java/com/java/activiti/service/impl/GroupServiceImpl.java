package com.java.activiti.service.impl;

import com.java.activiti.common.constraint.JncszhzfSysParam;
import com.java.activiti.common.dto.BaseOperDTO;
import com.java.activiti.common.dto.BootstrapTableDTO;
import com.java.activiti.controller.sysmanage.dto.GroupTreeDTO;
import com.java.activiti.controller.sysmanage.dto.GroupTreeParamDTO;
import com.java.activiti.controller.sysmanage.param.GroupExistParam;
import com.java.activiti.controller.sysmanage.param.GroupsTableParam;
import com.java.activiti.dao.GroupDao;
import com.java.activiti.dao.UserDao;
import com.java.activiti.dao.ZdDao;
import com.java.activiti.model.Group;
import com.java.activiti.model.ZdBean;
import com.java.activiti.service.GroupService;
import com.java.activiti.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    @Resource
    GroupDao groupDao;
    @Resource
    UserDao userDao;
    @Resource
    ZdDao zdDao;

    @Override
    public List<GroupTreeDTO> selectGroup2Tree(String userId) {
        Map map = userDao.selectGroupTreeParam(userId);
        GroupTreeParamDTO groupTreeParamDTO = JsonUtil.formatJSON(map, GroupTreeParamDTO.class);
        List<Group> tempGroupList = groupDao.findGroup();

        List<Group> groupList = new ArrayList<>();
        if (JncszhzfSysParam.GroupType.STREET_CENTER.toString().equals(groupTreeParamDTO.getGroupTypeId()) || JncszhzfSysParam.GroupType.BUREAUS
                .toString().equals(groupTreeParamDTO.getGroupTypeId())) {
            for (int i = 0; i < tempGroupList.size(); i++) {
                if (tempGroupList.get(i).getGroupTypeId().equals(groupTreeParamDTO.getGroupTypeId())) {
                    if ((StringUtils.isBlank(tempGroupList.get(i).getGroupPid()) && tempGroupList.get(i).getGroupId().equals(groupTreeParamDTO
                            .getGroupPid()) || (StringUtils.isNotBlank(tempGroupList.get(i).getGroupPid()) && tempGroupList.get(i).getGroupPid()
                            .equals(groupTreeParamDTO.getGroupPid())))) {
                        groupList.add(tempGroupList.get(i));
                    }
                }
            }
        } else {
            groupList.addAll(tempGroupList);
        }

        Map map1 = new HashMap();
        map1.put("dmmc", JncszhzfSysParam.SysZdCodeName.GROUP_NAME);
        map1.put("orderString", JncszhzfSysParam.SortFiled.CODE_NUMBER);
        List<ZdBean> tempZdList = zdDao.selectZdByClause(map1);

        List<ZdBean> zdList = new ArrayList<>();
        if (JncszhzfSysParam.GroupType.STREET_CENTER.toString().equals(groupTreeParamDTO.getGroupTypeId()) || JncszhzfSysParam.GroupType.BUREAUS
                .toString().equals(groupTreeParamDTO.getGroupTypeId())) {
            for (int i = 0; i < tempZdList.size(); i++) {
                if (tempZdList.get(i).getDmbh().equals(groupTreeParamDTO.getGroupTypeId())) {
                    zdList.add(tempZdList.get(i));
                }
            }
        } else {
            zdList.addAll(tempZdList);
        }

        List<GroupTreeDTO> tempGroupTree = new ArrayList<>();
        for (int i = 0; i < zdList.size(); i++) {
            int count = 0;
            for (int j = 0; j < groupList.size(); j++) {
                if (groupList.get(j).getGroupTypeId().equals(zdList.get(i).getDmbh())) {
                    count++;
                }
            }
            GroupTreeDTO tempObj = new GroupTreeDTO(String.valueOf(i + 1), "0", null, zdList.get(i).getDmms(), zdList.get(i).getDmbh(), null, count);
            tempGroupTree.add(tempObj);
        }

        List<GroupTreeDTO> result = new ArrayList<>();
        for (int i = 0; i < tempGroupTree.size(); i++) {
            String pid = tempGroupTree.get(i).getId();
            if (tempGroupTree.get(i).getTempCount() == 0) {
                result.add(tempGroupTree.get(i));
            }
            if (tempGroupTree.get(i).getTempCount() == 1) {
                GroupTreeDTO tempGroupTreeDTO = tempGroupTree.get(i);
                for (int j = 0; j < groupList.size(); j++) {
                    if (groupList.get(j).getGroupTypeId().equals(tempGroupTree.get(i).getGroupType())) {
                        tempGroupTreeDTO.setGroupId(groupList.get(j).getGroupId());
                        tempGroupTreeDTO.setGroupType(groupList.get(j).getGroupTypeId());
                        tempGroupTreeDTO.setRoleId(groupList.get(j).getGroupTag());
                    }
                }
                result.add(tempGroupTreeDTO);
            } else if (tempGroupTree.get(i).getTempCount() > 1) {  //有多种角色的分组
                result.add(tempGroupTree.get(i));
                for (int j = 0; j < groupList.size(); j++) {
                    if (groupList.get(j).getGroupTypeId().equals(tempGroupTree.get(i).getGroupType())) {
                        GroupTreeDTO tempGroupTreeDTO = new GroupTreeDTO();
                        if (StringUtils.isBlank(groupList.get(j).getGroupPid())) {
                            tempGroupTreeDTO.setPid(pid);
                        } else {
                            tempGroupTreeDTO.setPid(groupList.get(j).getGroupPid());
                        }
                        tempGroupTreeDTO.setId(groupList.get(j).getGroupId());
                        tempGroupTreeDTO.setGroupId(groupList.get(j).getGroupId());
                        tempGroupTreeDTO.setGroupName(groupList.get(j).getGroupName());
                        tempGroupTreeDTO.setGroupType(groupList.get(j).getGroupTypeId());
                        tempGroupTreeDTO.setRoleId(groupList.get(j).getGroupTag());
                        result.add(tempGroupTreeDTO);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<Group> findGroup() {
        return groupDao.findGroup();
    }

    @Override
    public BootstrapTableDTO selectGroupsTable(GroupsTableParam groupsTableParam) {
        Map map = new HashMap();
        if (StringUtils.isNotBlank(groupsTableParam.getGroupTag())) {
            map.put("groupTag", groupsTableParam.getGroupTag());
        }
        map.put("pageIndex", groupsTableParam.getPaginationParam().getPage() * groupsTableParam.getPaginationParam().getPageSize());
        map.put("pageSize", groupsTableParam.getPaginationParam().getPageSize());
        // 取得总页数
        int userCount = groupDao.groupCount(map);
        List<Map> list = groupDao.groupPage(map);
        return new BootstrapTableDTO(userCount, list);
    }

    @Override
    public List<Group> selectGroupsFirstLevel(String groupTypeId) {
        return groupDao.selectGroupsFirstLevel(groupTypeId);
    }

    @Override
    public BaseOperDTO selectGroupExists(GroupExistParam groupExistParam) {
        if (StringUtils.isBlank(groupExistParam.getGroupId()) && StringUtils.isBlank(groupExistParam.getGroupName())) {
            return new BaseOperDTO(false);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(groupExistParam.getGroupId())) {
            map.put("groupId", groupExistParam.getGroupId());
        }
        if (StringUtils.isNotBlank(groupExistParam.getGroupName())) {
            map.put("groupName", groupExistParam.getGroupName());
        }
        List<Group> groupList = groupDao.checkGroup(map);
        if (groupList.size() > 0) {
            return new BaseOperDTO(true);
        }
        return new BaseOperDTO(false);
    }

    @Override
    public BaseOperDTO addGroup(Group group) {
        if (JncszhzfSysParam.TOTAL_STATUS.toString().equals(group.getGroupPid())) {
            group.setGroupPid(null);
        }
        int rows = groupDao.addGroup(group);
        if (rows > 0) {
            return new BaseOperDTO(true);
        }
        return new BaseOperDTO(false);
    }

    @Override
    public BaseOperDTO updateGroup(Group group) {
        int rows = groupDao.updateGroup(group);
        if (rows > 0) {
            return new BaseOperDTO(true);
        }
        return new BaseOperDTO(false);
    }

    @Override
    public BaseOperDTO deleteGroup(String ids) {
        List<String> list = new ArrayList<String>();
        String[] groupIds = ids.split(",");
        for (String groupId : groupIds) {
            list.add(groupId);
        }
        int rows = groupDao.deleteGroup(list);
        if (rows > 0) {
            return new BaseOperDTO(true);
        }
        return new BaseOperDTO(false);
    }
    @Override
    public List<Group> findByUserId(String id) {
        return groupDao.findByUserId(id);
    }


    public List<Group> findGroupByType(String typeId) {
        // TODO Auto-generated method stub
        return groupDao.findGroupByType(typeId);
    }
    public List<Group> findLianbanGroupByType() {
        // TODO Auto-generated method stub
        return groupDao.findLianbanGroupByType();
    }
    @Override
    public List<Group> findByType(Map map) {
        // TODO Auto-generated method stub
        return groupDao.findByType(map);
    }


    /**
     * 归档查询组织机构树
     * @param userId
     * @return
     */
    @Override
    public List<GroupTreeDTO> archiveOfGroup(String userId) {
        List<GroupTreeDTO> result = new ArrayList<>();

        //查询当前用户所属组织机构
        Group group = groupDao.findByUserPk(userId);
        group = this.getTopGroupInfo(group);

        if("2".equals(group.getGroupTypeId().trim())){//属于街道，查询街道二级树
            result = this.getArchiveOfGroup(group.getGroupId());
//        }else if("1".equals(group.getGroupTypeId().trim())
//                ||"6".equals(group.getGroupTypeId().trim())){ //属于委办局或管理员，查询街道和委办局二级树
//            result = this.getArchiveOfGroup();
        }else{//其他人员，查询街道和委办局二级树
            result = this.getArchiveOfGroup();
        }

        return result;
    }



    /**
     * 获取归档查询数信息
     * @return
     */
    private List<GroupTreeDTO> getArchiveOfGroup() {
        return this.getArchiveOfGroup(null);
    }

    /**
     * 获取归档查询数信息
     * @param groupId
     * @return
     */
    private List<GroupTreeDTO> getArchiveOfGroup(String groupId) {
        List<GroupTreeDTO> list = new ArrayList<>();
        List<ZdBean> zdList;
        List<Group> groupList;
        Map zdMap = new HashMap();
        Map groupMap = new HashMap();
        List dmbhs = new ArrayList();
        //查询第一级树列表
        if (groupId == null) {
            dmbhs.add("6");
        }else{
            groupMap.put("groupId", groupId);
        }
        dmbhs.add("2");
        zdMap.put("dmmc", "groupname");
        zdMap.put("dmbhs", dmbhs);
        zdList = zdDao.selectZdByMcandBh(zdMap);

        for (int i = 0; i < zdList.size(); i++) {
            GroupTreeDTO tempObj = new GroupTreeDTO(zdList.get(i).getDmbh(), "0", zdList.get(i).getDmbh(), zdList.get(i).getDmms(), zdList.get(i).getDmbh(), null, zdList.size());
            list.add(tempObj);
        }

        //查询第二级树列表
        if (zdList != null && zdList.size() > 0) {
            groupList = groupDao.selectArchiveOfGroup(groupMap);
            if (groupList != null && groupList.size() > 0) {
                for (ZdBean bean : zdList) {
                    String pid = bean.getDmbh() + "";
                    for (int j = 0; j < groupList.size(); j++) {
                        if (groupList.get(j).getGroupTypeId().equals(bean.getDmbh())) {
                            GroupTreeDTO tempGroupTreeDTO = new GroupTreeDTO();
                            tempGroupTreeDTO.setPid(pid);
                            tempGroupTreeDTO.setId(groupList.get(j).getGroupId());
                            tempGroupTreeDTO.setGroupId(groupList.get(j).getGroupId());
                            tempGroupTreeDTO.setGroupName(groupList.get(j).getGroupName());
                            tempGroupTreeDTO.setGroupType(groupList.get(j).getGroupTypeId());
                            tempGroupTreeDTO.setRoleId(groupList.get(j).getGroupTag());
                            list.add(tempGroupTreeDTO);
                        }
                    }
                }
            }
        }

        return list;
    }


    /**
     * 递归获取第一级组织机构
     * @param group
     * @return
     */
    @Override
    public Group getTopGroupInfo(Group group){
        if(group.getGroupPid()==null || group.getGroupPid().isEmpty()){
           return group;
        }
        Map map = new HashMap();
        map.put("groupId",group.getGroupPid());
        Group group2 = groupDao.findGroupByGroupPk(map);
        return getTopGroupInfo(group2);
    }


    @Override
    public String getGroupMenuByUserId(String userId) {
       return groupDao.getGroupMenuByUserId(userId);
    }

    @Override
    public Group findGroupByGroupPk(Map map) {
        return groupDao.findGroupByGroupPk(map);
    }

    @Override
    public String getTypeById(String id) {
        return groupDao.getTypeById(id);
    }

    @Override
    public List<Group> findLianbanListByType() {
        return groupDao.findLianbanListByType();
    }

    @Override
    public String getGroupMenuByUserName(String id) {
        return groupDao.getGroupMenuByUserName(id);
    }


}
