package com.java.activiti.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.activiti.dao.WeibanjuzdMapper;
import com.java.activiti.model.WeibanjuzdBean;
import com.java.activiti.util.Constants;

@Controller
public class WeibanjuzdController {

	@Autowired
	private WeibanjuzdMapper weibanjuzdMapper;

	public WeibanjuzdMapper getWeibanjuzdMapper() {
		return weibanjuzdMapper;
	}

	public void setWeibanjuzdMapper(WeibanjuzdMapper weibanjuzdMapper) {
		this.weibanjuzdMapper = weibanjuzdMapper;
	}
	
	@Transactional
	@RequestMapping(value = "/frame/listAllWeibanjuzd")
	public
	String listAllWeibanjuzdByClause(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<WeibanjuzdBean> list = weibanjuzdMapper.selectAllWeibanjuzdByClause(null);
		JSONObject result=new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(list, Constants.getJsonConfig());
		result.put("groupList", jsonArray);
		ResponseUtil.write(response, result);
		return null;
	}

	@Transactional
	@RequestMapping(value = "/frame/listWeibanjuzd")
	public
	String listWeibanjuzd(HttpServletRequest request,HttpServletResponse response) throws Exception {

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
		int count = weibanjuzdMapper.selectWeibanjuzdCountByClause(map1);
		map1.put("begin", Integer.parseInt(page)-1);
		map1.put("pageSize", Integer.parseInt(rows));
		map1.put("orderString", orderString);
		List<WeibanjuzdBean> list = weibanjuzdMapper.selectWeibanjuzdByClause(map1);


		Map map2 = new HashMap();
		map2.put("total", count);
		map2.put("rows", list);

		JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
		ResponseUtil.write(response, jSONObject);
		return null;
	}

	@Transactional
	@RequestMapping(value = "/frame/toEditWeibanjuzd")
	public @ResponseBody
	String toEditWeibanjuzd(HttpServletRequest request) throws IOException {

		String id = request.getParameter("id");
		WeibanjuzdBean weibanjuzdBean = weibanjuzdMapper.selectWeibanjuzdByPk(Integer.parseInt(id));

		JSONObject jSONObject = JSONObject.fromObject(weibanjuzdBean, Constants.getJsonConfig());
		return jSONObject.toString();
	}

	@Transactional
	@RequestMapping(value = "/frame/addWeibanjuzd")
	public @ResponseBody
	Map addWeibanjuzd(WeibanjuzdBean weibanjuzdBean, HttpServletRequest request) throws IOException, ParseException {
	
		int result = 0;
		String errMessage = "";
		try {
			weibanjuzdBean.setId(null);
			result = weibanjuzdMapper.insertWeibanjuzd(weibanjuzdBean);
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
	@RequestMapping(value = "/frame/updateWeibanjuzd")
	public @ResponseBody
	Map updateWeibanjuzd(WeibanjuzdBean weibanjuzdBean,HttpServletRequest request) throws IOException, ParseException {
		
		int result = 0;
		String errMessage = "";
		try {
			result = weibanjuzdMapper.updateWeibanjuzd(weibanjuzdBean);
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
	@RequestMapping(value = "/frame/deleteWeibanjuzd")
	public @ResponseBody
	Map deleteWeibanjuzd(HttpServletRequest request) throws IOException {

		String id = request.getParameter("id");
		int result = 0;
		String errMessage = "";
		try {
		    result = weibanjuzdMapper.deleteWeibanjuzd(Integer.parseInt(id));
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
