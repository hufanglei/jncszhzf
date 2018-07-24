package com.java.activiti.controller.sysmanage.param;

import com.java.activiti.common.param.PaginationParam;

/**
 * Created by hoipang on 2017/12/21.
 *
 * 用户组bootstrap-table参数
 */
public class GroupsTableParam {
    //角色类型
    private String groupTag;
    //分页
    private PaginationParam paginationParam;

    public String getGroupTag() {
        return groupTag;
    }

    public void setGroupTag(String groupTag) {
        this.groupTag = groupTag;
    }

    public PaginationParam getPaginationParam() {
        return paginationParam;
    }

    public void setPaginationParam(PaginationParam paginationParam) {
        this.paginationParam = paginationParam;
    }
}
