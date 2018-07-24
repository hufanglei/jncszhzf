package com.java.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/13 013.
 *  一些重复代码的 封装
 */
public class CodingUtil {
    /**
     *  获取本月或者上个月时间字符串  2017-10这种格式
     * @param index -1 获取上个月;其他值 本月
     * @return
     */
    public static String getMonthByCalender(int index){
        Calendar calendar = Calendar.getInstance();
        String year =  calendar.get(calendar.YEAR)+"";
        int month = calendar.get(calendar.MONTH); //取上个月的 月份
        if(index!=-1){
            month = month+1;
        }
        String monthString = month<10 ? ("0"+month) : (month+"");
        return year+"-"+monthString;
    }


    public static void main(String[] args) {
        String date = getMonthByCalender(0);
        System.out.println(date);
    }
}
