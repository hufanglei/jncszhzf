package com.java.activiti.model.dto;

/**
 * Created by Administrator on 2017/12/13.
 */
public class UserDTO {
    private String id; // 主键 用户名
    private String userName;  // 姓名
    private String tel; // 电话
    private String email; // 邮箱
    private String orgNumber;//组织编码，社区编码
    private int groupId; //用户组ID
    private String groupName; // 用户组名称  即所属机构

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
