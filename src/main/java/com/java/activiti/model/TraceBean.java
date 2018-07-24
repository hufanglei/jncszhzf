package com.java.activiti.model;

public class TraceBean {
		private	Integer  id; // id
		private	String  userId; // uid
		private	double  x; // x
		private	double  y; // y
		private	String  curTime; // curtime

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getCurTime() {
		return curTime;
	}

	public void setCurTime(String curTime) {
		this.curTime = curTime;
	}
}