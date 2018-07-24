package com.java.activiti.service.impl;

import com.java.activiti.dao.TissueDcdbKhMapper;
import com.java.activiti.model.MemberShip;
import com.java.activiti.model.TMainIssueBean;
import com.java.activiti.model.TissueDcdbKhBean;
import com.java.activiti.model.User;
import com.java.activiti.service.TissueDcdbKhService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Transactional
@Service("tissueDcdbKhService")
public class TissueDcdbKhServiceImpl  implements TissueDcdbKhService {
    @Resource
    private TissueDcdbKhMapper tissueDcdbKhMapper;
    @Override
    public Map selectGdDcdbKh(HttpServletRequest request, String startDay, String endDay, String page, String rows, String groupId, String groupTypeId, HttpSession session) {
        Map queryMap = new HashMap();
        Map map = new HashMap();
        int count =0;
        List<TissueDcdbKhBean> list =new ArrayList<>();
        String caseType = request.getParameter("caseType");
        //根据会话信息获取当前登录账号
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户
        User currentUser = currentMemberShip.getUser();
        String userId = currentUser.getId();
        Integer userTypeID = currentUser.getUserTypeID();
        if(userTypeID==null){
            map.put("total", count);
            map.put("rows", list);
            return map;
        }

        queryMap.put("startDay", startDay);
        queryMap.put("endTime", endDay);
        queryMap.put("caseType", caseType);


        queryMap.put("groupId",groupId);
        count = tissueDcdbKhMapper.selectGdDcdbKhCountByGroupId(queryMap);
        if(count>0){
            queryMap.put("pageIndex", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
            queryMap.put("pageSize", Integer.parseInt(rows));

                list = tissueDcdbKhMapper.selectGdDcdbKhByGroupId(queryMap);

        }
        map.put("total", count);
        map.put("rows", list);

        return map;
    }

    @Override
    public TissueDcdbKhBean getDbIssueByProcess(String processId) throws Exception{
        return tissueDcdbKhMapper.getDbIssueByProcess(processId);
    }
    @Override
    public   List<TissueDcdbKhBean> selectffts(Map map ){
        List<TissueDcdbKhBean> list = tissueDcdbKhMapper.selectffts(map);
        return list;
    }
    @Override
    public   List<TissueDcdbKhBean> selectfftsJsgs(Map map ){
        List<TissueDcdbKhBean> list = tissueDcdbKhMapper.selectfftsJsgs(map);
        return list;
    }
    @Override
    public   List<TissueDcdbKhBean> refreshDcdbDetails(Map map ){
        List<TissueDcdbKhBean> list = tissueDcdbKhMapper.refreshDcdbDetails(map);
        return list;
    }
    @Override
    public   List<TissueDcdbKhBean> refreshZcDcdbDetails(Map map ){
        List<TissueDcdbKhBean> list = tissueDcdbKhMapper.refreshZcDcdbDetails(map);
        return list;
    }
    @Override
    public   int  selectCountFfts(Map map ){
       int list = tissueDcdbKhMapper.selectCountFfts(map);
        return list;
    }
    @Override
    public   int  selectCountZcFfts(Map map ){
       int list = tissueDcdbKhMapper.selectCountZcFfts(map);
        return list;
    }
    @Override
    public   int  selectCountKfFfts(Map map ){
       int list = tissueDcdbKhMapper.selectCountKfFfts(map);
        return list;
    }
}
