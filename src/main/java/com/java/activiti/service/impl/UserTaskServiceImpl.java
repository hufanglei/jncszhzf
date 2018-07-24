package com.java.activiti.service.impl;

import com.java.activiti.dao.TMainIssueDao;
import com.java.activiti.dao.UserTaskMapper;
import com.java.activiti.model.CommentDetail;
import com.java.activiti.service.UserTaskService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class UserTaskServiceImpl implements UserTaskService {

    @Resource
    HistoryService historyService;
    @Resource
    UserTaskMapper userTaskDao;
    @Resource
    TMainIssueDao tMainIssueMapper;

    @Override
    public List<CommentDetail> getCommonDetailList(String taskId) {
        List<CommentDetail> commentDetailList = null;
        List<CommentDetail> result = new ArrayList<>();
        taskId = taskId==null?"":taskId;
        HistoricTaskInstance hti = historyService.createHistoricTaskInstanceQuery()
                .taskId(taskId)
                .singleResult();

        if (hti != null) {
            result = getCommentDetailByProcessId(hti.getProcessInstanceId());
        }

        return result;
    }

    @Override
    public List<CommentDetail> getCommentDetailByProcessId(String processId) {
        List<CommentDetail> commentDetailList;
        List<CommentDetail> result = new ArrayList<>();
        Map map = new HashMap();
        map.put("processInstanceId",processId);
        commentDetailList = userTaskDao.getCommonDetailList(map);
        // 集合元素反转
        Collections.reverse(commentDetailList);
        if(commentDetailList!=null && commentDetailList.size()>0){
            for(CommentDetail comment:commentDetailList){
                String userId = comment.getUserName();
                String userNames=userId.substring(0,userId.indexOf("["));
                Map map2 = new HashMap();
                map2.put("userId",userNames);
                HashMap<String,String> resultMap = userTaskDao.selectUserDepartment(map2);
                String department = resultMap.get("departmentName");
                String role = resultMap.get("roleName");
                comment.setDepartment(department);
                String userName = comment.getUserName();
                String userName2 = "";
                if(StringUtils.isNotBlank(userName)){
                    userName2 = userName.substring(0,userName.indexOf("["))+"["+role+"]";
                }
                comment.setUserName(userName2);
                result.add(comment);
            }
        }
        return result;
    }
    @Override
    public List<CommentDetail> getDbDetailList(String processId) {
        List<CommentDetail> commentDetailList;
        List<CommentDetail> result = new ArrayList<>();
        Map map = new HashMap();
        map.put("processInstanceId",processId);
        commentDetailList = userTaskDao.getDbDetailList(map);
        // 集合元素反转
        Collections.reverse(commentDetailList);
        if(commentDetailList!=null && commentDetailList.size()>0){
            for(CommentDetail comment:commentDetailList){
                String userId = comment.getUserName();
                Map map2 = new HashMap();
                map2.put("userId",userId);
                HashMap<String,String> resultMap = userTaskDao.selectUserDepartment(map2);
                String department = resultMap.get("departmentName");
                String role = resultMap.get("roleName");
                comment.setDepartment(department);
                String userName = comment.getUserName();
                String userName2 = "";
                if(StringUtils.isNotBlank(userName)){
                    userName2 = userName.substring(0,userName.indexOf("["))+"["+role+"]";
                }
                comment.setUserName(userName2);
                result.add(comment);
            }
        }
        return result;
    }
    @Override
    public List<CommentDetail> getPtCommentDetailByProcessId(String processId) {
        List<CommentDetail> commentDetailList;
        List<CommentDetail> result = new ArrayList<>();
        Map map = new HashMap();
        map.put("processInstanceId",processId);
        commentDetailList = userTaskDao.getPtCommonDetailList(map);
        // 集合元素反转
        Collections.reverse(commentDetailList);
        if(commentDetailList!=null && commentDetailList.size()>0){
            for(CommentDetail comment:commentDetailList){
                String userId = comment.getUserName();
                Map map2 = new HashMap();
                map2.put("userId",userId);
                HashMap<String,String> resultMap = userTaskDao.selectUserDepartment(map2);
                String department = resultMap.get("departmentName");
                String role = resultMap.get("roleName");
                comment.setDepartment(department);
                String userName = comment.getUserName();
                String userName2 = "";
                if(StringUtils.isNotBlank(userName)){
                    userName2 = userName.substring(0,userName.indexOf("["))+"["+role+"]";
                }
                comment.setUserName(userName2);
                result.add(comment);
            }
        }
        return result;
    }
}
