package com.java.activiti.dao;

import java.util.Map;

import com.java.activiti.model.MemberShip;

public interface MemberShipDao {
	/**
	 * 用户登入的方法
	 * @return
	 */
	public MemberShip userLogin(Map<String, Object> map);
	
	int deleteMemberShipByUserId(String userId);
	
	public int addMemberShip(MemberShip memberShip);
	
	public MemberShip getMemberShipByClase(Map<String, Object> map);

	MemberShip login(Map<String, Object> map);

	/**
	 * 	根据网格员id找到其所在街道的组id
	 BY hfl
	 * @param userId
	 * @return
	 */
	public String getStreetGroupIdByUserId(String userId);
}
