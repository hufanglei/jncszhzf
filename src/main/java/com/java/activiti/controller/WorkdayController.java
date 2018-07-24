package com.java.activiti.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.activiti.model.WorkdayBean;
import com.java.activiti.service.WorkdayService;
import com.java.activiti.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/workday")
public class WorkdayController {
	/**
	 * 私有变量，工作日设置服务实例
	 */
	@Resource
	private WorkdayService workdayService;

	@RequestMapping("/selectWorkDay")
	public String selectWorkDay(String year, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonConfig jsonConfig=new JsonConfig();
		JSONObject result=new JSONObject();
		//查询数据
		List<String> workDayList = workdayService.getWorkDayListByYear(year);
		JSONArray jsonArray=JSONArray.fromObject(workDayList, jsonConfig);
		result.put("rows", jsonArray);
		ResponseUtil.write(response, result);
		return null;
	}
	
	@RequestMapping("/setWorkDay")
	public String setWorkDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject result=new JSONObject();
		
		String dateStr = request.getParameter("data");
		String year = request.getParameter("year");
		
		//插入前删除本年度的记录
		workdayService.deleteYearWorkDay(year);
		
		//将字符串转换成数组
		String[] dayArr = dateStr.split(", ");
		
		boolean flag = true;
		for (int i = 0 ;i < dayArr.length; i++){
			int res = workdayService.insertWorkDay(dayArr[i].substring(0,4), dayArr[i]);
			if(res != 1){
				flag = false;
			}
		}
		result.put("success", flag);
		ResponseUtil.write(response, result);
		return null;
	}
}
