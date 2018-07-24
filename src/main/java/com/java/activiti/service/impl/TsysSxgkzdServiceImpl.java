package com.java.activiti.service.impl;

import com.java.activiti.dao.TsysSxgkzdMapper;
import com.java.activiti.service.TsysSxgkzdService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TsysSxgkzdServiceImpl implements TsysSxgkzdService{

    @Resource
    private TsysSxgkzdMapper tsysSxgkzdMapper;
    @Override
    public List<Map<String, Object>> selectSxgkzdList(String sxid) {
        Map map=new HashMap();
    /*    map.put("sxid",tsysSxgkzdBean.getSxid());
        map.put("sxjb",tsysSxgkzdBean.getSxjb());
        map.put("parentId",tsysSxgkzdBean.getParentid());
        map.put("typeMc",tsysSxgkzdBean.getTypeMc());*/

        return tsysSxgkzdMapper.selectSxgkzdList(sxid);
    }
}
