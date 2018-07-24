package com.java.activiti.common.dto;

/**
 * Created by hoipang on 2017/12/15.
 *
 * 响应结果DTO
 */
public class BaseDTO {

    /**
     * 响应Code
     */
    private Integer respCode;

    /**
     * 响应消息
     */
    private String respMsg;

    /**
     * 响应结果
     */
    private Object result;

    public Integer getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
