package com.java.activiti.dao;

import com.java.activiti.model.DbIssueDeal;

public interface DbIssueDealMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DbIssueDeal record);

    int insertSelective(DbIssueDeal record);

    DbIssueDeal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DbIssueDeal record);

    int updateByPrimaryKey(DbIssueDeal record);
}