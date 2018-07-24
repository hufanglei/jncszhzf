package com.java.activiti.controller;

import com.java.activiti.dao.ActEvtLogMapper;
import com.java.activiti.model.ActEvtLog;
import com.java.activiti.util.Constants;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/logs")
public class ActEvtLogController {

    @Resource
    private ActEvtLogMapper actEvtLogMapper;

    /**
     * 日志分页查询
     *
     * @param response
     * @param page     当前页数
     * @param rows     每页显示页数
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/logPage")
    public @ResponseBody
    String logPage(HttpServletResponse response, String qhzdName, String khyf, String page, String rows, HttpSession session) throws Exception {
        Map map1 = new HashMap();

        int count = actEvtLogMapper.selectCount(map1);
        map1.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
        map1.put("pageSize", Integer.parseInt(rows));
        map1.put("orderString", "log_nr_ desc");
        List<ActEvtLog> list = actEvtLogMapper.selectListPage(map1);

        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);

        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        return jSONObject.toString();
    }

}