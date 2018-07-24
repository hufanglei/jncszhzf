package com.java.activiti.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.activiti.dao.TQwgStatusMapper;
import com.java.activiti.model.TQwgStatusBean;
import com.java.activiti.util.Constants;

@Controller
public class TQwgStatusController {

	@Autowired
	private TQwgStatusMapper tQwgStatusMapper;

	public TQwgStatusMapper getTQwgStatusMapper() {
		return tQwgStatusMapper;
	}

	public void setTQwgStatusMapper(TQwgStatusMapper tQwgStatusMapper) {
		this.tQwgStatusMapper = tQwgStatusMapper;
	}

	@Transactional
	@RequestMapping(value = "/frame/listTQwgStatus")
	public @ResponseBody
	String listTQwgStatus(HttpServletRequest request) throws IOException {

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
			sort = "orderno ";
		}
		if (order == null || "".equals(order)) {
			order = " desc";
		}
		String orderString = sort + " " + order;
		


		Map map1 = new HashMap();
		int count = tQwgStatusMapper.selectTQwgStatusCountByClause(map1);
		map1.put("begin", Integer.parseInt(page)-1);
		map1.put("pageSize", Integer.parseInt(rows));
		map1.put("orderString", orderString);
		List<TQwgStatusBean> list = tQwgStatusMapper.selectTQwgStatusByClause(map1);


		Map map2 = new HashMap();
		map2.put("total", count);
		map2.put("rows", list);

		JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
		return jSONObject.toString();
	}

	@Transactional
	@RequestMapping(value = "/frame/toEditTQwgStatus")
	public @ResponseBody
	String toEditTQwgStatus(HttpServletRequest request) throws IOException {

		String id = request.getParameter("id");
		TQwgStatusBean tQwgStatusBean = tQwgStatusMapper.selectTQwgStatusByPk(Integer.parseInt(id));

		JSONObject jSONObject = JSONObject.fromObject(tQwgStatusBean, Constants.getJsonConfig());
		return jSONObject.toString();
	}

	@Transactional
	@RequestMapping(value = "/frame/addTQwgStatus")
	public @ResponseBody
	Map addTQwgStatus(TQwgStatusBean tQwgStatusBean, HttpServletRequest request) throws IOException, ParseException {
	
		int result = 0;
		String errMessage = "";
		try {
			tQwgStatusBean.setOrderno(null);
			result = tQwgStatusMapper.insertTQwgStatus(tQwgStatusBean);
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
	@RequestMapping(value = "/frame/insertTQwgStatus")
	public @ResponseBody
	Map insertTQwgStatus(TQwgStatusBean tQwgStatusBean, HttpServletRequest request) throws IOException, ParseException {
	
		int result = 0;
		String errMessage = "";
		try {
			tQwgStatusBean.setOrderno(null);
			result = tQwgStatusMapper.insertTQwgStatus(tQwgStatusBean);
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
	@RequestMapping(value = "/frame/updateTQwgStatus")
	public @ResponseBody
	Map updateTQwgStatus(TQwgStatusBean tQwgStatusBean,HttpServletRequest request) throws IOException, ParseException {
		
		int result = 0;
		String errMessage = "";
		try {
			result = tQwgStatusMapper.updateTQwgStatus(tQwgStatusBean);
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
	@RequestMapping(value = "/frame/deleteTQwgStatus")
	public @ResponseBody
	Map deleteTQwgStatus(HttpServletRequest request) throws IOException {

		String id = request.getParameter("id");
		int result = 0;
		String errMessage = "";
		try {
		    result = tQwgStatusMapper.deleteTQwgStatus(Integer.parseInt(id));
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
