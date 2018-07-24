package com.java.activiti.model;

public class ZdBean {
		private	Integer  id; // id
		private	String  dmmc; // 代码名称
		private	String  dmbh; // 代码编号
		private	String  sssjbh; // 所属上级编号
		private	String  dmms; // 代码描述
		private	Integer  dmorder; // 字典项的顺序。1表示位于第一位，2表示第二位，以此类推
		private	String  description; // 对代码名称的描述
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}		
		public String getDmmc() {
			return dmmc;
		}
		public void setDmmc(String dmmc) {
			this.dmmc = dmmc;
		}		
		public String getDmbh() {
			return dmbh;
		}
		public void setDmbh(String dmbh) {
			this.dmbh = dmbh;
		}		
		public String getSssjbh() {
			return sssjbh;
		}
		public void setSssjbh(String sssjbh) {
			this.sssjbh = sssjbh;
		}		
		public String getDmms() {
			return dmms;
		}
		public void setDmms(String dmms) {
			this.dmms = dmms;
		}		
		public Integer getDmorder() {
			return dmorder;
		}
		public void setDmorder(Integer dmorder) {
			this.dmorder = dmorder;
		}		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}		
	
}