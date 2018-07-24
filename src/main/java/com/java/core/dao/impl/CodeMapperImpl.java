package com.java.core.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.java.core.dao.CodeMapper;
import com.java.core.model.InitCodeBean;

public class CodeMapperImpl implements CodeMapper{
	private SqlSessionFactory sessionFactory;
	private Map<String, Object> initMap;
	private static Map<String, Map<String, String>> allCodeMap;
	
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> getAllCodeMap() {
		if (allCodeMap == null) {
			allCodeMap = new LinkedHashMap<String, Map<String, String>>();
			for (Entry<String, Object> entry : initMap.entrySet()) {
				Object value = entry.getValue();
				if (value instanceof InitCodeBean) {
					allCodeMap.put(entry.getKey(),
							getCodeMap((InitCodeBean) value));
				} else if (value instanceof Map) {
					allCodeMap.put(entry.getKey(), (Map<String,String>)entry.getValue());
				}
			}
		}
		return allCodeMap;
	}

	public Map<String, String> getCodeMap(InitCodeBean codeBean) {
		String sql = null;
		if (StringUtils.isNotEmpty(codeBean.getWhere())) {
			sql = "select " + codeBean.getId() + " id, " + codeBean.getName()
					+ " as name from " + codeBean.getTableName() + " where "
					+ codeBean.getWhere() + "='"+codeBean.getWhereValue()+"' order by dmorder"; 
					//+ codeBean.getId();
		} else {
			sql = "select " + codeBean.getId() + " id, " + codeBean.getName()
					+ " as name from " + codeBean.getTableName() + " order by  dmorder";
					//+ codeBean.getId();
		}
		
		SqlSession session = sessionFactory.openSession();
		Connection connection = session.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				map.put(rs.getString("id"), rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!= null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!= null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		return map;
	}

	public Map<String, Object> getInitMap() {
		return initMap;
	}

	public void setInitMap(Map<String, Object> initMap) {
		this.initMap = initMap;
	}

	public void setAllCodeMap(Map<String, Map<String, String>> allCodeMap) {
		CodeMapperImpl.allCodeMap = allCodeMap;
	}

	public SqlSessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
