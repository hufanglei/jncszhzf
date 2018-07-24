package com.java.activiti.model;

import java.util.Date;

public class IssueDeal {
    private Integer id;

    private String actCommentId;

    private String dealWay;

    private String processId;

    private String issueNumber;

    private String  createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActCommentId() {
        return actCommentId;
    }

    public void setActCommentId(String actCommentId) {
        this.actCommentId = actCommentId == null ? null : actCommentId.trim();
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

    public String  getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}