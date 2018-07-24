package com.java.activiti.controller;

import com.java.activiti.dao.TDcdbAttachmentMapper;
import com.java.activiti.dao.TissueDcdbKhMapper;
import com.java.activiti.model.*;
import com.java.activiti.service.AttachmentService;
import com.java.activiti.service.TaskDbService;
import com.java.activiti.util.Const;
import com.java.activiti.util.Constants;
import com.java.activiti.util.ProcessInstanceConst;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.engine.*;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 督办案件任务 controller
 */
@Controller
@RequestMapping("/dbissueTask")
public class DbIssueTaskController {
    @Resource
    private ProcessEngine processEngine;

    // 引入activiti自带的Service接口
    @Resource
    private TaskService taskService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private FormService formService;

    @Resource
    private HistoryService historyService;

    @Resource
    private AttachmentService attachmentService;

    @Resource
    private TissueDcdbKhMapper tissueDcdbKhMapper;

    @Resource
    private TaskDbService taskDbService;

    @Resource
    private TDcdbAttachmentMapper tDcdbAttachmentMapper;

    /**
     * 待办流程分页查询
     *
     * @param response
     * @param page     当前页数
     * @param rows     每页显示页数
     * @param s_name   流程名称
     * @return
     * @throws Exception
     */
    @RequestMapping("/taskPage")
    public String taskPage(HttpServletResponse response, String page, String rows, String s_name, HttpSession session) throws Exception {
        JSONObject result = taskDbService.taskPage2(page, rows, s_name, session);
//        return result.toString();
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 查询流程下的附件
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/getAttachments/{taskId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<TDcdbAttachment>> getAttachments(@PathVariable String taskId) {
        return taskDbService.queryAttachments(taskId);
    }


    /*******************************以下为新的考核流程**************************************/

    /**
     * 区中心派发
     * @param taskId
     * @param comment
     * @param state
     * @param myfileData
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/districtDistribution")
    @ResponseBody
    public Map districtDistribution(String taskId, String comment, Integer state, String myfileData, HttpServletRequest request, HttpSession session) throws Exception {
        return taskDbService.districtDistribution(taskId, comment, state, myfileData, request, session);
    }

    /**
     *  考核组审核
     *
     * @param taskId  任务ID
     * @param comment 批注
     * @param status   状态，表示同意接收或者不同意接收，1表示同意接收，2表示不同意接收
     * @param request 请求
     * @param session 会话信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/examinationGroupReview")
    public @ResponseBody
    Map examinationGroupReview(String taskId, String comment, String status, String myfileData, HttpServletRequest request, HttpSession session) throws Exception {
        return taskDbService.examinationGroupReview(taskId, comment, status, myfileData, request, session);
    }



    /**
     * 考核组派发
     * @param taskId     String 任务id
     * @param comment    String 批注信息
     * @param state       Integer 处理状态 1 主管部门接收，
     * @param districtCenterDepartGroup 区级中心主管部门
     * @param subdistrictGroupID   派发目标街道的groupID
     * @param response
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/dbexaminationGroupDistribution")
    public @ResponseBody
    Map dbexaminationGroupDistribution(String taskId, String comment, String state,String myfileData, String districtCenterDepartGroup, String subdistrictGroupID, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        return taskDbService.dbexaminationGroupDistribution(taskId, comment, state, myfileData, districtCenterDepartGroup, subdistrictGroupID, response, request, session);
    }

    /**
     * 督办案件： 街道派发
     * @param taskId
     * @param comment
     * @param state
     * @param districtCenterDepartGroup
     * @param subdistrictDepartGroupID
     * @param response
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/dbStreetDistribution")
    public @ResponseBody
    Map dbStreetDistribution(String taskId, String comment, String state, String myfileData, String districtCenterDepartGroup, String subdistrictDepartGroupID, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        return taskDbService.dbStreetDistribution(taskId, comment, state,myfileData, districtCenterDepartGroup, subdistrictDepartGroupID, response, request, session);
    }


    /**
     *科室处理
     * @param taskId
     * @param comment
     * @param myfileData
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/dbkeshiDeal")
    @ResponseBody
    Map dbkeshiDeal(String taskId, String comment, String myfileData,HttpServletRequest request, HttpSession session) throws Exception{
        return  taskDbService.dbkeshiDeal(taskId, comment, myfileData, request, session);
    }




    /**
     * 督办案件： 主管部门派遣
     * @param taskId
     * @param comment
     * @param zfzd
     * @param myfileData
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/dbDeptDeal")
    @ResponseBody
    Map dbDeptDeal(String taskId, String comment, String zfzd, String myfileData, HttpServletRequest request, HttpSession session) throws Exception{
        return taskDbService.dbDeptDeal(taskId, comment, zfzd, myfileData, request, session);
    }

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
    @RequestMapping("/dblochus")
    @ResponseBody
    Map dblochus(String taskId, String comment, String myfileData, HttpServletRequest request, HttpSession session) throws Exception{
        return taskDbService.dblochus(taskId, comment, myfileData, request, session);
    }


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
    @RequestMapping("/dbexaminationGroupEvaluation")
    @ResponseBody
    Map dbexaminationGroupEvaluation(String taskId, String comment, String status, String score, HttpServletRequest request, HttpSession session) throws Exception{
        return  taskDbService.dbexaminationGroupEvaluation(taskId, comment, status, score, request, session);
    }

}
