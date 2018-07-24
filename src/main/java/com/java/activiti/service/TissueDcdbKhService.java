package com.java.activiti.service;

import com.java.activiti.model.TissueDcdbKhBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface TissueDcdbKhService {

    Map selectGdDcdbKh(HttpServletRequest request, String startDay, String endDay, String page, String rows, String groupId, String groupTypeId, HttpSession session);

    TissueDcdbKhBean getDbIssueByProcess(String processId)throws Exception;

    List<TissueDcdbKhBean> selectffts(Map map);

    List<TissueDcdbKhBean> selectfftsJsgs(Map map);

    List<TissueDcdbKhBean> refreshDcdbDetails(Map map);

    List<TissueDcdbKhBean> refreshZcDcdbDetails(Map map);

    int selectCountFfts(Map map);

    int selectCountZcFfts(Map map);

    int selectCountKfFfts(Map map);
}
