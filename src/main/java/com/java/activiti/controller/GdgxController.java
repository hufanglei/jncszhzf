package com.java.activiti.controller;


import com.java.activiti.service.GdgxService;
import com.java.activiti.util.Constants;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 归档共享业务
 */
@Controller
@RequestMapping("/gdgx")
public class GdgxController {

    @Resource
    private GdgxService gdgxService;

    /**
     * 归档业务查询
     *
     * @param response
     * @param page     当前页数
     * @param rows     每页显示页数
     * @param issueSubject 案件归口
     * @return
     * @throws Exception
     */
    @RequestMapping("/gdgxPage")
    public @ResponseBody
    String gdgxPage(HttpServletResponse response, String startDay, String endDay,String issueSubject,String page, String rows, String s_name, HttpSession session) throws Exception {
        //根据会话信息获取当前登录账号
//        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户
//        User currentUser = currentMemberShip.getUser();
//        String userId = currentUser.getId();

//        if (s_name == null || s_name.equals("null")) {
//            s_name = "";
//        }

        Map map = gdgxService.gdgxPage(startDay,endDay,issueSubject,page,rows,session);

        JSONObject jSONObject = JSONObject.fromObject(map, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    /**
     * 归档业务查询
     * @param startDay
     * @param endDay
     * @param issueSubject 案件归口
     * @param page
     * @param rows
     * @param groupId  案件执行街道 or 委办局
     * @param groupTypeId
     * @param session
     * @return
     */
    @RequestMapping("/selectGdgxge")
    public @ResponseBody String  selectGdgxge(HttpServletRequest request, String startDay, String endDay,String issueSubject,String page, String rows, String groupId,String groupTypeId, HttpSession session,String type){
        Map map = gdgxService.selectGdgxge(request,startDay,endDay,issueSubject,page,rows,groupId,groupTypeId,session,type);
        JSONObject jSONObject = JSONObject.fromObject(map, Constants.getJsonConfig());
        return jSONObject.toString();
    }
    @RequestMapping("/selecAttachList")
    @ResponseBody
    public List<Map<String,Object>> selecAttachList(String processId) {
        List<Map<String,Object>> list = gdgxService.selecAttachList(processId);
        return list;
    }
    @RequestMapping("/selecPtAttachList")
    @ResponseBody
    public List<Map<String,Object>> selecPtAttachList(String processId) {
        List<Map<String,Object>> list = gdgxService.selecPtAttachList(processId);
        return list;
    }

}
