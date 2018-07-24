package com.java.activiti.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java.activiti.dao.MemberShipDao;
import com.java.activiti.model.MemberShip;
import com.java.activiti.service.MemberShipService;

@Service("memberShipService")
public class MemberShipServiceImpl implements MemberShipService{

	@Resource
	private MemberShipDao menberShipDao;
	
	/**
	 * 用户登入的方法
	 * @return
	 */
	@Override
	public MemberShip userLogin(Map<String, Object> map){
		return menberShipDao.userLogin(map);
	}

	@Override
	public int deleteAllGroupsByUserId(String userId){
		return menberShipDao.deleteMemberShipByUserId(userId);
	}

	@Override
	public int addMemberShip(MemberShip memberShip){
		return menberShipDao.addMemberShip(memberShip);
	}

	@Override
	public MemberShip getMemberShipByClase(Map<String, Object> map){
		return menberShipDao.getMemberShipByClase(map);
	}

	@Override
	public MemberShip login(Map map) {
		return menberShipDao.login(map);
	}
	@Override
	public String getStreetGroupIdByUserId(String userId){
		return menberShipDao.getStreetGroupIdByUserId(userId);
	}
}
