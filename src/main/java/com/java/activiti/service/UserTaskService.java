package com.java.activiti.service;

import com.java.activiti.model.CommentDetail;

import java.util.List;

public interface UserTaskService {
    /**
     * 查询案件历史备注详情信息
     * @param taskId
     * @return
     */
    List<CommentDetail> getCommonDetailList(String taskId);

    /**
     * 查询案件历史备注详情信息
     * @param processId
     * @return
     */
    List<CommentDetail> getCommentDetailByProcessId(String processId);

    /**
     * 督办案件中查看普通案件历史批注
     * @param processId
     * @return
     */
    List<CommentDetail> getPtCommentDetailByProcessId(String processId);

    /**
     * 查询督办案件 历史批注
     * @param processId
     * @return
     */
    List<CommentDetail> getDbDetailList(String processId);
}
