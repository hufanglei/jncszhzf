package com.java.activiti.model;

import java.util.Date;

public class TissueJdjcKhBean {
    private Integer id;

    private String issueNum;

    private String sourceId;

    private String qhzdName;

    private String qhzdId;

    private String descript;

    private String score;

    private Date createTime;

    private String khyf;

    private String comment;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssueNum() {
        return issueNum;
    }

    public void setIssueNum(String issueNum) {
        this.issueNum = issueNum == null ? null : issueNum.trim();
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}