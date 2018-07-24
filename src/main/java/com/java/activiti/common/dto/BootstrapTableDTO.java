package com.java.activiti.common.dto;

/**
 * Created by Administrator on 2017/12/21.
 *
 * bootstrap-table 返回数据格式
 */
public class BootstrapTableDTO {
    private Integer total;
    private Object rows;

    public BootstrapTableDTO() {
    }

    public BootstrapTableDTO(Integer total, Object rows) {
        this.total = total;
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }
}
