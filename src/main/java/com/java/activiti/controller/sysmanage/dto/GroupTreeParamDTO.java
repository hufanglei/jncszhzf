package com.java.activiti.controller.sysmanage.dto;

/**
 * Created by Administrator on 2017/12/25.
 */
public class GroupTreeParamDTO {
    private String groupTypeId; // 组类型ID
    private String groupPid;//pid。

    public String getGroupTypeId() {
        return groupTypeId;
    }

    public void setGroupTypeId(String groupTypeId) {
        this.groupTypeId = groupTypeId;
    }

    public String getGroupPid() {
        return groupPid;
    }

    public void setGroupPid(String groupPid) {
        this.groupPid = groupPid;
    }
}
