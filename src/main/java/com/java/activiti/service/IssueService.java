package com.java.activiti.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java.activiti.model.TMainIssueBean;

public interface IssueService {
	
	/**
	 * 提交受理事件上报请求
	 * @param tMainIssueBean
	 * @return
	 */
	public int insertIssue(TMainIssueBean tMainIssueBean);
	
	/**
	 * 获取用户提交的所有事项请求数量
	 * @param map 条件参数
	 * @return int 请求数量数目
	 */
	public int issueCount(Map<String, Object> map);
	
	/**
	 * 分页获取用户请求数量
	 * @param map 条件参数
	 * @return List 结果结合
	 */
	public List<TMainIssueBean> IssuePage(Map<String, Object> map);
	
	/**
	 * 根据主流程ID获取主流程信息
	 * @param mainIssueId
	 * @return
	 */
	public TMainIssueBean findById(int mainIssueId);
	
	/**
	 * 更新主流程信息
	 * @param tMainIssueBean
	 * @return
	 */
	public int updateMainIssue(TMainIssueBean tMainIssueBean);
	
	/**
	 * 根据流程ID获取事件详情
	 * @param processId
	 * @return
	 */
	public TMainIssueBean getMainIssueByProcessId(String processId);
	
	/**
	 * 获取未结束的任务
	 * @return
	 */
	public List<TMainIssueBean> getUnfinishedIssueList();
	
	/**
	 * 更新灯的状态
	 * @param map
	 * @return
	 */
	public int updateLightStatus(Map<String, Object> map);
	
	/**
	 * 根据主流程ID删除主流程信息
	 * @param mainIssueId
	 * @return
	 */
	public int deleteMainIssue(int mainIssueId);

    List<HashMap<String,Object>> selectProcessId(String issueNumber);
}
