package com.java.activiti.service.impl;


import com.java.activiti.dao.GdgxDao;
import com.java.activiti.model.MemberShip;
import com.java.activiti.model.TMainIssueBean;
import com.java.activiti.model.User;
import com.java.activiti.service.GdgxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("gdgxService")
public class GdgxServiceImpl implements GdgxService {

    @Resource
    private GdgxDao gdgxDao;

    /**
     * TODO 此处的逻辑应该是 街道中心、区级中心、委办局、区级领导看到的，街道只能显示自己的，暂时先显示全部
     *
     * @param startDay
     * @param endDay
     * @param page
     * @param rows
     * @param session
     * @return
     */
    @Override
    public Map gdgxPage(String startDay, String endDay, String issueSubject, String page, String rows, HttpSession session) {
        Map queryMap = new HashMap();
        Map map = new HashMap();
        int count = 0;
        List<TMainIssueBean> list = new ArrayList<>();

        //根据会话信息获取当前登录账号
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户
        User currentUser = currentMemberShip.getUser();
        String userId = currentUser.getId();
        Integer userTypeID = currentUser.getUserTypeID();
        if (userTypeID == null) {
            map.put("total", count);
            map.put("rows", list);
            return map;
        }

//        if(userTypeID==0) {
        queryMap.put("userId", "");
//        }else{
//            queryMap.put("userId", userId);
//        }
        queryMap.put("status", "16");
        queryMap.put("createTime", startDay);
        queryMap.put("endTime", endDay);
        queryMap.put("issueSubject", issueSubject);

//        //网格员
//        if(userTypeID==1){
//            count = gdgxDao.selectGdgxWgyIssueCountByClause(queryMap);
//        }else{
        count = gdgxDao.selectGdgxIssueCountByClause(queryMap);
//        }

        if (count > 0) {
            queryMap.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
            queryMap.put("pageSize", Integer.parseInt(rows));
            //网格员
            if (userTypeID == 1) {
                list = gdgxDao.selectGdgxWgyIssueByClause(queryMap);
            } else {
                list = gdgxDao.selectGdgxIssueByClause(queryMap);
            }
        }

        map.put("total", count);
        map.put("rows", list);

        return map;
    }

    /**
     * TODO 此处的逻辑应该是 街道中心、区级中心、委办局、区级领导看到的，街道只能显示自己的，
     *
     * @param startDay
     * @param endDay
     * @param issueSubject 案件归口
     * @param page
     * @param rows
     * @param groupId      案件执行街道or委办局
     * @param groupTypeId
     * @param session
     * @return
     */
    @Override
    public Map selectGdgxge(HttpServletRequest request, String startDay, String endDay, String issueSubject, String page, String rows, String groupId, String groupTypeId, HttpSession session, String type) {

        Map map = new HashMap();
        int count = 0;
        List<TMainIssueBean> list = new ArrayList<>();
        String caseType = request.getParameter("caseType");
        //根据会话信息获取当前登录账号
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户
        User currentUser = currentMemberShip.getUser();
        String userId = currentUser.getId();
        Integer userTypeID = currentUser.getUserTypeID();
        if (userTypeID == null) {
            map.put("total", count);
            map.put("rows", list);
            return map;
        }
        Map pageMap = new HashMap();
        //处理中的案件
        pageMap.put("createTime", startDay);
        pageMap.put("endTime", endDay);
        pageMap.put("caseType", caseType);
        pageMap.put("issueSubject", issueSubject);
        pageMap.put("issueExecStreet", groupId);
        if (type.equals("1")) {
            if (userTypeID == 1 || userTypeID == 2) {
                userId = userId + "[" + currentMemberShip.getGroup().getGroupName() + "]";
                pageMap.put("userId", userId);
                count = gdgxDao.selectZzajWgyIssueCount(pageMap);
            } else {
                count = gdgxDao.selectZzajIssueCount(pageMap);
            }

            if (count > 0) {
                pageMap.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                pageMap.put("pageSize", Integer.parseInt(rows));
                //网格员
                if (userTypeID == 1 || userTypeID == 2) {
                    pageMap.put("userId", userId);
                    list = gdgxDao.selectZzajWgyIssueByGroupId(pageMap);
                } else {
                    list = gdgxDao.selectZzajIssueByGroupId(pageMap);
                }
            }
        } else {
            //归档案件
            Map queryMap = new HashMap();
            queryMap.put("createTime", startDay);
            queryMap.put("endTime", endDay);
            queryMap.put("caseType", caseType);
            queryMap.put("issueSubject", issueSubject);
            //根据groupTypeId 是2(街道) 还得6 (委办局)为条件,过滤查询
            /*   if(groupTypeId=="2"||groupTypeId.equals("2")){*/
            queryMap.put("issueExecStreet", groupId);
            count = gdgxDao.selectGdgxIssueCountByGroupId(queryMap);
            if (count > 0) {
                queryMap.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                queryMap.put("pageSize", Integer.parseInt(rows));
                //网格员
                if (userTypeID == 1) {
                    list = gdgxDao.selectGdgxWgyIssueByGroupId(queryMap);
                } else {
                    list = gdgxDao.selectGdgxIssueByGroupId(queryMap);
                }
            }
        }
        /*}else if(groupTypeId=="6"||groupTypeId.equals("6")){
            //t_main_issue 表中有issue_exec_dept 字段,委办局联办,联办部门保存的名称,可能有多个委办局,例(wbj_lyp,wbj_nyp)需要分割
            queryMap.put("issueExecStreet",groupId);
            count = gdgxDao.selectGdgxIssueCountByGroupId(queryMap);
            if(count>0){
                queryMap.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                queryMap.put("pageSize", Integer.parseInt(rows));
                //网格员
                if(userTypeID==1){
                   // list=gdgxDao.selectGdgxIssueByDept(queryMap);
                    list=gdgxDao.selectGdgxWgyIssueByGroupId(queryMap);

                }else{
                    //list = gdgxDao.selectGdgxIssueBydept(queryMap);
                    list = gdgxDao.selectGdgxIssueByGroupId(queryMap);
                }
            }
        }else{
            count = gdgxDao.selectGdgxIssueCountByGroupId(queryMap);
            if(count>0){
                queryMap.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                queryMap.put("pageSize", Integer.parseInt(rows));
                //网格员
                if(userTypeID==1){
                    list = gdgxDao.selectGdgxWgyIssueByGroupId(queryMap);
                }else{
                    list = gdgxDao.selectGdgxIssueByGroupId(queryMap);
                }
            }
        }*/
        map.put("total", count);
        map.put("rows", list);

        return map;
    }

    @Override
    public List<Map<String, Object>> selecAttachList(String processId) {
        List<Map<String, Object>> list = gdgxDao.selecAttachList(processId);
        return list;
    }

    @Override
    public List<Map<String, Object>> selecDbAttachList(String processId) {
        List<Map<String, Object>> list = gdgxDao.selecDbAttachList(processId);
        return list;
    }

    @Override
    public List<Map<String, Object>> selecPtAttachList(String processId) {
        List<Map<String, Object>> list = gdgxDao.selecPtAttachList(processId);
        return list;
    }
}
