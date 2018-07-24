package com.java.activiti.controller.sysmanage.param;

import com.java.activiti.common.param.PaginationParam;

/**
 * Created by hoipang on 2017/12/21.
 *
 * 用户列表bootstrap-table参数
 */
public class UsersTableParam {
    //用户ID
    private String userId;
    //用户组Id
    private String groupId;
    //分页
    private PaginationParam paginationParam;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public PaginationParam getPaginationParam() {
        return paginationParam;
    }

    public void setPaginationParam(PaginationParam paginationParam) {
        this.paginationParam = paginationParam;
    }
}