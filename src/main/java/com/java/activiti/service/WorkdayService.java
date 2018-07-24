package com.java.activiti.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface WorkdayService {

	/**
	 * 获取工作日
	 * @param year
	 * @return
	 */
	public List<String> getWorkDayListByYear(String year);
	
	/**
	 * 插入工作日
	 * @param year 年份
	 * @param workDay 工作日
	 * @return int
	 */
	public int insertWorkDay(String year, String workDay);
	
	/**
	 * 删除特定年份工作日
	 * @param year
	 * @return
	 */
	public int deleteYearWorkDay(String year);
	
	public List<String> getWorkDayRange(String startTime, String endTime);
	
	public boolean checkTimeOut(int timeLimit, String sDate, String eDate) throws ParseException;
	
	/**
	 * 在原有日期上增加制定工作日
	 * @param sTime 开始时间,格式为yyyy-MM-dd HH:mm:ss
	 * @param daysCount 要增加的工作日数量
	 * @return 增加后的时间，格式为yyyy-MM-dd HH:mm:ss
	 */
	public Date getLimitTime(Date sTime, int timeLimit)throws Exception;
}
