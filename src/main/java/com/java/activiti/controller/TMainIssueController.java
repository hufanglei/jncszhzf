package com.java.activiti.controller;

import com.google.gson.JsonObject;
import com.java.activiti.dao.IssueDealMapper;
import com.java.activiti.dao.TMainIssueDao;
import com.java.activiti.dao.TsysSxgkzdMapper;
import com.java.activiti.dao.WorkdayDao;
import com.java.activiti.model.*;
import com.java.activiti.service.AttachmentService;
import com.java.activiti.service.MemberShipService;
import com.java.activiti.service.TMainIssueService;
import com.java.activiti.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/issues")
public class TMainIssueController {

    @Resource
    private TMainIssueDao tMainIssueMapper;

    @Resource
    private AttachmentService attachmentService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private MemberShipService memberShipService;

    @Resource
    private TMainIssueService tMainIssueService;

    @Resource
    private TsysSxgkzdMapper tsysSxgkzdMapper;
    @Resource
    private WorkdayDao workdayDao;
    @Resource
    private IssueDealMapper issueDealMapper;

    @Transactional
    @RequestMapping(value = "/listTMainIssue")
    public
    @ResponseBody
    String listTMainIssue(HttpServletRequest request) throws IOException {

        String rows = request.getParameter("rows");
        String page = request.getParameter("page");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");

        if (rows == null || "".equals(rows)) {
            rows = "10";
        }
        if (page == null || "".equals(page)) {
            page = "1";
        } else {
            int p = Integer.parseInt(page);
            int r = Integer.parseInt(rows);
            int pr = (p - 1) * r + 1;
            page = String.valueOf(pr);
        }
        if (sort == null || "".equals(sort)) {
            sort = "id ";
        }
        if (order == null || "".equals(order)) {
            order = " desc";
        }
        String orderString = sort + " " + order;


        Map map1 = new HashMap();
        int count = tMainIssueMapper.selectTMainIssueCountByClause(map1);
        map1.put("begin", Integer.parseInt(page) - 1);
        map1.put("pageSize", Integer.parseInt(rows));
        map1.put("orderString", orderString);
        List<TMainIssueBean> list = tMainIssueMapper.selectTMainIssueByClause(map1);


        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);

        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    @Transactional
    @RequestMapping(value = "/listTMainIssueForMap")
    public
    @ResponseBody
    String listTMainIssueForMap(HttpServletRequest request) throws IOException {

        Map map1 = new HashMap();
        map1.put("gis", true);
        int count = tMainIssueMapper.selectTMainIssueCountByClause(map1);
        List<TMainIssueBean> list = tMainIssueMapper.selectTMainIssueByClause(map1);

        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);

        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    @Transactional
    @RequestMapping(value = "/listTMainIssueForHeatmap")
    public
    @ResponseBody
    String listTMainIssueForHeatmap(HttpServletRequest request) throws IOException {

        Map map1 = new HashMap();
        map1.put("showall", true);
        List<HashMap<String, String>> list = tMainIssueMapper.selectTMainIssueForHeatmap(map1);

        Map map2 = new HashMap();
        map2.put("rows", list);

        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    @Transactional
    @RequestMapping(value = "/toEditTMainIssue")
    public
    @ResponseBody
    String toEditTMainIssue(HttpServletRequest request) throws IOException {

        String id = request.getParameter("id");
        TMainIssueBean tMainIssueBean = tMainIssueMapper.selectTMainIssueByPk(Integer.parseInt(id));

        JSONObject jSONObject = JSONObject.fromObject(tMainIssueBean, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    private int startProcess(HttpServletRequest request, String taskId, String comment, String status, TMainIssueBean tMainIssueBean) throws Exception {
        int result = -1;
        try {
            Map<String, Object> variables = new HashMap<String, Object>();
            MemberShip tMemberShip = (MemberShip) request.getSession().getAttribute("currentMemberShip");
            if (!Constants.isEmptyOrNull(status)) {
                if (status.equals("2")) {//自处置
                    variables.put("isHandleSelf", "1");
                    variables.put("userIds", tMemberShip.getUserId());
                    tMainIssueBean.setStatus(Const.STATUS_HANDLE_SELF);//自处置
                } else if (status.equals("3")) {//网格员上报
                    variables.put("isHandleSelf", "0");
                    tMainIssueBean.setStatus(Const.STATUS_WAIT_SUBDISTRICT_DISTRIBUTE);//待街道派发
//                    Map map = new HashMap();
//                    map.put("orgno", tMemberShip.getUser().getOrgNumber().substring(0, 9) + "000");
                    //根据网格员找到其所在街道的组id
                    //MemberShip jiedaoMemberSip = memberShipService.getMemberShipByClase(map);
                    String handleGroupId = memberShipService.getStreetGroupIdByUserId(tMemberShip.getUserId());
                    variables.put("handleGroupId", handleGroupId);
                    variables.put("userIds", tMemberShip.getUserId());
                } else if (status.equals("4")) {//区级中心提交
                    variables.put("isHandleSelf", "2");
                    variables.put("userIds", tMemberShip.getUserId());
                    tMainIssueBean.setStatus(Const.STATUS_WAIT_DISTRICT_CENTER_DISTRIBUTE);//待区级中心派发
                }

                variables.put("TMainIssueBean", tMainIssueBean);

                if (!Constants.isEmptyOrNull(tMainIssueBean.getProcessId())) {

                    // 设置用户id
                    Authentication.setAuthenticatedUserId(tMemberShip.getUserId() + "[" + tMemberShip.getGroup().getGroupName() + "]");
                    // 添加批注信息
                    taskService.addComment(taskId, tMainIssueBean.getProcessId(), comment);
                    // 完成任务
                    taskService.complete(taskId, variables);
                } else {
                    // 启动流程
                    //ProcessInstance pi = runtimeService.startProcessInstanceByKey("activitiJncszhzfProcess", variables);
//                    ProcessInstance pi = runtimeService.startProcessInstanceByKey("activitiJncszhzfProcessTest01", variables); //新添加的流程
                    ProcessInstance pi = runtimeService.startProcessInstanceByKey(ProcessInstanceConst.KEY, variables); //新添加的流程
                    // 根据流程实例Id查询任务
                    Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
                    // 完成填写上报事项任务
                    taskService.complete(task.getId(), variables);
                    // 更新主流程表，设置流程ID
                    tMainIssueBean.setProcessId(pi.getProcessInstanceId());


                }
                result = tMainIssueMapper.updateTMainIssue(tMainIssueBean);

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = -1;
        }
        return result;
    }


    /**
     * 上报案件
     *
     * @param tMainIssueBean 事件bean,操作类型 status 1：暂存 2：自处置 3：网格员上报 4：区级中心提交
     * @param request        请求
     * @return 成功：result>0 失败：result<=0
     * @throws Exception
     */
    @Transactional
    @RequestMapping(value = "/reportIssue")
    public
    @ResponseBody
    String reportIssue(String status, TMainIssueBean tMainIssueBean, String myfileData, HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        String issueNum = "";
        int result = -1;
        try {
            if (!Constants.isEmptyOrNull(status)) {
                tMainIssueBean.setId(null);
                MemberShip tMemberShip = (MemberShip) request.getSession().getAttribute("currentMemberShip");
                //判断当前用户是否为区级中心,如果是则设置是否区级中心上报标志为是
                //获取当前登录用户角色字典值
                String roleType = tMemberShip.getGroup().getGroupTag();
                if (Const.WANGGE_REPORT.equals(roleType)) {
                    //社区网格员上报
                    tMainIssueBean.setWhoReported("1");
                    tMainIssueBean.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                   /* Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date today = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(today);
                    c.add(Calendar.DAY_OF_MONTH, 2);// 今天+2天
                    Date newDate = c.getTime();*/
                    List  list=SetWeekDay.getEndTime(tMainIssueBean.getStartTime(),Const.TIME_LIMIT_TWO);
                   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                    int c=0;
                    int count = 0;
                    for(int i=0;i<list.size();i++){
                        System.out.println(list.get(i));
                        try {
                            count = workdayDao.selectWorkdayCountByWorkDay((String) list.get(i));
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        if(count!=0){
                            c+=count;
                        }
                    }
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
                    int total= Const.TIME_LIMIT_TWO+c;
                    c2.add(Calendar.DAY_OF_MONTH,total);
                    temp2 = sdf.format(c2.getTime());
                    tMainIssueBean.setTimeLimit(String.valueOf(total));
                    tMainIssueBean.setTotalTime( temp2);
                    issueNum = tMainIssueService.queryCaseNum("XC");
                    tMainIssueBean.setCaseType("XC");
                } else {
                    //区级中心上报
                    tMainIssueBean.setWhoReported("2");
                    issueNum = tMainIssueService.queryCaseNum("JN");
                    //保存经过区级的字段值
                    tMainIssueBean.setIsPassDistrictcenter("1");
                    tMainIssueBean.setCaseType("JN");
                }
//                tMainIssueBean.setIssueNumber(tMemberShip.getUser().getOrgNumber() + new SimpleDateFormat("yyMMddHHmmss").format(new Date()));//受理编号
                tMainIssueBean.setIssueNumber(issueNum);//受理编号
                tMainIssueBean.setAddUserid(tMemberShip.getUserId());//受理人
                tMainIssueBean.setAddTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                tMainIssueBean.setAddUserTel(tMemberShip.getUser().getTel());
                result = tMainIssueMapper.insertTMainIssue(tMainIssueBean);

                if (result > 0) {
                    // 流程信息插入成功，处理附件插入
                    int mainIssueId = tMainIssueBean.getId();
                    //上传文件目录
                    String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
                    if (StringUtils.isNotBlank(myfileData)) {
                        JSONArray jsonArray = JSONArray.fromObject(myfileData);
                        Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
                        for (UploadFileData file : list) {
                            result = attachmentService.addNewAttachment(rootDir, file, mainIssueId, tMainIssueBean.getAddUserid(), 1);
                            if (result < 0) {
                                obj.put("result", result);
                                return obj.toString();
                            }
                        }
                    }


                    // 附件插入完成，处理启动工作流
                    if (!status.equals("1")) {//如果是暂存，则不启动流程
                        result = startProcess(request, "", "", status, tMainIssueBean);
                    }
                    //查询processId 和taskid 保存到issue_deal表中
            List<HashMap<String,Object>>  list=tMainIssueMapper.selectProcessId(tMainIssueBean.getIssueNumber());
                    System.out.println(list);
                    IssueDeal issueDeal=new IssueDeal();
             if (Const.WANGGE_REPORT.equals(roleType)){
                 issueDeal.setIssueNumber(String.valueOf(list.get(0).get("issue_number")));
                 issueDeal.setActCommentId(String.valueOf(list.get(0).get("TASK_ID_")));
                 issueDeal.setProcessId(String.valueOf(list.get(0).get("PROC_INST_ID_")));
                 issueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                 issueDeal.setDealWay("已上报");
             }else {
                 issueDeal.setIssueNumber(String.valueOf(list.get(0).get("issue_number")));
                 issueDeal.setActCommentId(String.valueOf(list.get(0).get("TASK_ID_")));
                 issueDeal.setProcessId(String.valueOf(list.get(0).get("PROC_INST_ID_")));
                 issueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                 issueDeal.setDealWay("已录入");
             }
             issueDealMapper.insert(issueDeal);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }
        obj.put("result", result);
        return obj.toString();

    }

    @Transactional
    @RequestMapping(value = "/updateTMainIssue")
    public
    @ResponseBody
    Map updateTMainIssue(TMainIssueBean tMainIssueBean, HttpServletRequest request) throws IOException, ParseException {

        int result = 0;
        String errMessage = "";
        try {
            result = tMainIssueMapper.updateTMainIssue(tMainIssueBean);
        } catch (Exception e) {
            e.printStackTrace();
            errMessage = e.getCause().toString();
        }
        Map map = new HashMap();
        map.put("result", result);
        map.put("errMessage", errMessage);
        return map;

    }

    /**
     * 社区网格员处理
     *
     * @param status  2:自处置 3：上报
     * @param request 请求
     * @return
     * @throws Exception
     */
    @Transactional
    @RequestMapping(value = "/startupProcess")
    public String startupProcess(String status, String taskId, String comment, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject obj = new JSONObject();
        int result = -1;
        try {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            TMainIssueBean tMainIssueBean = tMainIssueMapper.selectTMainIssueByProcessId(task.getProcessInstanceId());
            result = startProcess(request, taskId, comment, status, tMainIssueBean);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (result == 1) {
            obj.put("success", "true");
        } else {
            obj.put("success", "false");
        }
        ResponseUtil.write(response, obj);
        return null;
    }


    @Transactional
    @RequestMapping(value = "/deleteTMainIssue")
    public
    @ResponseBody
    Map deleteTMainIssue(HttpServletRequest request) throws IOException {

        String id = request.getParameter("id");
        int result = 0;
        String errMessage = "";
        try {
            result = tMainIssueMapper.deleteTMainIssue(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
            errMessage = e.getCause().toString();
        }
        Map map = new HashMap();
        map.put("result", result);
        map.put("errMessage", errMessage);
        return map;
    }


    /**
     * 查询案件信息
     *
     * @param response
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/getIssueByTaskId")
    public String getIssueByTaskId(HttpServletResponse response, String taskId) throws Exception {

        //先根据流程ID查询
        if (Constants.isEmptyOrNull(taskId)) {
            return null;
        }
        JSONObject result = new JSONObject();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        TMainIssueBean tMainIssueBean = tMainIssueMapper.selectTMainIssueByProcessId(task.getProcessInstanceId());
        result.put("tMainIssueBean", tMainIssueBean);
        ResponseUtil.write(response, result);
        return null;
    }


    /**
     * 查询案件信息
     *
     * @param response
     * @param processId
     * @return
     * @throws Exception
     */
    @RequestMapping("/getIssueByProcessId")
    public String getIssueByProcessId(HttpServletResponse response, String processId) throws Exception {

        //先根据流程ID查询
        if (Constants.isEmptyOrNull(processId)) {
            return null;
        }
 /*       String  dealWay=tMainIssueMapper.selectDealWay(processId);*/
        JSONObject result = new JSONObject();
        TMainIssueBean tMainIssueBean;
     /*   if (dealWay.equals("1")){
            tMainIssueBean = tMainIssueMapper.selectIssueByProcessId(processId);
        }else{*/
            tMainIssueBean = tMainIssueMapper.selectTMainIssueByProcessId(processId);



        result.put("tMainIssueBean", tMainIssueBean);
        ResponseUtil.write(response, result);
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    /**
     * 已办业务分页查询
     *
     * @param page 当前页数
     * @param rows 每页显示页数
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/ybywPage")
    public String ybywPage(HttpServletResponse response, String startDay, String endDay, String page, String rows, HttpSession session) throws Exception {
        //根据会话信息获取当前登录账号
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户
        User currentUser = currentMemberShip.getUser();
        String userId = currentUser.getId();

        Map map1 = new HashMap();
        map1.put("status", "16");
        map1.put("createTime", startDay);
        map1.put("endTime", endDay);
        map1.put("userId", userId);
        int count = tMainIssueMapper.selectTMainIssueCountByClause(map1);
        map1.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
        map1.put("pageSize", Integer.parseInt(rows));
        map1.put("orderString", "id desc");
        List<TMainIssueBean> list = tMainIssueMapper.selectTMainIssueByClause(map1);


        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);

        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        ResponseUtil.write(response, jSONObject);
        return null;
    }

    /**
     * 案件跟踪业务分页查询
     *
     * @param page 当前页数
     * @param rows 每页显示页数
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajgzPage")
    public String ajgzPage(HttpServletResponse response, String startDay, String endDay, String page, String rows, HttpSession session) throws Exception {
        //根据会话信息获取当前登录账号
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户
        User currentUser = currentMemberShip.getUser();
        String userId = currentUser.getId();

        Map map1 = new HashMap();
        //map1.put("status", "16");
        map1.put("createTime", startDay);
        map1.put("endTime", endDay);
        if (1 != currentUser.getUserTypeID()) { //如果不是网格员
            userId = userId + "[" + currentMemberShip.getGroup().getGroupName() + "]";
        }
        map1.put("userId", userId);
        int count = tMainIssueMapper.selectTMainIssueCountAjgz(map1);
        map1.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
        map1.put("pageSize", Integer.parseInt(rows));
        map1.put("orderString", "id desc");
        List<TMainIssueBean> list = tMainIssueMapper.selectTMainIssueAjgz(map1);


        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);

        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        ResponseUtil.write(response, jSONObject);
        return null;
    }


    /**
     * 查询案件信息分布
     *
     * @return
     */
    @RequestMapping("/queryIssueMap")
    @ResponseBody
    public List<Map<String, String>> queryIssueMap(HttpServletRequest request) {
        String beginDate = request.getParameter("beginDate") + "";
        String endDate = request.getParameter("endDate") + "";
        String groupId = request.getParameter("groupId") + "";
        String groupTypeId = request.getParameter("groupTypeId") + "";
        List<Map<String, String>> list = tMainIssueService.queryIssueMap(beginDate, endDate, groupId, groupTypeId);
        return list;
    }

    /**
     * 查询流程下的附件
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/getAttachments/{taskId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<TAttachmentBean>> getAttachments(@PathVariable String taskId) {
        return tMainIssueService.queryAttachments(taskId);
    }

    /**
     * 显示附件
     *
     * @param response 响应
     * @param path     文件名
     */
    @RequestMapping("/showAttachment")
    @ResponseBody
    public void showImage(HttpServletResponse response, String path) {
        tMainIssueService.showAttachment(response, path);
    }

    /**
     * 根据案件编号查询案件信息
     */
    @RequestMapping("/getIssueAndProcessInfoByIssueNumber")
    public String getIssueAndProcessInfoByIssueNumber(HttpServletResponse response, String issueNumber) throws Exception {
        //根据普通案件编号异步查询
        //根据案件编号获取 流程id，获取历史任务信息 和批注信息
        JSONObject issueAndProcessInfoByIssueNumber = tMainIssueService.getIssueAndProcessInfoByIssueNumber(issueNumber);
        ResponseUtil.write(response, issueAndProcessInfoByIssueNumber);
        return null;
    }

    /**
     * 显示上报人姓名
     */
    @RequestMapping("/showAddUserNameById")
    public String showAddUserNameById(String addUserId, HttpServletResponse response) throws Exception {
        String addUserName = tMainIssueService.showAddUserNameById(addUserId);
        JSONObject result = new JSONObject();
        result.put("addUserName", addUserName);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 查询事项归口
     *
     * @param parentId 父节点id
     * @return
     */
    @RequestMapping("/getSxgkList")
    @ResponseBody
    public List<Map<String, Object>> getSxgkList(String parentId) {
        List<Map<String, Object>> list = tsysSxgkzdMapper.selectSxgkzdList(parentId);
        return list;
    }

    /**
     * 生成案件编码
     *
     * @return
     */
    public String queryCaseNum(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        String caseNum = tMainIssueService.queryCaseNum(type);
        JSONObject result = new JSONObject();
        result.put("caseNum", caseNum);
        try {
            ResponseUtil.write(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
