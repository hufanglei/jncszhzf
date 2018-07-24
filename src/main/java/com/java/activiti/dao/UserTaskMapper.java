package com.java.activiti.dao;

import com.java.activiti.model.CommentDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserTaskMapper {


    List<CommentDetail> getCommonDetailList(Map map);

    HashMap<String,String> selectUserDepartment(Map map2);

    List<CommentDetail> getPtCommonDetailList(Map map);

    List<CommentDetail> getDbDetailList(Map map);
}
