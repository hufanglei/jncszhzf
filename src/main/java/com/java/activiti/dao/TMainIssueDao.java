package com.java.activiti.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java.activiti.model.TMainIssueBean;


public interface TMainIssueDao {

	List<TMainIssueBean> selectTMainIssueByClause(Map<String, Object> map);

	/**
	 * 案件跟踪
	 * @param map
	 * @return
	 */
	List<TMainIssueBean> selectTMainIssueAjgz(Map<String, Object> map);


	TMainIssueBean selectTMainIssueByPk(int id);

	TMainIssueBean selectTMainIssueByIssueNumber(String issueNumber);

	List<TMainIssueBean> selectIssuePage(Map<String, Object> map);
	
	int selectTMainIssueCountByClause(Map<String, Object> map);

	/**
	 * 案件跟踪 记录数
	 * @param map
	 * @return
	 */
	int selectTMainIssueCountAjgz(Map<String, Object> map);

	int insertTMainIssue(TMainIssueBean tMainIssueBean);

	int deleteTMainIssue(int id);

	int updateTMainIssue(TMainIssueBean tMainIssueBean);

	TMainIssueBean selectTMainIssueByProcessId(String ProcessId);
	
	List<TMainIssueBean> selectUnFinishedIssue();
	
	int updateLightStatus(Map<String, Object> map);
	
	int deleteTMainIssueByPk(int mainIssueId);
	
	List selectTMainIssueStatusCount();
	
	int selectLightStatusCount(HashMap<String, Object> map);
	
	List<HashMap<String, String>> selectIssueStatusStatistic(Map map);
	
	List<HashMap<String, String>> selectIssueCountStatistic(Map map);
	
	List<HashMap<String, String>> selectStreetIssueCountByClause(Map map);
	
	List<HashMap<String, String>> selectWeiBanJuIssueCountByClause(Map map);

	List<HashMap<String, String>> selectPowerBelongIssueCount(Map map);
	
	List<HashMap<String, String>> selectTMainIssueForHeatmap(Map map);

    List<Map<String, String>> queryIssueMap(Map map);

	List<Map<String,String>> queryIssueMapByDept(Map map);

    int queryCaseNum(Map map);

	String getSubject(String issueSubject);

	List<HashMap<String,String>> selectStreetIssueCountByDay(Map map1);

	List<HashMap<String,String>> selectWeiBanJuIssueCountByDay(Map map1);

	Double selectCount(String khyf);

	List<HashMap<String,Object>> selectJdajNum(String khyf);

    /**
     * 归档查询中  督办案件案件详情查询
     * @param processId
     * @return
     */
	TMainIssueBean getDbIssueByProcessId(String processId);

    String getUserId(String issueNumber);

    List<HashMap<String,Object>>  selectProcessId(String issueNumber);

	/*String selectDealWay(String processId);*/

	TMainIssueBean selectIssueByProcessId(String processId);

	List<TMainIssueBean> selectZxclDetails(Map map);

	List<TMainIssueBean> selectAjbzDetails(Map map);

	List<TMainIssueBean> selectJsgs(Map map);

	List<TMainIssueBean> selectAsclDetails(Map map);

    List<TMainIssueBean> selectAsclJsgs(Map map);

    List<TMainIssueBean> refreshZxclDetails(Map map);

    List<TMainIssueBean> refreshAsclDetails(Map map);

    List<TMainIssueBean> selectWzxDetails(Map map);

    List<TMainIssueBean> selectWzxJsgs(Map map);

	List<TMainIssueBean> refreshZcAsclDetails(Map map);

	List<TMainIssueBean> selectZcWzxDetails(Map map);

    List<TMainIssueBean> selectKsclDetails(Map map);

	List<TMainIssueBean> refreshZcKsclDetails(Map map);

	List<TMainIssueBean> refreshKsclDetails(Map map);

	List<TMainIssueBean> selectZcAjbzDetails(Map map);

	List<TMainIssueBean> selectAjbzJsgs(Map map);

    List<TMainIssueBean> selectKsclJsgs(Map map);

    int selectCountZxcl(Map map);

/*	int selectCountZcZxcl(Map map);*/

    int selectCountAscl(Map map);

    int selectCountKscl(Map map);

    int selectCountWzxgfx(Map map);

	int selectRefreshCountZxcl(Map map);

	int selectCountZcAscl(Map map);

	int selectCountKfAscl(Map map);

	int selectCountZcKscl(Map map);

	int selectCountKfKscl(Map map);

	int selectCountZcWzxgfx(Map map);

	int selectCountAjbz(Map map);

	int selectCountZcAjbz(Map map);



}
