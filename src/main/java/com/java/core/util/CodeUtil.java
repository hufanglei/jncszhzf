package com.java.core.util;

import java.util.Map;

import com.java.core.dao.CodeMapper;
import com.java.core.util.SpringUtil;
public class CodeUtil {
	private static Map<String, Map<String, String>> allCodeMap;
	
	public static Map<String, String> getCodeMap(String targetName) {
		return getAllCodeMap().get(targetName);
	}

	
	public static String getCodeName(String targetName, String codeId) {
		String codeName = null;
		Map<String, Map<String, String>> allMap = getAllCodeMap();
		if (allMap != null) {
			Map<String, String> map = allMap.get(targetName);
			if (map != null) {
				codeName = map.get(codeId);
			}
		}
		return codeName;
	}


	public static Map<String, Map<String, String>> getAllCodeMap() {
		if(allCodeMap == null) {
			CodeMapper codeMapper = (CodeMapper)SpringUtil.getBean("codeMapper");
			allCodeMap = codeMapper.getAllCodeMap();
		}
		return allCodeMap;
	}


	public static void setAllCodeMap(Map<String, Map<String, String>> allCodeMap) {
		CodeUtil.allCodeMap = allCodeMap;
	}
}
