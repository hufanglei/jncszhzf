package com.java.activiti.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java.activiti.dao.TMainIssueDao;
import com.java.activiti.model.QhzdBean;
import com.java.activiti.model.TMainIssueBean;
import com.java.activiti.model.WeibanjuzdBean;
import com.java.activiti.service.IssueService;
import com.java.activiti.util.Const;
import com.java.activiti.util.Constants;

@Service("issueService")
public class IssueServiceImpl implements IssueService{

	@Resource
	private TMainIssueDao tMainIssueDao;
	
	/**
	 * 插入事件
	 */
	public int insertIssue(TMainIssueBean tMainIssueBean) {
		int result = tMainIssueDao.insertTMainIssue(tMainIssueBean);
		if (result > 0){
			//插入成功，返回刚插入数据的ID
			return tMainIssueBean.getId();
		}
		return 0;
	}

	/**
	 * 获取用户提交的所有事项请求数量
	 * @param map 条件参数
	 * @return int 请求数量数目
	 */
	public int issueCount(Map<String, Object> map) {
		return tMainIssueDao.selectTMainIssueCountByClause(map);
	}

	/**
	 * 分页获取用户请求数量
	 * @param map 条件参数
	 * @return List 结果结合
	 */
	public List<TMainIssueBean> IssuePage(Map<String, Object> map) {
		return tMainIssueDao.selectIssuePage(map);
	}

	public TMainIssueBean findById(int mainIssueId) {
		return tMainIssueDao.selectTMainIssueByPk(mainIssueId);
	}

	public int updateMainIssue(TMainIssueBean tMainIssueBean) {
		return tMainIssueDao.updateTMainIssue(tMainIssueBean);
	}

	public TMainIssueBean getMainIssueByProcessId(String processId) {
		return tMainIssueDao.selectTMainIssueByProcessId(processId);
	}

	public List<TMainIssueBean> getUnfinishedIssueList() {
		return tMainIssueDao.selectUnFinishedIssue();
	}

	public int updateLightStatus(Map<String, Object> map) {
		return tMainIssueDao.updateLightStatus(map);
	}
	
	public int deleteMainIssue(int mainIssueId) {
		return tMainIssueDao.deleteTMainIssueByPk(mainIssueId);
	}

	@Override
	public List<HashMap<String, Object>> selectProcessId(String issueNumber) {
		return tMainIssueDao.selectProcessId(issueNumber);
	}


}
