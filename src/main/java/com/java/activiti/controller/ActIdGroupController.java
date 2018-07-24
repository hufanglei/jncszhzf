package com.java.activiti.controller;

import java.io.IOException;

import com.java.activiti.model.SelectBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.transaction.annotation.Transactional;

import com.java.activiti.util.Constants;
import com.java.activiti.dao.ActIdGroupMapper;
import com.java.activiti.model.ActIdGroupBean;

@Controller
@RequestMapping("/actidgroup")
public class ActIdGroupController {

	@Autowired
	private ActIdGroupMapper actIdGroupMapper;

	public ActIdGroupMapper getActIdGroupMapper() {
		return actIdGroupMapper;
	}

	public void setActIdGroupMapper(ActIdGroupMapper actIdGroupMapper) {
		this.actIdGroupMapper = actIdGroupMapper;
	}

@Transactional
	@RequestMapping(value = "/listActIdGroup")
	public @ResponseBody
	String listActIdGroup(HttpServletRequest request) throws IOException {

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
			sort = "id_ ";
		}
		if (order == null || "".equals(order)) {
			order = " desc";
		}
		String orderString = sort + " " + order;
		


		Map map1 = new HashMap();
		int count = actIdGroupMapper.selectActIdGroupCountByClause(map1);
		map1.put("begin", Integer.parseInt(page)-1);
		map1.put("pageSize", Integer.parseInt(rows));
		map1.put("orderString", orderString);
		List<ActIdGroupBean> list = actIdGroupMapper.selectActIdGroupByClause(map1);


		Map map2 = new HashMap();
		map2.put("total", count);
		map2.put("rows", list);

		JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
		return jSONObject.toString();
	}

@Transactional
	@RequestMapping(value = "/toEditActIdGroup")
	public @ResponseBody
	String toEditActIdGroup(HttpServletRequest request) throws IOException {

		String id = request.getParameter("id");
		ActIdGroupBean actIdGroupBean = actIdGroupMapper.selectActIdGroupByPk(Integer.parseInt(id));

		JSONObject jSONObject = JSONObject.fromObject(actIdGroupBean, Constants.getJsonConfig());
		return jSONObject.toString();
	}

@Transactional
	@RequestMapping(value = "/addActIdGroup")
	public @ResponseBody
	Map addActIdGroup(ActIdGroupBean actIdGroupBean, HttpServletRequest request) throws IOException, ParseException {
	
		int result = 0;
		String errMessage = "";
		try {
//			actIdGroupBean.setId(null);
			result = actIdGroupMapper.insertActIdGroup(actIdGroupBean);
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
	@RequestMapping(value = "/updateActIdGroup")
	public @ResponseBody
	Map updateActIdGroup(ActIdGroupBean actIdGroupBean,HttpServletRequest request) throws IOException, ParseException {
		
		int result = 0;
		String errMessage = "";
		try {
			result = actIdGroupMapper.updateActIdGroup(actIdGroupBean);
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
	@RequestMapping(value = "/deleteActIdGroup")
	public @ResponseBody
	Map deleteActIdGroup(HttpServletRequest request) throws IOException {

		String id = request.getParameter("id");
		int result = 0;
		String errMessage = "";
		try {
		    result = actIdGroupMapper.deleteActIdGroup(Integer.parseInt(id));
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


	/**
	 * 查询数据字典
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Transactional
	@RequestMapping(value = "/listGroupInfo")
	public @ResponseBody
	List<SelectBean> listGroupInfo(HttpServletRequest request) throws IOException {
		List<SelectBean> beans = new ArrayList<>();
		String groupPid = request.getParameter("groupPid");
		if(StringUtils.isNotBlank(groupPid)) {
			groupPid = "'" + groupPid.replace(",", "','") + "'";
		}
		Map map = new HashMap<>();
		map.put("groupPid",groupPid);
		List<ActIdGroupBean> list = actIdGroupMapper.listGroupInfo(map);

		for(ActIdGroupBean obj :list){
			SelectBean bean = new SelectBean();
			bean.setId(obj.getId()+"");
			bean.setText(obj.getName());
			beans.add(bean);
		}
//		JSONArray jSONObject = JSONArray.fromObject(beans, Constants.getJsonConfig());
		return beans;
	}


	/**
	 * 查询数据字典
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Transactional
	@RequestMapping(value = "/listGroupInfoMenu")
	public @ResponseBody
	List<SelectBean> listGroupInfoMenu(HttpServletRequest request,String groupPid) throws IOException {
		List<SelectBean> beans = new ArrayList<>();
		Map map = new HashMap<>();
		//“涉嫌机构”一栏，各委办局用户(5)在此页面只显示各街道中心，区中心(3)和法制办(4)用户在此页面显示各街道中心和委办局。
		List<ActIdGroupBean> list = null;
		if("5".equals(groupPid)){
            //委办局只显示街道中心
			map.put("groupPid","2");
		     //list = actIdGroupMapper.listGroupInfoMenu(map);
		     list = actIdGroupMapper.listGroupMenu(map);
		}else if ("3".equals(groupPid) || "4".equals(groupPid)){
			//区中心显示委办局和街道中心
			map.put("groupPid","2,5");
//			list = actIdGroupMapper.listGroupInfoMenu(map);
			list = actIdGroupMapper.listGroupMenuInfo(map);
		}else{
			throw  new RuntimeException("督办上报的的角色有误");
		}
		for(ActIdGroupBean obj :list){
			SelectBean bean = new SelectBean();
			bean.setId(obj.getId()+"");
			bean.setText(obj.getName());
			beans.add(bean);
		}
//		JSONArray jSONObject = JSONArray.fromObject(beans, Constants.getJsonConfig());
		return beans;
	}
}
