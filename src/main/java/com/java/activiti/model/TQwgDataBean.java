package com.java.activiti.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TQwgDataBean {
    private int id; // orderNo
    private String orderno; // orderNo
    private String sourceid; // sourceId
    private String sourceperson; // sourcePerson
    private String sourcemobile; // sourceMobile
    private String flowattachfiledubbos; // flowAttachFileDubbos
    private String occurlocation; // occurLocation
    private String completedata; // completeData
    private String title; // title
    private String occurdate; // occurDate
    private String content; // content
    private Integer issuetypeid; // issueTypeId
    private String occurorg; // occurOrg
    private String sourcename; // sourceName


    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getSourceid() {
        return sourceid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public String getSourceperson() {
        return sourceperson;
    }

    public void setSourceperson(String sourceperson) {
        this.sourceperson = sourceperson;
    }

    public String getSourcemobile() {
        return sourcemobile;
    }

    public void setSourcemobile(String sourcemobile) {
        this.sourcemobile = sourcemobile;
    }

    public String getFlowattachfiledubbos() {
        return flowattachfiledubbos;
    }

    public void setFlowattachfiledubbos(String flowattachfiledubbos) {
        this.flowattachfiledubbos = flowattachfiledubbos;
    }

    public String getOccurlocation() {
        return occurlocation;
    }

    public void setOccurlocation(String occurlocation) {
        this.occurlocation = occurlocation;
    }

    public String getCompletedata() {
        return completedata;
    }

    public void setCompletedata(String completedata) {
        this.completedata = completedata;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOccurdate() {
        return occurdate;
    }

    public void setOccurdate(String occurdate) {
        this.occurdate = occurdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIssuetypeid() {
        return issuetypeid;
    }

    public void setIssuetypeid(Integer issuetypeid) {
        this.issuetypeid = issuetypeid;
    }

    public String getOccurorg() {
        return occurorg;
    }

    public void setOccurorg(String occurorg) {
        this.occurorg = occurorg;
    }

    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }

    public List<String> getFileAttachments() {
        if (StringUtils.isBlank(this.getFlowattachfiledubbos())) {
            return new ArrayList<>();
        }
        return Arrays.asList(this.getFlowattachfiledubbos().split(","));
    }
}