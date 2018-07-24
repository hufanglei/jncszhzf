package com.java.activiti.controller;

import com.java.activiti.dao.TissueDcdbKhMapper;
import com.java.activiti.model.TissueDcdbKhBean;
import com.java.activiti.model.TissueJdjcKhBean;
import com.java.activiti.model.TissueXcjzKhBean;
import com.java.activiti.service.AttachmentService;
import com.java.activiti.service.TaskDbService;
import com.java.activiti.util.Constants;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.activiti.engine.*;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 督办案件流程任务（反复投诉的） Controller
 */
@Controller
@RequestMapping("/taskDb")
public class TaskDbController {
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
        JSONObject result = taskDbService.taskPage(page, rows, s_name, session);
//        return result.toString();
        ResponseUtil.write(response, result);
        return null;
    }




    /**
     * 根据taskId查询督办信息
     * @param response
     * @param taskId
     * @return
     * @throws Exception
     */
    @RequestMapping("/getDbIssueByTaskId")
    public String getIssueByTaskId(HttpServletResponse response, String taskId) throws Exception {
        //先根据流程ID查询
        if (Constants.isEmptyOrNull(taskId)) {
            return null;
        }
        JSONObject result = new JSONObject();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        TissueDcdbKhBean bean = tissueDcdbKhMapper.selectTMDbIssueByProcessId(task.getProcessInstanceId());
        result.put("tDbMainIssueBean", bean);
        ResponseUtil.write(response, result);
        return null;
    }



    /**
     * 考小组派发
     * @param taskId  任务id
     * @param qhzdId 组织机构代码（组的id）
     * @param state  状态
     * @param comment  评论
     * @param zgqx  整改期限 天数
     * @param tzdlx  通知单类型
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/khxzpf")
    @ResponseBody
    public Map  khxzpf(String taskId, String qhzdId, String state, String comment,
                       String zgqx, String tzdlx, HttpServletRequest request,HttpServletResponse response){
        System.out.println("============fsfdjkdhf=============");
        return taskDbService.khxzpf(taskId, qhzdId, comment, zgqx, tzdlx, request);
    }


    /**
     * 街道中心处理分发
     *
     * @param taskId                   String 任务id
     * @param comment                  string 批注信息
     * @param state                    String 处理状态 1 上报区级中心 2派发,3街道线下督查,4问题派遣
     * @param subdistrictDepartGroupID String 街道派发部门的groupID
     * @param gridOperatorName         String 网格员名字，如果是驳回，则这个字段为非空
     * @param response                 HttpServletResponse
     * @param request                  HttpServletRequest
     * @param session                  HttpSession
     * @return
     * @throws Exception
     */
    @RequestMapping("/subdistrictOfficeDistribute")
    public
    String subdistrictOfficeDistribute(String taskId, String comment, String state, String subdistrictDepartGroupID, String gridOperatorName, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        JSONObject result = taskDbService.subdistrictOfficeDistribute(taskId, comment, state, subdistrictDepartGroupID, gridOperatorName, session);
        ResponseUtil.write(response,result);
        return null;
    }



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
    @RequestMapping("/subdistrictOfficeHandle")
    public @ResponseBody
    String subdistrictOfficeHandle(String taskId, String comment, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        return taskDbService.subdistrictOfficeHandle(taskId, comment, response, (MultipartHttpServletRequest) request, session);
    }




    /**
     *  网格员处理
     * @param status
     * @param taskId
     * @param comment
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/wgycl")
    public
    String wgycl(String status, String taskId, String comment, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject obj = taskDbService.wgycl(taskId, comment, request);
        ResponseUtil.write(response,obj);
        return null;
    }


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
    @RequestMapping("/competentDepartmentHandle")
    public String competentDepartmentHandle(String taskId, String comment, Integer state, String zfzd, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        return taskDbService.competentDepartmentHandle(taskId, comment, state, zfzd, response, request, session);
    }


    /**
     * 执法部门处理
     *
     * @param taskId   任务ID
     * @param comment  批注
     * @param response 响应
     * @param request  请求
     * @param session  会话信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/competentDepartmentFiling")
    public
    String competentDepartmentFiling(String taskId, String comment,String myfileData, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        //参数检查
        taskId = taskId.trim();
        JSONObject result = new JSONObject();
        if (taskId == "" || taskId == null) {
            //参数检查
            result.put("failure", false);
            ResponseUtil.write(response, result);
            return null;
        }
        return taskDbService.competentDepartmentFiling(taskId, comment,myfileData, response, session, result);
    }


    /**
     * 巡查机制 考核小组 审核
     * @param bean
     * @return
     */
    @RequestMapping("/xcjzsh")
    public String xcjzsh(TissueXcjzKhBean bean, HttpServletResponse response) throws Exception {
        JSONObject result = new JSONObject();
        int row = taskDbService.xcjzsh(bean);
        if(row==1){
            result.put("success",true);
        }else{
            result.put("failure",false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 巡监督检查考核小组 审核
     * @param bean
     * @return
     */
    @RequestMapping("/jdjcsh")
    public String jdjcsh(TissueJdjcKhBean bean, HttpServletResponse response) throws Exception {
        JSONObject result = new JSONObject();
        int row = taskDbService.jdjcsh(bean);
        if(row==1){
            result.put("success",true);
        }else{
            result.put("failure",false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

}
