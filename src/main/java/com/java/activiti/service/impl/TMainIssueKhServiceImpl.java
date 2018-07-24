package com.java.activiti.service.impl;

import com.java.activiti.dao.*;
import com.java.activiti.model.*;
import com.java.activiti.service.GroupService;
import com.java.activiti.service.TMainIssueKhService;
import com.java.activiti.service.TMainIssueService;
import com.java.activiti.util.Const;
import com.java.activiti.util.Constants;
import com.java.activiti.util.ProcessInstanceConst;
import net.sf.json.JSONArray;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service("tMainIssueKhService")
public class TMainIssueKhServiceImpl implements TMainIssueKhService {

    @Autowired
    private TissueDcdbKhMapper tissueDcdbKhMapper;
    @Autowired
    private TissueRadioKhMapper tIssueRadioKhMapper;
    @Autowired
    private TDcdbAttachmentMapper tDcdbAttachmentMapper;

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private TMainIssueService tMainIssueService;
    @Resource
    private TMainIssueKhDao tMainIssueKhMapper;
    @Resource
    private  TissueRadioSetKhMapper tissueRadioSetKhMapper;
    @Resource
    private  TMainIssueDao tMainIssueDao;
    @Resource
    private GroupService groupService;
    @Resource
    DbIssueDealMapper dbIssueDealMapper;

    /**
     * 计算宣传报道分数
     *
     * @param xcbdKh
     * @return
     */
    @Override
    public Float getScore(TIssueXcbdKh xcbdKh) {
        float score = 0;
        //"国家级每篇5分，省级每篇3分、市级每篇2分、区级每篇1分，
        // 网络媒体分值减半，每月得分10分封顶。"
        if (xcbdKh.getCountrywlmt() != null) {
            score += xcbdKh.getCountrywlmt() * 5;
        }
        if (xcbdKh.getCountryfwlmt() != null) {
            score += xcbdKh.getCountryfwlmt() * 2.5;
        }
        if (xcbdKh.getProvincewlmt() != null) {
            score += xcbdKh.getProvincewlmt() * 3;
        }
        if (xcbdKh.getProvincefwlmt() != null) {
            score += xcbdKh.getProvincefwlmt() * 1.5;
        }
        if (xcbdKh.getCitywlmt() != null) {
            score += xcbdKh.getCitywlmt() * 2;
        }
        if (xcbdKh.getCityfwlmt() != null) {
            score += xcbdKh.getCityfwlmt();
        }
        if (xcbdKh.getDistrictwlmt() != null) {
            score += xcbdKh.getDistrictwlmt() * 1;
        }
        if (xcbdKh.getDistrictfwlmt() != null) {
            score += xcbdKh.getDistrictfwlmt() * 0.5;
        }

        return score > 10 ? 10 : score;
    }

    /**
     * 获取督查督办编号
     *
     * @return
     */
    @Override
    public synchronized String getDcdbNumInfo() {
        //ZHZF+日期+顺序编号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        StringBuffer code = new StringBuffer("ZHZF" + sdf.format(new Date()));
        Map map = new HashMap();
        map.put("code", code.toString());
        int num = tissueDcdbKhMapper.selectCountByDate(map);
        code.append(formatCodeNum(num + 1));

        return code.toString();
    }

    @Override
    public List<TissueRadioKhBean> selectByKhyf(Map khyf) {
        return tIssueRadioKhMapper.selectByKhyf(khyf);
    }

    @Override
    public int updateAjbz(TissueRadioKhBean tissueRadioKhBean) {
        return tIssueRadioKhMapper.updateByKhyf(tissueRadioKhBean);
    }

    /**
     * 格式化序列号
     *
     * @param num
     * @return
     */
    private String formatCodeNum(int num) {
        String numStr = "";
        if (num < 10) {
            numStr = "00" + num;
        } else if (num < 100) {
            numStr = "0" + num;
        } else {
            numStr = "" + num;
        }
        return numStr;
    }


    /**
     * 更新案件比重信息
     *
     * @param khyf
     * @param avg12345num
     * @param coefficient
     * @return
     */
    @Override
    public int updateAjbzInfo(String khyf, String avg12345num, String coefficient) throws RuntimeException {
        int result = 0;
        Map map1 = new HashMap();
        map1.put("khyf", khyf);
        List<TissueRadioKhBean> list = tIssueRadioKhMapper.selectByKhyf(map1);
        TMainIssueKhBean tMainIssueKhBean = null;
        if (list != null && list.size() > 0) {
            for (TissueRadioKhBean tissueRadioKhBean : list) {
                tissueRadioKhBean.setAvg12345num(Integer.valueOf(avg12345num));
                tissueRadioKhBean.setCoefficient(Double.valueOf(coefficient));
                if (tissueRadioKhBean.getAvg12345num() != null && tissueRadioKhBean.getAvg12345num() != 0) {
                    Double bz = (double) tissueRadioKhBean.getIssuenum() * 100 / tissueRadioKhBean.getAvg12345num();
                    tissueRadioKhBean.setProportion(new java.text.DecimalFormat("#.00").format(bz) + "%");
                    //（发生案件数量/街道（园区）上年度12345月平均工单量）×150分，若比值大于1，将适情乘以统一系数。
                    Double num = (double) tissueRadioKhBean.getIssuenum() * 150 / tissueRadioKhBean.getAvg12345num();
                    Double score = num > 1 ? num * tissueRadioKhBean.getCoefficient() : num;
                    tissueRadioKhBean.setScore(new java.text.DecimalFormat("#.00").format(score));
                    //保存案件比重
                    Map map = new HashMap();
                    map.put("qhzdDm", tissueRadioKhBean.getQhzdId());
                    map.put("khyf", tissueRadioKhBean.getKhyf());
                    tMainIssueKhBean = tMainIssueKhMapper.selectXcbd(map);
                    if (tMainIssueKhBean == null) {
                        TMainIssueKhBean tMainIssueKhBean1 = new TMainIssueKhBean();
                        tMainIssueKhBean1.setQhzdId(tissueRadioKhBean.getQhzdId());
                        tMainIssueKhBean1.setQhzdName(tissueRadioKhBean.getQhzdName());
                        tMainIssueKhBean1.setKhyf(tissueRadioKhBean.getKhyf());
                        tMainIssueKhBean1.setKhsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        tMainIssueKhBean1.setAjbz(String.valueOf(score));
                        tMainIssueKhMapper.insertTMainIssueKh(tMainIssueKhBean1);
                    } else {
                        tMainIssueKhBean.setAjbz(String.valueOf(score));
                        tMainIssueKhMapper.updateTMainIssueXcbd(tMainIssueKhBean);
                    }
                }
                tIssueRadioKhMapper.updateByPrimaryKey(tissueRadioKhBean);
            }
            result = 1;
        }
        return result;
    }


    @Override
    public int saveorUpdateTissueDcdbKhResult(TissueDcdbKhBean bean, String fileData, HttpSession session) throws Exception {

        int userResult = 0;
        String issueNum ="";
        Integer dcdbKhId;
        MemberShip tMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        if (StringUtils.isBlank(bean.getDcdbnum())) {
            issueNum = tMainIssueService.queryCaseNum("DC");
            bean.setCaseType("DC");
            bean.setDcdbnum(issueNum);
        }
        bean.setCreateTime(new Date());
        Calendar calendar = Calendar.getInstance();
        String year =  calendar.get(Calendar.YEAR)+"";
        String month = (calendar.get(Calendar.MONTH)+1)<10 ? ("0"+(calendar.get(Calendar.MONTH)+1)) : (calendar.get(Calendar.MONTH)+1)+"";
        bean.setKhyf(year+"-"+month);
        bean.setAddUserid(tMemberShip.getUserId());
        if (bean.getId() == null || bean.getId() == 0) {

                //待督办派发
                bean.setStatus(Const.DB_DISTRICT_DISTRIBUTE);
                userResult = tissueDcdbKhMapper.insertSelective(bean);

                dcdbKhId = bean.getId();
                //开始走流程
                Map<String, Object> variables = new HashMap<String, Object>();
                String groupId = tMemberShip.getGroupId();
                variables.put("dbUsers", groupId);
                variables.put("TissueDcdbKhBean", bean);
                variables.put("handerGroup", Const.QUZHONGXIN_GROUP_ID); //待区中心派发

                //新添加的流程
                ProcessInstance pi = runtimeService.startProcessInstanceByKey(ProcessInstanceConst.DB_KEY, variables);
                // 根据流程实例Id查询任务
                Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
                // 完成填写上报事项任务
                taskService.complete(task.getId(), variables);
                // 更新主流程表，设置流程ID
                bean.setProcessId(pi.getProcessInstanceId());
                userResult = tissueDcdbKhMapper.updateByPrimaryKey(bean);

            List<HashMap<String,Object>>  list1=tissueDcdbKhMapper.selectProcessId(bean.getDcdbnum());

            DbIssueDeal dbIssueDeal=new DbIssueDeal();
            dbIssueDeal.setDcdbnum(String.valueOf(list1.get(0).get("dcdbNum")));
            dbIssueDeal.setTaskId(String.valueOf(list1.get(0).get("TASK_ID_")));
            dbIssueDeal.setProcessId(String.valueOf(list1.get(0).get("PROC_INST_ID_")));
            dbIssueDeal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            dbIssueDeal.setDealWay("已上报");
            dbIssueDealMapper.insert(dbIssueDeal);
            // 流程信息插入成功，处理附件插入
            String userId = tMemberShip.getUserId();
            //上传文件目录
            String rootDir = System.getProperty("java.io.tmpdir") + "\\jncszhzf";
            if (org.apache.commons.lang3.StringUtils.isNotBlank(fileData)) {
                JSONArray jsonArray = JSONArray.fromObject(fileData);
                Collection<UploadFileData> list = JSONArray.toCollection(jsonArray, UploadFileData.class);
                for (UploadFileData file : list) {
                    userResult = addAttachment(rootDir, file, dcdbKhId, userId, 1);
                }
            }
            return userResult;
        } else {
            userResult = tissueDcdbKhMapper.updateByPrimaryKey(bean);
        }
        return userResult;
    }

    /**
     * 保存案件比重考核分数
     * @param
     * @param khyf
     * @return
     */
    @Override
    public int saveRadioKh(HttpServletRequest request, String khyf)  throws Exception{

     /*   String num = request.getParameter("num");
        String code = request.getParameter("code");
        String[] numArr = num.split(",");
        String[] codeArr = code.split(",");*/



        double num1 = Double.parseDouble(request.getParameter("num1"));
        String code1 = request.getParameter("code1");
        double num2 = Double.parseDouble(request.getParameter("num2"));
        String code2 = request.getParameter("code2");
        double num3 = Double.parseDouble(request.getParameter("num3"));
        String code3 = request.getParameter("code3");
        double num4 = Double.parseDouble(request.getParameter("num4"));
        String code4 = request.getParameter("code4");
        double num5 = Double.parseDouble(request.getParameter("num5"));
        String code5 = request.getParameter("code5");
        double num6 = Double.parseDouble(request.getParameter("num6"));
        String code6 = request.getParameter("code6");
        double num7 = Double.parseDouble(request.getParameter("num7"));
        String code7 = request.getParameter("code7");
        double num8 = Double.parseDouble(request.getParameter("num8"));
        String code8 = request.getParameter("code8");
        double num9 = Double.parseDouble(request.getParameter("num9"));
        String code9 = request.getParameter("code9");
        double num10 = Double.parseDouble(request.getParameter("num10"));
        String code10 = request.getParameter("code10");
        double num11 = Double.parseDouble(request.getParameter("num11"));
        String code11 = request.getParameter("code11");
        double num12 = Double.parseDouble(request.getParameter("num12"));
        String code12 = request.getParameter("code12");
        double num13 = Double.parseDouble(request.getParameter("num13"));
        String code13 = request.getParameter("code13");
        DecimalFormat df = new DecimalFormat("0.0");

        List<TissueRadioKhBean> list = new ArrayList();
     /*   double numValue = 0;
        String codeValue = "";
        for(int i=0;i<numArr.length;i++){
            numValue = Double.parseDouble(numArr[i]);
            codeValue = codeArr[i];
            list.add(new TissueRadioKhBean(codeValue, (int) numValue,df.format(numValue/12), khyf));
        }*/
        list.add(new TissueRadioKhBean(code1, (int) num1,df.format(num1/12), khyf));
        list.add(new TissueRadioKhBean(code2, (int) num2,df.format(num2/12),khyf));
        list.add(new TissueRadioKhBean(code3, (int) num3,df.format(num3/12),khyf));
        list.add(new TissueRadioKhBean(code4, (int) num4,df.format(num4/12),khyf));
        list.add(new TissueRadioKhBean(code5, (int) num5,df.format(num5/12),khyf));
        list.add(new TissueRadioKhBean(code6, (int) num6,df.format(num6/12),khyf));
        list.add(new TissueRadioKhBean(code7, (int) num7,df.format(num7/12),khyf));
        list.add(new TissueRadioKhBean(code8, (int) num8,df.format(num8/12),khyf));
        list.add(new TissueRadioKhBean(code9, (int) num9,df.format(num9/12),khyf));
        list.add(new TissueRadioKhBean(code10, (int) num10,df.format(num10/12), khyf));
        list.add(new TissueRadioKhBean(code11, (int) num11,df.format(num11/12), khyf));
        list.add(new TissueRadioKhBean(code12, (int) num12,df.format(num12/12), khyf));
        list.add(new TissueRadioKhBean(code13, (int) num13,df.format(num13/12), khyf));
        int result =0;
        /**
         * 先保存13街道的年度总工单量
         *
         * A=13个街道园区的当月自处置案件总量    B=用户13个街道园区12345网站年工单量总和/12
         当A<=B时，统一系数P=1
         当A>B时，统一系数P=1-（A-B）/A
         N=单个街道园区的当月自处置案件量     M=单个街道园区12345网站年工单量/12
         “案件比重”分值=（N/M）*P
         */
        for (TissueRadioKhBean tissueRadioKhBean : list) {

            Map map1=new HashMap();
            String qhzdDm=tissueRadioKhBean.getQhzdId();
           // String qhzdMc=tissueRadioKhBean.getQhzdName();
            map1.put("khyf",khyf);
            map1.put("id",qhzdDm);

            TissueRadioKhBean tissueRadioKhBean1=tIssueRadioKhMapper.selectTissue(map1);
            if(tissueRadioKhBean1==null){
                TissueRadioKhBean tissueRadioKhBean2=new TissueRadioKhBean();
                tissueRadioKhBean2.setQhzdId(qhzdDm);
                String execGroupName = groupService.getGroupMenuByUserName(qhzdDm);
                tissueRadioKhBean2.setQhzdName(execGroupName);
                tissueRadioKhBean2.setAvg12345num(tissueRadioKhBean.getAvg12345num());
                tissueRadioKhBean2.setKhyf(khyf);
                tissueRadioKhBean2.setProportion(tissueRadioKhBean.getProportion());
                tIssueRadioKhMapper.insertSelective(tissueRadioKhBean2);
            }else {
                tissueRadioKhBean1.setAvg12345num(tissueRadioKhBean.getAvg12345num());
                tissueRadioKhBean1.setKhyf(khyf);
                tissueRadioKhBean1.setProportion(tissueRadioKhBean.getProportion());
                tIssueRadioKhMapper.updateByKhyf(tissueRadioKhBean1);
            }

        result=1;
        }
        Map map =new HashMap();
        map.put("khyf",khyf);
        double  a=0;
        double  zgdNum=0;
        try{
            a=tIssueRadioKhMapper.selectJdCountBykhyf(map);//13个街道园区的当月自处置案件总量
            zgdNum =tIssueRadioKhMapper.selectSumByKhyf(map);//用户13个街道园区12345网站年工单量总和
        }catch (Exception e){
            e.printStackTrace();
        }

        double p = 0;
        double b;

        if(a!=0&&!String.valueOf(a).equals(null)){
           b= Double.parseDouble(df.format(zgdNum/12));
           if(a<=b){
               p=1;
           }else if(a>b){
               p= Double.parseDouble(df.format(1-(a-b)/a));//求统一系数
           }
            Map map1 = new HashMap();
            map1.put("khyf", khyf);
            List<TissueRadioKhBean> list1 = tIssueRadioKhMapper.selectByKhyf(map1);//查询固定月份的案件比重数据结果集

            for (TissueRadioKhBean tissueRadioKhBean3: list1){
                double n =0;
                double m =0;
                try {
                    n =tissueRadioKhBean3.getIssuenum();
                    m = Double.parseDouble(tissueRadioKhBean3.getProportion());
                }catch (Exception e){
                    e.printStackTrace();
                }
                double score=0;
                 score = n/m*p;
                double newScore=score*150>150 ? 150:score*150;
                tissueRadioKhBean3.setCoefficient(p);
                tissueRadioKhBean3.setScore(String.valueOf(df.format(newScore)));
                tissueRadioKhBean3.setIssuenum((int) n);
                tissueRadioKhBean3.setProportion(String.valueOf(m));
                tIssueRadioKhMapper.updateByPrimaryKeySelective(tissueRadioKhBean3);//将案件比重得分和统一系数保存到案件比重表中
                String qhzdDm=tissueRadioKhBean3.getQhzdId();
                String qhzdMc=tissueRadioKhBean3.getQhzdName();

                Map map2 = new HashMap();
                map2.put("qhzdDm", qhzdDm);
                map2.put("khyf",khyf);
                TMainIssueKhBean tMainIssueKhBean = tMainIssueKhMapper.selectXcbd(map2);
                if (tMainIssueKhBean==null) {
                    TMainIssueKhBean tMainIssueKhBean1=new TMainIssueKhBean();
                    tMainIssueKhBean1.setQhzdId(qhzdDm);
                    tMainIssueKhBean1.setQhzdName(qhzdMc);
                    tMainIssueKhBean1.setKhyf(khyf);
                    tMainIssueKhBean1.setKhsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    tMainIssueKhBean1.setAjbz(String.valueOf(df.format(newScore)));
                    tMainIssueKhMapper.insertTMainIssueKh(tMainIssueKhBean1);
                }else{
                    tMainIssueKhBean.setAjbz(String.valueOf(df.format(newScore)));
                    tMainIssueKhMapper.updateTMainIssueXcbd(tMainIssueKhBean);
                }
                }
                result=1;
            }
        return result;
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
    private int addAttachment(String rootDir, UploadFileData fileData, int issueId, String userId, int type) throws Exception {
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
        Object  o =map.get("fileType");
        if (o!=null){
            String fileType=(String)o;
            tAttachmentBean.setType(Integer.valueOf(fileType) );
        }

        result = tDcdbAttachmentMapper.insert(tAttachmentBean);
        return result;
    }
   /* @Override
   //案件考核评分保存
    public String updateDealSelf() {
        List<HashMap<String, Object>>  dealSelfList = tMainIssueKhMapper.selectDealSelf();
        List<HashMap<String, Object>>  onTimeList = tMainIssueKhMapper.onTimeList();
        List<HashMap<String, Object>>  quickList = tMainIssueKhMapper.quickList();
        List<HashMap<String, Object>>  onTimeListWbj = tMainIssueKhMapper.onTimeListWbj();//委办局 按时处理 案件数量
        List<HashMap<String, Object>>  quickListWbj = tMainIssueKhMapper.quickListWbj();//委办局快速处理案件数量
        List<HashMap<String, Object>>  ajbzList = tMainIssueKhMapper.ajbzList();//委办局快速处理案件数量

        TMainIssueKhBean tMainIssueKhBean=null;
        int count = 0;
        for (int i=0;i<onTimeListWbj.size();i++){
            tMainIssueKhBean = new TMainIssueKhBean();
            HashMap<String, Object> onTimeListWbjItem = onTimeListWbj.get(i);
            HashMap<String, Object> quickListWbjItem = quickListWbj.get(i);
            String qhdm = (String) onTimeListWbjItem.get("id_");
            String qhmc = (String) onTimeListWbjItem.get("name_");
            Calendar calendar = Calendar.getInstance();
            String year = calendar.get(Calendar.YEAR) + "";
            String month = (calendar.get(Calendar.MONTH) + 1) < 10 ? ("0" + (calendar.get(Calendar.MONTH) + 1)) : (calendar.get(Calendar.MONTH) + 1) + "";//本月
            String khyf = year + "-" + month;
            BigDecimal onTimeListScore = (BigDecimal) onTimeListWbjItem.get("score"); //给按时处理分数字段赋值
            double quickListWbjScore = (double) quickListWbjItem.get("score"); //给快速处理分数字段赋值
            Map map = new HashMap();
            map.put("qhzdDm", qhdm);
            map.put("khyf", khyf);
            TMainIssueKhBean tMainIssueKhBean1 = tMainIssueKhMapper.selectXcbd(map);
            if (tMainIssueKhBean1 != null) {
                tMainIssueKhBean1.setAscl(String.valueOf(onTimeListScore));
                tMainIssueKhBean1.setKscl(String.valueOf(quickListWbjScore));
                tMainIssueKhMapper.updateTMainIssueKh(tMainIssueKhBean1);
            } else {
                tMainIssueKhBean.setQhzdId(qhdm);
                tMainIssueKhBean.setQhzdName(qhmc);
                tMainIssueKhBean.setAscl(String.valueOf(onTimeListScore));
                tMainIssueKhBean.setKscl(String.valueOf(quickListWbjScore));
                tMainIssueKhBean.setKhyf(khyf);
                tMainIssueKhBean.setKhsj(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                tMainIssueKhMapper.insertTMainIssueKh(tMainIssueKhBean);
            }
        }
        for (int i=0;i<dealSelfList.size();i++) {
            tMainIssueKhBean = new TMainIssueKhBean();
            HashMap<String, Object> dealSelfItem = dealSelfList.get(i);
            HashMap<String, Object> dealOnTimeItem = onTimeList.get(i);
            HashMap<String, Object> ajsbItem = ajbzList.get(i);
            String qhdm = (String) dealSelfItem.get("id_");
            String qhmc = (String) dealSelfItem.get("name_");
            Calendar calendar = Calendar.getInstance();
            String year = calendar.get(Calendar.YEAR) + "";
            String month = (calendar.get(Calendar.MONTH) + 1) < 10 ? ("0" + (calendar.get(Calendar.MONTH) + 1)) : (calendar.get(Calendar.MONTH) + 1) + "";//本月
            String khyf = year + "-" + month;

            BigDecimal dealSelfscore = (BigDecimal) dealSelfItem.get("score");//给自行处理分数字段赋值
            HashMap<String, Object> dealQuickItem = quickList.get(i);
            double dealQuickScore = (double) dealQuickItem.get("score"); //给快速处理分数字段赋值
            BigDecimal dealOnTimeScore = (BigDecimal) dealOnTimeItem.get("score"); //给按时处理分数字段赋值
            double ajsbItemScore = (double) ajsbItem.get("score"); //给案件比重处理分数赋值
            Map map = new HashMap();
            map.put("qhzdDm", qhdm);
            map.put("khyf", khyf);
            TMainIssueKhBean tMainIssueKhBean1 = tMainIssueKhMapper.selectXcbd(map);
            if (tMainIssueKhBean1 != null) {
                tMainIssueKhBean1.setZxcl(dealSelfscore.toString());//给自行处理分数字段赋
                tMainIssueKhBean1.setAscl(dealOnTimeScore.toString());
                tMainIssueKhBean1.setKscl(String.valueOf(dealQuickScore));
                tMainIssueKhBean1.setAjbz(String.valueOf(ajsbItemScore));
                tMainIssueKhMapper.updateTMainIssueKh(tMainIssueKhBean1);
            } else {
                tMainIssueKhBean.setQhzdId(qhdm);
                tMainIssueKhBean.setQhzdName(qhmc);
                tMainIssueKhBean.setZxcl(dealSelfscore.toString());//给自行处理分数字段赋
                tMainIssueKhBean.setAscl(dealOnTimeScore.toString());
                tMainIssueKhBean.setKscl(String.valueOf(dealQuickScore));
                tMainIssueKhBean.setAjbz(String.valueOf(ajsbItemScore));
                tMainIssueKhBean.setKhyf(khyf);
                tMainIssueKhBean.setKhsj(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                tMainIssueKhMapper.insertTMainIssueKh(tMainIssueKhBean);
            }
        }
        return null;
    }
*/
}
