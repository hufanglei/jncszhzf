package com.java.activiti.dao;

import com.java.activiti.model.TMainIssueBean;
import com.java.activiti.model.TissueDcdbKhBean;
import com.java.activiti.model.dto.TMainIssueDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TissueDcdbKhMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TissueDcdbKhBean record);

    int insertSelective(TissueDcdbKhBean record);

    TissueDcdbKhBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TissueDcdbKhBean record);

    int updateByPrimaryKey(TissueDcdbKhBean record);
    int selectCount(Map map);

    List<TissueDcdbKhBean> selectListPage(Map map);

    int selectCountByDate(Map map);

    public abstract TissueDcdbKhBean selectTMDbIssueByProcessId(String ProcessId);

    String selectKeyByQhzd(String qhzdId);

    /**
     * 查询案件编码数量
     * @param map
     * @return
     */
    int queryCaseNum(Map map);

    TMainIssueDTO selectTMainIssueByProcessId(String ProcessId);

    /**
     * 校验案件编号
     * @param issueNum
     * @return
     */
    int selectIssueNum(String issueNum);

    /**
     * 查询督办考核
     * @param map
     * @return
     */
    List<TissueDcdbKhBean> selectDbkh(Map map);

    int countXcjzByMonth(Map<String, Object> map);

    List<TissueDcdbKhBean> selectXcjzByMonth(Map<String, Object> map);

    int selectGdDcdbKhCountByGroupId(Map queryMap);

    List<TissueDcdbKhBean> selectGdDcdbKhByGroupId(Map queryMap);

    TissueDcdbKhBean getDbIssueByProcess(String processId);

    String getUserId(String taskId);

    List<HashMap<String,Object>> selectProcessId(String dcdbnum);

    List<TissueDcdbKhBean> selectffts(Map map);

    List<TissueDcdbKhBean> selectfftsJsgs(Map map);

    List<TissueDcdbKhBean> refreshDcdbDetails(Map map);

    List<TissueDcdbKhBean> refreshZcDcdbDetails(Map map);

    int selectCountFfts(Map map);

    int selectCountZcFfts(Map map);

    int selectCountKfFfts(Map map);
}