package com.java.core.model;

public class InitCodeBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7920354718704227832L;
	private String id;
	private String tableName;
	private String name;
	private String where;
	private String whereValue;
	
	public String getWhereValue() {
		return whereValue;
	}

	public void setWhereValue(String whereValue) {
		this.whereValue = whereValue;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
