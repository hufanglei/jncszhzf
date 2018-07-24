package com.java.activiti.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.activiti.dao.TMainIssueDao;
import com.java.activiti.util.Constants;

@Controller
@RequestMapping("/statistics")
public class IssueStatisticsController {
	
	@Autowired
	private TMainIssueDao tMainIssueMapper;
	
	public TMainIssueDao getTMainIssueMapper() {
		return tMainIssueMapper;
	}

	public void setTMainIssueMapper(TMainIssueDao tMainIssueMapper) {
		this.tMainIssueMapper = tMainIssueMapper;
	}

	
	/**
	 * 案件总量
	 * 	查询 2015-08-02 至 2017-08-10之间的案件数据，按天分组	statistics/issueCount?startDay='2015-08-02'&endDay='2017-08-10'
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@Transactional
	@RequestMapping(value = "/issueCount")
	public @ResponseBody
	String selectIssueCountStatistic(HttpServletRequest request) throws IOException, ParseException {
		String startDay = Constants.isEmptyOrNull(request.getParameter("startDay"))?null:request.getParameter("startDay");
		String endDay = Constants.isEmptyOrNull(request.getParameter("endDay"))?null:request.getParameter("endDay");
		Map map1 = new HashMap();
		map1.put("startDay", startDay);
		map1.put("endDay", endDay);
		List<HashMap<String, String>> list = tMainIssueMapper.selectIssueCountStatistic(map1);
		JSONArray jsonArray = JSONArray.fromObject(list, Constants.getJsonConfig());
		return jsonArray.toString();
		
	}
	
	/**
	 * 案件状态占比统计 
	 * 	 查询 2015-08-02 至 2017-08-10之间的各案件状态百分比值	statistics/issueStatusPercent?startDay='2015-08-02'&endDay='2017-08-10'
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@Transactional
	@RequestMapping(value = "/issueStatusPercent")
	public @ResponseBody
	String selectIssueStatusPercentStatistic(HttpServletRequest request) throws IOException, ParseException {
		String startDay = Constants.isEmptyOrNull(request.getParameter("startDay"))?null:request.getParameter("startDay");
		String endDay = Constants.isEmptyOrNull(request.getParameter("endDay"))?null:request.getParameter("endDay");		
		Map map1 = new HashMap();
		map1.put("startDay", startDay);
		map1.put("endDay", endDay);
		List<HashMap<String, String>> list = tMainIssueMapper.selectIssueStatusStatistic(map1);
		JSONArray jsonArray = JSONArray.fromObject(list, Constants.getJsonConfig());
		return jsonArray.toString();
	}
	
	/**
	 * 街道案件统计 
	 * 	查询2015-08-02 至 2017-08-10之间的各街道案件数量	statistics/streetIssue?startDay='2015-08-02'&endDay='2017-08-10'
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@Transactional
	@RequestMapping(value = "/streetIssue")
	public @ResponseBody
	String selectStreetIssueStatistic(HttpServletRequest request) throws IOException, ParseException {
		String startDay = Constants.isEmptyOrNull(request.getParameter("startDay"))?null:request.getParameter("startDay");
		String endDay = Constants.isEmptyOrNull(request.getParameter("endDay"))?null:request.getParameter("endDay");
		Map map1 = new HashMap();
		map1.put("startDay", startDay);
		map1.put("endDay", endDay);
		List<HashMap<String, String>> list = tMainIssueMapper.selectStreetIssueCountByDay(map1);
/*		List<HashMap<String, String>> list = tMainIssueMapper.selectStreetIssueCountByClause(map1);*/
		JSONArray jsonArray = JSONArray.fromObject(list, Constants.getJsonConfig());
		return jsonArray.toString();
	}
	
	/**
	 * 街道办结案件统计 
	 * 	查询2015-08-02 至 2017-08-10之间的各街道办结案件数量	statistics/streetFinishedIssue?startDay='2015-08-02'&endDay='2017-08-10'
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@Transactional
	@RequestMapping(value = "/streetFinishedIssue")
	public @ResponseBody
	String selectStreetFinishedIssueStatistic(HttpServletRequest request) throws IOException, ParseException {
		String startDay = Constants.isEmptyOrNull(request.getParameter("startDay"))?null:request.getParameter("startDay");
		String endDay = Constants.isEmptyOrNull(request.getParameter("endDay"))?null:request.getParameter("endDay");
		Map map1 = new HashMap();
		map1.put("startDay", startDay);
		map1.put("endDay", endDay);
		map1.put("finished", "1");
		List<HashMap<String, String>> list = tMainIssueMapper.selectStreetIssueCountByDay(map1);
		JSONArray jsonArray = JSONArray.fromObject(list, Constants.getJsonConfig());
		return jsonArray.toString();
	}
	
	/**
	 * 处置部门案件统计
	 * 	查询2015-08-02 至 2017-08-10之间的各委办局被分配案件数量	statistics/wbjIssue?startDay='2015-08-02'&endDay='2017-08-10'
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@Transactional
	@RequestMapping(value = "/wbjIssue")
	public @ResponseBody
	String selectWbjIssueStatistic(HttpServletRequest request) throws IOException, ParseException {
		String startDay = Constants.isEmptyOrNull(request.getParameter("startDay"))?null:request.getParameter("startDay");
		String endDay = Constants.isEmptyOrNull(request.getParameter("endDay"))?null:request.getParameter("endDay");
		Map map1 = new HashMap();
		map1.put("startDay", startDay);
		map1.put("endDay", endDay);
	//	List<HashMap<String, String>> list = tMainIssueMapper.selectWeiBanJuIssueCountByClause(map1);
		List<HashMap<String, String>> list = tMainIssueMapper.selectWeiBanJuIssueCountByDay(map1);
		JSONArray jsonArray = JSONArray.fromObject(list, Constants.getJsonConfig());
		return jsonArray.toString();
	}
	
	/**
	 * 处置部门办结案件统计
	 *  查询2015-08-02 至 2017-08-10之间的各委办局办结案件数量	statistics/wbjFinishedIssue?startDay='2015-08-02'&endDay='2017-08-10'
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@Transactional
	@RequestMapping(value = "/wbjFinishedIssue")
	public @ResponseBody
	String selectWbjFinishedIssueStatistic(HttpServletRequest request) throws IOException, ParseException {
		String startDay = Constants.isEmptyOrNull(request.getParameter("startDay"))?null:request.getParameter("startDay");
		String endDay = Constants.isEmptyOrNull(request.getParameter("endDay"))?null:request.getParameter("endDay");
		Map map1 = new HashMap();
		map1.put("startDay", startDay);
		map1.put("endDay", endDay);
		map1.put("finished", "1");
		//List<HashMap<String, String>> list = tMainIssueMapper.selectWeiBanJuIssueCountByClause(map1);
		List<HashMap<String, String>> list = tMainIssueMapper.selectWeiBanJuIssueCountByDay(map1);
		JSONArray jsonArray = JSONArray.fromObject(list, Constants.getJsonConfig());
		return jsonArray.toString();
	}
	
	/**
	 * 事项归口统计
	 * 	 查询2015-08-02 至 2017-08-10之间的各类案件数量	statistics/wbjFinishedIssue?startDay='2015-08-02'&endDay='2017-08-10'
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@Transactional
	@RequestMapping(value = "/issueBelong")
	public @ResponseBody
	String selectIssueTypeStatistic(HttpServletRequest request) throws IOException, ParseException {
		String startDay = Constants.isEmptyOrNull(request.getParameter("startDay"))?null:request.getParameter("startDay");
		String endDay = Constants.isEmptyOrNull(request.getParameter("endDay"))?null:request.getParameter("endDay");
		Map map1 = new HashMap();
		map1.put("startDay", startDay);
		map1.put("endDay", endDay);
		List<HashMap<String, String>> list = tMainIssueMapper.selectPowerBelongIssueCount(map1);
		JSONArray jsonArray = JSONArray.fromObject(list, Constants.getJsonConfig());
		return jsonArray.toString();
	}
	
	
}
