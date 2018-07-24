package com.java.activiti.model;

public class WorkdayBean {
		private	Integer  id; // 自增主键
		private	String  year; // 所属年份
		private	String  workday; // 工作日
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}		
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}		
		public String getWorkday() {
			return workday;
		}
		public void setWorkday(String workday) {
			this.workday = workday;
		}		
	
}