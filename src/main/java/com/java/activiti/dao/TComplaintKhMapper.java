package com.java.activiti.dao;

import com.java.activiti.model.TComplaintKhBean;
import com.java.activiti.model.TMainIssueKhBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TComplaintKhMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TComplaintKhBean record);

    int insertSelective(TComplaintKhBean record);

    TComplaintKhBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TComplaintKhBean record);

    int updateByPrimaryKey(TComplaintKhBean record);

    //查询总记录数
    public abstract int selectCount(Map map1);

    public abstract List<TComplaintKhBean> selectListPage(Map<String, Object> map);

    //查询所有的投诉部门
    public abstract List<HashMap> selectAllOrg();

    //删除所有的考核项
    /**
     * 批量删除考核项
     * @param id
     * @return
     */
    public int deleteComplaint(List<Integer> id);



}