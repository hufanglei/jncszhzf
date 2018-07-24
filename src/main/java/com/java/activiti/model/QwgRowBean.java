package com.java.activiti.model;

import java.util.List;

public class QwgRowBean {

	private String sourceId;
	private String orderNo;
	private String sourcePerson;
	private String sourceMobile;
	private List<FileBean> flowAttachFileDubbos;
	private String occurLocation;
	private String completeData;
	private String title;
	private String occurDate;
	private String content;
	private int issueTypeId;
	private String occurOrg;
	private String sourceName;

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setSourcePerson(String sourcePerson) {
		this.sourcePerson = sourcePerson;
	}

	public String getSourcePerson() {
		return sourcePerson;
	}

	public void setSourceMobile(String sourceMobile) {
		this.sourceMobile = sourceMobile;
	}

	public String getSourceMobile() {
		return sourceMobile;
	}

	public void setFlowAttachFileDubbos(List<FileBean> flowAttachFileDubbos) {
		this.flowAttachFileDubbos = flowAttachFileDubbos;
	}

	public List<FileBean> getFlowAttachFileDubbos() {
		return flowAttachFileDubbos;
	}

	public void setOccurLocation(String occurLocation) {
		this.occurLocation = occurLocation;
	}

	public String getOccurLocation() {
		return occurLocation;
	}

	public void setCompleteData(String completeData) {
		this.completeData = completeData;
	}

	public String getCompleteData() {
		return completeData;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setOccurDate(String occurDate) {
		this.occurDate = occurDate;
	}

	public String getOccurDate() {
		return occurDate;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setIssueTypeId(int issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	public int getIssueTypeId() {
		return issueTypeId;
	}

	public void setOccurOrg(String occurOrg) {
		this.occurOrg = occurOrg;
	}

	public String getOccurOrg() {
		return occurOrg;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceName() {
		return sourceName;
	}

}
