package com.java.activiti.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.java.activiti.dao.GroupDao;
import com.java.activiti.model.Group;
import com.java.activiti.model.MemberShip;
import com.java.activiti.service.GroupService;
import org.springframework.stereotype.Service;

import com.java.activiti.dao.QhzdDao;
import com.java.activiti.model.QhzdBean;
import com.java.activiti.service.QhzdService;

@Service("QhzdService")
public class QhzdServiceImpl implements QhzdService{
	
	@Resource
	private QhzdDao qhzdDao;
	@Resource
	private GroupDao groupDao;
	@Resource
	private GroupService groupService;

	public List<QhzdBean> getQhzd(String qhdm, int qhjb, String ssqhdm) {
		//构造查询条件
		Map<String, Object> map = new HashMap<String, Object>();
		if (qhdm != "" && qhdm != null){
			map.put("qhdm", qhdm);
		}
		if (qhjb != 0){
			map.put("qhjb", qhjb);
		}
		if (ssqhdm != "" && ssqhdm != null){
			map.put("ssqhdm", ssqhdm);
		}
		
		return qhzdDao.selectQhzdByClause(map);
	}

	@Override
	public List<Group> getQhzdByAuthority(MemberShip user) {
		//构造查询条件
		Map<String, Object> map = new HashMap<>();
		List<Group> groups= new ArrayList<>();
		//查询当前用户所属组织机构
		Group group = groupDao.findByUserPk(user.getUserId());
		Group topGroup = groupService.getTopGroupInfo(group);
		if("2".equals(topGroup.getGroupTypeId().trim())) {//属于街道，只显示本街道信息
			groups.add(topGroup);
		}else{
			map.put("type","2");
			groups = groupDao.selectGroupByGid(map);
		}

		return groups;
	}

	@Override
	public List<QhzdBean> getStreetQhzdInfoTree(String qhdm) {
		//构造查询条件
		Map<String, Object> map = new HashMap<>();
		if (qhdm != "" && qhdm != null){
			map.put("qhdm", qhdm);
		}
		
		return qhzdDao.selectQhzdTreeDataByOrgPrefix(map);
	}

}
