package com.java.activiti.util;

import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class TimeUtil
{



  public static  void main(String[] args) throws ParseException
  {
	  TimeUtil tt = new TimeUtil();
    System.out.println("获取这一天是星期几:" + tt.getWeek("2016-05-24"));
    System.out.println("获取昨天日期:" + tt.getyd());
    System.out.println("获取当天日期:" + tt.getNowTime("yyyy-MM-dd"));
    System.out.println("获取本周一日期:" + tt.getMondayOFWeek());
    System.out.println("获取本周日的日期:" + tt.getCurrentWeekday());
    System.out.println("获取上周一日期:" + tt.getPreviousWeekday(-1));////----------
    System.out.println("获取上周日日期:" + tt.getPreviousWeekSunday(-1));///-------------
    System.out.println("获取本月第一天日期:" + tt.getFirstDayOfMonth());
    System.out.println("获取本月最后一天日期:" + tt.getDefaultDay());
    System.out.println("获取上月第一天日期:" + tt.getPreviousMonthFirst(-1));///------------
    System.out.println("获取上月最后一天的日期:" + tt.getPreviousMonthEnd(-1));///---------
    System.out.println("前一个小时"+tt.getFixedHour(-1));
    System.out.println("前五分钟"+tt.getFixedMinute(-5));
    System.out.println("前20秒"+tt.getFixedSecond(-20));
    System.out.println("两个日期相差多少天"+tt.daysBetween("2015-07-08","2015-07-10"));
    System.out.println("计算指定日期的前后几天"+tt.getFixedDayfromDay("2015-07-08",1));
    
    System.out.println("获取指定年月的第一天"+tt.getFirstDayofCertainMonth("2015", "3"));
    System.out.println("获取指定年月的最后一天"+tt.getLastDayofCertainMonth("2015","3"));
    
    System.out.println("获取指定年月下个月的第一天"+tt.getFirstDayofNextMonth("2016", "1"));
    System.out.println("获取指定时间的前五分钟"+tt.getFixedMinutefromDay("2015-07-08 09:08:00",-5));
    System.out.println("获取当前年份"+tt.getCurrentYear());
    System.out.println("获取当前月份"+tt.getCurrentMonth());
    System.out.println("获取当前日"+tt.getCurrentDay());
    System.out.println("获取当前日期所处季度"+tt.getCurrentQuarter());
    System.out.println("获取指定日期处于本年度第几周"+tt.getWeekByDate("2015-07-08"));
    System.out.println("根据日期获得所在周的日期 list"+tt.getDateToWeek("2015-07-08"));
    
  }

  
  /**
   * 计算两个日期相差多少天
   * @param smdate
   * 起始日期 yyyy-MM-dd
   * @param bdate
   * 截止日期 yyyy-MM-dd
   * @return
   */
  public  int daysBetween(String smdate,String bdate)  {  
      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
      Calendar cal = Calendar.getInstance();    
      try {
		cal.setTime(sdf.parse(smdate));
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}    
      long time1 = cal.getTimeInMillis();                 
      try {
		cal.setTime(sdf.parse(bdate));
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}    
      long time2 = cal.getTimeInMillis();         
      long between_days=(time2-time1)/(1000*3600*24);  
          
     return Integer.parseInt(String.valueOf(between_days));     
  }  
  
  /**
   * 获取两个日期相差月数
   * @param smdate
   * 起始日期 yyyy-MM-dd
   * @param bdate
   * 截止日期 yyyy-MM-dd
   * @return
   */
  public int monthsBetween(String smdate,String bdate)  {  
	  
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");  	
	  int num=0;

		try {
			Date d1 = sdf.parse(smdate);
			Date d2 = sdf.parse(bdate);
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(d1);
			c2.setTime(d2);

			int year = c1.get(Calendar.YEAR);
			int month = c1.get(Calendar.MONTH);

			int year1 = c2.get(Calendar.YEAR);
			int month1 = c2.get(Calendar.MONTH);

			
			if (year == year1) {
				num = month1 - month;// 两个日期相差几个月，即月份差
			} else {
				num = 12 * (year1 - year) + month1 - month;// 两个日期相差几个月，即月份差
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
  } 
  

  
  /**
   * 获取两个日期中间的日期list
   * @param smdate
   * 起始日期 yyyy-MM-dd
   * @param bdate
   * 截止日期 yyyy-MM-dd
   * @return
   */
  public List daysListBetween(String smdate,String bdate) {    	    
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  	
		  List days = new ArrayList();

			try {
				Date d1 = sdf.parse(smdate);
				Date d2 = sdf.parse(bdate);
				Calendar c1 = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				c1.setTime(d1);
				c2.setTime(d2);
				days.add(sdf.format(d1));
				while (c1.compareTo(c2) < 0) {
					c1.add(Calendar.DAY_OF_MONTH, 1);// 开始日期加一天直到等于结束日期为止
					Date ss = c1.getTime();
					String str = sdf.format(ss);
					days.add(str);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return days;
	}
  
  
  /**
   * 获取两个日期中间的月份list
   * @param smdate
   * 起始日期 yyyy-MM-dd
   * @param bdate
   * 截止日期 yyyy-MM-dd
   * @return
   */
  public List monthsListBetween(String smdate,String bdate)  {  
	  
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");  	
	  List months = new ArrayList();

		try {
			Date d1 = sdf.parse(smdate);
			Date d2 = sdf.parse(bdate);
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(d1);
			c2.setTime(d2);
			months.add(sdf.format(d1));
			while (c1.compareTo(c2) < 0) {
				c1.add(Calendar.MONTH, 1);// 开始日期加一个月直到等于结束日期为止
				Date ss = c1.getTime();
				String str = sdf.format(ss);
				months.add(str);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return months;
  }  
  
  /**
   * 获取两个日期中的年list
   * @param smdate
   * 起始日期 yyyy-MM-dd
   * @param bdate
   * 截止日期 yyyy-MM-dd
   * @return
   */
  public List yearsListBetween(String smdate,String bdate)  {  
	  
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy");  	
	  List years = new ArrayList();

		try {
			Date d1 = sdf.parse(smdate);
			Date d2 = sdf.parse(bdate);
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(d1);
			c2.setTime(d2);
			years.add(sdf.format(d1));
			while (c1.compareTo(c2) < 0) {
				c1.add(Calendar.YEAR, 1);// 开始日期加一年直到等于结束日期为止
				Date ss = c1.getTime();
				String str = sdf.format(ss);
				years.add(str);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return years;
  }  
  
  
  /**
   * 获取sdate这一天是星期几
   * @param sdate
   * @return
   */
  public  String getWeek(String sdate)
  {
    Date date = strToDate(sdate);


    Calendar c = Calendar.getInstance();


    c.setTime(date);


    return new SimpleDateFormat("EEEE").format(c.getTime());
  }


  public  Date strToDate(String strDate)
  {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


    ParsePosition pos = new ParsePosition(0);


    Date strtodate = formatter.parse(strDate, pos);


    return strtodate;
  }


  /**
   * 获取date1与date2之间天数的差值
   * 当date1在前时，差值为负；当date2在前时，差值为正
   * @param date1
   * @param date2
   * @return
   */
  public  long getDays(String date1, String date2)
  {
    if ((date1 == null) || (date1.equals("")))
    {
      return 0L;
    }
    if ((date2 == null) || (date2.equals("")))
    {
      return 0L;
    }


    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");


    Date date = null;


    Date mydate = null;
    try
    {
      date = myFormatter.parse(date1);


      mydate = myFormatter.parse(date2);
    }
    catch (Exception localException)
    {
    }


    long day = (date.getTime() - mydate.getTime()) / 86400000L;


    return day;
  }


  /**
   * 获取昨天日期
   * @return
   */
  public String getyd()
  {
    Calendar cal = Calendar.getInstance();
    cal.add(5, -1);
    String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
    return yesterday;
  }

/**
 * 获取本月最后一天日期
 * @return
 */
  public String getDefaultDay()
  {
    String str = "";


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    Calendar lastDate = Calendar.getInstance();


    lastDate.set(5, 1);


    lastDate.add(2, 1);


    lastDate.add(5, -1);


    str = sdf.format(lastDate.getTime());


    return str;
  }
  
  
  /**
   * 获取指定年月的最后一天
   * @param year
   * 年份
   * @param month
   * 月份
   * @return
   */
  public String getLastDayofCertainMonth(String year,String month)
  {
	  String str = "";
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	  
	  Calendar cal = Calendar.getInstance();
	  cal.set(Calendar.YEAR,Integer.parseInt(year));
	  cal.set(Calendar.MONTH, Integer.parseInt(month));
	  cal.set(Calendar.DAY_OF_MONTH, 1);
	  cal.add(Calendar.DAY_OF_MONTH, -1);
	  Date lastDate = cal.getTime();
	  
	  str = sdf.format(lastDate.getTime());
	  return str;
  }
  
  /**
   * 获取指定年月下个月的第一天
   * @param year
   * @param month
   * @return
   */
  public String getFirstDayofNextMonth(String year,String month)
  {
	  String str = "";
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	  
	  Calendar cal = Calendar.getInstance();
	  cal.set(Calendar.YEAR,Integer.parseInt(year));
	  cal.set(Calendar.MONTH, Integer.parseInt(month));
//	  cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
	  cal.set(Calendar.DAY_OF_MONTH, 1);
	  Date date = cal.getTime();
	  
	  str = sdf.format(date.getTime());
	  return str;
  }
  
  
  /**
   * 获取指定年月的第一天
   * @param year
   * 年份
   * @param month
   * 月份
   * @return
   */
  public String getFirstDayofCertainMonth(String year,String month)
  {
	  String str = "";
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	  
	  Calendar cal = Calendar.getInstance();
	  cal.set(Calendar.YEAR,Integer.parseInt(year));
	  cal.set(Calendar.MONTH, Integer.parseInt(month));
	  
	  cal.set(Calendar.DAY_OF_MONTH, 1);
	  cal.add(Calendar.DAY_OF_MONTH, -1);
	  cal.set(Calendar.DAY_OF_MONTH, 1);
	  Date firstDate = cal.getTime();


	  str = sdf.format(firstDate.getTime());
	  return str;
  }


  /**
   * 获取前n月第一天日期，n为负数时，指向前推n个月；n为正数时，指向后推n个月
   * @return
   */
  public String getPreviousMonthFirst(int n)
  {
    String str = "";


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    Calendar lastDate = Calendar.getInstance();


    lastDate.set(5, 1);


    lastDate.add(Calendar.MONTH, n);


    str = sdf.format(lastDate.getTime());


    return str;
  }

/**
 * "获取本月第一天日期
 * @return
 */
  public String getFirstDayOfMonth()
  {
    String str = "";


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    Calendar lastDate = Calendar.getInstance();


    lastDate.set(5, 1);


    str = sdf.format(lastDate.getTime());


    return str;
  }

/**
 *获取本周日的日期
 * @return
 */
  public String getCurrentWeekday()
  {
    int weeks = 0;


    int mondayPlus = getMondayPlus();


    GregorianCalendar currentDate = new GregorianCalendar();


    currentDate.add(5, mondayPlus + 6);


    Date monday = currentDate.getTime();


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    String preMonday = sdf.format(monday);


    return preMonday;
  }

/**
 * 获取当天日期
 * @param dateformat
 * @return
 */
  public String getNowTime(String dateformat)
  {
    Date now = new Date();


    SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);


    String hehe = dateFormat.format(now);


    return hehe;
  }


  private int getMondayPlus()
  {
    Calendar cd = Calendar.getInstance();


    int dayOfWeek = cd.get(7) - 1;


    if (dayOfWeek == 1)
    {
      return 0;
    }


    return (1 - dayOfWeek);
  }

/**
 * 获取本周一日期
 * @return
 */
  public String getMondayOFWeek()
  {
    int weeks = 0;


    int mondayPlus = getMondayPlus();


    GregorianCalendar currentDate = new GregorianCalendar();


    currentDate.add(5, mondayPlus);


    Date monday = currentDate.getTime();


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    String preMonday = sdf.format(monday);


    return preMonday;
  }

/**
 * 获取前n周周日日期 负数往前推 正数往后推
 * @return
 */
  public String getPreviousWeekSunday(int n)
  {


    int weeks = n;


    int mondayPlus = getMondayPlus();


    GregorianCalendar currentDate = new GregorianCalendar();


    currentDate.add(5, mondayPlus + weeks);


    Date monday = currentDate.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String preMonday = sdf.format(monday);


    return preMonday;
  }


  /**
   * 获取前n周周一的日期 负数往前推 正数往后推
   * @return
   */
  public String getPreviousWeekday(int n)
  {
	  
int weeks=n;
	  
    int mondayPlus = getMondayPlus();


    GregorianCalendar currentDate = new GregorianCalendar();


    currentDate.add(5, mondayPlus + 7 * weeks);


    Date monday = currentDate.getTime();


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    String preMonday = sdf.format(monday);


    return preMonday;
  }

/**
 * 获取前n月最后一天的日期，n为负数时，指向前推n个月；n为负数时，指向后推n个月
 * @return
 */
  public String getPreviousMonthEnd(int n)
  {
    String str = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar lastDate = Calendar.getInstance();
    lastDate.add(2, n);
    lastDate.set(5, 1);
    lastDate.roll(5, -1);
    str = sdf.format(lastDate.getTime());
    return str;
  }
  
  
  /**
   * 获取当前日期的前dayCount天的日期
   * @param dayCount
   * @return
   * yyyy-MM-dd
   */
  public  String getFixedDay(int dayCount)
	{
		Format f = new SimpleDateFormat("yyyy-MM-dd");
		 Calendar c = Calendar.getInstance();
		// c = day(c, dayCount);
		 c.add(Calendar.DATE, dayCount);
		 return f.format(c.getTime());
	}
  
  
  /**
   * 计算指定日期的前后几天 
   * @param day 
   * yyyy-MM-dd
   * @param count
   * 正数往前推 负数往后推
   * @return
   * yyyy-MM-dd
   * @throws ParseException
   */
  public String getFixedDayfromDay(String day,int count) 
  {

	  	SimpleDateFormat   sdf=new   SimpleDateFormat( "yyyy-MM-dd"); 
	    Date dt = sdf.parse(day ,new   ParsePosition(0));
	    Calendar   rightNow   =   Calendar.getInstance(); 
	    rightNow.setTime(dt); 
	    rightNow.add(Calendar.DATE,count);//你要加减的日期   
	    Date   dt1=rightNow.getTime(); 
	    String   reStr=sdf.format(dt1); 
	   
	  
	  return reStr;
  }
  
  /**
   * 获取当前时间的前、后hourCount（小时）的时间
   * @param hourCount
   * @return
   */
  public String getFixedHour(int hourCount)
  {
	  Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Calendar c = Calendar.getInstance();
		// c = day(c, dayCount);
		 c.add(Calendar.HOUR, hourCount);
		 return f.format(c.getTime());
  }
  
  
  /**
   * 获取当指定时间的前、后minuteCount（分钟）的时间
   * @param minuteCount
   * @return
 * @throws ParseException 
   */
  public String getFixedMinutefromDay(String day,int minuteCount) throws ParseException
  {
	  SimpleDateFormat   sdf=new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    Date dt = sdf.parse(day);
	    Calendar   c   =   Calendar.getInstance(); 
	    c.setTime(dt); 
	    c.add(Calendar.MINUTE, minuteCount);
	    return sdf.format(c.getTime());
	   
  }
  
  /**
   * 获取当前时间的前、后minuteCount（分钟）的时间
   * @param minuteCount
   * @return
   */
  public String getFixedMinute(int minuteCount)
  {
	  Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Calendar c = Calendar.getInstance();
		// c = day(c, dayCount);
		 c.add(Calendar.MINUTE, minuteCount);
		 return f.format(c.getTime());
  }
  
  /**
   * 获取当前时间的前、后secondCount（秒）的时间
   * @param secondCount
   * @return
   */
  public String getFixedSecond(int secondCount)
  {
	  Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Calendar c = Calendar.getInstance();
		// c = day(c, dayCount);
		 c.add(Calendar.SECOND, secondCount);
		 return f.format(c.getTime());
  }
  
  /**
   * 获取当前年
   * @return
   */
  public String getCurrentYear()
  {
	  Calendar c = Calendar.getInstance();
	   return String.valueOf( c.get(Calendar.YEAR));//得到年
  }
  
  /**
   * 获取当前月
   * @return
   */
  public String getCurrentMonth()
  {
	  Calendar c = Calendar.getInstance();
	  String month = String.valueOf( c.get(Calendar.MONTH)+1);
	  if(month.length()<2)
		  month = "0"+month;
	   return month;//得到月
  }
 
  /**
   * 获取当前日
   * @return
   */
  public int getCurrentDay()
  {
	  Calendar c = Calendar.getInstance();
	  return c.get(Calendar.DAY_OF_MONTH);
  }
  
  /**
   * 获得当前日期所在季度
   * 
   * @return
   */
  public String getCurrentQuarter() {
	  Calendar c = Calendar.getInstance();
	  int currentMonth = c.get(Calendar.MONTH);
	  String currentQuarter="";
	  try {
          if (currentMonth >= 0 && currentMonth <= 2) {
        	  currentQuarter = "1";
          } else if (currentMonth >= 3 && currentMonth <= 5) {
        	  currentQuarter = "2";
          } else if (currentMonth >= 6 && currentMonth <= 8) {
        	  currentQuarter = "3";
          } else if (currentMonth >= 9 && currentMonth <= 11) {
        	  currentQuarter = "4";
          }

      } catch (Exception e) {
          e.printStackTrace();
      }
	  return currentQuarter;
  }
  
  /*
   * 根据指定日期获取本年度第几周
   */
  public int getWeekByDate(String dateString){

	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	  Date date;	
	  Calendar calendar = Calendar.getInstance();
	  try {		
		  date = sdf.parse(dateString);  
		  calendar.setTime(date);
		 
	  } catch (ParseException e) {
			// TODO Auto-generated catch block		
		  e.printStackTrace();		
	  }
	  return calendar.get(Calendar.WEEK_OF_MONTH);	

  }

  /**
	 * 根据日期获得所在周的日期 list
	 * @param mdate
	 * @return
	 */
  public List<String> getDateToWeek(String dateString) {
	
	  SimpleDateFormat   sdf=new   SimpleDateFormat("yyyy-MM-dd"); 
	    Date dt;
	    List<String> list = null;
		try {
			dt = sdf.parse(dateString);
			int b = dt.getDay();
			Date fdate;
			list = new ArrayList<String>();
			Long fTime = dt.getTime() - b * 24 * 3600000;
			for (int a = 1; a <= 5; a++) {
				fdate = new Date();
				fdate.setTime(fTime + (a * 24 * 3600000));
				String str = sdf.format(fdate.getTime());
				list.add(str);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

  /*
   * 获取两个日期之间的工作日天数 算头算尾
   */

	public int getDutyDays(String strStartDate,String strEndDate) {  
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
		Date startDate=null;  
		Date endDate = null;  
		  
		try {  
			startDate=df.parse(strStartDate);  
			endDate = df.parse(strEndDate);  
		} catch (ParseException e) {  
			System.out.println("非法的日期格式,无法进行转换");  
			e.printStackTrace();  
		}  
		int result = 0;  
		while (startDate.compareTo(endDate) <= 0) {  
			if (startDate.getDay() != 6 && startDate.getDay() != 0)  
				result++;  
				startDate.setDate(startDate.getDate() + 1);  
			}  
		  
		return result;  
	}
}