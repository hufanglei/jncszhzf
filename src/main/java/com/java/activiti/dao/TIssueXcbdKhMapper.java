package com.java.activiti.dao;

import com.java.activiti.model.TIssueXcbdKh;

import java.util.List;
import java.util.Map;

public interface TIssueXcbdKhMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TIssueXcbdKh record);

    int insertSelective(TIssueXcbdKh record);

    TIssueXcbdKh selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TIssueXcbdKh record);

    int updateByPrimaryKey(TIssueXcbdKh record);

    /**
     * 通过月份查询总数，用于分页
     * @param map
     * @return
     */
    int countByMonth(Map<String, Object> map);

    /**
     * 通过月份分页查询
     * @param map
     * @return
     */
    List<TIssueXcbdKh> selectByMonth(Map<String,Object> map);

    int  checkXcbd(Map map);

    List<TIssueXcbdKh> selectXcbd(Map map);

    List<TIssueXcbdKh> showXcbdDetails(Map<String, Object> map);
}