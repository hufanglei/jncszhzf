package com.java.activiti.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.activiti.dao.ZdDao;
import com.java.activiti.model.*;
import com.java.activiti.util.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.activiti.service.QhzdService;
import com.java.activiti.util.ResponseUtil;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 行政区划相关方法controller
 * @author wujj@sugon.com
 *
 */
@Controller
@RequestMapping("/qhzd")
public class QhzdController {

	/**
	 * 私有变量，区划服务实例
	 */
	@Resource
	private QhzdService qhzdService;

	@Resource
	private ZdDao zdDao;

	/**
	 * 查询数据字典
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Transactional
	@RequestMapping(value = "/listDictionary")
	public @ResponseBody
	String listDictionary(HttpServletRequest request) throws IOException {
		List<SelectBean> beans = new ArrayList<>();
		String dmmc =request.getParameter("dmmc");
		Map map = new HashMap<>();
		map.put("dmmc",dmmc);
		List<ZdBean> list = zdDao.listDictionary(map);

		for(ZdBean obj :list){
			SelectBean bean = new SelectBean();
			bean.setId(obj.getId()+"");
			bean.setText(obj.getDmms());
			beans.add(bean);
		}
		JSONArray jSONObject = JSONArray.fromObject(beans, Constants.getJsonConfig());
		return jSONObject.toString();
	}

	/**
	 *根据dmmc 查询 案件来源字典
	 * @param dmmc
	 * @return
	 */
	@RequestMapping("/getDictionary")
	@ResponseBody
	public List<Map<String,Object>> getDictionary(String dmmc) {
		List<Map<String,Object>> list = zdDao.getDictionary(dmmc);
		return list;
	}
	/**
	 * 查询区划字典相关方法
	 * 
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/selectQhzd")
	public String selectQhzd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String qhdm = request.getParameter("qhdm");
		String qhjbStr =request.getParameter("qhjb");
		int qhjb = (qhjbStr==null)?0:Integer.parseInt(qhjbStr);
		String ssqhdm = request.getParameter("ssqhdm");
		List<QhzdBean> list = qhzdService.getQhzd(qhdm, qhjb, ssqhdm);
		JsonConfig jsonConfig=new JsonConfig();
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(list, jsonConfig);
		result.put("rows", jsonArray);
		ResponseUtil.write(response, result);
		return null;
	}
	

	/**
	 * 分权限查询区划字典相关方法
	 *
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectQhzdByAuthority")
	public String selectQhzdByAuthority(HttpServletRequest request, HttpServletResponse response) throws Exception {


		MemberShip memberShip  = (MemberShip) request.getSession().getAttribute("currentMemberShip");
		List<Group> list = qhzdService.getQhzdByAuthority(memberShip);

		JsonConfig jsonConfig=new JsonConfig();
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(list, jsonConfig);
		result.put("rows", jsonArray);
		ResponseUtil.write(response, result);
		return null;
	}


	/**
	 * 获取指定街道的行政区划信息树（本街道及下属社区信息）
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/selectQhzdByStreetPrefix")
	public String getStreetQhzdInfoTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String qhdm = request.getParameter("qhdm");
		List<QhzdBean> list = qhzdService.getStreetQhzdInfoTree(qhdm);
		JsonConfig jsonConfig=new JsonConfig();
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(list, jsonConfig);
		result.put("rows", jsonArray);
		ResponseUtil.write(response, result);
		return null;
	}
}
