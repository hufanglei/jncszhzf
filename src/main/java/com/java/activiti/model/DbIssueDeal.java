package com.java.activiti.model;

public class DbIssueDeal {
    private Integer id;

    private String taskId;

    private String dealWay;

    private String processId;

    private String issueNumber;

    private String createTime;

    private String dcdbnum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getDealWay() {
        return dealWay;
    }

    public void setDealWay(String dealWay) {
        this.dealWay = dealWay == null ? null : dealWay.trim();
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber == null ? null : issueNumber.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getDcdbnum() {
        return dcdbnum;
    }

    public void setDcdbnum(String dcdbnum) {
        this.dcdbnum = dcdbnum == null ? null : dcdbnum.trim();
    }
}