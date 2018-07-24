package com.java.activiti.model.dto;

import com.java.activiti.util.Constants;

import java.io.Serializable;

public class TMainIssueDTO implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		private	Integer  id; // 自增主键
		private	String  issueNumber; // 受理编号
		private	String  addUserTel; // 上报人电话
		private	String  addUserid; // 上报人userID
		private	String  addTime; // 上报时间
		private	String  isMultiExector; // 是否联办
		private	String  issueExecDept; // 案件执行部门（联办多个部门ID用逗号隔开）
		private	String  issueStreet; // 案件执行街道
		private	String  belongWeibanjuId; // 权利事项归属委办局ID
		private	String  issueTime; // 案发时间
		private	String  issueSource; // 案件来源
		private	String  issueType; // 权力事项编码（问题类型）
		private	String  issueSubject; // 诉求主题
		private	String  issueDesc; // 诉求详情
		private	String  requestorType; // 当事人类型
		private	String  refCompanyName; // 涉事企业名称
		private	String  orgId; // 涉事企业组织机构代码
		private	String  requestorName; // 主要诉求人姓名
		private	String  requestorTel; // 主要诉求人电话
		private	String  requestorIdcard; // 证件号码
		private	String  requestorSex; // 主要诉求人性别（0未知，1男，2女）
		private	Integer  requestorAge; // 主要诉求人年龄
		private	String  requestorAddress; // 主要诉求人地址
		private	String  hasModLimit; // 是否增加过处理时限
		private	String  timeLimit; // 处理时限
		private	String  emrgencyLevel; // 紧急程度
		private	String  comment; // 备注
		private	String  endTime; // 结束时间
		private	String  x; // X坐标
		private	String  y; // Y坐标
		private	String  addrDesc; // 地址描述
		private	String  status; // 状态（具体值参见字典表）
		private	String  evaluationStatus; // 评价状态，1为满意，2为不满意
		private	Integer  evaluationLevel; // 评价打分
		private	String  d1; // 灯1状态
		private	String  processId; // 对应流程ID
		private	String  isArchived; // 是否归档
		private	String  whoReported; // 上报者
		private	String  isOpen; // 是否公开
		private	String  dealWay; // 处理方式
		private	String  isPassDistrictcenter = "0"; // 审批是否经过区级中心
		
		private	String  issueSource_ms; // 案件来源
		private	String  requestorType_ms; // 当事人类型
		private	String  timeLimit_ms; // 处理时限
		private	String  emrgencyLevel_ms; // 紧急程度
		private	String  status_ms; // 状态（具体值参见字典表）
		private	String  caseType; // 状态（具体值参见字典表）
	    private String  dbStatus;  //督办状态
	    private String  dbId;  //督办案件的id
		private String sourceId;
		private String dcdbnum;
		private String qhzdName;
		private String descript;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}		
		public String getIssueNumber() {
			return issueNumber;
		}
		public void setIssueNumber(String issueNumber) {
			this.issueNumber = issueNumber;
		}		
		public String getAddUserTel() {
			return addUserTel;
		}
		public void setAddUserTel(String addUserTel) {
			this.addUserTel = addUserTel;
		}		
		public String getAddUserid() {
			return addUserid;
		}
		public void setAddUserid(String addUserid) {
			this.addUserid = addUserid;
		}		
		public String getAddTime() {
			return addTime;
		}
		public void setAddTime(String addTime) {
			this.addTime = addTime;
		}		
		public String getIsMultiExector() {
			return isMultiExector;
		}
		public void setIsMultiExector(String isMultiExector) {
			this.isMultiExector = isMultiExector;
		}		
		public String getIssueExecDept() {
			return issueExecDept;
		}
		public void setIssueExecDept(String issueExecDept) {
			this.issueExecDept = issueExecDept;
		}		
		public String getBelongWeibanjuId() {
			return belongWeibanjuId;
		}
		public void setBelongWeibanjuId(String belongWeibanjuId) {
			this.belongWeibanjuId = belongWeibanjuId;
		}		
		public String getIssueTime() {
			return issueTime;
		}
		public void setIssueTime(String issueTime) {
			this.issueTime = issueTime;
		}		
		public String getIssueSource() {
			return issueSource;
		}
		public void setIssueSource(String issueSource) {
			this.issueSource = issueSource;
			this.issueSource_ms = Constants.getDictionaryValue("ajly_"+issueSource);
		}		
		public String getIssueType() {
			return issueType;
		}
		public void setIssueType(String issueType) {
			this.issueType = issueType;
		}		
		public String getIssueSubject() {
			return issueSubject;
		}
		public void setIssueSubject(String issueSubject) {
			this.issueSubject = issueSubject;
		}		
		public String getIssueDesc() {
			return issueDesc;
		}
		public void setIssueDesc(String issueDesc) {
			this.issueDesc = issueDesc;
		}		
		public String getRequestorType() {
			return requestorType;
		}
		public void setRequestorType(String requestorType) {
			this.requestorType = requestorType;
			this.requestorType_ms = Constants.getDictionaryValue("dsrlx_"+requestorType);
		}		
		public String getRefCompanyName() {
			return refCompanyName;
		}
		public void setRefCompanyName(String refCompanyName) {
			this.refCompanyName = refCompanyName;
		}		
		public String getOrgId() {
			return orgId;
		}
		public void setOrgId(String orgId) {
			this.orgId = orgId;
		}		
		public String getRequestorName() {
			return requestorName;
		}
		public void setRequestorName(String requestorName) {
			this.requestorName = requestorName;
		}		
		public String getRequestorTel() {
			return requestorTel;
		}
		public void setRequestorTel(String requestorTel) {
			this.requestorTel = requestorTel;
		}		
		public String getRequestorIdcard() {
			return requestorIdcard;
		}
		public void setRequestorIdcard(String requestorIdcard) {
			this.requestorIdcard = requestorIdcard;
		}		
		public String getRequestorSex() {
			return requestorSex;
		}
		public void setRequestorSex(String requestorSex) {
			this.requestorSex = requestorSex;
		}		
		public Integer getRequestorAge() {
			return requestorAge;
		}
		public void setRequestorAge(Integer requestorAge) {
			this.requestorAge = requestorAge;
		}		
		public String getRequestorAddress() {
			return requestorAddress;
		}
		public void setRequestorAddress(String requestorAddress) {
			this.requestorAddress = requestorAddress;
		}		
		public String getHasModLimit() {
			return hasModLimit;
		}
		public void setHasModLimit(String hasModLimit) {
			this.hasModLimit = hasModLimit;
		}		
		public String getTimeLimit() {
			return timeLimit;
		}
		public void setTimeLimit(String timeLimit) {
			this.timeLimit = timeLimit;
			this.timeLimit_ms = Constants.getDictionaryValue("czsx_"+timeLimit);
		}		
		public String getEmrgencyLevel() {
			return emrgencyLevel;
		}
		public void setEmrgencyLevel(String emrgencyLevel) {
			this.emrgencyLevel = emrgencyLevel;
			this.emrgencyLevel_ms = Constants.getDictionaryValue("jjcd_"+emrgencyLevel);
		}		
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}		
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}		
		public String getX() {
			return x;
		}
		public void setX(String x) {
			this.x = x;
		}		
		public String getY() {
			return y;
		}
		public void setY(String y) {
			this.y = y;
		}		
		public String getAddrDesc() {
			return addrDesc;
		}
		public void setAddrDesc(String addrDesc) {
			this.addrDesc = addrDesc;
		}		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
			this.status_ms = Constants.getDictionaryValue("statuscode_"+status);
		}		
		public String getEvaluationStatus() {
			return evaluationStatus;
		}
		public void setEvaluationStatus(String evaluationStatus) {
			this.evaluationStatus = evaluationStatus;
		}		
		public Integer getEvaluationLevel() {
			return evaluationLevel;
		}
		public void setEvaluationLevel(Integer evaluationLevel) {
			this.evaluationLevel = evaluationLevel;
		}		
		public String getD1() {
			return d1;
		}
		public void setD1(String d1) {
			this.d1 = d1;
		}		
		public String getProcessId() {
			return processId;
		}
		public void setProcessId(String processId) {
			this.processId = processId;
		}		
		public String getIsArchived() {
			return isArchived;
		}
		public void setIsArchived(String isArchived) {
			this.isArchived = isArchived;
		}
		public String getIsOpen() {
			return isOpen;
		}
		public void setIsOpen(String isOpen) {
			this.isOpen = isOpen;
		}
		public String getWhoReported() {
			return whoReported;
		}
		public void setWhoReported(String whoReported) {
			this.whoReported = whoReported;
		}
		public String getDealWay() {
			return dealWay;
		}
		public void setDealWay(String dealWay) {
			this.dealWay = dealWay;
		}
		public String getIsPassDistrictcenter() {
			return isPassDistrictcenter;
		}
		public void setIsPassDistrictcenter(String isPassDistrictcenter) {
			this.isPassDistrictcenter = isPassDistrictcenter;
		}
		public String getIssueSource_ms() {
			return issueSource_ms;
		}
		public String getRequestorType_ms() {
			return requestorType_ms;
		}
		public String getTimeLimit_ms() {
			return timeLimit_ms;
		}
		public String getEmrgencyLevel_ms() {
			return emrgencyLevel_ms;
		}
		public String getStatus_ms() {
			return status_ms;
		}

		public String getIssueStreet() {
			return issueStreet;
		}

		public void setIssueStreet(String issueStreet) {
			this.issueStreet = issueStreet;
		}

		public String getCaseType() {
			return caseType;
		}

		public void setCaseType(String caseType) {
			this.caseType = caseType;
		}

		public String getDbStatus() {
			return dbStatus;
		}

		public void setDbStatus(String dbStatus) {
			this.dbStatus = dbStatus;
		}

		public String getDbId() {
			return dbId;
		}

		public void setDbId(String dbId) {
			this.dbId = dbId;
		}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getDcdbnum() {
		return dcdbnum;
	}

	public void setDcdbnum(String dcdbnum) {
		this.dcdbnum = dcdbnum;
	}

	public String getQhzdName() {
		return qhzdName;
	}

	public void setQhzdName(String qhzdName) {
		this.qhzdName = qhzdName;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
}