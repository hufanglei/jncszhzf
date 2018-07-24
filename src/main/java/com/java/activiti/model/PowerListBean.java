package com.java.activiti.model;

public class PowerListBean {
		private	Integer  id; // 自增主键
		private	String  powerCode; // 权力编码
		private	String  ssWeibanju; // 类别
		private	String  powerDesc; // 检查事项名称
		private	String  execLevel; // 行使层级
		private	Integer  limitedWorkdays; // 工作日时限
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}		
		public String getPowerCode() {
			return powerCode;
		}
		public void setPowerCode(String powerCode) {
			this.powerCode = powerCode;
		}		
		public String getSsWeibanju() {
			return ssWeibanju;
		}
		public void setSsWeibanju(String ssWeibanju) {
			this.ssWeibanju = ssWeibanju;
		}		
		public String getPowerDesc() {
			return powerDesc;
		}
		public void setPowerDesc(String powerDesc) {
			this.powerDesc = powerDesc;
		}		
		public String getExecLevel() {
			return execLevel;
		}
		public void setExecLevel(String execLevel) {
			this.execLevel = execLevel;
		}		
		public Integer getLimitedWorkdays() {
			return limitedWorkdays;
		}
		public void setLimitedWorkdays(Integer limitedWorkdays) {
			this.limitedWorkdays = limitedWorkdays;
		}		
	
}