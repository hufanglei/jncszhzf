package com.java.activiti.controller.sysmanage.param;

import com.java.activiti.common.param.PaginationParam;
import com.java.activiti.common.param.SortParam;

/**
 * Created by Administrator on 2017/12/26.
 */
public class PermissionsTableParam {
    //父Id
    private Integer parentId;
    //排序
    private SortParam sortParam;
    //分页
    private PaginationParam paginationParam;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public SortParam getSortParam() {
        return sortParam;
    }

    public void setSortParam(SortParam sortParam) {
        this.sortParam = sortParam;
    }

    public PaginationParam getPaginationParam() {
        return paginationParam;
    }

    public void setPaginationParam(PaginationParam paginationParam) {
        this.paginationParam = paginationParam;
    }
}
