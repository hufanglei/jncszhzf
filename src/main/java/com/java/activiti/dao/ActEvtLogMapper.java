package com.java.activiti.dao;

import com.java.activiti.model.ActEvtLog;

import java.util.List;
import java.util.Map;

public interface ActEvtLogMapper {
    int deleteByPrimaryKey(Long logNr);

    int insert(ActEvtLog record);

    int insertSelective(ActEvtLog record);

    ActEvtLog selectByPrimaryKey(Long logNr);

    int updateByPrimaryKeySelective(ActEvtLog record);

    int updateByPrimaryKeyWithBLOBs(ActEvtLog record);

    int updateByPrimaryKey(ActEvtLog record);

    public abstract List<ActEvtLog> selectListPage(Map<String, Object> map);

    public abstract int selectCount(Map map1);
}