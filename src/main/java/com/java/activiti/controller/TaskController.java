package com.java.activiti.controller;

import com.java.activiti.dao.*;
import com.java.activiti.model.*;
import com.java.activiti.service.*;
import com.java.activiti.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.activiti.engine.*;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 历史流程批注管理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/task")
public class TaskController {

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
    private IssueService issueService;
    @Resource
    private HistoryService historyService;
    @Resource
    private AttachmentService attachmentService;
    @Resource
    private GroupService groupService;
    @Resource
    private MemberShipService memberShipService;
    @Resource
    private UserTaskService userTaskService;

    @Resource
    private TMainIssueDao tMainIssueDao;

    @Resource
    private TissueLbbmMapper tissueLbbmMapper;
    @Resource
    private TissueRadioKhMapper tissueRadioKhMapper;
    @Resource
    private TMainIssueKhDao tMainIssueKhDao;
    @Resource
    private TMainIssueKhService tMainIssueKhService;
    @Resource
    private WorkdayDao workdayDao;
    @Resource
    private IssueDealMapper issueDealMapper;

    @Resource
    UserTaskMapper userTaskDao;

    @Resource
    TissueDcdbKhMapper tissueDcdbKhMapper;
    /**
     * 查询历史流程批注
     *
     * @param response
     * @param processInstanceId 流程ID
     * @return
     * @throws Exception 参数为：processInstanceId String 主流程ID
     *                   返回格式为：
     *                   {
     *                   {fullMessage:"[青山消防大队]该问题请职能部门按时限处理。",processInstanceId:"387637",taskId:"387648",time:"2017-08-31 11:41:28",userId:"xiaonengshi01[分发中心]"},
     *                   {fullMessage:"[青山消防大队]该问题请职能部门按时限处理。",processInstanceId:"387637",taskId:"387648",time:"2017-08-31 11:41:28",userId:"xiaonengshi01[分发中心]"},
     *                   {fullMessage:"[青山消防大队]该问题请职能部门按时限处理。",processInstanceId:"387637",taskId:"387648",time:"2017-08-31 11:41:28",userId:"xiaonengshi01[分发中心]"},
     *                   ...
     *                   }
     */
    @RequestMapping("/listHistoryCommentWithProcessInstanceId")
    public @ResponseBody
    String listHistoryCommentWithProcessInstanceId(
            HttpServletResponse response, String processInstanceId) throws Exception {
        if (processInstanceId == null) {
            return "";
        }
        List<Comment> commentList = taskService.getProcessInstanceComments(processInstanceId);
        // 改变顺序，按原顺序的反向顺序返回list
        Collections.reverse(commentList); //集合元素反转
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class,
                //时间格式转换
                new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONArray jsonArray = JSONArray.fromObject(commentList, jsonConfig);
        return jsonArray.toString();
    }

    /**
     * 重定向审核处理页面
     *
     * @param taskId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/redirectPage")
    public @ResponseBody
    String redirectPage(String taskId, HttpServletResponse response) throws Exception {
        TaskFormData formData = formService.getTaskFormData(taskId);
        String url = formData.getFormKey();
        JSONObject result = new JSONObject();
        result.put("url", url);
        return result.toString();
    }


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
        //根据会话信息获取当前登录账号
        System.out.println("=============fjdjskjhfjd============");
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户
        User currentUser = currentMemberShip.getUser();
        String userId = currentUser.getId();

        if (s_name == null) {
            s_name = "";
        }

        PageInfo pageInfo = new PageInfo();
        Integer pageSize = Integer.parseInt(rows);
        pageInfo.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        pageInfo.setPageIndex((Integer.parseInt(page) - 1) * pageInfo.getPageSize());
        // 获取总记录数
//        long total = taskService.createTaskQuery().processDefinitionKey(ProcessInstanceConst.KEY)
//                .taskCandidateUser(userId)
//                .taskNameLike("%" + s_name + "%")
//                .count(); // 获取总记录数
        //有想法的话，可以去数据库观察  ACT_RU_TASK 的变化
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(ProcessInstanceConst.KEY)
                // 根据用户id查询
                .taskCandidateUser(userId)
                // 根据任务名称查询
//                .taskNameLike("%" + s_name + "%")
                .taskNameLike(s_name + "%")
                //按照创建时间降序排列
                .orderByTaskCreateTime().desc()
                // 返回带分页的结果集合
                .listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
        //获取总记录数
        long total = taskList != null ? taskList.size() : 0;
        //这里需要使用一个工具类来转换一下主要是转成JSON格式
        List<MyTask> MyTaskList = new ArrayList<MyTask>();
        for (Task t : taskList) {
            MyTask myTask = new MyTask();
            myTask.setId(t.getId());
            myTask.setName(t.getName());
            myTask.setCreateTime(t.getCreateTime());

            //todo 网格员 科室 街道中心  待办任务 1. 没经过区中心 加创建时间加 2天 2. 经过区中心 以区中心派遣到街道开始计时 加2
            //todo 委办局 区中心派发给委办局开始计时加7  立案

            //获取主流程
            TMainIssueBean tMainIssueBeanTmp = (TMainIssueBean) taskService.getVariable(t.getId(), "TMainIssueBean");
            if (tMainIssueBeanTmp != null) {
                String subject= tMainIssueDao.getSubject(tMainIssueBeanTmp.getIssueSubject());
                // myTask.setSubject(tMainIssueBeanTmp.getIssueSubject());
                myTask.setSubject(subject);
                myTask.setIssueNumber(tMainIssueBeanTmp.getIssueNumber());
            } else {
            }

            MyTaskList.add(myTask);
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(MyTaskList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", total);
//        return result.toString();
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 查询当前流程图
     *
     * @return
     */
    @RequestMapping("/showCurrentView")
    public ModelAndView showCurrentView(HttpServletResponse response, String taskId) {
        //视图
        ModelAndView mav = new ModelAndView();

        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        // 获取流程定义id
        String processDefinitionId = task.getProcessDefinitionId();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery() // 创建流程定义查询
                // 根据流程定义id查询
                .processDefinitionId(processDefinitionId)
                .singleResult();
        // 部署id
        mav.addObject("deploymentId", processDefinition.getDeploymentId());
        mav.addObject("diagramResourceName", processDefinition.getDiagramResourceName()); // 图片资源文件名称

        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)
                repositoryService.getProcessDefinition(processDefinitionId);
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 根据流程实例id获取流程实例
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if (pi.getActivityId() != null) {
            String activityId = pi.getActivityId();
            // 根据活动id获取活动实例
            ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
            //整理好View视图返回到显示页面
            mav.addObject("x", activityImpl.getX()); // x坐标
            mav.addObject("y", activityImpl.getY()); // y坐标
            mav.addObject("width", activityImpl.getWidth()); // 宽度
            mav.addObject("height", activityImpl.getHeight()); // 高度
        }

        mav.setViewName("page/currentView");
        return mav;
    }

    /**
     * 根据流程ID查询当前流程图
     *
     * @return
     */
    @RequestMapping("/showCurrentViewByProcessID")
    public ModelAndView showCurrentViewByProcessID(HttpServletResponse response, String processInstanceId) {
        //视图
        ModelAndView mav = new ModelAndView();

        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0); // 创建任务查询;
        // 获取流程定义id
        String processDefinitionId = task.getProcessDefinitionId();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery() // 创建流程定义查询
                // 根据流程定义id查询
                .processDefinitionId(processDefinitionId)
                .singleResult();
        // 部署id
        mav.addObject("deploymentId", processDefinition.getDeploymentId());
        mav.addObject("diagramResourceName", processDefinition.getDiagramResourceName()); // 图片资源文件名称

        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)
                repositoryService.getProcessDefinition(processDefinitionId);
        // 根据流程实例id获取流程实例
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        if (pi.getActivityId() != null) {
            String activityId = pi.getActivityId();

            // 根据活动id获取活动实例
            ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
            //整理好View视图返回到显示页面
            mav.addObject("x", activityImpl.getX()); // x坐标
            mav.addObject("y", activityImpl.getY()); // y坐标
            mav.addObject("width", activityImpl.getWidth()); // 宽度
            mav.addObject("height", activityImpl.getHeight()); // 高度
        }

        mav.setViewName("page/currentView");
        return mav;
    }

    /**
     * 查询历史批注
     *
     * @param response
     * @param taskId   流程ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/listHistoryComment")
    public String listHistoryComment(HttpServletResponse response, String taskId) throws Exception {
        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<Comment> commentList = null;

        taskId = taskId==null?"":taskId;
        HistoricTaskInstance hti = historyService.createHistoricTaskInstanceQuery()
                .taskId(taskId)
                .singleResult();

        if (hti != null) {
            commentList = taskService.getProcessInstanceComments(hti.getProcessInstanceId());
            // 集合元素反转
            Collections.reverse(commentList);
            //日期格式转换
            jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        }
        JSONArray jsonArray = JSONArray.fromObject(commentList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 查询历史批注
     *
     * @param response
     * @param taskId   流程ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/listHistoryCommentDetal")
    public String listHistoryCommentDetal(HttpServletResponse response, String taskId) throws Exception {
        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<CommentDetail> commentDetailList = userTaskService.getCommonDetailList(taskId);

        JSONArray jsonArray = JSONArray.fromObject(commentDetailList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }
    /**
     * 查询历史批注
     *
     * @param response
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/getDepartment")

    public String getDepartment(HttpServletResponse response, String issueNumber) throws Exception {
        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        String userId=tMainIssueDao.getUserId(issueNumber);
        Map map2 = new HashMap();
        map2.put("userId",userId);
        HashMap<String,String> resultMap = userTaskDao.selectUserDepartment(map2);
        JSONArray jsonArray = JSONArray.fromObject(resultMap, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 查询历史批注
     *
     * @param response
     * @param processId 流程ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/listHistoryCommentByProcessId")
    public String listHistoryCommentByProcessId(HttpServletResponse response, String processId) throws Exception {
        if (processId == null) {
            processId = "";
        }

        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<Comment> commentList = taskService.getProcessInstanceComments(processId);
        // 集合元素反转
        Collections.reverse(commentList);

        //日期格式转换
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONArray jsonArray = JSONArray.fromObject(commentList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 查询历史批注
     *
     * @param response
     * @param processId 流程ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/listHistoryCommentDetailByProcessId")
    public String listHistoryCommentDetailByProcessId(HttpServletResponse response, String processId) throws Exception {
        if (processId == null) {
            processId = "";
        }

        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<CommentDetail> commentDetailList = userTaskService.getCommentDetailByProcessId(processId);

        JSONArray jsonArray = JSONArray.fromObject(commentDetailList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }
    /**
     * 督办案件查询历史批注
     *
     * @param response
     * @param processId 流程ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/listHistorydbDetailByProcessId")
    public String listHistorydbDetailByProcessId(HttpServletResponse response, String processId) throws Exception {
        if (processId == null) {
            processId = "";
        }

        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<CommentDetail> commentDetailList = userTaskService.getDbDetailList(processId);

        JSONArray jsonArray = JSONArray.fromObject(commentDetailList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }
    /**
     * 督办案件中查询普通案件历史批注
     *
     * @param response
     * @param processId 流程ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/listHistoryPtCommentDetailByProcessId")
    public String listHistoryPtCommentDetailByProcessId(HttpServletResponse response, String processId) throws Exception {
        if (processId == null) {
            processId = "";
        }

        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<CommentDetail> commentDetailList = userTaskService.getPtCommentDetailByProcessId(processId);

        JSONArray jsonArray = JSONArray.fromObject(commentDetailList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }
    /**
     * 查询督办案件上报人历史批注
     *
     * @param response
     * @param taskId   流程ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/getDbDepartment")
    public String getDbDepartment(HttpServletResponse response, String taskId) throws Exception {
        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        String userId=tissueDcdbKhMapper.getUserId(taskId);
        Map map2 = new HashMap();
        map2.put("userId",userId);
        HashMap<String,String> resultMap = userTaskDao.selectUserDepartment(map2);
        JSONArray jsonArray = JSONArray.fromObject(resultMap, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 录入立即 自处置
     *
     * @param taskId   任务ID
     * @param comment  批注
     * @param response 响应
     * @param request  请求
     * @param session  会话信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/handleSelf")
    public @ResponseBody
    Map handleSelf(String taskId, String comment, HttpServletResponse response, HttpServletRequest request, HttpSession session,String myfileData) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
       /* List<MultipartFile> files = multipartRequest.getFiles("myfileData");*/
        //参数检查
        taskId = taskId.trim();
        Map result = new HashMap();
        if (taskId == "" || taskId == null || comment == "" || comment == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TMainIssueBean tMainIssueBean = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        int mainIssueId = tMainIssueBean.getId();
        tMainIssueBean.setDealWay("1");//自处置
        //设置流程状态
        tMainIssueBean.setStatus(Const.STATUS_FINISH);
        tMainIssueBean.setZxczType("1");

        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        Group currentGroup = currentMemberShip.getGroup();

        //设置流程结束时间
        tMainIssueBean.setEndTime(currentTime);
        String execGroupId = groupService.getGroupMenuByUserId(currentUser.getId());
        String execGroupName = groupService.getGroupMenuByUserName(execGroupId);
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = (calendar.get(Calendar.MONTH) + 1) < 10 ? ("0" + (calendar.get(Calendar.MONTH) + 1)) : (calendar.get(Calendar.MONTH) + 1) + "";
        String khyf = year + "-" + month;
        tMainIssueBean.setIssueStreet(execGroupId);  // 事件归口 ：归街道办理
        tMainIssueBean.setDealWay(Const.DEAL_WAY_ZCL);//处理方式:
        tMainIssueBean.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//网格员自处置完,计时结束
        //计算时间完成的时间
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String  time=null;
        if(tMainIssueBean.getStartTime()==null){
            time =tMainIssueBean.getAddTime();
        }else{
            time =tMainIssueBean.getStartTime();
        }
        long from = simpleFormat.parse(time).getTime();
        long to = simpleFormat.parse(currentTime).getTime();
        DecimalFormat def = new DecimalFormat("0.00");
        double days = Double.parseDouble(def.format((to - from)/(1000 * 60 * 60 * 24)));
        tMainIssueBean.setActualTime(String.valueOf(days));
        issueService.updateMainIssue(tMainIssueBean);
        Map  map =new HashMap();
        map.put("khyf",khyf);
        map.put("id",execGroupId);
        TissueRadioKhBean tissueRadioKhBean = tissueRadioKhMapper.selectTissue(map);
        if (tissueRadioKhBean != null) {
            tissueRadioKhBean.setIssuenum(tissueRadioKhBean.getIssuenum() + 1);
            tissueRadioKhBean.setQhzdName(execGroupName);
            tissueRadioKhMapper.updateByPrimaryKey(tissueRadioKhBean);
        } else {
            TissueRadioKhBean tissueRadioKhBean1 = new TissueRadioKhBean();
            tissueRadioKhBean1.setQhzdId(execGroupId);
            tissueRadioKhBean1.setQhzdName(execGroupName);
            tissueRadioKhBean1.setIssuenum(1);
            tissueRadioKhBean1.setKhyf(khyf);
            tissueRadioKhMapper.insertSelective(tissueRadioKhBean1);
        }
        //将事件归口保存到中间表中便于查询
        TissueLbbmBean tissueLbbmBean=new TissueLbbmBean();
        tissueLbbmBean.setIssueNumber(tMainIssueBean.getIssueNumber());
        tissueLbbmBean.setIssueExec(execGroupId);
        String type=groupService.getTypeById(execGroupId);
        tissueLbbmBean.setExecType(type);
        tissueLbbmMapper.insert(tissueLbbmBean);

        //查询processId 和taskid 保存到issue_deal表中
        List<HashMap<String,Object>>  list1=issueService.selectProcessId(tMainIssueBean.getIssueNumber());
        System.out.println(list1);
        IssueDeal issueDeal=new IssueDeal();
            issueDeal.setIssueNumber(String.valueOf(list1.get(0).get("issue_number")));
            issueDeal.setActCommentId(taskId);
            issueDeal.setProcessId(String.valueOf(list1.get(0).get("PROC_INST_ID_")));
            issueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            issueDeal.setDealWay("已结束");
        issueDealMapper.insert(issueDeal);

        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        variables.put("TMainIssueBean", tMainIssueBean);
        variables.put("wgDealStatus", 1);
        taskService.complete(taskId, variables);

        //插入附件
        String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
        if (StringUtils.isNotBlank(myfileData)) {
            JSONArray jsonArray = JSONArray.fromObject(myfileData);
            Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
            for (UploadFileData file : list) {
                int addAttachmentResult = attachmentService.addNewAttachment(rootDir, file, mainIssueId, currentMemberShip.getUserId(), Integer.parseInt(Const.PT_FJTYPE_FIVE));
                if (addAttachmentResult <= 0) {
                    result.put("failure", false);
                    return result;
                }
            }
        }
       /* String rootDir = request.getSession().getServletContext().getRealPath("");

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                int addAttachmentResult = attachmentService.addNewAttachment(rootDir, file, mainIssueId, currentUser.getId(), 2);
                if (addAttachmentResult <= 0) {
                    result.put("failure", false);
                    return result;
                }
            }
        }*/

        result.put("success", true);
        return result;
    }


    /**
     * 网格员被派遣后的 处置
     *
     * @param taskId   任务ID
     * @param comment  批注
     * @param response 响应
     * @param request  请求
     * @param session  会话信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/gridHandle")
    @ResponseBody
    public Map gridHandle(String taskId, String comment, String status,HttpServletResponse response, HttpServletRequest request, HttpSession session,String myfileData ) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      //  List<MultipartFile> files = multipartRequest.getFiles("myfile");
        //参数检查
        int re = -1;
        JSONObject obj = new JSONObject();
        taskId = taskId.trim();
        Map result = new HashMap();
        if (taskId == "" || taskId == null || comment == "" || comment == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TMainIssueBean tMainIssueBean = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        int mainIssueId = tMainIssueBean.getId();
        //取得角色用户登入的session对象
        MemberShip tMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = tMemberShip.getUser();
        Group currentGroup = tMemberShip.getGroup();
        //计算时间完成的时间
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String  time=null;
        if(tMainIssueBean.getStartTime()==null){
            time =tMainIssueBean.getAddTime();
        }else{
            time =tMainIssueBean.getStartTime();
        }
        long from = simpleFormat.parse(time).getTime();
        long to = simpleFormat.parse(currentTime).getTime();
        DecimalFormat def = new DecimalFormat("0.00");
        double days = Double.parseDouble(def.format((to - from)/(1000 * 60 * 60 * 24)));

        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = (calendar.get(Calendar.MONTH) + 1) < 10 ? ("0" + (calendar.get(Calendar.MONTH) + 1)) : (calendar.get(Calendar.MONTH) + 1) + "";
        String khyf = year + "-" + month;
        //查询processId 和taskid 保存到issue_deal表中
        List<HashMap<String,Object>>  list1=issueService.selectProcessId(tMainIssueBean.getIssueNumber());
        System.out.println(list1);
        IssueDeal issueDeal=new IssueDeal();
        issueDeal.setIssueNumber(String.valueOf(list1.get(0).get("issue_number")));
        issueDeal.setActCommentId(taskId);
        issueDeal.setProcessId(String.valueOf(list1.get(0).get("PROC_INST_ID_")));
        issueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //上报还是自处理
        if (!Constants.isEmptyOrNull(status)) {
            if (status.equals("2")) {//自处置
                variables.put("wgDealStatus", 1);
                tMainIssueBean.setEndTime(currentTime);
                tMainIssueBean.setDealWay(Const.DEAL_WAY_ZCL);//自处理
                tMainIssueBean.setStatus(Const.STATUS_FINISH);
                tMainIssueBean.setZxczType("1");
                tMainIssueBean.setActualTime(String.valueOf(days));
                String execGroupId = groupService.getGroupMenuByUserId(currentUser.getId());
                String execGroupName = groupService.getGroupMenuByUserName(execGroupId);
                tMainIssueBean.setIssueStreet(execGroupId);  // 事件归口 ：归街道办理
                Map  map =new HashMap();
                map.put("khyf",khyf);
                map.put("id",execGroupId);
                TissueRadioKhBean tissueRadioKhBean = tissueRadioKhMapper.selectTissue(map);
                if (tissueRadioKhBean != null) {
                    tissueRadioKhBean.setIssuenum(tissueRadioKhBean.getIssuenum() + 1);
                    tissueRadioKhBean.setQhzdName(execGroupName);
                    tissueRadioKhMapper.updateByPrimaryKey(tissueRadioKhBean);
                } else {
                    TissueRadioKhBean tissueRadioKhBean1 = new TissueRadioKhBean();
                    tissueRadioKhBean1.setQhzdId(execGroupId);
                    tissueRadioKhBean1.setQhzdName(execGroupName);
                    tissueRadioKhBean1.setIssuenum(1);
                    tissueRadioKhBean1.setKhyf(khyf);
                    tissueRadioKhMapper.insertSelective(tissueRadioKhBean1);
                }
                //自处置保存事件归口
                TissueLbbmBean tissueLbbmBean=new TissueLbbmBean();
                tissueLbbmBean.setIssueNumber(tMainIssueBean.getIssueNumber());
                tissueLbbmBean.setIssueExec(execGroupId);
                String type=groupService.getTypeById(execGroupId);
                tissueLbbmBean.setExecType(type);
                tissueLbbmMapper.insert(tissueLbbmBean);

                //插入附件
                String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
                if (StringUtils.isNotBlank(myfileData)) {
                    JSONArray jsonArray = JSONArray.fromObject(myfileData);
                    Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
                    for (UploadFileData file : list) {
                        re = attachmentService.addNewAttachment(rootDir, file, mainIssueId, currentUser.getId(), Integer.parseInt(Const.PT_FJTYPE_FIVE));
                        if (re < 0) {
                            obj.put("result", re);
                            return obj;
                        }
                    }
                }
                issueDeal.setDealWay("已结束");
            } else if (status.equals("3")) {//网格员上报
                variables.put("wgDealStatus", 0);
                tMainIssueBean.setStatus(Const.STATUS_WAIT_SUBDISTRICT_DISTRIBUTE);//待街道派发
//                    Map map = new HashMap();
//                    map.put("orgno", tMemberShip.getUser().getOrgNumber().substring(0, 9) + "000");
                //根据网格员找到其所在街道的组id
                //MemberShip jiedaoMemberSip = memberShipService.getMemberShipByClase(map);
                String handleGroupId = memberShipService.getStreetGroupIdByUserId(tMemberShip.getUserId());
                variables.put("handleGroupId", handleGroupId);
                issueDeal.setDealWay("已上报");
            }
        }
        issueDealMapper.insert(issueDeal);
        issueService.updateMainIssue(tMainIssueBean);
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        variables.put("TMainIssueBean", tMainIssueBean);
        taskService.complete(taskId, variables);



        result.put("success", true);
        return result;
    }


    /**
     * 联办 网格员处理
     *
     * @param taskId   任务ID
     * @param comment  批注
     * @param response 响应
     * @param request  请求
     * @param session  会话信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/lbHandleSelf")
    public @ResponseBody
    Map lbHandleSelf(String taskId, String comment, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartRequest.getFiles("myfile");
        //参数检查
        taskId = taskId.trim();
        Map result = new HashMap();
        if (taskId == "" || taskId == null || comment == "" || comment == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TMainIssueBean tMainIssueBean = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        int mainIssueId = tMainIssueBean.getId();
//        tMainIssueBean.setDealWay("1");//自处置
//        //设置流程状态
//        tMainIssueBean.setStatus(Const.STATUS_FINISH);

        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        Group currentGroup = currentMemberShip.getGroup();

        //设置流程结束时间
       // tMainIssueBean.setEndTime(currentTime);
//        String execGroupId = groupService.getGroupMenuByUserId(currentUser.getId());
        //tMainIssueBean.setIssueStreet(execGroupId);  // 事件归口 ：归街道办理
       // tMainIssueBean.setDealWay(Const.DEAL_WAY_ZCL);//处理方式:
        //TODO 此处需要判断是否已经执行完毕  如果联办全部执行完毕了，那么需要修改状态 并且为等待评价
        //获取流程变量
        int nrOfInstances = (Integer) taskService.getVariable(taskId, "nrOfInstances"); //总联办数量
        int nrOfCompletedInstances = (Integer) taskService.getVariable(taskId, "nrOfCompletedInstances"); //已经完成的联办数量
        if (nrOfInstances - nrOfCompletedInstances == 1) {
            //表示已经完成
            //设置状态为待评价
            tMainIssueBean.setStatus(Const.STATUS_WAIT_COMMENT);
            tMainIssueBean.setDealWay(Const.DEAL_WAY_LBCL);
            //设置经过区级中心审批
            //tMainIssueBean.setIsPassDistrictcenter("1");
            tMainIssueBean.setIsMultiExector("1"); //联办标识：  1 待办是； 默认0 不是
        }
        issueService.updateMainIssue(tMainIssueBean);

        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        variables.put("TMainIssueBean", tMainIssueBean);
        taskService.complete(taskId, variables);

        //插入附件
        String rootDir = request.getSession().getServletContext().getRealPath("");
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                int addAttachmentResult = attachmentService.addNewAttachment(rootDir, file, mainIssueId, currentUser.getId(), 2);
                if (addAttachmentResult <= 0) {
                    result.put("failure", false);
                    return result;
                }
            }
        }

        result.put("success", true);
        return result;
    }


    /**
     * 社区处理结点
     *
     * @param taskId   任务ID status 2:自处置 3：上报 comment:批注内容
     * @param comment  批注
     * @param response 响应
     * @param request  请求
     * @param session  会话信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/communityHandle")
    public @ResponseBody
    String communityHandle(String status, String taskId, String comment, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        //参数检查
        taskId = taskId.trim();
        JSONObject result = new JSONObject();
        if (status == "" || status == null || taskId == "" || taskId == null || comment == "" || comment == null) {
            //参数检查
            result.put("failure", false);
            return result.toString();
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取主流程ID
        TMainIssueBean tMainIssueBean = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");

        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        if (status.equals("2")) {
            variables.put("isHandleSelf", "1");
            variables.put("userIds", tMainIssueBean.getAddUserid());
            tMainIssueBean.setStatus(Const.STATUS_HANDLE_SELF);//自处置
        } else if (status.equals("3")) {
            variables.put("isHandleSelf", "0");
            tMainIssueBean.setStatus(Const.STATUS_WAIT_SUBDISTRICT_DISTRIBUTE);//待街道派发


            Map map = new HashMap();
            map.put("orgno", currentMemberShip.getUser().getOrgNumber().substring(0, 9) + "000");//获取上级街道中心groupId
            MemberShip jiedaoMemberSip = memberShipService.getMemberShipByClase(map);

            variables.put("handleGroupId", jiedaoMemberSip.getGroupId());
            variables.put("userIds", currentMemberShip.getUserId());
        }

        issueService.updateMainIssue(tMainIssueBean);


        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        Group currentGroup = currentMemberShip.getGroup();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        variables.put("TMainIssueBean", tMainIssueBean);
        taskService.complete(taskId, variables);

        result.put("success", true);
        return result.toString();
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
    public @ResponseBody
    Map subdistrictOfficeDistribute(String taskId, String comment, String state, String subdistrictDepartGroupID, String gridOperatorName, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        Map result = new HashMap();
        if (taskId == "" || taskId == null || state == "" || state == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TMainIssueBean tMainIssueBeanSave = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        String mainIssueId = tMainIssueBeanSave.getId().toString();
        TMainIssueBean tMainIssueBean = issueService.findById(Integer.parseInt(mainIssueId));
        int stateCode = Integer.parseInt(state);
        //保存案件办理方式
        List<HashMap<String,Object>>  list=tMainIssueDao.selectProcessId(tMainIssueBean.getIssueNumber());
        IssueDeal issueDeal=new IssueDeal();
        issueDeal.setIssueNumber(String.valueOf(list.get(0).get("issue_number")));
        issueDeal.setActCommentId(taskId);
        issueDeal.setProcessId(String.valueOf(list.get(0).get("PROC_INST_ID_")));
        issueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        switch (stateCode) {
            case 1:
                //-------------处理待街道处理
                //参数检查
                if (subdistrictDepartGroupID == null || subdistrictDepartGroupID == "") {
                    //如果街道处理部门为空，则提示错误
                    return result;
                }
                //设置状态
                tMainIssueBean.setStatus(Const.STATUS_WAIT_SUBDISTRICT_HANDLE);

                //设置流程变量，设置为街道处理
                variables.put("streetHandleStatus", 1);
                variables.put("handleGroupId", subdistrictDepartGroupID);
                issueDeal.setDealWay("已派发");
                break;
            case 2:
                //----------上报区级中心
                //设置状态
                tMainIssueBean.setStatus(Const.STATUS_WAIT_DISTRICT_CENTER_DISTRIBUTE);
                //保存经过区级的字段值
                tMainIssueBean.setIsPassDistrictcenter("1");
                tMainIssueBean.setStartTime("");//案件由街道上报到区中心,案件计时清零
                //设置流程变量，设置为上报区级中心
                variables.put("streetHandleStatus", 2);
                issueDeal.setDealWay("已结束");
                break;
            case 3:
                //---转为街道线下督办
                //设置状态
                tMainIssueBean.setStatus(Const.STATUS_WAIT_SUBDISTRICT_HANDLE_OFFLINE);

                //设置流程变量
                variables.put("streetHandleStatus", 3);
                break;
            case 4:
                //-----问题派遣
                //参数检查
                if (gridOperatorName == null || gridOperatorName == "") {
                    //如果网格员为空，则表示驳回
                    gridOperatorName = tMainIssueBean.getAddUserid();
                }
                //设置状态
                tMainIssueBean.setStatus(Const.STATUS_WAIT_SUBDISTRICT_DISTRUBUTE_CASE_HANDLE);

                //设置流程变量
                variables.put("streetHandleStatus", 4);
                variables.put("userIds", gridOperatorName);
                issueDeal.setDealWay("已派遣");
                break;
            default:
                break;
        }
        //更新主流程表
        issueService.updateMainIssue(tMainIssueBean);
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        Group currentGroup = currentMemberShip.getGroup();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);

        issueDealMapper.insert(issueDeal);
       /*     String execGroupId = groupService.getGroupMenuByUserId(currentUser.getId());
            String execGroupName = groupService.getGroupMenuByUserName(execGroupId);
            Calendar calendar = Calendar.getInstance();
            String year = calendar.get(Calendar.YEAR) + "";
            String month = (calendar.get(Calendar.MONTH) + 1) < 10 ? ("0" + (calendar.get(Calendar.MONTH) + 1)) : (calendar.get(Calendar.MONTH) + 1) + "";
            String khyf = year + "-" + month;
            Map map = new HashMap();
            map.put("id", execGroupId);
            map.put("khyf", khyf);
            TissueRadioKhBean tissueRadioKhBean = tissueRadioKhMapper.selectTissue(map);
            if (tissueRadioKhBean != null) {
                tissueRadioKhBean.setIssuenum(tissueRadioKhBean.getIssuenum() + 1);
                tissueRadioKhBean.setQhzdName(execGroupName);
                tissueRadioKhMapper.updateByPrimaryKey(tissueRadioKhBean);
            } else {
                TissueRadioKhBean tissueRadioKhBean1 = new TissueRadioKhBean();
                tissueRadioKhBean1.setQhzdId(execGroupId);
                tissueRadioKhBean1.setQhzdName(execGroupName);
                tissueRadioKhBean1.setIssuenum(1);
                tissueRadioKhBean1.setKhyf(khyf);
                tissueRadioKhMapper.insertSelective(tissueRadioKhBean1);
            }*/

        result.put("success", true);
        return result;
    }

    /**
     * 联办 街道派发
     * @param taskId
     * @param comment
     * @param state
     * @param subdistrictDepartGroupID
     * @param gridOperatorName
     * @param response
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/lbSubdistrictOfficeDistribute")
    public @ResponseBody
    Map lbSubdistrictOfficeDistribute(String taskId, String comment, String state, String subdistrictDepartGroupID, String gridOperatorName, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        //流程实例id
        String executionId = null;
        int districtCenterHandleStatus = (int) taskService.getVariable(taskId, "districtCenterHandleStatus");

        Map result = new HashMap();
        if (taskId == "" || taskId == null || state == "" || state == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TMainIssueBean tMainIssueBeanSave = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        String mainIssueId = tMainIssueBeanSave.getId().toString();
        TMainIssueBean tMainIssueBean = issueService.findById(Integer.parseInt(mainIssueId));
        int stateCode = Integer.parseInt(state);
        //先执行一次流程
        executionId = taskService.createTaskQuery()
                .taskId(taskId).singleResult().getExecutionId();
        variables.put("handerStatus", 1);
        taskService.complete(taskId,variables);
        switch (stateCode) {
            case 1:
                //-------------处理待街道处理
                //参数检查
                if (subdistrictDepartGroupID == null || subdistrictDepartGroupID == "") {
                    //如果街道处理部门为空，则提示错误
                    return result;
                }
                //设置状态
                //tMainIssueBean.setStatus(Const.STATUS_WAIT_SUBDISTRICT_HANDLE);
                //设置流程变量，设置为街道处理
                variables.put("CooStreetHandleStatus", 1);
                variables.put("keshi", subdistrictDepartGroupID);
                break;
            case 4:
                //-----问题派遣
                //参数检查
                if (gridOperatorName == null || gridOperatorName == "") {
                    //如果网格员为空，则表示驳回
                    gridOperatorName = tMainIssueBean.getAddUserid();
                }
                //设置流程变量
                variables.put("CooStreetHandleStatus", 2);
                variables.put("wgy", gridOperatorName);
                break;
            default:
                break;
        }
        //更新主流程表
        issueService.updateMainIssue(tMainIssueBean);
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        Group currentGroup = currentMemberShip.getGroup();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        //如果是联办的话查询当前的taskId，因为已经执行过一步了
        if (districtCenterHandleStatus == 4) {
            //再次查询taskId
            taskId=  taskService.createTaskQuery().executionId(executionId).orderByTaskCreateTime().desc().list().get(0).getId();
        }
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        result.put("success", true);
        return result;
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
    Map subdistrictOfficeHandle(String taskId,String status, String comment, HttpServletResponse response, HttpServletRequest request, HttpSession session,String myfileData) throws Exception {
        //参数检查
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //List<MultipartFile> files = multipartRequest.getFiles("myfile");

        taskId = taskId.trim();
        //任务处理逻辑：根据前端页面提交的请求类型，直接提交处理意见comment
        Map result = new HashMap();
        if (taskId == "" || taskId == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TMainIssueBean tMainIssueBeanSave = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        String mainIssueId = tMainIssueBeanSave.getId().toString();
        TMainIssueBean tMainIssueBean = issueService.findById(Integer.parseInt(mainIssueId));
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        List<HashMap<String,Object>>  list1=tMainIssueDao.selectProcessId(tMainIssueBean.getIssueNumber());
        IssueDeal issueDeal=new IssueDeal();
        issueDeal.setIssueNumber(String.valueOf(list1.get(0).get("issue_number")));
        issueDeal.setActCommentId(taskId);
        issueDeal.setProcessId(String.valueOf(list1.get(0).get("PROC_INST_ID_")));
        issueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        switch (status){
            case "1":
                if(Const.J_QJ_NO.equals(tMainIssueBean.getIsPassDistrictcenter())){//没有经过区级中心
                    //科室处理完 直接归档
                    variables.put("jiedaoDealStatus", 1);
                    tMainIssueBean.setDealWay(Const.DEAL_WAY_JDKECL);//处理方式:街道科室处理
                    tMainIssueBean.setStatus(Const.STATUS_FINISH);
                    tMainIssueBean.setZxczType("1");
                    //获取当前时间
                    tMainIssueBean.setEndTime(currentTime);
                    //计算时间完成的时间
                    SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    String time=null;
                    if (tMainIssueBean.getStartTime()==null){
                         time = tMainIssueBean.getAddTime();
                    }else {
                        time=tMainIssueBean.getStartTime();
                    }
                    long from = simpleFormat.parse(time).getTime();
                    long to = simpleFormat.parse(currentTime).getTime();
                    DecimalFormat def = new DecimalFormat("#.00");
                    double days = Double.parseDouble(def.format((to - from)/(1000 * 60 * 60 * 24)));
                    tMainIssueBean.setActualTime(String.valueOf(days));
                    issueDeal.setDealWay("已结束");
                }else{  //经过区级中心
                    //科室处理完 ，区中心去评价
                    variables.put("jiedaoDealStatus", 2);
                    tMainIssueBean.setStatus(Const.STATUS_WAIT_COMMENT);
                    issueDeal.setDealWay("待评价");
                }
                String execGroupId = groupService.getGroupMenuByUserId(currentUser.getId());
                String execGroupName = groupService.getGroupMenuByUserName(execGroupId);
                Calendar calendar = Calendar.getInstance();
                String year = calendar.get(Calendar.YEAR) + "";
                String month = (calendar.get(Calendar.MONTH) + 1) < 10 ? ("0" + (calendar.get(Calendar.MONTH) + 1)) : (calendar.get(Calendar.MONTH) + 1) + "";
                String khyf = year + "-" + month;
                Map map = new HashMap();
                map.put("id", execGroupId);
                map.put("khyf", khyf);
                TissueRadioKhBean tissueRadioKhBean = tissueRadioKhMapper.selectTissue(map);
                if (tissueRadioKhBean != null) {
                    tissueRadioKhBean.setIssuenum(tissueRadioKhBean.getIssuenum() + 1);
                    tissueRadioKhBean.setQhzdName(execGroupName);
                    tissueRadioKhMapper.updateByPrimaryKey(tissueRadioKhBean);
                } else {
                    TissueRadioKhBean tissueRadioKhBean1 = new TissueRadioKhBean();
                    tissueRadioKhBean1.setQhzdId(execGroupId);
                    tissueRadioKhBean1.setQhzdName(execGroupName);
                    tissueRadioKhBean1.setIssuenum(1);
                    tissueRadioKhBean1.setKhyf(khyf);
                    tissueRadioKhMapper.insertSelective(tissueRadioKhBean1);
                }
                String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
                if (StringUtils.isNotBlank(myfileData)) {
                    JSONArray jsonArray = JSONArray.fromObject(myfileData);
                    Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
                    for (UploadFileData file : list) {
                        attachmentService.addNewAttachment(rootDir, file, Integer.parseInt(mainIssueId), currentMemberShip.getUserId(), Integer.parseInt(Const.PT_FJTYPE_FIVE));
                    }
                }
                break;

            case "2":
                //科室上报
                //根据街道科室id查询所在的街道组id
                String handleGroupId = memberShipService.getStreetGroupIdByUserId(currentUser.getId());
                variables.put("jiedaoDealStatus", 0);
                variables.put("handleGroupId", handleGroupId);
                tMainIssueBean.setStatus(Const.STATUS_WAIT_SUBDISTRICT_DISTRIBUTE);
                issueDeal.setDealWay("已上报");
                break;
            default:
                break;
        }
        issueDealMapper.insert(issueDeal);
        //上报事件经街道科室,保存科室事件归口
        String execGroupId = groupService.getGroupMenuByUserId(currentUser.getId());
        tMainIssueBean.setIssueStreet(execGroupId);  // 事件归口 ：归街道办理
        TissueLbbmBean tissueLbbmBean=new TissueLbbmBean();
        tissueLbbmBean.setIssueNumber(tMainIssueBean.getIssueNumber());
        tissueLbbmBean.setIssueExec(execGroupId);
        String type=groupService.getTypeById(execGroupId);
        tissueLbbmBean.setExecType(type);
        tissueLbbmMapper.insert(tissueLbbmBean);
        issueService.updateMainIssue(tMainIssueBean);

        Group currentGroup = currentMemberShip.getGroup();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        result.put("success", true);
        return result;
    }


    /**
     * 联办 街道科室处理任务
     *
     * @param taskId   任务ID
     * @param comment  批注
     * @param response 响应
     * @param request  请求
     * @param session  会话信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/lbSubdistrictOfficeHandle")
    public @ResponseBody
    Map lbSubdistrictOfficeHandle(String taskId, String comment, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        //参数检查
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartRequest.getFiles("myfile");

        taskId = taskId.trim();
        //任务处理逻辑：根据前端页面提交的请求类型，直接提交处理意见comment
        Map result = new HashMap();
        if (taskId == "" || taskId == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TMainIssueBean tMainIssueBeanSave = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        String mainIssueId = tMainIssueBeanSave.getId().toString();
        TMainIssueBean tMainIssueBean = issueService.findById(Integer.parseInt(mainIssueId));

        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        //更改状态
        // tMainIssueBean.setDealWay(Const.DEAL_WAY_JDKECL);//街道科室处理
//        if(!Const.J_QJ_YES.equals(tMainIssueBean.getIsPassDistrictcenter())){//没有经过区级中心
//            variables.put("isByDistrictCenter",0);
       // tMainIssueBean.setDealWay(Const.DEAL_WAY_JDKECL);//处理方式:街道科室处理
      //  tMainIssueBean.setStatus(Const.STATUS_FINISH);
        //TODO 此处需要判断是否已经执行完毕  如果联办全部执行完毕了，那么需要修改状态 并且为等待评价
        //获取流程变量
        int nrOfInstances = (Integer) taskService.getVariable(taskId, "nrOfInstances"); //总联办数量
        int nrOfCompletedInstances = (Integer) taskService.getVariable(taskId, "nrOfCompletedInstances"); //已经完成的联办数量
        if (nrOfInstances - nrOfCompletedInstances == 1) {
            //表示已经完成
            //设置状态为待评价
            tMainIssueBean.setStatus(Const.STATUS_WAIT_COMMENT);
            tMainIssueBean.setDealWay(Const.DEAL_WAY_LBCL);
            tMainIssueBean.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            //设置经过区级中心审批
            //tMainIssueBean.setIsPassDistrictcenter("1");
            tMainIssueBean.setIsMultiExector("1"); //联办标识：  1 待办是； 默认0 不是
            variables.put("allRreceiveStatus", 1); //区评价
        }

        //获取当前时间
      //  tMainIssueBean.setEndTime(currentTime);

       // String execGroupId = groupService.getGroupMenuByUserId(currentUser.getId());
        //tMainIssueBean.setIssueStreet(execGroupId);  // 事件归口 ：归街道办理
        issueService.updateMainIssue(tMainIssueBean);

        Group currentGroup = currentMemberShip.getGroup();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        result.put("success", true);
        return result;
    }


    /**
     * 查询流程正常走完的历史流程表 :  act_hi_actinst
     *
     * @param response
     * @param rows
     * @param page
     * @param s_name
     * @param groupId
     * @return
     * @throws Exception
     */
    @RequestMapping("/finishedList")
    public @ResponseBody
    String finishedList(HttpServletResponse response, String rows, String page, String s_name, String groupId, HttpSession session) throws Exception {
        //为什么要这样呢？因为程序首次运行进入后台时，
        //s_name必定是等于null的，如果直接这样填写进查询语句中就会出现  % null %这样就会导致查询结果有误
        if (s_name == null) {
            s_name = "";
        }
        PageInfo pageInfo = new PageInfo();
        Integer pageSize = Integer.parseInt(rows);
        pageInfo.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        pageInfo.setPageIndex((Integer.parseInt(page) - 1) * pageSize);

        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();

        //创建流程历史实例查询
        List<HistoricTaskInstance> histList = historyService.createHistoricTaskInstanceQuery()
                .taskCandidateUser(currentUser.getId())
                //.taskCandidateGroup(groupId)//根据角色名称查询
                .taskNameLike("%" + s_name + "%")
                .orderByTaskCreateTime().desc()
                .includeProcessVariables().finished()
                .listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());

        long histCount = historyService.createHistoricTaskInstanceQuery()
                .taskCandidateUser(currentUser.getId())
                //.taskCandidateGroup(groupId)
                .taskNameLike("%" + s_name + "%").finished()
                .count();

        List<MyTask> taskList = new ArrayList<MyTask>();
        //这里递出没有用的字段，免得给前端页面做成加载压力
        for (HistoricTaskInstance hti : histList) {
            MyTask myTask = new MyTask();
            myTask.setId(hti.getId());
            myTask.setName(hti.getName());
            myTask.setSubject((String) hti.getProcessVariables().get("issueSubject"));
            myTask.setIssueNumber((String) hti.getProcessVariables().get("issueNumber"));
            myTask.setCreateTime(hti.getCreateTime());
            myTask.setEndTime(hti.getEndTime());

            taskList.add(myTask);
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(taskList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", histCount);
        return result.toString();
    }

    /**
     * 根据任务id查询流程实例的具体执行过程
     *
     * @param taskId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/listAction")
    public @ResponseBody
    String listAction(String taskId, HttpServletResponse response) throws Exception {
        if (taskId == null) {
            taskId = "";
        }
        JSONObject result = new JSONObject();
        JSONArray jsonArray = null;
        HistoricTaskInstance hti = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        if (hti != null) {
            String processInstanceId = hti.getProcessInstanceId(); // 获取流程实例id
            List<HistoricActivityInstance> haiList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("userTask").list();
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
            jsonArray = JSONArray.fromObject(haiList, jsonConfig);
        }

        result.put("rows", jsonArray);
        return result.toString();
    }

    /**
     * 根据任务id查询流程实例的具体执行过程ByProcessId
     *
     * @param processId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/listActionByProcessId")
    public String listActionByProcessId(String processId, HttpServletResponse response) throws Exception {
        if (processId == null) {
            processId = "";
        }
        JSONObject result = new JSONObject();
        JSONArray jsonArray = null;
        List<HistoricActivityInstance> haiList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processId).activityType("userTask").list();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        jsonArray = JSONArray.fromObject(haiList, jsonConfig);

        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 区级中心处理派发
     *
     * @param taskId                         String 任务id
     * @param comment                        string 批注信息
     * @param state                          Integer 处理状态 1 主管部门接收， 2疑难案件处理，3区级中心驳回，4问题联办
     * @param districtCenterDepartGroup      String 区级中心主管部门
     * @param subdistrictGroupID             String 派发目标街道的groupID
     * @param difficultCaseDealDepartgroupID String 疑难案件处理部门名称
     * @param cooperationDeparts             String 联办委办局，逗号分隔
     * @param response                       HttpServletResponse
     * @param request                        HttpServletRequest
     * @param session                        HttpSession
     * @return
     * @throws Exception
     */
    @RequestMapping("/districtCenterDistribute")
    public @ResponseBody
    Map districtCenterDistribute(String taskId, String comment, String state, String districtCenterDepartGroup, String subdistrictGroupID, String difficultCaseDealDepartgroupID, String cooperationDeparts, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        Map result = new HashMap();
        if (taskId == "" || taskId == null || state == "" || state == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TMainIssueBean tMainIssueBeanSave = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        String mainIssueId = tMainIssueBeanSave.getId().toString();
        TMainIssueBean tMainIssueBean = issueService.findById(Integer.parseInt(mainIssueId));
        int stateCode = Integer.parseInt(state);
        List<HashMap<String,Object>>  list1=tMainIssueDao.selectProcessId(tMainIssueBean.getIssueNumber());
        IssueDeal issueDeal=new IssueDeal();
        issueDeal.setIssueNumber(String.valueOf(list1.get(0).get("issue_number")));
        issueDeal.setActCommentId(taskId);
        issueDeal.setProcessId(String.valueOf(list1.get(0).get("PROC_INST_ID_")));
        issueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        switch (stateCode) {
            case 1:
                //-------------派发到主管部门接收
                //参数检查
                if (districtCenterDepartGroup == null || districtCenterDepartGroup == "") {
                    //如果主管部门为空，则提示错误
                    result.put("failure", false);
                    return result;
                }
                //设置状态
                tMainIssueBean.setStatus(Const.STATUS_COMPETENT_DEPARTMENT_FIFLING);
                //区中心上报案件 ,派遣给委办局开始计时
                tMainIssueBean.setStartTime(new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

                //设置流程变量
                variables.put("districtCenterHandleStatus", 1);
                variables.put("handleGroupId", districtCenterDepartGroup);
                issueDeal.setDealWay("已派发");
                break;
            case 2:
                //----------疑难案件处理
                //设置状态
                tMainIssueBean.setStatus(Const.STATUS_WAIT_DIFFICULT_CASE_HANDLE);
                //tMainIssueBean.setZxczType("1");
                //设置流程变量，设置为疑难那件处理
                variables.put("districtCenterHandleStatus", 2);
                variables.put("handleGroupId", difficultCaseDealDepartgroupID);
                issueDeal.setDealWay("已提交");
                break;
            case 3:
                //---区级中心派遣
                //设置状态为待街道派发
                tMainIssueBean.setStatus(Const.STATUS_WAIT_SUBDISTRICT_DISTRIBUTE);
               // tMainIssueBean.setZxczType("1");
                //区中心派遣到街道开始计时 加 2  处置时限是两天
             /*   tMainIssueBean.setTimeLimit(String.valueOf(Const.TIME_LIMIT_TWO));*/
                //区中心上报的案件,派遣给街道计时开始
                tMainIssueBean.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
              /*  String createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                Date date = sdf.parse(createDate);
                Calendar cl = Calendar.getInstance();
                cl.setTime(date);
                cl.add(Calendar.DAY_OF_MONTH, 2);
                String temp = "";
                temp = sdf.format(cl.getTime());*/
/*                List  list=SetWeekDay.getEndTime(tMainIssueBean.getStartTime(),Const.TIME_LIMIT_TWO);*/
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                int c=0;
                int count = 0;
              /*  for(int i=0;i<list.size();i++){
                    System.out.println(list.get(i));
                    try {
                        count = workdayDao.selectWorkdayCountByWorkDay((String) list.get(i));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(count!=0){
                        c+=count;
                    }
                }*/
                //4.得出list中元素的在表中存在的总条数,在limitTime的基础上再加c,就得到案件实际结束的时间
                String  temp2="";
                Calendar c2 = Calendar.getInstance();
                Date date2 = null;
                try {
                    date2 = sdf.parse(tMainIssueBean.getStartTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c2.setTime(date2);
            /*    int total= Const.TIME_LIMIT_TWO+c;*/
                c2.add(Calendar.DAY_OF_MONTH,2);
                temp2 = sdf.format(c2.getTime());
                tMainIssueBean.setTimeLimit("2");
                tMainIssueBean.setTotalTime( temp2);
                //设置流程变量
                variables.put("districtCenterHandleStatus", 3);
                variables.put("handleGroupId", subdistrictGroupID);
                issueDeal.setDealWay("已派遣");
                break;
            case 4:
                //------问题联办
                //参数检查
                if (cooperationDeparts == null || cooperationDeparts.equals("")) {
                    result.put("failure", false);

                    return result;
                }
                issueDeal.setDealWay("已联办");
                //设置状态
                tMainIssueBean.setStatus(Const.STATUS_WAIT_COOPERATION);
                tMainIssueBean.setZxczType("3");//设置联办状态
                List<String> assigneeList = new ArrayList<String>(); //联办单位id列表

                String[] userList = cooperationDeparts.split(",");
               // Person person = null;
                for (int i = 0; i < userList.length; i++) {
                    assigneeList.add(userList[i]);
                    //联办时 保存事件归口

                    TissueLbbmBean tissueLbbmBean=new TissueLbbmBean();
                    tissueLbbmBean.setIssueNumber(tMainIssueBean.getIssueNumber());
                    tissueLbbmBean.setIssueExec(userList[i]);
                    String type=groupService.getTypeById(userList[i]);
                    tissueLbbmBean.setExecType(type);
                    tissueLbbmMapper.insert(tissueLbbmBean);
                }

                variables.put("persons", assigneeList);
//                variables.put("groups", groupMap);
                //设置流程变量
                variables.put("districtCenterHandleStatus", 4);
              //  comment = "[" + cooperationDeparts + "]" + comment;
                break;
            default:
                break;
        }
        //tMainIssueBean.setIsPassDistrictcenter(Const.J_QJ_YES);
        //更新主流程表
        issueDealMapper.insert(issueDeal);
        issueService.updateMainIssue(tMainIssueBean);
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        Group currentGroup = currentMemberShip.getGroup();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        result.put("success", true);
        return result;
    }


    /**
     * 主管部门接收任务+立案
     *
     * @param taskId  任务ID
     * @param comment 批注
     * @param state   状态，表示同意接收或者不同意接收，1表示同意接收，2表示不同意接收
     * @param request 请求
     * @param session 会话信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/competentDepartmentReceive")
    public @ResponseBody
    Map competentDepartmentReceive(String taskId, String comment, Integer state, String myfileData, HttpServletRequest request, HttpSession session) throws Exception {
        //流程实例id
        String executionId = null;
        //参数检查
        taskId = taskId.trim();
        Map result = new HashMap();
        if (taskId == "" || taskId == null || state == 0 || state == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TMainIssueBean tMainIssueBeanSave = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        String mainIssueId = tMainIssueBeanSave.getId().toString();
        TMainIssueBean tMainIssueBean = issueService.findById(Integer.parseInt(mainIssueId));
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        Group currentGroup = currentMemberShip.getGroup();

        int districtCenterHandleStatus = (int) taskService.getVariable(taskId, "districtCenterHandleStatus");
        //添加 案件办理方式
        List<HashMap<String,Object>>  list1=tMainIssueDao.selectProcessId(tMainIssueBean.getIssueNumber());
        IssueDeal issueDeal=new IssueDeal();
        issueDeal.setIssueNumber(String.valueOf(list1.get(0).get("issue_number")));
        issueDeal.setActCommentId(taskId);
        issueDeal.setProcessId(String.valueOf(list1.get(0).get("PROC_INST_ID_")));
        issueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //是否为联办
        if (districtCenterHandleStatus == 4) {

            //获取流程变量 //总联办数量
//            int nrOfInstances = (Integer) taskService.getVariable(taskId, "nrOfInstances");
//            //已经完成的联办数量
//            int nrOfCompletedInstances = (Integer) taskService.getVariable(taskId, "nrOfCompletedInstances");
//            //立案拒绝数量 存储变量
//            Object refuseCountObject = taskService.getVariable(taskId, "refuseCount");
//            int refuseCount = 0;
//            if (refuseCountObject != null) {
//                refuseCount = (int) refuseCountObject;
//            } else {
//                refuseCount = 0;
//            }
            //先走一次流程
            variables.put("handerStatus", 2);
             executionId = taskService.createTaskQuery()
                    .taskId(taskId).singleResult().getExecutionId();
            taskService.complete(taskId,variables);
//            //是否立案
//            if (state == 1) {
                //设置流程变量，设置为可以接收
//                variables.put("receiveStatus", 1);
                variables.put("handleGroupId", currentMemberShip.getGroupId());
                //保存联办的id
                String execDept = tMainIssueBean.getIssueExecDept();
                String execGroupId = groupService.getGroupMenuByUserId(currentUser.getId());
                if (StringUtil.isEmpty(execDept)) {
                    tMainIssueBean.setIssueExecDept(execGroupId);
                } else {
                    tMainIssueBean.setIssueExecDept(execDept + "," + execGroupId);
                }
//            } else {
//                if (refuseCount != 0) {
//                    refuseCount++;
//                } else {
//                    refuseCount = 1;
//                }
//                String person = (String) taskService.getVariable(taskId, "person");
//                List<String> assigneeList = (List<String>) taskService.getVariable(taskId, "persons");
//                assigneeList.remove(person);
//                variables.put("persons", assigneeList);
//                variables.put("refuseCount", refuseCount);
//            }
            //如果是联办的最后一个部门处理在处理
//            if (nrOfInstances - nrOfCompletedInstances == 1) {
////                //如果联办的都给拒绝了,回退到区中心从新派发
////                if (refuseCount == nrOfInstances) {
////                    //区中心从新派发
////                    tMainIssueBean.setStatus(Const.STATUS_WAIT_DISTRICT_CENTER_DISTRIBUTE);
////                    variables.put("allRreceiveStatus", 0);
////                    variables.put("refuseCount", 0); //重置
////                } else {
//                    //设置状态为待评价
//                    //tMainIssueBean.setStatus(Const.STATUS_WAIT_COMMENT);
//                    //设置经过区级中心审批
//                    tMainIssueBean.setIsPassDistrictcenter("1");
//                    tMainIssueBean.setIsMultiExector("1"); //联办标识：  1 待办是； 默认0 不是
//                   // variables.put("allRreceiveStatus", 1);
////                }
//            }

        } else {
            //接收任务，转到委办局处理
            if (state == 1) {
                //设置状态
                //tMainIssueBean.setStatus(Const.STATUS_COMPETENT_DEPARTMENT_FIFLING);
                tMainIssueBean.setStatus(Const.STATUS_COMPETENT_DEPARTMENT_PAIQIAN);
                String execGroupId = groupService.getGroupMenuByUserId(currentUser.getId());
                tMainIssueBean.setIssueExecDept(execGroupId);  // 事件归口 ：归非联办办理
                TissueLbbmBean tissueLbbmBean=new TissueLbbmBean();
                tissueLbbmBean.setIssueNumber(tMainIssueBean.getIssueNumber());
                tissueLbbmBean.setIssueExec(execGroupId);
                String type=groupService.getTypeById(execGroupId);
                tissueLbbmBean.setExecType(type);
                issueDeal.setDealWay("已立案");
                //设置处理时限  委办局 立案时开始计时  时限 7天
/*                tMainIssueBean.setTimeLimit(String.valueOf(Const.TIME_LIMIT_SEVEN));*/

               /* tMainIssueBean.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add(Calendar.DAY_OF_MONTH, 7);// 今天+7天
                Date newDate = c.getTime();*/
                //temp=SetWeekDay.getEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),Const.TIME_LIMIT_SEVEN);
        /*            List list = SetWeekDay.getEndTime(tMainIssueBean.getStartTime(), Const.TIME_LIMIT_SEVEN);*/
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                    int c = 0;
                    int count = 0;
                /*    for (int i = 0; i < list.size(); i++) {
                        System.out.println(list.get(i));
                        try {
                            count = workdayDao.selectWorkdayCountByWorkDay((String) list.get(i));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (count != 0) {
                            c += count;
                        }
                    }*/
                    //4.得出list中元素的在表中存在的总条数,在limitTime的基础上再加c,就得到案件实际结束的时间
                    String temp2 = "";
                    Calendar c2 = Calendar.getInstance();
                    Date date2 = null;
                    try {
                        date2 = sdf.parse(tMainIssueBean.getStartTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    c2.setTime(date2);
                 /*   int total = Const.TIME_LIMIT_SEVEN + c;*/
                    c2.add(Calendar.DAY_OF_MONTH, 7);
                    temp2 = sdf.format(c2.getTime());

                    tMainIssueBean.setTimeLimit("7");
                    tMainIssueBean.setTotalTime(temp2);
                tMainIssueBean.setZxczType("2");
                tissueLbbmMapper.insert(tissueLbbmBean);
                MemberShip memberShip = (MemberShip) request.getSession().getAttribute("currentMemberShip");
                //计算时间完成的时间
                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String time=null;
                if (tMainIssueBean.getStartTime()==null){
                    time = tMainIssueBean.getAddTime();
                }else {
                    time=tMainIssueBean.getStartTime();
                }
                long from = simpleFormat.parse(time).getTime();
                long to = simpleFormat.parse(currentTime).getTime();
                DecimalFormat def = new DecimalFormat("#.00");
                double days = Double.parseDouble(def.format((to - from)/(1000 * 60 * 60 * 24)));
                tMainIssueBean.setActualTime(String.valueOf(days));
                //设置流程变量，设置为可以接收
                variables.put("receiveStatus", 1);
                variables.put("handleGroupId", memberShip.getGroupId());
            } else if (state == 2) {
                //-------------拒绝接收任务，流转到区级部门问题分发
                //设置状态
                tMainIssueBean.setStatus(Const.STATUS_WAIT_DISTRICT_CENTER_DISTRIBUTE);
                //设置流程变量，设置有接受单位1
                variables.put("receiveStatus", 0);
                //回退
                issueDeal.setDealWay("已回退");
            }
        }

issueDealMapper.insert(issueDeal);
        issueService.updateMainIssue(tMainIssueBean);

        //处理附件插入
        //上传文件目录
        String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
        if (StringUtils.isNotBlank(myfileData)) {
            JSONArray jsonArray = JSONArray.fromObject(myfileData);
            Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
            for (UploadFileData file : list) {
                int res = attachmentService.addNewAttachment(rootDir, file, Integer.parseInt(mainIssueId), tMainIssueBean.getAddUserid(), 4);
            }
        }

        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        //如果是联办的话查询当前的taskId，因为已经执行过一步了
        if (districtCenterHandleStatus == 4) {
            //再次查询taskId
            taskId=  taskService.createTaskQuery().executionId(executionId).orderByTaskCreateTime().desc().list().get(0).getId();
        }
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        result.put("success", true);
        return result;
    }

    /**
     * 主管部门派遣
     *
     * @param taskId  任务ID
     * @param comment 批注
     * @param state   状态，表示需要增加时限或者直接到评价1表示不需要增加时限，直接到评价，2表示需增加时限，到修改时限页面
     * @param request 请求
     * @param session 会话信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/competentDepartmentHandle")
    public @ResponseBody
    Map competentDepartmentHandle(String taskId, String comment, Integer state, String zfzd, String myfileData, HttpServletRequest request, HttpSession session) throws Exception {
        int res = -1;
        //委办局选择的执法中队人员ID
        System.out.println("TODO:taskController.competentDepartmentHandle() ------ 委办局选择的执法中队人员ID  zfzd " + zfzd);

        taskId = taskId.trim();
        //任务处理逻辑：根据前端页面提交的请求类型，如果是可以处理，直接提交处理意见comment，如果需要修改时限，提交到效能室修改处理时限
        Map result = new HashMap();
        if (taskId == "" || taskId == null || state == 0 || state == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TMainIssueBean tMainIssueBeanSave = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        Integer mainIssueId = tMainIssueBeanSave.getId();
        TMainIssueBean tMainIssueBean = issueService.findById(mainIssueId);

        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        //添加 案件办理方式
        List<HashMap<String,Object>>  list1=tMainIssueDao.selectProcessId(tMainIssueBean.getIssueNumber());
        IssueDeal issueDeal=new IssueDeal();
        issueDeal.setIssueNumber(String.valueOf(list1.get(0).get("issue_number")));
        issueDeal.setActCommentId(taskId);
        issueDeal.setProcessId(String.valueOf(list1.get(0).get("PROC_INST_ID_")));
        issueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        //## 处理任务，不增加处理时限，转到用户评价流程
        //处理任务，不增加处理时限，转到部门派遣
        if (state == 1) {
            //设置状态为待评价
            //"districtCenterHandleStatus";
            int districtCenterHandleStatus = (int) taskService.getVariable(taskId, "districtCenterHandleStatus");
            if (districtCenterHandleStatus == 4) {//是否为联办
                tMainIssueBean.setStatus(Const.STATUS_WAIT_COOPERATION);//继续待联办
            } else {
                tMainIssueBean.setStatus(Const.STATUS_COMPETENT_DEPARTMENT_HANDLE);//待执法中队处理
            }

//            //判断该条任务是否经过区级中心，如果经过区级中心，评价人设置为区级中心，如果没有经过区级中心，则评价人设置为上报人
//            String isByDistrictCenter = tMainIssueBean.getIsPassDistrictcenter();
//
//            if (isByDistrictCenter.equals("1")) {
//                String groupId = "districtCenter";
//                variables.put("isByDistrictCenter", 1);
//                variables.put("handleId", groupId);
//            } else {
//                //不经过区级中心
//                variables.put("isByDistrictCenter", 0);
//                String userIds = tMainIssueBean.getAddUserid();
//                variables.put("handleId", userIds);
//            }

            //设置流程变量，设置为不增加处理时限
            variables.put("modTimeLimitStatus", 0);
            variables.put("ZFGroupId", zfzd);

            //上传文件目录
            String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
            if (StringUtils.isNotBlank(myfileData)) {
                JSONArray jsonArray = JSONArray.fromObject(myfileData);
                Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
                for (UploadFileData file : list) {
                    res = attachmentService.addNewAttachment(rootDir, file, mainIssueId, tMainIssueBean.getAddUserid(), Integer.parseInt(Const.PT_FJTYPE_TWO));
                    if (res < 0) {
                        result.put("result", res);
                        return result;
                    }
                }
            }
            issueDeal.setDealWay("已派遣");
        } else if (state == 2) {
            //-------------无法按时完成，转到增加处理时限
            //设置状态
            tMainIssueBean.setStatus(Const.STATUS_WAIT_MOD_TIME_LIMIT);
            //设置流程变量，设置有接受单位1
            variables.put("modTimeLimitStatus", 1);
        }
        issueDealMapper.insert(issueDeal);
        //设置经过区级中心审批
        //tMainIssueBean.setIsPassDistrictcenter("1");
        issueService.updateMainIssue(tMainIssueBean);

        Group currentGroup = currentMemberShip.getGroup();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        result.put("success", true);
        return result;
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
    public @ResponseBody
    Map competentDepartmentFiling(String taskId, String comment, String myfileData, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        //参数检查
        taskId = taskId.trim();
        Map result = new HashMap();
        if (taskId == "" || taskId == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取主流程ID
        TMainIssueBean tMainIssueBeanSave = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        String mainIssueId = tMainIssueBeanSave.getId().toString();
        TMainIssueBean tMainIssueBean = issueService.findById(Integer.parseInt(mainIssueId));
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        //添加 案件办理方式
        List<HashMap<String,Object>>  list1=tMainIssueDao.selectProcessId(tMainIssueBean.getIssueNumber());
        IssueDeal issueDeal=new IssueDeal();
        issueDeal.setIssueNumber(String.valueOf(list1.get(0).get("issue_number")));
        issueDeal.setActCommentId(taskId);
        issueDeal.setProcessId(String.valueOf(list1.get(0).get("PROC_INST_ID_")));
        issueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //设置状态
        int districtCenterHandleStatus = (int) taskService.getVariable(taskId, "districtCenterHandleStatus");
        if (districtCenterHandleStatus == 4) {//是否为联办
            //获取流程变量
            int nrOfInstances = (Integer) taskService.getVariable(taskId, "nrOfInstances"); //总联办数量
            int nrOfCompletedInstances = (Integer) taskService.getVariable(taskId, "nrOfCompletedInstances"); //已经完成的联办数量
            if (nrOfInstances - nrOfCompletedInstances == 1) {
                //表示已经完成
                //设置状态为待评价
                tMainIssueBean.setStatus(Const.STATUS_WAIT_COMMENT);
                tMainIssueBean.setDealWay(Const.DEAL_WAY_LBCL);
                //设置经过区级中心审批
                //tMainIssueBean.setIsPassDistrictcenter("1");
                tMainIssueBean.setIsMultiExector("1"); //联办标识：  1 待办是； 默认0 不是
                variables.put("allRreceiveStatus", 1); //区评价
                //上传文件目录
                String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
                if (StringUtils.isNotBlank(myfileData)) {
                    JSONArray jsonArray = JSONArray.fromObject(myfileData);
                    Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
                    for (UploadFileData file : list) {
                        attachmentService.addNewAttachment(rootDir, file, Integer.parseInt(mainIssueId), currentMemberShip.getUserId(), Integer.parseInt(Const.PT_FJTYPE_TWO));
                    }
                }
            }
        } else {
            tMainIssueBean.setStatus(Const.STATUS_WAIT_COMMENT);
            tMainIssueBean.setDealWay(Const.DEAL_WAY_WBJCL);
            tMainIssueBean.setIsMultiExector("0");
            issueDeal.setDealWay("待评价");
            //权利事项归属委办局ID 赋值
//            tMainIssueBean.setBelongWeibanjuId(currentMemberShip.getGroupId());
//            tMainIssueBean.setIssueExecDept(currentMemberShip.getGroupId());
            String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
            if (StringUtils.isNotBlank(myfileData)) {
                JSONArray jsonArray = JSONArray.fromObject(myfileData);
                Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
                for (UploadFileData file : list) {
                    attachmentService.addNewAttachment(rootDir, file, Integer.parseInt(mainIssueId), currentMemberShip.getUserId(), Integer.parseInt(Const.PT_FJTYPE_FIVE));
                }
            }
        }

        User currentUser = currentMemberShip.getUser();
        Group currentGroup = currentMemberShip.getGroup();

        issueDealMapper.insert(issueDeal);
        issueService.updateMainIssue(tMainIssueBean);



        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        result.put("success", true);
        return result;
    }


    /**
     * 区级中心修改事项处理时限
     *
     * @param taskId       任务ID
     * @param comment      批注
     * @param state        状态，表示同意接收或者不同意接收
     * @param newTimeLimit 想修改后的处理时限
     * @param response     响应
     * @param request      请求
     * @param session      会话信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/modifyTimeLimit")
    public @ResponseBody
    String modifyTimeLimit(String taskId, String comment, Integer state, String newTimeLimit, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        //参数检查
        taskId = taskId.trim();
        //修改处理时限逻辑：根据前端页面提交的请求类型，如果是增加修改时限，更新主流程表中的处理时限值，如不增加，任务继续流转
        JSONObject result = new JSONObject();
        if (taskId == "" || taskId == null || state == 0 || state == null) {
            //参数检查
            result.put("failure", false);
            return result.toString();
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取主流程ID
        TMainIssueBean tMainIssueBeanSave = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        String mainIssueId = tMainIssueBeanSave.getId().toString();
        TMainIssueBean tMainIssueBean = issueService.findById(Integer.parseInt(mainIssueId));

        //处理任务，确认增加时限
        if (state == 1) {
            //参数检查
            if (newTimeLimit == null || newTimeLimit == "") {
                //如果确认修改，修改后的处理时限小于等于0，则报出错误
                result.put("failure", false);
                return result.toString();
            }
            //设置已修改时限标记位为1
            tMainIssueBean.setHasModLimit(Const.HAS_MOD_TIME_LIMIT);
            //设置新的处理时限
            tMainIssueBean.setTimeLimit(newTimeLimit);

        }
        //设置状态为待处理
        tMainIssueBean.setStatus(Const.STATUS_COMPETENT_DEPARTMENT_HANDLE);
        //更新主流程信息
        issueService.updateMainIssue(tMainIssueBean);
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        Group currentGroup = currentMemberShip.getGroup();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        result.put("success", true);
        return result.toString();
    }

    /**
     * 评价
     *
     * @param taskId          任务ID
     * @param comment         批注
     * @param evaluationLevel 评论等级
     * @param response        响应
     * @param request         请求
     * @param session         会话信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/evaluation")
    public @ResponseBody
    Map evaluation(String taskId, String comment, Integer evaluationLevel, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        //参数检查
        //参数检查
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartRequest.getFiles("myfile");

        taskId = taskId.trim();
        //处理评价流程
        JSONObject result = new JSONObject();
        if (taskId == "" || taskId == null || comment == "" || comment == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }
        if (Constants.isEmptyOrNull(evaluationLevel)) {
            //参数检查
            result.put("failure", false);
            return result;
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程
        TMainIssueBean tMainIssueBean = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        String mainIssueId = tMainIssueBean.getId().toString();
        tMainIssueBean.setEvaluationLevel(evaluationLevel);
        //处理任务，满意
        List<HashMap<String,Object>>  list1=tMainIssueDao.selectProcessId(tMainIssueBean.getIssueNumber());
        IssueDeal issueDeal=new IssueDeal();
        issueDeal.setIssueNumber(String.valueOf(list1.get(0).get("issue_number")));
        issueDeal.setActCommentId(taskId);
        issueDeal.setProcessId(String.valueOf(list1.get(0).get("PROC_INST_ID_")));
        issueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (evaluationLevel >= 6) {
            //设置状态为结束
            tMainIssueBean.setStatus(Const.STATUS_FINISH);
            tMainIssueBean.setEndTime(currentTime);

            //设置评价内容
            if (tMainIssueBean.getWhoReported().equals("2")) {//区级中心上报
                if (!Constants.isEmptyOrNull(tMainIssueBean.getIsMultiExector())) {//联办标志不为空
                    if (tMainIssueBean.getIsMultiExector().equals("1")) {
                        tMainIssueBean.setDealWay("6");//联办处理
                    } else {
                        tMainIssueBean.setDealWay("4");//主管部门处理
                    }
                }
            } else {
                tMainIssueBean.setDealWay("2");//街道处理
            }
            //设置流程变量，设置为评价满意
            variables.put("solvedStatus", 1);
            issueDeal.setDealWay("已结束");
        } else if (evaluationLevel < 6) {
            //-------------评价不满意
            //不满意星级设为0
//            if (tMainIssueBean.getWhoReported() != null && tMainIssueBean.getWhoReported().equals("2")) {//区级中心上报的由区级中心评价
            if (tMainIssueBean.getWhoReported() != null) {//区级中心上报的由区级中心评价
                //设置流程状态
                tMainIssueBean.setStatus(Const.STATUS_WAIT_DISTRICT_CENTER_HANDLE_OFFLINE);
                //设置流程变量，设置为评价满意
                variables.put("solvedStatus", 3);
//            } else {//街道线下督察
//                //设置流程状态
//                tMainIssueBean.setStatus(Const.STATUS_WAIT_SUBDISTRICT_HANDLE_OFFLINE);
//                //设置流程变量，设置为评价满意
//                variables.put("solvedStatus", 2);
            }
            issueDeal.setDealWay("待线下督查");

        }
        issueDealMapper.insert(issueDeal);
        issueService.updateMainIssue(tMainIssueBean);

        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();

        String rootDir = request.getSession().getServletContext().getRealPath("");

        //插入附件
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {

                int addAttachmentResult = attachmentService.addNewAttachment(rootDir, file, Integer.parseInt(mainIssueId), currentUser.getId(), 3);
                if (addAttachmentResult <= 0) {
                    result.put("failure", false);
                    return result;
                }
            }
        }

        Group currentGroup = currentMemberShip.getGroup();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        result.put("success", true);
        return result;
    }


    /**
     * 督办流程
     *
     * @param taskId   任务ID
     * @param comment  批注
     * @param response 响应
     * @param request  请求
     * @param session  会话信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/supervisionOffLine")
    public @ResponseBody
    Map supervisionOffLine(String taskId, String comment,String myfileData, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartRequest.getFiles("myfile");
        //参数检查
        taskId = taskId.trim();
        //处理线下督办流程
        Map result = new HashMap();
        if (taskId == "" || taskId == null || comment == "" || comment == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TMainIssueBean tMainIssueBean = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        int mainIssueId = tMainIssueBean.getId();
//        if (tMainIssueBean.getIsPassDistrictcenter() != null && tMainIssueBean.getIsPassDistrictcenter().equals("1")) {//区级中心审批的由区级中心线下督办
        tMainIssueBean.setDealWay("5");//区级中心线下督办
//        } else {
//            tMainIssueBean.setDealWay("3");//街道线下督办
//        }
        //设置流程状态
        tMainIssueBean.setStatus(Const.STATUS_FINISH);
        //设置流程结束时间
        tMainIssueBean.setEndTime(currentTime);

        issueService.updateMainIssue(tMainIssueBean);

        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        Group currentGroup = currentMemberShip.getGroup();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        variables.put("TMainIssueBean", tMainIssueBean);
        taskService.complete(taskId, variables);

        //上传文件目录
        String rootDir = System.getProperty("java.io.tmpdir")+"\\jncszhzf";
        if (StringUtils.isNotBlank(myfileData)) {
            JSONArray jsonArray = JSONArray.fromObject(myfileData);
            Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
            for (UploadFileData file : list) {
                attachmentService.addNewAttachment(rootDir, file, mainIssueId, tMainIssueBean.getAddUserid(), 3);
            }
        }

        result.put("success", true);
        return result;
    }


    /**
     * 获取任务数量
     *
     * @param request  请求
     * @param response 相应
     * @param session  会话
     * @return
     * @throws Exception 返回格式为：
     *                   {
     *                   "res":{
     *                   "districeCenterDifficultCaseWaitHandleCount":0,
     *                   "districeCenterWaitDistributeCount":0,
     *                   "districeCenterWaitModTimeLimitCount":0,
     *                   "subDistriceDistributeCount":0,
     *                   "subDistriceWaitDistributeCount":0,
     *                   "waitCompetentDepartmentCooperationCount":0,
     *                   "waitCompetentDepartmentFilingCount":0,
     *                   "waitCompetentDepartmentHandleCount":0,
     *                   "waitCompetentDepartmentReceiveCount":0,
     *                   "waitDistriceCenterOfflineSuperVisorCount":0,
     *                   "waitEvalutateCount":0,
     *                   "waitHandleSelfCount":0,
     *                   "waitSubDistriceHandleCount":0,
     *                   "waitSubDistriceSuperVisorCount":0
     *                   }
     *                   }
     */
    @RequestMapping("/getTaskCount")
    public @ResponseBody
    Map getTaskCount(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        //根据会话信息获取当前登录账号
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        Group currentGroup = currentMemberShip.getGroup();
        String userId = currentUser.getId();

        TodoListCountBean todoListCountBean = new TodoListCountBean();
        String key = ProcessInstanceConst.KEY;
        //街道派发待处理
        long subDistriceDistributeCount = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
                .taskNameLike("%街道派遣%")
                .count(); // 获取总记录数
        todoListCountBean.setSubDistriceDistributeCount(subDistriceDistributeCount);
        //自处置数量
        long waitHandleSelfCount = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
                .taskNameLike("%自处置%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitHandleSelfCount(waitHandleSelfCount);
        //待评价数量
        long waitEvalutateCount = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
                .taskNameLike("%评价%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitEvalutateCount(waitEvalutateCount);
        //街道中心待派发数量
        long subDistriceWaitDistributeCount = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
                .taskNameLike("街道派发%")
                .count(); // 获取总记录数
        todoListCountBean.setSubDistriceWaitDistributeCount(subDistriceWaitDistributeCount);
        //待街道处理数量processDefinitionKey(ProcessInstanceConst.KEY)
        long waitSubDistriceHandleCount = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
                .taskNameLike("街道科室处理%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitSubDistriceHandleCount(waitSubDistriceHandleCount);
        //待街道督察组线下督查数量
        long waitSubDistriceSuperVisorCount = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
                .taskNameLike("线下督查%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitSubDistriceSuperVisorCount(waitSubDistriceSuperVisorCount);
        //区级中心待派发数量
        long districeCenterWaitDistributeCount = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
                .taskNameLike("%区级中心派发%")
                .count(); // 获取总记录数
        todoListCountBean.setDistriceCenterWaitDistributeCount(districeCenterWaitDistributeCount);
        //区级中心待修改时限数量
        long districeCenterWaitModTimeLimitCount = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
                .taskNameLike("%修改处理时限%")
                .count(); // 获取总记录数
        todoListCountBean.setDistriceCenterWaitModTimeLimitCount(districeCenterWaitModTimeLimitCount);
        //疑难问题待处理事项数量
        long districeCenterDifficultCaseWaitHandleCount = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
                .taskNameLike("%疑难案件处理%")
                .count(); // 获取总记录数
        todoListCountBean.setDistriceCenterDifficultCaseWaitHandleCount(districeCenterDifficultCaseWaitHandleCount);
        //待主管部门接收数量
        long waitCompetentDepartmentReceiveCount = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
                .taskNameLike("%主管部门接收%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitCompetentDepartmentReceiveCount(waitCompetentDepartmentReceiveCount);
        //待主案数量
        long waitCompetentDepartmentFilingCount = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
                .taskNameLike("%主管部门立案%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitCompetentDepartmentFilingCount(waitCompetentDepartmentFilingCount);
        //###待主管部门处理数量
        //主管部门派遣处理数量
        long waitCompetentDepartmentHandleCount = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
//                .taskNameLike("%主管部门处理%")
                .taskNameLike("主管部门派遣%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitCompetentDepartmentHandleCount(waitCompetentDepartmentHandleCount);
        //待主管部门联办数量
        long waitCompetentDepartmentCooperationCount = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
                .taskNameLike("%联办%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitCompetentDepartmentCooperationCount(waitCompetentDepartmentCooperationCount);
        //区级中心督察组待线下督查数量
        long waitDistriceCenterOfflineSuperVisorCount = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
                .taskNameLike("督查组线下督查%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitDistriceCenterOfflineSuperVisorCount(waitDistriceCenterOfflineSuperVisorCount);
        //待执法中队处理
        long waitExamination = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(userId)
                .taskNameLike("执法中队处理%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitExamination(waitExamination);
        //督办案件
        String dbKey=ProcessInstanceConst.DB_KEY;
        //街道中心待派发数量
        long subDistriceDistributeCount2 = taskService.createTaskQuery().processDefinitionKey(dbKey)
                .taskCandidateUser(userId)
                .taskNameLike("待街道派发%")
                .count(); // 获取总记录数
        todoListCountBean.setSubDistriceDistributeCount2(subDistriceDistributeCount2);
        //待街道处理数量
        long waitSubDistriceHandleCount2 = taskService.createTaskQuery().processDefinitionKey(dbKey)
                .taskCandidateUser(userId)
                .taskNameLike("待街道科室处理%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitSubDistriceHandleCount2(waitSubDistriceHandleCount2);
        //主管部门派遣处理数量
        long waitCompetentDepartmentHandleCount2 = taskService.createTaskQuery().processDefinitionKey(dbKey)
                .taskCandidateUser(userId)
//                .taskNameLike("%主管部门处理%")
                .taskNameLike("待主管部门派遣%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitCompetentDepartmentHandleCount2(waitCompetentDepartmentHandleCount2);


        long districeCenterDistributionCount = taskService.createTaskQuery().processDefinitionKey(dbKey)
                .taskCandidateUser(userId)
                .taskNameLike("区中心派发%")
                .count(); // 获取总记录数
        todoListCountBean.setDistriceCenterDistributionCount(districeCenterDistributionCount);
        long waitEvaluationGroupCount = taskService.createTaskQuery().processDefinitionKey(dbKey)
                .taskCandidateUser(userId)
                .taskNameLike("%考核组评价%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitEvaluationGroupCount(waitEvaluationGroupCount);
        long waitDistributionCooperationCount = taskService.createTaskQuery().processDefinitionKey(dbKey)
                .taskCandidateUser(userId)
                .taskNameLike("%考核组派发%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitDistributionCooperationCount(waitDistributionCooperationCount);
        long waitExaminationCount = taskService.createTaskQuery().processDefinitionKey(dbKey)
                .taskCandidateUser(userId)
                .taskNameLike("%考核组审核%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitExaminationCount(waitExaminationCount);
        long waitLawEnforcementCount = taskService.createTaskQuery().processDefinitionKey(dbKey)
                .taskCandidateUser(userId)
                .taskNameLike("待执法中队处理%")
                .count(); // 获取总记录数
        todoListCountBean.setWaitLawEnforcementCount(waitLawEnforcementCount);


        Map result = new HashMap();
        result.put("res", todoListCountBean);
        return result;
    }

    /**
     * 问题联办处理逻辑
     * `
     *
     * @param taskId
     * @param comment
     * @param response
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/cooperation")
    public @ResponseBody
    Map cooperation(String taskId, String comment, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        //参数检查
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartRequest.getFiles("myfile");

        taskId = taskId.trim();
        //任务处理逻辑：根据前端页面提交的请求类型，如果是可以处理，直接提交处理意见comment，如果需要修改时限，提交到效能室修改处理时限
        Map result = new HashMap();
        if (taskId == "" || taskId == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }

        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TMainIssueBean tMainIssueBeanSave = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        String mainIssueId = tMainIssueBeanSave.getId().toString();
        TMainIssueBean tMainIssueBean = issueService.findById(Integer.parseInt(mainIssueId));

        String rootDir = request.getSession().getServletContext().getRealPath("");
        Constants.logger.info("rootDir---" + rootDir);

        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();

        //插入附件
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {

                int addAttachmentResult = attachmentService.addNewAttachment(rootDir, file, Integer.parseInt(mainIssueId), currentUser.getId(), 2);
                if (addAttachmentResult <= 0) {
                    result.put("failure", false);
                    return result;
                }
            }
        }

        //获取流程变量
        int nrOfInstances = (Integer) taskService.getVariable(taskId, "nrOfInstances"); //总联办数量
        int nrOfCompletedInstances = (Integer) taskService.getVariable(taskId, "nrOfCompletedInstances"); //已经完成的联办数量
        if (nrOfInstances - nrOfCompletedInstances == 1) {
            //表示已经完成
            //设置状态为待评价
            tMainIssueBean.setStatus(Const.STATUS_WAIT_COMMENT);
            //设置经过区级中心审批
            //tMainIssueBean.setIsPassDistrictcenter("1");
            tMainIssueBean.setIsMultiExector("1"); //联办标识：  1 待办是； 默认0 不是
            issueService.updateMainIssue(tMainIssueBean);


            //判断该条任务是否经过区级中心，如果经过区级中心，评价人设置为区级中心，如果没有经过区级中心，则评价人设置为上报人
            String isByDistrictCenter = tMainIssueBean.getIsPassDistrictcenter();
            if (isByDistrictCenter == "1") {
                String groupId = "districtCenter";
                variables.put("isByDistrictCenter", 1);
                variables.put("handleId", groupId);
            } else {
                //不经过区级中心
                variables.put("isByDistrictCenter", 0);
                String userIds = tMainIssueBean.getAddUserid();
                variables.put("handleId", userIds);
            }
        }

        Group currentGroup = currentMemberShip.getGroup();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        result.put("success", true);
        return result;
    }

    /**
     * 疑难案件处理逻辑
     *
     * @param taskId   String 任务ID
     * @param comment  String 批注信息
     * @param response
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/difficultCaseHandle")
    public @ResponseBody
    Map difficultCaseHandle(String taskId, String comment, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        //参数检查
        Map result = new HashMap();
        if (taskId == "" || taskId == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }
        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取主流程ID
        TMainIssueBean tMainIssueBeanSave = (TMainIssueBean) taskService.getVariable(taskId, "TMainIssueBean");
        String mainIssueId = tMainIssueBeanSave.getId().toString();
        TMainIssueBean tMainIssueBean = issueService.findById(Integer.parseInt(mainIssueId));

        //设置状态
        tMainIssueBean.setStatus(Const.STATUS_WAIT_DISTRICT_CENTER_DISTRIBUTE);

        issueService.updateMainIssue(tMainIssueBean);
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        Group currentGroup = currentMemberShip.getGroup();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        result.put("success", true);
        return result;
    }
/*
    @RequestMapping("selectDealSelf")
    @ResponseBody
    //案件考核计分保存
    public  String  selectDealSelf(){
        String result=null;
    try{
         result=  tMainIssueKhService.updateDealSelf();
    }catch(Exception e){
        e.printStackTrace();
    }
        return result;
    }*/

}
