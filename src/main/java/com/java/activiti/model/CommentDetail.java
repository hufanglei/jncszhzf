package com.java.activiti.model;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 案件备注详情信息
 */
public class CommentDetail {

    String taskId;
    String processId;
    String userName;
    String department;
    String handleWay;
    String beginTime;
    String endTime;
    String consumTime;
    String comment;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getHandleWay() {
        return handleWay;
    }

    public void setHandleWay(String handleWay) {
        this.handleWay = handleWay;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getConsumTime() {
        if(StringUtils.isBlank(beginTime)|| StringUtils.isBlank(endTime)){
            return "";
        }
        try {
            Date beginDate;
            Date endDate;
            DateFormat df_parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            beginDate = df_parseDate.parse(beginTime);
            endDate= df_parseDate.parse(endTime);
            Long time = (endDate.getTime()-beginDate.getTime())/1000;
            String hour = (time/3600)+"";
            Long time2 = (time%3600);
            String min = (time2/60)+"";
            String sceond = (time2%60)+"";
            consumTime =(hour!=null && !hour.equals("0"))?(hour+"h:"+min+"m:"+sceond+"s"):(min+"m:"+sceond+"s");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return consumTime;
    }

    public void setConsumTime(String consumTime) {
        this.consumTime = consumTime;
    }
}
