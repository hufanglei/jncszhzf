package com.java.activiti.common.enums;

/**
 * Created by Administrator on 2017/12/16.
 */
public enum SysRespEnum {
    SUCCESS(200, "成功"),
    SYSTEM_ERROR(500, "系统错误");

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回结果描述
     */
    private String message;

    SysRespEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
