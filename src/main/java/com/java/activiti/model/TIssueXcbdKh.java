package com.java.activiti.model;

import java.util.Date;

public class TIssueXcbdKh {
    private Integer id;

    private String qhzdDm;

    private String qhzdMc;

    private Integer countrywlmt;

    private Integer countryfwlmt;

    private Integer provincewlmt;

    private Integer provincefwlmt;

    private Integer citywlmt;

    private Integer cityfwlmt;

    private Integer districtwlmt;

    private Integer districtfwlmt;

    private String khyf;

    private Float score;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQhzdDm() {
        return qhzdDm;
    }

    public void setKhyf(String khyf) {
        this.khyf = khyf == null ? null : khyf.trim();
    }

    public String getKhyf() {
        return khyf;
    }

    public void setQhzdDm(String qhzdDm) {
        this.qhzdDm = qhzdDm == null ? null : qhzdDm.trim();
    }

    public String getQhzdMc() {
        return qhzdMc;
    }

    public void setQhzdMc(String qhzdMc) {
        this.qhzdMc = qhzdMc == null ? null : qhzdMc.trim();
    }

    public Integer getCountrywlmt() {
        return countrywlmt;
    }

    public void setCountrywlmt(Integer countrywlmt) {
        this.countrywlmt = countrywlmt;
    }

    public Integer getCountryfwlmt() {
        return countryfwlmt;
    }

    public void setCountryfwlmt(Integer countryfwlmt) {
        this.countryfwlmt = countryfwlmt;
    }

    public Integer getProvincewlmt() {
        return provincewlmt;
    }

    public void setProvincewlmt(Integer provincewlmt) {
        this.provincewlmt = provincewlmt;
    }

    public Integer getProvincefwlmt() {
        return provincefwlmt;
    }

    public void setProvincefwlmt(Integer provincefwlmt) {
        this.provincefwlmt = provincefwlmt;
    }

    public Integer getCitywlmt() {
        return citywlmt;
    }

    public void setCitywlmt(Integer citywlmt) {
        this.citywlmt = citywlmt;
    }

    public Integer getCityfwlmt() {
        return cityfwlmt;
    }

    public void setCityfwlmt(Integer cityfwlmt) {
        this.cityfwlmt = cityfwlmt;
    }

    public Integer getDistrictwlmt() {
        return districtwlmt;
    }

    public void setDistrictwlmt(Integer districtwlmt) {
        this.districtwlmt = districtwlmt;
    }

    public Integer getDistrictfwlmt() {
        return districtfwlmt;
    }

    public void setDistrictfwlmt(Integer districtfwlmt) {
        this.districtfwlmt = districtfwlmt;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}