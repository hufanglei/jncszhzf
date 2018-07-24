package com.java.activiti.model;

import java.util.Date;

public class ActEvtLog {

    private Long logNr; // 自增ID
    private String type; // 日志类型
    private String procDefId; // 流程定义 ID
    private String procInstId; // 流程实例 ID
    private String executionId; // 执行实例 ID
    private String taskId; // 节点实例 ID
    private Date timeStamp; // 日志创建时间
    private String userId; // 用户ID
    private String lockOwner; // 锁定者
    private Date lockTime; // 锁定时间
    private Byte isProcessed; // 是否已处理
    private byte[] data; // 日志内容

    public Long getLogNr() {
        return logNr;
    }

    public void setLogNr(Long logNr) {
        this.logNr = logNr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId == null ? null : procDefId.trim();
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId == null ? null : procInstId.trim();
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId == null ? null : executionId.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getLockOwner() {
        return lockOwner;
    }

    public void setLockOwner(String lockOwner) {
        this.lockOwner = lockOwner == null ? null : lockOwner.trim();
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public Byte getIsProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(Byte isProcessed) {
        this.isProcessed = isProcessed;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}