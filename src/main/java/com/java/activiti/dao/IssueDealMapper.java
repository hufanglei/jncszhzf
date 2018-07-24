package com.java.activiti.dao;

import com.java.activiti.model.IssueDeal;

public interface IssueDealMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IssueDeal record);

    int insertSelective(IssueDeal record);

    IssueDeal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IssueDeal record);

    int updateByPrimaryKey(IssueDeal record);

}