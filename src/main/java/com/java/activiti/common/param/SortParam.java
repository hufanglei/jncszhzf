package com.java.activiti.common.param;

/**
 * Created by Administrator on 2017/12/20.
 */
public class SortParam {
    public final static String ASC = "asc";
    public final static String DESC = "desc";

    /**
     * 排序字段
     */
    private String sort;

    /**
     * 排序方向
     */
    private String order;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public SortParam() {
    }

    public SortParam(String sort, String order) {
        this.sort = sort;
        this.order = order;
    }
}
