package com.java.activiti.model;

public class TQwgStatusBean {
		private	String  orderno; // orderNo
		private	String  status; // status
		private	String  processTime; // process_time
		private	String  comments; // comments
		private	String  issuccess; // 是否成功反馈给全网格
		public String getOrderno() {
			return orderno;
		}
		public void setOrderno(String orderno) {
			this.orderno = orderno;
		}		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}		
		public String getProcessTime() {
			return processTime;
		}
		public void setProcessTime(String processTime) {
			this.processTime = processTime;
		}		
		public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}		
		public String getIssuccess() {
			return issuccess;
		}
		public void setIssuccess(String issuccess) {
			this.issuccess = issuccess;
		}		
	
}