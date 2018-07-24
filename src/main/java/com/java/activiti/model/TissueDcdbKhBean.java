package com.java.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class TissueDcdbKhBean  implements Serializable{
    private Integer id;

    private String dcdbnum;

    private String issuenum;

    private String processId;

    private String sourceId;

    private String qhzdName;

    private String qhzdId;

    private String typeId;

    private String typeName;

    private String descript;

    private String score;

    private String info;

    private Date createTime;

    private String khyf;

    private String sourceName;

    private String status;

    private String statusName;
    private String caseType;
    private String startTime;//案件处理开始时间
    private String totalTime;//案件处理总时长
    private String timeLimit; // 处理时限
    private String endTime;
    private String addUserid;//上报人id
    private String evaluationStatus;//案件处理 满意:1不满意 :2

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDcdbnum() {
        return dcdbnum;
    }

    public void setDcdbnum(String dcdbnum) {
        this.dcdbnum = dcdbnum == null ? null : dcdbnum.trim();
    }

    public String getIssuenum() {
        return issuenum;
    }

    public void setIssuenum(String issuenum) {
        this.issuenum = issuenum == null ? null : issuenum.trim();
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    public String getQhzdName() {
        return qhzdName;
    }

    public void setQhzdName(String qhzdName) {
        this.qhzdName = qhzdName == null ? null : qhzdName.trim();
    }

    public String getQhzdId() {
        return qhzdId;
    }

    public void setQhzdId(String qhzdId) {
        this.qhzdId = qhzdId == null ? null : qhzdId.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript == null ? null : descript.trim();
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getKhyf() {
        return khyf;
    }

    public void setKhyf(String khyf) {
        this.khyf = khyf == null ? null : khyf.trim();
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName == null ? null : sourceName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
        }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddUserid() {
        return addUserid;
    }

    public void setAddUserid(String addUserid) {
        this.addUserid = addUserid;
    }

    public String getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }
}