package com.java.activiti.model;

public class QhzdBean {
		private	Integer  id; // 自增主键
		private	String  qhdm; // 区划代码
		private	String  qhmc; // 区划名称
		private	Integer  qhjb; // 区划级别
		private	String  ssqhdm; // 所属区划代码
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}		
		public String getQhdm() {
			return qhdm;
		}
		public void setQhdm(String qhdm) {
			this.qhdm = qhdm;
		}		
		public String getQhmc() {
			return qhmc;
		}
		public void setQhmc(String qhmc) {
			this.qhmc = qhmc;
		}		
		public Integer getQhjb() {
			return qhjb;
		}
		public void setQhjb(Integer qhjb) {
			this.qhjb = qhjb;
		}		
		public String getSsqhdm() {
			return ssqhdm;
		}
		public void setSsqhdm(String ssqhdm) {
			this.ssqhdm = ssqhdm;
		}		
	
}