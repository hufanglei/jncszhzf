package com.java.activiti.model;

import java.io.Serializable;

public class PermissionBean implements Serializable{
		private	Integer  id; // id
		private	String  name; // name
		private	String  description; // description
		private	String  tag; // tag
		private	Integer  fatherid; // fatherid
		private	Integer  order; // order
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}		
		public String getName() {
			return name;
		}		
		public void setName(String name) {
			this.name = name;
		}		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}		
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}		
		public Integer getFatherid() {
			return fatherid;
		}
		public void setFatherid(Integer fatherid) {
			this.fatherid = fatherid;
		}		
		public Integer getOrder() {
			return order;
		}
		public void setOrder(Integer order) {
			this.order = order;
		}		
	
}