package com.java.activiti.common.param;

/**
 * Created by Administrator on 2017/12/20.
 */
public class PaginationParam {

    /**
     * 页码（从0开始）
     */
    private Integer page = 0;

    /**
     * 页容量
     */
    private Integer pageSize = 10;

    public PaginationParam() {
    }

    public PaginationParam(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        if (page == null || page < 0) page = 0;
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize == 0 || pageSize < 0) pageSize = 10;
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
