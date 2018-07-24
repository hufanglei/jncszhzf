package com.java.activiti.controller;

import com.java.activiti.dao.*;
import com.java.activiti.model.*;
import com.java.activiti.model.dto.TMainIssueDTO;
import com.java.activiti.service.*;
import com.java.activiti.util.Constants;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/jxkh")
public class TMainIssueKhController {
    @Resource
    private AttachmentService attachmentService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private GdgxService gdgxService;
    @Resource
    private TMainIssueDao tMainIssueMapper;
    @Resource
    private TMainIssueService tMainIssueService;


    //绩效考核分数显示
    @Resource
    private TMainIssueKhDao tMainIssueKhMapper;
    @Resource
    private TMainIssueKhService tMainIssueKhService;
    //案件比重
    @Resource
    private TissueRadioKhMapper tIssueRadioKhMapper;
    //案件比重参数
    @Resource
    private TissueRadioSetKhMapper tissueRadioSetKhMapper;
    //宣传报道考核
    @Autowired
    private TIssueXcbdKhMapper xcbdKhMapper;
    //巡查机制考核
    @Autowired
    private TissueXcjzKhMapper xcjzKhMapper;
    //监督检查考核
    @Autowired
    private TissueJdjcKhMapper jdjcKhMapper;
    //督察督办
    @Resource
    private TissueDcdbKhMapper tissueDcdbKhMapper;
    @Resource
    private TissueDcdbKhService tissueDcdbKhService;
    @Resource
    private TIssueXcbdKhMapper tIssueXcbdKhMapper;

    /**
     * 综合执法工作考核分页查询
     * 月度统计
     *
     * @param response
     * @param page     当前页数
     * @param rows     每页显示页数
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/zhzfgzkhPage")
    public @ResponseBody
    String zhzfgzkhPage(HttpServletResponse response, String khyf, String page, String rows, HttpSession session, String bmType) throws Exception {
        //根据会话信息获取当前登录账号
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");

        String qhzdId = "";

//        //取出用户
//        User currentUser = currentMemberShip.getUser();
//        if (currentUser.getUserTypeID() != 0) {
//            if (CommonUtils.isEmpty(currentUser.getJiedao())) {
//                qhzdId = "-1";
//            } else {
//                qhzdId = currentUser.getJiedao().substring(0, 12);
//            }
//        }

        Map map1 = new HashMap();
        map1.put("qhzdId", qhzdId);
        map1.put("khyf", khyf);
        map1.put("bmType", bmType);

        int count = tMainIssueKhMapper.selectCount(map1);

        /*map1.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
        map1.put("pageSize", Integer.parseInt(rows));*/
        map1.put("orderString", "id desc");
        List<TMainIssueKhBean> list = tMainIssueKhMapper.selectListPage(map1);

        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);

        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    /**
     * 绩效考核年度统计
     *
     * @param khyf
     * @param page
     * @param rows
     * @param bmType
     * @return
     * @throws Exception
     */
    @RequestMapping("/zhzfPage")
    public @ResponseBody
    String zhzfPage(String khyf, String page, String rows, String bmType) throws Exception {
        Map map1 = new HashMap();
        map1.put("khyf", khyf);
        map1.put("bmType", bmType);
        int count = tMainIssueKhMapper.selectCountZhzf(map1);
     /*   map1.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
        map1.put("pageSize", Integer.parseInt(rows));*/

        List<TMainIssueKhBean> list = tMainIssueKhMapper.selectZhzf(map1);
        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);
        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    /**
     * 绩效考核季度统计
     *
     * @param khyf
     * @param page
     * @param rows
     * @param bmType
     * @param jdfreshNum
     * @return
     * @throws Exception
     */
    @RequestMapping("/zhzfJdPage")
    public @ResponseBody
    String zhzfJdPage(String khyf, String page, String rows, String bmType, String jdfreshNum) throws Exception {
        Map map1 = new HashMap();
        String startMouth = "";
        String endMouth = "";
        if (jdfreshNum == "1" || jdfreshNum.equals("1")) {
            startMouth = khyf + "-" + "01";
            endMouth = khyf + "-" + "03";
        } else if (jdfreshNum == "2" || jdfreshNum.equals("2")) {
            startMouth = khyf + "-" + "04";
            endMouth = khyf + "-" + "06";
        } else if (jdfreshNum == "3" || jdfreshNum.equals("3")) {
            startMouth = khyf + "-" + "07";
            endMouth = khyf + "-" + "09";
        } else if (jdfreshNum == "4" || jdfreshNum.equals("4")) {
            startMouth = khyf + "-" + "10";
            endMouth = khyf + "-" + "12";
        }

        map1.put("start", startMouth);
        map1.put("end", endMouth);
        map1.put("bmType", bmType);
        int count = tMainIssueKhMapper.selectCountjd(map1);
    /*    map1.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
        map1.put("pageSize", Integer.parseInt(rows));
*/
        List<TMainIssueKhBean> list = tMainIssueKhMapper.zhzfJdPage(map1);
        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);
        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        return jSONObject.toString();
    }


    /**
     * 案件比重 列表
     *
     * @param response
     * @param page     页码
     * @param rows
     * @param khyf     考核的月份
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/issueRadio")
    @ResponseBody
    public String issueRadio(HttpServletResponse response, String page, String rows, String khyf, HttpSession session) throws Exception {
        Map map1 = new HashMap();
        //如果没有传递月份，则显示的是上个月的月份
        if (khyf == null || "".equals(khyf)) {
//            khyf = CodingUtil.getMonthByCalender(-1);

            map1.put("total", 0);
            map1.put("rows", new ArrayList<>());
            return JSONObject.fromObject(map1, Constants.getJsonConfig()).toString();
        }
        Calendar calendar = Calendar.getInstance();
        String year1 = calendar.get(Calendar.YEAR) + "";
        String month1 = (calendar.get(Calendar.MONTH) - 1) < 10 ? ("0" + (calendar.get(Calendar.MONTH) - 1)) : (calendar.get(Calendar.MONTH) - 1) + "";//本月

        String newKhyf = year1 + "-" + month1;
        map1.put("khyf", khyf);
        int count = tIssueRadioKhMapper.selectCount(map1);
       /* map1.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
        map1.put("pageSize", Integer.parseInt(rows));*/
        map1.put("orderString", "id desc");
        List<TissueRadioKhBean> list = tIssueRadioKhMapper.selectListPage(map1);
        Map map2 = new HashMap();
        Map map3 = new HashMap();
        map3.put("khyf", khyf);
        map3.put("newKhyf", newKhyf);
        for (TissueRadioKhBean tissueRadioKhBean : list) {

            if (tissueRadioKhBean.getAvg12345num() != null) {
                map2.put("total", count);
                map2.put("rows", list);
            } else {
                List<TissueRadioKhBean> list1 = tIssueRadioKhMapper.selectPageList(map3);
                map2.put("total", count);
                map2.put("rows", list1);
            }
        }

        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    /**
     * 保存 案件比重参数
     */
    @RequestMapping("/saveRadioSet")
    @ResponseBody
    public int saveRadioSet(HttpServletResponse response, TissueRadioSetKhBean tissueRadioSetKhBean) throws Exception {
        return tissueRadioSetKhMapper.insertSelective(tissueRadioSetKhBean);
    }

    @RequestMapping("/updateAjbz")
    @ResponseBody
    public int updateAjbz(String khyf, String avg12345num, String coefficient) {
        int result = 0;
        try {
            result = tMainIssueKhService.updateAjbzInfo(khyf, avg12345num, coefficient);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 保存案件比重考核评分
     */

    @RequestMapping("/saveRadioKh")
    @ResponseBody
    public int saveRadioKh(HttpServletRequest request, String khyf) throws Exception {

        int result = 0;
        try {
            result = tMainIssueKhService.saveRadioKh(request, khyf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 督察督办 列表
     *
     * @param response
     * @param page     页码
     * @param rows
     * @param khyf     考核的月份
     * @param jgmc     机构名称
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/listDcdbKh")
    @ResponseBody
    public String listDcdbKh(HttpServletResponse response, String page, String rows, String khyf, String jgmc, HttpSession session) throws Exception {
        //根据会话信息获取当前登录账号
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户
        User currentUser = currentMemberShip.getUser();
        String userId = currentUser.getId();
        Map map1 = new HashMap();
        //如果没有传递月份，则显示的是上个月的月份
//        if(StringUtils.isBlank(khyf)){
//            khyf = CodingUtil.getMonthByCalender(-1);
//        }
        map1.put("khyf", khyf);
        map1.put("qhzdName", jgmc);
        int count = tissueDcdbKhMapper.selectCount(map1);
        map1.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
        map1.put("pageSize", Integer.parseInt(rows));
        map1.put("orderString", "id desc");
        List<TissueDcdbKhBean> list = tissueDcdbKhMapper.selectListPage(map1);
        //給督办表添加taskId字段向前台展示用
        //有想法的话，可以去数据库观察  ACT_RU_TASK 的变化
        List<Task> taskList = taskService.createTaskQuery()
                // 根据用户id查询
                .taskCandidateUser(userId)
                // 根据任务名称查询
                .taskNameLike("%" + "考核组派发" + "%").list();
        //封装返回页面的数据
        List<TissueDcdbKhTask> dcdbList = new ArrayList<TissueDcdbKhTask>();
        for (TissueDcdbKhBean bean : list) {
            TissueDcdbKhTask tissueDcdbKhTask = new TissueDcdbKhTask();
            BeanUtils.copyProperties(bean, tissueDcdbKhTask);
            for (Task t : taskList) {
                String processId = t.getProcessInstanceId();
                String taskId = t.getId();
                if (processId.equals(bean.getProcessId())) {
                    tissueDcdbKhTask.setTaskId(taskId);
                    break;
                }
            }
            if (tissueDcdbKhTask.getTaskId() == "" || tissueDcdbKhTask.getTaskId() == null) {
                tissueDcdbKhTask.setTaskId("0");
            }
            dcdbList.add(tissueDcdbKhTask);
        }
        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", dcdbList);
        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        return jSONObject.toString();
    }


    /**
     * 通过id查询实体，用于修改
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getXcbd")
    public String getTissueXcbdKh(Integer id) {
        TIssueXcbdKh xcbdKh = xcbdKhMapper.selectByPrimaryKey(id);
        return JSONObject.fromObject(xcbdKh).toString();
    }


    /**
     * 分页查询
     *
     * @param month
     * @return
     */
    @ResponseBody
    @RequestMapping("/findTissueXcbdKh")
    public String findTissueXcbdKh(String page, String rows, String month) {
        Map<String, Object> map = new HashMap<>();
        map.put("month", month);
        int count = xcbdKhMapper.countByMonth(map);
        map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
        map.put("pageSize", Integer.parseInt(rows));
        List<TIssueXcbdKh> list = xcbdKhMapper.selectByMonth(map);

        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);

        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    /**
     * @param response
     * @param khyf
     * @param columName
     * @param jgmc
     * @return
     */
    @RequestMapping("/showXcbdDetails")
    public String showXcbdDetails(HttpServletResponse response, String khyf, String columName, String jgmc) {
        Map<String, Object> map = new HashMap<>();
        map.put("khyf", khyf);
        map.put("jgmc", jgmc);
        List<TIssueXcbdKh> list = null;
        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        list = tIssueXcbdKhMapper.showXcbdDetails(map);
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        result.put("rows", jsonArray);
        try {
            ResponseUtil.write(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 巡查机制分页查询
     *
     * @param month
     * @return
     */
    @ResponseBody
    @RequestMapping("/findTissueXcjzKh")
    public String findTissueXcjzKh(String page, String rows, String month) {
        Map<String, Object> map = new HashMap<>();
        map.put("month", month);
        int count = tissueDcdbKhMapper.countXcjzByMonth(map);
        map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
        map.put("pageSize", Integer.parseInt(rows));
        List<TissueDcdbKhBean> list = tissueDcdbKhMapper.selectXcjzByMonth(map);


        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);

        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    /**
     * 监督检查分页查询
     *
     * @param month
     * @return
     */
    @ResponseBody
    @RequestMapping("/findTissueJdjcKh")
    public String findTissueJdjcKh(String page, String rows, String month) {
        Map<String, Object> map = new HashMap<>();
        map.put("month", month);
        int count = jdjcKhMapper.countByMonth(map);
        map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
        map.put("pageSize", Integer.parseInt(rows));
        List<TissueJdjcKhBean> list = jdjcKhMapper.selectByMonth(map);

        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);

        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    /**
     * 保存或修改
     *
     * @param xcbdKh
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveOrUpdateXcbdKh")
    public Map saveOrUpdateXcbdKh(TIssueXcbdKh xcbdKh) {
        int result = 0;
        String errMessage = "";

        TMainIssueKhBean tMainIssueKhBean = null;
        try {
            xcbdKh.setCreateTime(new Date());
            Float score = tMainIssueKhService.getScore(xcbdKh);
            xcbdKh.setScore(score);
            if (xcbdKh.getId() == null) {
                result = xcbdKhMapper.insert(xcbdKh);
            } else {
                result = xcbdKhMapper.updateByPrimaryKey(xcbdKh);
            }
            Map map1 = new HashMap();
            map1.put("qhzdDm", xcbdKh.getQhzdDm());
            map1.put("khyf", xcbdKh.getKhyf());

            tMainIssueKhBean = tMainIssueKhMapper.selectXcbd(map1);

            if (tMainIssueKhBean == null) {
                TMainIssueKhBean tMainIssueKhBean1 = new TMainIssueKhBean();
                tMainIssueKhBean1.setQhzdId(xcbdKh.getQhzdDm());
                tMainIssueKhBean1.setQhzdName(xcbdKh.getQhzdMc());
                tMainIssueKhBean1.setKhyf(xcbdKh.getKhyf());
                tMainIssueKhBean1.setKhsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                tMainIssueKhBean1.setXcbd(String.valueOf(score));
                tMainIssueKhMapper.insertTMainIssueKh(tMainIssueKhBean1);
            } else {
                tMainIssueKhBean.setXcbd(String.valueOf(score));
                tMainIssueKhMapper.updateTMainIssueXcbd(tMainIssueKhBean);
            }

        } catch (Exception e) {
            e.printStackTrace();
            errMessage = e.getCause().toString();
        }
        Map map = new HashMap();
        map.put("result", result);
        map.put("errMessage", errMessage);
        return map;
    }


    @RequestMapping("/deleteXcbdKh")
    @ResponseBody
    public Map deleteXcbdKh(HttpServletRequest request) {
        Boolean userResult = true;
        try {
            String ids = request.getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                for (String id : ids.split(",")) {
                    xcbdKhMapper.deleteByPrimaryKey(Integer.valueOf(id));
                }
            } else {
                userResult = false;
            }
        } catch (Exception e) {
            userResult = false;
        }

        Map map = new HashMap();
        map.put("success", userResult);
        return map;
    }

    /**
     * 保存巡查机制
     *
     * @param request
     * @param record
     * @return
     */
    @RequestMapping("/saveXcjzKh")
    public String saveXcjzKh(HttpServletResponse response, HttpServletRequest request, TissueXcjzKhBean record, String groupId, String groupName) throws Exception {
        System.out.println("===333333==============");
        MemberShip currentMemberShip = (MemberShip) request.getSession().getAttribute("currentMemberShip");
        //巡查机制来源 （委办局用户id）
        record.setSourceId(currentMemberShip.getUser().getUserName());
        // 此处需要保存 案件的 事项归口: 即 街道的或者委办局的组id，之前是写死的，东山街道的吧,现在已修改
        record.setQhzdId(groupId);
        record.setQhzdName(groupName);
        record.setStatus("2");
        //默认未审核  --2
        record.setCreateTime(new Date());
        int row = xcjzKhMapper.insertSelective(record);
        JSONObject result = new JSONObject();
        if (row == 1) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }


    /**
     * 巡查机制删除
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteXcjzKh")
    @ResponseBody
    public Map deleteXcjzKh(HttpServletRequest request) {
        Boolean userResult = true;
        try {
            String ids = request.getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                for (String id : ids.split(",")) {
                    xcjzKhMapper.deleteByPrimaryKey(Integer.valueOf(id));
                }
            } else {
                userResult = false;
            }
        } catch (Exception e) {
            userResult = false;
        }

        Map map = new HashMap();
        map.put("success", userResult);
        return map;
    }

    /**
     * 保存监督检查
     *
     * @param request
     * @param record
     * @return
     */
    @RequestMapping("/saveJdjcKh")
    public String saveJdjcKh(HttpServletResponse response, HttpServletRequest request, TissueJdjcKhBean record, String groupId, String groupName) throws Exception {
        System.out.println("===33333dddd==============");
        MemberShip currentMemberShip = (MemberShip) request.getSession().getAttribute("currentMemberShip");
        //巡查机制来源 （委办局用户id）
        record.setSourceId(currentMemberShip.getUser().getUserName());
        //此处需要保存 案件的 事项归口: 即 街道的或者委办局的组id，之前是写死的，东山街道的吧,现在已修改
        record.setQhzdId(groupId);
        record.setQhzdName(groupName);
        record.setStatus("2");
        //默认未审核--2
        record.setCreateTime(new Date());
        int row = jdjcKhMapper.insertSelective(record);
        JSONObject result = new JSONObject();
        if (row == 1) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 监督检查删除
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteJdjcKh")
    @ResponseBody
    public Map deleteJdjcKh(HttpServletRequest request) {
        Boolean userResult = true;
        try {
            String ids = request.getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                for (String id : ids.split(",")) {
                    jdjcKhMapper.deleteByPrimaryKey(Integer.valueOf(id));
                }
            } else {
                userResult = false;
            }
        } catch (Exception e) {
            userResult = false;
        }

        Map map = new HashMap();
        map.put("success", userResult);
        return map;
    }

    /**
     * 校验案件编号
     */
    @RequestMapping("/selectIssueNum")
    @ResponseBody
    public int selectIssueNum(String issueNum) {
        int result = tissueDcdbKhMapper.selectIssueNum(issueNum);

        return result;
    }

    /**
     * 保存督办督查
     *
     * @param response
     * @param bean
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveDcdbInfo")
    public String saveDcdbInfo(HttpServletResponse response, TissueDcdbKhBean bean, String myfileData, HttpSession session) throws Exception {
        int userResult = tMainIssueKhService.saveorUpdateTissueDcdbKhResult(bean, myfileData, session);
        JSONObject json = new JSONObject();
        if (userResult > 0) {
            json.put("success", true);
        } else {
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
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
        TMainIssueDTO tMainIssueBean = tissueDcdbKhMapper.selectTMainIssueByProcessId(task.getProcessInstanceId());
        //TODO 根据taskid获取 督办案件信息
        result.put("tDbMainIssueBean", tMainIssueBean);
        ResponseUtil.write(response, result);
        return null;
    }


    /**
     * 删除督察督办信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delDcdbInfo")
    public String delDcdbInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Boolean userResult = true;
        try {
            String ids = request.getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                for (String id : ids.split(",")) {
                    tissueDcdbKhMapper.deleteByPrimaryKey(Integer.valueOf(id));
                }
            } else {
                userResult = false;
            }
        } catch (Exception e) {
            userResult = false;
        }

        JSONObject json = new JSONObject();
        json.put("success", userResult);
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 宣传报道校验街道and月份事件的唯一性
     *
     * @return
     */
    @RequestMapping("/checkXcbd")
    @ResponseBody
    public int checkXcbd(String qhzdDm, String khyf) {
        int result = 0;
        Map map = new HashMap();
        map.put("qhzdDm", qhzdDm);
        map.put("khyf", khyf);
        result = xcbdKhMapper.checkXcbd(map);
        return result;
    }

    @RequestMapping("/selectGdDcdbKh")
    @ResponseBody
    public String selectGdDcdbKh(HttpServletRequest request, String startDay, String endDay, String page, String rows, String groupId, String groupTypeId, HttpSession session) {
        Map map = tissueDcdbKhService.selectGdDcdbKh(request, startDay, endDay, page, rows, groupId, groupTypeId, session);
        JSONObject jSONObject = JSONObject.fromObject(map, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    /**
     * 督办案件归档查询中的附件查询
     *
     * @param processId
     * @return
     */
    @RequestMapping("/selecAttachList")
    @ResponseBody
    public List<Map<String, Object>> selecDbAttachList(String processId) {
        List<Map<String, Object>> list = gdgxService.selecDbAttachList(processId);
        return list;
    }

    /**
     * 归档案件   督办案件中的案件详情查询
     *
     * @param response
     * @param processId
     * @return
     * @throws Exception
     */
    @RequestMapping("/getDbIssueByProcessId")
    public String getDbIssueByProcessId(HttpServletResponse response, String processId) throws Exception {

        //先根据流程ID查询
        if (Constants.isEmptyOrNull(processId)) {
            return null;
        }
        JSONObject result = new JSONObject();
        TMainIssueBean tMainIssueBean = tMainIssueMapper.getDbIssueByProcessId(processId);

        result.put("tMainIssueBean", tMainIssueBean);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/getDbIssueByProcess")
    public String getDbIssueByProcess(HttpServletResponse response, String processId) throws Exception {
        //先根据流程ID查询
        if (Constants.isEmptyOrNull(processId)) {
            return null;
        }
        System.out.println(processId);
        TissueDcdbKhBean tissueDcdbKhBean = tissueDcdbKhService.getDbIssueByProcess(processId);
        JSONObject result = new JSONObject();
        result.put("tissueDcdbKhBean", tissueDcdbKhBean);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 查询普通案件扣分的案件
     *
     * @param response
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/selectZxclDetails")
    public String selectZxclDetails(HttpServletResponse response, String khyf, String columName, String jgmc,String page,String rows,String num) throws Exception {
        System.out.println(jgmc);
        Map map = new HashMap();
        map.put("khyf", khyf);
        map.put("jgmc", jgmc);
        int count=0;
        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<TMainIssueBean> list = null;
        if (columName.equals("zxcl")) {
                //全部
            if (num.equals("1")){
                count=tMainIssueMapper.selectCountZxcl(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tMainIssueService.selectZxclDetails(map);
            }else if (num.equals("2")){
                map.put("num", "1");
                count=tMainIssueMapper.selectRefreshCountZxcl(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tMainIssueService.refreshZxclDetails(map);
            }else if (num.equals("3")){
                map.put("num", "2");
                count=tMainIssueMapper.selectRefreshCountZxcl(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tMainIssueService.refreshZxclDetails(map);
            }

        } else if (columName.equals("ajbz")) {
            if (num.equals("1")){
                count=tMainIssueMapper.selectCountAjbz(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tMainIssueService.selectAjbzDetails(map);
            }else if (num.equals("2")){
                map.put("num","1");
                count=tMainIssueMapper.selectCountZcAjbz(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tMainIssueService.selectZcAjbzDetails(map);
            }else if (num.equals("3")){
                map.put("num","2");
                count=tMainIssueMapper.selectCountZcAjbz(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tMainIssueService.selectZcAjbzDetails(map);
            }


        } else if (columName.equals("ascl")) {

            if (num.equals("1")) {
                count=tMainIssueMapper.selectCountAscl(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tMainIssueService.selectAsclDetails(map);
            } else if (num.equals("2")) {
                count=tMainIssueMapper.selectCountZcAscl(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tMainIssueService.refreshZcAsclDetails(map);
            } else if (num.equals("3")) {
                count=tMainIssueMapper.selectCountKfAscl(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tMainIssueService.refreshAsclDetails(map);
            }
        } else if (columName.equals("kscl")) {

            if (num.equals("1")){
                count=tMainIssueMapper.selectCountKscl(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list=tMainIssueService.selectKsclDetails(map);
            }else  if (num.equals("2")){
                count=tMainIssueMapper.selectCountZcKscl(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list=tMainIssueService.refreshZcKsclDetails(map);
            }else  if (num.equals("3")){
                count=tMainIssueMapper.selectCountKfKscl(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list=tMainIssueService.refreshKsclDetails(map);
            }
        } else if (columName.equals("wzxgfx")) {

            if (num.equals("1")) {
                count=tMainIssueMapper.selectCountWzxgfx(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tMainIssueService.selectWzxDetails(map);
            } else if (num.equals("2")) {

                map.put("counts", "2");
                count=tMainIssueMapper.selectCountZcWzxgfx(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tMainIssueService.selectZcWzxDetails(map);
            } else if (num.equals("3")) {
                map.put("counts", "1");
                count=tMainIssueMapper.selectCountWzxgfx(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tMainIssueService.selectZcWzxDetails(map);
            }
        }
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", count);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 查询普通案件扣分的案件数量  用来计算公式数据展示
     *
     * @param response
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/selectJsgs")
    public String selectJsgs(HttpServletResponse response, String khyf, String columName, String jgmc) throws Exception {
        System.out.println(jgmc);
        Map map = new HashMap();
        map.put("khyf", khyf);
        map.put("jgmc", jgmc);

        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<TMainIssueBean> list = null;
        if (columName.equals("zxcl")) {
            list = tMainIssueService.selectJsgs(map);
        } else if (columName.equals("ajbz")) {
               list=tMainIssueService.selectAjbzJsgs(map);
        } else if (columName.equals("ascl")) {
            list = tMainIssueService.selectAsclJsgs(map);
        } else if (columName.equals("kscl")) {
            list=tMainIssueService.selectKsclJsgs(map);
        } else if (columName.equals("wzxgfx")) {
            list = tMainIssueService.selectWzxJsgs(map);
        }
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 全部  正常项  扣分项 刷新
     *
     * @param response
     * @param khyf
     * @param columName
     * @param jgmc
     * @param num
     * @return
     * @throws Exception
     */
   /* @RequestMapping("/refreshZxclDetails")
    public String refreshZxclDetails(HttpServletResponse response, String khyf, String columName, String jgmc, String num) throws Exception {
        System.out.println(jgmc);
        Map map = new HashMap();
        map.put("khyf", khyf);
        map.put("jgmc", jgmc);
        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<TMainIssueBean> list = null;
        if (columName.equals("zxcl")) {
            if (num.equals("1")) {
                list = tMainIssueService.selectZxclDetails(map);
            } else if (num.equals("2")) {
                map.put("num", "1");
                list = tMainIssueService.refreshZxclDetails(map);
            } else if (num.equals("3")) {
                map.put("num", "2");
                list = tMainIssueService.refreshZxclDetails(map);
            }
        } else if (columName.equals("ajbz")) {
          *//*  if (num.equals("1")){
                list=tMainIssueService.selectAsclDetails(map);
            }else  if (num.equals("2")){
                list=tMainIssueService.refreshZcAsclDetails(map);
            }else  if (num.equals("3")){
                list=tMainIssueService.refreshAsclDetails(map);
            }*//*
        } else if (columName.equals("ascl")) {
            if (num.equals("1")) {
                list = tMainIssueService.selectAsclDetails(map);
            } else if (num.equals("2")) {
                list = tMainIssueService.refreshZcAsclDetails(map);
            } else if (num.equals("3")) {
                list = tMainIssueService.refreshAsclDetails(map);
            }
        } else if (columName.equals("kscl")) {
            if (num.equals("1")){
                list=tMainIssueService.selectKsclDetails(map);
            }else  if (num.equals("2")){
                list=tMainIssueService.refreshZcKsclDetails(map);
            }else  if (num.equals("3")){
                list=tMainIssueService.refreshKsclDetails(map);
            }
        } else if (columName.equals("wzxgfx")) {
            if (num.equals("1")) {
                list = tMainIssueService.selectWzxDetails(map);
            } else if (num.equals("2")) {
                map.put("counts", "2");
                list = tMainIssueService.selectZcWzxDetails(map);
            } else if (num.equals("3")) {
                map.put("counts", "1");
                list = tMainIssueService.selectZcWzxDetails(map);
            }
        }
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }
*/
    /**
     * 查询督办案件扣分的案件
     *
     * @param response
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/showdcdbDetails")
    public String showdcdbDetails(HttpServletResponse response, String khyf, String columName, String jgmc,String page,String rows,String num) throws Exception {
        Map map = new HashMap();
        map.put("khyf", khyf);
        map.put("jgmc", jgmc);
        int count =0;

        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<TissueDcdbKhBean> list = null;
        if (columName.equals("ffts")) {

            map.put("sourceId", "反复投诉");
            if (num.equals("1")) {
                count=tissueDcdbKhService.selectCountFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.selectffts(map);
            } else if (num.equals("2")) {
                count=tissueDcdbKhService.selectCountZcFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.refreshZcDcdbDetails(map);
            } else if (num.equals("3")) {
                count=tissueDcdbKhService.selectCountKfFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.refreshDcdbDetails(map);
            }
        } else if (columName.equals("dbwz")) {
            map.put("sourceId", "督办问责");
            if (num.equals("1")) {
                count=tissueDcdbKhService.selectCountFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.selectffts(map);
            } else if (num.equals("2")) {
                count=tissueDcdbKhService.selectCountZcFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.refreshZcDcdbDetails(map);
            } else if (num.equals("3")) {
                count=tissueDcdbKhService.selectCountKfFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.refreshDcdbDetails(map);
            }
        } else if (columName.equals("xcjz")) {
            map.put("sourceId", "巡查机制");
            if (num.equals("1")) {
                count=tissueDcdbKhService.selectCountFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.selectffts(map);
            } else if (num.equals("2")) {
                count=tissueDcdbKhService.selectCountZcFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.refreshZcDcdbDetails(map);
            } else if (num.equals("3")) {
                count=tissueDcdbKhService.selectCountKfFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.refreshDcdbDetails(map);
            }
        } else if (columName.equals("jdjc")) {
            map.put("sourceId", "监督检查");
            count=tissueDcdbKhService.selectCountFfts(map);
            if (num.equals("1")) {
                count=tissueDcdbKhService.selectCountFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.selectffts(map);
            } else if (num.equals("2")) {
                count=tissueDcdbKhService.selectCountZcFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.refreshZcDcdbDetails(map);
            } else if (num.equals("3")) {
                count=tissueDcdbKhService.selectCountKfFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.refreshDcdbDetails(map);
            }
        } else if (columName.equals("gzxt")) {
            map.put("sourceId", "工作协调");
            if (num.equals("1")) {
                count=tissueDcdbKhService.selectCountFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.selectffts(map);
            } else if (num.equals("2")) {
                count=tissueDcdbKhService.selectCountZcFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.refreshZcDcdbDetails(map);
            } else if (num.equals("3")) {
                count=tissueDcdbKhService.selectCountKfFfts(map);
                if (count>0){
                    map.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
                    map.put("pageSize", Integer.parseInt(rows));
                }
                list = tissueDcdbKhService.refreshDcdbDetails(map);
            }
        }
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", count);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 查询普通案件扣分的案件数量  用来计算公式数据展示
     *
     * @param response
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/selectDcdbJsgs")
    public String selectDcdbJsgs(HttpServletResponse response, String khyf, String columName, String jgmc) throws Exception {
        System.out.println(jgmc);
        Map map = new HashMap();
        map.put("khyf", khyf);
        map.put("jgmc", jgmc);

        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<TissueDcdbKhBean> list = null;
        if (columName.equals("ffts")) {
            map.put("sourceId", "反复投诉");
            list = tissueDcdbKhService.selectfftsJsgs(map);
        } else if (columName.equals("dbwz")) {
            map.put("sourceId", "督办问责");
            list = tissueDcdbKhService.selectfftsJsgs(map);
        } else if (columName.equals("xcjz")) {
            map.put("sourceId", "巡查机制");
            list = tissueDcdbKhService.selectfftsJsgs(map);
        } else if (columName.equals("jdjc")) {
            map.put("sourceId", "监督检查");
            list = tissueDcdbKhService.selectfftsJsgs(map);
        } else if (columName.equals("gzxt")) {
            map.put("sourceId", "工作协调");
            list = tissueDcdbKhService.selectfftsJsgs(map);
        }
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 督办案件全部  正常项  扣分项 刷新
     *
     * @param response
     * @param khyf
     * @param columName
     * @param jgmc
     * @param num
     * @return
     * @throws Exception
     */
/*    @RequestMapping("/refreshDcdbDetails")
    public String refreshDcdbDetails(HttpServletResponse response, String khyf, String columName, String jgmc, String num) throws Exception {
        System.out.println(jgmc);
        Map map = new HashMap();
        map.put("khyf", khyf);
        map.put("jgmc", jgmc);
        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<TissueDcdbKhBean> list = null;
        if (columName.equals("ffts")) {
            map.put("sourceId", "反复投诉");
            if (num.equals("1")) {
                list = tissueDcdbKhService.selectffts(map);
            } else if (num.equals("2")) {
                list = tissueDcdbKhService.refreshZcDcdbDetails(map);
            } else if (num.equals("3")) {
                list = tissueDcdbKhService.refreshDcdbDetails(map);
            }
        } else if (columName.equals("dbwz")) {
            map.put("sourceId", "督办问责");
            if (num.equals("1")) {
                list = tissueDcdbKhService.selectffts(map);
            } else if (num.equals("2")) {
                list = tissueDcdbKhService.refreshZcDcdbDetails(map);
            } else if (num.equals("3")) {
                list = tissueDcdbKhService.refreshDcdbDetails(map);
            }
        } else if (columName.equals("xcjz")) {
            map.put("sourceId", "巡查机制");
            if (num.equals("1")) {
                list = tissueDcdbKhService.selectffts(map);
            } else if (num.equals("2")) {
                list = tissueDcdbKhService.refreshZcDcdbDetails(map);
            } else if (num.equals("3")) {
                list = tissueDcdbKhService.refreshDcdbDetails(map);
            }
        } else if (columName.equals("jdjc")) {
            map.put("sourceId", "监督检查");
            if (num.equals("1")) {
                list = tissueDcdbKhService.selectffts(map);
            } else if (num.equals("2")) {
                list = tissueDcdbKhService.refreshZcDcdbDetails(map);
            } else if (num.equals("3")) {
                list = tissueDcdbKhService.refreshDcdbDetails(map);
            }
        } else if (columName.equals("gzxt")) {
            map.put("sourceId", "工作协调");
            if (num.equals("1")) {
                list = tissueDcdbKhService.selectffts(map);
            } else if (num.equals("2")) {
                list = tissueDcdbKhService.refreshZcDcdbDetails(map);
            } else if (num.equals("3")) {
                list = tissueDcdbKhService.refreshDcdbDetails(map);
            }
        }

        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }*/

    @RequestMapping("/selectXcbd")
    public String selectXcbd(HttpServletResponse response, String khyf, String columName, String jgmc, String num) throws Exception {
        System.out.println(jgmc);
        Map map = new HashMap();
        map.put("khyf", khyf);
        map.put("jgmc", jgmc);
        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<TIssueXcbdKh> list = null;
        if (columName.equals("xcbd")) {
            map.put("sourceId", "宣传报道");
            list = xcbdKhMapper.selectXcbd(map);
        }
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

}
