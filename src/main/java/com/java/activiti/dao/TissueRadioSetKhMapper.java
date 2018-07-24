package com.java.activiti.dao;

import com.java.activiti.model.TissueRadioSetKhBean;

public interface TissueRadioSetKhMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TissueRadioSetKhBean record);

    int insertSelective(TissueRadioSetKhBean record);

    TissueRadioSetKhBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TissueRadioSetKhBean record);

    int updateByPrimaryKey(TissueRadioSetKhBean record);
}