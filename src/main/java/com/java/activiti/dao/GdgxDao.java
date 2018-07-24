package com.java.activiti.dao;

import com.java.activiti.model.TMainIssueBean;

import java.util.List;
import java.util.Map; /**
 * 归档共享业务
 */
public interface GdgxDao {

    /**
     * 查询归档数据条数
     * @param map
     * @return
     */
    int selectGdgxIssueCountByClause(Map<String, Object> map);

    /**
     * 查询归档共享记录
     * @param map
     * @return
     */
    List<TMainIssueBean> selectGdgxIssueByClause(Map<String, Object> map);

    /**
     * 根据网格员查询归档数据条数
     * @param map
     * @return
     */
    int selectGdgxWgyIssueCountByClause(Map<String, Object> map);

    /**
     * 根据网格员查询归档共享记录
     * @param map
     * @return
     */
    List<TMainIssueBean> selectGdgxWgyIssueByClause(Map<String, Object> map);

    /**
     * 根据网格员查询归档记录条数
     * @param queryMap
     * @return
     */
    int selectGdgxIssueCountByGroupId(Map queryMap);

    /**
     * 网格员  按街道查询
     * @param queryMap
     * @return
     */
    List<TMainIssueBean> selectGdgxWgyIssueByGroupId(Map queryMap);

    /**
     * 非网格员 按街道查询
     * @param queryMap
     * @return
     */
    List<TMainIssueBean> selectGdgxIssueByGroupId(Map queryMap);

    /**
     * 非网格员 委办局 查询
     * @param queryMap
     * @return
     */
    List<TMainIssueBean> selectGdgxIssueByDept(Map queryMap);

    /**
     * 非网格员
     * @param queryMap
     * @return
     */
    List<TMainIssueBean> selectGdgxIssueBydept(Map queryMap);

    /**
     * 查询统计记录条数
     * @param queryMap
     * @return
     */
    int selectGdgxIssueCountByDept(Map queryMap);

    List<Map<String,Object>> selecAttachList(String processId);

    List<Map<String,Object>> selecDbAttachList(String processId);

    List<Map<String,Object>> selecPtAttachList(String processId);

    int selectZzajWgyIssueCount(Map queryMap);

    List<TMainIssueBean> selectZzajWgyIssueByGroupId(Map queryMap);

    List<TMainIssueBean> selectZzajIssueByGroupId(Map queryMap);

    int selectZzajIssueCount(Map pageMap);
}
