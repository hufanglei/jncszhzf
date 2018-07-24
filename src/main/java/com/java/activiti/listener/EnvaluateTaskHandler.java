package com.java.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class EnvaluateTaskHandler implements TaskListener {

	private static final long serialVersionUID = 1L;

	public void notify(DelegateTask delegateTask) {
		System.out.println("进入动态设定评论人监听器");

		//获取流程变量
//		if ((Integer)delegateTask.getVariable("isByDistrictCenter") != null && (String) delegateTask.getVariable("handleId") != null){
//			Integer isPassDistrictCenter = (Integer) delegateTask.getVariable("isByDistrictCenter");
//			String handleId = (String) delegateTask.getVariable("handleId");
//			if (isPassDistrictCenter == 1){
				//该事项经历了区级中心
//				delegateTask.addCandidateGroup(handleId);
				delegateTask.addCandidateGroup("districtCenter");
				System.out.println("经过区级中心处理，指定区级中心group");
//			}else{
//				delegateTask.addCandidateUser(handleId);
//				System.out.println("不经过区级中心，指定上报人");
//			}
//		}else{
//			TMainIssueBean tMainIssueBean = (TMainIssueBean) delegateTask.getVariable("TMainIssueBean");
//			delegateTask.addCandidateUser(tMainIssueBean.getAddUserid());
//			System.out.println("街道处理，直接指定上报人");
//		}
	}
}