package com.java.activiti.dao;

import com.java.activiti.model.TissueLbbmBean;

public interface TissueLbbmMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TissueLbbmBean record);

    int insertSelective(TissueLbbmBean record);

    TissueLbbmBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TissueLbbmBean record);

    int updateByPrimaryKey(TissueLbbmBean record);
}