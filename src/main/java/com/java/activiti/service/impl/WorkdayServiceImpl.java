package com.java.activiti.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java.activiti.dao.WorkdayDao;
import com.java.activiti.model.WorkdayBean;
import com.java.activiti.service.WorkdayService;
import com.java.activiti.util.TimeUtil;

@Service("WorkdayServiceImpl")
public class WorkdayServiceImpl implements WorkdayService{
	
	@Resource
	private WorkdayDao workdayDao;

	/**
	 * 根据年份获取当年的工作日
	 */
	public List<String> getWorkDayListByYear(String year) {
		year = year.trim();
		List<String> resultList = new ArrayList<String>();
		if (year == ""){
			//参数检查
			return resultList;
		}
		//构造查询条件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", year);
		resultList = workdayDao.selectWorkdayByClause(map);
		return resultList;
	}

	/**
	 * 插入日期
	 */
	public int insertWorkDay(String year, String workDay) {
		WorkdayBean workdayBean = new WorkdayBean();
		workdayBean.setYear(year);
		workdayBean.setWorkday(workDay);
		return workdayDao.insertWorkday(workdayBean);
	}

	public int deleteYearWorkDay(String year) {
		return workdayDao.deleteWorkdayByYear(year);
	}

	public List<String> getWorkDayRange(String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paramStartDate", startTime);
		map.put("paramEndDate", endTime);
		return workdayDao.selectWorkdayByClause(map);
	}

	public boolean checkTimeOut(int timeLimit, String sDate, String eDate) throws ParseException {

		TimeUtil tu = new TimeUtil();
		if (tu.daysBetween(sDate, eDate) <= timeLimit) {
			return false;
		}

		// 如果结束时间比开始时间超过时限，需要去掉其中的工作日
		List<String> workDayList = getWorkDayRange(sDate, eDate);
		if (workDayList.size() == 0) {
			return true;
		}

		int workDayCount = workDayList.size();
		if (tu.daysBetween(sDate, eDate) - workDayCount <= timeLimit) {
			return false;
		}

		return true;
	}
	
	/**
	 * 在原有日期上增加制定工作日
	 * @param sTime 开始时间,格式为yyyy-MM-dd HH:mm:ss
	 * @param daysCount 要增加的工作日数量
	 * @return 增加后的时间，格式为yyyy-MM-dd HH:mm:ss
	 */
	public Date getLimitTime(Date sTime, int timeLimit) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//
		//开始日期
		String sDateStr = sdf.format(sTime);
		String eDateStr = "";
		int additionDays = 0;
		do{
			eDateStr = sdf.format(sTime.getTime() + (timeLimit+additionDays)*24*3600*1000);
			if (checkTimeOut(timeLimit, sDateStr, eDateStr)){
				break;
			}else{
				additionDays += 1;
			}
		} while (additionDays > 0);
		
		return new Date(sTime.getTime() + (timeLimit + additionDays -1)*24*3600*1000);
	}
}
