package com.java.activiti.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface GdgxService {

    /**
     * 查询归档共享业务
     * @param startDay
     * @param endDay
     * @param page
     * @param rows
     * @param session
     * @return
     */
    Map gdgxPage(String startDay, String endDay, String issueSbject, String page, String rows, HttpSession session);

    /**
     * 查询归档共享业务
     * @param startDay
     * @param endDay
     * @param issueSubject 案件归口
     * @param page
     * @param rows
     * @param groupId  案件执行街道or委办局
     * @param groupTypeId
     * @param session
     * @return
     */
    Map selectGdgxge(HttpServletRequest request, String startDay, String endDay, String issueSubject, String page, String rows, String groupId, String groupTypeId, HttpSession session,String type);

    List<Map<String,Object>> selecAttachList(String processId);

    /**
     * 归档案件中  督办案件 的附件详情
     * @param processId
     * @return
     */
    List<Map<String,Object>> selecDbAttachList(String processId);

    /**
     *督办归档案件中 显示普通案件详情 附件
     * @param processId
     * @return
     */
    List<Map<String,Object>> selecPtAttachList(String processId);
}
