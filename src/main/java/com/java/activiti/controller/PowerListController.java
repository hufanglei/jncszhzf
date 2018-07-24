package com.java.activiti.controller;

import com.java.activiti.dao.PowerListMapper;
import com.java.activiti.model.PowerListBean;
import com.java.activiti.service.PowerListService;
import com.java.activiti.util.Constants;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PowerListController {

    @Resource
    private PowerListMapper powerListMapper;

    @Resource
    private PowerListService powerListService;

    public PowerListMapper getPowerListMapper() {
        return powerListMapper;
    }

    public void setPowerListMapper(PowerListMapper powerListMapper) {
        this.powerListMapper = powerListMapper;
    }

    @Transactional
    @RequestMapping(value = "/frame/listPowerList")
    public @ResponseBody
    String listPowerList(HttpServletRequest request) throws IOException {

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
        int count = powerListMapper.selectPowerListCountByClause(map1);
        map1.put("begin", Integer.parseInt(page) - 1);
        map1.put("pageSize", Integer.parseInt(rows));
        map1.put("orderString", orderString);
        List<PowerListBean> list = powerListMapper.selectPowerListByClause(map1);


        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);

        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        return jSONObject.toString();
    }


    @Transactional
    @RequestMapping(value = "/frame/listAllPowerList")
    public @ResponseBody
    Map listAllPowerList(HttpServletRequest request) throws IOException {

//		String rows = request.getParameter("rows");
//		String page = request.getParameter("page");
//		String sort = request.getParameter("sort");
//		String order = request.getParameter("order");
//
//		if (rows == null || "".equals(rows)) {
//			rows = "10";
//		}
//		if (page == null || "".equals(page)) {
//			page = "1";
//		} else {
//			int p = Integer.parseInt(page);
//			int r = Integer.parseInt(rows);
//			int pr = (p - 1) * r + 1;
//			page = String.valueOf(pr);
//		}
//		if (sort == null || "".equals(sort)) {
//			sort = "id ";
//		}
//		if (order == null || "".equals(order)) {
//			order = " desc";
//		}
//		String orderString = sort + " " + order;


        Map map1 = new HashMap();
        int count = powerListMapper.selectPowerListCountByClause(map1);
//		map1.put("begin", Integer.parseInt(page)-1);
//		map1.put("pageSize", Integer.parseInt(rows));
//		map1.put("orderString", orderString);
        List<PowerListBean> list = powerListMapper.selectPowerListByClause(map1);


        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);
        return map2;
    }

    @Transactional
    @RequestMapping(value = "/frame/toEditPowerList")
    public @ResponseBody
    String toEditPowerList(HttpServletRequest request) throws IOException {

        String id = request.getParameter("id");
        PowerListBean powerListBean = powerListMapper.selectPowerListByPk(Integer.parseInt(id));
        JSONObject jSONObject = JSONObject.fromObject(powerListBean, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    @Transactional
    @RequestMapping(value = "/frame/addPowerList")
    @ResponseBody
    public Map addPowerList(PowerListBean powerListBean, HttpServletRequest request) throws IOException, ParseException {

        int result = 0;
        String errMessage = "";
        try {
            powerListBean.setId(null);
            result = powerListMapper.insertPowerList(powerListBean);
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
    @RequestMapping(value = "/frame/updatePowerList")
    public @ResponseBody
    Map updatePowerList(PowerListBean powerListBean, HttpServletRequest request) throws IOException, ParseException {

        int result = 0;
        String errMessage = "";
        try {
            result = powerListMapper.updatePowerList(powerListBean);
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
    @RequestMapping(value = "/frame/deletePowerList")
    public @ResponseBody
    Map deletePowerList(HttpServletRequest request) throws IOException {

        String id = request.getParameter("id");
        int result = 0;
        String errMessage = "";
        try {
            result = powerListMapper.deletePowerList(Integer.parseInt(id));
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
     * 查询数据字典表数据 分页展示
     *
     * @param
     * @return
     * @throws IOException
     */
    @Transactional
    @RequestMapping(value = "/frame/listAllPower")
    public @ResponseBody
    Map listAllPower(HttpServletResponse response, String page, String rows, String ssWeibanju) throws IOException {
        List<PowerListBean> list = powerListService.selectPowerByClause(page, rows, ssWeibanju);
        Map map = new HashMap<String, Object>();
        map.put("ssWeibanju", ssWeibanju);
        int count = powerListMapper.selectPowerListCountByClause(map);
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(list);
        result.put("total", count);
        result.put("rows", jsonArray);
        try {
            ResponseUtil.write(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 数据字典表数据 新增保存
     *
     * @param powerListBean
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @Transactional
    @RequestMapping(value = "/frame/addPower")
    @ResponseBody
    public int addPower(PowerListBean powerListBean) {
        int result = 0;
        try {
            result = powerListService.insertPowerList(powerListBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 数据字典表数据 更新保存
     *
     * @param powerListBean
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @Transactional
    @RequestMapping(value = "/frame/updatePower")
    public @ResponseBody
    int updatePower(PowerListBean powerListBean) throws IOException, ParseException {

        int result = 0;
        try {
            result = powerListService.updatePowerList(powerListBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 数据字典表数据 删除数据字典数据
     *
     * @return
     * @throws IOException
     */
    @Transactional
    @RequestMapping(value = "/frame/deletePower")
    public @ResponseBody
    int deletePower(String id) throws IOException {
        int result = 0;
        try {
            result = powerListService.deletePower(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 所属部门下拉框
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/frame/selectSsWeibanju")
    public String selectSsWeibanju(HttpServletResponse response) throws Exception {

        List<PowerListBean> groupList = powerListService.selectSsWeibanju();
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(groupList);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

}
