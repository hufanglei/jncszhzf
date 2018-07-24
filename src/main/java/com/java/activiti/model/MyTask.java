package com.java.activiti.model;

import java.util.Date;

/**
 * 自定义任务实体 转json的时候用到
 * @author user
 *
 */
public class MyTask {

	private String id; // 任务id
	private String subject; //任务主题
	private String issueNumber;//受理编号
	private String name; // 任务名称
	private Date createTime;  // 创建日期
	private Date endTime; // 结束日期
	private int lightStatus; //办理时限判断逻辑
	private Date limitTime; //任务办理截止时间
	private String dbIssueNum;//督办案件受理编号
    private String processId;//
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	/**
	 * @return the lightStatus
	 */
	public int getLightStatus() {
		return lightStatus;
	}
	/**
	 * @param lightStatus the lightStatus to set
	 */
	public void setLightStatus(int lightStatus) {
		this.lightStatus = lightStatus;
	}
	/**
	 * @return the limitTime
	 */
	public Date getLimitTime() {
		return limitTime;
	}
	/**
	 * @param limitTime the limitTime to set
	 */
	public void setLimitTime(Date limitTime) {
		this.limitTime = limitTime;
	}

	public String getDbIssueNum() {
		return dbIssueNum;
	}

	public void setDbIssueNum(String dbIssueNum) {
		this.dbIssueNum = dbIssueNum;
	}

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
