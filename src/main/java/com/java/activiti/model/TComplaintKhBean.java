package com.java.activiti.model;

public class TComplaintKhBean {
    private Integer id;

    private String source;

    private String content;

    private String score;

    private String qhzdId;

    private String qhzdName;

    private String khGroupId;

    private String createTime;

    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    public String getQhzdId() {
        return qhzdId;
    }

    public void setQhzdId(String qhzdId) {
        this.qhzdId = qhzdId == null ? null : qhzdId.trim();
    }

    public String getQhzdName() {
        return qhzdName;
    }

    public void setQhzdName(String qhzdName) {
        this.qhzdName = qhzdName == null ? null : qhzdName.trim();
    }

    public String getKhGroupId() {
        return khGroupId;
    }

    public void setKhGroupId(String khGroupId) {
        this.khGroupId = khGroupId == null ? null : khGroupId.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}