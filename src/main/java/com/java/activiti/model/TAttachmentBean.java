package com.java.activiti.model;

public class TAttachmentBean {
		private	Integer  id; // 自增主键
		private	Integer  mainIssueId; // 对应的问题ID
		private	String  addUserId; // 上传人ID
		private	Integer  type; // 附件类型
		private	String  name; // 附件名称
		private	String  size; // 附件大小
		private	String  savePath; // 文件保存路径
		private	String  addtime; // 上传时间
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}		
		public Integer getMainIssueId() {
			return mainIssueId;
		}
		public void setMainIssueId(Integer mainIssueId) {
			this.mainIssueId = mainIssueId;
		}		
		public String getAddUserId() {
			return addUserId;
		}
		public void setAddUserId(String addUserId) {
			this.addUserId = addUserId;
		}		
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}		
		public String getSize() {
			return size;
		}
		public void setSize(String size) {
			this.size = size;
		}		
		public String getSavePath() {
			return savePath;
		}
		public void setSavePath(String savePath) {
			this.savePath = savePath;
		}		
		public String getAddtime() {
			return addtime;
		}
		public void setAddtime(String addtime) {
			this.addtime = addtime;
		}		
	
}