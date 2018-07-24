package com.java.activiti.service.impl;

import com.java.activiti.controller.sysmanage.param.SysZdParam;
import com.java.activiti.dao.ZdDao;
import com.java.activiti.model.ZdBean;
import com.java.activiti.service.SysZdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/20.
 */
@Service
public class SysZdServiceImpl implements SysZdService {
    @Resource
    ZdDao zdDao;

    @Override
    public List<ZdBean> selectSysZdList(SysZdParam sysZdParam) {
        Map map = new HashMap();
        map.put("dmmc", sysZdParam.getDmmc());
        map.put("dmbh", sysZdParam.getDmbh());
        map.put("orderString", sysZdParam.getSortParam().getSort());
        map.put("sortDirection", sysZdParam.getSortParam().getOrder());
        return zdDao.selectZdByClause(map);
    }
}
