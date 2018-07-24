package com.java.activiti.service;

import java.util.HashMap;
import java.util.List;

import com.java.activiti.model.ZdBean;

public interface ZdService {

	/**
	 * 根据代码名称获取代码信息
	 * @param dmmc 代码名称
	 * @return List，相应代码名称的代码信息
	 */
	public List<ZdBean> getZdByDmmc(String dmmc);
	
	public HashMap<String, String> getDictionary();
}
