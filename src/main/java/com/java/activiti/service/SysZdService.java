package com.java.activiti.service;

import com.java.activiti.controller.sysmanage.param.SysZdParam;
import com.java.activiti.model.ZdBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */
public interface SysZdService {
    List<ZdBean> selectSysZdList(SysZdParam sysZdParam);
}
