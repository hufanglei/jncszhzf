package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.TAttachmentBean;


public abstract interface TAttachmentDao {

	public abstract List<TAttachmentBean> selectTAttachmentByClause(Map<String, Object> map);

	public abstract TAttachmentBean selectTAttachmentByPk(int id);
	
	public abstract int selectTAttachmentCountByClause(Map<String, Object> map);

	public abstract int insertTAttachment(TAttachmentBean tAttachmentBean);

	public abstract int deleteTAttachment(int id);

	public abstract int updateTAttachment(TAttachmentBean tAttachmentBean);
	
	public abstract List<TAttachmentBean> selectTAttachmentByMainIssueId(Map<String, Object> map);

	public abstract int deleteAttachmentByMainIssueId(int id);
}
