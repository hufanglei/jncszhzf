package com.java.activiti.model;

/**
 * 用户扩展实体
 * @author user
 *
 */
public class User {

	private String id; // 主键 用户名
	private String userName;  // 姓名
	private String tel; // 电话
	private String email; // 邮箱
	private String password; // 密码
	private String groups; // 用户所有的角色 多个角色之间用逗号隔开
	private String orgNumber;//组织编码，社区编码
	private String jiedao; //所属街道
	private Integer userTypeID; //用户类型
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	/**
	 * @return the orgNumber
	 */
	public String getOrgNumber() {
		return orgNumber;
	}
	/**
	 * @param orgNumber the orgNumber to set
	 */
	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}
	/**
	 * @return the jiedao
	 */
	public String getJiedao() {
		return jiedao;
	}
	/**
	 * @param jiedao the jiedao to set
	 */
	public void setJiedao(String jiedao) {
		this.jiedao = jiedao;
	}
	/**
	 * @return the userTypeID
	 */
	public Integer getUserTypeID() {
		return userTypeID;
	}
	/**
	 * @param userTypeID the userTypeID to set
	 */
	public void setUserTypeID(Integer userTypeID) {
		this.userTypeID = userTypeID;
	}
	
}
