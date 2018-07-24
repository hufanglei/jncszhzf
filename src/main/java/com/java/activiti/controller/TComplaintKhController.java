package com.java.activiti.controller;
import com.java.activiti.dao.TComplaintKhMapper;
import com.java.activiti.model.*;
import com.java.activiti.util.Constants;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jasypt.commons.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hfl on 2017/11/28.
 * 反复投诉Controller
 */
@Controller
@RequestMapping("/")
public class TComplaintKhController {

    @Resource
    private TComplaintKhMapper  tComplaintKhMapper;


    @RequestMapping("/jxkh/fftskhPage")
    @ResponseBody
    public String fftskhPage(HttpServletResponse response, String qhzdName, String khyf, String page,String type, String rows, HttpSession session){
        //根据会话信息获取当前登录账号
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        String qhzdId = "";
        //取出用户
        User currentUser = currentMemberShip.getUser();
        if (currentUser.getUserTypeID() != 0) {
            if (CommonUtils.isEmpty(currentUser.getJiedao())) {
                qhzdId = "-1";
            } else {
                qhzdId = currentUser.getJiedao().substring(0, 12);
            }
        }
        Map map1 = new HashMap();
        map1.put("qhzdId", qhzdId);
        map1.put("qhzdName", qhzdName);
        map1.put("khyf", khyf);
        map1.put("type", type);
        int count = tComplaintKhMapper.selectCount(map1);
        map1.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
        map1.put("pageSize", Integer.parseInt(rows));
        map1.put("orderString", "id desc");
        List<TComplaintKhBean> list = tComplaintKhMapper.selectListPage(map1);

        Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);

        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    /**
     * 下拉框显示所有的组织
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/jxkh/listAllGroups")
    public String listAllGroups(HttpServletResponse response) throws Exception{
        List<HashMap> list=tComplaintKhMapper.selectAllOrg();
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(list);
        result.put("groupList", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 保存
     * @param response
     * @param complaint
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/jxkh/saveComplaint")
    public String saveComplaint(HttpServletResponse response,TComplaintKhBean complaint, HttpSession session) throws Exception{
        complaint.setCreateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户
        User currentUser = currentMemberShip.getUser();
        complaint.setKhGroupId(currentUser.getId());
        int count = tComplaintKhMapper.insertSelective(complaint);
        JSONObject json=new JSONObject();
        if(count>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }



    /**
     *  编辑考核项
     * @param response
     * @param complaint
     * @return
     * @throws Exception
     */
    @RequestMapping("/jxkh/EditComplaint")
    public String editComplaint(HttpServletResponse response,TComplaintKhBean complaint) throws Exception{
        int count = tComplaintKhMapper.updateByPrimaryKeySelective(complaint);
        JSONObject json=new JSONObject();
        if(count > 0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *  删除考核项
     */
    @RequestMapping("/jxkh/deleteComplaint")
    public String deleteComplaint(HttpServletResponse response,HttpServletRequest request) throws Exception{
        String ids=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<Integer> list=new ArrayList<Integer>();
        String[] strs = ids.split(",");
        for (String str:strs){
            Integer id = Integer.valueOf(str);
            list.add(id);
        }
        try {
            int userResult=tComplaintKhMapper.deleteComplaint(list);
            if(userResult>0){
                json.put("success", true);
            }else{
                json.put("success", false);
            }
        } catch (Exception e) {
            json.put("success", false);
            e.printStackTrace();
        }
        ResponseUtil.write(response, json);
        return null;
    }

}
