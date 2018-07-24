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

import com.java.activiti.dao.TQwgWbjMapper;
import com.java.activiti.model.TQwgWbjBean;
import com.java.activiti.util.Constants;

@Controller
public class TQwgWbjController {

	@Autowired
	private TQwgWbjMapper tQwgWbjMapper;

	public TQwgWbjMapper getTQwgWbjMapper() {
		return tQwgWbjMapper;
	}

	public void setTQwgWbjMapper(TQwgWbjMapper tQwgWbjMapper) {
		this.tQwgWbjMapper = tQwgWbjMapper;
	}

	@Transactional
	@RequestMapping(value = "/frame/listTQwgWbj")
	public @ResponseBody
	String listTQwgWbj(HttpServletRequest request) throws IOException {

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
			sort = "wbjid ";
		}
		if (order == null || "".equals(order)) {
			order = " desc";
		}
		String orderString = sort + " " + order;
		


		Map map1 = new HashMap();
		int count = tQwgWbjMapper.selectTQwgWbjCountByClause(map1);
		map1.put("begin", Integer.parseInt(page)-1);
		map1.put("pageSize", Integer.parseInt(rows));
		map1.put("orderString", orderString);
		List<TQwgWbjBean> list = tQwgWbjMapper.selectTQwgWbjByClause(map1);


		Map map2 = new HashMap();
		map2.put("total", count);
		map2.put("rows", list);

		JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
		return jSONObject.toString();
	}

	@Transactional
	@RequestMapping(value = "/frame/toEditTQwgWbj")
	public @ResponseBody
	String toEditTQwgWbj(HttpServletRequest request) throws IOException {

		String id = request.getParameter("id");
		TQwgWbjBean tQwgWbjBean = tQwgWbjMapper.selectTQwgWbjByPk(Integer.parseInt(id));

		JSONObject jSONObject = JSONObject.fromObject(tQwgWbjBean, Constants.getJsonConfig());
		return jSONObject.toString();
	}

	@Transactional
	@RequestMapping(value = "/frame/addTQwgWbj")
	public @ResponseBody
	Map addTQwgWbj(TQwgWbjBean tQwgWbjBean, HttpServletRequest request) throws IOException, ParseException {
	
		int result = 0;
		String errMessage = "";
		try {
			tQwgWbjBean.setWbjid(null);
			result = tQwgWbjMapper.insertTQwgWbj(tQwgWbjBean);
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
	@RequestMapping(value = "/frame/updateTQwgWbj")
	public @ResponseBody
	Map updateTQwgWbj(TQwgWbjBean tQwgWbjBean,HttpServletRequest request) throws IOException, ParseException {
		
		int result = 0;
		String errMessage = "";
		try {
			result = tQwgWbjMapper.updateTQwgWbj(tQwgWbjBean);
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
	@RequestMapping(value = "/frame/deleteTQwgWbj")
	public @ResponseBody
	Map deleteTQwgWbj(HttpServletRequest request) throws IOException {

		String id = request.getParameter("id");
		int result = 0;
		String errMessage = "";
		try {
		    result = tQwgWbjMapper.deleteTQwgWbj(Integer.parseInt(id));
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
