package com.java.activiti.service.impl;

import com.java.activiti.dao.TAttachmentDao;
import com.java.activiti.dao.TMainIssueDao;
import com.java.activiti.dao.TissueDcdbKhMapper;
import com.java.activiti.dao.UserDao;
import com.java.activiti.model.*;
import com.java.activiti.service.TMainIssueService;
import net.sf.json.JSONObject;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional(rollbackFor = Exception.class)
@Service("tMainIssueService")
@PropertySource("classpath:system.properties")
public class TMainIssueServiceImpl implements TMainIssueService {


    @Resource
    private TMainIssueDao tMainIssueMapper;
    @Resource
    private TissueDcdbKhMapper tissueDcdbKhMapper;

    @Resource
    private TAttachmentDao tAttachmentDao;

    @Autowired
    private TaskService taskService;

    @Autowired
    private Environment env;

    @Resource
    private UserDao userDao;

    @Resource
    private HistoryService historyService;

    /**
     * 查询案件分布地图信息
     *
     * @return
     * @param beginDate
     * @param endDate
     */
    @Override
    public List<Map<String, String>> queryIssueMap(String beginDate, String endDate, String groupId, String groupTypeId) {
        List<Map<String, String>> list = new ArrayList<>();
//        String x = "";
//        String y = "";
        try {
            Map map = new HashMap();
            map.put("beginDate",beginDate);
            map.put("endDate",endDate);
            //map.put("status", "16");//16 案件状态为 已完结,
            if (groupTypeId=="2"||groupTypeId.equals("2")){
                map.put("groupId",groupId);
                map.put("groupTypeId",groupTypeId);
                list = tMainIssueMapper.queryIssueMap(map);
            }else if (groupTypeId=="6"||groupTypeId.equals("6")){
                map.put("issueExecDept",groupId);
                map.put("groupTypeId",groupTypeId);
                list = tMainIssueMapper.queryIssueMapByDept(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 通过流程实例分类型查询附件
     *
     * @param taskId
     * @return
     */
    @Override
    public Map<String, List<TAttachmentBean>> queryAttachments(String taskId) {
        Map<String, List<TAttachmentBean>> resMap = new HashMap<>(3);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        TMainIssueBean tMainIssueBean = tMainIssueMapper.selectTMainIssueByProcessId(task.getProcessInstanceId());
        if (null != tMainIssueBean) {
            Map<String, Object> param = new HashMap<>(2);
            param.put("mainIssueId", tMainIssueBean.getId());
            param.put("type", 1);//处理前附件
            List<TAttachmentBean> reportAttachments = tAttachmentDao.selectTAttachmentByMainIssueId(param);
            /*param.put("type", 2);//处理时附件
            List<TAttachmentBean> processingAttachments = tAttachmentDao.selectTAttachmentByMainIssueId(param);*/
            param.put("type", 3);//评价时附件
            List<TAttachmentBean> evaluationAttachments = tAttachmentDao.selectTAttachmentByMainIssueId(param);
            param.put("type", 4);//立案时附件
            List<TAttachmentBean> registerAttachments = tAttachmentDao.selectTAttachmentByMainIssueId(param);
            param.put("type", 5);//处理后附件 网格员  街道科室  执法中队处理时时附件
            List<TAttachmentBean> handleAttachments = tAttachmentDao.selectTAttachmentByMainIssueId(param);

            resMap.put("reportAttachments", reportAttachments);
            resMap.put("handleAttachments", handleAttachments);
            resMap.put("evaluationAttachments", evaluationAttachments);
            resMap.put("registerAttachments", registerAttachments);
        }
        return resMap;
    }


    /**
     * 显示附件
     */
    @Override
    public void showAttachment(HttpServletResponse response, String path) {
        String filePath = env.getProperty("fileupload");
        String fileName = filePath + File.separator + path;
        try {
            FileInputStream inputStream = new FileInputStream(fileName);
            //得到文件大小
            int i = inputStream.available();
            byte[] data = new byte[i];
            //读数据
            inputStream.read(data);
            //设置返回的文件类型
            response.setContentType("image/*");
            //得到向客户端输出二进制数据的对象
            OutputStream outputStream = response.getOutputStream();
            //输出数据
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject getIssueAndProcessInfoByIssueNumber(String issueNumber) {
        JSONObject result = new JSONObject();
        //案件信息
        TMainIssueBean tMainIssueBean = tMainIssueMapper.selectTMainIssueByIssueNumber(issueNumber);
        result.put("tMainIssueBean",tMainIssueBean);
        return result;
    }

    @Override
    public String showAddUserNameById(String addUserId){
        User user = userDao.selectByPrimaryKey(addUserId);
        return user.getUserName();
    }

    @Override
    public   List<TMainIssueBean> selectZxclDetails(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.selectZxclDetails(map);
        return list;
    }

    @Override
    public   List<TMainIssueBean> selectAjbzDetails(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.selectAjbzDetails(map);
        return list;
    }
   @Override
    public   List<TMainIssueBean> selectJsgs(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.selectJsgs(map);
        return list;
    }
    @Override
    public   List<TMainIssueBean> selectAsclDetails(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.selectAsclDetails(map);
        return list;
    }
    @Override
    public   List<TMainIssueBean> selectAsclJsgs(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.selectAsclJsgs(map);
        return list;
    }
  @Override
    public   List<TMainIssueBean> refreshZxclDetails(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.refreshZxclDetails(map);
        return list;
    }
    @Override
    public   List<TMainIssueBean> refreshAsclDetails(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.refreshAsclDetails(map);
        return list;
    }
    @Override
    public   List<TMainIssueBean> selectWzxDetails(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.selectWzxDetails(map);
        return list;
    }

    @Override
    public   List<TMainIssueBean> selectWzxJsgs(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.selectWzxJsgs(map);
        return list;
    }
    @Override
    public   List<TMainIssueBean> refreshZcAsclDetails(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.refreshZcAsclDetails(map);
        return list;
    }
    @Override
    public   List<TMainIssueBean> selectZcWzxDetails(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.selectZcWzxDetails(map);
        return list;
    }
  @Override
    public   List<TMainIssueBean> selectKsclDetails(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.selectKsclDetails(map);
        return list;
    }
    @Override
    public   List<TMainIssueBean> refreshZcKsclDetails(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.refreshZcKsclDetails(map);
        return list;
    }
    @Override
    public   List<TMainIssueBean> refreshKsclDetails(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.refreshKsclDetails(map);
        return list;
    }
    @Override
    public   List<TMainIssueBean> selectZcAjbzDetails(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.selectZcAjbzDetails(map);
        return list;
    }

    @Override
    public   List<TMainIssueBean> selectAjbzJsgs(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.selectAjbzJsgs(map);
        return list;
    }
 @Override
    public   List<TMainIssueBean> selectKsclJsgs(Map map ){
        List<TMainIssueBean> list = tMainIssueMapper.selectKsclJsgs(map);
        return list;
    }



    /**
     * 生成案件编码
     * @return
     */
    @Override
    public synchronized String queryCaseNum(String type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = new Date();
        Integer lastNum =null;
        Map map = new HashMap();

        //案件类型
        String caseType = type.trim().toUpperCase();
        //案件编码
        String caseNum =caseType+sdf.format(nowDate);

        map.put("caseType",caseType);
        map.put("nowDate",sdf2.format(nowDate));

        if("DC".equals(type)){
            lastNum = tissueDcdbKhMapper.queryCaseNum(map);
        }else {
            lastNum = tMainIssueMapper.queryCaseNum(map);
        }
        if(lastNum==null){
            return null;
        }
        caseNum += formatNum(lastNum+1);

        return caseNum;
    }

    /**
     * 四位顺序号格式化
     * @param num
     * @return
     */
    private String formatNum(int num){
        String result=null;
        if (num<0){
            result =null;
        }else if(num <10){
            result="000"+num;
        }else if (num<100){
            result ="00"+num;
        }else if(num<1000){
            result="0"+num;
        }else{
            result=""+num;
        }

        return result;
    }
}
