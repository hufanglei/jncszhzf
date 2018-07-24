package com.java.activiti.common.dto;

/**
 * Created by hoipang on 2017/12/21.
 *
 * 增删改操作结果    true为成功，false为失败
 */
public class BaseOperDTO {
    private Boolean oper;

    public BaseOperDTO() {
    }

    public BaseOperDTO(Boolean oper) {
        this.oper = oper;
    }

    public Boolean getOper() {
        return oper;
    }

    public void setOper(Boolean oper) {
        this.oper = oper;
    }
}
