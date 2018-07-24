package com.java.activiti.model;

public class TissueLbbmBean {
    private Integer id;

    private String issueNumber;

    private String issueExec;

    private String execType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber == null ? null : issueNumber.trim();
    }

    public String getIssueExec() {
        return issueExec;
    }

    public void setIssueExec(String issueExec) {
        this.issueExec = issueExec == null ? null : issueExec.trim();
    }

    public String getExecType() {
        return execType;
    }

    public void setExecType(String execType) {
        this.execType = execType == null ? null : execType.trim();
    }
}