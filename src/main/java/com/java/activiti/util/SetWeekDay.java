package com.java.activiti.util;

import com.java.activiti.dao.WorkdayDao;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SetWeekDay {

    @Resource
    private static WorkdayDao workdayDao;
    public static final  List getEndTime(String startTime,int limitTime) {
        //1.计算出正常的截止时间  开始时间+时限 计算出每一天的详细时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        List list =new LinkedList();
        try {
            date = sdf.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        String temp = "";
        int count=0;
        //2.得出在limitTime时间段中详细的每一天,放到list中
        for (int i=0;i<limitTime;i++){
            cl.add(Calendar.DAY_OF_MONTH,1);
            temp = sdf.format(cl.getTime());
            //  System.out.println(temp+"测试中,请稍后................... ");
            list.add(temp);
        }
 /*       //3.遍历list,查询每一个list中的每一个元素在数据库中是否存在,如果存在+1
        int c=0;
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
            try {
                count = workdayDao.selectWorkdayCountByWorkDay(String.valueOf(list.get(i)));
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
            date2 = sdf.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c2.setTime(date2);
        int total=limitTime+c;
        c2.add(Calendar.DAY_OF_MONTH,total);
        temp2 = sdf.format(c2.getTime());
        return temp2;*/

        return list;
    }
}
