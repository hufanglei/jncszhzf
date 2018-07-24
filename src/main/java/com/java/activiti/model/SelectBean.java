package com.java.activiti.model;

/**
 * select下拉对象
 * @author PeiXQ
 *
 */
public class SelectBean {
	private	String id; // id
	private String text; // 主键 角色名 - 该字段有值的才是角色对象，否则为非实体的树目录

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
