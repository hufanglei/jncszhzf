package com.java.activiti.service.impl;

import com.java.activiti.dao.*;
import com.java.activiti.model.*;
import com.java.activiti.service.AttachmentService;
import com.java.activiti.service.TaskDbService;
import com.java.activiti.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TaskDbServiceImpl  implements TaskDbService {

    @Resource
    private TissueDcdbKhMapper tissueDcdbKhMapper;

    @Resource
    private TaskService taskService;

    @Resource
    private AttachmentService attachmentService;
    @Resource
    private TissueXcjzKhMapper tissueXcjzKhMapper;
    @Resource
    private TissueJdjcKhMapper tissueJdjcKhMapper;
    @Resource
    private TMainIssueDao tMainIssueDao;

    @Resource
    private TDcdbAttachmentMapper tDcdbAttachmentMapper;

    @Resource
    private GroupDao groupDao;
    @Resource
    private WorkdayDao workdayDao;

    @Resource
    private TMainIssueKhDao tMainIssueKhDao;
    @Resource
    private DbIssueDealMapper dbIssueDealMapper;




    @Override
    public Map khxzpf(String taskId, String qhzdId, String comment, String zgqx, String tzdlx, HttpServletRequest request) {
        Map result = new HashMap();
        if (taskId == "" || taskId == null) {
            //参数检查
            result.put("failure", false);
            return result;
        }
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) request.getSession().getAttribute("currentMemberShip");
        User currentUser = currentMemberShip.getUser();
        Group currentGroup = currentMemberShip.getGroup();
        //获取任务
        Map<String, Object> variables = new HashMap<String, Object>();
        //获取主流程ID
        TissueDcdbKhBean tissueDcdbKhBean = (TissueDcdbKhBean) taskService.getVariable(taskId, "TissueDcdbKhBean");
        //根据组的 类型判断 督办案件状态: 待街道派发3 or 待主管部门派遣6
        //写个接口 返回2 就是 待街道派发  ，返回5 就是 委办局
        String id =tissueDcdbKhBean.getQhzdId();
        String type=tissueDcdbKhMapper.selectKeyByQhzd(id);
        if ("2".equals(type)){
            tissueDcdbKhBean.setStatus("3");
            tissueDcdbKhBean.setStatusName("待街道派发");
        }else if("5".equals(type)){
            tissueDcdbKhBean.setStatus("6");
            tissueDcdbKhBean.setStatusName("待主管部门派遣");
        }else {
        }

        if("3".equals(tissueDcdbKhBean.getStatus())){
            variables.put("dealWay",0);
            variables.put("JDGroupId",qhzdId);
        }else{
            variables.put("dealWay",1);
            variables.put("WBJGroupId",qhzdId);
        }
        variables.put("zgqx",zgqx);
        tissueDcdbKhMapper.updateByPrimaryKeySelective(tissueDcdbKhBean);
        //        //处理流程 和添加批注

        variables.put("tzdlx",tzdlx);
        //更新督办案件表urrentMemberShip.getGroup();
        //取出用户，角色信息
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        // 添加批注时候的审核人，通常应该从session获取
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        taskService.addComment(taskId,processInstanceId,comment);
        taskService.complete(taskId,variables);

        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject taskPage(String page, String rows, String s_name, HttpSession session) {
        //根据会话信息获取当前登录账号
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
        long total = taskService.createTaskQuery().processDefinitionKey(ProcessInstanceConst.DB_KEY)
                .taskCandidateUser(userId)
                .taskNameLike("%" + s_name + "%")
                .count(); // 获取总记录数
        //有想法的话，可以去数据库观察  ACT_RU_TASK 的变化
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(ProcessInstanceConst.DB_KEY)
                // 根据用户id查询
                .taskCandidateUser(userId)
                // 根据任务名称查询
                .taskNameLike("%" + s_name + "%")
                //按照创建时间降序排列
                .orderByTaskCreateTime().desc()
                // 返回带分页的结果集合
                .listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
        //这里需要使用一个工具类来转换一下主要是转成JSON格式
        List<MyTask> MyTaskList = new ArrayList<MyTask>();
        for (Task t : taskList) {
            MyTask myTask = new MyTask();
            myTask.setId(t.getId());
            myTask.setName(t.getName());
            myTask.setCreateTime(t.getCreateTime());

            //获取主流程
            TissueDcdbKhBean tMainIssueBeanTmp = (TissueDcdbKhBean) taskService.getVariable(t.getId(), "TissueDcdbKhBean");

            myTask.setSubject(tMainIssueBeanTmp.getDescript());
            myTask.setIssueNumber(tMainIssueBeanTmp.getIssuenum());
            MyTaskList.add(myTask);
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(MyTaskList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", total);
        return result;
    }

    @Override
    public JSONObject  taskPage2(String page, String rows, String s_name, HttpSession session) {
        //根据会话信息获取当前登录账号
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


        //有想法的话，可以去数据库观察  ACT_RU_TASK 的变化
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(ProcessInstanceConst.DB_KEY)
                // 根据用户id查询
                .taskCandidateUser(userId)
                // 根据任务名称查询
                .taskNameLike("%" + s_name + "%")
                //按照创建时间降序排列
                .orderByTaskCreateTime().desc()
                // 返回带分页的结果集合
                .listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
        // 获取总记录数
        long total = taskList != null ? taskList.size() : 0;
        //这里需要使用一个工具类来转换一下主要是转成JSON格式
        List<MyTask> MyTaskList = new ArrayList<MyTask>();
        for (Task t : taskList) {
            MyTask myTask = new MyTask();
            myTask.setId(t.getId());
            myTask.setName(t.getName());
            myTask.setCreateTime(t.getCreateTime());
            //获取主流程
            Object obj = taskService.getVariable(t.getId(), "TissueDcdbKhBean");
            if(obj!=null){
                TissueDcdbKhBean tMainIssueBeanTmp =  (TissueDcdbKhBean)obj;
                //普通案件编号
                String issueNumber = tMainIssueBeanTmp.getIssuenum();
                //根据案件编号查询案件信息
                TMainIssueBean tMainIssueBean = tMainIssueDao.selectTMainIssueByIssueNumber(issueNumber);
                //根据案件信息获取案件主题
                String subject=tMainIssueDao.getSubject(tMainIssueBean.getIssueSubject());
                String sourceId=tMainIssueBeanTmp.getSourceId();
                String processId=t.getProcessInstanceId();
                myTask.setSubject(sourceId);
                myTask.setIssueNumber(tMainIssueBeanTmp.getIssuenum());
                myTask.setDbIssueNum(tMainIssueBeanTmp.getDcdbnum());
                myTask.setProcessId(processId);

            }
            MyTaskList.add(myTask);

        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(MyTaskList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", total);
        return result;
    }

    @Override
    public JSONObject subdistrictOfficeDistribute(String taskId, String comment, String state, String subdistrictDepartGroupID, String gridOperatorName, HttpSession session) {
        JSONObject result = new JSONObject();
        if (taskId == "" || taskId == null || state == "" || state == null) {
            //参数检查
            result.put("failure", false);
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
        TissueDcdbKhBean tMainIssueBeanTmp = (TissueDcdbKhBean) taskService.getVariable(taskId, "TissueDcdbKhBean");

        String mainIssueId = tMainIssueBeanTmp.getId().toString();
        final TissueDcdbKhBean tissueDcdbKhBean = tissueDcdbKhMapper.selectByPrimaryKey(Integer.parseInt(mainIssueId));
        int stateCode = Integer.parseInt(state);
        switch (stateCode) {
            case 1:
                //-------------处理待街道处理
                //参数检查
                if (subdistrictDepartGroupID == null || subdistrictDepartGroupID == "") {
                    //如果街道处理部门为空，则提示错误
                    result.put("failure", false);
                }
                //设置状态
                tissueDcdbKhBean.setStatus(Const.STATUS_WAIT_SUBDISTRICT_HANDLE);
                tissueDcdbKhBean.setStatusName("待街道处理");

                //设置流程变量，设置为街道处理
                variables.put("streetHandleStatus", 1);
                variables.put("handleGroupId", subdistrictDepartGroupID);
                break;
//            case 2:
//                //----------上报区级中心
//                //设置状态
//                tissueDcdbKhBean.setStatus(Const.STATUS_WAIT_DISTRICT_CENTER_DISTRIBUTE);
//
//                //设置流程变量，设置为上报区级中心
//                variables.put("streetHandleStatus", 2);
//                break;
//            case 3:
//                //---转为街道线下督办
//                //设置状态
//                tissueDcdbKhBean.setStatus(Const.STATUS_WAIT_SUBDISTRICT_HANDLE_OFFLINE);
//
//                //设置流程变量
//                variables.put("streetHandleStatus", 3);
//                break;
            case 4:
                //-----网格员派遣
                //参数检查
                if (gridOperatorName == null || gridOperatorName == "") {
                    //如果网格员为空，则表示驳回
                    throw new RuntimeException("没有选择网格员");
                }
                //设置状态
                tissueDcdbKhBean.setStatus(Const.STATUS_WAIT_SUBDISTRICT_DISTRUBUTE_CASE_HANDLE);
                tissueDcdbKhBean.setStatusName("街道派遣待处理");

                //设置流程变量
                variables.put("streetHandleStatus", 0);
                variables.put("userIds", gridOperatorName);
                break;
            default:
                break;
        }
        //更新主流程表
        tissueDcdbKhMapper.updateByPrimaryKeySelective(tissueDcdbKhBean);
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

    @Override
    public String subdistrictOfficeHandle(String taskId, String comment, HttpServletResponse response, MultipartHttpServletRequest request, HttpSession session) throws Exception {
        //参数检查
        MultipartHttpServletRequest multipartRequest = request;
        List<MultipartFile> files = multipartRequest.getFiles("myfile");

        taskId = taskId.trim();
        //任务处理逻辑：根据前端页面提交的请求类型，直接提交处理意见comment
        JSONObject result = new JSONObject();
        if (taskId == "" || taskId == null) {
            //参数检查
            result.put("failure", false);
            ResponseUtil.write(response,result);
            return null;
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
//

        TissueDcdbKhBean tMainIssueBeanTmp = (TissueDcdbKhBean) taskService.getVariable(taskId, "TissueDcdbKhBean");

        String mainIssueId = tMainIssueBeanTmp.getId().toString();
        final TissueDcdbKhBean tissueDcdbKhBean = tissueDcdbKhMapper.selectByPrimaryKey(Integer.parseInt(mainIssueId));

        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        //更改状态
        // tMainIssueBean.setDealWay(Const.DEAL_WAY_JDKECL);//街道科室处理
//        if(!Const.J_QJ_YES.equals(tMainIssueBean.getIsPassDistrictcenter())){//没有经过区级中心
//            variables.put("isByDistrictCenter",0);
        //tissueDcdbKhBean.setDealWay(Const.DEAL_WAY_JDKECL);//处理方式:街道科室处理
        tissueDcdbKhBean.setStatus(Const.STATUS_YWJ);
        tissueDcdbKhBean.setStatusName("已完结");
//        }else{  //经过区级中心
//            variables.put("isByDistrictCenter",1);
//            tMainIssueBean.setStatus(Const.STATUS_WAIT_COMMENT);
//        }
        //不经过区级中心
//        variables.put("isByDistrictCenter", 0);
//        String userIds = tMainIssueBean.getAddUserid();
//        variables.put("handleId", userIds);

        tissueDcdbKhMapper.updateByPrimaryKeySelective(tissueDcdbKhBean);


        Group currentGroup = currentMemberShip.getGroup();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId,variables);
        result.put("success", true);
        ResponseUtil.write(response,result);
        return null;
    }

    @Override
    public JSONObject wgycl(String taskId, String comment, HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        int result = -1;
        try {
            TissueDcdbKhBean tMainIssueBeanTmp = (TissueDcdbKhBean) taskService.getVariable(taskId, "TissueDcdbKhBean");
            tMainIssueBeanTmp.setStatus(Const.STATUS_YWJ);
            tMainIssueBeanTmp.setStatusName("已完结");
            MemberShip tMemberShip = (MemberShip) request.getSession().getAttribute("currentMemberShip");
            // 设置用户id
            Authentication.setAuthenticatedUserId(tMemberShip.getUserId() + "[" + tMemberShip.getGroup().getGroupName() + "]");
            // 添加批注信息
            taskService.addComment(taskId, tMainIssueBeanTmp.getProcessId(), comment);
            // 完成任务
            taskService.complete(taskId);
            result = tissueDcdbKhMapper.updateByPrimaryKeySelective(tMainIssueBeanTmp);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(result==1){
            obj.put("success","true");
        }else{
            obj.put("success","false");
        }
        return obj;
    }

    @Override
    public String competentDepartmentHandle(String taskId, String comment, Integer state, String zfzd, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {

        //委办局选择的执法中队人员ID  zfzd
        System.out.println("TODO:taskController.competentDepartmentHandle() ------ 委办局选择的执法中队人员ID  zfzd "+zfzd);

        //参数检查
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartRequest.getFiles("myfile");

        taskId = taskId.trim();
        //任务处理逻辑：根据前端页面提交的请求类型，如果是可以处理，直接提交处理意见comment，如果需要修改时限，提交到效能室修改处理时限
        JSONObject result = new JSONObject();
        if (taskId == "" || taskId == null || state == 0 || state == null) {
            //参数检查
            result.put("failure", false);
            ResponseUtil.write(response, result);
            return null;
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
        TissueDcdbKhBean tMainIssueBeanTmp = (TissueDcdbKhBean) taskService.getVariable(taskId, "TissueDcdbKhBean");

        String mainIssueId = tMainIssueBeanTmp.getId().toString();
        TissueDcdbKhBean tissueDcdbKhBean = tissueDcdbKhMapper.selectByPrimaryKey(Integer.parseInt(mainIssueId));

        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();

        //处理任务，不增加处理时限，转到部门派遣
        tissueDcdbKhBean.setStatus(Const.STATUS_COMPETENT_DEPARTMENT_HANDLE);//待执法中队处理
        tissueDcdbKhBean.setStatusName("待执法中队处理 ");
        variables.put("ZFGroupId",zfzd);
        String rootDir = request.getSession().getServletContext().getRealPath("");
        Constants.logger.info("rootDir---" + rootDir);

        //插入附件
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                int addAttachmentResult = attachmentService.addNewAttachment(rootDir, file, Integer.parseInt(mainIssueId), currentUser.getId(), 2);
                if (addAttachmentResult <= 0) {
                    result.put("failure", false);
                    ResponseUtil.write(response, result);
                    return null;
                }
            }
        }
//
        tissueDcdbKhMapper.updateByPrimaryKeySelective(tissueDcdbKhBean);

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
        ResponseUtil.write(response, result);
        return null;
    }


    @Override
    public String competentDepartmentFiling(String taskId, String comment,String myfileData, HttpServletResponse response, HttpSession session, JSONObject result) throws Exception {
        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();

        //获取主流程ID
        TissueDcdbKhBean tMainIssueBeanTmp = (TissueDcdbKhBean) taskService.getVariable(taskId, "TissueDcdbKhBean");
        String mainIssueId = tMainIssueBeanTmp.getId().toString();
        TissueDcdbKhBean tissueDcdbKhBean = tissueDcdbKhMapper.selectByPrimaryKey(Integer.parseInt(mainIssueId));

        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        //设置状态

        tissueDcdbKhBean.setStatus(Const.STATUS_YWJ);
        tissueDcdbKhBean.setStatusName("已完结");
        //权利事项归属委办局ID 赋值
        User currentUser = currentMemberShip.getUser();
        Group currentGroup = currentMemberShip.getGroup();

        tissueDcdbKhMapper.updateByPrimaryKeySelective(tissueDcdbKhBean);

        //处理附件插入
        //上传文件目录
        String rootDir = System.getProperty("java.io.tmpdir")+"\\jncszhzf";
        if (StringUtils.isNotBlank(myfileData)) {
            JSONArray jsonArray = JSONArray.fromObject(myfileData);
            Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
            for (UploadFileData file : list) {
                int res = attachmentService.addNewAttachment(rootDir, file, Integer.parseInt(mainIssueId), currentMemberShip.getUserId(), 2);
            }
        }

        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentGroup.getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    @Override
    public int xcjzsh(TissueXcjzKhBean bean) {
        return tissueXcjzKhMapper.updateByPrimaryKeySelective(bean);
    }
    @Override
    public int jdjcsh(TissueJdjcKhBean bean){
        return tissueJdjcKhMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 通过流程实例分类型查询附件
     *
     * @param taskId
     * @return
     */
//    考核的图片显示查询
//   根据taskid得到流程id
//    在督办表中根据 流程id得到督办 id
//    根据督办id 和类型获取附件列表
    @Override
    public Map<String, List<TDcdbAttachment>> queryAttachments(String taskId) {
        Map<String, List<TDcdbAttachment>> resMap = new HashMap<>(3);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        List<TDcdbAttachment> reportAttachments = tDcdbAttachmentMapper.selectAttachmentByProccessId(task.getProcessInstanceId());
        resMap.put("reportAttachments", reportAttachments);
        return resMap;
    }

    /**
     * 插入督办附件
     *
     * @param rootDir  路径
     * @param fileData 附件数据
     * @param issueId  案件上报
     * @param userId
     * @param type
     * @return
     * @throws Exception
     */
    public int addAttachment(String rootDir, UploadFileData fileData, int issueId, String userId, int type) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        int result = 0;
        try {
            map = Constants.writeFile(rootDir, fileData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TDcdbAttachment tAttachmentBean = new TDcdbAttachment();
        tAttachmentBean.setMainIssueId(issueId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ret = sdf.format(new Date());
        tAttachmentBean.setAddtime(ret);
        tAttachmentBean.setAddUserId(userId);
        tAttachmentBean.setName((String) map.get("annexName"));
        tAttachmentBean.setSize((String) map.get("filesize"));
        tAttachmentBean.setSavePath((String) map.get("saveAnnexName"));
        tAttachmentBean.setType(type);
        result = tDcdbAttachmentMapper.insert(tAttachmentBean);
        return result;
    }



    /*****************************新流程******************************************/

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
    @Override
    public Map districtDistribution(String taskId, String comment, Integer state, String myfileData, HttpServletRequest request, HttpSession session) throws Exception{
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

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TissueDcdbKhBean tDIssueBean = (TissueDcdbKhBean) taskService.getVariable(taskId, "TissueDcdbKhBean");
        int dbIssueId = tDIssueBean.getId();
        TissueDcdbKhBean tDbMainIssueBean = tissueDcdbKhMapper.selectByPrimaryKey(dbIssueId);
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        //保存考核组变量
        variables.put("examineGroup", Const.KHXZ_GROUP_ID);

        //状态修改为：待考核组审核
        tDbMainIssueBean.setStatus(Const.DB_EXAMINE_GROUP_CHECK);
        List<HashMap<String,Object>>  list1=tissueDcdbKhMapper.selectProcessId(tDbMainIssueBean.getDcdbnum());
        System.out.println(list1);
        DbIssueDeal dbIssueDeal=new DbIssueDeal();
        dbIssueDeal.setDcdbnum(String.valueOf(list1.get(0).get("dcdbNum")));
        dbIssueDeal.setTaskId(taskId);
        dbIssueDeal.setProcessId(String.valueOf(list1.get(0).get("PROC_INST_ID_")));
        dbIssueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        dbIssueDeal.setDealWay("已提交");
        dbIssueDealMapper.insert(dbIssueDeal);
        tissueDcdbKhMapper.updateByPrimaryKeySelective(tDbMainIssueBean);

        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentMemberShip.getGroup().getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        //执行任务
        taskService.complete(task.getId(), variables);

        // 流程信息插入成功，处理附件插入

        String userId = currentMemberShip.getUserId();
        //上传文件目录
        String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
        if (org.apache.commons.lang3.StringUtils.isNotBlank(myfileData)) {
            JSONArray jsonArray = JSONArray.fromObject(myfileData);
            Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
            for (UploadFileData file : list) {
                int res = addAttachment(rootDir, file, dbIssueId, userId, Integer.parseInt(Const.PT_FJTYPE_ONE));
            }
        }
        result.put("success", true);
        return result;
    }

    /**
     * 考核组审核
     * @param taskId
     * @param comment
     * @param status
     * @param myfileData
     * @param request
     * @param session
     * @return
     */
    @Override
    public Map examinationGroupReview(String taskId, String comment, String status, String myfileData, HttpServletRequest request, HttpSession session) throws Exception {
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

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TissueDcdbKhBean tDIssueBean = (TissueDcdbKhBean) taskService.getVariable(taskId, "TissueDcdbKhBean");
        int dbIssueId = tDIssueBean.getId();
        TissueDcdbKhBean tDbMainIssueBean = tissueDcdbKhMapper.selectByPrimaryKey(dbIssueId);
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        List<HashMap<String,Object>>  list1=tissueDcdbKhMapper.selectProcessId(tDbMainIssueBean.getDcdbnum());
        System.out.println(list1);
        DbIssueDeal dbIssueDeal=new DbIssueDeal();
        dbIssueDeal.setDcdbnum(String.valueOf(list1.get(0).get("dcdbNum")));
        dbIssueDeal.setTaskId(taskId);
        dbIssueDeal.setProcessId(String.valueOf(list1.get(0).get("PROC_INST_ID_")));
        dbIssueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //是否审核通过
        if("1".equals(status)){
            //审核通过
            variables.put("examineStatus", 1);
            //保存考核组变量
            variables.put("examineGroups", Const.KHXZ_GROUP_ID);
            tDbMainIssueBean.setStatus(Const.DB_EXAMINE_GROUP_DISTRIBUTE);
            dbIssueDeal.setDealWay("审核通过");
        }else{
            //审核不通过
            variables.put("examineStatus", 0);
            //状态修改为：完成
            tDbMainIssueBean.setStatus(Const.DB_FINISH);
            dbIssueDeal.setDealWay("审核不通过");
        }
        //保存考核组变量


        tissueDcdbKhMapper.updateByPrimaryKeySelective(tDbMainIssueBean);
        dbIssueDealMapper.insert(dbIssueDeal);
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentMemberShip.getGroup().getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);

        //执行任务
        taskService.complete(task.getId(), variables);

        // 流程信息插入成功，处理附件插入
        String userId = currentMemberShip.getUserId();
        //上传文件目录
        String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
        if (org.apache.commons.lang3.StringUtils.isNotBlank(myfileData)) {
            JSONArray jsonArray = JSONArray.fromObject(myfileData);
            Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
            for (UploadFileData file : list) {
                int res = addAttachment(rootDir, file, dbIssueId, userId, 1);
            }
        }
        result.put("success", true);
        return result;
    }

    @Override
    public Map dbexaminationGroupDistribution(String taskId, String comment, String state,String myfileData, String districtCenterDepartGroup, String subdistrictGroupID, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //获取主流程ID
        TissueDcdbKhBean tDIssueBean = (TissueDcdbKhBean) taskService.getVariable(taskId, "TissueDcdbKhBean");
        int dbIssueId = tDIssueBean.getId();
        TissueDcdbKhBean tDbMainIssueBean = tissueDcdbKhMapper.selectByPrimaryKey(dbIssueId);
        int stateCode = Integer.parseInt(state);
        List<HashMap<String,Object>>  list2=tissueDcdbKhMapper.selectProcessId(tDbMainIssueBean.getDcdbnum());
        DbIssueDeal dbIssueDeal=new DbIssueDeal();
        dbIssueDeal.setDcdbnum(String.valueOf(list2.get(0).get("dcdbNum")));
        dbIssueDeal.setTaskId(taskId);
        dbIssueDeal.setProcessId(String.valueOf(list2.get(0).get("PROC_INST_ID_")));
        dbIssueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        switch (stateCode) {
            case 1:
                //---区级中心派遣
                //设置状态为待街道派发
                tDbMainIssueBean.setStatus(Const.DB_STREET_DISTRIBUTE);
                //设置流程变量
                variables.put("examineCode", 2);
                variables.put("distributionGroup", subdistrictGroupID);
                dbIssueDeal.setDealWay("已派遣");
                break;
            case 2:
                //-------------派发到主管部门接收
                //参数检查
                if (districtCenterDepartGroup == null || districtCenterDepartGroup == "") {
                    //如果主管部门为空，则提示错误
                    result.put("failure", false);
                    return result;
                }
                //设置状态
                tDbMainIssueBean.setStatus(Const.DB_DEPT_DISPATCH);
                //设置流程变量
                variables.put("examineCode", 1);
                variables.put("distributionGroup", districtCenterDepartGroup);
                dbIssueDeal.setDealWay("已派发");
                break;
            default:
                break;
        }
     /*   tDbMainIssueBean.setTimeLimit(String.valueOf(Const.TIME_LIMIT_FIVE));*/
        tDbMainIssueBean.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
       /* String createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        Date date = sdf.parse(createDate);
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.DAY_OF_MONTH, 5);
        String temp = "";
        temp = sdf.format(cl.getTime());*/
        List list1;
      /*  list1=SetWeekDay.getEndTime(tDbMainIssueBean.getStartTime(),Const.TIME_LIMIT_FIVE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        int c=0;
        int count = 0;
        for(int i=0;i<list1.size();i++){
            System.out.println(list1.get(i));
            try {
                count = workdayDao.selectWorkdayCountByWorkDay((String) list1.get(i));

            }catch(Exception e){
                e.printStackTrace();
            }
            if(count!=0){
                c+=count;
            }
        }*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        //4.得出list中元素的在表中存在的总条数,在limitTime的基础上再加c,就得到案件实际结束的时间
        String  temp2="";
        Calendar c2 = Calendar.getInstance();
        Date date2 = null;
        try {
            date2 = sdf.parse(tDbMainIssueBean.getStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c2.setTime(date2);
     /*   int total= Const.TIME_LIMIT_FIVE+c;*/
        c2.add(Calendar.DAY_OF_MONTH,5);
        temp2 = sdf.format(c2.getTime());
        tDbMainIssueBean.setTimeLimit("5");
        tDbMainIssueBean.setTotalTime( temp2);
       // tDbMainIssueBean.setTotalTime(temp);
        //更新主流程表
        tissueDcdbKhMapper.updateByPrimaryKeySelective(tDbMainIssueBean);
        //保存案件处理方式
        dbIssueDealMapper.insert(dbIssueDeal);
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
        // 流程信息插入成功，处理附件插入
        String userId = currentMemberShip.getUserId();
        //上传文件目录
        String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
        if (org.apache.commons.lang3.StringUtils.isNotBlank(myfileData)) {
            JSONArray jsonArray = JSONArray.fromObject(myfileData);
            Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
            for (UploadFileData file : list) {
                int res = addAttachment(rootDir, file, dbIssueId, userId, 1);
            }
        }
        result.put("success", true);
        return result;
    }

    @Override
    public Map dbStreetDistribution(String taskId, String comment, String state, String myfileData,String districtCenterDepartGroup, String subdistrictGroupID, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
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
        TissueDcdbKhBean tDIssueBean = (TissueDcdbKhBean) taskService.getVariable(taskId, "TissueDcdbKhBean");
        int dbIssueId = tDIssueBean.getId();
        TissueDcdbKhBean tDbMainIssueBean = tissueDcdbKhMapper.selectByPrimaryKey(dbIssueId);
        int stateCode = Integer.parseInt(state);
        List<HashMap<String,Object>>  list2=tissueDcdbKhMapper.selectProcessId(tDbMainIssueBean.getDcdbnum());
        DbIssueDeal dbIssueDeal=new DbIssueDeal();
        dbIssueDeal.setDcdbnum(String.valueOf(list2.get(0).get("dcdbNum")));
        dbIssueDeal.setTaskId(taskId);
        dbIssueDeal.setProcessId(String.valueOf(list2.get(0).get("PROC_INST_ID_")));
        dbIssueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        switch (stateCode) {
            case 1:
                //-------------处理待街道处理
                //参数检查
                if (subdistrictGroupID == null || subdistrictGroupID == "") {
                    //如果街道处理部门为空，则提示错误
                    return result;
                }
                //设置状态
                tDbMainIssueBean.setStatus(Const.DB_STREETDEPART_DEAL);

                //设置流程变量，设置为街道处理
                variables.put("keshiGroup", subdistrictGroupID);
                dbIssueDeal.setDealWay("已派发");
                break;
            default:
                break;
        }
        //更新主流程表
        tissueDcdbKhMapper.updateByPrimaryKeySelective(tDbMainIssueBean);
        //保存案件办理方式
        dbIssueDealMapper.insert(dbIssueDeal);
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
        // 流程信息插入成功，处理附件插入
        String userId = currentMemberShip.getUserId();
        //上传文件目录
        String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
        if (org.apache.commons.lang3.StringUtils.isNotBlank(myfileData)) {
            JSONArray jsonArray = JSONArray.fromObject(myfileData);
            Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
            for (UploadFileData file : list) {
                int res = addAttachment(rootDir, file, dbIssueId, userId, 1);
            }
        }
        result.put("success", true);
        return result;
    }

    @Override
    public Map dbkeshiDeal(String taskId, String comment, String myfileData, HttpServletRequest request, HttpSession session) throws Exception {
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

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TissueDcdbKhBean tDIssueBean = (TissueDcdbKhBean) taskService.getVariable(taskId, "TissueDcdbKhBean");
        int dbIssueId = tDIssueBean.getId();
        TissueDcdbKhBean tDbMainIssueBean = tissueDcdbKhMapper.selectByPrimaryKey(dbIssueId);
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        //保存考核组变量
        variables.put("evaluateGroup", Const.KHXZ_GROUP_ID);
        //保存案件办理方式
        List<HashMap<String,Object>>  list2=tissueDcdbKhMapper.selectProcessId(tDbMainIssueBean.getDcdbnum());
        DbIssueDeal dbIssueDeal=new DbIssueDeal();
        dbIssueDeal.setDcdbnum(String.valueOf(list2.get(0).get("dcdbNum")));
        dbIssueDeal.setTaskId(taskId);
        dbIssueDeal.setProcessId(String.valueOf(list2.get(0).get("PROC_INST_ID_")));
        dbIssueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        dbIssueDeal.setDealWay("待评价");
        //状态修改为：待考核组审核
        tDbMainIssueBean.setStatus(Const.DB_EXAMINE_GROUP_EVALUATE);
        tissueDcdbKhMapper.updateByPrimaryKeySelective(tDbMainIssueBean);

        //保存案件出来方式
        dbIssueDealMapper.insert(dbIssueDeal);
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentMemberShip.getGroup().getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        //执行任务
        taskService.complete(task.getId(), variables);

        // 流程信息插入成功，处理附件插入

        String userId = currentMemberShip.getUserId();
        //上传文件目录
        String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
        if (org.apache.commons.lang3.StringUtils.isNotBlank(myfileData)) {
            JSONArray jsonArray = JSONArray.fromObject(myfileData);
            Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
            for (UploadFileData file : list) {
                int res = addAttachment(rootDir, file, dbIssueId, userId, 1);
            }
        }
        result.put("success", true);
        return result;
    }



    @Override
    public Map dbDeptDeal(String taskId, String comment, String zfzd, String myfileData, HttpServletRequest request, HttpSession session) throws Exception {
        int res = -1;
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
        //获取主流程ID
        TissueDcdbKhBean tDIssueBean = (TissueDcdbKhBean) taskService.getVariable(taskId, "TissueDcdbKhBean");
        int dbIssueId = tDIssueBean.getId();
        TissueDcdbKhBean tDbMainIssueBean = tissueDcdbKhMapper.selectByPrimaryKey(dbIssueId);
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        variables.put("ZFGroup", zfzd);
        tDbMainIssueBean.setStatus(Const.DB_LOCHUS_DEAL);
        //保存案件办理方式
        List<HashMap<String,Object>>  list2=tissueDcdbKhMapper.selectProcessId(tDbMainIssueBean.getDcdbnum());
        DbIssueDeal dbIssueDeal=new DbIssueDeal();
        dbIssueDeal.setDcdbnum(String.valueOf(list2.get(0).get("dcdbNum")));
        dbIssueDeal.setTaskId(taskId);
        dbIssueDeal.setProcessId(String.valueOf(list2.get(0).get("PROC_INST_ID_")));
        dbIssueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        dbIssueDeal.setDealWay("已派发");
        //上传文件目录
        String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
        if (StringUtils.isNotBlank(myfileData)) {
            JSONArray jsonArray = JSONArray.fromObject(myfileData);
            Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
            for (UploadFileData file : list) {
                res = attachmentService.addNewAttachment(rootDir, file, dbIssueId, currentUser.getId(), 1);
                if (res < 0) {
                    result.put("result", res);
                    return result;
                }
            }
        }

        //设置经过区级中心审批
        tissueDcdbKhMapper.updateByPrimaryKeySelective(tDbMainIssueBean);
        //保存案件处理方式
        dbIssueDealMapper.insert(dbIssueDeal);
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

    @Override
    public Map dblochus(String taskId, String comment, String myfileData, HttpServletRequest request, HttpSession session) throws Exception {
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
        TissueDcdbKhBean tDIssueBean = (TissueDcdbKhBean) taskService.getVariable(taskId, "TissueDcdbKhBean");
        int dbIssueId = tDIssueBean.getId();
        TissueDcdbKhBean tDbMainIssueBean = tissueDcdbKhMapper.selectByPrimaryKey(dbIssueId);
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        //保存考核组变量
        variables.put("evaluateGroup", Const.KHXZ_GROUP_ID);

        //状态修改为：待考核组审核
        tDbMainIssueBean.setStatus(Const.DB_EXAMINE_GROUP_EVALUATE);
        //保存案件办理方式
        List<HashMap<String,Object>>  list2=tissueDcdbKhMapper.selectProcessId(tDbMainIssueBean.getDcdbnum());
        DbIssueDeal dbIssueDeal=new DbIssueDeal();
        dbIssueDeal.setDcdbnum(String.valueOf(list2.get(0).get("dcdbNum")));
        dbIssueDeal.setTaskId(taskId);
        dbIssueDeal.setProcessId(String.valueOf(list2.get(0).get("PROC_INST_ID_")));
        dbIssueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        dbIssueDeal.setDealWay("待评价");
        tissueDcdbKhMapper.updateByPrimaryKeySelective(tDbMainIssueBean);

        //保存案件处理方式
        dbIssueDealMapper.insert(dbIssueDeal);
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentMemberShip.getGroup().getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        //执行任务
        taskService.complete(task.getId(), variables);

        // 流程信息插入成功，处理附件插入

        String userId = currentMemberShip.getUserId();
        //上传文件目录
        String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
        if (org.apache.commons.lang3.StringUtils.isNotBlank(myfileData)) {
            JSONArray jsonArray = JSONArray.fromObject(myfileData);
            Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
            for (UploadFileData file : list) {
                int res = addAttachment(rootDir, file, dbIssueId, userId, 1);
            }
        }
        result.put("success", true);
        return result;
    }

    @Override
    public Map dbexaminationGroupEvaluation(String taskId, String comment, String status, String score, HttpServletRequest request, HttpSession session) throws Exception {
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

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String currentTime = df.format(new Date());

        //获取主流程ID
        TissueDcdbKhBean tDIssueBean = (TissueDcdbKhBean) taskService.getVariable(taskId, "TissueDcdbKhBean");
        int dbIssueId = tDIssueBean.getId();
        TissueDcdbKhBean tDbMainIssueBean = tissueDcdbKhMapper.selectByPrimaryKey(dbIssueId);
        //取得角色用户登入的session对象
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户，角色信息
        User currentUser = currentMemberShip.getUser();
        //保存案件办理方式
        List<HashMap<String,Object>>  list2=tissueDcdbKhMapper.selectProcessId(tDbMainIssueBean.getDcdbnum());
        DbIssueDeal dbIssueDeal=new DbIssueDeal();
        dbIssueDeal.setDcdbnum(String.valueOf(list2.get(0).get("dcdbNum")));
        dbIssueDeal.setTaskId(taskId);
        dbIssueDeal.setProcessId(String.valueOf(list2.get(0).get("PROC_INST_ID_")));
        dbIssueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //是否审核通过
        switch (status){
            case "3":
                //返回街道 重办
                //获取考核组派发的distributionGroup流程变量的值，如果type=2，走街道流程，如果type=5，走委办局流程
                String distributionGroup = (String) taskService.getVariable(taskId, "distributionGroup");
                String type = groupDao.getTypeById(distributionGroup);
                if("2".equals(type)){
                    variables.put("evaluateStatus", 2);
                    tDbMainIssueBean.setStatus(Const.DB_STREET_DISTRIBUTE);
                }else if("5".equals(type)){
                    variables.put("evaluateStatus", 0);
                    tDbMainIssueBean.setStatus(Const.DB_DEPT_DISPATCH);
                }else{
                    throw new RuntimeException("参数不对，考核组评价流程执行错误");
                }
                dbIssueDeal.setDealWay("发回重办");
                break;
            case "1":
                //满意
                //保存考核组变量
                tDbMainIssueBean.setScore("0");
                tDbMainIssueBean.setEvaluationStatus("1");
                variables.put("evaluateStatus", 1);
                tDbMainIssueBean.setStatus(Const.DB_FINISH);
                dbIssueDeal.setDealWay("满意");
                break;
            case "2":

                //不满意就扣分
                tDbMainIssueBean.setScore(score);
                tDbMainIssueBean.setEvaluationStatus("2");
                //不满意
                variables.put("evaluateStatus", 1);
                tDbMainIssueBean.setStatus(Const.DB_FINISH);
                dbIssueDeal.setDealWay("不满意");
                break;
            default:
                break;
        }
        tDbMainIssueBean.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        tissueDcdbKhMapper.updateByPrimaryKeySelective(tDbMainIssueBean);
        //保存案件处理方式
        dbIssueDealMapper.insert(dbIssueDeal);
     /*   Map map1 = new HashMap();
        map1.put("qhzdDm", tDbMainIssueBean.getQhzdId());
        map1.put("khyf", tDbMainIssueBean.getKhyf());
        //查询 案件考核表中的数据是否存在
       // TMainIssueKhBean tMainIssueKhBean=tMainIssueKhDao.selectXcbd(map1);
        //如果存在,更新 否则 添加 在案件考核表中
        List<HashMap<String, Object>>  dcdbList = tMainIssueKhDao.selectDcdbList();
        for (int i=0;i<dcdbList.size();i++){
            HashMap<String, Object> dcdbListItem = dcdbList.get(i);
            String qhdm = (String) dcdbListItem.get("id_");
            String qhmc = (String) dcdbListItem.get("name_");
            Calendar calendar = Calendar.getInstance();
            String year = calendar.get(Calendar.YEAR) + "";
            String month = (calendar.get(Calendar.MONTH) + 1) < 10 ? ("0" + (calendar.get(Calendar.MONTH) + 1)) : (calendar.get(Calendar.MONTH) + 1) + "";//本月
            String khyf = year + "-" + month;
            double onTimeListScore = (double) dcdbListItem.get("score");
            Map map = new HashMap();
            map.put("qhzdDm", qhdm);
            map.put("khyf", khyf);
            TMainIssueKhBean tMainIssueKhBean2 = tMainIssueKhDao.selectXcbd(map);
            if (tMainIssueKhBean2!=null){
                String newScore = String.valueOf(dcdbListItem.get("score").equals(null)||dcdbListItem.get("score").equals("") ? 0 : dcdbListItem.get("score"));
                if (dcdbListItem.get("source_id")!=null&&dcdbListItem.get("source_id").equals("反复投诉")){
                    tMainIssueKhBean2.setFfts(newScore);
                }else if (dcdbListItem.get("source_id")!=null&&dcdbListItem.get("source_id").equals("督办问责")){
                    tMainIssueKhBean2.setDbwz(newScore);
                }else if (dcdbListItem.get("source_id")!=null&&dcdbListItem.get("source_id").equals("工作协调")){
                    tMainIssueKhBean2.setGzxt(newScore);
                }else if (dcdbListItem.get("source_id")!=null&&dcdbListItem.get("source_id").equals("巡查机制")){
                    tMainIssueKhBean2.setXcjz(newScore);
                }
                tMainIssueKhDao.updateTMainIssueXcbd(tMainIssueKhBean2);
            }else {
                TMainIssueKhBean tMainIssueKhBean = new TMainIssueKhBean();
                tMainIssueKhBean.setQhzdId(qhdm);
                tMainIssueKhBean.setQhzdName(qhmc);
                tMainIssueKhBean.setKhyf(khyf);
                String newScore = String.valueOf(dcdbListItem.get("score").equals(null)||dcdbListItem.get("score").equals("") ? 0 : dcdbListItem.get("source_id"));
                tMainIssueKhBean.setKhsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                if (dcdbListItem.get("source_id")!=null&&dcdbListItem.get("source_id").equals("反复投诉")){
                    tMainIssueKhBean.setFfts(newScore);
                }else if (dcdbListItem.get("source_id")!=null&&dcdbListItem.get("source_id").equals("督办问责")){
                    tMainIssueKhBean.setDbwz(newScore);
                }else if (dcdbListItem.get("source_id")!=null&&dcdbListItem.get("source_id").equals("工作协调")){
                    tMainIssueKhBean.setGzxt(newScore);
                }else if (dcdbListItem.get("source_id")!=null&&dcdbListItem.get("source_id").equals("巡查机制")){
                    tMainIssueKhBean.setXcjz(newScore);
                }
                tMainIssueKhDao.insertTMainIssueKh(tMainIssueKhBean);
            }

        }*/

        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getId() + "[" + currentMemberShip.getGroup().getGroupName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        //执行任务
        taskService.complete(task.getId(), variables);
        result.put("success", true);
        return result;
    }


}
