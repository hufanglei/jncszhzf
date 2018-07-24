package com.java.activiti.controller.sysmanage.dto;

/**
 * Created by Administrator on 2017/12/20.
 */
public class GroupTreeDTO {
    private String id; // id
    private String pid; // 父id
    private String groupId; // 主键 用户组名 - 该字段有值的才是用户组对象，否则为非实体的树目录
    private String groupName; // 名称
    private String groupType; //标识。不同的角色，其标识可相同
    private String roleId; //标识。不同的角色，其标识可相同
    private int tempCount;    // 用户转换标记的临时字段，无实际含义

    public GroupTreeDTO() {
    }

    public GroupTreeDTO(String id, String pid, String groupId, String groupName, String groupType, String roleId, int tempCount) {
        this.id = id;
        this.pid = pid;
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupType = groupType;
        this.roleId = roleId;
        this.tempCount = tempCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public int getTempCount() {
        return tempCount;
    }

    public void setTempCount(int tempCount) {
        this.tempCount = tempCount;
    }
}
