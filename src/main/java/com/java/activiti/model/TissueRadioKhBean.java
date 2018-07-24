package com.java.activiti.model;

/**
 *  案件比重实体Bean
 */
public class TissueRadioKhBean {

    private Integer id;

    private String qhzdId;

    private String qhzdName;

    private Integer issuenum;

    private Integer avg12345num;

    private Double coefficient;

    private String proportion;

    private String khyf;

    private String score;

    public TissueRadioKhBean(){
    }
    public TissueRadioKhBean(String qhzdId,Integer avg12345num,String proportion,String khyf){
        this.qhzdId=qhzdId;
        this.avg12345num=avg12345num;
        this.proportion=proportion;
        this.khyf=khyf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getIssuenum() {
        return issuenum;
    }

    public void setIssuenum(Integer issuenum) {
        this.issuenum = issuenum;
    }

    public Integer getAvg12345num() {
        return avg12345num;
    }

    public void setAvg12345num(Integer avg12345num) {
        this.avg12345num = avg12345num;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion == null ? null : proportion.trim();
    }

    public String getKhyf() {
        return khyf;
    }

    public void setKhyf(String khyf) {
        this.khyf = khyf == null ? null : khyf.trim();
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }
}