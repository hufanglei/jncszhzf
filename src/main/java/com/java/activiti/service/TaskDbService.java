package com.java.activiti.service;

import com.java.activiti.model.TAttachmentBean;
import com.java.activiti.model.TDcdbAttachment;
import com.java.activiti.model.TissueJdjcKhBean;
import com.java.activiti.model.TissueXcjzKhBean;
import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface TaskDbService {

    /**
     * 考小组派发
     *
     * @param taskId  任务id
     * @param qhzdId  组织机构代码（组的id）
     * @param comment 评论
     * @param zgqx    整改期限 天数
     * @param tzdlx   通知单类型
     * @param request
     * @return
     */
    public Map khxzpf(String taskId, String qhzdId, String comment, String zgqx, String tzdlx, HttpServletRequest request);

    /**
     * 待办流程分页查询
     *
     * @param page   当前页数
     * @param rows   每页显示页数
     * @param s_name 流程名称
     * @return
     * @throws Exception
     */
    public JSONObject taskPage(String page, String rows, String s_name, HttpSession session);

    /**
     * 督办待办流程分页查询2
     *
     * @param page   当前页数
     * @param rows   每页显示页数
     * @param s_name 流程名称
     * @return
     * @throws Exception
     */
    public JSONObject taskPage2(String page, String rows, String s_name, HttpSession session);

    /**
     * 街道中心处理分发
     *
     * @param taskId                   String 任务id
     * @param comment                  string 批注信息
     * @param state                    String 处理状态 1 上报区级中心 2派发,3街道线下督查,4问题派遣
     * @param subdistrictDepartGroupID String 街道派发部门的groupID
     * @param gridOperatorName         String 网格员名字，如果是驳回，则这个字段为非空
     * @param session                  HttpSession
     * @return
     * @throws Exception
     */
    public JSONObject subdistrictOfficeDistribute(String taskId, String comment, String state, String subdistrictDepartGroupID, String gridOperatorName, HttpSession session);


    /**
     * 街道处理任务
     *
     * @param taskId   任务ID
     * @param comment  批注
     * @param response 响应
     * @param request  请求
     * @param session  会话信息
     * @return
     * @throws Exception
     */
    public String subdistrictOfficeHandle(String taskId, String comment, HttpServletResponse response, MultipartHttpServletRequest request, HttpSession session) throws Exception;


    /**
     * 网格员处理
     *
     * @param taskId
     * @param comment
     * @param request
     * @return
     * @throws Exception
     */
    public JSONObject wgycl(String taskId, String comment, HttpServletRequest request);


    /**
     * 主管部门派遣
     *
     * @param taskId   任务ID
     * @param comment  批注
     * @param state    状态，表示需要增加时限或者直接到评价1表示不需要增加时限，直接到评价，2表示需增加时限，到修改时限页面
     * @param response 响应
     * @param request  请求
     * @param session  会话信息
     * @return
     * @throws Exception
     */
    public String competentDepartmentHandle(String taskId, String comment, Integer state, String zfzd, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception;


    /**
     * 执法部门处理
     *
     * @param taskId   任务ID
     * @param comment  批注
     * @param response 响应
     * @param session  会话信息
     * @return
     * @throws Exception
     */
    public String competentDepartmentFiling(String taskId, String comment, String myfileData, HttpServletResponse response, HttpSession session, JSONObject result) throws Exception;


    /**
     * 巡查机制 考核小组 审核
     *
     * @param bean
     * @return
     */
    public int xcjzsh(TissueXcjzKhBean bean);

    /**
     * 监督检查考核小组 审核
     *
     * @param bean
     * @return
     */
    public int jdjcsh(TissueJdjcKhBean bean);

    /**
     * 查询附件
     *
     * @param taskId
     * @return
     */
    Map<String, List<TDcdbAttachment>> queryAttachments(String taskId);


    /*********************新考核流程*******************************/
    /**
     * 区中心派发
     * @param taskId 任务id
     * @param comment  评论
     * @param state    状态
     * @param myfileData 附件
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
   public Map districtDistribution(String taskId, String comment, Integer state, String myfileData, HttpServletRequest request, HttpSession session) throws Exception;

    /** 考核组审核
     * @param taskId
     * @param comment
     * @param status
     * @param myfileData
     * @param request
     * @param session
     * @return
     */
   public Map examinationGroupReview(String taskId, String comment, String status, String myfileData, HttpServletRequest request, HttpSession session) throws Exception;


    /**
     * 督办案件： 考核组派发
     * @param taskId
     * @param comment
     * @param state
     * @param districtCenterDepartGroup
     * @param subdistrictGroupID
     * @param response
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    public Map dbexaminationGroupDistribution(String taskId, String comment, String state,String myfileData, String districtCenterDepartGroup, String subdistrictGroupID, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception;


    /**
     *  督办案件 ： 街道派发
     * @param taskId
     * @param comment
     * @param state
     * @param districtCenterDepartGroup
     * @param subdistrictGroupID
     * @param response
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    Map dbStreetDistribution(String taskId, String comment, String state, String myfileData, String districtCenterDepartGroup, String subdistrictGroupID, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception;


    /**
     * 科室处理
     * @param taskId
     * @param comment
     * @param myfileData
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    Map dbkeshiDeal(String taskId, String comment, String myfileData,HttpServletRequest request, HttpSession session) throws Exception;


    /**
     * 主管部门派遣
     * @param taskId
     * @param comment
     * @param zfzd
     * @param myfileData
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    Map dbDeptDeal(String taskId, String comment, String zfzd, String myfileData, HttpServletRequest request, HttpSession session) throws Exception;

    /**
     * 执法中队处理
     * @param taskId
     * @param comment
     * @param myfileData
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    Map dblochus(String taskId, String comment, String myfileData, HttpServletRequest request, HttpSession session) throws Exception;

    /**
     * 考核组评价
     * @param taskId
     * @param comment
     * @param status
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    Map dbexaminationGroupEvaluation(String taskId, String comment, String status, String score, HttpServletRequest request, HttpSession session) throws Exception;

}