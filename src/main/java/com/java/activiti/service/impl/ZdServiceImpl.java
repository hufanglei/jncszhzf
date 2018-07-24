package com.java.activiti.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.java.activiti.dao.ZdDao;
import com.java.activiti.model.ZdBean;
import com.java.activiti.service.ZdService;

@Service("zdServiceImpl")
public class ZdServiceImpl implements ZdService{
	
	@Resource
	private ZdDao zdDao;

	/**
	 * 根据字典名称获取字典信息
	 */
	public List<ZdBean> getZdByDmmc(String dmmc) {
		//参数检查
		dmmc = dmmc.toString();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dmmc", dmmc);
		List<ZdBean> zdList = zdDao.selectZdByClause(map);
		return zdList;
	}

	/**
	 * 获取所有字典定义和对应明文关系
	 * @return hashmap，键值为类似于：xb_1，值为男
	 */
	@Cacheable(value="dictionaryCache")
	public HashMap<String, String> getDictionary(){
		HashMap<String, String> map = new HashMap<String, String>();
		Map<String, Object> mapClause = new HashMap<String, Object>();
		mapClause.put("dmmc","");
		mapClause.put("dmbh", "");
		List<ZdBean> dicList = zdDao.selectZdByClause(mapClause);
		if (dicList.size() > 0){
			for (int i = 0; i < dicList.size(); i++){
				map.put(dicList.get(i).getDmmc().toString() + "_" + dicList.get(i).getDmbh().toString(), dicList.get(i).getDmms().toString());
			}
		}
		return map;
	}
}
