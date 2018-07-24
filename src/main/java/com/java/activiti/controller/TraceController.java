package com.java.activiti.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.java.activiti.dao.GroupDao;
import com.java.activiti.dao.UserDao;
import com.java.activiti.model.Group;
import com.java.activiti.model.User;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.activiti.dao.TraceMapper;
import com.java.activiti.model.TraceBean;
import com.java.activiti.util.Constants;
import com.java.core.util.TraceUtils;

@Controller
public class TraceController {

	@Autowired
	private TraceMapper traceMapper;
	@Autowired
	private UserDao userDao;
	@Autowired
	private GroupDao groupDao;

	public TraceMapper getTraceMapper() {
		return traceMapper;
	}

	public void setTraceMapper(TraceMapper traceMapper) {
		this.traceMapper = traceMapper;
	}
	
	@Transactional
	@RequestMapping(value = "/frame/listTraceByUserId")
	public @ResponseBody
	String listTraceByUserId(HttpServletRequest request) throws IOException {
		
		String userId = request.getParameter("userId");
		String startDay = request.getParameter("startDay");
		String endDay = request.getParameter("endDay");
		
		Map map1 = new HashMap();
		map1.put("userId",userId );
		map1.put("startDay", startDay);
		map1.put("endDay", endDay);
		map1.put("orderString", "curTime");
		List<TraceBean> list = traceMapper.selectTraceByClause(map1);
		
		Map map2 = new HashMap();
		map2.put("rows",new TraceUtils().parsePoints(list));
		
		JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
		return jSONObject.toString();
	}

	@Transactional
	@RequestMapping(value = "/frame/listTraceByUser")
	@ResponseBody
	public List listTraceByUser(HttpServletRequest request) throws IOException {
		String userId = request.getParameter("userId");
		String groupId = request.getParameter("groupId");
		List list = new ArrayList();
		List<HashMap<String, Object>> traces = new ArrayList<>();
		if(userId!=null && userId.length()>0){
			list.add(userId);
		}else if(groupId!=null && groupId.length()>0){
			Map groupMap = new HashMap();
			groupMap.put("pid", groupId);
			List<Group> groups = groupDao.selectGroupByPid(groupMap);

			if(groups!=null && groups.size()>0) {
				List ids = new ArrayList();
				for (Group group : groups) {
					ids.add(group.getGroupId());
				}

				List<User> users = userDao.selectUserByGroupIds(ids);

				if(users!=null &&users.size()>0){
					for(User user:users){
						list.add(user.getId());
					}
				}
			}
		}

		if(list.size()>0) {
			Map map2 = new HashMap();
			map2.put("users", list);
			traces = traceMapper.selectTraceByUser(map2);
		}
		return traces;
	}

	@Transactional
	@RequestMapping(value = "/frame/listTrace")
	public @ResponseBody
	String listTrace(HttpServletRequest request) throws IOException {

		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");

		if (rows == null || "".equals(rows)) {
			rows = "10";
		}
		if (page == null || "".equals(page)) {
			page = "1";
		} else {
			int p = Integer.parseInt(page);
			int r = Integer.parseInt(rows);
			int pr = (p - 1) * r + 1;
			page = String.valueOf(pr);
		}
		if (sort == null || "".equals(sort)) {
			sort = "id ";
		}
		if (order == null || "".equals(order)) {
			order = " desc";
		}
		String orderString = sort + " " + order;

		Map map1 = new HashMap();
		int count = traceMapper.selectTraceCountByClause(map1);
		map1.put("begin", Integer.parseInt(page)-1);
		map1.put("pageSize", Integer.parseInt(rows));
		map1.put("orderString", orderString);
		List<TraceBean> list = traceMapper.selectTraceByClause(map1);

		Map map2 = new HashMap();
		map2.put("total", count);
		map2.put("rows", list);

		JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
		return jSONObject.toString();
	}

	@Transactional
	@RequestMapping(value = "/frame/toEditTrace")
	public @ResponseBody
	String toEditTrace(HttpServletRequest request) throws IOException {

		String id = request.getParameter("id");
		TraceBean traceBean = traceMapper.selectTraceByPk(Integer.parseInt(id));

		JSONObject jSONObject = JSONObject.fromObject(traceBean, Constants.getJsonConfig());
		return jSONObject.toString();
	}

	@Transactional
	@RequestMapping(value = "/frame/addTrace")
	public @ResponseBody
	Map addTrace(TraceBean traceBean, HttpServletRequest request) throws IOException, ParseException {
	
		int result = 0;
		String errMessage = "";
		try {
			traceBean.setId(null);
			result = traceMapper.insertTrace(traceBean);
		} catch (Exception e) {
			e.printStackTrace();
			errMessage = e.getCause().toString();
		}
		Map map = new HashMap();
		map.put("result", result);
		map.put("errMessage", errMessage);
		return map;
		
	}

	@Transactional
	@RequestMapping(value = "/frame/updateTrace")
	public @ResponseBody
	Map updateTrace(TraceBean traceBean,HttpServletRequest request) throws IOException, ParseException {
		
		int result = 0;
		String errMessage = "";
		try {
			result = traceMapper.updateTrace(traceBean);
		} catch (Exception e) {
			e.printStackTrace();
			errMessage = e.getCause().toString();
		}
		Map map = new HashMap();
		map.put("result", result);
		map.put("errMessage", errMessage);
		return map;
		
	}

	@Transactional
	@RequestMapping(value = "/frame/deleteTrace")
	public @ResponseBody
	Map deleteTrace(HttpServletRequest request) throws IOException {

		String id = request.getParameter("id");
		int result = 0;
		String errMessage = "";
		try {
		    result = traceMapper.deleteTrace(Integer.parseInt(id));
	    } catch (Exception e) {
			e.printStackTrace();
			errMessage = e.getCause().toString();
		}
		Map map = new HashMap();
		map.put("result", result);
		map.put("errMessage", errMessage);
		return map;
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }



}
