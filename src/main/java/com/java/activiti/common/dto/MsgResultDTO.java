package com.java.activiti.common.dto;

/**
 * Created by hoipang on 2017/9/18.
 *
 * 结果信息DTO
 */
public class MsgResultDTO {

    /**
     * 结果信息
     */
    private String message;

    public MsgResultDTO() {
    }

    public MsgResultDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
