package com.java.activiti.service;

import com.java.activiti.model.TAttachmentBean;
import com.java.activiti.model.TMainIssueBean;
import com.java.activiti.model.TissueJdjcKhBean;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface TMainIssueService {

    /**
     * 案件分布查询
     * @param beginDate
     * @param endDate
     * @return
     */
    List<Map<String, String>> queryIssueMap(String beginDate, String endDate, String groupId, String groupTypeId);

    /**
     * 查询附件
     * @param taskId
     * @return
     */
    Map<String,List<TAttachmentBean>> queryAttachments(String taskId);

    /**
     * 预览附件
     * @param response
     * @param path
     */
    void showAttachment(HttpServletResponse response, String path);

    JSONObject getIssueAndProcessInfoByIssueNumber(String issueNumber);

    /**
     * 显示上报人
     * @param addUserId
     * @return
     */
    public String showAddUserNameById(String addUserId);

    /**
     * 生成案件编码
     * @return
     */
    String queryCaseNum(String type);

    /**
     * 自行处理 查询扣分项  正常项
     * @param map
     * @return
     */
    List<TMainIssueBean> selectZxclDetails(Map map);

    /**
     * 案件比重
     * @param map
     * @return
     */
    List<TMainIssueBean> selectAjbzDetails(Map map);

    List<TMainIssueBean> selectJsgs(Map map);

    List<TMainIssueBean> selectAsclDetails(Map map);

    List<TMainIssueBean> selectAsclJsgs(Map map);

    List<TMainIssueBean> refreshZxclDetails(Map map);

    List<TMainIssueBean> refreshAsclDetails(Map map);

    List<TMainIssueBean> selectWzxDetails(Map map);

    List<TMainIssueBean> selectWzxJsgs(Map map);

    List<TMainIssueBean> refreshZcAsclDetails(Map map);

    List<TMainIssueBean> selectZcWzxDetails(Map map);

    List<TMainIssueBean> selectKsclDetails(Map map);

    List<TMainIssueBean> refreshZcKsclDetails(Map map);

    List<TMainIssueBean> refreshKsclDetails(Map map);

    List<TMainIssueBean> selectZcAjbzDetails(Map map);

    List<TMainIssueBean> selectAjbzJsgs(Map map);

    List<TMainIssueBean> selectKsclJsgs(Map map);
}
