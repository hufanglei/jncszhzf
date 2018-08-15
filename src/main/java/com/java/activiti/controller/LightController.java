package com.java.activiti.controller;


import com.java.activiti.dao.GroupDao;
import com.java.activiti.dao.TMainIssueKhDao;
import com.java.activiti.dao.TissueRadioKhMapper;
import com.java.activiti.model.Group;
import com.java.activiti.model.TMainIssueBean;
import com.java.activiti.model.TMainIssueKhBean;
import com.java.activiti.model.TissueRadioKhBean;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
//@RequestMapping("/light")
public class LightController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(LightController.class);

    @Resource
    private TMainIssueKhDao tMainIssueKhDao;
    @Resource
    private TissueRadioKhMapper tIssueRadioKhMapper;
    @Resource
    private TMainIssueKhDao tMainIssueKhMapper;
    @Resource
    private GroupDao groupDao;

    /**
     * 定时任务:每月初 定时计算 上个月 每个街道 部门 的考核得分，保存到考核表中
     */
    @Transactional
//    @RequestMapping("/runTask")
    public void runTask() {
//        logger.error("定时器执行--》》");
//        TMainIssueKhBean tMainIssueKhBean = new TMainIssueKhBean();
//        tMainIssueKhBean.setAjbz("100");
//        int count = tMainIssueKhDao.insertTMainIssueKh(tMainIssueKhBean);
//        logger.error("--insertTMainIssueKh--》》" + count);
        System.out.println("1111");

        TMainIssueKhBean tMainIssueKhBean = null;
        int count = 0;
        //计算每个街道的 自行处理的得分
        List<HashMap<String, Object>> dealSelfList = tMainIssueKhDao.statistictDealSelf();
        //计算每个街道的 案件比重的得分
        List<HashMap<String, Object>> ratioList = tMainIssueKhDao.statistictStreetRatio();
        //计算每个街道的 按时处理的得分
        List<HashMap<String, Object>> dealOnTimeList = tMainIssueKhDao.statistictDealOnTime();
        //计算每个街道的 快速处理的得分
        List<HashMap<String, Object>> dealQuickList = tMainIssueKhDao.statistictDealQuick();

        for (int i = 0; i < dealSelfList.size(); i++) {
            tMainIssueKhBean = new TMainIssueKhBean();
            HashMap<String, Object> dealSelfItem = dealSelfList.get(i);
            String qhdm = (String) dealSelfItem.get("qhdm");
            String qhmc = (String) dealSelfItem.get("qhmc");
            BigDecimal dealSelfscore = (BigDecimal) dealSelfItem.get("score");
            tMainIssueKhBean.setQhzdId(qhdm);
            tMainIssueKhBean.setQhzdName(qhmc);
            tMainIssueKhBean.setZxcl(dealSelfscore.toString());//给自行处理分数字段赋值
            HashMap<String, Object> radioItem = ratioList.get(i);
            BigDecimal radioScore = (BigDecimal) radioItem.get("score"); //给案件比重分数字段赋值
            tMainIssueKhBean.setAjbz(radioScore.toString());
            HashMap<String, Object> dealOnTimeItem = dealOnTimeList.get(i);
            BigDecimal dealOnTimeScore = (BigDecimal) dealOnTimeItem.get("score"); //给按时处理分数字段赋值
            tMainIssueKhBean.setAscl(dealOnTimeScore.toString());
            HashMap<String, Object> dealQuickItem = dealQuickList.get(i);
            BigDecimal dealQuickScore = (BigDecimal) dealQuickItem.get("score"); //给按时处理分数字段赋值
            tMainIssueKhBean.setKscl("0.0".equals(String.valueOf(dealQuickScore.doubleValue())) ? "0.0000" : String.valueOf(dealQuickScore.doubleValue()));
            getTime(tMainIssueKhBean); //设置考核月份和时间
            count += tMainIssueKhDao.insertTMainIssueKh(tMainIssueKhBean);
        }
        logger.error("--insertTMainIssueKh--》》" + count);
    }

    public void getTime(TMainIssueKhBean tMainIssueKhBean) {
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(calendar.YEAR) + "";
        String month = calendar.get(calendar.MONTH) < 10 ? ("0" + calendar.get(calendar.MONTH)) : calendar.get(calendar.MONTH) + ""; //取上个月的 月份
        tMainIssueKhBean.setKhyf(year + "-" + month);
        tMainIssueKhBean.setKhsj(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    }

    public void taskRun() {
        System.out.println("这是一个定时器");
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = (calendar.get(Calendar.MONTH) + 1) < 10 ? ("0" + (calendar.get(Calendar.MONTH) + 1)) : (calendar.get(Calendar.MONTH) + 1) + "";//本月
        String year1 = calendar.get(Calendar.YEAR) + "";
        String month1 = (calendar.get(Calendar.MONTH) ) < 10 ? ("0" + (calendar.get(Calendar.MONTH) )) : (calendar.get(Calendar.MONTH) ) + "";//本月

        String khyf = year + "-" + month;
        String newKhyf = year1 + "-" + month1;
        Map map1 = new HashMap();
        map1.put("khyf", khyf);
        Map map2 = new HashMap();
        map2.put("khyf", khyf);
        map2.put("newKhyf", newKhyf);

        List<HashMap<String, Object>> dealSelfList = tMainIssueKhDao.selectDealSelf(map1);
        List<HashMap<String, Object>> onTimeList = tMainIssueKhDao.onTimeList(map1);
        List<HashMap<String, Object>> quickList = tMainIssueKhDao.quickList(map1);
        //委办局 按时处理 案件数量
        List<HashMap<String, Object>> onTimeListWbj = tMainIssueKhDao.onTimeListWbj(map1);
        //委办局快速处理案件数量
        List<HashMap<String, Object>> quickListWbj = tMainIssueKhDao.quickListWbj(map1);
        //执法痕迹考核评分 街道
        List<HashMap<String, Object>> zfhjList = tMainIssueKhDao.zfhjList(map1);
        //执法痕迹考核评分 委办局
        List<HashMap<String, Object>> zfhjWbjList = tMainIssueKhDao.zfhjWbjList(map1);
        //反复投诉考核评分   街道
        List<HashMap<String, Object>> fftsList = tMainIssueKhDao.selectFftsList(map1);
        //反复投诉考核评分  委办局
        List<HashMap<String, Object>> fftsWbjList = tMainIssueKhDao.selectFftsWbjList(map1);
        //督办问责考核评分   街道
        List<HashMap<String, Object>> dbwzList = tMainIssueKhDao.selectDbwzList(map1);
        //督办问责考核评分  委办局
        List<HashMap<String, Object>> dbwzWbjList = tMainIssueKhDao.selectDbwzWbjList(map1);
        //工作协调考核评分   街道
        List<HashMap<String, Object>> gzxtList = tMainIssueKhDao.selectGzxtList(map1);
        //工作协调考核评分  委办局
        List<HashMap<String, Object>> gzxtWbjList = tMainIssueKhDao.selectGzxtWbjList(map1);
        //监督检查考核评分   街道
        List<HashMap<String, Object>> jdjcList = tMainIssueKhDao.selectJdjcListt(map1);
        //监督检查考核评分  委办局
        List<HashMap<String, Object>> jdjcWbjList = tMainIssueKhDao.selectJdjcWbjList(map1);
        //巡查机制考核评分   街道
        List<HashMap<String, Object>> xcjzList = tMainIssueKhDao.selectXcjzList(map1);

        TMainIssueKhBean tMainIssueKhBean = null;
        int count = 0;
        /**
         * 案件比重考核评分生成
         * 1.先是判断该月的案件中有没有12345总工单量,如果没有沿用上一个月的工单量,如果有直接设置
         */
        DecimalFormat df = new DecimalFormat("0.0");
        List<TissueRadioKhBean> list = tIssueRadioKhMapper.selectListPage(map1);
        for(TissueRadioKhBean tissueRadioKhBean:list){
            if (tissueRadioKhBean.getAvg12345num()!=null){
                String pro= df.format(tissueRadioKhBean.getAvg12345num()/12);
                tissueRadioKhBean.setProportion(pro);
                tIssueRadioKhMapper.updateByQhzdId(tissueRadioKhBean);
            }else{
                List<TissueRadioKhBean> list1 = tIssueRadioKhMapper.selectPageList(map2);
                for(TissueRadioKhBean tissueRadioKhBean1:list1){
                    String pro= df.format(tissueRadioKhBean1.getAvg12345num()/12);
                    tissueRadioKhBean1.setProportion(pro);
                    tIssueRadioKhMapper.updateByQhzdId(tissueRadioKhBean1);
                }
            }
        }

        double a=0;
        double zgdNum =0;
        try{
            a=tIssueRadioKhMapper.selectJdCountBykhyf(map1);//13个街道园区的当月自处置案件总量
            zgdNum =tIssueRadioKhMapper.selectSumByKhyf(map1);//用户13个街道园区12345网站年工单量总和
        }catch (Exception e){
            e.printStackTrace();
        }
        double p = 0;
        double b;
        if(a!=0&&zgdNum!=0) {
            b = Double.parseDouble(df.format(zgdNum / 12));
            if (a <= b) {
                p = 1;
            } else if (a > b) {
                p = Double.parseDouble(df.format(1 - (a - b) / a));//求统一系数
            }
            List<TissueRadioKhBean> list1 = tIssueRadioKhMapper.selectByKhyf(map1);//查询固定月份的案件比重数据结果集
            double n=0;
            double m=0;

            for (TissueRadioKhBean tissueRadioKhBean : list1) {
                     n=tissueRadioKhBean.getIssuenum();
                     m =tissueRadioKhBean.getAvg12345num() / 12;
                double score = 0;
                double newScore=0;
                String score2="";
                if(n!=0&&!String.valueOf(n).equals("")&&m!=0&&!String.valueOf(m).equals("")) {
                    score = (n / m) * p;
                    score2=df.format(score*150);
                    newScore=score*150>150 ?  150:score*150;
                    tissueRadioKhBean.setScore(String.valueOf(df.format(newScore)));
                    tissueRadioKhBean.setIssuenum((int) n);
                    tissueRadioKhBean.setCoefficient(p);
                    tissueRadioKhBean.setProportion(String.valueOf(m));
                }else{
                    tissueRadioKhBean.setProportion(String.valueOf(m));
                    tissueRadioKhBean.setScore(String.valueOf(df.format(newScore)));
                    tissueRadioKhBean.setCoefficient(p);
                }

                tIssueRadioKhMapper.updateByPrimaryKey(tissueRadioKhBean);//将案件比重得分和统一系数保存到案件比重表中
                String qhzdDm = tissueRadioKhBean.getQhzdId();
                String qhzdMc = tissueRadioKhBean.getQhzdName();
                Map map3 = new HashMap();
                map3.put("qhzdDm", qhzdDm);
                map3.put("khyf", khyf);
                TMainIssueKhBean tMainIssueKhBean2 = tMainIssueKhMapper.selectXcbd(map3);
                if (tMainIssueKhBean2 == null) {
                    TMainIssueKhBean tMainIssueKhBean1 = new TMainIssueKhBean();
                    tMainIssueKhBean1.setQhzdId(qhzdDm);
                    tMainIssueKhBean1.setQhzdName(qhzdMc);
                    tMainIssueKhBean1.setKhyf(khyf);
                    tMainIssueKhBean1.setKhsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    tMainIssueKhBean1.setAjbz(String.valueOf(df.format(newScore)));
                    tMainIssueKhBean1.setBmType("2");
                    tMainIssueKhMapper.insertTMainIssueKh(tMainIssueKhBean1);
                } else {
                    tMainIssueKhBean2.setBmType("2");
                    tMainIssueKhBean2.setAjbz(String.valueOf(df.format(newScore)));
                    tMainIssueKhMapper.updateTMainIssueXcbd(tMainIssueKhBean2);
                }
            }
        }
        if (onTimeListWbj.size() == 0){
            TMainIssueKhBean tMainIssueKhBean3 = new TMainIssueKhBean();
            List<Group> qhlist=groupDao.selectQhList();
            for(Group group:qhlist){
                tMainIssueKhBean3.setQhzdId(group.getGroupPid());
                tMainIssueKhBean3.setQhzdName(group.getGroupName());
                tMainIssueKhBean3.setAscl(String.valueOf(0));
                tMainIssueKhBean3.setKscl(String.valueOf(0));
                tMainIssueKhBean3.setWzxgfx(String.valueOf(0));
                tMainIssueKhBean3.setFfts(String.valueOf(50));
                tMainIssueKhBean3.setDbwz(String.valueOf(50));
                tMainIssueKhBean3.setGzxt(String.valueOf(50));
                tMainIssueKhBean3.setJdjc(String.valueOf(40));
                tMainIssueKhBean3.setKhyf(khyf);
                tMainIssueKhBean3.setKhsj(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                tMainIssueKhBean3.setBmType("5");
                tMainIssueKhDao.insertTMainIssueKh(tMainIssueKhBean3);
            }



        }else {
            for (int i = 0; i < onTimeListWbj.size(); i++) {
                //给委办局赋值
                tMainIssueKhBean = new TMainIssueKhBean();
                HashMap<String, Object> onTimeListWbjItem = onTimeListWbj.get(i);
                HashMap<String, Object> quickListWbjItem = quickListWbj.get(i);
                HashMap<String, Object> zfhjWbjListItem = zfhjWbjList.get(i);
                HashMap<String, Object> fftsWbjListItem = fftsWbjList.get(i);
                HashMap<String, Object> dbwzWbjListItem = dbwzWbjList.get(i);
                HashMap<String, Object> gzxtWbjListItem = gzxtWbjList.get(i);
                HashMap<String, Object> jdjcWbjListItem = jdjcWbjList.get(i);
                String qhdm = (String) onTimeListWbjItem.get("qhzd_id");
                String qhmc = (String) onTimeListWbjItem.get("qhzd_name");
                double onTimeListScore = Double.valueOf(String.valueOf(onTimeListWbjItem.get("score"))); //给按时处理分数字段赋值
                double quickListWbjScore = Double.valueOf(String.valueOf(quickListWbjItem.get("score"))); //给快速处理分数字段赋值
                double zfhjWbjListScore = Double.valueOf(String.valueOf(zfhjWbjListItem.get("score"))); //给执法痕迹考核评分 委办局
                double ffxtWbjListScore = Double.valueOf(String.valueOf(fftsWbjListItem.get("score"))); //反复协调 委办局
                double dbwzWbjListScore = Double.valueOf(String.valueOf(dbwzWbjListItem.get("score"))); //督办问责 委办局
                double gzxtWbjListScore = Double.valueOf(String.valueOf(gzxtWbjListItem.get("score"))); //工作协调 委办局
                double jdjcWbjListScore = Double.valueOf(String.valueOf(jdjcWbjListItem.get("score"))); //监督检查 委办局

                double ffxtScore = (50 - ffxtWbjListScore) < 0 ? 0 : (50 - ffxtWbjListScore);
                double dbwzScore = (50 - dbwzWbjListScore) < 0 ? 0 : (50 - dbwzWbjListScore);
                double gzxtScore = (50 - gzxtWbjListScore) < 0 ? 0 : (50 - gzxtWbjListScore);
                double jdjcScore = (40 - jdjcWbjListScore) < 0 ? 0 : (40 - jdjcWbjListScore);

                Map map = new HashMap();
                map.put("qhzdDm", qhdm);
                map.put("khyf", khyf);
                TMainIssueKhBean tMainIssueKhBean1 = tMainIssueKhDao.selectXcbd(map);
                if (tMainIssueKhBean1 != null) {
                    tMainIssueKhBean1.setAscl(String.valueOf(onTimeListScore));
                    tMainIssueKhBean1.setKscl(String.valueOf(quickListWbjScore));
                    tMainIssueKhBean1.setWzxgfx(String.valueOf(zfhjWbjListScore));
                    tMainIssueKhBean1.setFfts(String.valueOf(ffxtScore));
                    tMainIssueKhBean1.setDbwz(String.valueOf(dbwzScore));
                    tMainIssueKhBean1.setGzxt(String.valueOf(gzxtScore));
                    tMainIssueKhBean1.setJdjc(String.valueOf(jdjcScore));
                    tMainIssueKhBean1.setBmType("5");
                    tMainIssueKhDao.updateTMainIssueKh(tMainIssueKhBean1);
                } else {
                    tMainIssueKhBean.setQhzdId(qhdm);
                    tMainIssueKhBean.setQhzdName(qhmc);
                    tMainIssueKhBean.setAscl(String.valueOf(onTimeListScore));
                    tMainIssueKhBean.setKscl(String.valueOf(quickListWbjScore));
                    tMainIssueKhBean.setWzxgfx(String.valueOf(zfhjWbjListScore));
                    tMainIssueKhBean.setFfts(String.valueOf(ffxtScore));
                    tMainIssueKhBean.setDbwz(String.valueOf(dbwzScore));
                    tMainIssueKhBean.setGzxt(String.valueOf(gzxtScore));
                    tMainIssueKhBean.setJdjc(String.valueOf(jdjcScore));
                    tMainIssueKhBean.setKhyf(khyf);
                    tMainIssueKhBean.setKhsj(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                    tMainIssueKhBean.setBmType("5");
                    tMainIssueKhDao.insertTMainIssueKh(tMainIssueKhBean);
                }
            }
        }
        for (int i = 0; i < dealSelfList.size(); i++) {
            //给街道中心赋值
            tMainIssueKhBean = new TMainIssueKhBean();
            HashMap<String, Object> dealSelfItem = dealSelfList.get(i);
            HashMap<String, Object> dealOnTimeItem = onTimeList.get(i);
            HashMap<String, Object> zfhjListItem = zfhjList.get(i);
            HashMap<String, Object> fftsListItem = fftsList.get(i);
            HashMap<String, Object> dbwzListItem = dbwzList.get(i);
            HashMap<String, Object> gzxtListItem = gzxtList.get(i);
            HashMap<String, Object> jdjcListItem = jdjcList.get(i);
            HashMap<String, Object> xcjzListItem = xcjzList.get(i);
            String qhdm = (String) dealSelfItem.get("qhzd_id");
            String qhmc = (String) dealSelfItem.get("qhzd_name");
            double dealSelfscore = Double.valueOf(String.valueOf(dealSelfItem.get("score")));//给自行处理分数字段赋值
            HashMap<String, Object> dealQuickItem = quickList.get(i);
            double dealQuickScore = Double.valueOf(String.valueOf(dealQuickItem.get("score"))); //给快速处理分数字段赋值
            double dealOnTimeScore = Double.valueOf(String.valueOf(dealOnTimeItem.get("score"))); //给按时处理分数字段赋值
            double zfhjListScore = Double.valueOf(String.valueOf(zfhjListItem.get("score"))); //给执法痕迹考核评分 街道
            double ffxtListScore = Double.valueOf(String.valueOf(fftsListItem.get("score"))); //反复协调 街道中心
            double dbwzListScore = Double.valueOf(String.valueOf(dbwzListItem.get("score"))); //督办问责 街道中心
            double gzxtListScore = Double.valueOf(String.valueOf(gzxtListItem.get("score"))); //工作协调 街道中心
            double xcjzListScore = Double.valueOf(String.valueOf(xcjzListItem.get("score"))); //巡查机制 街道中心
            double jdjcListScore = Double.valueOf(String.valueOf(jdjcListItem.get("score"))); //监督检查 街道中心

            double ffxtScore = (50 - ffxtListScore) < 0 ? 0 : (50 - ffxtListScore);
            double dbwzScore = (50 - dbwzListScore) < 0 ? 0 : (50 - dbwzListScore);
            double gzxtScore = (50 - gzxtListScore) < 0 ? 0 : (50 - gzxtListScore);
            double xcjzScore = (50 - xcjzListScore) < 0 ? 0 : (50 - xcjzListScore);
            double jdjcScore = (40 - jdjcListScore) < 0 ? 0 : (40 - jdjcListScore);


            Map map = new HashMap();
            map.put("qhzdDm", qhdm);
            map.put("khyf", khyf);
            TMainIssueKhBean tMainIssueKhBean1 = tMainIssueKhDao.selectXcbd(map);
            //判断是否存在此条记录,如果存在更新,否则新增
            if (tMainIssueKhBean1 != null) {
                tMainIssueKhBean1.setZxcl(String.valueOf(dealSelfscore));//给自行处理分数字段赋
                tMainIssueKhBean1.setAscl(String.valueOf(dealOnTimeScore));
                tMainIssueKhBean1.setKscl(String.valueOf(dealQuickScore));
                tMainIssueKhBean1.setWzxgfx(String.valueOf(zfhjListScore));
                tMainIssueKhBean1.setFfts(String.valueOf(ffxtScore));
                tMainIssueKhBean1.setDbwz(String.valueOf(dbwzScore));
                tMainIssueKhBean1.setGzxt(String.valueOf(gzxtScore));
                tMainIssueKhBean1.setXcjz(String.valueOf(xcjzScore));
                tMainIssueKhBean1.setJdjc(String.valueOf(jdjcScore));
                tMainIssueKhBean1.setBmType("2");
                tMainIssueKhDao.updateTMainIssueKh(tMainIssueKhBean1);
            } else {
                tMainIssueKhBean.setQhzdId(qhdm);
                tMainIssueKhBean.setQhzdName(qhmc);
                tMainIssueKhBean.setZxcl(String.valueOf(dealSelfscore));//给自行处理分数字段赋
                tMainIssueKhBean.setAscl(String.valueOf(dealOnTimeScore));
                tMainIssueKhBean.setKscl(String.valueOf(dealQuickScore));
                tMainIssueKhBean.setWzxgfx(String.valueOf(zfhjListScore));
                tMainIssueKhBean.setFfts(String.valueOf(ffxtScore));
                tMainIssueKhBean.setDbwz(String.valueOf(dbwzScore));
                tMainIssueKhBean.setGzxt(String.valueOf(gzxtScore));
                tMainIssueKhBean.setXcjz(String.valueOf(xcjzScore));
                tMainIssueKhBean.setJdjc(String.valueOf(jdjcScore));
                tMainIssueKhBean.setKhyf(khyf);
                tMainIssueKhBean.setKhsj(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                tMainIssueKhBean.setBmType("2");
                tMainIssueKhDao.insertTMainIssueKh(tMainIssueKhBean);
            }
        }
    }


}
