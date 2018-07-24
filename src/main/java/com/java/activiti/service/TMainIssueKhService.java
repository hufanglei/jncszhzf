package com.java.activiti.service;


import com.java.activiti.model.TIssueXcbdKh;
import com.java.activiti.model.TissueDcdbKhBean;
import com.java.activiti.model.TissueRadioKhBean;
import com.java.activiti.model.TissueRadioSetKhBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface TMainIssueKhService {


    /**
     * 获取督察督办编号
     * @return
     */
    String getDcdbNumInfo();

    /**
     * 计算宣传报道分数
     * @param xcbdKh
     * @return
     */
    Float getScore(TIssueXcbdKh xcbdKh);

    List<TissueRadioKhBean> selectByKhyf(Map map1);

    int  updateAjbz(TissueRadioKhBean tissueRadioKhBean);

    /**
     * 更新案件比重信息
     * @param khyf
     * @param avg12345num
     * @param coefficient
     * @return
     */
    int updateAjbzInfo(String khyf, String avg12345num, String coefficient);

    /**
     * 督办案件的保存或者更新
     * @param bean
     * @param session
     * @return
     */
    public int saveorUpdateTissueDcdbKhResult(TissueDcdbKhBean bean,String fileData, HttpSession session) throws Exception;

    int saveRadioKh(HttpServletRequest request, String  khyf)  throws Exception;
}
