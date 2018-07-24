package com.java.activiti.dao;

import com.java.activiti.model.TissueJdjcKhBean;

import java.util.List;
import java.util.Map;

public interface TissueJdjcKhMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TissueJdjcKhBean record);

    int insertSelective(TissueJdjcKhBean record);

    TissueJdjcKhBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TissueJdjcKhBean record);

    int updateByPrimaryKey(TissueJdjcKhBean record);

    /**
     * 监督检查按月统计
     * @param map
     * @return
     */
    int countByMonth(Map<String, Object> map);

    /**
     * 监督检查按月查询
     * @param map
     * @return
     */
    List<TissueJdjcKhBean> selectByMonth(Map<String, Object> map);
}