package com.java.activiti.dao;

import com.java.activiti.model.TDcdbAttachment;

import java.util.List;

public interface TDcdbAttachmentMapper {

    /**
     * 插入附件
     * @param record
     * @return
     */
    int insert(TDcdbAttachment record);

    /**
     * 查询案件包含的id
     * @param issueId
     * @return
     */
    List<TDcdbAttachment> selectAttachentListByIssueId(String issueId);

    List<TDcdbAttachment> selectAttachmentByProccessId(String issueId);

}