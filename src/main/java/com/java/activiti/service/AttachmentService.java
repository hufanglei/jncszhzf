package com.java.activiti.service;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.UploadFileData;
import org.springframework.web.multipart.MultipartFile;

import com.java.activiti.model.TAttachmentBean;

public interface AttachmentService {

	/**
	 * 根据代码名称获取代码信息
	 * @param dmmc 代码名称
	 * @return List，相应代码名称的代码信息
	 * @throws Exception 
	 */
	public int addNewAttachment(String rootDir,MultipartFile file,int mainIssueId,String userId,int type) throws Exception;


	/**
	 *
	 * @param rootDir
	 * @param mainIssueId
	 * @param userId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public int addNewAttachment(String rootDir, UploadFileData fileData, int mainIssueId, String userId, int type) throws Exception;


	public List<TAttachmentBean> getAttachmentByMainIssueId(Map<String, Object> map);
	
	public int deleteAttachment(int mainIssueId);
}
