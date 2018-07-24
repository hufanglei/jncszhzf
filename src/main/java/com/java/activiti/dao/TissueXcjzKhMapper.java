package com.java.activiti.dao;

import com.java.activiti.model.TissueXcjzKhBean;

import java.util.List;
import java.util.Map;

public interface TissueXcjzKhMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TissueXcjzKhBean record);

    int insertSelective(TissueXcjzKhBean record);

    TissueXcjzKhBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TissueXcjzKhBean record);

    int updateByPrimaryKey(TissueXcjzKhBean record);

    /**
     * 按考核月份查询
     * @param map
     * @return
     */
    int countByMonth(Map<String, Object> map);

    /**
     * 按考核月份查询
     * @param map
     * @return
     */
    List<TissueXcjzKhBean> selectByMonth(Map<String, Object> map);
}