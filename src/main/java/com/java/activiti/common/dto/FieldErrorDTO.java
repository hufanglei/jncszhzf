package com.java.activiti.common.dto;

/**
 * Created by hoipang on 2017/9/21.
 *
 * 字段异常信息DTO
 */
public class FieldErrorDTO extends MsgResultDTO {

    /**
     * 异常字段名
     */
    private String field;

    public FieldErrorDTO() {
    }

    public FieldErrorDTO(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
