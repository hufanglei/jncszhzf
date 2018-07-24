package com.java.activiti.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.java.activiti.model.UploadFileData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.java.activiti.dao.TAttachmentDao;
import com.java.activiti.model.TAttachmentBean;
import com.java.activiti.service.AttachmentService;
import com.java.activiti.util.Constants;

@Service("AttachmentServiceImpl")
public class AttachmentServiceImpl implements AttachmentService{
	
	@Resource
	private TAttachmentDao tAttachmentDao;

	/**
	 * 插入附件
	 */
	@Override
	public int addNewAttachment(String rootDir,MultipartFile file,int mainIssueId,String userId,int type) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			map = Constants.writeFile(rootDir, file);
		
			TAttachmentBean tAttachmentBean = new TAttachmentBean();
			tAttachmentBean.setMainIssueId(mainIssueId);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String ret = sdf.format(new Date());
			tAttachmentBean.setAddtime(ret);
			tAttachmentBean.setAddUserId(userId);
			tAttachmentBean.setName((String)map.get("annexName"));
			tAttachmentBean.setSize((String)map.get("filesize"));
			tAttachmentBean.setSavePath((String) map.get("saveAnnexName"));
			tAttachmentBean.setType(type);
			
			result =  tAttachmentDao.insertTAttachment(tAttachmentBean);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int addNewAttachment(String rootDir, UploadFileData fileData, int mainIssueId, String userId, int type) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			map = Constants.writeFile(rootDir, fileData);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TAttachmentBean tAttachmentBean = new TAttachmentBean();
		tAttachmentBean.setMainIssueId(mainIssueId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ret = sdf.format(new Date());
		tAttachmentBean.setAddtime(ret);
		tAttachmentBean.setAddUserId(userId);
		tAttachmentBean.setName((String)map.get("annexName"));
		tAttachmentBean.setSize((String)map.get("filesize"));
		tAttachmentBean.setSavePath((String) map.get("saveAnnexName"));
		tAttachmentBean.setType(type);

		result =  tAttachmentDao.insertTAttachment(tAttachmentBean);

		return result;
	}

	@Override
	public List<TAttachmentBean> getAttachmentByMainIssueId(Map<String, Object> map) {
		return tAttachmentDao.selectTAttachmentByMainIssueId(map);
	}

	@Override
	public int deleteAttachment(int mainIssueId) {
		return tAttachmentDao.deleteAttachmentByMainIssueId(mainIssueId);
	}
}
