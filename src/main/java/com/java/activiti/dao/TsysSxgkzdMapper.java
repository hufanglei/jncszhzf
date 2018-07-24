package com.java.activiti.dao;

import com.java.activiti.model.TsysSxgkzdBean;

import java.util.List;
import java.util.Map;

public interface TsysSxgkzdMapper {
    int insert(TsysSxgkzdBean record);

    int insertSelective(TsysSxgkzdBean record);

    List<Map<String,Object>> selectSxgkzdList(String parentId);
}