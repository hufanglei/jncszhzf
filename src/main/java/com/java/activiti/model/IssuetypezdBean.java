package com.java.activiti.model;

public class IssuetypezdBean {
		private	Integer  id; // id
		private	String  mc; // 名称
		private	String  bh; // 编号
		private	String  sssjbh; // 所属上级编号
		private	String  ms; // 描述
		private	Integer  bhorder; // 字典项的顺序。1表示位于第一位，2表示第二位，以此类推
		private	String  description; // 对代码名称的描述
		private	Integer  extend; // 对于具体处理事项的处理时间
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}		
		public String getMc() {
			return mc;
		}
		public void setMc(String mc) {
			this.mc = mc;
		}		
		public String getBh() {
			return bh;
		}
		public void setBh(String bh) {
			this.bh = bh;
		}		
		public String getSssjbh() {
			return sssjbh;
		}
		public void setSssjbh(String sssjbh) {
			this.sssjbh = sssjbh;
		}		
		public String getMs() {
			return ms;
		}
		public void setMs(String ms) {
			this.ms = ms;
		}		
		public Integer getBhorder() {
			return bhorder;
		}
		public void setBhorder(Integer bhorder) {
			this.bhorder = bhorder;
		}		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}		
		public Integer getExtend() {
			return extend;
		}
		public void setExtend(Integer extend) {
			this.extend = extend;
		}		
	
}