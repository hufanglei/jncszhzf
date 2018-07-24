package com.java.activiti.service;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.Group;
import com.java.activiti.model.MemberShip;
import org.springframework.stereotype.Service;

import com.java.activiti.model.QhzdBean;

public interface QhzdService {

	/**
	 * 获取区划字典信息
	 * @param qhdm 区划代码
	 * @param qhjb 区划级别
	 * @param ssqhdm 所属区划代码
	 * @return qhzdBean列表
	 */
	public List<QhzdBean> getQhzd(String qhdm, int qhjb, String ssqhdm);
	
	/**
	 * 获取指定街道的行政区划信息树（本街道及下属社区信息）
	 * @param qhdm 街道行政区划编码前9位
	 * @return
	 */
	List<QhzdBean> getStreetQhzdInfoTree(String qhdm);

	/**
	 * 查询用户组织机构信息————街道人员返回当前街道
	 * @param user
	 * @return
	 */
	List<Group> getQhzdByAuthority(MemberShip user);
}
